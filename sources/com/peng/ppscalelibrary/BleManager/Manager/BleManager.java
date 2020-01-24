package com.peng.ppscalelibrary.BleManager.Manager;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchRequest.Builder;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.iwown.sport_module.ui.repository.DataSource;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJConnectInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJExitInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJParttenInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJPassivityDisconnectInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleConnectFliterInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleDataProtocoInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleDataStateInterface;
import com.peng.ppscalelibrary.BleManager.Model.BleConnectFliterModel;
import com.peng.ppscalelibrary.BleManager.Model.BleDeviceModel;
import com.peng.ppscalelibrary.BleManager.Model.BleUserModel;
import java.util.List;
import java.util.UUID;

public class BleManager {
    private static BleManager bleManager;
    /* access modifiers changed from: private */
    public static BluetoothClient mBleClient;
    /* access modifiers changed from: private */
    public BleBMDJConnectInterface bmdjConnectInterface;
    /* access modifiers changed from: private */
    public BleBMDJExitInterface bmdjExitInterface;
    /* access modifiers changed from: private */
    public BleBMDJInterface bmdjInterface;
    /* access modifiers changed from: private */
    public BleBMDJParttenInterface bmdjParttenInterface;
    private final SearchResponse bmdjSearchResponse = new SearchResponse() {
        public void onSearchStarted() {
            BluetoothLog.d("onSearchStarted---bmdj");
        }

        public void onDeviceFounded(final SearchResult searchResult) {
            if (BleManager.this.mFliterManager.inBindedList2ConnectDevice(searchResult)) {
                BleManager.this.stopSearch();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        BleManager.this.connectBMDJDevice(searchResult);
                        BleManager.this.connectedDevice = searchResult;
                    }
                }, 700);
            }
        }

        public void onSearchStopped() {
            BluetoothLog.v("------------------------ onSearchStopped bmdj ------------------------");
        }

        public void onSearchCanceled() {
            BluetoothLog.v("------------------------ onSearchCanceled bmdj ------------------------");
        }
    };
    /* access modifiers changed from: private */
    public UUID connectedCharacter;
    /* access modifiers changed from: private */
    public SearchResult connectedDevice;
    /* access modifiers changed from: private */
    public UUID connectedService;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BleManager.this.deleteAdoreHistoryData();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean isBinding;
    /* access modifiers changed from: private */
    public String lastReciveData;
    private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        public void onConnectStatusChanged(String mac, int status) {
            BluetoothLog.v(String.format("CharacterActivity.onConnectStatusChanged status = %d", new Object[]{Integer.valueOf(status)}));
            if (status == 32 && BleManager.this.passivityDisconnectInterface != null) {
                BleManager.this.passivityDisconnectInterface.passivityDisconnect();
            }
        }
    };
    /* access modifiers changed from: private */
    public BleConnectFliterManager mFliterManager = new BleConnectFliterManager();
    private final SearchResponse mSearchResponse = new SearchResponse() {
        public void onSearchStarted() {
            BluetoothLog.d("onSearchStarted");
        }

        public void onDeviceFounded(final SearchResult device) {
            new Thread(new Runnable() {
                public void run() {
                    if (BleManager.this.isBinding) {
                        if (BleManager.this.mFliterManager.exceptBindedList2ConnectDevice(device)) {
                            BluetoothLog.v("exceptBindedList2ConnectDevice----" + device.toString());
                            if (BleManager.this.mFliterManager.needNotConnect(device)) {
                                BluetoothLog.v("needNotConnect----" + device.toString());
                                BleManager.this.recivingAdvData = true;
                                String finalData = BleManager.this.protocoManager.analysisSearchData(device, BleManager.this.mFliterManager.getMatchedFliterModel());
                                BluetoothLog.v("finalData----" + finalData);
                                if (finalData.length() > 0 && !BleManager.this.lastReciveData.equals(finalData)) {
                                    BleManager.this.lastReciveData = finalData;
                                    if (BleManager.this.protocoManager.isLockedData(finalData)) {
                                        BleManager.this.connectDevice(device);
                                        BleManager.this.connectedDevice = device;
                                    }
                                    BleManager.this.protocoManager.energyScaleProtocol(finalData, BleManager.this.mFliterManager.getMatchedFliterModel(), device.getAddress(), BleManager.this.mUserModel, BleManager.this.protocoInterface);
                                }
                            } else if (!BleManager.this.recivingAdvData) {
                                BleManager.this.connectDevice(device);
                                BleManager.this.connectedDevice = device;
                            }
                        }
                    } else if (!BleManager.this.mFliterManager.inBindedList2ConnectDevice(device)) {
                    } else {
                        if (BleManager.this.mFliterManager.needNotConnect(device)) {
                            BleManager.this.recivingAdvData = true;
                            String finalData2 = BleManager.this.protocoManager.analysisSearchData(device, BleManager.this.mFliterManager.getMatchedFliterModel());
                            if (finalData2.length() > 0 && !BleManager.this.lastReciveData.equals(finalData2)) {
                                BleManager.this.lastReciveData = finalData2;
                                BleManager.this.protocoManager.energyScaleProtocol(finalData2, BleManager.this.mFliterManager.getMatchedFliterModel(), device.getAddress(), BleManager.this.mUserModel, BleManager.this.protocoInterface);
                                if (BleManager.this.protocoManager.isLockedData(finalData2)) {
                                    BleManager.this.connectDevice(device);
                                    BleManager.this.connectedDevice = device;
                                }
                            }
                        } else if (!BleManager.this.recivingAdvData) {
                            BleManager.this.connectDevice(device);
                            BleManager.this.connectedDevice = device;
                        }
                    }
                }
            }).start();
        }

        public void onSearchStopped() {
            BluetoothLog.v("------------------------ onSearchStopped ------------------------");
        }

        public void onSearchCanceled() {
            BluetoothLog.v("------------------------ onSearchCanceled ------------------------");
        }
    };
    /* access modifiers changed from: private */
    public BleUserModel mUserModel;
    int num = 0;
    /* access modifiers changed from: private */
    public BleBMDJPassivityDisconnectInterface passivityDisconnectInterface;
    /* access modifiers changed from: private */
    public BleDataProtocoInterface protocoInterface;
    /* access modifiers changed from: private */
    public BleDataProtocoManager protocoManager = new BleDataProtocoManager();
    /* access modifiers changed from: private */
    public boolean recivingAdvData;

    public static BleManager shareInstance(Context context) {
        if (bleManager == null) {
            synchronized (BleManager.class) {
                if (bleManager == null) {
                    mBleClient = new BluetoothClient(context.getApplicationContext());
                    bleManager = new BleManager();
                }
            }
        }
        return bleManager;
    }

    public static boolean isBluetoothEnabled() {
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public void searchDevice(boolean isBinding2, List<BleDeviceModel> deviceList, BleUserModel userModel, BleDataProtocoInterface protocoInterface2) {
        SearchRequest request;
        this.isBinding = isBinding2;
        this.mUserModel = userModel;
        this.protocoInterface = protocoInterface2;
        this.mFliterManager.setBindingList(deviceList);
        this.recivingAdvData = false;
        this.lastReciveData = "";
        this.connectedService = null;
        this.connectedCharacter = null;
        this.bmdjParttenInterface = null;
        this.bmdjConnectInterface = null;
        this.bmdjInterface = null;
        this.bmdjExitInterface = null;
        this.passivityDisconnectInterface = null;
        mBleClient.stopSearch();
        disconnectDevice();
        if (VERSION.SDK_INT < 21) {
            request = new Builder().searchBluetoothLeDevice(1000, 1000).build();
        } else {
            request = new Builder().searchBluetoothLeDevice(DataSource.NetDataMaxTimeMs).build();
        }
        mBleClient.search(request, this.mSearchResponse);
    }

    public void connectBMDJ(List<BleDeviceModel> deviceList, BleBMDJConnectInterface connectInterface) {
        SearchRequest request;
        this.bmdjConnectInterface = connectInterface;
        this.lastReciveData = "";
        this.connectedService = null;
        this.connectedCharacter = null;
        this.mFliterManager.setBindingList(deviceList);
        this.protocoInterface = null;
        this.bmdjInterface = null;
        this.bmdjParttenInterface = null;
        this.bmdjExitInterface = null;
        this.passivityDisconnectInterface = null;
        mBleClient.stopSearch();
        disconnectDevice();
        if (VERSION.SDK_INT < 21) {
            request = new Builder().searchBluetoothLeDevice(1000, 1000).build();
        } else {
            request = new Builder().searchBluetoothLeDevice(DataSource.NetDataMaxTimeMs).build();
        }
        mBleClient.search(request, this.bmdjSearchResponse);
    }

    public void reSearchDevice() {
        SearchRequest request;
        mBleClient.stopSearch();
        disconnectDevice();
        this.recivingAdvData = false;
        if (VERSION.SDK_INT <= 21) {
            request = new Builder().searchBluetoothLeDevice(1000, 1000).build();
        } else {
            request = new Builder().searchBluetoothLeDevice(DataSource.NetDataMaxTimeMs).build();
        }
        mBleClient.search(request, this.mSearchResponse);
    }

    public void search() {
        SearchRequest request;
        mBleClient.stopSearch();
        this.recivingAdvData = false;
        if (VERSION.SDK_INT <= 21) {
            request = new Builder().searchBluetoothLeDevice(1000, 1000).build();
        } else {
            request = new Builder().searchBluetoothLeDevice(DataSource.NetDataMaxTimeMs).build();
        }
        mBleClient.search(request, this.mSearchResponse);
    }

    /* access modifiers changed from: private */
    public void disconnectADVDevice() {
        this.passivityDisconnectInterface = null;
        if (this.connectedDevice != null && !this.connectedDevice.getName().equals(BleConnectFliterModel.ELECTRONIC_SCALE) && !this.connectedDevice.getName().equals(BleConnectFliterModel.ADORE_SCALE)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    BleManager.mBleClient.disconnect(BleManager.this.connectedDevice.getAddress());
                }
            }, 500);
        }
    }

    public void disconnectDevice() {
        this.passivityDisconnectInterface = null;
        if (this.connectedDevice != null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    BleManager.mBleClient.disconnect(BleManager.this.connectedDevice.getAddress());
                }
            }, 500);
        }
    }

    public void stopSearch() {
        mBleClient.stopSearch();
        this.passivityDisconnectInterface = null;
        if (this.connectedDevice != null && this.connectedDevice.getName().equals(BleConnectFliterModel.ELECTRONIC_SCALE)) {
            mBleClient.disconnect(this.connectedDevice.getAddress());
        }
    }

    public void dissconnectBMScale() {
        mBleClient.stopSearch();
        this.passivityDisconnectInterface = null;
        if (this.connectedDevice != null) {
            mBleClient.write(this.connectedDevice.getAddress(), this.connectedService, this.connectedCharacter, this.protocoManager.sendExitMBDJData2Scale(), new BleWriteResponse() {
                public void onResponse(int code) {
                    BleManager.mBleClient.disconnect(BleManager.this.connectedDevice.getAddress());
                }
            });
        }
    }

    public void deleteAdoreHistoryData() {
        if (this.connectedDevice != null && this.connectedService != null && this.connectedCharacter != null) {
            final byte[] bytes = BleDataProtocoManager.deleteAdoreHistoryData();
            mBleClient.write(this.connectedDevice.getAddress(), this.connectedService, this.connectedCharacter, bytes, new BleWriteResponse() {
                public void onResponse(int code) {
                    Log.d("BleManager", " deleteAdoreHistoryData code = " + code);
                    if (code == 0) {
                        BleManager.this.disconnectDevice();
                    } else if (bytes.length > 0) {
                        BleManager.this.num++;
                        if (BleManager.this.num >= 3) {
                            BleManager.this.num = 0;
                            BleManager.this.disconnectDevice();
                            return;
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                BleManager.this.deleteAdoreHistoryData();
                            }
                        }, 500);
                    } else {
                        BleManager.this.disconnectDevice();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void connectBMDJDevice(SearchResult device) {
        final String deviceMac = device.getAddress();
        mBleClient.registerConnectStatusListener(deviceMac, this.mConnectStatusListener);
        mBleClient.connect(deviceMac, new BleConnectOptions.Builder().setConnectRetry(2).setConnectTimeout(7000).setServiceDiscoverRetry(2).setServiceDiscoverTimeout(7000).build(), new BleConnectResponse() {
            public void onResponse(int code, BleGattProfile profile) {
                BleManager.this.mFliterManager.monitirBMDJResponse(code, profile, new BleConnectFliterInterface() {
                    public void targetResponse(UUID service, UUID character, final BleConnectFliterModel fliterModel) {
                        BleManager.this.connectedService = service;
                        BleManager.this.connectedCharacter = character;
                        BleManager.mBleClient.notify(deviceMac, service, character, new BleNotifyResponse() {
                            public void onNotify(UUID service, UUID character, byte[] value) {
                                String reciveData = String.format("%s", new Object[]{ByteUtils.byteToString(value)});
                                if (!BleManager.this.lastReciveData.equals(reciveData)) {
                                    BleManager.this.lastReciveData = reciveData;
                                    BluetoothLog.v("reciveData--------- " + reciveData);
                                    if (BleManager.this.bmdjConnectInterface != null) {
                                        BleManager.this.protocoManager.bmScaleStatusProtocol(reciveData, fliterModel, BleManager.this.bmdjConnectInterface);
                                    }
                                    if (BleManager.this.bmdjInterface != null) {
                                        BleManager.this.protocoManager.bmScaleProtocol(reciveData, fliterModel, BleManager.this.bmdjInterface);
                                    }
                                    if (BleManager.this.bmdjParttenInterface != null) {
                                        BleManager.this.protocoManager.bmScaleStartTimingProtocol(reciveData, fliterModel, BleManager.this.bmdjParttenInterface);
                                    }
                                    if (BleManager.this.bmdjExitInterface != null) {
                                        BleManager.this.protocoManager.bmScaleExitStatusProtocol(reciveData, fliterModel, BleManager.this.bmdjExitInterface);
                                    }
                                }
                            }

                            public void onResponse(int code) {
                                BluetoothLog.v("reciveDataCode--------- " + code);
                            }
                        });
                        UUID uuid = service;
                        UUID uuid2 = character;
                        BleManager.mBleClient.write(deviceMac, uuid, uuid2, BleManager.this.protocoManager.sendMBDJData2Scale(), new BleWriteResponse() {
                            public void onResponse(int code) {
                            }
                        });
                    }

                    public void target2Write(UUID service, UUID character, BleConnectFliterModel fliterModel) {
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void connectDevice(final SearchResult device) {
        final String deviceMac = device.getAddress();
        mBleClient.registerConnectStatusListener(deviceMac, this.mConnectStatusListener);
        mBleClient.connect(deviceMac, new BleConnectOptions.Builder().setConnectRetry(2).setConnectTimeout(7000).setServiceDiscoverRetry(2).setServiceDiscoverTimeout(7000).build(), new BleConnectResponse() {
            public void onResponse(int code, BleGattProfile profile) {
                if (code == 0) {
                    BleManager.this.mFliterManager.monitorTargetResponse(code, profile, new BleConnectFliterInterface() {
                        public void target2Write(UUID service, UUID character, BleConnectFliterModel fliterModel) {
                            BleManager.this.connectedService = service;
                            BleManager.this.connectedCharacter = character;
                            BleManager.this.write(deviceMac, service, character, BleManager.this.protocoManager.sendData(fliterModel, BleManager.this.mUserModel));
                        }

                        public void targetResponse(UUID service, UUID character, final BleConnectFliterModel fliterModel) {
                            BleManager.mBleClient.notify(deviceMac, service, character, new BleNotifyResponse() {
                                public void onNotify(UUID service, UUID character, byte[] value) {
                                    String reciveData = String.format("%s", new Object[]{ByteUtils.byteToString(value)});
                                    if (!BleManager.this.lastReciveData.equals(reciveData)) {
                                        BleManager.this.lastReciveData = reciveData;
                                        BleManager.this.protocoManager.analysisReciveData(reciveData, deviceMac, fliterModel, BleManager.this.mUserModel, BleManager.this.protocoInterface, new BleDataStateInterface() {
                                            public void sendHistoryData() {
                                                BleManager.this.num = 0;
                                                BleManager.this.handler.sendEmptyMessage(0);
                                            }
                                        });
                                    }
                                }

                                public void onResponse(int code) {
                                    BluetoothLog.v("reciveDataCode--------- " + code);
                                    BleManager.this.connectedDevice = device;
                                }
                            });
                        }
                    });
                } else {
                    BleManager.this.disconnectADVDevice();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void write(String mac, UUID service, UUID character, List<byte[]> commond) {
        final List<byte[]> list = commond;
        final String str = mac;
        final UUID uuid = service;
        final UUID uuid2 = character;
        mBleClient.write(mac, service, character, (byte[]) commond.get(0), new BleWriteResponse() {
            public void onResponse(int code) {
                if (code == 0) {
                    BluetoothLog.v("writeSuccess--------- " + String.format("%s", new Object[]{ByteUtils.byteToString((byte[]) list.get(0))}));
                    list.remove(0);
                    if (!list.isEmpty()) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                BleManager.this.write(str, uuid, uuid2, list);
                            }
                        }, 700);
                    } else {
                        BleManager.this.disconnectADVDevice();
                    }
                } else {
                    BleManager.this.disconnectADVDevice();
                }
            }
        });
    }

    public void intoBMDJPattern(BleBMDJParttenInterface parttenInterface) {
        this.bmdjParttenInterface = parttenInterface;
    }

    public void BMDJTimeInterval(BleBMDJInterface bmdjInterface2) {
        this.bmdjInterface = bmdjInterface2;
    }

    public void exitBMDJPattern(BleBMDJExitInterface exitInterface) {
        this.bmdjExitInterface = exitInterface;
    }

    public void passivityDisconnect(BleBMDJPassivityDisconnectInterface passivityDisconnectInterface2) {
        this.passivityDisconnectInterface = passivityDisconnectInterface2;
    }
}
