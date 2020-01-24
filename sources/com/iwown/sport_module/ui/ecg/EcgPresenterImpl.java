package com.iwown.sport_module.ui.ecg;

import com.iwown.data_link.ecg.EcgHasDataNet.EcgHasData;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.ecg.EcgContract.EcgDataView;
import com.iwown.sport_module.ui.ecg.EcgContract.EcgPresenter;
import com.socks.library.KLog;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EcgPresenterImpl implements EcgPresenter {
    /* access modifiers changed from: private */
    public EcgDataView view;

    public EcgPresenterImpl(EcgDataView view2) {
        this.view = view2;
    }

    public void start(boolean b) {
    }

    public void onDestroy() {
    }

    public void braceletToView() {
        ModuleRouterEcgService.getInstance().braceletToView(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice());
    }

    public List<EcgViewDataBean> loadEcgDataByTime(long time) {
        List<EcgViewDataBean> dataBeanList = ModuleRouterEcgService.getInstance().ecgViewDataFromDB(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), time);
        if (dataBeanList == null || dataBeanList.size() <= 0) {
            return null;
        }
        return dataBeanList;
    }

    public void downLoadEcgDataByDay(DateUtil dateUtil) {
        NetFactory.getInstance().getClient(new MyCallback<Object>() {
            public void onSuccess(Object integer) {
                if (!(integer instanceof Integer)) {
                    return;
                }
                if (((Integer) integer).intValue() != 0) {
                    EcgPresenterImpl.this.view.noEcgDataByDay();
                } else {
                    EcgPresenterImpl.this.view.showDataOver();
                }
            }

            public void onFail(Throwable e) {
                KLog.i("---------------------" + e.toString());
            }
        }).downLoadEcgData(UserConfig.getInstance().getNewUID(), dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "");
    }

    public void loadEcgCalendarStatus(long uid) {
        Map<String, EcgHasData> contentBeans = new LinkedHashMap<>();
        List<EcgViewDataBean> list = ModuleRouterEcgService.getInstance().queryEcgDataByUid(uid);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                EcgViewDataBean bean = (EcgViewDataBean) list.get(i);
                DateUtil d = new DateUtil(bean.getUnixTime(), true);
                if (((EcgHasData) contentBeans.get(d.getY_M_D())) == null) {
                    EcgHasData eed = new EcgHasData();
                    eed.setData_from(bean.getData_from());
                    eed.setDate(bean.getDate());
                    eed.setUnixTime(d.getUnixTimestamp());
                    contentBeans.put(d.getY_M_D(), eed);
                }
            }
            this.view.updateCalendar(contentBeans);
        }
    }

    public void loadEcgHasData(long uid, int year, int month) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (o instanceof Integer) {
                    KLog.i("=======loadEcgHasData===code============" + ((Integer) o).intValue());
                }
            }

            public void onFail(Throwable e) {
            }
        }).hasEcgDataNet(uid, year + "", month + "");
    }
}
