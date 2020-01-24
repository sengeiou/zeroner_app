package com.iwown.device_module.device_camera.camera;

import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import com.github.mikephil.charting.utils.Utils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraUtils {
    private static final double ASPECT_TOLERANCE = 0.1d;

    private static class SizeComparator implements Comparator<Size> {
        private SizeComparator() {
        }

        public int compare(Size lhs, Size rhs) {
            int left = lhs.width * lhs.height;
            int right = rhs.width * rhs.height;
            if (left < right) {
                return -1;
            }
            if (left > right) {
                return 1;
            }
            return 0;
        }
    }

    public static Size getOptimalPreviewSize(int displayOrientation, int width, int height, Parameters parameters) {
        double targetRatio = ((double) width) / ((double) height);
        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = height;
        if (displayOrientation == 90 || displayOrientation == 270) {
            targetRatio = ((double) height) / ((double) width);
        }
        for (Size size : sizes) {
            if (Math.abs((((double) size.width) / ((double) size.height)) - targetRatio) <= ASPECT_TOLERANCE && ((double) Math.abs(size.height - targetHeight)) < minDiff) {
                optimalSize = size;
                minDiff = (double) Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) {
            double minDiff2 = Double.MAX_VALUE;
            for (Size size2 : sizes) {
                if (((double) Math.abs(size2.height - targetHeight)) < minDiff2) {
                    optimalSize = size2;
                    minDiff2 = (double) Math.abs(size2.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public static Size getBestAspectPreviewSize(int displayOrientation, int width, int height, Parameters parameters) {
        return getBestAspectPreviewSize(displayOrientation, width, height, parameters, Utils.DOUBLE_EPSILON);
    }

    public static Size getBestAspectPreviewSize(int displayOrientation, int width, int height, Parameters parameters, double closeEnough) {
        double targetRatio = ((double) width) / ((double) height);
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        if (displayOrientation == 90 || displayOrientation == 270) {
            targetRatio = ((double) height) / ((double) width);
        }
        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Collections.sort(sizes, Collections.reverseOrder(new SizeComparator()));
        for (Size size : sizes) {
            double ratio = ((double) size.width) / ((double) size.height);
            if (Math.abs(ratio - targetRatio) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(ratio - targetRatio);
            }
            if (minDiff < closeEnough) {
                break;
            }
        }
        return optimalSize;
    }

    public static Size getLargestPictureSize(CameraHost host, Parameters parameters) {
        return getLargestPictureSize(host, parameters, true);
    }

    public static Size getLargestPictureSize(CameraHost host, Parameters parameters, boolean enforceProfile) {
        Size result = null;
        for (Size size : parameters.getSupportedPictureSizes()) {
            if (!enforceProfile || (size.height <= host.getDeviceProfile().getMaxPictureHeight() && size.height >= host.getDeviceProfile().getMinPictureHeight())) {
                if (result == null) {
                    result = size;
                } else if (size.width * size.height > result.width * result.height) {
                    result = size;
                }
            }
        }
        if (result != null || !enforceProfile) {
            return result;
        }
        return getLargestPictureSize(host, parameters, false);
    }

    public static Size getSmallestPictureSize(Parameters parameters) {
        Size result = null;
        for (Size size : parameters.getSupportedPictureSizes()) {
            if (result == null) {
                result = size;
            } else if (size.width * size.height < result.width * result.height) {
                result = size;
            }
        }
        return result;
    }

    public static String findBestFlashModeMatch(Parameters params, String... modes) {
        List<String> flashModes = params.getSupportedFlashModes();
        if (flashModes == null) {
            return null;
        }
        for (String mode : modes) {
            if (flashModes.contains(mode)) {
                return mode;
            }
        }
        return null;
    }
}
