package com.iwown.device_module.device_setting.wifi_scale.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.weight.WifiScaleReq;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.adapter.ComViewHolder;
import com.iwown.device_module.common.adapter.ComViewHolder.OnItemClickListener;
import com.iwown.device_module.common.adapter.CommonRecyAdapter;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.sql.weight.TB_WeightUser;
import com.iwown.device_module.common.view.RecycleViewDivider;
import com.iwown.device_module.common.view.WrapContentLinearLayoutManager;
import com.iwown.device_module.device_setting.wifi_scale.bean.ScaleUserBean;
import com.iwown.device_module.device_setting.wifi_scale.eventbus.EventbusFinish;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

public class WifiUserListActivity extends DeviceModuleBaseActivity implements OnClickListener {
    public static final int Not_Belong_To = 1;
    public static final int User_List = 2;
    public static final int Weight_Data = 3;
    TextView addUserWifiScale;
    UserListCallback callback = new UserListCallback();
    List<ScaleUserBean> dataList = new ArrayList();
    MyAdapter mAdapter;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int type;
    /* access modifiers changed from: private */
    public int weightId;
    RecyclerView wifiScaleUserList;

    class MyAdapter extends CommonRecyAdapter<ScaleUserBean> {
        private Context context;

        public MyAdapter(Context context2, List<ScaleUserBean> dataList, int layoutId) {
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

        public void onBindItem(android.support.v7.widget.RecyclerView.ViewHolder holder, int position, ScaleUserBean device) {
            if (holder instanceof ViewHolder) {
                ((ViewHolder) holder).wifiScaleUserName.setText(device.getUserName());
            }
            super.onBindItem(holder, position, device);
        }
    }

    class UserListCallback implements MyCallback<Integer> {
        UserListCallback() {
        }

        public void onSuccess(Integer integer) {
            WifiUserListActivity.this.loadData();
        }

        public void onFail(Throwable e) {
        }
    }

    static class ViewHolder extends ComViewHolder {
        ImageView wifiScaleUserIcon;
        TextView wifiScaleUserName;

        ViewHolder(View view) {
            super(view);
            this.wifiScaleUserIcon = (ImageView) view.findViewById(R.id.wifi_scale_user_icon);
            this.wifiScaleUserName = (TextView) view.findViewById(R.id.wifi_scale_user_name);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_wifi_user_list);
        this.mContext = this;
        EventBus.getDefault().register(this);
        setLeftBackTo();
        this.type = getIntent().getIntExtra("type", 0);
        this.weightId = getIntent().getIntExtra("weightId", 0);
        if (this.type == 1) {
            setTitleText(R.string.device_module_scale_wifi_setting_4);
        } else if (this.type == 3) {
            setTitleText(R.string.device_module_scale_wifi_user_9);
        } else {
            setTitleText(R.string.device_module_scale_wifi_user_9);
        }
        initView();
    }

    private void initView() {
        this.wifiScaleUserList = (RecyclerView) findViewById(R.id.wifi_scale_user_list);
        this.addUserWifiScale = (TextView) findViewById(R.id.add_user_wifi_scale);
        this.addUserWifiScale.setOnClickListener(this);
        if (this.type == 3) {
            this.addUserWifiScale.setVisibility(8);
        }
        NetFactory.getInstance().getClient(this.callback).getNoAccountList();
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this.mContext);
        layoutManager.setOrientation(1);
        this.wifiScaleUserList.setLayoutManager(layoutManager);
        this.wifiScaleUserList.addItemDecoration(new RecycleViewDivider(this.mContext, 0, 1, getResources().getColor(R.color.device_module_common_line_color)));
        this.mAdapter = new MyAdapter(this.mContext, this.dataList, R.layout.device_module_wifi_scale_user_item);
        this.wifiScaleUserList.setAdapter(this.mAdapter);
        loadData();
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(int position, View view) {
                if (WifiUserListActivity.this.type == 1) {
                    try {
                        WifiScaleReq reqBody = new WifiScaleReq();
                        reqBody.setUid(((ScaleUserBean) WifiUserListActivity.this.dataList.get(position)).getUid());
                        reqBody.setWeightid(WifiUserListActivity.this.weightId);
                        NetFactory.getInstance().getClient(new MyCallback() {
                            public void onSuccess(Object o) {
                                HealthDataEventBus.updateHealthWeightEvent();
                                EventBus.getDefault().post(new EventbusFinish(3));
                                WifiUserListActivity.this.finish();
                            }

                            public void onFail(Throwable e) {
                            }
                        }).archiveData(reqBody, 0);
                        EventbusFinish ev1 = new EventbusFinish();
                        ev1.setAction(8);
                        ev1.setData(Long.valueOf(((ScaleUserBean) WifiUserListActivity.this.dataList.get(position)).getUid()));
                        EventBus.getDefault().post(ev1);
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                } else if (WifiUserListActivity.this.type == 3) {
                    EventbusFinish event = new EventbusFinish();
                    event.setAction(8);
                    event.setData(Long.valueOf(((ScaleUserBean) WifiUserListActivity.this.dataList.get(position)).getUid()));
                    EventBus.getDefault().post(event);
                    WifiUserListActivity.this.finish();
                } else if (position == 0) {
                    ToastUtil.showToast(WifiUserListActivity.this.getString(R.string.device_module_wifi_user_login_edit));
                } else {
                    Intent intent = new Intent(WifiUserListActivity.this.mContext, AddOrEditUserActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra(UserConst.UID, ((ScaleUserBean) WifiUserListActivity.this.dataList.get(position)).getUid());
                    WifiUserListActivity.this.startActivity(intent);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadData() {
        this.dataList.clear();
        List<TB_WeightUser> list = DataSupport.findAll(TB_WeightUser.class, new long[0]);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                this.dataList.add(new ScaleUserBean(((TB_WeightUser) list.get(i)).getName(), ((TB_WeightUser) list.get(i)).getUid()));
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, AddOrEditUserActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventbusFinish event) {
        if (event.getAction() == 2) {
            NetFactory.getInstance().getClient(this.callback).getNoAccountList();
        }
    }
}
