package com.iwown.device_module.device_setting.heart;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.SwitchItme;
import com.iwown.device_module.common.view.SwitchItme.OnSwitchChangedListener;
import com.iwown.device_module.common.view.TimeIntervalView;
import com.iwown.device_module.common.view.TimeIntervalView.OnScrollChangeListener;
import com.iwown.device_module.common.view.flowlayout.FlowLayout;
import com.iwown.device_module.common.view.flowlayout.TagAdapter;
import com.iwown.device_module.common.view.flowlayout.TagFlowLayout;
import com.iwown.device_module.common.view.flowlayout.TagFlowLayout.OnSelectListener;
import com.iwown.device_module.common.view.flowlayout.TagView;
import com.iwown.device_module.device_setting.heart.bean.HeartGuidanceStatue;
import com.socks.library.KLog;
import java.util.Set;

public class HeartGuidanceActivity extends DeviceModuleBaseActivity implements View {
    private SwitchItme heartRateGuide;
    /* access modifiers changed from: private */
    public TimeIntervalView heartRateIntervalPicker;
    /* access modifiers changed from: private */
    public HeartPresenter presenter;
    private ItemView setRemindZone;
    /* access modifiers changed from: private */
    public TagFlowLayout tagLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_heart_guidance);
        initView();
    }

    private void initView() {
        this.presenter = new HeartPresenter(this);
        setTitleText(R.string.device_module_setting_heart_guidance);
        setLeftBackTo();
        this.heartRateGuide = (SwitchItme) findViewById(R.id.heart_rate_guide);
        this.setRemindZone = (ItemView) findViewById(R.id.set_remind_zone);
        this.tagLayout = (TagFlowLayout) findViewById(R.id.tag_layout);
        this.heartRateIntervalPicker = (TimeIntervalView) findViewById(R.id.heart_rate_interval_picker);
        this.tagLayout.setAdapter(new TagAdapter<String>(getArray(R.array.device_module_heart_rate_guide_types)) {
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = (TextView) View.inflate(HeartGuidanceActivity.this, R.layout.device_module_tag_tv_layout, null);
                tv.setText(o);
                return tv;
            }
        });
        initEvent();
    }

    private void initEvent() {
        setHeartRateGuide();
        this.heartRateGuide.setOnSwitchChangedListener(new OnSwitchChangedListener() {
            public void onSwitchChanged(boolean isOn) {
                HeartGuidanceStatue statue = HeartGuidanceActivity.this.presenter.heartGuidanceStatue();
                statue.setHeart_guidance_switch(isOn);
                HeartGuidanceActivity.this.presenter.saveHeartGuidance(statue);
                HeartGuidanceActivity.this.setHeartRateGuide();
                HeartGuidanceActivity.this.presenter.writeCommandToDevice();
            }
        });
        this.setRemindZone.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HeartGuidanceStatue statue = HeartGuidanceActivity.this.presenter.heartGuidanceStatue();
                if (statue.isHeart_guidance_switch()) {
                    int startIndex = HeartGuidanceActivity.this.heartRateIntervalPicker.getStartTimeCurrPosition();
                    int endIndex = HeartGuidanceActivity.this.heartRateIntervalPicker.getEndTimeCurrentPosition();
                    int start = HeartPresenter.HEART_GUIDE_LEFT_START + startIndex;
                    int end = HeartPresenter.HEART_GUIDE_RIGHT_START + endIndex;
                    if (start > end && endIndex != 0) {
                        Toast.makeText(HeartGuidanceActivity.this, HeartGuidanceActivity.this.getString(R.string.device_module_invalid_interval), 0).show();
                    } else if (end - start < 10) {
                        Toast.makeText(HeartGuidanceActivity.this, HeartGuidanceActivity.this.getString(R.string.device_module_invalid_data_heart_guide), 0).show();
                    } else {
                        if (HeartGuidanceActivity.this.heartRateIntervalPicker.getVisibility() == 0) {
                            HeartGuidanceActivity.this.heartRateIntervalPicker.setVisibility(8);
                            HeartGuidanceActivity.this.tagLayout.setVisibility(8);
                            statue.setMinHeart(start);
                            statue.setMaxHeart(end);
                            HeartGuidanceActivity.this.presenter.saveHeartGuidance(statue);
                        } else {
                            HeartGuidanceActivity.this.heartRateIntervalPicker.setVisibility(0);
                            HeartGuidanceActivity.this.tagLayout.setVisibility(0);
                            HeartGuidanceActivity.this.heartRateIntervalPicker.setStartCurrPosition(startIndex);
                            HeartGuidanceActivity.this.heartRateIntervalPicker.setEndCurrPosition(endIndex);
                        }
                        HeartGuidanceActivity.this.setHeartRateGuide();
                        HeartGuidanceActivity.this.presenter.writeCommandToDevice();
                    }
                }
            }
        });
        this.tagLayout.setOnSelectListener(new OnSelectListener() {
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer integer : selectPosSet) {
                    int[] interval = HeartGuidanceActivity.this.presenter.getRemindInterval(26, integer.intValue());
                    HeartGuidanceStatue statue = HeartGuidanceActivity.this.presenter.heartGuidanceStatue();
                    statue.setHeart_guidance_type(integer.intValue());
                    statue.setMinHeart(interval[0]);
                    statue.setMaxHeart(interval[1]);
                    HeartGuidanceActivity.this.presenter.saveHeartGuidance(statue);
                }
                HeartGuidanceActivity.this.setHeartRateGuide();
            }
        });
        this.heartRateIntervalPicker.setOnScrollChangeListener(new OnScrollChangeListener() {
            public void onScroll(int startIndex, int endIndex) {
            }

            public void onEnd(int startIndex, int endIndex) {
                int start = HeartPresenter.HEART_GUIDE_LEFT_START + startIndex;
                int end = HeartPresenter.HEART_GUIDE_RIGHT_START + endIndex;
                if (start <= end && end - start >= 10) {
                    HeartGuidanceStatue statue = HeartGuidanceActivity.this.presenter.heartGuidanceStatue();
                    statue.setHeart_guidance_type(HeartGuidanceActivity.this.presenter.isWhatRemindType(26, start, end));
                    HeartGuidanceActivity.this.presenter.saveHeartGuidance(statue);
                    if (HeartGuidanceActivity.this.presenter.heartGuidanceStatue().getHeart_guidance_type() != -1) {
                        statue.setMinHeart(start);
                        statue.setMaxHeart(end);
                        HeartGuidanceActivity.this.presenter.saveHeartGuidance(statue);
                    }
                    HeartGuidanceActivity.this.setTagLayout();
                    HeartGuidanceActivity.this.setHeartRemindZone();
                }
            }
        });
    }

    public void setHeartRateGuide() {
        setHeartGuideSwitch();
        setHeartRemindZone();
        setTagLayout();
        setHeartGuidePicker();
    }

    public void setHeartGuidePicker() {
        this.heartRateIntervalPicker.setStartCurrPosition(this.presenter.heartGuidanceStatue().getMinHeart() - HeartPresenter.HEART_GUIDE_LEFT_START);
        this.heartRateIntervalPicker.setEndCurrPosition(this.presenter.heartGuidanceStatue().getMaxHeart() - HeartPresenter.HEART_GUIDE_RIGHT_START);
    }

    public void setTagLayout() {
        if (this.presenter.heartGuidanceStatue().getHeart_guidance_type() == -1) {
            this.tagLayout.controlAllTagCheckState(false);
        } else {
            this.tagLayout.post(new Runnable() {
                public void run() {
                    ((TagView) HeartGuidanceActivity.this.tagLayout.getChildAt(HeartGuidanceActivity.this.presenter.heartGuidanceStatue().getHeart_guidance_type())).setChecked(true);
                }
            });
        }
    }

    public void setHeartGuideSwitch() {
        this.heartRateGuide.setOn(this.presenter.heartGuidanceStatue().isHeart_guidance_switch());
    }

    public void setHeartRemindZone() {
        if (this.presenter.heartGuidanceStatue().getMinHeart() == 0 && this.presenter.heartGuidanceStatue().getMaxHeart() == 0) {
            int[] interval = this.presenter.getRemindInterval(26, this.presenter.heartGuidanceStatue().getHeart_guidance_type());
            this.setRemindZone.setMessageText(interval[0] + " - " + interval[1]);
            HeartGuidanceStatue statue = this.presenter.heartGuidanceStatue();
            statue.setMinHeart(interval[0]);
            statue.setMaxHeart(interval[1]);
            this.presenter.saveHeartGuidance(statue);
        } else if (this.presenter.heartGuidanceStatue().getHeart_guidance_type() == -1) {
            this.setRemindZone.setMessageText(this.presenter.heartGuidanceStatue().getMinHeart() + " - " + this.presenter.heartGuidanceStatue().getMaxHeart());
        } else {
            int[] interval2 = this.presenter.getRemindInterval(26, this.presenter.heartGuidanceStatue().getHeart_guidance_type());
            this.setRemindZone.setMessageText(interval2[0] + " - " + interval2[1]);
            HeartGuidanceStatue statue2 = this.presenter.heartGuidanceStatue();
            statue2.setMinHeart(interval2[0]);
            statue2.setMaxHeart(interval2[1]);
            this.presenter.saveHeartGuidance(statue2);
        }
        KLog.e(TAG, "心率提醒区间:" + this.presenter.heartGuidanceStatue().getMinHeart() + "/" + this.presenter.heartGuidanceStatue().getMaxHeart());
        if (this.presenter.heartGuidanceStatue().isHeart_guidance_switch()) {
            this.setRemindZone.setMsgColor(getResources().getColor(R.color.device_module_long_seat_text_1));
            this.setRemindZone.setClickable(true);
            this.setRemindZone.setVisibility(0);
            return;
        }
        this.setRemindZone.setMsgColor(getResources().getColor(R.color.device_module_long_seat_text_2));
        this.setRemindZone.setClickable(false);
        this.setRemindZone.setVisibility(8);
        this.heartRateIntervalPicker.setVisibility(8);
        this.tagLayout.setVisibility(8);
    }
}
