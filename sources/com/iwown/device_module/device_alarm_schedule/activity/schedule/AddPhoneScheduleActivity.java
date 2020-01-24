package com.iwown.device_module.device_alarm_schedule.activity.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Builder;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.sql.PhoneSchedule;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.view.dialog.PreDialog;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddScheduleContract.AddScheduleView;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.OnItemMultiSelectListener;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.Operation;
import com.iwown.device_module.device_alarm_schedule.adapter.EasyAdapter.SelectMode;
import com.iwown.device_module.device_alarm_schedule.bean.ScheduleInfo;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.toast.CustomToast;
import com.socks.library.KLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

public class AddPhoneScheduleActivity extends DeviceModuleBaseActivity implements PermissionCallbacks, OnClickListener, AddScheduleView {
    private static final String PREF_ACCOUNT_NAME = "accountName";
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    /* access modifiers changed from: private */
    public ModeAdapter adapter;
    /* access modifiers changed from: private */
    public Context context;
    GoogleAccountCredential mCredential;
    private Handler mHandler = new Handler(Looper.myLooper());
    /* access modifiers changed from: private */
    public List<PhoneSchedule> phoneScheduleData = new ArrayList();
    private RecyclerView phoneScheduleList;
    /* access modifiers changed from: private */
    public PreDialog preDialog;
    Runnable preDialogDismiss = new Runnable() {
        public void run() {
            if (AddPhoneScheduleActivity.this.preDialog != null) {
                AddPhoneScheduleActivity.this.preDialog.dismiss();
            }
        }
    };
    AddSchedulePresenter presenter;
    private TextView syncGoogleCal;
    private View view;

    public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private Exception mLastError = null;
        private Calendar mService = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            this.mService = new Builder(AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName("Google Calendar API Android Quickstart").build();
        }

        /* access modifiers changed from: protected */
        public List<String> doInBackground(Void... params) {
            try {
                KLog.i("================doInBackground========================");
                return getDataFromApi();
            } catch (Exception e) {
                this.mLastError = e;
                cancel(true);
                KLog.i("=============Exception================" + e.toString());
                return null;
            }
        }

        private List<String> getDataFromApi() throws IOException {
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> list = new ArrayList<>();
            List<Event> items = ((Events) this.mService.events().list("primary").setMaxResults(Integer.valueOf(10)).setTimeMin(now).setOrderBy("startTime").setSingleEvents(Boolean.valueOf(true)).execute()).getItems();
            KLog.e("========" + JsonUtils.toJson(items));
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                KLog.e("google" + event.getSummary());
                list.add(event.getSummary());
                PhoneSchedule phoneSchedule = (PhoneSchedule) DataSupport.where("uid =? and time=? ", ContextUtil.getUID(), String.valueOf(start.getValue())).findFirst(PhoneSchedule.class);
                if (phoneSchedule == null) {
                    PhoneSchedule phoneSchedule2 = new PhoneSchedule();
                    phoneSchedule2.setUid(ContextUtil.getLUID());
                    phoneSchedule2.setTime(start.getValue());
                    if (!TextUtils.isEmpty(event.getSummary())) {
                        phoneSchedule2.setMessage(event.getSummary());
                        phoneSchedule2.setChecked(2);
                        phoneSchedule2.save();
                    }
                } else {
                    phoneSchedule.saveOrUpdate("uid =? and time=? and message=?  ", ContextUtil.getUID(), String.valueOf(start.getValue()), String.valueOf(event.getSummary()));
                }
            }
            KLog.i(JsonUtils.toJson(list));
            return list;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            AddPhoneScheduleActivity.this.showDialog(true);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(List<String> output) {
            AddPhoneScheduleActivity.this.showDialog(false);
            if (output == null || output.size() == 0) {
                KLog.e("No results returned.");
            } else {
                KLog.e(TextUtils.join("\n", output));
            }
        }

