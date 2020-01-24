package com.iwown.device_module.device_setting.wifi_scale.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.OptionsPickerView.Builder;
import com.bigkoo.pickerview.OptionsPickerView.OnOptionsSelectListener;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.TimePickerView.OnTimeSelectListener;
import com.bigkoo.pickerview.listener.CustomListener;
import com.dmcbig.mediapicker.PickerConfig;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.FamilyNoAccountAddRequest;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.sql.weight.TB_WeightUser;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.view.ItemView;
import com.iwown.device_module.common.view.dialog.PreDialog;
import com.iwown.device_module.device_setting.wifi_scale.eventbus.EventbusFinish;
import com.iwown.device_module.device_setting.wifi_scale.util.Cm2FtUtil;
import com.iwown.lib_common.network.NetworkUtils;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

@Route(path = "/device/AddUserActivity")
public class AddOrEditUserActivity extends DeviceModuleBaseActivity implements OnClickListener {
    public static final int Type_Add = 1;
    public static final int Type_Delete = 2;
    AddEditUserCallback callback = new AddEditUserCallback();
    /* access modifiers changed from: private */
    public Context context;
    TextView deleteWeightUser;
    /* access modifiers changed from: private */
    public List<Integer> fits = new ArrayList();
    int gender = 0;
    /* access modifiers changed from: private */
    public List<String> genderItems = new ArrayList();
    /* access modifiers changed from: private */
    public int height = Opcodes.USHR_LONG;
    /* access modifiers changed from: private */
    public List<Integer> ins = new ArrayList();
    /* access modifiers changed from: private */
    public List<Integer> items = new ArrayList();
    private Handler mHandler = new Handler(Looper.myLooper());
    /* access modifiers changed from: private */
    public PreDialog preDialog;
    Runnable preDialogDismiss = new Runnable() {
        public void run() {
            if (AddOrEditUserActivity.this.preDialog != null && AddOrEditUserActivity.this.context != null) {
                AddOrEditUserActivity.this.preDialog.dismiss();
            }
        }
    };
    OptionsPickerView pvCustomOptions;
    OptionsPickerView pvGenderOptions;
    TimePickerView pvTime;
    Calendar selectedDate = Calendar.getInstance();
    /* access modifiers changed from: private */
    public int type;
    /* access modifiers changed from: private */
    public long uid;
    TB_WeightUser user;
    EditText userRemark;
    ItemView wifiScaleUserBirthday;
    ItemView wifiScaleUserGender;
    ItemView wifiScaleUserHeight;

    private class AddEditUserCallback implements MyCallback<Integer> {
        private AddEditUserCallback() {
        }

