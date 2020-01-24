package com.dmcbig.mediapicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.view.PreviewFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreviewActivity extends AppCompatActivity implements OnClickListener, OnPageChangeListener {
    TextView bar_title;
    View bottom;
    ImageView check_image;
    LinearLayout check_layout;
    Button done;
    ArrayList<Media> preRawList;
    ArrayList<Media> selects;
    View top;
    ViewPager viewpager;

    public class AdapterFragment extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public AdapterFragment(FragmentManager fm, List<Fragment> mFragments2) {
            super(fm);
            this.mFragments = mFragments2;
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragments.get(position);
        }

        public int getCount() {
            return this.mFragments.size();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_main);
        findViewById(R.id.btn_back).setOnClickListener(this);
        this.check_image = (ImageView) findViewById(R.id.check_image);
        this.check_layout = (LinearLayout) findViewById(R.id.check_layout);
        this.check_layout.setOnClickListener(this);
        this.bar_title = (TextView) findViewById(R.id.bar_title);
        this.done = (Button) findViewById(R.id.done);
        this.done.setOnClickListener(this);
        this.top = findViewById(R.id.top);
        this.bottom = findViewById(R.id.bottom);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.preRawList = getIntent().getParcelableArrayListExtra(PickerConfig.PRE_RAW_LIST);
        this.selects = new ArrayList<>();
        this.selects.addAll(this.preRawList);
        setView(this.preRawList);
    }

    /* access modifiers changed from: 0000 */
    public void setView(ArrayList<Media> default_list) {
        setDoneView(default_list.size());
        this.bar_title.setText("1/" + this.preRawList.size());
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        Iterator it = default_list.iterator();
        while (it.hasNext()) {
            fragmentArrayList.add(PreviewFragment.newInstance((Media) it.next(), ""));
        }
        this.viewpager.setAdapter(new AdapterFragment(getSupportFragmentManager(), fragmentArrayList));
        this.viewpager.addOnPageChangeListener(this);
    }

    /* access modifiers changed from: 0000 */
    public void setDoneView(int num1) {
        this.done.setText(getString(R.string.done) + "(" + num1 + "/" + getIntent().getIntExtra(PickerConfig.MAX_SELECT_COUNT, 40) + ")");
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            done(this.selects, PickerConfig.RESULT_UPDATE_CODE);
        } else if (id == R.id.done) {
            done(this.selects, PickerConfig.RESULT_CODE);
        } else if (id == R.id.check_layout) {
            Media media = (Media) this.preRawList.get(this.viewpager.getCurrentItem());
            int select = isSelect(media, this.selects);
            if (select < 0) {
                this.check_image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_selected));
                this.selects.add(media);
            } else {
                this.check_image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_unselected));
                this.selects.remove(select);
            }
            setDoneView(this.selects.size());
        }
    }

    public int isSelect(Media media, ArrayList<Media> list) {
        int is = -1;
        if (list.size() <= 0) {
            return -1;
        }
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            } else if (((Media) list.get(i)).path.equals(media.path)) {
                is = i;
                break;
            } else {
                i++;
            }
        }
        return is;
    }

    public void done(ArrayList<Media> list, int code) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, list);
        setResult(code, intent);
        finish();
    }

    public void setBarStatus() {
        Log.e("setBarStatus", "setBarStatus");
        if (this.top.getVisibility() == 0) {
            this.top.setVisibility(8);
            this.bottom.setVisibility(8);
            return;
        }
        this.top.setVisibility(0);
        this.bottom.setVisibility(0);
    }

    public void onBackPressed() {
        done(this.selects, PickerConfig.RESULT_UPDATE_CODE);
        super.onBackPressed();
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        this.bar_title.setText((position + 1) + "/" + this.preRawList.size());
        this.check_image.setImageDrawable(isSelect((Media) this.preRawList.get(position), this.selects) < 0 ? ContextCompat.getDrawable(this, R.drawable.btn_unselected) : ContextCompat.getDrawable(this, R.drawable.btn_selected));
    }

    public void onPageScrollStateChanged(int state) {
    }
}
