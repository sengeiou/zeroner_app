package com.iwown.data_link.walk_29_data;

import com.alibaba.android.arouter.facade.template.IProvider;
import java.util.List;

public interface IWalkService extends IProvider {
    void changeUpFlag(long j);

    V3_walk get29Walk(long j, long j2);

    String get29Walk(long j, String str, String str2);

    V3_walk get29WalkByDataFrom(long j, long j2, String str);

    List<V3_walk> query29UpData(long j);

    void save29DataFromHistory(List<Content> list);
}
