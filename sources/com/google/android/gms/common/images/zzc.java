package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbff;
import com.google.android.gms.internal.zzbfk;
import java.lang.ref.WeakReference;

public final class zzc extends zza {
    private WeakReference<ImageView> zzfxm;

    public zzc(ImageView imageView, int i) {
        super(null, i);
        com.google.android.gms.common.internal.zzc.zzv(imageView);
        this.zzfxm = new WeakReference<>(imageView);
    }

    public zzc(ImageView imageView, Uri uri) {
        super(uri, 0);
        com.google.android.gms.common.internal.zzc.zzv(imageView);
        this.zzfxm = new WeakReference<>(imageView);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = (ImageView) this.zzfxm.get();
        ImageView imageView2 = (ImageView) ((zzc) obj).zzfxm.get();
        return (imageView2 == null || imageView == null || !zzbg.equal(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        Drawable drawable2;
        Uri uri = null;
        ImageView imageView = (ImageView) this.zzfxm.get();
        if (imageView != null) {
            boolean z4 = !z2 && !z3;
            if (z4 && (imageView instanceof zzbfk)) {
                int zzakg = zzbfk.zzakg();
                if (this.zzfxh != 0 && zzakg == this.zzfxh) {
                    return;
                }
            }
            boolean zzc = zzc(z, z2);
            if (zzc) {
                Drawable drawable3 = imageView.getDrawable();
                if (drawable3 == null) {
                    drawable3 = null;
                } else if (drawable3 instanceof zzbff) {
                    drawable3 = ((zzbff) drawable3).zzake();
                }
                drawable2 = new zzbff(drawable3, drawable);
            } else {
                drawable2 = drawable;
            }
            imageView.setImageDrawable(drawable2);
            if (imageView instanceof zzbfk) {
                if (z3) {
                    uri = this.zzfxf.uri;
                }
                zzbfk.zzn(uri);
                zzbfk.zzcd(z4 ? this.zzfxh : 0);
            }
            if (zzc) {
                ((zzbff) drawable2).startTransition(250);
            }
        }
    }
}
