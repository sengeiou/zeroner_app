package com.iwown.my_module;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.iwown.data_link.Constants;
import com.iwown.data_link.consts.ApiConst;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.my_module.data.HealthGoalEntity;
import com.iwown.my_module.data.TB_data_import;
import com.iwown.my_module.data.UserInfoEntity;
import com.iwown.my_module.feedback.data.TB_feedback;
import com.iwown.my_module.healthy.HealthyConstants;
import com.youzan.sdk.YouzanSDK;
import java.util.List;
import org.litepal.LitePal;
import org.litepal.LitePalDB;

public class MyInitUtils {
    private static MyInitUtils instance;
    private Application myApplication;

    private MyInitUtils() {
    }

    public static MyInitUtils getInstance() {
        if (instance == null) {
            instance = new MyInitUtils();
        }
        return instance;
    }

    public void init(Application application, LitePalDB litePalDB) {
        this.myApplication = application;
        addTable2DB(litePalDB);
    }

    public Application getMyApplication() {
        return this.myApplication;
    }

    public static void addTable2DB(LitePalDB litePalDB) {
        List classNames = litePalDB.getClassNames();
        litePalDB.addClassName(UserInfoEntity.class.getName());
        litePalDB.addClassName(HealthGoalEntity.class.getName());
        litePalDB.addClassName(TB_feedback.class.getName());
        litePalDB.addClassName(TB_data_import.class.getName());
        LitePal.use(litePalDB);
        Log.i("myinit", "addTable2DB");
    }

    public void initInstabug() {
        String str = Constants.INSTABUG_KEY;
        if (AppConfigUtil.isZeronerHealthPro()) {
            String key = "49ff9d7293b6f88f2714e11d093258a9";
        } else if (AppConfigUtil.isIwownFitPro()) {
            String key2 = Constants.INSTABUG_KEY;
        } else if (AppConfigUtil.isNanfei_TRAX_GPS() || AppConfigUtil.isUpfit() || AppConfigUtil.isNewfit()) {
            String key3 = "49ff9d7293b6f88f2714e11d093258a9";
        }
    }

    public void changeURLRU(boolean b) {
        if (b) {
            ApiConst.AMAZON_API_ADDRESS_PROD = "https://search.iwown.com/venus/";
            ApiConst.AMAZON_UP_VEDIO = "http://search.iwown.com:7789/";
        }
    }

    public void initYouZan(Context context) {
        YouzanSDK.init(context, HealthyConstants.YOUZAN_ID);
    }
}
