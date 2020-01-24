package com.iwown.ble_module;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.mtk_ble.IDataReceiveHandler;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.scan.IScanCallback;
import com.iwown.ble_module.scan.Scanner;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.CommonAdapter;
import com.iwown.lib_common.ViewHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends Activity {
    private View bt_Zg;
    /* access modifiers changed from: private */
    public CommonAdapter<WristBand> mDevListAdapter;
    ListView mListView;
    List<WristBand> mWristBands = new ArrayList();
    Button scanBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_module_activity_main);
        MTKBle.getInstance(getApplicationContext()).setDataReceiveHandler(new IDataReceiveHandler() {
            public void onDataArrived(int ble_sdk_type, int dataType, String data) {
                Log.e("licl", "onDataArrived");
            }

            public void onScanResult(WristBand dev) {
            }

            public void onBluetoothInit() {
                Log.e("licl", "onBluetoothInit");
                MTKBle.getInstance().writeDataToWristBand(Util.hexStringToBytes("21FF0000"));
            }

            public void connectStatue(boolean isConnect) {
                Log.e("licl", "connectStatue");
            }

            public void onDiscoverService(String serviceUUID) {
                Log.e("licl", "onDiscoverService");
            }

            public void onDiscoverCharacter(String characterUUID) {
            }

            public void onCommonSend(byte[] data) {
                Log.e("licl", "onCommonSend");
            }

            public void onCharacteristicChange(String address) {
            }

            public void onBluetoothError(int errorCode) {
            }

            public void onNoCallback() {
            }

            public void onPreConnect() {
            }
        });
        initView();
        initEvent();
    }

    private void initEvent() {
        this.scanBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Scanner.getInstance(MainActivity.this).stopScan();
                MainActivity.this.mWristBands.clear();
                MainActivity.this.mDevListAdapter.notifyDataSetChanged();
                Scanner.getInstance(MainActivity.this).startScan(new UUID[0]);
            }
        });
        Scanner.getInstance(this).setIScanCallback(new IScanCallback() {
            public void onScanResult(BluetoothDevice device, int rssi, byte[] scanRecord, String nameFromScanRecord) {
                String dev_name = TextUtils.isEmpty(device.getName()) ? nameFromScanRecord : device.getName();
                String str = "licl";
                if (!TextUtils.isEmpty(device.getName())) {
                    nameFromScanRecord = device.getName();
                }
                Log.e(str, nameFromScanRecord);
                WristBand wristBand = new WristBand(dev_name, device.getAddress());
                if (!MainActivity.this.mWristBands.contains(wristBand)) {
                    MainActivity.this.mWristBands.add(wristBand);
                }
                MainActivity.this.mDevListAdapter.notifyDataSetChanged();
            }

            public void onError(int code) {
                Toast.makeText(MainActivity.this, "错误：" + code, 0).show();
            }
        });
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                if (Scanner.getInstance(MainActivity.this).isScanning()) {
                    Scanner.getInstance(MainActivity.this).stopScan();
                }
                MainActivity.this.showCustomDialog((WristBand) MainActivity.this.mWristBands.get(i));
            }
        });
    }

    /* access modifiers changed from: private */
    public void showCustomDialog(final WristBand wristBand) {
        Builder builder = new Builder(this);
        builder.setTitle((CharSequence) "调试类型");
        final String[] items = {"MTK", "Iwown", "Zg"};
        builder.setSingleChoiceItems((CharSequence[]) items, 0, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "You clicked " + items[i], 0).show();
                switch (i) {
                    case 0:
                        MTKBle.getInstance().setWristBand(wristBand);
                        MTKBle.getInstance().connect();
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    private void initView() {
        this.scanBtn = (Button) findViewById(R.id.scan_btn);
        this.bt_Zg = findViewById(R.id.bt_Zg);
        this.mListView = (ListView) findViewById(R.id.scan_lv);
        this.bt_Zg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.mDevListAdapter = new CommonAdapter<WristBand>(this, this.mWristBands, 17367044) {
            public void convert(ViewHolder helper, int position, WristBand item) {
                helper.setText(16908309, item.getDev_name() + "");
            }
        };
        this.mListView.setAdapter(this.mDevListAdapter);
        this.mDevListAdapter.notifyDataSetChanged();
    }
}
