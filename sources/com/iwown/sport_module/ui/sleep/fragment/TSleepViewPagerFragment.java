package com.iwown.sport_module.ui.sleep.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseFragment;
import com.iwown.sport_module.ui.sleep.RecyclerOnPagerListener;
import com.iwown.sport_module.ui.sleep.SleepShowActivity;
import com.iwown.sport_module.ui.sleep.adapter.SleepAdapter;
import com.iwown.sport_module.ui.sleep.adapter.SleepAdapter.DateClickListener;
import com.iwown.sport_module.ui.sleep.data.EventSleepUpdate;
import com.iwown.sport_module.ui.sleep.mvp.SleepContract.SPrecenter;
import com.iwown.sport_module.ui.sleep.mvp.SleepContract.SleepView;
import com.iwown.sport_module.ui.utils.ScreenLongShareUtils;
import com.iwown.sport_module.view.calendar.CalendarShowHanlder;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.socks.library.KLog;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TSleepViewPagerFragment extends BaseFragment implements SleepView, DateClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = TSleepViewPagerFragment.class.getName();
    /* access modifiers changed from: private */
    public Context mContext;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    /* access modifiers changed from: private */
    public SPrecenter mPresenter;
    /* access modifiers changed from: private */
    public long preOrNextTimeByDay1 = System.currentTimeMillis();
    /* access modifiers changed from: private */
    public RecyclerViewPager rvp_sleep;
    /* access modifiers changed from: private */
    public SleepAdapter sleepAdapter;

    public interface OnFragmentInteractionListener {
        void dismissLoading();

        void showLoading();

        void updateCalendar(Map<String, ContentBean> map);
    }

    public static TSleepViewPagerFragment newInstance(String param1, String param2) {
        TSleepViewPagerFragment fragment = new TSleepViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sport_module_fragment_tsleep_view_pager, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        this.rvp_sleep = (RecyclerViewPager) view.findViewById(R.id.rvp_sleep);
        this.sleepAdapter = new SleepAdapter(this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), 0, false);
        this.rvp_sleep.setLayoutManager(layout);
        this.rvp_sleep.setAdapter(this.sleepAdapter);
        this.rvp_sleep.addOnScrollListener(new RecyclerOnPagerListener(layout) {
            public void callBack(int firstCompletelyVisibleItemPosition) {
                long preOrNextTimeByDay = DateUtil.getPreOrNextTimeByDay((long) (SleepAdapter.getLastPosition() - firstCompletelyVisibleItemPosition));
                DateUtil dateUtil = new DateUtil(preOrNextTimeByDay, false);
                KLog.e("dateUtil " + dateUtil.getY_M_D());
                if (CalendarShowHanlder.getCalendarShowHanlder() == null) {
                    ((SleepShowActivity) TSleepViewPagerFragment.this.mContext).initCalendar();
                }
                CalendarShowHanlder.getCalendarShowHanlder().updateSelectDate(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());
                SleepDataDay sleepDataDay = new SleepDataDay();
                sleepDataDay.time_unix = dateUtil.getUnixTimestamp();
                sleepDataDay.data_from = UserConfig.getInstance().getDevice();
                sleepDataDay.uid = UserConfig.getInstance().getNewUID();
                TSleepViewPagerFragment.this.sleepAdapter.setRecyclerViewAndData(TSleepViewPagerFragment.this.rvp_sleep, sleepDataDay);
                if (TSleepViewPagerFragment.this.mPresenter != null) {
                    TSleepViewPagerFragment.this.preOrNextTimeByDay1 = preOrNextTimeByDay;
                    TSleepViewPagerFragment.this.mPresenter.loadDayDataByTime(preOrNextTimeByDay);
                }
            }
        });
        this.rvp_sleep.scrollToPosition(SleepAdapter.getLastPosition());
    }

    @Subscribe
    public void updateDataUI(EventSleepUpdate eventSleepUpdate) {
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void setPresenter(SPrecenter presenter) {
        this.mPresenter = presenter;
    }

    public void showSleepData(SleepDataDay sleepDataToday) {
        this.sleepAdapter.setRecyclerViewAndData(this.rvp_sleep, sleepDataToday);
    }

    public void showLoading() {
        this.mListener.showLoading();
    }

    public void dismissLoading() {
        if (this.mListener != null) {
            this.mListener.dismissLoading();
        }
    }

    public void updateCalendar(Map<String, ContentBean> statusDatas) {
        if (this.mListener != null) {
            this.mListener.updateCalendar(statusDatas);
        }
    }

    public void loadData(DateUtil dateUtil) {
        this.mPresenter.loadDayDataByTime(dateUtil.getTimestamp());
    }

    public void shareScreen() {
        ScreenLongShareUtils.shareScreenView(getContext(), this.sleepAdapter.getScroll_view());
    }

    public void scrollToPosition(int positionByTime, DateUtil dateUtil) {
        this.rvp_sleep.scrollToPosition(positionByTime);
    }

    public void onDateClickListener() {
        if (getActivity() instanceof SleepShowActivity) {
            ((SleepShowActivity) getActivity()).showCalendarPop();
        }
    }
}
