package router;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.app_link.IAPPService;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.my_module.MyInitUtils;
import com.iwown.sport_module.SportInitUtils;

@Route(path = "/app/service")
public class AppModuleHandler implements IAPPService {
    public void init(Context context) {
    }

    public void changeRuURL() {
        try {
            SportInitUtils.getInstance().changeURLRU(true);
            DeviceInitUtils.getInstance().changeURLRU(true);
            MyInitUtils.getInstance().changeURLRU(true);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