        /* access modifiers changed from: protected */
        public void onCancelled() {
            AddPhoneScheduleActivity.this.showDialog(false);
            if (this.mLastError == null) {
                KLog.e("Request cancelled.");
            } else if (this.mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                AddPhoneScheduleActivity.this.showGooglePlayServicesAvailabilityErrorDialog(((GooglePlayServicesAvailabilityIOException) this.mLastError).getConnectionStatusCode());
                KLog.i("=============GooglePlayServicesAvailabilityIOException============");
            } else if (this.mLastError instanceof UserRecoverableAuthIOException) {
                AddPhoneScheduleActivity.this.startActivityForResult(((UserRecoverableAuthIOException) this.mLastError).getIntent(), 1001);
            } else {
                KLog.e("The following error occurred:\n" + this.mLastError.getMessage());
            }
        }
    }

    class ModeAdapter extends EasyAdapter<MyViewHolder> {
        private LayoutInflater mInflater;

        public ModeAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(this.mInflater.inflate(R.layout.device_module_phone_schedule_item, null));
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getItemCount() {
            return AddPhoneScheduleActivity.this.phoneScheduleData.size();
        }

        public void whenBindViewHolder(MyViewHolder holder, int position) {
            holder.tvName.setText(((PhoneSchedule) AddPhoneScheduleActivity.this.phoneScheduleData.get(position)).getMessage());
            holder.tbTime.setText(new DateUtil(((PhoneSchedule) AddPhoneScheduleActivity.this.phoneScheduleData.get(position)).getTime(), false).getY_M_D_H_M_S());
            if (((PhoneSchedule) AddPhoneScheduleActivity.this.phoneScheduleData.get(position)).getChecked() == 1) {
                holder.check.setBackgroundResource(R.mipmap.select_yes_3x);
            } else if (((PhoneSchedule) AddPhoneScheduleActivity.this.phoneScheduleData.get(position)).getChecked() == 2) {
                holder.check.setBackgroundResource(R.mipmap.select_no_3x);
            }
        }
    }

    class MyViewHolder extends ViewHolder {
        public ImageView check;
        public TextView tbTime;
        public TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.blood_item_text);
            this.check = (ImageView) itemView.findViewById(R.id.blood_item_img);
            this.tbTime = (TextView) itemView.findViewById(R.id.blood_item_time);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.view = LayoutInflater.from(this.context).inflate(R.layout.device_module_activity_add_phone_schedule, null);
        setContentView(this.view);
        initView();
    }

    private void initView() {
        setLeftBackTo();
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                int num;
                AddScheduleUtil.list.clear();
                for (PhoneSchedule phoneSchedule : AddPhoneScheduleActivity.this.phoneScheduleData) {
                    ScheduleInfo scheduleInfo = new ScheduleInfo();
                    if (phoneSchedule.getChecked() == 1) {
                        DateUtil dateUtil = new DateUtil(phoneSchedule.getTime(), false);
                        int y = dateUtil.getYear();
                        int m = dateUtil.getMonth();
                        int d = dateUtil.getDay();
                        int h = dateUtil.getHour();
                        int min = dateUtil.getMinute();
                        int index = 0;
                        long zTime = dateUtil.getZeroTime();
                        List<PhoneSchedule> phoneList = DataSupport.where("uid=? and checked =1 and time >? ", ContextUtil.getUID(), String.valueOf(System.currentTimeMillis())).find(PhoneSchedule.class);
                        if (phoneList != null && phoneList.size() > 0) {
                            if (BluetoothOperation.isIv() || BluetoothOperation.isMtk()) {
                                num = DataSupport.where("UID=? AND year=? and month =? and day=? ", ContextUtil.getUID(), y + "", m + "", d + "").count(TB_schedulestatue.class);
                                for (int i = 0; i < phoneList.size(); i++) {
                                    if (new DateUtil(((PhoneSchedule) phoneList.get(i)).getTime(), false).getZeroTime() == zTime) {
                                        index++;
                                    }
                                }
                            } else if (BluetoothOperation.isZg()) {
                                num = DataSupport.where("UID=?", ContextUtil.getUID()).count(TB_schedulestatue.class);
                                if (phoneList.size() + num > 4) {
                                    CustomToast.makeText((Activity) AddPhoneScheduleActivity.this.context, AddPhoneScheduleActivity.this.getString(R.string.device_module_schedule_msg_effect_max1) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + AddPhoneScheduleActivity.this.presenter.getPerdaySetableNum() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + AddPhoneScheduleActivity.this.getString(R.string.device_module_schedule) + "!");
                                    return;
                                }
                            } else {
                                num = DataSupport.where("UID=? AND year=? and month =? and day=? ", ContextUtil.getUID(), y + "", m + "", d + "").count(TB_schedulestatue.class);
                            }
                            if (index + num > 4) {
                                CustomToast.makeText((Activity) AddPhoneScheduleActivity.this.context, AddPhoneScheduleActivity.this.getString(R.string.device_module_schedule_msg_perday_max1) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + AddPhoneScheduleActivity.this.presenter.getPerdaySetableNum() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + AddPhoneScheduleActivity.this.getString(R.string.device_module_schedule) + "!");
                                return;
                            }
                        }
                        if (AddPhoneScheduleActivity.this.checkBeforeAddOrEdit(y, m, d) && AddPhoneScheduleActivity.this.checkSameSchdule(y, m, d, h, min)) {
                            scheduleInfo.setId(0);
                            scheduleInfo.setYear(y);
                            scheduleInfo.setMonth(m);
                            scheduleInfo.setDay(d);
                            scheduleInfo.setHour(h);
                            scheduleInfo.setMin(min);
                            scheduleInfo.setTitle(phoneSchedule.getMessage());
                            AddScheduleUtil.list.add(scheduleInfo);
                        } else {
                            return;
                        }
                    }
                }
                AddPhoneScheduleActivity.this.setResult(AddSchedulePresenter.PHONE_SCHEDULE);
                AddPhoneScheduleActivity.this.finish();
            }
        });
        setTitleText(R.string.device_module_phone_schedule_error_4);
        this.presenter = new AddSchedulePresenter(this);
        this.phoneScheduleList = (RecyclerView) findViewById(R.id.phone_schedule_list);
        this.syncGoogleCal = (TextView) findViewById(R.id.sync_google_cal);
        this.syncGoogleCal.setOnClickListener(this);
        this.mCredential = this.presenter.googleServiceInit();
        this.adapter = new ModeAdapter(this.context);
        this.adapter.setSelectMode(SelectMode.MULTI_SELECT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(1);
        this.phoneScheduleList.setLayoutManager(linearLayoutManager);
        this.adapter.setOnItemMultiSelectListener(new OnItemMultiSelectListener() {
            public void onSelected(Operation operation, int itemPosition, boolean isSelected) {
                PhoneSchedule schedule = (PhoneSchedule) AddPhoneScheduleActivity.this.phoneScheduleData.get(itemPosition);
                if (schedule.getChecked() == 1) {
                    schedule.setChecked(2);
                } else if (schedule.getChecked() == 2) {
                    schedule.setChecked(1);
                }
                AddPhoneScheduleActivity.this.phoneScheduleData.set(itemPosition, schedule);
                schedule.saveOrUpdate("uid =? and time=? and message=? ", ContextUtil.getUID(), String.valueOf(schedule.getTime()), String.valueOf(schedule.getMessage()));
                AddPhoneScheduleActivity.this.adapter.notifyDataSetChanged();
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkSameSchdule(int year, int month, int day, int hour, int min) {
        if (AddScheduleUtil.isChangSchedule(String.valueOf(ContextUtil.getUID()), year, month, day, hour, min)) {
            return true;
        }
        Toast.makeText(this, R.string.device_module_schedule_msg_schedule_has_same, 0).show();
        return false;
    }

    public boolean checkBeforeAddOrEdit(int selectedYear, int selectedMonth, int selectedDay) {
        if (!BluetoothOperation.isConnected()) {
            Toast.makeText(this, R.string.device_module_schedule_msg_no_connect, 0).show();
            return false;
        } else if (!isSupportSchedule(true)) {
            return false;
        } else {
            java.util.Calendar curCal = java.util.Calendar.getInstance();
            curCal.setTimeInMillis(System.currentTimeMillis());
            if (AddScheduleUtil.isBeforeToday(selectedYear, selectedMonth, selectedDay, curCal)) {
                KLog.e(TAG, Integer.valueOf(selectedYear + selectedMonth + selectedDay));
                Toast.makeText(this, R.string.device_module_schedule_msg_schedule_before_today, 0).show();
                return false;
            }
            int isadd = this.presenter.isAddSchedule(selectedYear, selectedMonth, selectedDay);
            KLog.e("====================" + isadd);
            if (1 == isadd || 3 == isadd) {
                Toast.makeText(this, getString(R.string.device_module_schedule_msg_effect_max1) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.presenter.getMaxSetableNum() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.device_module_schedule_msg_effect_max2), 0).show();
                return false;
            } else if (2 == isadd) {
                Toast.makeText(this, getString(R.string.device_module_schedule_msg_perday_max1) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.presenter.getPerdaySetableNum() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.device_module_schedule) + "!", 0).show();
                return false;
            } else if (isadd == 0) {
                return true;
            } else {
                return true;
            }
        }
    }

    private boolean isSupportSchedule(boolean isShowMsg) {
        if (this.presenter.getIsSupportSchedule()) {
            return true;
        }
        if (!isShowMsg) {
            return false;
        }
        Toast.makeText(this, R.string.device_module_schedule_msg_no_support, 0).show();
        return false;
    }

    @AfterPermissionGranted(1003)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(this, "android.permission.GET_ACCOUNTS")) {
            String accountName = getPreferences(0).getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                this.mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
                return;
            }
            startActivityForResult(this.mCredential.newChooseAccountIntent(), 1000);
            return;
        }
        EasyPermissions.requestPermissions((Activity) this, "This app needs to access your Google account (via Contacts).", 1003, "android.permission.GET_ACCOUNTS");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == -1 && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra("authAccount");
                    if (accountName != null) {
                        Editor editor = getPreferences(0).edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        this.mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                        return;
                    }
                    return;
                }
                return;
            case 1001:
                if (resultCode == -1) {
                    getResultsFromApi();
                    return;
                }
                return;
            case 1002:
                if (resultCode != -1) {
                    ToastUtil.showToast(getString(R.string.device_module_phone_schedule_error_1));
                    return;
                } else {
                    getResultsFromApi();
                    return;
                }
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    public void onPermissionsDenied(int requestCode, List<String> list) {
        KLog.e("onPermissionsDenied" + JsonUtils.toJson(list));
    }

    public void onClick(View v) {
        if (v.getId() == R.id.sync_google_cal) {
            DataSupport.deleteAll(PhoneSchedule.class, "uid=?", ContextUtil.getUID());
            getResultsFromApi();
        }
    }

    public void readScheduleError(int errorCode, String action) {
        if (AddSchedulePresenter.Google_Schedule_Code == errorCode) {
            chooseAccount();
        }
    }

    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (this.mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (isDeviceOnline()) {
            new MakeRequestTask(this.mCredential).execute(new Void[0]);
        }
    }

    private boolean isDeviceOnline() {
        NetworkInfo networkInfo = ((ConnectivityManager) ContextUtil.app.getSystemService("connectivity")).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /* access modifiers changed from: 0000 */
    public void showGooglePlayServicesAvailabilityErrorDialog(int connectionStatusCode) {
        GoogleApiAvailability.getInstance().getErrorDialog(this, connectionStatusCode, 1002).show();
    }

    private boolean isGooglePlayServicesAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.context) == 0;
    }

    public void showDialog(boolean show) {
        if (this.preDialog == null) {
            if (show) {
                this.preDialog = new PreDialog(this.context);
                this.preDialog.show();
                this.preDialog.setMessage(getString(R.string.device_module_phone_schedule_error_2));
                this.mHandler.postDelayed(this.preDialogDismiss, 10000);
            } else {
                return;
            }
        } else if (!show) {
            this.preDialog.dismiss();
        } else {
            this.preDialog.show();
            this.preDialog.setMessage(getString(R.string.device_module_phone_schedule_error_2));
            this.mHandler.postDelayed(this.preDialogDismiss, 10000);
        }
        if (!show) {
            this.phoneScheduleData.clear();
            this.phoneScheduleData.addAll(DataSupport.where("uid=? and time> ?", ContextUtil.getUID(), String.valueOf(new DateUtil().getTimestamp())).find(PhoneSchedule.class));
            List<TB_schedulestatue> tb_schedulestatues = DataSupport.where("UID=? ", ContextUtil.getUID()).find(TB_schedulestatue.class);
            if (tb_schedulestatues != null && tb_schedulestatues.size() > 0) {
                for (int i = 0; i < tb_schedulestatues.size(); i++) {
                    TB_schedulestatue tb = (TB_schedulestatue) tb_schedulestatues.get(i);
                    long time = new DateUtil(tb.getYear(), tb.getMonth(), tb.getDay(), tb.getHour(), tb.getMinute()).getUnixTimestamp() * 1000;
                    for (int j = 0; j < this.phoneScheduleData.size(); j++) {
                        if (((PhoneSchedule) this.phoneScheduleData.get(j)).getTime() == time) {
                            this.phoneScheduleData.remove(j);
                        }
                    }
                }
            }
            if (this.phoneScheduleData != null && this.phoneScheduleData.size() > 0) {
                this.phoneScheduleList.setAdapter(this.adapter);
                this.adapter.notifyDataSetChanged();
            }
        }
    }
}
