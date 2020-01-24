package com.iwown.my_module.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.iwown.data_link.consts.UserConst.DrViva;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.eventbus.FeedbackServiceEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.lib_common.fragment.SupportFragment;
import com.iwown.my_module.R;
import com.iwown.my_module.contract.IvServiceBackContract.IvServicebackView;
import com.iwown.my_module.contract.IvServiceBackPresenter;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.feedback.FeedbackActivity;
import com.iwown.my_module.healthy.HealthySharedUtil;
import com.iwown.my_module.healthy.activity.IwownShopActivity;
import com.iwown.my_module.healthy.bbs.BBSActivity;
import com.iwown.my_module.healthy.bbs.BBSRegisterActivity;
import com.iwown.my_module.settingactivity.AppSettingActivity;
import com.iwown.my_module.settingactivity.CustomWebViewActivity;
import com.iwown.my_module.settingactivity.GoalSettingActivity;
import com.iwown.my_module.settingactivity.Link3rdPartyActivity;
import com.iwown.my_module.settingactivity.PersonCenterActivity;
import com.iwown.my_module.utility.Constants;
import com.iwown.my_module.utility.ScreenUtility;
import com.iwown.my_module.widget.NewSelectinfoView;
import com.iwown.my_module.widget.SelectinfoRightView;
import com.iwown.my_module.widget.SelectinfoView;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class ProfileFragment extends SupportFragment implements OnClickListener, IvServicebackView {
    private static final String TAG = "ProfileFragment";
    LinearLayout enterFeedback;
    LinearLayout facebookSpliterView;
    TextView feedbackNewTxt;
    ImageView feedbackRobot;
    private boolean isHealthy = false;
    private IvServiceBackPresenter ivServiceBackPresenter;
    private List<String> localeList;
    LinearLayout mActionBarLayout;
    NewSelectinfoView mAppSettingMenu;
    NewSelectinfoView mBonusView;
    private Context mContext;
    ImageView mEditImv;
    TextView mEditLabel;
    NewSelectinfoView mFacebookMenu;
    SelectinfoView mFacebookView;
    RelativeLayout mFullArea;
    NewSelectinfoView mGoalSettingMenu;
    NewSelectinfoView mGuide101Menu;
    LinearLayout mGuideLayout;
    SelectinfoRightView mHelpMenu;
    NewSelectinfoView mLinkMenu;
    private PreferenceUtility mPrefUtil;
    NewSelectinfoView mProductsMenu;
    TextView mProfileImage;
    TextView mProfileNameTextView;
    ScrollView mScrollMenuArea;
    TextView mTitleTextView;
    LinearLayout mTwitterSpliter;
    SelectinfoView mTwitterView;
    LinearLayout mYoutubeSpliter;
    SelectinfoView mYoutubeView;
    long uid;
    UserInfoEntity userInfo;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        this.mPrefUtil = new PreferenceUtility(this.mContext);
        this.localeList = new ArrayList();
        this.localeList.add("us");
        this.localeList.add("gb");
        this.localeList.add("pl");
        this.localeList.add("kr");
        this.localeList.add("jp");
        this.localeList.add("de");
        this.localeList.add("ca");
        this.localeList.add("br");
        this.isHealthy = AppConfigUtil.isHealthy(getContext());
        this.ivServiceBackPresenter = new IvServiceBackPresenter(this);
        this.ivServiceBackPresenter.getServiceCallBack(this.uid);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_module_fragment_profile, container, false);
        this.mActionBarLayout = (LinearLayout) view.findViewById(R.id.titlebar_layout);
        this.mFullArea = (RelativeLayout) view.findViewById(R.id.profile_fragment_content);
        this.mGuideLayout = (LinearLayout) view.findViewById(R.id.guide_layout);
        this.mGuideLayout.setOnClickListener(this);
        this.mProfileImage = (TextView) view.findViewById(R.id.person_photo_my);
        this.mProfileImage.setOnClickListener(this);
        this.mTitleTextView = (TextView) view.findViewById(R.id.mine_title);
        this.mTitleTextView.setOnClickListener(this);
        this.mProfileNameTextView = (TextView) view.findViewById(R.id.profile_name);
        this.mProfileNameTextView.setOnClickListener(this);
        this.enterFeedback = (LinearLayout) view.findViewById(R.id.enter_feedback);
        this.enterFeedback.setOnClickListener(this);
        this.mEditLabel = (TextView) view.findViewById(R.id.edit_label);
        this.mEditLabel.setOnClickListener(this);
        this.mEditImv = (ImageView) view.findViewById(R.id.image_to_user_center);
        this.feedbackRobot = (ImageView) view.findViewById(R.id.feedback_robot);
        this.feedbackNewTxt = (TextView) view.findViewById(R.id.feedback_new_txt);
        this.feedbackNewTxt.setVisibility(4);
        this.mEditImv.setOnClickListener(this);
        this.mGoalSettingMenu = (NewSelectinfoView) view.findViewById(R.id.profile_goal_setting_view);
        this.mGoalSettingMenu.setOnClickListener(this);
        this.mGuide101Menu = (NewSelectinfoView) view.findViewById(R.id.profile_101guide_view);
        this.mGuide101Menu.setOnClickListener(this);
        this.mHelpMenu = (SelectinfoRightView) view.findViewById(R.id.profile_help_view);
        this.mHelpMenu.setOnClickListener(this);
        this.mProductsMenu = (NewSelectinfoView) view.findViewById(R.id.profile_products_view);
        this.mProductsMenu.setOnClickListener(this);
        this.mFacebookMenu = (NewSelectinfoView) view.findViewById(R.id.profile_facebook_view);
        this.mFacebookMenu.setOnClickListener(this);
        this.mLinkMenu = (NewSelectinfoView) view.findViewById(R.id.profile_link_view);
        this.mLinkMenu.setOnClickListener(this);
        this.mAppSettingMenu = (NewSelectinfoView) view.findViewById(R.id.profile_setting_view);
        this.mAppSettingMenu.setOnClickListener(this);
        this.mScrollMenuArea = (ScrollView) view.findViewById(R.id.mine_scroll_menu);
        this.mActionBarLayout.setPadding(0, ScreenUtility.getStatusBarHeight(), 0, 0);
        this.facebookSpliterView = (LinearLayout) view.findViewById(R.id.facebook_splitter);
        this.mYoutubeView = (SelectinfoView) view.findViewById(R.id.profile_youtube_view);
        this.mYoutubeView.setOnClickListener(this);
        this.mTwitterView = (SelectinfoView) view.findViewById(R.id.profile_twitter_view);
        this.mTwitterView.setOnClickListener(this);
        this.mFacebookView = (SelectinfoView) view.findViewById(R.id.profile_facebook_view_viva);
        this.mFacebookView.setOnClickListener(this);
        this.mYoutubeSpliter = (LinearLayout) view.findViewById(R.id.youtube_splitter);
        this.mTwitterSpliter = (LinearLayout) view.findViewById(R.id.twitter_splitter);
        this.mBonusView = (NewSelectinfoView) view.findViewById(R.id.profile_bonus_view);
        this.mBonusView.setOnClickListener(this);
        this.mGoalSettingMenu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        this.mGuide101Menu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        this.mHelpMenu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        this.mProductsMenu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        this.mFacebookMenu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        this.mLinkMenu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        this.mAppSettingMenu.setControlTextColor(getResources().getColor(R.color.dark_theme_text_color));
        Log.i(TAG, String.format("---mine guide status:%d", new Object[]{Integer.valueOf(GlobalUserDataFetcher.getMineGuideStatus(getActivity()))}));
        if (GlobalUserDataFetcher.getMineGuideStatus(getActivity()) == 0) {
            this.mGuideLayout.setVisibility(0);
            this.enterFeedback.setVisibility(8);
        } else {
            this.mGuideLayout.setVisibility(8);
            this.enterFeedback.setVisibility(0);
        }
        if (AppConfigUtil.isCustomApp() || AppConfigUtil.isZeronerHealthPro()) {
            this.mProductsMenu.setVisibility(8);
            this.mFacebookMenu.setVisibility(8);
            this.facebookSpliterView.setVisibility(8);
        }
        if (AppConfigUtil.isCustomApp() && !AppConfigUtil.isNanfei_TRAX_GPS()) {
            this.mGuide101Menu.setVisibility(8);
        }
        if (this.isHealthy) {
            this.mProductsMenu.setLaberText("埃微商城");
            this.mFacebookMenu.setLaberText("逛逛论坛");
            this.mFacebookMenu.setTitleImg(R.mipmap.forum_icon3x);
        } else if (AppConfigUtil.isDrviva()) {
            this.mFacebookMenu.setLaberText("Facebook");
        }
        if (AppConfigUtil.isDrviva()) {
            this.mYoutubeView.setVisibility(0);
            this.mYoutubeView.setLeftImagRes(R.mipmap.youtube_3x);
            this.mYoutubeView.setLeftImagVisible(true);
            this.mYoutubeSpliter.setVisibility(0);
            this.mTwitterView.setVisibility(0);
            this.mTwitterView.setLeftImagRes(R.mipmap.twitter_3x);
            this.mTwitterView.setLeftImagVisible(true);
            this.mTwitterSpliter.setVisibility(0);
            this.mFacebookView.setVisibility(0);
            this.mFacebookView.setLeftImagRes(R.mipmap.follow_icon_3x);
            this.mFacebookView.setLeftImagVisible(true);
            this.mFacebookMenu.setVisibility(8);
            if (!TextUtils.isEmpty(this.mPrefUtil.fetchStrValueWithKey(DrViva.FacebookMsg))) {
                this.mFacebookView.setSecondTitle(this.mPrefUtil.fetchStrValueWithKey(DrViva.FacebookMsg));
            }
            if (!TextUtils.isEmpty(this.mPrefUtil.fetchStrValueWithKey(DrViva.YoutubeMsg))) {
                this.mYoutubeView.setSecondTitle(this.mPrefUtil.fetchStrValueWithKey(DrViva.YoutubeMsg));
            }
            if (!TextUtils.isEmpty(this.mPrefUtil.fetchStrValueWithKey(DrViva.TwitterMsg))) {
                this.mTwitterView.setSecondTitle(this.mPrefUtil.fetchStrValueWithKey(DrViva.TwitterMsg));
            }
        } else {
            this.mYoutubeView.setVisibility(8);
            this.mYoutubeSpliter.setVisibility(8);
            this.mTwitterView.setVisibility(8);
            this.mTwitterSpliter.setVisibility(8);
            this.mFacebookView.setVisibility(8);
            this.mFacebookMenu.setVisibility(8);
            this.mBonusView.setVisibility(8);
        }
        return view;
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        this.uid = GlobalUserDataFetcher.getCurrentUid(this.mContext).longValue();
        List<UserInfoEntity> userInfoList = DataSupport.where("uid=?", String.valueOf(this.uid)).find(UserInfoEntity.class);
        if (userInfoList == null || userInfoList.size() == 0) {
            Log.w(TAG, "user info not exist yet");
            this.userInfo = new UserInfoEntity();
        } else {
            this.userInfo = (UserInfoEntity) userInfoList.get(0);
            Log.i(TAG, String.format("nickname:%s, portrait_url:%s", new Object[]{this.userInfo.getNickname(), this.userInfo.getPortrait_url()}));
        }
        if (this.userInfo.getNickname() == null || this.userInfo.getNickname().equals("")) {
            this.mProfileNameTextView.setText(GlobalUserDataFetcher.getEmail(getContext()));
        } else {
            this.mProfileNameTextView.setText(this.userInfo.getNickname());
        }
        if (!TextUtils.isEmpty(this.userInfo.getNickname()) && !this.userInfo.getNickname().equals("(null)")) {
            this.mProfileImage.setText(this.userInfo.getNickname().substring(0, 1));
        } else if (!TextUtils.isEmpty(GlobalUserDataFetcher.getEmail(getContext()))) {
            this.mProfileImage.setText(GlobalUserDataFetcher.getEmail(getContext()).substring(0, 1));
        }
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.person_photo_my || i == R.id.image_to_user_center || i == R.id.profile_name || i == R.id.edit_label) {
            startActivity(new Intent(getActivity(), PersonCenterActivity.class));
        } else if (i == R.id.profile_goal_setting_view) {
            startActivity(new Intent(getActivity(), GoalSettingActivity.class));
        } else if (i == R.id.profile_setting_view) {
            startActivity(new Intent(getActivity(), AppSettingActivity.class));
        } else if (i == R.id.profile_101guide_view) {
            Intent intent = new Intent(getActivity(), CustomWebViewActivity.class);
            if (AppConfigUtil.isCustomApp()) {
                intent.putExtra("url", "https://api4.iwown.com/guide/bracelet/bracelet.html#/zhp");
            } else if (AppConfigUtil.isRussia(getActivity())) {
                intent.putExtra("url", " https://search.iwown.com/guide/bracelet/bracelet.html#/");
            } else if (AppConfigUtil.isIwownFitPro()) {
                intent.putExtra("url", Constants.GUIDE_URL);
            } else if (AppConfigUtil.isZeronerHealthPro()) {
                intent.putExtra("url", "https://api4.iwown.com/guide/bracelet/bracelet.html#/zhp");
            } else if (AppConfigUtil.isHealthy(getContext())) {
                intent.putExtra("url", "https://api2.iwown.com/guide/dist/index.html#/zhushou");
            } else if (AppConfigUtil.isDrviva()) {
                intent.putExtra("url", "https://api4.iwown.com/guide/bracelet/bracelet.html#/drviva");
            }
            intent.putExtra("title", getContext().getString(R.string.profile_101guide));
            startActivity(intent);
        } else if (i == R.id.profile_help_view) {
            startActivityForResult(new Intent(getActivity(), FeedbackActivity.class), 0);
        } else if (i == R.id.profile_products_view) {
            if (this.isHealthy) {
                startActivity(new Intent(getContext(), IwownShopActivity.class));
            } else if (AppConfigUtil.isDrviva()) {
                Intent intent2 = new Intent(getActivity(), CustomWebViewActivity.class);
                intent2.putExtra("url", "http://www.doctorviva.com/");
                intent2.putExtra("title", "Dr. Viva");
                startActivity(intent2);
            } else {
                String url = "http://api6.iwown.com/iwownfit/products-us.html";
                String country = getResources().getConfiguration().locale.getCountry().toUpperCase();
                Log.i(TAG, String.format("****geo locale of phone:%s", new Object[]{country}));
                if (TextUtils.equals("RU", country)) {
                    url = "http://www.iwownfit.com/";
                } else {
                    String locale = UserConfig.getInstance(getActivity()).getCountry();
                    if (locale == null || TextUtils.isEmpty(locale)) {
                        Log.i(TAG, "can't get locale of phone");
                    } else {
                        Log.i(TAG, String.format("****locale of phone:%s", new Object[]{locale}));
                        String locale2 = locale.toLowerCase();
                        if (this.localeList.contains(locale2)) {
                            url = String.format("http://api6.iwown.com/iwownfit/products-%s.html", new Object[]{locale2});
                        }
                    }
                }
                Intent intent3 = new Intent(getActivity(), CustomWebViewActivity.class);
                intent3.putExtra("url", url);
                intent3.putExtra("title", "Products");
                startActivity(intent3);
            }
        } else if (i == R.id.profile_facebook_view) {
            if (!this.isHealthy) {
                Intent intent4 = new Intent(getActivity(), CustomWebViewActivity.class);
                intent4.putExtra("url", Constants.FACEBOOK_URL);
                intent4.putExtra("title", "Follow");
                startActivity(intent4);
                return;
            }
            HealthySharedUtil sharedUtil = new HealthySharedUtil(getContext());
            int bbsType = new HealthySharedUtil(getContext()).getBBsBind();
            String account = sharedUtil.getBBsAccount();
            if (bbsType == 1 && !TextUtils.isEmpty(account)) {
                startActivity(new Intent(this.mContext, BBSActivity.class));
            } else if (bbsType == 2 || bbsType == 3) {
                startActivity(new Intent(this.mContext, BBSRegisterActivity.class));
            } else {
                startActivity(new Intent(this.mContext, BBSRegisterActivity.class));
            }
        } else if (i == R.id.profile_link_view) {
            startActivity(new Intent(getActivity(), Link3rdPartyActivity.class));
        } else if (i == R.id.enter_feedback) {
            startActivity(new Intent(getActivity(), FeedbackActivity.class));
        } else if (i == R.id.guide_layout) {
            this.mGuideLayout.setVisibility(8);
            this.enterFeedback.setVisibility(0);
            GlobalDataUpdater.setMineGuideStatus(getActivity(), 1);
        } else if (i == R.id.profile_facebook_view_viva) {
            Intent intent5 = new Intent(getActivity(), CustomWebViewActivity.class);
            intent5.putExtra("url", "http://bit.ly/drviva");
            intent5.putExtra("title", "Facebook");
            startActivity(intent5);
        } else if (i == R.id.profile_youtube_view) {
            Intent intent6 = new Intent(getActivity(), CustomWebViewActivity.class);
            intent6.putExtra("url", "http://bit.ly/drvivavideo");
            intent6.putExtra("title", "YouTube");
            startActivity(intent6);
        } else if (i == R.id.profile_twitter_view) {
            Intent intent7 = new Intent(getActivity(), CustomWebViewActivity.class);
            intent7.putExtra("url", "http://bit.ly/drvivatwitter");
            intent7.putExtra("title", "Twitter");
            startActivity(intent7);
        } else if (i == R.id.profile_bonus_view) {
            Intent intent8 = new Intent(getActivity(), CustomWebViewActivity.class);
            intent8.putExtra("url", "http://cn.mikecrm.com/UwRxI5i");
            intent8.putExtra("title", "Bonus");
            startActivity(intent8);
        }
    }

    public void getServiceFeedback() {
        if (this.ivServiceBackPresenter != null) {
            if (this.uid == 0 && this.mContext != null) {
                this.uid = GlobalUserDataFetcher.getCurrentUid(this.mContext).longValue();
            }
            this.ivServiceBackPresenter.getServiceCallBack(this.uid);
        }
    }

    public void serviceCallBack() {
        if (this.feedbackRobot != null) {
            this.feedbackRobot.setImageResource(R.mipmap.robot_has_new3x);
            this.feedbackNewTxt.setVisibility(0);
        }
        EventBus.getDefault().post(new FeedbackServiceEvent(1));
    }

    public void resrshRobotView() {
        if (this.feedbackRobot != null) {
            this.feedbackRobot.setImageResource(R.mipmap.robot_no_new3x);
            this.feedbackNewTxt.setVisibility(4);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        KLog.d("no2855--> 没有返回？？？");
        if (this.feedbackRobot != null) {
            this.feedbackRobot.setImageResource(R.mipmap.robot_no_new3x);
            this.feedbackNewTxt.setVisibility(4);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
