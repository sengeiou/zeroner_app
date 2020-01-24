package com.iwown.device_module.device_blood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.device_setting.fragment.IvSettingFragment;
import java.util.ArrayList;
import java.util.List;

public class BloodOldsetActivity extends DeviceModuleBaseActivity {
    private Button ChangeOldblood;
    private ListView oldbp_list;

    class OldbloodAdapter extends ArrayAdapter<Oldbloodbean> {
        public List<Oldbloodbean> items;

        class ViewHolder {
            TextView textbloodName;
            TextView textblooddbp;
            TextView textbloodsbp;

            ViewHolder() {
            }
        }

        public OldbloodAdapter(Context context, int resource, List<Oldbloodbean> items2) {
            super(context, resource, items2);
            this.items = items2;
        }

        public int getCount() {
            if (this.items == null) {
                return 0;
            }
            return this.items.size();
        }

        public long getItemId(int position) {
            return super.getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder mHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.device_module_activity_blood_oldlist, null);
                mHolder = new ViewHolder();
                mHolder.textbloodName = (TextView) view.findViewById(R.id.tv_blood_name);
                mHolder.textbloodsbp = (TextView) view.findViewById(R.id.et_blood_oldheigh);
                mHolder.textblooddbp = (TextView) view.findViewById(R.id.et_blood_oldlow);
                view.setTag(mHolder);
            } else {
                view = convertView;
                mHolder = (ViewHolder) view.getTag();
            }
            Oldbloodbean indexdata = (Oldbloodbean) getItem(position);
            if (indexdata != null) {
                mHolder.textbloodName.setText(indexdata.getPressureName());
                mHolder.textbloodsbp.setText(indexdata.getOld_sdp() + "");
                mHolder.textblooddbp.setText(indexdata.getOld_hdp() + "");
            }
            return view;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_blood_oldset);
        initView();
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_blood_pressure);
        this.oldbp_list = (ListView) findViewById(R.id.device_moudle_bloodlist);
        this.ChangeOldblood = (Button) findViewById(R.id.btn_changeblood);
        List<Oldbloodbean> oldbplists = new ArrayList<>();
        Oldbloodbean[] oldbloodbean = new Oldbloodbean[3];
        for (int i = 0; i < 3; i++) {
            oldbloodbean[i] = new Oldbloodbean();
            if (i == 0) {
                oldbloodbean[i].setPressureName(R.string.device_module_setting_blood_DetailOne);
                oldbloodbean[i].setOld_sdp(IvSettingFragment.historyBp[0]);
                oldbloodbean[i].setOld_hdp(IvSettingFragment.historyBp[1]);
            } else if (i == 1) {
                oldbloodbean[i].setPressureName(R.string.device_module_setting_blood_DetailTwo);
                oldbloodbean[i].setOld_sdp(IvSettingFragment.historyBp[2]);
                oldbloodbean[i].setOld_hdp(IvSettingFragment.historyBp[3]);
            } else if (i == 2) {
                oldbloodbean[i].setPressureName(R.string.device_module_setting_blood_DetailFinal);
                oldbloodbean[i].setOld_sdp(IvSettingFragment.historyBp[4]);
                oldbloodbean[i].setOld_hdp(IvSettingFragment.historyBp[5]);
            }
            oldbplists.add(oldbloodbean[i]);
        }
        this.oldbp_list.setAdapter(new OldbloodAdapter(this, R.layout.device_module_activity_blood_oldlist, oldbplists));
        this.ChangeOldblood.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                BloodOldsetActivity.this.finish();
                BloodOldsetActivity.this.startActivity(new Intent(BloodOldsetActivity.this, BloodSettingActivity.class));
            }
        });
    }
}
