package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.device_module.common.activity.DataImportActivity;
import com.iwown.device_module.device_firmware_upgrade.activity.FirmwareUpgradeActivity;
import com.iwown.device_module.device_operation.type.DeviceListActivity;
import com.iwown.device_module.device_operation.type.DeviceListFragment;
import com.iwown.device_module.device_setting.wifi_scale.activity.AddOrEditUserActivity;
import com.iwown.device_module.device_setting.wifi_scale.activity.WeightDataNotBelongToActivity;
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

public class ARouter$$Group$$device implements IRouteGroup {
    public void loadInto(Map<String, RouteMeta> atlas) {
        atlas.put(RouteUtils.Activity_Device_AddUserActivity, RouteMeta.build(RouteType.ACTIVITY, AddOrEditUserActivity.class, "/device/adduseractivity", "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Activity_Device_DataImportActivity, RouteMeta.build(RouteType.ACTIVITY, DataImportActivity.class, "/device/dataimportactivity", "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Activity_Device_DeviceListActivity, RouteMeta.build(RouteType.ACTIVITY, DeviceListActivity.class, "/device/devicelistactivity", "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Fragment_Device_List, RouteMeta.build(RouteType.FRAGMENT, DeviceListFragment.class, "/device/devicelistfragment", "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Activity_Device_FirmwareUpgradeActivity, RouteMeta.build(RouteType.ACTIVITY, FirmwareUpgradeActivity.class, "/device/firmwareupgradeactivity", "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.DEVICE_Weight_Data_Not_Belong, RouteMeta.build(RouteType.ACTIVITY, WeightDataNotBelongToActivity.class, RouteUtils.DEVICE_Weight_Data_Not_Belong, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_ECG_Service, RouteMeta.build(RouteType.PROVIDER, EcgService.class, RouteUtils.Device_ECG_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_Fatigue_Service, RouteMeta.build(RouteType.PROVIDER, FatigueService.class, RouteUtils.Device_Fatigue_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_Heart_Service, RouteMeta.build(RouteType.PROVIDER, HeartModuleService.class, RouteUtils.Device_Heart_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_DeviceInfo_Service, RouteMeta.build(RouteType.PROVIDER, DeviceService.class, RouteUtils.Device_DeviceInfo_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_RRI_Service, RouteMeta.build(RouteType.PROVIDER, RRIService.class, RouteUtils.Device_RRI_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_Sleep_Service, RouteMeta.build(RouteType.PROVIDER, DeviceSleepDataService.class, RouteUtils.Device_Sleep_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_Sport_Service, RouteMeta.build(RouteType.PROVIDER, SportService.class, RouteUtils.Device_Sport_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_Walk_Service, RouteMeta.build(RouteType.PROVIDER, WalkService.class, RouteUtils.Device_Walk_Service, "device", null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Device_Weight_Service, RouteMeta.build(RouteType.PROVIDER, WeightModuleService.class, RouteUtils.Device_Weight_Service, "device", null, -1, Integer.MIN_VALUE));
    }
}
