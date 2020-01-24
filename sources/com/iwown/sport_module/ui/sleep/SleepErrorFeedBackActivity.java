package com.iwown.sport_module.ui.sleep;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepDownCode;
import com.iwown.data_link.sleep_data.SleepDownData1;
import com.iwown.data_link.sleep_data.SleepUpNewSend;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.LoadingDialog;
import com.iwown.lib_common.toast.CustomToast;
import com.iwown.lib_common.views.TitleBar.ImageAction;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.skin_loader.SleepSkinHandler;
import com.iwown.sport_module.ui.sleep.adapter.NumberAdapter;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import java.util.List;

public class SleepErrorFeedBackActivity extends BaseActivity implements OnClickListener {
    /* access modifiers changed from: private */
    public LoadingDialog loadingDialog;
    /* access modifiers changed from: private */
    public ArrayList<String> numbers;
    /* access modifiers changed from: private */
    public RecyclerView rlvNumber;
    int select_index = 0;
    private SleepDataDay sleepDataToday;
    private TextTimeBean textTimeBeansleep_time;
    private TextTimeBean textTimeBeanwake_time;
    private int time_min_10 = ServiceErrorCode.YOU_AND_ME_IS_FRIEND;
    private int time_min_18 = 1080;
    private int time_s_24 = 86400;
    private long todayZeroTime;
    private TextView tvSleepTime;
    private TextView tvTime;
    private TextView tvWakeTime;
    private View vTop;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_sleep_error_feed_back);
        setLeftBackTo();
        setTitleTextID(R.string.sport_module_sleep_error_feedback_activity);
        getTitleBar().addAction(new ImageAction(R.drawable.icon_send) {
            public void performAction(View view) {
                SleepErrorFeedBackActivity.this.saveRecordSleep();
            }
        });
        this.sleepDataToday = (SleepDataDay) getIntent().getParcelableExtra("sleepDataToday");
        DateUtil dateUtil = new DateUtil(SleepShowActivity.currentTime, false);
        dateUtil.addDay(-1);
        this.todayZeroTime = dateUtil.getZeroTime();
        KLog.e(Long.valueOf(this.todayZeroTime));
        initView();
    }

    /* access modifiers changed from: private */
    public void saveRecordSleep() {
        String[] split = this.textTimeBeansleep_time.getTextValue().split(":");
        int time_start_min = (Integer.parseInt(split[0]) * 60) + Integer.parseInt(split[1]);
        if (time_start_min >= this.time_min_18 || time_start_min <= this.time_min_10) {
            String[] split1 = this.textTimeBeanwake_time.getTextValue().split(":");
            int time_send_min = (Integer.parseInt(split1[0]) * 60) + Integer.parseInt(split1[1]);
            if (time_send_min > 1439) {
                CustomToast.makeText(this, getResources().getString(R.string.sport_module_sleep_end_time_more_23_59));
                return;
            }
            long startTime = this.todayZeroTime + ((long) (time_start_min * 60));
            long endTime = this.todayZeroTime + ((long) (time_send_min * 60));
            if (time_start_min == time_send_min) {
                CustomToast.makeText(this, getResources().getString(R.string.sport_module_sleep_start_time_e_end_time));
                return;
            }
            if (time_start_min < this.time_min_10) {
                this.todayZeroTime += 86400;
                startTime = this.todayZeroTime + ((long) (time_start_min * 60));
                endTime = this.todayZeroTime + ((long) (time_send_min * 60));
                if (time_send_min <= time_start_min) {
                    CustomToast.makeText(this, getResources().getString(R.string.sport_module_sleep_start_time_e_end_time));
                    return;
                }
            }
            if (time_start_min > this.time_min_18) {
                if (time_send_min > this.time_min_18 && time_send_min < time_start_min) {
                    CustomToast.makeText(this, getResources().getString(R.string.sport_module_sleep_start_time_e_end_time));
                    return;
                } else if (time_send_min < this.time_min_18) {
                    endTime += 86400;
                }
            }
            KLog.e(DataTimeUtils.getyyyyMMddHHmmss(1000 * startTime) + "  " + DataTimeUtils.getyyyyMMddHHmmss(1000 * endTime));
            uploadSleepManaul(startTime, endTime);
            return;
        }
        CustomToast.makeText(this, getResources().getString(R.string.sport_module_sleep_start_time_error_18));
    }

    private void uploadSleepManaul(long startTime, long endTime) {
        SleepDownData1 sleepDownData1 = new SleepDownData1();
        sleepDownData1.setData_from("Android");
        sleepDownData1.setStart_time(startTime);
        sleepDownData1.setEnd_time(endTime);
        sleepDownData1.setLight_time((float) ((endTime - startTime) / 60));
        sleepDownData1.setUid(UserConfig.getInstance().getNewUID());
        if (this.sleepDataToday == null || this.sleepDataToday.sleepDownData1 != null) {
        }
        List<SleepDownData1> list = new ArrayList<>();
        list.add(sleepDownData1);
        SleepDownCode sleepDownCode = new SleepDownCode();
        sleepDownCode.setContent(list);
        ModuleRouteSleepService.getInstance().saveSleep(sleepDownCode, false);
        SleepDataDay sleepDataDay = new SleepDataDay();
        sleepDataDay.uid = sleepDownData1.getUid();
        sleepDataDay.data_from = sleepDownData1.getData_from();
        sleepDataDay.time_unix = new DateUtil().getUnixTimestamp();
        ModuleRouteSleepService.getInstance().getDaySleepByDataFrom(sleepDataDay);
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this);
        }
        this.loadingDialog.show();
        final SleepUpNewSend sleepUpNewSend = new SleepUpNewSend();
        sleepUpNewSend.setUid(sleepDataDay.uid);
        sleepUpNewSend.setContent(list);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.e("yanxi", "上传成功!");
                SleepErrorFeedBackActivity.this.loadingDialog.dismiss();
                ModuleRouteSleepService.getInstance().updateFinalSleepUploadStatus(sleepUpNewSend, true);
                SleepErrorFeedBackActivity.this.finish();
            }

            public void onFail(Throwable e) {
                KLog.e("yanxi", "上传失败!");
                SleepErrorFeedBackActivity.this.loadingDialog.dismiss();
                try {
                    CustomToast.makeText(SleepErrorFeedBackActivity.this, "" + e.getLocalizedMessage());
                } catch (Exception e1) {
                    ThrowableExtension.printStackTrace(e1);
                }
            }
        }).uploadSleepData(sleepUpNewSend);
    }

    public void updateBG() {
        super.updateBG();
        setTitleBarBG(SleepSkinHandler.getInstance().getSkinTitleBarBG());
        this.vTop.setBackground(SleepSkinHandler.getInstance().getSkinTopBG());
    }

    private void initView() {
        this.tvSleepTime = (TextView) findViewById(R.id.tv_sleep_time);
        this.tvWakeTime = (TextView) findViewById(R.id.tv_wake_time);
        this.vTop = findViewById(R.id.v_top);
        this.tvTime = (TextView) findViewById(R.id.tv_time);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.tvTime);
        updateBG();
        this.tvSleepTime.setOnClickListener(this);
        this.tvWakeTime.setOnClickListener(this);
        this.rlvNumber = (RecyclerView) findViewById(R.id.rlv_number);
        this.numbers = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String item = "" + i;
            if (TextUtils.equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, item)) {
                item = "";
            } else if (TextUtils.equals(Constants.VIA_REPORT_TYPE_SET_AVATAR, item)) {
                item = "-1";
            } else if (TextUtils.equals(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE, item)) {
                item = "0";
            }
            this.numbers.add(item);
        }
        final int screenHeight = DensityUtil.getScreenHeight(this);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        this.textTimeBeansleep_time = new TextTimeBean();
        this.tvTime.setText(this.textTimeBeansleep_time.getTextValue());
        this.textTimeBeanwake_time = new TextTimeBean();
        this.tvTime.setText(this.textTimeBeanwake_time.getTextValue());
        this.rlvNumber.postDelayed(new Runnable() {
            public void run() {
                int numbers_height = (screenHeight - DensityUtil.dip2px(SleepErrorFeedBackActivity.this, 350.0f)) - SleepErrorFeedBackActivity.this.getTitleBar().getHeight();
                int number_height = numbers_height / 4;
                KLog.e(screenHeight + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + number_height + " numbers_height " + numbers_height + "     " + DensityUtil.dip2px(SleepErrorFeedBackActivity.this, 350.0f));
                NumberAdapter numberAdapter = new NumberAdapter(SleepErrorFeedBackActivity.this.numbers, number_height);
                SleepErrorFeedBackActivity.this.rlvNumber.setLayoutManager(gridLayoutManager);
                SleepErrorFeedBackActivity.this.rlvNumber.addItemDecoration(new DividerGridItemDecoration(Color.parseColor("#1E2944")));
                SleepErrorFeedBackActivity.this.rlvNumber.setAdapter(numberAdapter);
                numberAdapter.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        String s = (String) SleepErrorFeedBackActivity.this.numbers.get(position);
                        if (!TextUtils.isEmpty(s)) {
                            SleepErrorFeedBackActivity.this.updateTimeShow(s);
                        }
                    }
                });
            }
        }, 100);
    }

    /* access modifiers changed from: private */
    public void updateTimeShow(String s) {
        if (this.select_index == 0) {
            if (!TextUtils.isEmpty(s)) {
                this.textTimeBeansleep_time.updateValue(s);
            }
            this.tvTime.setText(this.textTimeBeansleep_time.getTextValue());
        } else if (this.select_index == 1) {
            if (!TextUtils.isEmpty(s)) {
                this.textTimeBeanwake_time.updateValue(s);
            }
            this.tvTime.setText(this.textTimeBeanwake_time.getTextValue());
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_sleep_time) {
            if (this.select_index == 1) {
                this.tvSleepTime.setBackgroundResource(R.drawable.sport_module_shape_sleep_error_fedback_left1);
                this.tvSleepTime.setTextColor(getResources().getColor(R.color.sleep_color_error_fedback_time_type_select));
                this.tvWakeTime.setBackgroundResource(R.drawable.sport_module_shape_sleep_error_fedback_right0);
                this.tvWakeTime.setTextColor(getResources().getColor(R.color.white));
                this.select_index = 0;
            }
        } else if (view.getId() == R.id.tv_wake_time && this.select_index == 0) {
            this.tvSleepTime.setBackgroundResource(R.drawable.sport_module_shape_sleep_error_fedback_left0);
            this.tvSleepTime.setTextColor(getResources().getColor(R.color.white));
            this.tvWakeTime.setBackgroundResource(R.drawable.sport_module_shape_sleep_error_fedback_right1);
            this.tvWakeTime.setTextColor(getResources().getColor(R.color.sleep_color_error_fedback_time_type_select));
            this.select_index = 1;
        }
        updateTimeShow(null);
    }
}
