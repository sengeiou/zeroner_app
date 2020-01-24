package com.iwown.device_module.device_operation.search;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.device_module.R;
import com.iwown.device_module.common.adapter.ComViewHolder;
import com.iwown.device_module.common.adapter.CommonRecyAdapter;
import java.util.List;

public class DeviceSearchAdapter extends CommonRecyAdapter<WristBand> {
    private Context context;

    class ViewHolder extends ComViewHolder {
        TextView devTv;
        TextView subTv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.devTv = (TextView) itemView.findViewById(R.id.device_tv);
            this.subTv = (TextView) itemView.findViewById(R.id.sub_title);
            this.subTv.setText(R.string.device_module_connect);
        }
    }

    public DeviceSearchAdapter(Context context2, List<WristBand> dataList, int layoutId) {
        super(context2, dataList, layoutId);
        this.context = context2;
    }

    public int getItemCount() {
        return super.getItemCount();
    }

    /* access modifiers changed from: protected */
    public ComViewHolder setComViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    public void onBindItem(android.support.v7.widget.RecyclerView.ViewHolder holder, int position, WristBand device) {
        super.onBindItem(holder, position, device);
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).devTv.setText(TextUtils.isEmpty(device.getAlias()) ? device.getAddress() : device.getAlias());
        }
    }
}
