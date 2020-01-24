package com.iwown.device_module.device_add_sport.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_add_sport.Listener.OnItemOnclikListener;
import com.iwown.device_module.device_add_sport.activity.AddSupportSportsContract.AddSportPresenter;
import com.iwown.device_module.device_add_sport.activity.AddSupportSportsContract.addSportView;
import com.iwown.device_module.device_add_sport.bean.AddSport;
import com.iwown.device_module.device_add_sport.callback.ItemDragHelperCallback;
import com.iwown.device_module.device_add_sport.recyledapter.RecycleAdapter;
import com.iwown.device_module.device_add_sport.util.AddSportUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import java.util.ArrayList;
import java.util.List;

public class AddSupportSportsActivity extends DeviceModuleBaseActivity implements addSportView {
    ArrayList<AddSport> addList = new ArrayList<>();
    /* access modifiers changed from: private */
    public int addSportLength = 4;
    private RecyclerView addTopRecycler;
    RecycleAdapter added;
    /* access modifiers changed from: private */
    public AddSupportSportsPresenter presenter;
    private RecyclerView unAddTopRecycler;
    RecycleAdapter unAdded;
    ArrayList<AddSport> unAddedRecleList = new ArrayList<>();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_add_support_sports);
        initView();
        listenView();
        initData();
    }

    private void initData() {
        this.addList.addAll(this.presenter.defaultIcon(this.addSportLength));
        this.unAddedRecleList.addAll(this.presenter.unCheckSupportSports(this.presenter.supportSports()));
    }

    private void initView() {
        String setting;
        TB_DeviceSettings settings = DeviceSettingsBiz.getInstance().queryDevSettings();
        Gson gson = new Gson();
        if (settings == null) {
            setting = "";
        } else {
            setting = settings.getSetting();
        }
        List<SettingBean> dev_settings = (List) gson.fromJson(setting, new TypeToken<List<SettingBean>>() {
        }.getType());
        if (dev_settings != null && dev_settings.size() > 0) {
            for (SettingBean setting2 : dev_settings) {
                switch (setting2.getType()) {
                    case 23:
                        this.addSportLength = setting2.getValueInt();
                        break;
                }
            }
        }
        this.presenter = new AddSupportSportsPresenter(this);
        setLeftBackTo();
        setTitleText(R.string.device_module_setting_add_sport);
        this.addTopRecycler = (RecyclerView) findViewById(R.id.add_top_recycler);
        this.unAddTopRecycler = (RecyclerView) findViewById(R.id.un_add_top_recycler);
        this.added = new RecycleAdapter(this.addList, ContextUtil.app, 1);
        this.addTopRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        new ItemTouchHelper(new ItemDragHelperCallback(this.added)).attachToRecyclerView(this.addTopRecycler);
        this.addTopRecycler.setAdapter(this.added);
        this.unAdded = new RecycleAdapter(this.unAddedRecleList, ContextUtil.app, 2);
        this.unAddTopRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        this.unAddTopRecycler.setAdapter(this.unAdded);
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                AddSupportSportsActivity.this.presenter.sendSupportSportCommand(AddSupportSportsActivity.this.addList);
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Support_Sports_Status, JsonUtils.toJson(AddSupportSportsActivity.this.addList));
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Support_Sports_Status_UnChecked, JsonUtils.toJson(AddSupportSportsActivity.this.unAddedRecleList));
                AddSupportSportsActivity.this.finish();
            }
        });
    }

    private void listenView() {
        this.added.SetOnClickListener(new OnItemOnclikListener() {
            public void OnItemLongClik(View view, int postion) {
            }

            public void OnItemClik(View view, int postion) {
                AddSport add = (AddSport) AddSupportSportsActivity.this.addList.get(postion);
                if (add.getType() != 999) {
                    add.setDrawableId(AddSportUtil.getSporyImgOrName(2, add.getType()));
                    AddSupportSportsActivity.this.unAddedRecleList.add(add);
                    AddSupportSportsActivity.this.addList.remove(postion);
                    AddSport addSport = new AddSport();
                    addSport.setType(999);
                    addSport.setSportName("");
                    addSport.setDrawableId(R.mipmap.circle_3x);
                    AddSupportSportsActivity.this.addList.add(postion, addSport);
                    AddSupportSportsActivity.this.unAdded.setmData(AddSupportSportsActivity.this.unAddedRecleList);
                    AddSupportSportsActivity.this.added.setmData(AddSupportSportsActivity.this.addList);
                    AddSupportSportsActivity.this.added.notifyDataSetChanged();
                    AddSupportSportsActivity.this.unAdded.notifyDataSetChanged();
                }
            }
        });
        this.unAdded.SetOnClickListener(new OnItemOnclikListener() {
            public void OnItemLongClik(View view, int postion) {
            }

            public void OnItemClik(View view, int postion) {
                AddSport unAdd = (AddSport) AddSupportSportsActivity.this.unAddedRecleList.get(postion);
                int i = 0;
                while (true) {
                    if (i >= AddSupportSportsActivity.this.addSportLength) {
                        break;
                    } else if (((AddSport) AddSupportSportsActivity.this.addList.get(i)).getType() == 999) {
                        unAdd.setDrawableId(AddSportUtil.getSporyImgOrName(1, unAdd.getType()));
                        AddSupportSportsActivity.this.addList.set(i, unAdd);
                        AddSupportSportsActivity.this.unAddedRecleList.remove(postion);
                        break;
                    } else {
                        i++;
                    }
                }
                AddSupportSportsActivity.this.unAdded.setmData(AddSupportSportsActivity.this.unAddedRecleList);
                AddSupportSportsActivity.this.added.setmData(AddSupportSportsActivity.this.addList);
                AddSupportSportsActivity.this.added.notifyDataSetChanged();
                AddSupportSportsActivity.this.unAdded.notifyDataSetChanged();
            }
        });
    }

    public void setPresenter(AddSportPresenter presenter2) {
    }
}
