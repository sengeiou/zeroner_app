package com.iwown.data_link.googlefit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GoogleFitUtility {
    private static final String TAG = "googlefit";
    private static GoogleFitUtility instance;
    private static FragmentActivity mContext;
    private static GoogleApiClient mGoogleClient = null;
    private static Object syncObj = new Object();
    /* access modifiers changed from: private */
    public OnGoogleServiceConnectStatusListener mStatusChangeListener;

    private class InsertCariesDataTask extends AsyncTask<Void, Void, Void> {
        private float mCalorie;
        private GoogleApiClient mClient;

        public InsertCariesDataTask(GoogleApiClient client, float calorie) {
            this.mClient = client;
            this.mCalorie = calorie;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            DataUpdateRequest request = GoogleFitUtility.this.insertCariesData(this.mCalorie);
            if (!(request == null || this.mClient == null)) {
                if (!((Status) Fitness.HistoryApi.updateData(this.mClient, request).await(1, TimeUnit.MINUTES)).isSuccess()) {
                    KLog.e("There was a problem inserting the dataset.");
                } else {
                    KLog.e("googlefit Caries success");
                }
            }
            return null;
        }
    }

    private class InsertStepDataTask extends AsyncTask<Void, Void, Void> {
        private GoogleApiClient mClient;
        private int mStep;

        public InsertStepDataTask(GoogleApiClient client, int step) {
            this.mClient = client;
            this.mStep = step;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            DataUpdateRequest dataSet = GoogleFitUtility.this.insertStepData(this.mStep);
            if (!(dataSet == null || this.mClient == null)) {
                if (!((Status) Fitness.HistoryApi.updateData(this.mClient, dataSet).await(1, TimeUnit.MINUTES)).isSuccess()) {
                    KLog.e("There was a problem inserting the dataset.");
                } else {
                    KLog.e("googlefit step success");
                }
            }
            return null;
        }
    }

    private GoogleFitUtility() {
    }

    public static GoogleFitUtility createInstance() {
        if (instance == null) {
            synchronized (syncObj) {
                if (instance == null) {
                    instance = new GoogleFitUtility();
                }
            }
        }
        return instance;
    }

    public void initContext(FragmentActivity context) {
        mContext = context;
    }

    public void setConnectStatusHandler(OnGoogleServiceConnectStatusListener statusChangeListener) {
        this.mStatusChangeListener = statusChangeListener;
    }

    public void connect() {
        if (isGooglePlayAvailable() && mGoogleClient == null) {
            mGoogleClient = new Builder(mContext.getApplicationContext()).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE)).addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE)).addConnectionCallbacks(new ConnectionCallbacks() {
                public void onConnected(Bundle bundle) {
                    Log.i(GoogleFitUtility.TAG, "------------googlefit connected");
                    GoogleFitUtility.this.upload();
                }

                public void onConnectionSuspended(int i) {
                    Log.i(GoogleFitUtility.TAG, String.format("------------googlefit onConnectionSuspended:%d", new Object[]{Integer.valueOf(i)}));
                    if (GoogleFitUtility.this.mStatusChangeListener != null) {
                        GoogleFitUtility.this.mStatusChangeListener.onFailed(i);
                    }
                }
            }).enableAutoManage(mContext, 0, new OnConnectionFailedListener() {
                public void onConnectionFailed(ConnectionResult result) {
                    if (GoogleFitUtility.this.mStatusChangeListener != null) {
                        GoogleFitUtility.this.mStatusChangeListener.onFailed(3);
                    }
                }
            }).build();
            Log.i(TAG, "create google client");
        }
    }

    public void disconnect() {
        if (mGoogleClient != null) {
            mGoogleClient.stopAutoManage(mContext);
            mGoogleClient.disconnect();
            mGoogleClient = null;
            KLog.e("---GoogleFitUtility disconnect");
        }
    }

    /* access modifiers changed from: private */
    public void upload() {
        Calendar calendar = Calendar.getInstance();
        V3_walk walk = ModuleRouteWalkService.getInstance().getWalk(UserConfig.getInstance().getNewUID(), new DateUtil(calendar.get(1), calendar.get(2) + 1, calendar.get(5), 8, 0, 0).getSyyyyMMddDate(), UserConfig.getInstance().getDevice());
        if (walk != null) {
            int steps = walk.getStep();
            float cal = walk.getCalorie();
            createInstance().uploadStepData(steps);
            createInstance().uploadCalorieData(cal);
        }
    }

    public void uploadGoogleFitData() {
        if (!isGooglePlayAvailable()) {
            if (mGoogleClient != null) {
            }
        } else if (mGoogleClient != null && mGoogleClient.isConnected()) {
            upload();
        } else if (mGoogleClient == null) {
            connect();
        } else {
            mGoogleClient.connect();
        }
    }

    public void uploadStepData(int step) {
        new InsertStepDataTask(mGoogleClient, step).execute(new Void[0]);
    }

    public void uploadCalorieData(float calorie) {
        new InsertCariesDataTask(mGoogleClient, calorie).execute(new Void[0]);
    }

    private boolean isGooglePlayAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(mContext);
        if (resultCode == 0) {
            return true;
        }
        if (googleApiAvailability.isUserResolvableError(resultCode)) {
            googleApiAvailability.getErrorDialog(mContext, resultCode, 2404).show();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public DataUpdateRequest insertStepData(int step) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long endTime = cal.getTimeInMillis();
        cal.add(11, -cal.get(11));
        long startTime = cal.getTimeInMillis();
        DataSource dataSource = new DataSource.Builder().setAppPackageName(AppConfigUtil.package_name).setDataType(DataType.TYPE_STEP_COUNT_DELTA).setType(0).build();
        try {
            Log.i(TAG, "googlefit step is : " + String.valueOf(step));
            DataSet dataSet = DataSet.create(dataSource);
            DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
            dataPoint.getValue(Field.FIELD_STEPS).setInt(step);
            dataSet.add(dataPoint);
            return new DataUpdateRequest.Builder().setDataSet(dataSet).setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS).build();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public DataUpdateRequest insertCariesData(float calorie) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long endTime = cal.getTimeInMillis();
        cal.add(11, -cal.get(11));
        long startTime = cal.getTimeInMillis();
        DataSource dataSource = new DataSource.Builder().setAppPackageName(AppConfigUtil.package_name).setDataType(DataType.AGGREGATE_CALORIES_EXPENDED).setType(0).build();
        try {
            Log.i(TAG, "googlefit caries: " + String.valueOf(calorie));
            DataSet dataSet = DataSet.create(dataSource);
            DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
            dataPoint.getValue(Field.FIELD_CALORIES).setFloat(calorie);
            dataSet.add(dataPoint);
            return new DataUpdateRequest.Builder().setDataSet(dataSet).setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS).build();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }
}
