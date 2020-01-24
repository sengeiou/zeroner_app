package com.iwown.device_module.device_setting.language;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.fragment.SettingContract.Presenter;
import com.iwown.device_module.device_setting.fragment.SettingContract.View;
import com.iwown.device_module.device_setting.fragment.SettingPresenter;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends DeviceModuleBaseActivity implements View {
    /* access modifiers changed from: private */
    public List<Integer> languages = new ArrayList();
    ListView mBloodRecycler;
    /* access modifiers changed from: private */
    public int mCurrPosition = -1;
    /* access modifiers changed from: private */
    public ModeAdapter modeAdapter;
    /* access modifiers changed from: private */
    public SettingPresenter presenter;

    class ModeAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ModeAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return LanguageActivity.this.languages.size();
        }

        public Object getItem(int position) {
            return LanguageActivity.this.languages.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = this.mInflater.inflate(R.layout.device_module_shake_mode_item, null);
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.blood_item_text);
                viewHolder.check = (ImageView) convertView.findViewById(R.id.blood_item_img);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvName.setText(LanguageUtil.getLanguageString(LanguageActivity.this, ((Integer) LanguageActivity.this.languages.get(position)).intValue()));
            viewHolder.check.setVisibility(position == LanguageActivity.this.mCurrPosition ? 0 : 4);
            KLog.d(DeviceModuleBaseActivity.TAG, "当前语言选中项：" + LanguageActivity.this.mCurrPosition);
            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView check;
        public TextView tvName;

        ViewHolder() {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_language_select);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        this.mBloodRecycler.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long id) {
                LanguageActivity.this.mCurrPosition = position;
                LanguageActivity.this.modeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        this.presenter = new SettingPresenter(this);
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_language);
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                DeviceSettingLocal device = LanguageActivity.this.presenter.localDeviceSetting();
                device.setLanguage(((Integer) LanguageActivity.this.languages.get(LanguageActivity.this.mCurrPosition)).intValue());
                KLog.i(String.format("---*language:%d", new Object[]{LanguageActivity.this.languages.get(LanguageActivity.this.mCurrPosition)}));
                LanguageActivity.this.presenter.saveLocalDeviceSetting(device);
                PrefUtil.save((Context) ContextUtil.app, UserAction.HEALTHY_LANGUAGE, true);
                DeviceUtils.writeCommandToDevice(7);
                LanguageActivity.this.finish();
            }
        });
        this.mBloodRecycler = (ListView) findViewById(R.id.blood_recycler);
        this.modeAdapter = new ModeAdapter(this);
        this.mBloodRecycler.setAdapter(this.modeAdapter);
        this.modeAdapter.notifyDataSetChanged();
    }

    private void initData() {
        this.languages = LanguageUtil.getLanguageType();
        for (int i = 0; i < this.languages.size(); i++) {
            KLog.i(String.format("---*load language:%d", new Object[]{this.languages.get(i)}));
            if (((Integer) this.languages.get(i)).intValue() == this.presenter.localDeviceSetting().getLanguage()) {
                this.mCurrPosition = i;
                KLog.d(TAG, "language_type from DeviceSettingActivity-->" + this.mCurrPosition);
            }
        }
    }

    public void connectStatue(boolean statue) {
    }

    public void updateConfigUi(String action) {
    }

    public void upDateFirmwareUi(String content, int cancelSwitch, int errorCode) {
    }

    public void connectingView(int type) {
    }

    public void updateFirmwareUpgradeSuccess() {
    }

    public void keyDownDismissDialog() {
    }

    public void setPresenter(Presenter presenter2) {
    }
}
