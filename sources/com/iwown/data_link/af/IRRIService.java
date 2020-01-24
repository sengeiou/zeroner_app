package com.iwown.data_link.af;

import com.alibaba.android.arouter.facade.template.IProvider;
import java.util.Map;

public interface IRRIService extends IProvider {
    Map<String, AfContenBean> getCalendarMap(long j, String str);

    String getRRIDataFrom(long j, String str);

    String getRRIHasDataFrom(long j, String str, String str2);

    boolean hasRRIData(long j);
}
