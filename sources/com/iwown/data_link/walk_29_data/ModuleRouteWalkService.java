package com.iwown.data_link.walk_29_data;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.lib_common.json.JsonTool;
import java.util.List;

public class ModuleRouteWalkService {
    @Autowired
    IWalkService mWalkService;

    static class ModuleRouteWalkServiceHolder {
        static ModuleRouteWalkService moduleRouteWalkService = new ModuleRouteWalkService();

        ModuleRouteWalkServiceHolder() {
        }
    }

    private ModuleRouteWalkService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteWalkService getInstance() {
        return ModuleRouteWalkServiceHolder.moduleRouteWalkService;
    }

    public V3_walk getWalk(long uid, String date, String dataFrom) {
        if (this.mWalkService != null) {
            return (V3_walk) JsonTool.fromJson(this.mWalkService.get29Walk(uid, date, dataFrom), V3_walk.class);
        }
        return new V3_walk();
    }

    public List<V3_walk> getUpWalkData(long uid) {
        if (this.mWalkService != null) {
            return this.mWalkService.query29UpData(uid);
        }
        return null;
    }

    public void changeUpFlag(long uid) {
        if (this.mWalkService != null) {
            this.mWalkService.changeUpFlag(uid);
        }
    }

    public void save29DataFromHistory(List<Content> list) {
        if (this.mWalkService != null) {
            this.mWalkService.save29DataFromHistory(list);
        }
    }

    public V3_walk get29Walk(long uid, long long_unix_record_date) {
        if (this.mWalkService != null) {
            return this.mWalkService.get29Walk(uid, long_unix_record_date);
        }
        return null;
    }

    public V3_walk get29WalkByDataFrom(long uid, long long_unix_record_date, String data_from) {
        if (this.mWalkService != null) {
            return this.mWalkService.get29WalkByDataFrom(uid, long_unix_record_date, data_from);
        }
        return null;
    }
}
