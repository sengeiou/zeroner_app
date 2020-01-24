package com.tencent.tinker.loader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareResPatchInfo;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;
import java.io.File;

public class TinkerResourceLoader {
    protected static final String RESOURCE_FILE = "resources.apk";
    protected static final String RESOURCE_META_FILE = "assets/res_meta.txt";
    protected static final String RESOURCE_PATH = "res";
    private static final String TAG = "Tinker.ResourceLoader";
    private static ShareResPatchInfo resPatchInfo = new ShareResPatchInfo();

    private TinkerResourceLoader() {
    }

    public static boolean loadTinkerResources(TinkerApplication application, String directory, Intent intentResult) {
        if (resPatchInfo == null || resPatchInfo.resArscMd5 == null) {
            return true;
        }
        String resourceString = directory + "/" + "res" + "/" + "resources.apk";
        File resourceFile = new File(resourceString);
        long start = System.currentTimeMillis();
        if (application.isTinkerLoadVerifyFlag()) {
            if (!SharePatchFileUtil.checkResourceArscMd5(resourceFile, resPatchInfo.resArscMd5)) {
                Log.e(TAG, "Failed to load resource file, path: " + resourceFile.getPath() + ", expect md5: " + resPatchInfo.resArscMd5);
                ShareIntentUtil.setIntentReturnCode(intentResult, -24);
                return false;
            }
            Log.i(TAG, "verify resource file:" + resourceFile.getPath() + " md5, use time: " + (System.currentTimeMillis() - start));
        }
        try {
            TinkerResourcePatcher.monkeyPatchExistingResources(application, resourceString);
            Log.i(TAG, "monkeyPatchExistingResources resource file:" + resourceString + ", use time: " + (System.currentTimeMillis() - start));
            return true;
        } catch (Throwable th) {
            Log.e(TAG, "uninstallPatchDex failed", e);
        }
        intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_EXCEPTION, e);
        ShareIntentUtil.setIntentReturnCode(intentResult, -23);
        return false;
    }

    public static boolean checkComplete(Context context, String directory, ShareSecurityCheck securityCheck, Intent intentResult) {
        String meta = (String) securityCheck.getMetaContentMap().get("assets/res_meta.txt");
        if (meta == null) {
            return true;
        }
        ShareResPatchInfo.parseResPatchInfoFirstLine(meta, resPatchInfo);
        if (resPatchInfo.resArscMd5 == null) {
            return true;
        }
        if (!ShareResPatchInfo.checkResPatchInfo(resPatchInfo)) {
            intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_PACKAGE_PATCH_CHECK, -8);
            ShareIntentUtil.setIntentReturnCode(intentResult, -8);
            return false;
        }
        String resourcePath = directory + "/" + "res" + "/";
        File resourceDir = new File(resourcePath);
        if (!resourceDir.exists() || !resourceDir.isDirectory()) {
            ShareIntentUtil.setIntentReturnCode(intentResult, -21);
            return false;
        } else if (!SharePatchFileUtil.isLegalFile(new File(resourcePath + "resources.apk"))) {
            ShareIntentUtil.setIntentReturnCode(intentResult, -22);
            return false;
        } else {
            try {
                TinkerResourcePatcher.isResourceCanPatch(context);
                return true;
            } catch (Throwable e) {
                Log.e(TAG, "resource hook check failed.", e);
                intentResult.putExtra(ShareIntentUtil.INTENT_PATCH_EXCEPTION, e);
                ShareIntentUtil.setIntentReturnCode(intentResult, -23);
                return false;
            }
        }
    }
}
