package com.tencent.tinker.lib.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.Service;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.tencent.tinker.loader.TinkerRuntimeException;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class TinkerPatchService {
    private static final int MIN_SDKVER_TO_USE_JOBSCHEDULER = 26;
    private static final String PATCH_PATH_EXTRA = "patch_path_extra";
    private static final String RESULT_CLASS_EXTRA = "patch_result_class";
    private static final String TAG = "Tinker.TinkerPatchService";
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public static int notificationId = ShareConstants.TINKER_PATCH_SERVICE_NOTIFICATION;
    private static Class<? extends AbstractResultService> resultServiceClass = null;
    private static AbstractPatch upgradePatchProcessor = null;

    public static class IntentServiceRunner extends IntentService {

        public static class InnerService extends Service {
            public void onCreate() {
                super.onCreate();
                try {
                    startForeground(TinkerPatchService.notificationId, new Notification());
                } catch (Throwable e) {
                    TinkerLog.e(TinkerPatchService.TAG, "InnerService set service for push exception:%s.", e);
                }
                stopSelf();
            }

            public void onDestroy() {
                stopForeground(true);
                super.onDestroy();
            }

            public IBinder onBind(Intent intent) {
                return null;
            }
        }

        public IntentServiceRunner() {
            super("TinkerPatchService");
        }

        /* access modifiers changed from: protected */
        public void onHandleIntent(@Nullable Intent intent) {
            increasingPriority();
            TinkerPatchService.doApplyPatch(getApplicationContext(), intent);
        }

        private void increasingPriority() {
            if (VERSION.SDK_INT >= 26) {
                TinkerLog.i(TinkerPatchService.TAG, "for system version >= Android O, we just ignore increasingPriority job to avoid crash or toasts.", new Object[0]);
            } else if ("ZUK".equals(Build.MANUFACTURER)) {
                TinkerLog.i(TinkerPatchService.TAG, "for ZUK device, we just ignore increasingPriority job to avoid crash.", new Object[0]);
            } else {
                TinkerLog.i(TinkerPatchService.TAG, "try to increase patch process priority", new Object[0]);
                try {
                    Notification notification = new Notification();
                    if (VERSION.SDK_INT < 18) {
                        startForeground(TinkerPatchService.notificationId, notification);
                        return;
                    }
                    startForeground(TinkerPatchService.notificationId, notification);
                    startService(new Intent(this, InnerService.class));
                } catch (Throwable e) {
                    TinkerLog.i(TinkerPatchService.TAG, "try to increase patch process priority error:" + e, new Object[0]);
                }
            }
        }
    }

    @TargetApi(21)
    public static class JobServiceRunner extends JobService {
        private JobAsyncTask mTask = null;

        private static class JobAsyncTask extends AsyncTask<JobParameters, Void, Void> {
            private final WeakReference<JobService> mHolderRef;

            JobAsyncTask(JobService holder) {
                this.mHolderRef = new WeakReference<>(holder);
            }

            /* access modifiers changed from: protected */
            public Void doInBackground(JobParameters... paramsList) {
                JobParameters params = paramsList[0];
                PersistableBundle extras = params.getExtras();
                Intent paramIntent = new Intent();
                paramIntent.putExtra(TinkerPatchService.PATCH_PATH_EXTRA, extras.getString(TinkerPatchService.PATCH_PATH_EXTRA));
                paramIntent.putExtra(TinkerPatchService.RESULT_CLASS_EXTRA, extras.getString(TinkerPatchService.RESULT_CLASS_EXTRA));
                JobService holder = (JobService) this.mHolderRef.get();
                if (holder == null) {
                    TinkerLog.e(TinkerPatchService.TAG, "unexpected case: holder job service is null.", new Object[0]);
                } else {
                    TinkerPatchService.doApplyPatch(holder.getApplicationContext(), paramIntent);
                    notifyFinished(params);
                }
                return null;
            }

            private void notifyFinished(JobParameters params) {
                JobService holder = (JobService) this.mHolderRef.get();
                if (holder != null) {
                    holder.jobFinished(params, false);
                }
            }
        }

        public boolean onStartJob(JobParameters params) {
            this.mTask = new JobAsyncTask(this);
            this.mTask.execute(new JobParameters[]{params});
            return true;
        }

        public boolean onStopJob(JobParameters params) {
            TinkerLog.w(TinkerPatchService.TAG, "Stopping TinkerPatchJob service.", new Object[0]);
            if (this.mTask != null) {
                this.mTask.cancel(true);
                this.mTask = null;
            }
            return false;
        }
    }

    public static void runPatchService(final Context context, final String path) {
        try {
            if (VERSION.SDK_INT < 26) {
                runPatchServiceByIntentService(context, path);
            } else if (!runPatchServiceByJobScheduler(context, path)) {
                TinkerLog.e(TAG, "start patch job service fail, try to fallback to intent service.", new Object[0]);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        TinkerLog.i(TinkerPatchService.TAG, "fallback: prepare trying to run patch service by intent service.", new Object[0]);
                        if (!TinkerServiceInternals.isTinkerPatchServiceRunning(context)) {
                            TinkerPatchService.runPatchServiceByIntentService(context, path);
                        }
                    }
                }, TimeUnit.SECONDS.toMillis(3));
            }
        } catch (Throwable throwable) {
            TinkerLog.e(TAG, "start patch service fail, exception:" + throwable, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public static void runPatchServiceByIntentService(Context context, String path) {
        TinkerLog.i(TAG, "run patch service by intent service.", new Object[0]);
        Intent intent = new Intent(context, IntentServiceRunner.class);
        intent.putExtra(PATCH_PATH_EXTRA, path);
        intent.putExtra(RESULT_CLASS_EXTRA, resultServiceClass.getName());
        context.startService(intent);
    }

    @TargetApi(21)
    private static boolean runPatchServiceByJobScheduler(Context context, String path) {
        boolean z = true;
        TinkerLog.i(TAG, "run patch service by job scheduler.", new Object[0]);
        Builder jobInfoBuilder = new Builder(1, new ComponentName(context, JobServiceRunner.class));
        PersistableBundle extras = new PersistableBundle();
        extras.putString(PATCH_PATH_EXTRA, path);
        extras.putString(RESULT_CLASS_EXTRA, resultServiceClass.getName());
        jobInfoBuilder.setExtras(extras);
        jobInfoBuilder.setOverrideDeadline(5);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler == null) {
            TinkerLog.e(TAG, "jobScheduler is null.", new Object[0]);
            return false;
        }
        if (jobScheduler.schedule(jobInfoBuilder.build()) != 1) {
            z = false;
        }
        return z;
    }

    public static void setPatchProcessor(AbstractPatch upgradePatch, Class<? extends AbstractResultService> serviceClass) {
        upgradePatchProcessor = upgradePatch;
        resultServiceClass = serviceClass;
        try {
            Class.forName(serviceClass.getName());
        } catch (ClassNotFoundException e) {
        }
    }

    public static String getPatchPathExtra(Intent intent) {
        if (intent != null) {
            return ShareIntentUtil.getStringExtra(intent, PATCH_PATH_EXTRA);
        }
        throw new TinkerRuntimeException("getPatchPathExtra, but intent is null");
    }

    public static String getPatchResultExtra(Intent intent) {
        if (intent != null) {
            return ShareIntentUtil.getStringExtra(intent, RESULT_CLASS_EXTRA);
        }
        throw new TinkerRuntimeException("getPatchResultExtra, but intent is null");
    }

    public static Class<? extends Service> getRealRunnerClass() {
        if (VERSION.SDK_INT < 26) {
            return IntentServiceRunner.class;
        }
        return JobServiceRunner.class;
    }

    public static void setTinkerNotificationId(int id) {
        notificationId = id;
    }

    /* access modifiers changed from: private */
    public static void doApplyPatch(Context context, Intent intent) {
        boolean result;
        Tinker tinker = Tinker.with(context);
        tinker.getPatchReporter().onPatchServiceStart(intent);
        if (intent == null) {
            TinkerLog.e(TAG, "TinkerPatchService received a null intent, ignoring.", new Object[0]);
            return;
        }
        String path = getPatchPathExtra(intent);
        if (path == null) {
            TinkerLog.e(TAG, "TinkerPatchService can't get the path extra, ignoring.", new Object[0]);
            return;
        }
        File patchFile = new File(path);
        long begin = SystemClock.elapsedRealtime();
        Throwable e = null;
        PatchResult patchResult = new PatchResult();
        try {
            if (upgradePatchProcessor == null) {
                throw new TinkerRuntimeException("upgradePatchProcessor is null.");
            }
            result = upgradePatchProcessor.tryPatch(context, path, patchResult);
            long cost = SystemClock.elapsedRealtime() - begin;
            tinker.getPatchReporter().onPatchResult(patchFile, result, cost);
            patchResult.isSuccess = result;
            patchResult.rawPatchFilePath = path;
            patchResult.costTime = cost;
            patchResult.e = e;
            AbstractResultService.runResultService(context, patchResult, getPatchResultExtra(intent));
        } catch (Throwable throwable) {
            e = throwable;
            result = false;
            tinker.getPatchReporter().onPatchException(patchFile, e);
        }
    }
}
