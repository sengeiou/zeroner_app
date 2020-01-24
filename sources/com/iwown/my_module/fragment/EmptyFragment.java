package com.iwown.my_module.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.my_module.R;
import com.iwown.my_module.model.ThemeConfig;
import com.iwown.my_module.utility.ScreenUtility;

public class EmptyFragment extends Fragment {
    private LinearLayout mActionBarLayout;
    private LinearLayout mEmptyContentArea;
    private TextView mTitleView;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_module_fragment_empty, container, false);
        this.mActionBarLayout = (LinearLayout) view.findViewById(R.id.titlebar_layout);
        if (this.mActionBarLayout != null) {
            this.mActionBarLayout.setPadding(0, ScreenUtility.getStatusBarHeight(), 0, 0);
        }
        this.mTitleView = (TextView) view.findViewById(R.id.title);
        if (getArguments() != null) {
            this.mTitleView.setText(getArguments().getString("title"));
        }
        this.mEmptyContentArea = (LinearLayout) view.findViewById(R.id.empty_fragment_content);
        int theme = new PreferenceUtility(getActivity()).fetchNumberValueWithKey(ThemeConfig.THEME_KEY);
        if (theme == 0 || theme > 2) {
            theme = 1;
        }
        if (theme == 1) {
            this.mActionBarLayout.setBackgroundColor(getResources().getColor(R.color.dark_theme_title_background_color));
            this.mTitleView.setTextColor(getResources().getColor(R.color.dark_theme_title_text_color));
            this.mEmptyContentArea.setBackgroundColor(getResources().getColor(R.color.dark_theme_background_color));
        } else {
            this.mActionBarLayout.setBackgroundColor(getResources().getColor(R.color.light_theme_title_background_color));
            this.mTitleView.setTextColor(getResources().getColor(R.color.light_theme_title_text_color));
            this.mEmptyContentArea.setBackgroundColor(getResources().getColor(R.color.light_theme_background_color));
        }
        return view;
    }

    public void onResume() {
        super.onResume();
    }
}
