package com.iwown.device_module.device_setting.wifi_scale.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.weight.archive.DataNotBelongToBean;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.ScaleObsoleteReq;
import com.iwown.device_module.common.sql.weight.TB_rawWeightData;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.view.dialog.PreDialog;
import com.iwown.device_module.common.view.iosStyle.SwipeMenu;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuCreator;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuItem;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView.OnMenuItemClickListener;
import com.iwown.device_module.common.view.iosStyle.SwipeMenuListView.OpenOrCloseListener;
import com.iwown.device_module.device_alarm_schedule.utils.Utils;
import com.iwown.device_module.device_setting.wifi_scale.adapter.AbsListAdapter;
import com.iwown.device_module.device_setting.wifi_scale.dialog.S2WifiAlertBelongTo;
import com.iwown.device_module.device_setting.wifi_scale.dialog.S2WifiAlertBelongTo.CallBack;
import com.iwown.device_module.device_setting.wifi_scale.eventbus.EventbusFinish;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

@Route(path = "/device/device_weight_data_not_belong")
public class WeightDataNotBelongToActivity extends DeviceModuleBaseActivity implements CallBack {
    public static final int Type_More = 2;
    public static final int Type_One = 1;
    /* access modifiers changed from: private */
    public S2WifiAlertBelongTo alert;
    List<DataNotBelongToBean> dataList = new ArrayList();
    MyAdapter mAdapter;
    /* access modifiers changed from: private */
    public Context mContext;
    private Handler mHandler = new Handler(Looper.myLooper());
    String mac;
    TextView noWeightData;
    /* access modifiers changed from: private */
    public PreDialog preDialog;
    Runnable preDialogDismiss = new Runnable() {
        public void run() {
            if (WeightDataNotBelongToActivity.this.preDialog != null && WeightDataNotBelongToActivity.this.mContext != null) {
                WeightDataNotBelongToActivity.this.preDialog.dismiss();
            }
        }
    };
    SwipeMenuListView wifiScaleDataNotBelongTo;

    class MyAdapter extends AbsListAdapter<DataNotBelongToBean> {
        private Context context;

        class ViewHolder {
            TextView scaleWeight1;
            ImageView scaleWeightIcon;
            TextView weightDate;

            ViewHolder() {
            }
        }

