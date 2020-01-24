package com.iwown.my_module.feedback;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.entity.MyMedia;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.eventbus.FeedbackServiceEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.my_module.MyInitUtils;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.feedback.adapter.TalkRecyclerAdapter;
import com.iwown.my_module.feedback.adapter.TalkRecyclerAdapter.OnItemClickListener;
import com.iwown.my_module.feedback.contract.FeedbackContract.FeedbackView;
import com.iwown.my_module.feedback.data.TB_feedback;
import com.iwown.my_module.feedback.data.TalkMsgEntity;
import com.iwown.my_module.feedback.network.FeedbackService;
import com.iwown.my_module.feedback.network.request.UserSend;
import com.iwown.my_module.feedback.network.response.AnswerCode;
import com.iwown.my_module.feedback.network.response.AnswerCustomCode;
import com.iwown.my_module.feedback.network.response.ImageCode;
import com.iwown.my_module.feedback.network.response.QuestionCode;
import com.iwown.my_module.feedback.network.response.QuestionData;
import com.iwown.my_module.feedback.network.response.SolveCode;
import com.iwown.my_module.feedback.presenter.FeedbackPresenter;
import com.iwown.my_module.feedback.util.AndroidBug5497Workaround;
import com.iwown.my_module.feedback.util.AndroidBug5497Workaround.CallBack;
import com.iwown.my_module.feedback.util.LocaleUtil;
import com.iwown.my_module.feedback.util.OperationSql;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.utility.StatusbarHelper;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedbackActivity extends BaseActivity implements CallBack, OnItemClickListener, FeedbackView {
    private final int REQUEST_CODE = 4;
    private String brand;
    /* access modifiers changed from: private */
    public EditText editText;
    private FeedbackPresenter feedbackPresenter;
    /* access modifiers changed from: private */
    public FeedbackService feedbackService;
    private boolean isEditMove = true;
    /* access modifiers changed from: private */
    public boolean isShowKey = false;
    private int keyboardHeight = 0;
    /* access modifiers changed from: private */
    public List<TalkMsgEntity> listDataArrays;
    Handler mHandler;
    OnGlobalLayoutListener mLayoutChangeListener = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            Rect r = new Rect();
            FeedbackActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            int heightDifference = FeedbackActivity.this.screenHeight - (r.bottom - r.top);
            if (heightDifference > FeedbackActivity.this.screenHeight / 4) {
                FeedbackActivity.this.editText.animate().translationY((float) (-heightDifference)).setDuration(0).start();
            } else {
                FeedbackActivity.this.editText.animate().translationY(0.0f).start();
            }
        }
    };
    private Retrofit mRetrofit;
    private TextView mService_tip;
    /* access modifiers changed from: private */
    public String network;
    /* access modifiers changed from: private */
    public OperationSql operationSql;
    private List<TalkMsgEntity> questionArrays;
    private List<QuestionData> questionData;
    /* access modifiers changed from: private */
    public int screenHeight = 0;
    private ArrayList<MyMedia> select = new ArrayList<>();
    private ImageView selectImg;
    private LinearLayout sendLay;
    /* access modifiers changed from: private */
    public ImageView sendView;
    private int tableHeight = 0;
    /* access modifiers changed from: private */
    public TalkRecyclerAdapter talkAdapter;
    private RecyclerView talkRecycler;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if ("".equals(s.toString().trim())) {
                FeedbackActivity.this.sendView.setEnabled(false);
            } else {
                FeedbackActivity.this.sendView.setEnabled(true);
            }
        }
    };
    long uid = 0;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_feedback_main);
        setTitleText(getString(R.string.my_module_feedback_title));
        setLeftBackTo();
        if (AppConfigUtil.isHealthy()) {
            this.network = Constants.MSG_UNKNOWN_ERROR;
        } else {
            this.network = "unknown Error";
        }
        this.brand = AppConfigUtil.brand;
        initView();
        initData();
        this.mHandler = new Handler(Looper.getMainLooper());
        AndroidBug5497Workaround.assistActivity((Activity) this, (CallBack) this);
        MyInitUtils.getInstance().initInstabug();
        postUserInfo();
        this.feedbackPresenter = new FeedbackPresenter(this);
        EventBus.getDefault().post(new FeedbackServiceEvent(2));
    }

    private void initView() {
        this.talkRecycler = (RecyclerView) findViewById(R.id.talk_list);
        this.editText = (EditText) findViewById(R.id.talk_send_edit);
        this.sendView = (ImageView) findViewById(R.id.talk_send_img);
        this.sendLay = (LinearLayout) findViewById(R.id.send_layout);
        this.selectImg = (ImageView) findViewById(R.id.feedback_select_img);
        this.sendView.setEnabled(false);
        this.talkRecycler.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.editText.addTextChangedListener(this.textWatcher);
        this.sendView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!"".equals(FeedbackActivity.this.editText.getText().toString().trim())) {
                    FeedbackActivity.this.addUserQuestion();
                }
            }
        });
        this.talkRecycler.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (FeedbackActivity.this.isShowKey) {
                    FeedbackActivity.this.isShowKey = false;
                }
                return false;
            }
        });
        this.selectImg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, PickerActivity.class);
                intent.putExtra(PickerConfig.SELECT_MODE, 101);
                intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9);
                FeedbackActivity.this.startActivityForResult(intent, 4);
            }
        });
    }

    /* access modifiers changed from: private */
    public void addUserQuestion() {
        final String msd = this.editText.getText().toString().trim();
        addTalkList(msd, 0, new Long(0), true);
        this.editText.setText("");
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                FeedbackActivity.this.smoothScrollToLast();
                FeedbackActivity.this.getNetAnswerCustom(msd, 1);
            }
        }, 400);
    }

    /* access modifiers changed from: private */
    public void smoothScrollToLast() {
        this.talkAdapter.notifyDataSetChanged();
        if (this.listDataArrays.size() > 0) {
            this.talkRecycler.smoothScrollToPosition(this.listDataArrays.size() - 1);
        }
    }

    private void initData() {
        this.operationSql = new OperationSql();
        initNetWork();
        this.listDataArrays = new ArrayList();
        this.questionArrays = new ArrayList();
        this.questionData = new ArrayList();
        getHistoryTalk();
        this.uid = GlobalUserDataFetcher.getCurrentUid(this).longValue();
        this.talkAdapter = new TalkRecyclerAdapter(this, this.listDataArrays, this.questionArrays);
        this.talkAdapter.setOnItemClickListener(this);
        this.talkRecycler.setAdapter(this.talkAdapter);
        getNetQuestion();
        this.screenHeight = getHasVirtualKey() - getNoHasVirtualKey() > 0 ? getHasVirtualKey() : getNoHasVirtualKey();
    }

    private void initNetWork() {
        this.mRetrofit = MyRetrofitClient.getFeedbackAPIRetrofit();
        this.feedbackService = (FeedbackService) this.mRetrofit.create(FeedbackService.class);
    }

    private void getHistoryTalk() {
        List<TB_feedback> list = this.operationSql.getHistory();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SpannableString srcText = new SpannableString(((TB_feedback) list.get(i)).getMessage());
                if (((TB_feedback) list.get(i)).getMsg_type() == 5) {
                    srcText.setSpan(new UnderlineSpan(), 0, srcText.length(), 33);
                }
                TalkMsgEntity msgEntity = new TalkMsgEntity(((TB_feedback) list.get(i)).getDate(), srcText, ((TB_feedback) list.get(i)).getMsg_type(), ((TB_feedback) list.get(i)).getId());
                if (((TB_feedback) list.get(i)).getMsg_type() != 100) {
                    this.listDataArrays.add(msgEntity);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.operationSql != null) {
            this.operationSql.deleteTbFeedback();
        }
        setResult(0);
    }

    public void back() {
        super.back();
    }

    private void showInputMethod(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        if (isShow) {
            imm.toggleSoftInput(0, 2);
        } else {
            imm.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        }
    }

    public int getNoHasVirtualKey() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    private int getHasVirtualKey() {
        int dpi = 0;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[]{DisplayMetrics.class}).invoke(display, new Object[]{dm});
            return dm.heightPixels;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return dpi;
        }
    }

    private void howShowView(boolean isHidden) {
        if (isDestroyed()) {
            return;
        }
        if (!isHidden) {
            if (this.keyboardHeight == 0) {
                Rect r = new Rect();
                this.screenHeight = getHasVirtualKey() - getNoHasVirtualKey() > 0 ? getHasVirtualKey() : getNoHasVirtualKey();
                KLog.d("whiaassd: screenHeight: " + this.screenHeight + " r.height(): " + r.height() + "statusBarHeight: " + StatusbarHelper.getStatusBarHeight() + " --- " + getNoHasVirtualKey() + " -- " + getHasVirtualKey());
                this.keyboardHeight = ((this.screenHeight - r.height()) - StatusbarHelper.getStatusBarHeight()) - Math.abs(getNoHasVirtualKey() - getHasVirtualKey());
            }
            this.editText.setFocusable(false);
            this.editText.setFocusableInTouchMode(false);
            this.editText.requestFocus();
            this.isEditMove = true;
            this.sendLay.setPadding(CommonUtility.dip2px(this, 10.0f), 10, CommonUtility.dip2px(this, 10.0f), (this.keyboardHeight - this.tableHeight) + 2);
            KLog.d("whiaassd: padding is: " + ((this.keyboardHeight - this.tableHeight) + 2));
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    FeedbackActivity.this.editText.setFocusable(true);
                    FeedbackActivity.this.editText.setFocusableInTouchMode(true);
                    FeedbackActivity.this.editText.requestFocus();
                }
            }, 700);
            return;
        }
        this.sendLay.setPadding(CommonUtility.dip2px(this, 10.0f), 0, CommonUtility.dip2px(this, 10.0f), 0);
        this.editText.setFocusable(false);
        this.editText.setFocusableInTouchMode(false);
        this.editText.requestFocus();
    }

    public void onSoftKeyboardShow() {
        KLog.e("软键盘弹出来了");
        this.isShowKey = true;
    }

    public void onSoftKeyboardHide() {
        this.isShowKey = false;
        KLog.e("软键盘隐藏了");
    }

    /* access modifiers changed from: private */
    public void addTalkList(String msg, int talkType, Long chatRecordId, boolean isReshUI) {
        TB_feedback feedback = this.operationSql.getNewTalkSql(msg, talkType, chatRecordId);
        if (!isDestroyed() && feedback != null) {
            SpannableString srcText = new SpannableString(msg);
            if (talkType == 5) {
                srcText.setSpan(new UnderlineSpan(), 0, srcText.length(), 33);
            }
            TalkMsgEntity msgEntity = new TalkMsgEntity(feedback.getDate(), srcText, talkType, feedback.getId());
            if (chatRecordId == null) {
                msgEntity.setChatRecordId(0);
            } else {
                msgEntity.setChatRecordId(chatRecordId.longValue());
            }
            this.listDataArrays.add(msgEntity);
            if (isReshUI) {
                smoothScrollToLast();
            }
        }
    }

    public void onAnswerClick(int viewType, int position, String answer, String message) {
        int solve;
        KLog.e("点击的是: viewType: " + viewType + " position: " + position + " answer: " + answer);
        if (viewType == 100) {
            addTalkList(message, 0, new Long(0), true);
            getNetAnswer(answer);
        } else if (viewType == 2) {
            long retId = ((TalkMsgEntity) this.listDataArrays.get(position)).getChatRecordId();
            if ("0".equals(answer)) {
                this.operationSql.updateTbFeedback(((TalkMsgEntity) this.listDataArrays.get(position)).getSqlId(), 3);
                ((TalkMsgEntity) this.listDataArrays.get(position)).setMegType(3);
                addTalkList(getString(R.string.feedback_answer_solve), 1, Long.valueOf(retId), true);
                solve = 1;
            } else {
                this.operationSql.updateTbFeedback(((TalkMsgEntity) this.listDataArrays.get(position)).getSqlId(), 1);
                ((TalkMsgEntity) this.listDataArrays.get(position)).setMegType(1);
                addTalkList(getString(R.string.feedback_unsolved_msg1), 4, Long.valueOf(retId), true);
                solve = 2;
            }
            upNetSolved(retId, solve);
        } else if (viewType == 4) {
            initInstabug();
        }
    }

    public void imgSendAgain(String path, int mediaType, int listPosition) {
        if (this.listDataArrays != null && listPosition < this.listDataArrays.size()) {
            int position = listPosition;
            ((TalkMsgEntity) this.listDataArrays.get(listPosition)).setSend(true);
            this.talkAdapter.notifyItemChanged(position);
        }
        if (this.feedbackPresenter != null) {
            this.feedbackPresenter.uploadImgOrVideo(path, mediaType, this.uid, listPosition);
        }
    }

    private void getNetQuestion() {
        String deviceType = ModuleRouteDeviceInfoService.getInstance().getDevicemodel();
        if (TextUtils.isEmpty(deviceType)) {
            deviceType = "";
        }
        this.feedbackService.getQuestionRepo(deviceType, this.brand, LocaleUtil.getLanguage()).enqueue(new Callback<QuestionCode>() {
            public void onResponse(Call<QuestionCode> call, Response<QuestionCode> response) {
                if (FeedbackActivity.this.isDestroyed()) {
                    return;
                }
                if (response == null || response.body() == null) {
                    if (response != null) {
                        FeedbackActivity.this.showError(response.code());
                    } else {
                        FeedbackActivity.this.showError(0);
                    }
                } else if (((QuestionCode) response.body()).getRetCode() == 0) {
                    String msg = new Gson().toJson((Object) ((QuestionCode) response.body()).getData());
                    KLog.d("feedback: 获取到的问题: " + msg);
                    TB_feedback feedback = FeedbackActivity.this.operationSql.getNewTalkSql(msg, 100, new Long(0));
                    if (feedback != null) {
                        TalkMsgEntity msgEntity = new TalkMsgEntity(feedback.getDate(), new SpannableString(msg), 100, feedback.getId());
                        if (((QuestionCode) response.body()).getData() != null && ((QuestionCode) response.body()).getData().size() > 5) {
                            msgEntity.setFirstType(2);
                        }
                        FeedbackActivity.this.listDataArrays.add(msgEntity);
                        FeedbackActivity.this.smoothScrollToLast();
                    }
                } else {
                    FeedbackActivity.this.showError(((QuestionCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<QuestionCode> call, Throwable t) {
                if (!FeedbackActivity.this.isDestroyed()) {
                    FeedbackActivity.this.showError(-1);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void addNetAnswer(int retCode, Long recordId, String url, int isShowThumb, String answer) {
        if (retCode == 0) {
            if (isShowThumb == 1) {
                addTalkList(answer, 2, recordId, true);
            } else if (isShowThumb == 2) {
                addTalkList(answer, 4, recordId, true);
            } else {
                addTalkList(answer, 1, recordId, true);
            }
            if (!TextUtils.isEmpty(url)) {
                addTalkList(url, 5, recordId, true);
                return;
            }
            return;
        }
        addTalkList(this.network + recordId, 1, new Long(0), true);
    }

    private void getNetAnswer(String answerCode) {
        this.feedbackService.getAnswerRepo(answerCode, this.uid, this.brand).enqueue(new Callback<AnswerCode>() {
            public void onResponse(Call<AnswerCode> call, Response<AnswerCode> response) {
                if (FeedbackActivity.this.isDestroyed()) {
                    return;
                }
                if (response == null || response.body() == null) {
                    FeedbackActivity.this.addTalkList(FeedbackActivity.this.network, 1, new Long(0), true);
                    return;
                }
                AnswerCode answerCode = (AnswerCode) response.body();
                String answer = answerCode.getData().getAnswer().getAnswer();
                int isShowThumb = answerCode.getData().getIsShowThumb();
                FeedbackActivity.this.addNetAnswer(answerCode.getRetCode(), answerCode.getChatRecordId(), answerCode.getData().getAnswer().getUrl(), isShowThumb, answer);
            }

            public void onFailure(Call<AnswerCode> call, Throwable t) {
                if (!FeedbackActivity.this.isDestroyed()) {
                    FeedbackActivity.this.addTalkList(FeedbackActivity.this.network, 1, new Long(0), true);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void getNetAnswerCustom(String question, final int contentType) {
        Call<AnswerCustomCode> call = this.feedbackService.getAnswerCustomRepo(question, this.uid, this.brand, contentType);
        KLog.e("no2855feedback:准备手动获取到的uid： " + this.uid + "问题: " + question);
        call.enqueue(new Callback<AnswerCustomCode>() {
            public void onResponse(Call<AnswerCustomCode> call, Response<AnswerCustomCode> response) {
                if (!FeedbackActivity.this.isDestroyed() && contentType == 1) {
                    if (response == null || response.body() == null) {
                        FeedbackActivity.this.addTalkList(FeedbackActivity.this.network, 1, new Long(0), true);
                        return;
                    }
                    AnswerCustomCode answerCode = (AnswerCustomCode) response.body();
                    String answer = answerCode.getData().getAnswer().getAnswer();
                    int isShowThumb = answerCode.getData().getIsShowThumb();
                    FeedbackActivity.this.addNetAnswer(answerCode.getRetCode(), answerCode.getData().getChatRecordId(), answerCode.getData().getAnswer().getUrl(), isShowThumb, answer);
                }
            }

            public void onFailure(Call<AnswerCustomCode> call, Throwable t) {
                if (!FeedbackActivity.this.isDestroyed() && contentType == 1) {
                    FeedbackActivity.this.addTalkList(FeedbackActivity.this.network, 1, new Long(0), true);
                }
            }
        });
    }

    private void upNetSolved(long chatRecordId, int answer) {
        this.feedbackService.solvedRepo(chatRecordId, UserConfig.getInstance().getNewUID(), answer).enqueue(new Callback<SolveCode>() {
            public void onResponse(Call<SolveCode> call, Response<SolveCode> response) {
                if (response != null && response.body() != null) {
                    KLog.i("feedback:用户提交反馈结果： " + ((SolveCode) response.body()).getRetCode());
                }
            }

            public void onFailure(Call<SolveCode> call, Throwable t) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void showError(int code) {
        if (!isDestroyed()) {
            Toast.makeText(this, this.network + code, 0).show();
            if (this.listDataArrays.size() > 0) {
                this.talkRecycler.smoothScrollToPosition(this.listDataArrays.size() - 1);
            }
        }
    }

    public void initInstabug() {
        KLog.d("feedback 获取到的邮箱: " + new PreferenceUtility(this).fetchStrValueWithKey("email"));
    }

    private void postUserInfo() {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                UserSend send = null;
                try {
                    send = FeedbackActivity.this.operationSql.getUserSend(FeedbackActivity.this);
                    KLog.d("feedback->发送个人信息 " + new Gson().toJson((Object) send));
                } catch (NameNotFoundException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                FeedbackActivity.this.feedbackService.upUserInfoRepo(send.getUid(), send.getApp(), send.getAppVersion(), send.getPhone(), send.getPhoneVersion(), send.getPhoneSystem(), send.getDevice(), send.getDeviceVersion(), send.getCountry(), send.getCity(), send.getBrand()).enqueue(new Callback<RetCode>() {
                    public void onResponse(Call<RetCode> call, Response<RetCode> response) {
                    }

                    public void onFailure(Call<RetCode> call, Throwable t) {
                    }
                });
            }
        }, 2000);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == 19901026) {
            ArrayList<Media> mSelect = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            Gson gson = new Gson();
            for (int i = 0; i < mSelect.size(); i++) {
                MyMedia myMedia = new MyMedia();
                if (((Media) mSelect.get(i)).mediaType == 3) {
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(((Media) mSelect.get(i)).path);
                    Bitmap bitmap = mmr.getFrameAtTime();
                    myMedia.setMedia((Media) mSelect.get(i), bitmap.getWidth(), bitmap.getHeight());
                } else {
                    myMedia.setMedia((Media) mSelect.get(i));
                }
                this.select.add(myMedia);
                if (i == mSelect.size() - 1) {
                    this.talkAdapter.setSelectImg(this.select);
                    addTalkList(gson.toJson((Object) myMedia), 6, Long.valueOf(0), true);
                } else {
                    addTalkList(gson.toJson((Object) myMedia), 6, Long.valueOf(0), false);
                }
                KLog.e("no2855-->大小" + ((Media) mSelect.get(i)).size);
                this.feedbackPresenter.uploadImgOrVideo(((Media) mSelect.get(i)).path, ((Media) mSelect.get(i)).mediaType, this.uid, this.listDataArrays.size() - 1);
            }
        }
    }

    public void imgVideoCode(ImageCode imageCode, int contentType) {
        getNetAnswerCustom(imageCode.getUrl(), contentType);
    }

    public void imgVideoUpFail(int listPosition) {
        if (this.listDataArrays != null && listPosition < this.listDataArrays.size()) {
            final int position = listPosition;
            KLog.d("no2855上传失败的图片： " + listPosition);
            ((TalkMsgEntity) this.listDataArrays.get(listPosition)).setSend(false);
            this.talkRecycler.post(new Runnable() {
                public void run() {
                    FeedbackActivity.this.talkAdapter.notifyItemChanged(position);
                }
            });
        }
    }
}
