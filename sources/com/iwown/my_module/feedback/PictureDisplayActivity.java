package com.iwown.my_module.feedback;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.entity.MyMedia;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.my_module.R;
import com.iwown.my_module.feedback.adapter.TalkRecyclerAdapter;
import java.io.File;
import java.util.ArrayList;

public class PictureDisplayActivity extends Activity {
    float downX = 0.0f;
    private boolean isCanMove;
    /* access modifiers changed from: private */
    public VideoView mVideoView;
    float moveX = 0.0f;
    private String path = "";
    private ImageView pictureImg;
    int position = 0;
    ArrayList<MyMedia> selectImg;
    MyMedia selectMedia;
    private int type = 1;
    float upX = 0.0f;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_show_main);
        this.pictureImg = (ImageView) findViewById(R.id.picture_img);
        this.mVideoView = (VideoView) findViewById(R.id.picture_video);
        Intent intent = getIntent();
        this.selectMedia = (MyMedia) intent.getParcelableExtra(TalkRecyclerAdapter.MEDIA_LIST);
        this.type = intent.getIntExtra("position", 1);
        this.path = intent.getStringExtra(TalkRecyclerAdapter.MEDIA_PATH);
        this.selectMedia = new MyMedia();
        this.selectMedia.mediaType = this.type;
        this.selectMedia.path = this.path;
        this.selectImg = new ArrayList<>();
        this.selectImg.add(this.selectMedia);
        if (this.selectMedia != null) {
            if (this.selectMedia.mediaType == 1) {
                this.mVideoView.setVisibility(8);
                Glide.with((Activity) this).load(this.selectMedia.path).into(this.pictureImg);
            } else {
                this.pictureImg.setVisibility(8);
                this.mVideoView.setVisibility(0);
                setupVideo();
            }
            this.isCanMove = false;
        }
    }

    private void setupVideo() {
        this.mVideoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                PictureDisplayActivity.this.mVideoView.start();
            }
        });
        this.mVideoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                PictureDisplayActivity.this.stopPlaybackVideo();
            }
        });
        this.mVideoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                PictureDisplayActivity.this.stopPlaybackVideo();
                return true;
            }
        });
        try {
            this.mVideoView.setVideoURI(Uri.fromFile(new File(this.selectMedia.path)));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void stopPlaybackVideo() {
        try {
            if (this.type != 1) {
                this.mVideoView.stopPlayback();
            }
            this.mVideoView = null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        stopPlaybackVideo();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.mVideoView != null && this.mVideoView.canPause()) {
            this.mVideoView.pause();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isCanMove) {
            finish();
            return false;
        }
        switch (event.getAction()) {
            case 0:
                this.downX = event.getRawX();
                break;
            case 1:
                this.upX = event.getRawX();
                this.moveX = this.upX - this.downX;
                if (this.moveX >= -120.0f) {
                    if (this.moveX <= 120.0f) {
                        if (Math.abs(this.moveX) < 2.0f) {
                            finish();
                            break;
                        }
                    } else {
                        this.position--;
                        if (this.position <= 0) {
                            this.position = 0;
                        }
                        Glide.with((Activity) this).load(((MyMedia) this.selectImg.get(this.position)).path).into(this.pictureImg);
                        break;
                    }
                } else {
                    this.position++;
                    if (this.position >= this.selectImg.size() - 1) {
                        this.position = this.selectImg.size() - 1;
                    }
                    Glide.with((Activity) this).load(((MyMedia) this.selectImg.get(this.position)).path).into(this.pictureImg);
                    break;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
