package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.adapter.FolderAdapter;
import com.dmcbig.mediapicker.adapter.MediaGridAdapter;
import com.dmcbig.mediapicker.adapter.MediaGridAdapter.OnRecyclerViewItemClickListener;
import com.dmcbig.mediapicker.adapter.SpacingDecoration;
import com.dmcbig.mediapicker.data.DataCallback;
import com.dmcbig.mediapicker.data.ImageLoader;
import com.dmcbig.mediapicker.data.MediaLoader;
import com.dmcbig.mediapicker.data.VideoLoader;
import com.dmcbig.mediapicker.entity.Folder;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.utils.ScreenUtils;
import java.util.ArrayList;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PickerActivity extends AppCompatActivity implements DataCallback, OnClickListener {
    Intent argsIntent;
    Button category_btn;
    Button done;
    MediaGridAdapter gridAdapter;
    /* access modifiers changed from: private */
    public FolderAdapter mFolderAdapter;
    ListPopupWindow mFolderPopupWindow;
    Button preview;
    RecyclerView recyclerView;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.argsIntent = getIntent();
        setContentView(R.layout.main);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        findViewById(R.id.btn_back).setOnClickListener(this);
        setTitleBar();
        this.done = (Button) findViewById(R.id.done);
        this.category_btn = (Button) findViewById(R.id.category_btn);
        this.preview = (Button) findViewById(R.id.preview);
        this.done.setOnClickListener(this);
        this.category_btn.setOnClickListener(this);
        this.preview.setOnClickListener(this);
        createAdapter();
        createFolderAdapter();
        getMediaData();
    }

    public void setTitleBar() {
        int type = this.argsIntent.getIntExtra(PickerConfig.SELECT_MODE, 101);
        if (type == 101) {
            ((TextView) findViewById(R.id.bar_title)).setText(getString(R.string.select_title));
        } else if (type == 100) {
            ((TextView) findViewById(R.id.bar_title)).setText(getString(R.string.select_image_title));
        } else if (type == 102) {
            ((TextView) findViewById(R.id.bar_title)).setText(getString(R.string.select_video_title));
        }
    }

    /* access modifiers changed from: 0000 */
    public void createAdapter() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, PickerConfig.GridSpanCount));
        this.recyclerView.addItemDecoration(new SpacingDecoration(PickerConfig.GridSpanCount, PickerConfig.GridSpace));
        this.recyclerView.setHasFixedSize(true);
        this.gridAdapter = new MediaGridAdapter(new ArrayList<>(), this, this.argsIntent.getParcelableArrayListExtra(PickerConfig.DEFAULT_SELECTED_LIST), this.argsIntent.getIntExtra(PickerConfig.MAX_SELECT_COUNT, 40), this.argsIntent.getLongExtra(PickerConfig.MAX_SELECT_SIZE, PickerConfig.DEFAULT_SELECTED_MAX_SIZE));
        this.recyclerView.setAdapter(this.gridAdapter);
    }

    /* access modifiers changed from: 0000 */
    public void createFolderAdapter() {
        this.mFolderAdapter = new FolderAdapter(new ArrayList<>(), this);
        this.mFolderPopupWindow = new ListPopupWindow(this);
        this.mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(-1));
        this.mFolderPopupWindow.setAdapter(this.mFolderAdapter);
        this.mFolderPopupWindow.setHeight((int) (((double) ScreenUtils.getScreenHeight(this)) * 0.6d));
        this.mFolderPopupWindow.setAnchorView(findViewById(R.id.footer));
        this.mFolderPopupWindow.setModal(true);
        this.mFolderPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PickerActivity.this.mFolderAdapter.setSelectIndex(position);
                PickerActivity.this.category_btn.setText(PickerActivity.this.mFolderAdapter.getItem(position).name);
                PickerActivity.this.gridAdapter.updateAdapter(PickerActivity.this.mFolderAdapter.getSelectMedias());
                PickerActivity.this.mFolderPopupWindow.dismiss();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    @AfterPermissionGranted(119)
    public void getMediaData() {
        if (EasyPermissions.hasPermissions(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            int type = this.argsIntent.getIntExtra(PickerConfig.SELECT_MODE, 101);
            if (type == 101) {
                getLoaderManager().initLoader(type, null, new MediaLoader(this, this));
            } else if (type == 100) {
                getLoaderManager().initLoader(type, null, new ImageLoader(this, this));
            } else if (type == 102) {
                getLoaderManager().initLoader(type, null, new VideoLoader(this, this));
            }
        } else {
            EasyPermissions.requestPermissions((Activity) this, getString(R.string.READ_EXTERNAL_STORAGE), 119, "android.permission.READ_EXTERNAL_STORAGE");
        }
    }

    public void onData(ArrayList<Folder> list) {
        setView(list);
        this.category_btn.setText(((Folder) list.get(0)).name);
        this.mFolderAdapter.updateAdapter(list);
    }

    /* access modifiers changed from: 0000 */
    public void setView(ArrayList<Folder> list) {
        this.gridAdapter.updateAdapter(((Folder) list.get(0)).getMedias());
        setButtonText();
        this.gridAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            public void onItemClick(View view, Media data, ArrayList<Media> arrayList) {
                PickerActivity.this.setButtonText();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void setButtonText() {
        this.done.setText(getString(R.string.done) + "(" + this.gridAdapter.getSelectMedias().size() + "/" + this.argsIntent.getIntExtra(PickerConfig.MAX_SELECT_COUNT, 40) + ")");
        this.preview.setText(getString(R.string.preview) + "(" + this.gridAdapter.getSelectMedias().size() + ")");
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            done(new ArrayList<>());
        } else if (id == R.id.category_btn) {
            if (this.mFolderPopupWindow.isShowing()) {
                this.mFolderPopupWindow.dismiss();
            } else {
                this.mFolderPopupWindow.show();
            }
        } else if (id == R.id.done) {
            done(this.gridAdapter.getSelectMedias());
        } else if (id != R.id.preview) {
        } else {
            if (this.gridAdapter.getSelectMedias().size() <= 0) {
                Toast.makeText(this, getString(R.string.select_null), 0).show();
                return;
            }
            Intent intent = new Intent(this, PreviewActivity.class);
            intent.putExtra(PickerConfig.MAX_SELECT_COUNT, this.argsIntent.getIntExtra(PickerConfig.MAX_SELECT_COUNT, 40));
            intent.putExtra(PickerConfig.PRE_RAW_LIST, this.gridAdapter.getSelectMedias());
            startActivityForResult(intent, 200);
        }
    }

    public void done(ArrayList<Media> selects) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, selects);
        setResult(PickerConfig.RESULT_CODE, intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    public void onBackPressed() {
        done(new ArrayList<>());
        super.onBackPressed();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            ArrayList<Media> selects = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            if (resultCode == 1990) {
                this.gridAdapter.updateSelectAdapter(selects);
                setButtonText();
            } else if (resultCode == 19901026) {
                done(selects);
            }
        }
    }
}
