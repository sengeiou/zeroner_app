package com.iwown.device_module.device_vibration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.iwown.task.ZeronerBleWriteDataTask;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.BleWriteDataTask;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.utils.ContextUtil;
import java.util.ArrayList;
import java.util.List;
import org.litepal.util.Const.TableSchema;

public class VibrationModeActivity extends DeviceModuleBaseActivity {
    /* access modifiers changed from: private */
    public List<Item> dataList = new ArrayList();
    /* access modifiers changed from: private */
    public int mCurrPosition = -1;
    /* access modifiers changed from: private */
    public String mCurrText;
    /* access modifiers changed from: private */
    public int mId;
    /* access modifiers changed from: private */
    public int mShakeNum;
    /* access modifiers changed from: private */
    public int mType;
    /* access modifiers changed from: private */
    public ModeAdapter modeAdapter;
    ListView recycler;

    static class Item {
        private String name;
        private int position;

        Item() {
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }

        public int getPosition() {
            return this.position;
        }

        public void setPosition(int position2) {
            this.position = position2;
        }
    }

    class ModeAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ModeAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return VibrationModeActivity.this.dataList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
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
            viewHolder.tvName.setText(((Item) VibrationModeActivity.this.dataList.get(position)).getName());
            viewHolder.check.setVisibility(((Item) VibrationModeActivity.this.dataList.get(position)).getPosition() == VibrationModeActivity.this.mCurrPosition ? 0 : 4);
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
        setContentView(R.layout.device_module_activity_vibration_mode);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        int[] shakeMode;
        this.mId = getIntent().getIntExtra("id", -1);
        this.mType = getIntent().getIntExtra("type", -1);
        this.mCurrPosition = getIntent().getIntExtra("position", -1);
        this.mShakeNum = getIntent().getIntExtra("shake_num", 5);
        String[] shakeModeName = getResources().getStringArray(R.array.device_module_shake_mode_name);
        if (BluetoothOperation.isZg()) {
            shakeMode = getResources().getIntArray(R.array.device_module_shake_mode_zg);
        } else {
            shakeMode = getResources().getIntArray(R.array.device_module_shake_mode);
        }
        for (int index = 0; index < shakeModeName.length; index++) {
            Item item = new Item();
            item.setName(shakeModeName[index]);
            item.setPosition(shakeMode[index]);
            this.dataList.add(item);
        }
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(getString(R.string.device_module_mode_selection));
        this.recycler = (ListView) findViewById(R.id.blood_recycler);
        ((TextView) findViewById(R.id.mode_text_title)).setText(getIntent().getStringExtra(TableSchema.COLUMN_NAME));
        this.modeAdapter = new ModeAdapter(this);
        this.recycler.setAdapter(this.modeAdapter);
        this.recycler.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = (Item) VibrationModeActivity.this.dataList.get(i);
                if (BluetoothOperation.isZg()) {
                    BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().testShake(item.getPosition(), VibrationModeActivity.this.mShakeNum)));
                } else if (BluetoothOperation.isIv()) {
                    byte[] bytes = ZeronerSendBluetoothCmdImpl.getInstance().setShakeMode(2, item.getPosition(), VibrationModeActivity.this.mShakeNum, null);
                    List<byte[]> writes = new ArrayList<>();
                    DataBean bean = new DataBean();
                    writes.add(bytes);
                    bean.setData(writes);
                    bean.setFlag(true);
                    ZeronerBackgroundThreadManager.getInstance().addTask(new ZeronerBleWriteDataTask(VibrationModeActivity.this.getApplicationContext(), bean));
                } else if (BluetoothOperation.isMtk()) {
                    MtkBackgroundThreadManager.getInstance().addTask(new BleWriteDataTask(VibrationModeActivity.this.getApplicationContext(), MtkCmdAssembler.getInstance().setShakeMode(2, item.getPosition(), VibrationModeActivity.this.mShakeNum, null)));
                } else if (BluetoothOperation.isProtoBuf()) {
                    BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMotorVibrate(item.getPosition(), VibrationModeActivity.this.mShakeNum));
                }
                VibrationModeActivity.this.mCurrPosition = item.getPosition();
                VibrationModeActivity.this.mCurrText = item.getName();
                VibrationModeActivity.this.modeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initEvent() {
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                if (VibrationModeActivity.this.mCurrPosition == -1) {
                    Toast.makeText(VibrationModeActivity.this, R.string.device_module_activity_shake_mode_select_error, 0).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("id", VibrationModeActivity.this.mId);
                intent.putExtra("type", VibrationModeActivity.this.mType);
                intent.putExtra("position", VibrationModeActivity.this.mCurrPosition);
                intent.putExtra("position_name", VibrationModeActivity.this.mCurrText);
                VibrationModeActivity.this.setResult(-1, intent);
                VibrationModeActivity.this.finish();
            }
        });
    }
}
