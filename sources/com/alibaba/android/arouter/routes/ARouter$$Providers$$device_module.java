package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.device_module.interactive_service.DeviceService;
import com.iwown.device_module.interactive_service.DeviceSleepDataService;
import com.iwown.device_module.interactive_service.EcgService;
import com.iwown.device_module.interactive_service.FatigueService;
import com.iwown.device_module.interactive_service.HeartModuleService;
import com.iwown.device_module.interactive_service.RRIService;
import com.iwown.device_module.interactive_service.SportService;
import com.iwown.device_module.interactive_service.WalkService;
import com.iwown.device_module.interactive_service.WeightModuleService;
import java.util.Map;

public class ARouter$$Providers$$device_module implements IProviderGroup {
    public void loadInto(Map<String, RouteMeta> providers) {
        providers.put("com.iwown.data_link.ecg.IEcgService", RouteMeta.build(RouteType.PROVIDER, EcgService.class, RouteUtils.Device_ECG_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.fatigue.IFatigueService", RouteMeta.build(RouteType.PROVIDER, FatigueService.class, RouteUtils.Device_Fatigue_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.heart.IHeartService", RouteMeta.build(RouteType.PROVIDER, HeartModuleService.class, RouteUtils.Device_Heart_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.device.IDeviceService", RouteMeta.build(RouteType.PROVIDER, DeviceService.class, RouteUtils.Device_DeviceInfo_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.af.IRRIService", RouteMeta.build(RouteType.PROVIDER, RRIService.class, RouteUtils.Device_RRI_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.sleep_data.ISleepService", RouteMeta.build(RouteType.PROVIDER, DeviceSleepDataService.class, RouteUtils.Device_Sleep_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.sport_data.ISportService", RouteMeta.build(RouteType.PROVIDER, SportService.class, RouteUtils.Device_Sport_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.walk_29_data.IWalkService", RouteMeta.build(RouteType.PROVIDER, WalkService.class, RouteUtils.Device_Walk_Service, "device", null, -1, Integer.MIN_VALUE));
        providers.put("com.iwown.data_link.weight.IWeightService", RouteMeta.build(RouteType.PROVIDER, WeightModuleService.class, RouteUtils.Device_Weight_Service, "device", null, -1, Integer.MIN_VALUE));
    }
}