        public void onSuccess(Integer integer) {
            if (integer.intValue() == 0) {
                AddOrEditUserActivity.this.showDialog(false);
                EventBus.getDefault().post(new EventbusFinish(2));
                AddOrEditUserActivity.this.finish();
                return;
            }
            ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.failed));
        }

        public void onFail(Throwable e) {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_add_or_edit_user);
        this.context = this;
        setLeftBackTo();
        setTitleText(R.string.device_module_scale_wifi_user_3);
        this.type = getIntent().getIntExtra("type", 1);
        this.uid = getIntent().getLongExtra(UserConst.UID, 0);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 40; i < 250; i++) {
            this.items.add(Integer.valueOf(i));
        }
        for (int i2 = 1; i2 < 10; i2++) {
            this.fits.add(Integer.valueOf(i2));
        }
        for (int i3 = 0; i3 < 12; i3++) {
            this.ins.add(Integer.valueOf(i3));
        }
        this.genderItems.add(getString(R.string.device_module_s2_wifi_gender_male));
        this.genderItems.add(getString(R.string.device_module_s2_wifi_gender_female));
        this.user = (TB_WeightUser) DataSupport.where("uid=?", String.valueOf(this.uid)).findLast(TB_WeightUser.class);
        if (this.user != null) {
            this.userRemark.setText(this.user.getName());
            if (this.user.getGender() == 1) {
                this.gender = 1;
            } else if (this.user.getGender() == 2) {
                this.gender = 2;
            } else {
                this.gender = 1;
            }
            this.height = (int) this.user.getHeight();
            if (UserConfig.getInstance().isMertric()) {
                this.wifiScaleUserHeight.setMessageText(String.valueOf(this.user.getHeight()) + "cm");
            } else {
                try {
                    double ind = Cm2FtUtil.cm2Inch((double) this.user.getHeight());
                    int in = (int) (ind % 12.0d);
                    this.wifiScaleUserHeight.setMessageText(String.valueOf((int) (ind / 12.0d)) + "ft" + in + "in");
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
            this.wifiScaleUserBirthday.setMessageText(this.user.getBirthday());
            if (this.gender == 1) {
                this.wifiScaleUserGender.setMessageText(getString(R.string.device_module_s2_wifi_gender_male));
            } else if (this.gender == 2) {
                this.wifiScaleUserGender.setMessageText(getString(R.string.device_module_s2_wifi_gender_female));
            } else {
                this.wifiScaleUserGender.setMessageText(getString(R.string.device_module_s2_wifi_gender_male));
            }
            try {
                KLog.i("user.getBirthday()" + this.user.getBirthday());
                String[] dateStr = this.user.getBirthday().split(HelpFormatter.DEFAULT_OPT_PREFIX);
                if (dateStr.length == 3) {
                    this.selectedDate.set(Integer.parseInt(dateStr[0]), Integer.parseInt(dateStr[1]) - 1, Integer.parseInt(dateStr[2]));
                }
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
    }

    private void initView() {
        this.wifiScaleUserHeight = (ItemView) findViewById(R.id.wifi_scale_user_height);
        this.wifiScaleUserBirthday = (ItemView) findViewById(R.id.wifi_scale_user_birthday);
        this.deleteWeightUser = (TextView) findViewById(R.id.delete_weight_user);
        this.userRemark = (EditText) findViewById(R.id.user_remark);
        this.wifiScaleUserGender = (ItemView) findViewById(R.id.wifi_scale_user_gender);
        this.wifiScaleUserGender.setOnClickListener(this);
        this.wifiScaleUserHeight.setOnClickListener(this);
        this.wifiScaleUserBirthday.setOnClickListener(this);
        this.deleteWeightUser.setOnClickListener(this);
        if (this.type == 1) {
            this.deleteWeightUser.setVisibility(8);
        } else if (this.type == 2) {
            this.deleteWeightUser.setVisibility(0);
        }
        setRightText(getString(R.string.iwown_save), new ActionOnclickListener() {
            public void onclick() {
                if (TextUtils.isEmpty(AddOrEditUserActivity.this.wifiScaleUserBirthday.getMessageText())) {
                    ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.device_module_wifi_user_birthday_null));
                } else if (AddOrEditUserActivity.this.height == 0 || TextUtils.isEmpty(AddOrEditUserActivity.this.wifiScaleUserHeight.getMessageText())) {
                    ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.device_module_wifi_user_height_null));
                } else if (TextUtils.isEmpty(AddOrEditUserActivity.this.userRemark.getText().toString())) {
                    ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.device_module_wifi_user_nick_null));
                } else if (AddOrEditUserActivity.this.userRemark.getText().toString().length() > 12) {
                    ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.device_module_wifi_user_nick_null_to_long));
                } else if (AddOrEditUserActivity.this.gender == 0) {
                    ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.device_module_wifi_user_gender_null));
                } else if (!NetworkUtils.isNetworkAvailable()) {
                    ToastUtil.showToast(AddOrEditUserActivity.this.getString(R.string.network_error));
                } else {
                    FamilyNoAccountAddRequest request = new FamilyNoAccountAddRequest();
                    request.setUid(ContextUtil.getLUID());
                    request.setBirthday(AddOrEditUserActivity.this.wifiScaleUserBirthday.getMessageText());
                    request.setRelation(AddOrEditUserActivity.this.userRemark.getText().toString());
                    request.setHeight((float) AddOrEditUserActivity.this.height);
                    if (AddOrEditUserActivity.this.gender == 1) {
                        request.setGender(1);
                    } else if (AddOrEditUserActivity.this.gender == 2) {
                        request.setGender(2);
                    } else {
                        request.setGender(1);
                    }
                    if (AddOrEditUserActivity.this.type == 1) {
                        AddOrEditUserActivity.this.showDialog(true);
                        NetFactory.getInstance().getClient(AddOrEditUserActivity.this.callback).commitNoAccountProfile(request);
                    } else if (AddOrEditUserActivity.this.type == 2) {
                        request.setFamilyUid(AddOrEditUserActivity.this.uid);
                        NetFactory.getInstance().getClient(AddOrEditUserActivity.this.callback).editNoAccountProfile(request);
                    }
                }
            }
        });
        this.selectedDate.set(PickerConfig.RESULT_UPDATE_CODE, 0, 1);
    }

    private void hideKeyBord() {
        try {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void showDialog(boolean show) {
        if (this.preDialog == null) {
            if (show) {
                this.preDialog = new PreDialog(this.context);
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

    public void onClick(View view) {
        int i;
        int id = view.getId();
        hideKeyBord();
        if (id == R.id.wifi_scale_user_height) {
            if (UserConfig.getInstance().isMertric()) {
                this.pvCustomOptions = new Builder(this, new OnOptionsSelectListener() {
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        AddOrEditUserActivity.this.wifiScaleUserHeight.setMessageText(String.valueOf(AddOrEditUserActivity.this.items.get(options1)) + "cm");
                        AddOrEditUserActivity.this.height = ((Integer) AddOrEditUserActivity.this.items.get(options1)).intValue();
                    }
                }).setLayoutRes(R.layout.device_module_layout_picker_wheelview_option, new CustomListener() {
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.ok_dialog_option);
                        ((TextView) v.findViewById(R.id.dialog_title_option)).setText(AddOrEditUserActivity.this.getString(R.string.height));
                        TextView ivCancel = (TextView) v.findViewById(R.id.cancel_dialog_option);
                        tvSubmit.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                AddOrEditUserActivity.this.pvCustomOptions.returnData();
                                AddOrEditUserActivity.this.pvCustomOptions.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                AddOrEditUserActivity.this.pvCustomOptions.dismiss();
                            }
                        });
                    }
                }).setSelectOptions(this.height - 40).setOutSideCancelable(false).setTextColorOut(getResources().getColor(R.color.device_module_device_wifi_picker_un_select)).setLineSpacingMultiplier(2.0f).setDividerColor(getResources().getColor(R.color.device_module_common_line_color)).setTextColorCenter(getResources().getColor(R.color.device_module_white)).setBgColor(getResources().getColor(R.color.device_module_common_background_1)).setContentTextSize(22).isCenterLabel(true).isDialog(false).setLabels("cm", "", "").build();
                this.pvCustomOptions.setNPicker(this.items, null, null);
                this.pvCustomOptions.show();
                return;
            }
            double ind = Cm2FtUtil.cm2Inch((double) this.height);
            KLog.i("=====ft======" + ind + "==cm=" + this.height);
            int in = (int) (ind % 12.0d);
            this.pvCustomOptions = new Builder(this, new OnOptionsSelectListener() {
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    try {
                        AddOrEditUserActivity.this.wifiScaleUserHeight.setMessageText(String.valueOf(AddOrEditUserActivity.this.fits.get(options1)) + "ft" + AddOrEditUserActivity.this.ins.get(option2) + "in");
                        AddOrEditUserActivity.this.height = (int) Cm2FtUtil.inch2Cm(Cm2FtUtil.ft2in(((Integer) AddOrEditUserActivity.this.fits.get(options1)).intValue()) + ((Integer) AddOrEditUserActivity.this.ins.get(option2)).intValue());
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }).setLayoutRes(R.layout.device_module_layout_picker_wheelview_option, new CustomListener() {
                public void customLayout(View v) {
                    TextView tvSubmit = (TextView) v.findViewById(R.id.ok_dialog_option);
                    ((TextView) v.findViewById(R.id.dialog_title_option)).setText(AddOrEditUserActivity.this.getString(R.string.height));
                    TextView ivCancel = (TextView) v.findViewById(R.id.cancel_dialog_option);
                    tvSubmit.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            AddOrEditUserActivity.this.pvCustomOptions.returnData();
                            AddOrEditUserActivity.this.pvCustomOptions.dismiss();
                        }
                    });
                    ivCancel.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            AddOrEditUserActivity.this.pvCustomOptions.dismiss();
                        }
                    });
                }
            }).setSelectOptions(((int) (ind / 12.0d)) - 1, in).setOutSideCancelable(false).setTextColorOut(getResources().getColor(R.color.device_module_device_wifi_picker_un_select)).setLineSpacingMultiplier(2.0f).setDividerColor(getResources().getColor(R.color.device_module_common_line_color)).setTextColorCenter(getResources().getColor(R.color.device_module_white)).setBgColor(getResources().getColor(R.color.device_module_common_background_1)).setContentTextSize(22).isCenterLabel(true).isDialog(false).setLabels("ft", "in", "").build();
            this.pvCustomOptions.setNPicker(this.fits, this.ins, null);
            this.pvCustomOptions.show();
        } else if (id == R.id.wifi_scale_user_birthday) {
            Calendar startDate = Calendar.getInstance();
            startDate.set(1920, 0, 1);
            Calendar endDate = Calendar.getInstance();
            endDate.set(2050, 12, 30);
            this.pvTime = new TimePickerView.Builder(this, new OnTimeSelectListener() {
                public void onTimeSelect(Date date, View v) {
                    AddOrEditUserActivity.this.wifiScaleUserBirthday.setMessageText(AddOrEditUserActivity.this.getTime(date));
                }
            }).setLayoutRes(R.layout.device_module_layout_picker_wheelview_option_date, new CustomListener() {
                public void customLayout(View v) {
                    TextView ivCancel = (TextView) v.findViewById(R.id.cancel_dialog_option);
                    ((TextView) v.findViewById(R.id.ok_dialog_option)).setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            AddOrEditUserActivity.this.pvTime.returnData();
                            AddOrEditUserActivity.this.pvTime.dismiss();
                        }
                    });
                    ivCancel.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            AddOrEditUserActivity.this.pvTime.dismiss();
                        }
                    });
                }
            }).setType(new boolean[]{true, true, true, false, false, false}).setContentSize(22).setOutSideCancelable(false).setTextColorOut(getResources().getColor(R.color.device_module_device_wifi_picker_un_select)).isCyclic(true).setLineSpacingMultiplier(2.0f).setDividerColor(getResources().getColor(R.color.device_module_common_line_color)).setTextColorCenter(getResources().getColor(R.color.device_module_white)).setBgColor(getResources().getColor(R.color.device_module_common_background_1)).setRangDate(startDate, endDate).setLabel("", "", "", "", "", "").setDate(this.selectedDate).isCenterLabel(true).isDialog(false).gravity(17).build();
            this.pvTime.show();
        } else if (id == R.id.delete_weight_user) {
            if (!NetworkUtils.isNetworkAvailable()) {
                ToastUtil.showToast(getString(R.string.network_error));
            } else {
                NetFactory.getInstance().getClient(this.callback).deleteNoAccountProfile(this.uid);
            }
        } else if (id == R.id.wifi_scale_user_gender) {
            Builder layoutRes = new Builder(this, new OnOptionsSelectListener() {
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    AddOrEditUserActivity.this.wifiScaleUserGender.setMessageText((String) AddOrEditUserActivity.this.genderItems.get(options1));
                    if (((String) AddOrEditUserActivity.this.genderItems.get(options1)).equalsIgnoreCase(AddOrEditUserActivity.this.getString(R.string.device_module_s2_wifi_gender_male))) {
                        AddOrEditUserActivity.this.gender = 1;
                    } else {
                        AddOrEditUserActivity.this.gender = 2;
                    }
                }
            }).setLayoutRes(R.layout.device_module_layout_picker_wheelview_option, new CustomListener() {
                public void customLayout(View v) {
                    TextView tvSubmit = (TextView) v.findViewById(R.id.ok_dialog_option);
                    ((TextView) v.findViewById(R.id.dialog_title_option)).setText(AddOrEditUserActivity.this.getString(R.string.gender));
                    TextView ivCancel = (TextView) v.findViewById(R.id.cancel_dialog_option);
                    tvSubmit.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            AddOrEditUserActivity.this.pvGenderOptions.returnData();
                            AddOrEditUserActivity.this.pvGenderOptions.dismiss();
                        }
                    });
                    ivCancel.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            AddOrEditUserActivity.this.pvGenderOptions.dismiss();
                        }
                    });
                }
            });
            if (this.gender == 1) {
                i = 0;
            } else {
                i = 1;
            }
            this.pvGenderOptions = layoutRes.setSelectOptions(i).setOutSideCancelable(false).setTextColorOut(getResources().getColor(R.color.device_module_device_wifi_picker_un_select)).setLineSpacingMultiplier(2.0f).setDividerColor(getResources().getColor(R.color.device_module_common_line_color)).setTextColorCenter(getResources().getColor(R.color.device_module_white)).setBgColor(getResources().getColor(R.color.device_module_common_background_1)).setContentTextSize(22).isCenterLabel(true).isDialog(false).setLabels("", "", "").build();
            this.pvGenderOptions.setNPicker(this.genderItems, null, null);
            this.pvGenderOptions.show();
        }
    }

    /* access modifiers changed from: private */
    public String getTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
