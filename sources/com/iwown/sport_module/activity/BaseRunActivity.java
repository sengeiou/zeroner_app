package com.iwown.sport_module.activity;

import android.support.v7.app.AppCompatActivity;
import com.iwown.data_link.heart.HeartData;
import com.iwown.sport_module.contract.RunActivityContract.Presenter;
import com.iwown.sport_module.contract.RunActivityContract.View;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.pojo.R1DataBean;
import com.iwown.sport_module.presenter.BaseRunActivityPresenterImpl;
import java.util.List;

public class BaseRunActivity extends AppCompatActivity implements View {
    protected Presenter mPresenter = new BaseRunActivityPresenterImpl(this);

    public void controlLoading(boolean shouldShow) {
    }

    public void refreshMapView(List<LongitudeAndLatitude> list) {
    }

    public void onMapHealthDataArrive(MapHealthyData healthyData) {
    }

    public void onHeartDataArrive(HeartData heartData) {
    }

    public void onPaceChartBeansArrive(List<DataFragmentBean> list) {
    }

    public void onDiagramArrive(DiagramsData diagramsData) {
    }

    public void onR1Data(R1DataBean dataBean) {
    }
}
