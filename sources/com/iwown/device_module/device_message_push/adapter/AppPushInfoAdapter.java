package com.iwown.device_module.device_message_push.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.device_module.R;
import com.iwown.device_module.device_message_push.bean.AppInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppPushInfoAdapter extends BaseAdapter {
    public static int FOR_OTHER_APP = 1;
    public static int FOR_SMS_APP = 0;
    private boolean checkboxFlag;
    private Context context;
    private HashMap<Integer, Boolean> isSelected = new HashMap<>();
    /* access modifiers changed from: private */
    public CheckChangeListener listener;
    /* access modifiers changed from: private */
    public List<AppInfo> mList = new ArrayList();
    /* access modifiers changed from: private */
    public int type;

    public interface CheckChangeListener {
        void oncheckChange(int i, boolean z);
    }

    class ViewHolder {
        /* access modifiers changed from: private */
        public ImageView apkIcon;
        /* access modifiers changed from: private */
        public TextView apkName;
        /* access modifiers changed from: private */
        public ImageView appCheck;

        ViewHolder() {
        }
    }

    public boolean isCheckboxFlag() {
        return this.checkboxFlag;
    }

    public void setCheckboxFlag(boolean checkboxFlag2) {
        this.checkboxFlag = checkboxFlag2;
    }

    public CheckChangeListener getListener() {
        return this.listener;
    }

    public void setListener(CheckChangeListener listener2) {
        this.listener = listener2;
    }

    public AppPushInfoAdapter(Context context2, List<AppInfo> mList2) {
        this.mList = mList2;
        this.context = context2;
    }

    public AppPushInfoAdapter(Context context2, List<AppInfo> mList2, HashMap<Integer, Boolean> isSelected2, int type2) {
        this.mList = mList2;
        this.context = context2;
        this.isSelected = isSelected2;
        this.type = type2;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int i) {
        return this.mList.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.device_module_app_row, null);
            viewHolder = new ViewHolder();
            viewHolder.apkName = (TextView) convertView.findViewById(R.id.apk_name);
            viewHolder.apkIcon = (ImageView) convertView.findViewById(R.id.apk_logo);
            viewHolder.appCheck = (ImageView) convertView.findViewById(R.id.choose_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (this.checkboxFlag) {
            viewHolder.appCheck.setVisibility(8);
        }
        viewHolder.apkName.setText(((AppInfo) getItem(position)).getAppName());
        viewHolder.apkIcon.setImageDrawable(((AppInfo) getItem(position)).getResolveInfo().activityInfo.applicationInfo.loadIcon(this.context.getPackageManager()));
        if (((AppInfo) this.mList.get(position)).isCheck()) {
            viewHolder.appCheck.setImageResource(R.mipmap.select_yes_3x);
        } else {
            viewHolder.appCheck.setImageResource(R.mipmap.select_no_3x);
        }
        viewHolder.appCheck.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                boolean z = true;
                if (AppPushInfoAdapter.this.listener == null) {
                    return;
                }
                if (AppPushInfoAdapter.this.type == AppPushInfoAdapter.FOR_OTHER_APP) {
                    AppPushInfoAdapter.this.listener.oncheckChange(position, !((AppInfo) AppPushInfoAdapter.this.mList.get(position)).isCheck());
                } else if (((AppInfo) AppPushInfoAdapter.this.mList.get(position)).isCheck()) {
                    AppPushInfoAdapter.this.listener.oncheckChange(position, ((AppInfo) AppPushInfoAdapter.this.mList.get(position)).isCheck());
                } else {
                    CheckChangeListener access$300 = AppPushInfoAdapter.this.listener;
                    int i = position;
                    if (((AppInfo) AppPushInfoAdapter.this.mList.get(position)).isCheck()) {
                        z = false;
                    }
                    access$300.oncheckChange(i, z);
                }
            }
        });
        return convertView;
    }
}
