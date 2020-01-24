package com.dmcbig.mediapicker.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.PreviewActivity;
import com.dmcbig.mediapicker.R;
import com.dmcbig.mediapicker.entity.Media;
import java.io.File;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class PreviewFragment extends Fragment {
    private PhotoView mPhotoView;
    ImageView play_view;

    public static PreviewFragment newInstance(Media media, String label) {
        PreviewFragment f = new PreviewFragment();
        Bundle b = new Bundle();
        b.putParcelable("media", media);
        f.setArguments(b);
        return f;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preview_fragment_item, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Media media = (Media) getArguments().getParcelable("media");
        this.play_view = (ImageView) view.findViewById(R.id.play_view);
        this.mPhotoView = (PhotoView) view.findViewById(R.id.photoview);
        this.mPhotoView.setMaximumScale(5.0f);
        this.mPhotoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            public void onPhotoTap(View view, float x, float y) {
                ((PreviewActivity) PreviewFragment.this.getActivity()).setBarStatus();
            }
        });
        setPlayView(media);
        Glide.with(getActivity()).load(media.path).into(this.mPhotoView);
    }

    /* access modifiers changed from: 0000 */
    public void setPlayView(final Media media) {
        if (media.mediaType == 3) {
            this.play_view.setVisibility(0);
            this.play_view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(PreviewFragment.this.getUri(media.path), "video/*");
                    intent.addFlags(1);
                    if (PreviewFragment.this.isIntentAvailable(PreviewFragment.this.getContext(), intent)) {
                        PreviewFragment.this.startActivity(intent);
                    } else {
                        Toast.makeText(PreviewFragment.this.getContext(), PreviewFragment.this.getString(R.string.cant_play_video), 0).show();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public Uri getUri(String path) {
        if (VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".dmc", new File(path));
        }
        return Uri.fromFile(new File(path));
    }

    /* access modifiers changed from: private */
    public boolean isIntentAvailable(Context context, Intent intent) {
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            return true;
        }
        return false;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }
}
