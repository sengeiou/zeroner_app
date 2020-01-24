package com.iwown.sport_module.zxing.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.io.IOException;

public class PhotosGetCodeUtils {
    public static Result getCodeResult(Context context, Uri sourceUri) throws FormatException, ChecksumException, NotFoundException {
        Bitmap bitmap = null;
        try {
            bitmap = Media.getBitmap(context.getContentResolver(), sourceUri);
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
        Bitmap bitmap2 = ImageUtils.getSmallerBitmap(bitmap);
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int[] pixels = new int[(width * height)];
        bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);
        return new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(width, height, pixels))));
    }
}
