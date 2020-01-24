package com.iwown.device_module.device_guide;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.R;
import com.iwown.device_module.device_guide.a_interface.IGuideAnimController;
import com.iwown.device_module.device_guide.a_interface.LottieAnimController;
import com.iwown.lib_common.BaseActivity2;
import com.iwown.lib_common.BaseActivity2.ActionOnclickListener;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.views.SlideVpIndicator;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class DeviceGuideAcitvity extends BaseActivity2 {
    private static final int STYLE_I6Hrc = 1;
    private static final int STYLE_I7A = 2;
    private static final int STYLE_R1 = 3;
    /* access modifiers changed from: private */
    public static int guide_style = 2;
    /* access modifiers changed from: private */
    public String[] contents = null;
    private String empty = "  ";
    private List<IGuideAnimController> mGuideAnimControllers = new ArrayList();
    /* access modifiers changed from: private */
    public ViewPager mGuideVp;
    /* access modifiers changed from: private */
    public GuideVpShowHelper mGuideVpShowHelper;
    /* access modifiers changed from: private */
    public TextView mPage_content;
    /* access modifiers changed from: private */
    public TextView mPage_title;
    private ConstraintLayout mRoot;
    /* access modifiers changed from: private */
    public SlideVpIndicator mSlideVpIndicator;
    private int mVp_page_bg;
    /* access modifiers changed from: private */
    public CharSequence[] titles = null;
    /* access modifiers changed from: private */
    public List<View> vp_views = new ArrayList();

    private class GuidePagerAdapter extends PagerAdapter {
        private GuidePagerAdapter() {
        }

        public int getCount() {
            return DeviceGuideAcitvity.this.vp_views.size();
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) DeviceGuideAcitvity.this.vp_views.get(position));
        }

        public Object instantiateItem(ViewGroup view, int position) {
            view.addView((View) DeviceGuideAcitvity.this.vp_views.get(position));
            return DeviceGuideAcitvity.this.vp_views.get(position);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_guide_acitvity);
        getWindow().addFlags(134217728);
        guide_style = getIntent().getIntExtra("guide_style", 2);
        initView();
        initEvent();
    }

    private void initEvent() {
        this.mGuideVp.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                DeviceGuideAcitvity.this.mSlideVpIndicator.setMoveOffset(((float) position) + positionOffset);
            }

            public void onPageSelected(int position) {
                KLog.d("onPageSelected");
                DeviceGuideAcitvity.this.mPage_content.scrollTo(0, 0);
                DeviceGuideAcitvity.this.mGuideVpShowHelper.start(position);
                if (DeviceGuideAcitvity.guide_style == 3) {
                    if (position > 1) {
                        LayoutParams layoutParams = DeviceGuideAcitvity.this.mGuideVp.getLayoutParams();
                        layoutParams.height = DensityUtil.dip2px(DeviceGuideAcitvity.this, 400.0f);
                        DeviceGuideAcitvity.this.mGuideVp.setLayoutParams(layoutParams);
                        DeviceGuideAcitvity.this.mPage_content.setVisibility(8);
                    } else {
                        LayoutParams layoutParams2 = DeviceGuideAcitvity.this.mGuideVp.getLayoutParams();
                        layoutParams2.height = DensityUtil.dip2px(DeviceGuideAcitvity.this, 270.0f);
                        DeviceGuideAcitvity.this.mGuideVp.setLayoutParams(layoutParams2);
                        DeviceGuideAcitvity.this.mPage_content.setVisibility(0);
                        DeviceGuideAcitvity.this.mPage_content.setText(DeviceGuideAcitvity.this.contents[position % DeviceGuideAcitvity.this.contents.length]);
                    }
                    DeviceGuideAcitvity.this.mPage_title.setText(DeviceGuideAcitvity.this.titles[position % DeviceGuideAcitvity.this.titles.length]);
                    return;
                }
                DeviceGuideAcitvity.this.mPage_title.setText(DeviceGuideAcitvity.this.titles[position % DeviceGuideAcitvity.this.titles.length]);
                DeviceGuideAcitvity.this.mPage_content.setText(DeviceGuideAcitvity.this.contents[position % DeviceGuideAcitvity.this.contents.length]);
            }

            public void onPageScrollStateChanged(int state) {
                KLog.d("onPageScrollStateChanged: " + state);
            }
        });
    }

    private void initView() {
        int titlebar_top_color = getResources().getColor(R.color.device_module_guide_acty_titlebar_top);
        int titlebar_bottom_color = getResources().getColor(R.color.device_module_guide_acty_titlebar_bottom);
        int bg_color = getResources().getColor(R.color.device_module_guide_acty_bg_color);
        this.mVp_page_bg = getResources().getColor(R.color.device_module_guide_acty_vp_page_bg);
        setLeftBackTo();
        getTitleBar().setTitle((CharSequence) getString(R.string.device_module_quick_start));
        getTitleBar().setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{titlebar_top_color, titlebar_bottom_color}));
        this.mSlideVpIndicator = (SlideVpIndicator) findViewById(R.id.indicator);
        this.mPage_title = (TextView) findViewById(R.id.page_title);
        this.mPage_content = (TextView) findViewById(R.id.page_content);
        this.mGuideVp = (ViewPager) findViewById(R.id.guide_vp);
        this.mPage_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        setRightText(getString(R.string.device_module_skip), new ActionOnclickListener() {
            public void onclick() {
                DeviceGuideAcitvity.this.finish();
            }
        });
        this.mRoot = (ConstraintLayout) findViewById(R.id.cl_root);
        this.mRoot.setBackgroundColor(bg_color);
        this.mGuideVpShowHelper = new GuideVpShowHelper();
        switch (guide_style) {
            case 2:
                initI7AStyle();
                break;
            case 3:
                initRINStyle();
                break;
        }
        if (this.vp_views.size() > 0) {
            this.mGuideVp.setAdapter(new GuidePagerAdapter());
            this.mGuideVp.setCurrentItem(0);
            this.mPage_title.setText(this.titles[0]);
            this.mPage_content.setText(this.contents[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void initI7AStyle() {
        this.titles = getArray(R.array.dev_guide_titles_i7a);
        this.contents = getArray(R.array.dev_guide_contents_i7a);
        for (int i = 0; i < 6; i++) {
            View view = View.inflate(this, R.layout.device_module_guide_vp_item, null);
            LottieAnimationView lottieAnimationView = (LottieAnimationView) view.findViewById(R.id.lottie_anim_view);
            ImageView imageView = (ImageView) view.findViewById(R.id.gif_iv);
            if (i == 0) {
                LottieAnimController wearControl = new LottieAnimController(lottieAnimationView);
                wearControl.setLottiedAssetsPath("airbnb_wear_guide/").setLottiedJsonName("wear.json");
                this.mGuideAnimControllers.add(wearControl);
            } else if (i == 1) {
                LottieAnimController sceenLightControl = new LottieAnimController(lottieAnimationView);
                sceenLightControl.setLottiedAssetsPath("airbnb_screen_light_guide/").setLottiedJsonName("screen_light.json");
                this.mGuideAnimControllers.add(sceenLightControl);
            } else if (i == 2) {
                LottieAnimController viewDataControl = new LottieAnimController(lottieAnimationView);
                viewDataControl.setLottiedAssetsPath("airbnb_view_data/").setLottiedJsonName("view_data.json");
                this.mGuideAnimControllers.add(viewDataControl);
            } else if (i == 3) {
                LottieAnimController hrMonitorControl = new LottieAnimController(lottieAnimationView);
                hrMonitorControl.setLottiedAssetsPath("airbnb_hr_monitor/").setLottiedJsonName("hr_monitor.json");
                this.mGuideAnimControllers.add(hrMonitorControl);
            } else if (i == 4) {
                LottieAnimController sportModeControl = new LottieAnimController(lottieAnimationView);
                sportModeControl.setLottiedAssetsPath("airbnb_sport_mode/").setLottiedJsonName("sport_mode.json");
                this.mGuideAnimControllers.add(sportModeControl);
            } else if (i == 5) {
                LottieAnimController viewMsgControl = new LottieAnimController(lottieAnimationView);
                viewMsgControl.setLottiedAssetsPath("airbnb_view_msg/").setLottiedJsonName("view_msg.json");
                this.mGuideAnimControllers.add(viewMsgControl);
            }
            this.mGuideVpShowHelper.initAll(this.mGuideAnimControllers);
            this.mRoot.postDelayed(new Runnable() {
                public void run() {
                    DeviceGuideAcitvity.this.mGuideVpShowHelper.start(0);
                }
            }, 800);
            this.vp_views.add(view);
        }
    }

    private void initRINStyle() {
        String[] r1_anims = {"airbnb_r1_image/airbnb_r1_connect/", "airbnb_r1_image/airbnb_r1_wear/", "airbnb_r1_image/airbnb_r1_music/", "airbnb_r1_image/airbnb_r1_call/", "airbnb_r1_image/airbnb_r1_sport/"};
        String[] r1_jsons = getJsons();
        this.titles = initR1titleData();
        this.contents = getArray(R.array.dev_guide_contents_r1);
        this.mSlideVpIndicator.setDot_counts(r1_jsons.length);
        for (int i = 0; i < r1_jsons.length; i++) {
            View view = View.inflate(this, R.layout.device_module_guide_vp_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gif_iv);
            LottieAnimController wearControl = new LottieAnimController((LottieAnimationView) view.findViewById(R.id.lottie_anim_view));
            wearControl.setLottiedAssetsPath(r1_anims[i]).setLottiedJsonName(r1_jsons[i]).setRepeatCount(1);
            this.mGuideAnimControllers.add(wearControl);
            this.mGuideVpShowHelper.initAll(this.mGuideAnimControllers);
            this.mRoot.postDelayed(new Runnable() {
                public void run() {
                    DeviceGuideAcitvity.this.mGuideVpShowHelper.start(0);
                }
            }, 800);
            this.vp_views.add(view);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.mGuideVpShowHelper != null) {
            this.mGuideVpShowHelper.cancelAll();
        }
        if (guide_style == 3) {
            UserConfig.getInstance().setHasGuideR1(true);
            UserConfig.getInstance().save();
        } else if (guide_style == 2) {
            UserConfig.getInstance().setHasGuideI7A(true);
            UserConfig.getInstance().save();
        }
        super.onDestroy();
    }

    private CharSequence[] initR1titleData() {
        String r1_title1 = getResources().getString(R.string.r1_guide_title1);
        String r1_title2 = getResources().getString(R.string.r1_guide_title2);
        String r1_title3 = getResources().getString(R.string.r1_guide_title3);
        CharSequence[] strings = {r1_title1, r1_title2, r1_title3 + this.empty + getResources().getString(R.string.r1_guide_title3_1), r1_title3 + this.empty + getResources().getString(R.string.r1_guide_title4_1), r1_title3 + this.empty + getResources().getString(R.string.r1_guide_title5_1)};
        for (int i = 2; i < strings.length; i++) {
            int end1 = strings[i].toString().indexOf(this.empty);
            strings[i] = getChangeTextSize(strings[i], 0, end1, end1 + this.empty.length(), strings[i].length());
        }
        return strings;
    }

    public CharSequence getChangeTextSize(CharSequence s, int start1, int end1, int start2, int end2) {
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(28, true);
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(16, true);
        builder.setSpan(span1, start1, end1, 33);
        builder.setSpan(span2, start2, end2, 33);
        return builder;
    }

    public String getLanguage() {
        return getResources().getConfiguration().locale.getLanguage();
    }

    private String[] getJsons() {
        if (getLanguage().equals("zh")) {
            return new String[]{"airbnb_r1_json/r1_connect.json", "airbnb_r1_json/r1_wear.json", "airbnb_r1_json/r1_music.json", "airbnb_r1_json/r1_call.json", "airbnb_r1_json/r1_sport.json"};
        }
        return new String[]{"airbnb_r1_json/r1_connect.json", "airbnb_r1_json/r1_wear.json", "airbnb_r1_json_en/r1_music.json", "airbnb_r1_json_en/r1_call.json", "airbnb_r1_json_en/r1_sport.json"};
    }
}