        public MyAdapter(Context context2, List<DataNotBelongToBean> mList) {
            super(context2, mList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.device_module_layout_data_not_belongto, null);
                viewHolder = new ViewHolder();
                viewHolder.scaleWeight1 = (TextView) convertView.findViewById(R.id.scale_weight_1);
                viewHolder.weightDate = (TextView) convertView.findViewById(R.id.weight_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (((DataNotBelongToBean) getItem(position)).getType() == 3) {
                viewHolder.scaleWeight1.setText(String.format(WeightDataNotBelongToActivity.this.getString(R.string.device_module_scale_wifi_data_belong_to_1), new Object[]{Float.valueOf(((DataNotBelongToBean) this.mList.get(position)).getWeightData())}));
            } else if (((DataNotBelongToBean) getItem(position)).getType() == 2) {
                viewHolder.scaleWeight1.setText(String.format(WeightDataNotBelongToActivity.this.getString(R.string.device_module_scale_wifi_data_belong_to_2), new Object[]{Float.valueOf(((DataNotBelongToBean) this.mList.get(position)).getWeightData())}));
            } else if (((DataNotBelongToBean) getItem(position)).getType() == 1) {
                viewHolder.scaleWeight1.setText(String.format(WeightDataNotBelongToActivity.this.getString(R.string.device_module_scale_wifi_data_belong_to_3), new Object[]{Float.valueOf(((DataNotBelongToBean) this.mList.get(position)).getWeightData())}));
            }
            viewHolder.weightDate.setText(((DataNotBelongToBean) this.mList.get(position)).getDate());
            return convertView;
        }
    }

    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                KLog.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_weight_data_not_belong_to);
        this.mContext = this;
        setTitleText(R.string.device_module_scale_wifi_setting_3);
        setLeftBackTo();
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        this.wifiScaleDataNotBelongTo = (SwipeMenuListView) findViewById(R.id.wifi_scale_data_not_belong_to);
        this.noWeightData = (TextView) findViewById(R.id.no_weight_data);
        setRightText(getString(R.string.device_module_delete), new ActionOnclickListener() {
            public void onclick() {
                EventBus eventBus = EventBus.getDefault();
                new EventbusFinish();
                eventBus.post(Integer.valueOf(11));
                EventBus eventBus2 = EventBus.getDefault();
                new EventbusFinish();
                eventBus2.post(Integer.valueOf(13));
                List<TB_rawWeightData> rawData = DataSupport.where("macaddr=?", WeightDataNotBelongToActivity.this.mac).find(TB_rawWeightData.class);
                if (rawData == null || rawData.size() <= 0) {
                    WeightDataNotBelongToActivity.this.finish();
                    return;
                }
                WeightDataNotBelongToActivity.this.alert = new S2WifiAlertBelongTo(WeightDataNotBelongToActivity.this.mContext);
                WeightDataNotBelongToActivity.this.alert.setCallBack(WeightDataNotBelongToActivity.this);
                WeightDataNotBelongToActivity.this.alert.show();
                WeightDataNotBelongToActivity.this.alert.setOkText(WeightDataNotBelongToActivity.this.getString(R.string.device_module_common_cormfir_no));
                WeightDataNotBelongToActivity.this.alert.setCancelText(WeightDataNotBelongToActivity.this.getString(R.string.device_module_common_cormfir_yes));
                WeightDataNotBelongToActivity.this.alert.msgContentText(WeightDataNotBelongToActivity.this.getString(R.string.device_module_scale_wifi_data_belong_to_4));
            }
        });
        this.mac = S2WifiUtils.wifiScaleMac(ContextUtil.getUID());
        DateUtil dateUtil = new DateUtil(new Date());
        dateUtil.addDay(-30);
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer o) {
                WeightDataNotBelongToActivity.this.showDialog(false);
                if (o.intValue() == 0) {
                    WeightDataNotBelongToActivity.this.dataList.clear();
                    List<TB_rawWeightData> rawData = DataSupport.where("macaddr=?", WeightDataNotBelongToActivity.this.mac).order("weightime desc").find(TB_rawWeightData.class);
                    for (int i = 0; i < rawData.size(); i++) {
                        WeightDataNotBelongToActivity.this.dataList.add(new DataNotBelongToBean(((TB_rawWeightData) rawData.get(i)).getUnit(), ((TB_rawWeightData) rawData.get(i)).getWeight(), ((TB_rawWeightData) rawData.get(i)).getWeightime(), ((TB_rawWeightData) rawData.get(i)).getWeightid()));
                    }
                    if (WeightDataNotBelongToActivity.this.dataList.size() == 0) {
                        WeightDataNotBelongToActivity.this.noWeightData.setVisibility(0);
                    } else {
                        WeightDataNotBelongToActivity.this.noWeightData.setVisibility(8);
                    }
                    WeightDataNotBelongToActivity.this.mAdapter.notifyDataSetChanged();
                } else if (o.intValue() == 10404) {
                    WeightDataNotBelongToActivity.this.noWeightData.setVisibility(0);
                }
            }

            public void onFail(Throwable e) {
                if (WeightDataNotBelongToActivity.this.dataList.size() == 0) {
                    WeightDataNotBelongToActivity.this.noWeightData.setVisibility(0);
                } else {
                    WeightDataNotBelongToActivity.this.noWeightData.setVisibility(8);
                }
            }
        }).getWifiScaleData(dateUtil.getTimestamp(), this.mac, 1);
        showDialog(true);
        new WrapContentLinearLayoutManager(this.mContext).setOrientation(1);
        this.wifiScaleDataNotBelongTo.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(WeightDataNotBelongToActivity.this.mContext.getApplicationContext());
                deleteItem.setBackground((Drawable) new ColorDrawable(Color.rgb(249, 63, 37)));
                deleteItem.setWidth(Utils.dip2px(WeightDataNotBelongToActivity.this.mContext, 90.0f));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        });
        this.wifiScaleDataNotBelongTo.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                ScaleObsoleteReq req = new ScaleObsoleteReq();
                req.setUid(ContextUtil.getLUID());
                req.setWeightid(new long[]{(long) ((DataNotBelongToBean) WeightDataNotBelongToActivity.this.dataList.get(position)).getWeightId()});
                NetFactory.getInstance().getClient(new MyCallback() {
                    public void onSuccess(Object o) {
                    }

                    public void onFail(Throwable e) {
                    }
                }).obsoleteData(req, 1);
            }
        });
        this.wifiScaleDataNotBelongTo.setOnOpenOrCloseListener(new OpenOrCloseListener() {
            public void isOpen(boolean isOpen) {
            }
        });
        this.mAdapter = new MyAdapter(this.mContext, this.dataList);
        this.wifiScaleDataNotBelongTo.setAdapter((ListAdapter) this.mAdapter);
        this.wifiScaleDataNotBelongTo.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(WeightDataNotBelongToActivity.this.mContext, WifiUserListActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("weightId", ((DataNotBelongToBean) WeightDataNotBelongToActivity.this.dataList.get(position)).getWeightId());
                WeightDataNotBelongToActivity.this.startActivity(intent);
            }
        });
        if (this.mac != null) {
            List<TB_rawWeightData> rawData = DataSupport.where("macaddr=?", this.mac).find(TB_rawWeightData.class);
            if (rawData == null || rawData.size() == 0) {
                this.noWeightData.setVisibility(0);
            }
        }
    }

    public void ok() {
        EventBus eventBus = EventBus.getDefault();
        new EventbusFinish();
        eventBus.post(Integer.valueOf(11));
        EventBus eventBus2 = EventBus.getDefault();
        new EventbusFinish();
        eventBus2.post(Integer.valueOf(13));
        ScaleObsoleteReq req = new ScaleObsoleteReq();
        req.setUid(ContextUtil.getLUID());
        List<TB_rawWeightData> rawData = DataSupport.where("macaddr=?", this.mac).find(TB_rawWeightData.class);
        if (rawData.size() > 0) {
            long[] weightIds = new long[rawData.size()];
            for (int i = 0; i < rawData.size(); i++) {
                weightIds[i] = (long) ((TB_rawWeightData) rawData.get(i)).getWeightid();
            }
            req.setWeightid(weightIds);
            NetFactory.getInstance().getClient(null).obsoleteData(req, 2);
        }
        this.mac = S2WifiUtils.wifiScaleMac(ContextUtil.getUID());
        DateUtil dateUtil = new DateUtil(new Date());
        dateUtil.addDay(-30);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
            }

            public void onFail(Throwable e) {
            }
        }).getWifiScaleData(dateUtil.getTimestamp(), this.mac, 2);
    }

    public void onError() {
    }

    /* access modifiers changed from: private */
    public void showDialog(boolean show) {
        if (this.preDialog == null) {
            if (show) {
                this.preDialog = new PreDialog(this.mContext);
                this.preDialog.show();
                this.mHandler.postDelayed(this.preDialogDismiss, 10000);
            }
        } else if (!show) {
            this.preDialog.dismiss();
        } else {
            this.preDialog.show();
            this.mHandler.postDelayed(this.preDialogDismiss, 10000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventbusFinish event) {
        if (event.getAction() == 3 || event.getAction() == 8) {
            this.dataList.clear();
            List<TB_rawWeightData> rawData = DataSupport.where("macaddr=?", this.mac).find(TB_rawWeightData.class);
            for (int i = 0; i < rawData.size(); i++) {
                this.dataList.add(new DataNotBelongToBean(((TB_rawWeightData) rawData.get(i)).getUnit(), ((TB_rawWeightData) rawData.get(i)).getWeight(), ((TB_rawWeightData) rawData.get(i)).getWeightime(), ((TB_rawWeightData) rawData.get(i)).getWeightid()));
            }
            if (this.dataList.size() == 0) {
                this.noWeightData.setVisibility(0);
            } else {
                this.noWeightData.setVisibility(8);
            }
            this.mAdapter.notifyDataSetChanged();
        } else if (event.getAction() == 4) {
            this.mac = S2WifiUtils.wifiScaleMac(ContextUtil.getUID());
            DateUtil dateUtil = new DateUtil(new Date());
            dateUtil.addDay(-30);
            NetFactory.getInstance().getClient(null).getWifiScaleData(dateUtil.getTimestamp(), this.mac, 2);
        } else if (event.getAction() == 5) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
