package com.iwown.sport_module.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

final class CameraConfigurationManager {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final int DESIRED_SHARPNESS = 30;
    private static final String TAG = CameraConfigurationManager.class.getSimpleName();
    private static final int TEN_DESIRED_ZOOM = 27;
    private Point cameraResolution;
    private final Context context;
    private int previewFormat;
    private String previewFormatString;
    private Point screenResolution;

    CameraConfigurationManager(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: 0000 */
    public void initFromCameraParameters(Camera camera) {
        Parameters parameters = camera.getParameters();
        this.previewFormat = parameters.getPreviewFormat();
        this.previewFormatString = parameters.get("preview-format");
        Log.d(TAG, "Default preview format: " + this.previewFormat + '/' + this.previewFormatString);
        Display display = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        this.screenResolution = new Point(display.getWidth(), display.getHeight());
        Log.d(TAG, "Screen resolution: " + this.screenResolution);
        this.cameraResolution = getCameraResolution(parameters, this.screenResolution);
        Log.d(TAG, "Camera resolution: " + this.screenResolution);
    }

    /* access modifiers changed from: 0000 */
    public void setDesiredCameraParameters(Camera camera) {
        int position;
        Parameters parameters = camera.getParameters();
        List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes.size() > 2) {
            position = (supportedPreviewSizes.size() / 2) + 1;
        } else {
            position = supportedPreviewSizes.size() / 2;
        }
        int width = ((Size) supportedPreviewSizes.get(position)).width;
        int height = ((Size) supportedPreviewSizes.get(position)).height;
        camera.setDisplayOrientation(90);
        this.cameraResolution.x = width;
        this.cameraResolution.y = height;
        parameters.setPreviewSize(width, height);
        setFlash(parameters);
        setZoom(parameters);
        camera.setParameters(parameters);
    }

    private Size getPicSize(List<Size> list, double d) {
        Collections.sort(list, new Comparator<Size>() {
            public int compare(Size lhs, Size rhs) {
                if (lhs.width - rhs.width == 0) {
                    return lhs.height - rhs.height;
                }
                return lhs.width - rhs.width;
            }
        });
        for (Size size : list) {
            if (Math.abs(((((double) size.width) * 1.0d) / ((double) size.height)) - d) < 0.03d) {
                return size;
            }
        }
        return (Size) list.get(0);
    }

    /* access modifiers changed from: 0000 */
    public Point getCameraResolution() {
        return this.cameraResolution;
    }

    /* access modifiers changed from: 0000 */
    public Point getScreenResolution() {
        if (this.screenResolution == null) {
            Display display = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
            this.screenResolution = new Point(display.getWidth(), display.getHeight());
        }
        return this.screenResolution;
    }

    /* access modifiers changed from: 0000 */
    public int getPreviewFormat() {
        return this.previewFormat;
    }

    /* access modifiers changed from: 0000 */
    public String getPreviewFormatString() {
        return this.previewFormatString;
    }

    private static Point getCameraResolution(Parameters parameters, Point screenResolution2) {
        System.out.println("pingmu  " + screenResolution2.x + "  " + screenResolution2.y);
        List<Size> list = parameters.getSupportedPreviewSizes();
        Collections.sort(list, new Comparator<Size>() {
            public int compare(Size lhs, Size rhs) {
                if (lhs.width - rhs.width == 0) {
                    return lhs.height - rhs.height;
                }
                return lhs.width - rhs.width;
            }
        });
        for (Size size : list) {
            System.out.println(size.width + "   " + size.height);
            if (Math.abs(((((double) size.width) * 1.0d) / ((double) size.height)) - ((((double) screenResolution2.y) * 1.0d) / ((double) screenResolution2.x))) < 0.1d) {
                return new Point(size.width, size.height);
            }
        }
        return new Point(((Size) list.get(0)).width, ((Size) list.get(0)).height);
    }

