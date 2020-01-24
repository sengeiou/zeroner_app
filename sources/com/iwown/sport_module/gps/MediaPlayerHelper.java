package com.iwown.sport_module.gps;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;
import com.socks.library.KLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaPlayerHelper {
    public static MediaPlayerHelper instance = null;
    /* access modifiers changed from: private */
    public static MediaPlayer mediaPlayer;
    /* access modifiers changed from: private */
    public boolean isEnd;
    private boolean isPlay = false;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int number = 0;

    private MediaPlayerHelper() {
    }

    public static MediaPlayerHelper getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper();
                    mediaPlayer = new MediaPlayer();
                }
            }
        }
        return instance;
    }

    public void initMedia(Context context) {
        this.mContext = context;
    }

    public void initMedia(Context context, boolean isPlay2) {
        this.mContext = context;
        this.isPlay = isPlay2;
    }

    public void destroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            this.mContext = null;
            instance = null;
        }
    }

    public void playGpsVoice(final List<Integer> nums) {
        if (this.mContext == null) {
            this.mContext = SportInitUtils.getInstance().app.getApplicationContext();
            restMediaPlayer();
            this.isPlay = AppConfigUtil.isHealthy(this.mContext);
        }
        if (this.isPlay) {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            try {
                this.number = 0;
                if (nums != null && nums.size() > 0) {
                    L.file("语音播报-->num.size: " + nums.size(), 3);
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(this.mContext, Uri.parse("android.resource://" + this.mContext.getPackageName() + "/" + nums.get(0)));
                    mediaPlayer.prepare();
                    this.number++;
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            if (MediaPlayerHelper.this.number < nums.size()) {
                                KLog.d("no2855 语音播报: " + MediaPlayerHelper.this.number);
                                MediaPlayerHelper.mediaPlayer.reset();
                                try {
                                    L.file("语音播报-->number is : " + MediaPlayerHelper.this.number, 3);
                                    MediaPlayerHelper.mediaPlayer.setDataSource(MediaPlayerHelper.this.mContext, Uri.parse("android.resource://" + MediaPlayerHelper.this.mContext.getPackageName() + "/" + nums.get(MediaPlayerHelper.this.number)));
                                    MediaPlayerHelper.mediaPlayer.prepare();
                                    MediaPlayerHelper.this.number = MediaPlayerHelper.this.number + 1;
                                    MediaPlayerHelper.mediaPlayer.start();
                                } catch (IOException e) {
                                    L.file("语音播报-->异常: ", 3);
                                    MediaPlayerHelper.this.restMediaPlayer();
                                    ThrowableExtension.printStackTrace(e);
                                }
                            } else {
                                MediaPlayerHelper.this.restMediaPlayer();
                                if (MediaPlayerHelper.this.isEnd) {
                                    MediaPlayerHelper.this.destroy();
                                }
                            }
                        }
                    });
                }
            } catch (IOException e) {
                L.file("语音播报-->异常222: ", 3);
                restMediaPlayer();
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void restMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playStart() {
        this.isEnd = false;
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.start));
        playGpsVoice(start);
    }

    public void playPause() {
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.pause));
        playGpsVoice(start);
    }

    public synchronized void playGpsDistance(int kilometre, int time) {
        List<Integer> nums = new ArrayList<>();
        nums.add(Integer.valueOf(R.raw.has_sport));
        nums.addAll(getListNumber(kilometre));
        nums.add(Integer.valueOf(R.raw.kilometre));
        nums.add(Integer.valueOf(R.raw.time));
        int second = time % 60;
        nums.addAll(getListNumber(time / 60));
        nums.add(Integer.valueOf(R.raw.min));
        nums.addAll(getListNumber(second));
        nums.add(Integer.valueOf(R.raw.sec));
        playGpsVoice(nums);
    }

    private List<Integer> getListNumber(int number2) {
        List<Integer> nums = new ArrayList<>();
        int hus = number2 / 100;
        int shi = (number2 - (hus * 100)) / 10;
        int ge = number2 % 10;
        if (hus > 0) {
            nums.add(Integer.valueOf(getNumberVoice(hus)));
            nums.add(Integer.valueOf(getNumberVoice(100)));
            nums.add(Integer.valueOf(getNumberVoice(shi)));
            nums.add(Integer.valueOf(getNumberVoice(10)));
        } else if (shi > 1) {
            nums.add(Integer.valueOf(getNumberVoice(shi)));
            nums.add(Integer.valueOf(getNumberVoice(10)));
        } else if (shi == 1) {
            nums.add(Integer.valueOf(getNumberVoice(10)));
        }
        if (ge > 0) {
            nums.add(Integer.valueOf(getNumberVoice(ge)));
        }
        return nums;
    }

    private int getNumberVoice(int distance) {
        switch (distance) {
            case 1:
                return R.raw.one;
            case 2:
                return R.raw.two;
            case 3:
                return R.raw.three;
            case 4:
                return R.raw.four;
            case 5:
                return R.raw.five;
            case 6:
                return R.raw.six;
            case 7:
                return R.raw.seven;
            case 8:
                return R.raw.eight;
            case 9:
                return R.raw.nine;
            case 10:
                return R.raw.ten;
            case 100:
                return R.raw.hundred;
            default:
                return R.raw.one;
        }
    }

    public void playNumber(int number2) {
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(getNumberVoice(number2)));
        playGpsVoice(start);
    }

    public void playEnd() {
        this.isEnd = true;
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.end));
        playGpsVoice(start);
    }

    public void playSurplus500() {
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.hundred_5));
        playGpsVoice(start);
    }

    public void playKm() {
        MediaPlayer.create(this.mContext, R.raw.kilometre).start();
    }

    public void playHeightSport() {
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.heigh_sport));
        playGpsVoice(start);
    }

    public void playSlowDown() {
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.slow_down));
        playGpsVoice(start);
    }

    public void playHurryUp() {
        List<Integer> start = new ArrayList<>();
        start.add(Integer.valueOf(R.raw.hurry_up));
        playGpsVoice(start);
    }
}
