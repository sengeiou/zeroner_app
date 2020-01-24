package com.iwown.my_module.settingactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.utility.CommonUtility;
import java.util.Timer;
import java.util.TimerTask;

public class AboutActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public static int click_title_times;
    /* access modifiers changed from: private */
    public static boolean is_title_clicked = false;
    /* access modifiers changed from: private */
    public static int max_click_times = 5;
    private ImageView aboutImg;
    private TextView url;
    private TextView version;

    static /* synthetic */ int access$108() {
        int i = click_title_times;
        click_title_times = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_about);
        initView();
        setTitleText(R.string.about_us_title);
        setLeftBackTo();
    }

    @SuppressLint({"StringFormatInvalid"})
    private void initView() {
        this.version = (TextView) findViewById(R.id.version_name);
        this.url = (TextView) findViewById(R.id.text_url);
        this.aboutImg = (ImageView) findViewById(R.id.about_img);
        this.url.getPaint().setFlags(8);
        this.version.setText(getString(R.string.about_us_app_version, new Object[]{CommonUtility.getClientVersionName(this)}));
        this.url.setAutoLinkMask(15);
        this.url.setMovementMethod(LinkMovementMethod.getInstance());
        this.url.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, CustomWebViewActivity.class);
                intent.putExtra("url", AppConfigUtil.Privacy_Link);
                intent.putExtra("title", AboutActivity.this.getString(R.string.iwown_privacy));
                AboutActivity.this.startActivity(intent);
            }
        });
        this.aboutImg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!AboutActivity.is_title_clicked) {
                    AboutActivity.is_title_clicked = true;
                    AboutActivity.click_title_times = 1;
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            AboutActivity.is_title_clicked = false;
                            AboutActivity.click_title_times = 0;
                        }
                    }, 2000);
                    return;
                }
                AboutActivity.access$108();
                if (AboutActivity.click_title_times == AboutActivity.max_click_times) {
                    AboutActivity.is_title_clicked = false;
                    AboutActivity.click_title_times = 0;
                    AboutActivity.this.startActivity(new Intent(AboutActivity.this, DebugActivity.class));
                }
            }
        });
    }
}