    private static Point findBestPreviewSizeValue(CharSequence previewSizeValueString, Point screenResolution2) {
        int bestX = 0;
        int bestY = 0;
        int diff = Integer.MAX_VALUE;
        String[] split = COMMA_PATTERN.split(previewSizeValueString);
        int length = split.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String previewSize = split[i].trim();
            int dimPosition = previewSize.indexOf(120);
            if (dimPosition < 0) {
                Log.w(TAG, "Bad preview-size: " + previewSize);
            } else {
                try {
                    int newX = Integer.parseInt(previewSize.substring(0, dimPosition));
                    int newY = Integer.parseInt(previewSize.substring(dimPosition + 1));
                    int newDiff = Math.abs(newX - screenResolution2.x) + Math.abs(newY - screenResolution2.y);
                    if (newDiff == 0) {
                        bestX = newX;
                        bestY = newY;
                        break;
                    } else if (newDiff < diff) {
                        bestX = newX;
                        bestY = newY;
                        diff = newDiff;
                    }
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Bad preview-size: " + previewSize);
                }
            }
            i++;
        }
        if (bestX <= 0 || bestY <= 0) {
            return null;
        }
        return new Point(bestX, bestY);
    }

    private static int findBestMotZoomValue(CharSequence stringValues, int tenDesiredZoom) {
        int tenBestValue = 0;
        String[] split = COMMA_PATTERN.split(stringValues);
        int length = split.length;
        int i = 0;
        while (i < length) {
            try {
                double value = Double.parseDouble(split[i].trim());
                int tenValue = (int) (10.0d * value);
                if (Math.abs(((double) tenDesiredZoom) - value) < ((double) Math.abs(tenDesiredZoom - tenBestValue))) {
                    tenBestValue = tenValue;
                }
                i++;
            } catch (NumberFormatException e) {
                return tenDesiredZoom;
            }
        }
        return tenBestValue;
    }

    private void setFlash(Parameters parameters) {
        if (!Build.MODEL.contains("Behold II") || CameraManager.SDK_INT != 3) {
            parameters.set("flash-value", 2);
        } else {
            parameters.set("flash-value", 1);
        }
        parameters.set("flash-mode", "off");
    }

    private void setZoom(Parameters parameters) {
        String zoomSupportedString = parameters.get("zoom-supported");
        if (zoomSupportedString == null || Boolean.parseBoolean(zoomSupportedString)) {
            int tenDesiredZoom = 27;
            String maxZoomString = parameters.get("max-zoom");
            if (maxZoomString != null) {
                try {
                    int tenMaxZoom = (int) (10.0d * Double.parseDouble(maxZoomString));
                    if (27 > tenMaxZoom) {
                        tenDesiredZoom = tenMaxZoom;
                    }
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Bad max-zoom: " + maxZoomString);
                }
            }
            String takingPictureZoomMaxString = parameters.get("taking-picture-zoom-max");
            if (takingPictureZoomMaxString != null) {
                try {
                    int tenMaxZoom2 = Integer.parseInt(takingPictureZoomMaxString);
                    if (tenDesiredZoom > tenMaxZoom2) {
                        tenDesiredZoom = tenMaxZoom2;
                    }
                } catch (NumberFormatException e2) {
                    Log.w(TAG, "Bad taking-picture-zoom-max: " + takingPictureZoomMaxString);
                }
            }
            String motZoomValuesString = parameters.get("mot-zoom-values");
            if (motZoomValuesString != null) {
                tenDesiredZoom = findBestMotZoomValue(motZoomValuesString, tenDesiredZoom);
            }
            String motZoomStepString = parameters.get("mot-zoom-step");
            if (motZoomStepString != null) {
                try {
                    int tenZoomStep = (int) (10.0d * Double.parseDouble(motZoomStepString.trim()));
                    if (tenZoomStep > 1) {
                        tenDesiredZoom -= tenDesiredZoom % tenZoomStep;
                    }
                } catch (NumberFormatException e3) {
                }
            }
            if (!(maxZoomString == null && motZoomValuesString == null)) {
                parameters.set("zoom", String.valueOf(((double) tenDesiredZoom) / 10.0d));
            }
            if (takingPictureZoomMaxString != null) {
                parameters.set("taking-picture-zoom", tenDesiredZoom);
            }
        }
    }

    public static int getDesiredSharpness() {
        return 30;
    }

    /* access modifiers changed from: protected */
    public void setDisplayOrientation(Camera camera, int angle) {
        try {
            Method downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{Integer.TYPE});
            if (downPolymorphic != null) {
                downPolymorphic.invoke(camera, new Object[]{Integer.valueOf(angle)});
            }
        } catch (Exception e) {
        }
    }
}
