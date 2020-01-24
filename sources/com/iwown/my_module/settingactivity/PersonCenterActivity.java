package com.iwown.my_module.settingactivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import com.android.tu.loadingdialog.LoadingDailog;
import com.android.tu.loadingdialog.LoadingDailog.Builder;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.enumtype.EnumMeasureUnit;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.dialog.CompilePhotoDialog;
import com.iwown.my_module.dialog.CompilePhotoDialog.OnPhotoConfirmListener;
import com.iwown.my_module.dialog.DatetimeDialog;
import com.iwown.my_module.dialog.DatetimeDialog.OnConfirmListener;
import com.iwown.my_module.dialog.EditTextDialog;
import com.iwown.my_module.dialog.EditTextDialog.OnTextConfirmListener;
import com.iwown.my_module.dialog.GenderDialog;
import com.iwown.my_module.dialog.GenderDialog.OnGenderConfirmListener;
import com.iwown.my_module.dialog.HeightDialog;
import com.iwown.my_module.dialog.HeightDialog.OnHeightConfirmListener;
import com.iwown.my_module.dialog.WeightDialog;
import com.iwown.my_module.dialog.WeightDialog.OnWeightConfirmListener;
import com.iwown.my_module.healthy.network.request.AccountProfile;
import com.iwown.my_module.healthy.network.request.WeightSend;
import com.iwown.my_module.model.request.UploadPhotoCode;
import com.iwown.my_module.model.response.ReturnCode;
import com.iwown.my_module.model.response.UserInfo;
import com.iwown.my_module.network.FileService;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.my_module.utility.BitmapManager;
import com.iwown.my_module.utility.CalendarUtility;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.utility.Constants;
import com.iwown.my_module.utility.ImageUtility;
import com.iwown.my_module.utility.MeasureTransform;
import com.iwown.my_module.utility.ScreenUtility;
import com.iwown.my_module.widget.CommonPopupWindow;
import com.iwown.my_module.widget.CommonPopupWindow.LayoutGravity;
import com.iwown.my_module.widget.ErrorTipTextView.OnDisplayEndListener;
import com.iwown.my_module.widget.SelectinfoView;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.litepal.crud.DataSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonCenterActivity extends BaseActivity implements OnPhotoConfirmListener, OnClickListener {
    private static final String TAG = "PersonCenterActivity";
    private Bitmap bitMap;
    /* access modifiers changed from: private */
    public TextView btnCopy;
    private DatetimeDialog dialog;
    private float firstWeight = 0.0f;
    private GenderDialog genderDialog;
    private HeightDialog heightDialog;
    private Uri imageUri;
    TextView imgTv;
    private Intent intent = new Intent(Constants.ACTION_PERSONCENTER_USER_PHOTO);
    /* access modifiers changed from: private */
    public Context mContext;
    Builder mDialogBuilder;
    File mFile;
    private FileService mFileService;
    /* access modifiers changed from: private */
    public EnumMeasureUnit mMeasureUnit;
    private Retrofit mRetrofit;
    /* access modifiers changed from: private */
    public UserInfoEntity mUserInfo;
    private UserService mUserService;
    private LoadingDailog mWaitDialog;
    /* access modifiers changed from: private */
    public boolean modified = false;
    private EditTextDialog nickNameDialog;
    SelectinfoView nickNameView;
    private CompilePhotoDialog photoDialog;
    /* access modifiers changed from: private */
    public LayoutGravity popupLayoutGravity;
    /* access modifiers changed from: private */
    public CommonPopupWindow popupWindow;
    SelectinfoView settingBirthday;
    SelectinfoView settingGender;
    SelectinfoView settingHeight;
    SelectinfoView settingWeight;
    private long timeMillis;
    TextView uidView;
    private WeightDialog weightDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.my_module_activity_personcenter);
        this.settingBirthday = (SelectinfoView) findViewById(R.id.settings_birthday);
        this.settingBirthday.setOnClickListener(this);
        this.settingGender = (SelectinfoView) findViewById(R.id.settings_gender);
        this.settingGender.setOnClickListener(this);
        this.settingHeight = (SelectinfoView) findViewById(R.id.settings_height);
        this.settingHeight.setOnClickListener(this);
        this.settingWeight = (SelectinfoView) findViewById(R.id.settings_weight);
        this.settingWeight.setOnClickListener(this);
        this.uidView = (TextView) findViewById(R.id.uid_view);
        this.uidView.setOnClickListener(this);
        this.nickNameView = (SelectinfoView) findViewById(R.id.nickname_menu);
        this.nickNameView.setOnClickListener(this);
        setTitleText(R.string.title_person_center);
        this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        initUserProfile();
        initView();
        initEvent();
        PermissionsUtils.handleSTORAGE(this, new PermissinCallBack() {
            public void callBackOk() {
            }

            public void callBackFial() {
            }
        });
        this.mDialogBuilder = new Builder(this).setMessage("waiting...").setCancelable(true).setCancelOutside(true);
    }

    private void initView() {
        Calendar cal = Calendar.getInstance();
        cal.add(1, -24);
        updateClearTime(cal.getTimeInMillis());
        this.uidView.setText(String.valueOf(this.mUserInfo.getUid()));
        this.uidView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (PersonCenterActivity.this.popupWindow == null) {
                    PersonCenterActivity.this.popupWindow = new CommonPopupWindow(PersonCenterActivity.this, R.layout.my_module_copy_popup_window, 200, -2) {
                        /* access modifiers changed from: protected */
                        public void initView() {
                            PersonCenterActivity.this.btnCopy = (TextView) getContentView().findViewById(R.id.btn_copy);
                        }

                        /* access modifiers changed from: protected */
                        public void initEvent() {
                            PersonCenterActivity.this.btnCopy.setOnClickListener(new OnClickListener() {
                                public void onClick(View view) {
                                    ((ClipboardManager) AnonymousClass1.this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, PersonCenterActivity.this.uidView.getText()));
                                    PersonCenterActivity.this.popupWindow.dismiss();
                                }
                            });
                        }
                    };
                }
                if (PersonCenterActivity.this.popupLayoutGravity == null) {
                    PersonCenterActivity.this.popupLayoutGravity = new LayoutGravity(Opcodes.LONG_TO_INT);
                }
                PersonCenterActivity.this.popupWindow.showBashOfAnchor(PersonCenterActivity.this.uidView, PersonCenterActivity.this.popupLayoutGravity, 0, 0);
                return false;
            }
        });
        if (!TextUtils.isEmpty(this.mUserInfo.getNickname())) {
            this.nickNameView.setMessageText(this.mUserInfo.getNickname());
        } else {
            this.nickNameView.setMessageText(GlobalUserDataFetcher.getEmail(this));
        }
        Log.i(TAG, String.format("scow----gender to set:%d", new Object[]{Integer.valueOf(this.mUserInfo.getGender())}));
        if (this.mUserInfo.getGender() == 1) {
            this.settingGender.setMessageText(getString(R.string.male));
        } else if (this.mUserInfo.getGender() == 2) {
            this.settingGender.setMessageText(getString(R.string.female));
        } else {
            this.settingGender.setMessageText("");
        }
        this.settingBirthday.setMessageText(this.mUserInfo.getBirthday());
        this.mMeasureUnit = GlobalUserDataFetcher.getPreferredMeasureUnit(this);
        AppConfigUtil.getInstance(this);
        if (AppConfigUtil.isHealthy() && this.mMeasureUnit == EnumMeasureUnit.Imperial) {
            GlobalDataUpdater.setMeasureUnit(this, EnumMeasureUnit.Metric);
            this.mMeasureUnit = EnumMeasureUnit.Metric;
        }
        KLog.i("----------------" + this.mMeasureUnit);
        if (this.mMeasureUnit == EnumMeasureUnit.Imperial) {
            long in = CommonUtility.cmToIn((double) this.mUserInfo.getHeight());
            this.settingHeight.setMessageText((in / 12) + "ft" + (in % 12) + "in");
            float lbs = MeasureTransform.kg2Lbs(this.mUserInfo.getWeight());
            this.settingWeight.setMessageText(String.format("%.1flbs", new Object[]{Float.valueOf(lbs)}));
            return;
        }
        this.settingHeight.setMessageText(((int) this.mUserInfo.getHeight()) + "cm");
        this.settingWeight.setMessageText(String.format("%.1fkg", new Object[]{Float.valueOf(this.mUserInfo.getWeight())}));
    }

    private void initEvent() {
        setLeftBtn(new OnClickListener() {
            public void onClick(View view) {
                PersonCenterActivity.this.saveDataAndExit();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void saveDataAndExit() {
        Call<ReturnCode> call;
        if (!this.modified) {
            back();
            return;
        }
        setErrorTipEndListener(new OnDisplayEndListener() {
            public void onDisplayEnd() {
                PersonCenterActivity.this.back();
            }
        });
        showLoadingDialog();
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        final UserInfo userInfo = new UserInfo();
        userInfo.setNickname(this.mUserInfo.getNickname());
        userInfo.setBirthday(this.mUserInfo.getBirthday());
        userInfo.setHeight(this.mUserInfo.getHeight());
        userInfo.setWeight(this.mUserInfo.getWeight());
        userInfo.setGender(this.mUserInfo.getGender());
        userInfo.setUid(this.mUserInfo.getUid());
        if (AppConfigUtil.isHealthy(this)) {
            call = this.mUserService.postHealthyProfile(userInfo);
        } else {
            call = this.mUserService.updateUserInfo(userInfo);
        }
        call.enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                PersonCenterActivity.this.hideLoadingDialog();
                if (response == null || response.body() == null) {
                    PersonCenterActivity.this.raiseErrorNotice(PersonCenterActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                switch (((ReturnCode) response.body()).getRetCode()) {
                    case 0:
                        PersonCenterActivity.this.mUserInfo.saveOrUpdate("uid=?", String.valueOf(PersonCenterActivity.this.mUserInfo.getUid()));
                        if (AppConfigUtil.isHealthy()) {
                            PersonCenterActivity.this.postHealthyWeight();
                        }
                        Date birthDate = new Date();
                        if (userInfo.getBirthday() != null && !userInfo.getBirthday().equals("")) {
                            try {
                                birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(userInfo.getBirthday());
                            } catch (Exception ex) {
                                Log.e(PersonCenterActivity.TAG, ex.getMessage());
                            }
                        }
                        int age = CalendarUtility.getAgeByBirthday(birthDate);
                        int gender = userInfo.getGender();
                        float height = userInfo.getHeight();
                        float weight = userInfo.getWeight();
                        if (gender == 0) {
                            gender = 1;
                        }
                        if (height == 0.0f) {
                            if (gender == 1) {
                                height = 175.0f;
                            } else {
                                height = 165.0f;
                            }
                        }
                        if (weight == 0.0f) {
                            if (gender == 1) {
                                weight = 70.0f;
                            } else {
                                weight = 50.0f;
                            }
                        }
                        ModuleRouteDeviceInfoService.getInstance().updateBaseUserInfo(gender, height, weight, age);
                        PersonCenterActivity.this.back();
                        return;
                    case 10001:
                        PersonCenterActivity.this.raiseErrorNotice(R.string.sql_error);
                        return;
                    default:
                        PersonCenterActivity.this.raiseErrorNotice(PersonCenterActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((ReturnCode) response.body()).getRetCode())}));
                        return;
                }
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                PersonCenterActivity.this.hideLoadingDialog();
                PersonCenterActivity.this.raiseErrorNotice(PersonCenterActivity.this.getString(R.string.network_error));
            }
        });
    }

    private void saveBitmap(final String photoUrl) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    final Bitmap bm = BitmapManager.getImage(photoUrl);
                    PersonCenterActivity.this.mUserInfo.setPortraitImgStr(ImageUtility.bitmaptoString(bm));
                    PersonCenterActivity.this.mUserInfo.save();
                    PersonCenterActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (bm != null) {
                                Drawable drawable = new BitmapDrawable(PersonCenterActivity.this.getCroppedRoundBitmap(bm, ScreenUtility.dip2px(PersonCenterActivity.this.mContext, 50.0f)));
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                PersonCenterActivity.this.imgTv.setCompoundDrawables(null, null, drawable, null);
                            }
                        }
                    });
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }).start();
    }

    private void initUserProfile() {
        long uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
        if (uid > 0) {
            List<UserInfoEntity> users = null;
            try {
                users = DataSupport.where("uid = ?", String.valueOf(uid)).find(UserInfoEntity.class);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
            if (users != null && users.size() > 0) {
                this.mUserInfo = (UserInfoEntity) users.get(0);
                this.firstWeight = this.mUserInfo.getWeight();
                Log.i(TAG, String.format("scow----user from db uid:%d, gender:%d, height:%f, weight:%f, birthday:%s", new Object[]{Long.valueOf(uid), Integer.valueOf(this.mUserInfo.getGender()), Float.valueOf(this.mUserInfo.getHeight()), Float.valueOf(this.mUserInfo.getWeight()), this.mUserInfo.getBirthday()}));
            }
        }
        if (this.mUserInfo == null) {
            this.mUserInfo = new UserInfoEntity();
            this.mUserInfo.setUid(uid);
        }
        Log.i(TAG, String.format("scow---- user return uid:%d, gender:%d, height:%f, weight:%f, birthday:%s", new Object[]{Long.valueOf(uid), Integer.valueOf(this.mUserInfo.getGender()), Float.valueOf(this.mUserInfo.getHeight()), Float.valueOf(this.mUserInfo.getWeight()), this.mUserInfo.getBirthday()}));
    }

    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap squareBitmap;
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        if (bmpHeight > bmpWidth) {
            Bitmap bitmap = bmp;
            int i = (bmpHeight - bmpWidth) / 2;
            squareBitmap = Bitmap.createBitmap(bitmap, 0, i, bmpWidth, bmpWidth);
        } else if (bmpHeight < bmpWidth) {
            int x = (bmpWidth - bmpHeight) / 2;
            Bitmap bitmap2 = bmp;
            squareBitmap = Bitmap.createBitmap(bitmap2, x, 0, bmpHeight, bmpHeight);
        } else {
            squareBitmap = bmp;
        }
        if (squareBitmap.getWidth() == diameter && squareBitmap.getHeight() == diameter) {
            scaledSrcBmp = squareBitmap;
        } else {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle((float) (scaledSrcBmp.getWidth() / 2), (float) (scaledSrcBmp.getHeight() / 2), (float) (scaledSrcBmp.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        return output;
    }

    private void updateClearTime(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        this.timeMillis = cal.getTimeInMillis();
    }

    /* access modifiers changed from: 0000 */
    public void setBirthday(View v) {
        if (this.mUserInfo.getBirthday() == null || this.mUserInfo.getBirthday().equals("")) {
            Calendar now = Calendar.getInstance();
            now.add(1, -25);
            int year = now.get(1);
            int month = now.get(2) + 1;
            int day = now.get(5);
            this.dialog = new DatetimeDialog(this.mContext, String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)}));
        } else {
            this.dialog = new DatetimeDialog(this.mContext, this.mUserInfo.getBirthday());
        }
        this.dialog.show();
        this.dialog.setOnConfirmListener(new OnConfirmListener() {
            public void OnConfirm(String dateStr) {
                PersonCenterActivity.this.mUserInfo.setBirthday(dateStr);
                PersonCenterActivity.this.settingBirthday.setMessageText(PersonCenterActivity.this.mUserInfo.getBirthday());
                Log.i(PersonCenterActivity.TAG, String.format("date from dialog : %s", new Object[]{dateStr}));
                PersonCenterActivity.this.modified = true;
            }
        });
    }

    public void setGender(View v) {
        this.genderDialog = new GenderDialog(this.mContext, this.mUserInfo.getGender());
        this.genderDialog.setOnGenderConfirmListener(new OnGenderConfirmListener() {
            public void OnConfirm(boolean isBoy) {
                if (isBoy) {
                    PersonCenterActivity.this.settingGender.setMessageText(PersonCenterActivity.this.getString(R.string.male));
                    PersonCenterActivity.this.mUserInfo.setGender(1);
                } else {
                    PersonCenterActivity.this.settingGender.setMessageText(PersonCenterActivity.this.getString(R.string.female));
                    PersonCenterActivity.this.mUserInfo.setGender(2);
                }
                PersonCenterActivity.this.modified = true;
            }
        });
        this.genderDialog.show();
    }

    public void setHeight(View v) {
        this.heightDialog = new HeightDialog(this.mContext, (float) ((int) this.mUserInfo.getHeight()));
        this.heightDialog.setOnHeightConfirmListener(new OnHeightConfirmListener() {
            public void OnHeightConfirm(float height) {
                Log.i(PersonCenterActivity.TAG, String.format("height in activity:%f", new Object[]{Float.valueOf(height)}));
                if (height > 249.0f) {
                    height = 249.0f;
                }
                PersonCenterActivity.this.mUserInfo.setHeight(height);
                if (GlobalUserDataFetcher.getPreferredMeasureUnit(PersonCenterActivity.this) == EnumMeasureUnit.Imperial) {
                    int totalIn = (int) MeasureTransform.cm2Inch(height);
                    int inch = totalIn % 12;
                    int feet = totalIn / 12;
                    PersonCenterActivity.this.settingHeight.setMessageText(String.format("%dft%din", new Object[]{Integer.valueOf(feet), Integer.valueOf(inch)}));
                } else {
                    PersonCenterActivity.this.settingHeight.setMessageText(((int) height) + "cm");
                }
                if (PersonCenterActivity.this.mMeasureUnit != GlobalUserDataFetcher.getPreferredMeasureUnit(PersonCenterActivity.this)) {
                    PersonCenterActivity.this.mMeasureUnit = GlobalUserDataFetcher.getPreferredMeasureUnit(PersonCenterActivity.this);
                    if (PersonCenterActivity.this.mMeasureUnit == EnumMeasureUnit.Imperial) {
                        float lbs = MeasureTransform.kg2Lbs(PersonCenterActivity.this.mUserInfo.getWeight());
                        PersonCenterActivity.this.settingWeight.setMessageText(String.format("%.1flbs", new Object[]{Float.valueOf(lbs)}));
                    } else {
                        PersonCenterActivity.this.settingWeight.setMessageText(String.format("%.1fkg", new Object[]{Float.valueOf(PersonCenterActivity.this.mUserInfo.getWeight())}));
                    }
                }
                PersonCenterActivity.this.modified = true;
            }
        });
        this.heightDialog.show();
    }

    public void setWeight(View v) {
        this.weightDialog = new WeightDialog(this.mContext);
        this.weightDialog.setWeight(this.mUserInfo.getWeight());
        this.weightDialog.setOnWeightConfirmListener(new OnWeightConfirmListener() {
            public void OnWeightConfirm(float weight) {
                if (GlobalUserDataFetcher.getPreferredMeasureUnit(PersonCenterActivity.this) == EnumMeasureUnit.Imperial) {
                    float lbs = MeasureTransform.kg2Lbs(weight);
                    PersonCenterActivity.this.settingWeight.setMessageText(String.format("%.1flbs", new Object[]{Float.valueOf(lbs)}));
                } else {
                    PersonCenterActivity.this.settingWeight.setMessageText(String.format("%.1fkg", new Object[]{Float.valueOf(weight)}));
                }
                PersonCenterActivity.this.mUserInfo.setWeight(weight);
                if (PersonCenterActivity.this.mMeasureUnit != GlobalUserDataFetcher.getPreferredMeasureUnit(PersonCenterActivity.this)) {
                    PersonCenterActivity.this.mMeasureUnit = GlobalUserDataFetcher.getPreferredMeasureUnit(PersonCenterActivity.this);
                    if (PersonCenterActivity.this.mMeasureUnit == EnumMeasureUnit.Imperial) {
                        int totalIn = (int) MeasureTransform.cm2Inch(PersonCenterActivity.this.mUserInfo.getHeight());
                        int inch = totalIn % 12;
                        int feet = totalIn / 12;
                        PersonCenterActivity.this.settingHeight.setMessageText(String.format("%dft%din", new Object[]{Integer.valueOf(feet), Integer.valueOf(inch)}));
                    } else {
                        PersonCenterActivity.this.settingHeight.setMessageText(((int) PersonCenterActivity.this.mUserInfo.getHeight()) + "cm");
                    }
                }
                PersonCenterActivity.this.modified = true;
            }
        });
        this.weightDialog.show();
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.settings_birthday) {
            setBirthday(view);
        } else if (i == R.id.settings_gender) {
            setGender(view);
        } else if (i == R.id.settings_height) {
            setHeight(view);
        } else if (i == R.id.settings_weight) {
            setWeight(view);
        } else if (i == R.id.nickname_menu) {
            setNickName(view);
        }
    }

    public void setPortrait(View v) {
        if (this.photoDialog == null) {
            this.mFile = new File(String.format("%s/Zeroner/%d.jpg", new Object[]{Environment.getExternalStorageDirectory(), GlobalUserDataFetcher.getCurrentUid(this)}));
            this.photoDialog = new CompilePhotoDialog(this);
            this.photoDialog.setOnPhotoConfirmListener(this);
        }
        this.photoDialog.show();
    }

    public void setNickName(View v) {
        this.nickNameDialog = new EditTextDialog(this, this.mUserInfo.getNickname());
        this.nickNameDialog.setOnTextConfirmListener(new OnTextConfirmListener() {
            public void OnConfirm(String text) {
                PersonCenterActivity.this.mUserInfo.setNickname(text);
                PersonCenterActivity.this.nickNameView.setMessageText(PersonCenterActivity.this.mUserInfo.getNickname());
                PersonCenterActivity.this.modified = true;
            }
        });
        this.nickNameDialog.show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "requestCode = " + requestCode + "resultCode = " + resultCode + "data = " + (data == null));
        switch (requestCode) {
            case 1:
                startPhotoZoom(Uri.fromFile(this.mFile));
                break;
            case 3:
                if (data != null) {
                    setPicToView(data);
                    break;
                }
                break;
            case 4:
                if (data != null) {
                    startPhotoZoom(data.getData());
                    break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent2 = new Intent("com.android.camera.action.CROP");
        intent2.setDataAndType(uri, "image/*");
        intent2.putExtra("crop", "true");
        intent2.putExtra("scale", true);
        intent2.putExtra("aspectX", 1);
        intent2.putExtra("aspectY", 1);
        intent2.putExtra("outputX", 150);
        intent2.putExtra("outputY", 150);
        intent2.putExtra("return-data", true);
        intent2.putExtra("output", this.imageUri);
        startActivityForResult(intent2, 3);
    }

    private void setPicToView(Intent picdata) {
        try {
            this.bitMap = BitmapFactory.decodeStream(getContentResolver().openInputStream(this.imageUri));
            if (this.bitMap != null) {
                Drawable drawable = new BitmapDrawable(getCroppedRoundBitmap(this.bitMap, CommonUtility.dip2px(this.mContext, 50.0f)));
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.imgTv.setCompoundDrawables(null, null, drawable, null);
                ImageUtility.saveImageToSD(this.mContext, this.mFile.getPath(), this.bitMap, 100);
                uploadPhoto(this.mFile.getPath(), ImageUtility.bitmaptoString(this.bitMap));
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void uploadPhoto(String filePath, final String bitMapStr) {
        if (this.mRetrofit == null) {
            this.mRetrofit = MyRetrofitClient.getAPIRetrofit();
        }
        if (this.mFileService == null) {
            this.mFileService = (FileService) this.mRetrofit.create(FileService.class);
        }
        this.mFileService.uploadImage(GlobalUserDataFetcher.getCurrentUid(this).longValue(), RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath))).enqueue(new Callback<UploadPhotoCode>() {
            public void onResponse(Call<UploadPhotoCode> call, Response<UploadPhotoCode> response) {
                if (response == null || response.body() == null) {
                    PersonCenterActivity.this.raiseErrorNotice(PersonCenterActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
                    return;
                }
                switch (((UploadPhotoCode) response.body()).getRetCode()) {
                    case 0:
                        PersonCenterActivity.this.mUserInfo.setPortrait_url(((UploadPhotoCode) response.body()).getUrl());
                        PersonCenterActivity.this.mUserInfo.setPortraitImgStr(bitMapStr);
                        PersonCenterActivity.this.modified = true;
                        return;
                    default:
                        PersonCenterActivity.this.raiseErrorNotice(PersonCenterActivity.this.getString(R.string.unkown_error, new Object[]{String.valueOf(((UploadPhotoCode) response.body()).getRetCode())}));
                        return;
                }
            }

            public void onFailure(Call<UploadPhotoCode> call, Throwable t) {
                PersonCenterActivity.this.raiseErrorNotice(PersonCenterActivity.this.getString(R.string.unkown_error, new Object[]{"network error"}));
            }
        });
    }

    public void OnPhotoConfirm(int chose) {
        try {
            if (this.mFile.exists()) {
                this.mFile.delete();
            }
            this.mFile.createNewFile();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
        this.imageUri = Uri.fromFile(this.mFile);
        if (chose == 0) {
            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
            intent2.putExtra("output", this.imageUri);
            startActivityForResult(intent2, 1);
            return;
        }
        Intent data = new Intent("android.intent.action.PICK", null);
        data.setDataAndType(Media.EXTERNAL_CONTENT_URI, "image/*");
        this.intent.putExtra("output", this.imageUri);
        startActivityForResult(data, 4);
    }

    /* access modifiers changed from: private */
    public void postHealthyWeight() {
        if (this.firstWeight == this.mUserInfo.getWeight()) {
            postHealthyNickName();
            return;
        }
        if (this.mUserService == null) {
            this.mUserService = (UserService) this.mRetrofit.create(UserService.class);
        }
        WeightSend t_weight = new WeightSend();
        long uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
        t_weight.setUid(uid);
        t_weight.setWeight(this.mUserInfo.getWeight());
        t_weight.setData_from("手动录入");
        t_weight.setRecord_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        t_weight.setHeight((int) this.mUserInfo.getHeight());
        t_weight.setGender(this.mUserInfo.getGender());
        List<WeightSend> weights = new ArrayList<>();
        weights.add(t_weight);
        Call<ReturnCode> call = this.mUserService.postMyWeightData(uid, weights);
        showLoadingDialog();
        call.enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
                PersonCenterActivity.this.postHealthyNickName();
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
                PersonCenterActivity.this.postHealthyNickName();
            }
        });
    }

    /* access modifiers changed from: private */
    public void postHealthyNickName() {
        AccountProfile profile = new AccountProfile();
        profile.setUid(GlobalUserDataFetcher.getCurrentUid(this).longValue());
        profile.setNickname(this.mUserInfo.getNickname());
        Call<ReturnCode> call = this.mUserService.postNickNameProfile(profile);
        showLoadingDialog();
        call.enqueue(new Callback<ReturnCode>() {
            public void onResponse(Call<ReturnCode> call, Response<ReturnCode> response) {
            }

            public void onFailure(Call<ReturnCode> call, Throwable t) {
            }
        });
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != 4) {
            return super.dispatchKeyEvent(event);
        }
        saveDataAndExit();
        return true;
    }
}
