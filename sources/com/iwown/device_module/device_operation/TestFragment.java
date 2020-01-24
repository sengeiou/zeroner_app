package com.iwown.device_module.device_operation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.iwown.device_module.R;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.fragment.BaseMainFragment;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TestFragment extends BaseMainFragment {
    private Button deviceModuleButton;

    public static TestFragment newInstance() {
        Bundle args = new Bundle();
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_module_test_fragment, container, false);
        initView(view);
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DateUtil event) {
        KLog.i("event.getSyyyyMMddDate()");
    }

    private void initView(View view) {
        this.deviceModuleButton = (Button) view.findViewById(R.id.device_module_button);
        this.deviceModuleButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                KLog.i("=====onClick======");
            }
        });
    }
}
