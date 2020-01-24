package me.yokeyword.fragmentation.debug;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentationMagician;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.iwown.lib_common.R;
import java.util.ArrayList;
import java.util.List;

public class DebugStackDelegate implements SensorEventListener {
    private FragmentActivity mActivity;
    private SensorManager mSensorManager;
    private AlertDialog mStackDialog;

    private class StackViewTouchListener implements OnTouchListener {
        private int clickLimitValue;
        private float dX;
        private float dY = 0.0f;
        private float downX;
        private float downY = 0.0f;
        private boolean isClickState;
        private View stackView;

        StackViewTouchListener(View stackView2, int clickLimitValue2) {
            this.stackView = stackView2;
            this.clickLimitValue = clickLimitValue2;
        }

        public boolean onTouch(View v, MotionEvent event) {
            float X = event.getRawX();
            float Y = event.getRawY();
            switch (event.getAction()) {
                case 0:
                    this.isClickState = true;
                    this.downX = X;
                    this.downY = Y;
                    this.dX = this.stackView.getX() - event.getRawX();
                    this.dY = this.stackView.getY() - event.getRawY();
                    break;
                case 1:
                case 3:
                    if (X - this.downX < ((float) this.clickLimitValue) && this.isClickState) {
                        this.stackView.performClick();
                        break;
                    }
                case 2:
                    if (Math.abs(X - this.downX) < ((float) this.clickLimitValue) && Math.abs(Y - this.downY) < ((float) this.clickLimitValue) && this.isClickState) {
                        this.isClickState = true;
                        break;
                    } else {
                        this.isClickState = false;
                        this.stackView.setX(event.getRawX() + this.dX);
                        this.stackView.setY(event.getRawY() + this.dY);
                        break;
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    public DebugStackDelegate(FragmentActivity activity) {
        this.mActivity = activity;
    }

    public void onCreate(int mode) {
        if (mode == 1) {
            this.mSensorManager = (SensorManager) this.mActivity.getSystemService("sensor");
            this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(1), 3);
        }
    }

    public void onPostCreate(int mode) {
        if (mode == 2) {
            View root = this.mActivity.findViewById(16908290);
            if (root instanceof FrameLayout) {
                FrameLayout content = (FrameLayout) root;
                ImageView stackView = new ImageView(this.mActivity);
                stackView.setImageResource(R.drawable.fragmentation_ic_stack);
                LayoutParams params = new LayoutParams(-2, -2);
                params.gravity = GravityCompat.END;
                int dp18 = (int) TypedValue.applyDimension(1, 18.0f, this.mActivity.getResources().getDisplayMetrics());
                params.topMargin = dp18 * 7;
                params.rightMargin = dp18;
                stackView.setLayoutParams(params);
                content.addView(stackView);
                stackView.setOnTouchListener(new StackViewTouchListener(stackView, dp18 / 4));
                stackView.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        DebugStackDelegate.this.showFragmentStackHierarchyView();
                    }
                });
            }
        }
    }

    public void onDestroy() {
        if (this.mSensorManager != null) {
            this.mSensorManager.unregisterListener(this);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float[] values = event.values;
        if (sensorType != 1) {
            return;
        }
        if (Math.abs(values[0]) >= ((float) 12) || Math.abs(values[1]) >= ((float) 12) || Math.abs(values[2]) >= ((float) 12)) {
            showFragmentStackHierarchyView();
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void showFragmentStackHierarchyView() {
        if (this.mStackDialog == null || !this.mStackDialog.isShowing()) {
            DebugHierarchyViewContainer container = new DebugHierarchyViewContainer(this.mActivity);
            container.bindFragmentRecords(getFragmentRecords());
            container.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            this.mStackDialog = new Builder(this.mActivity).setView((View) container).setPositiveButton(17039360, (DialogInterface.OnClickListener) null).setCancelable(true).create();
            this.mStackDialog.show();
        }
    }

    public void logFragmentRecords(String tag) {
        List<DebugFragmentRecord> fragmentRecordList = getFragmentRecords();
        if (fragmentRecordList != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = fragmentRecordList.size() - 1; i >= 0; i--) {
                DebugFragmentRecord fragmentRecord = (DebugFragmentRecord) fragmentRecordList.get(i);
                if (i == fragmentRecordList.size() - 1) {
                    sb.append("═══════════════════════════════════════════════════════════════════════════════════\n");
                    if (i == 0) {
                        sb.append("\t栈顶\t\t\t").append(fragmentRecord.fragmentName).append("\n");
                        sb.append("═══════════════════════════════════════════════════════════════════════════════════");
                    } else {
                        sb.append("\t栈顶\t\t\t").append(fragmentRecord.fragmentName).append("\n\n");
                    }
                } else if (i == 0) {
                    sb.append("\t栈底\t\t\t").append(fragmentRecord.fragmentName).append("\n\n");
                    processChildLog(fragmentRecord.childFragmentRecord, sb, 1);
                    sb.append("═══════════════════════════════════════════════════════════════════════════════════");
                    Log.i(tag, sb.toString());
                    return;
                } else {
                    sb.append("\t↓\t\t\t").append(fragmentRecord.fragmentName).append("\n\n");
                }
                processChildLog(fragmentRecord.childFragmentRecord, sb, 1);
            }
        }
    }

    private List<DebugFragmentRecord> getFragmentRecords() {
        List<DebugFragmentRecord> fragmentRecordList = new ArrayList<>();
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(this.mActivity.getSupportFragmentManager());
        if (fragmentList == null || fragmentList.size() < 1) {
            return null;
        }
        for (Fragment fragment : fragmentList) {
            addDebugFragmentRecord(fragmentRecordList, fragment);
        }
        return fragmentRecordList;
    }

    private void processChildLog(List<DebugFragmentRecord> fragmentRecordList, StringBuilder sb, int childHierarchy) {
        if (fragmentRecordList != null && fragmentRecordList.size() != 0) {
            for (int j = 0; j < fragmentRecordList.size(); j++) {
                DebugFragmentRecord childFragmentRecord = (DebugFragmentRecord) fragmentRecordList.get(j);
                for (int k = 0; k < childHierarchy; k++) {
                    sb.append("\t\t\t");
                }
                if (j == 0) {
                    sb.append("\t子栈顶\t\t").append(childFragmentRecord.fragmentName).append("\n\n");
                } else if (j == fragmentRecordList.size() - 1) {
                    sb.append("\t子栈底\t\t").append(childFragmentRecord.fragmentName).append("\n\n");
                    processChildLog(childFragmentRecord.childFragmentRecord, sb, childHierarchy + 1);
                    return;
                } else {
                    sb.append("\t↓\t\t\t").append(childFragmentRecord.fragmentName).append("\n\n");
                }
                processChildLog(childFragmentRecord.childFragmentRecord, sb, childHierarchy);
            }
        }
    }

    private List<DebugFragmentRecord> getChildFragmentRecords(Fragment parentFragment) {
        List<DebugFragmentRecord> fragmentRecords = new ArrayList<>();
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(parentFragment.getChildFragmentManager());
        if (fragmentList == null || fragmentList.size() < 1) {
            return null;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            addDebugFragmentRecord(fragmentRecords, (Fragment) fragmentList.get(i));
        }
        return fragmentRecords;
    }

    private void addDebugFragmentRecord(List<DebugFragmentRecord> fragmentRecords, Fragment fragment) {
        if (fragment != null) {
            int backStackCount = fragment.getFragmentManager().getBackStackEntryCount();
            CharSequence name = fragment.getClass().getSimpleName();
            if (backStackCount == 0) {
                name = span(name);
            } else {
                int j = 0;
                while (j < backStackCount && !fragment.getFragmentManager().getBackStackEntryAt(j).getName().equals(fragment.getTag())) {
                    if (j == backStackCount - 1) {
                        name = span(name);
                    }
                    j++;
                }
            }
            fragmentRecords.add(new DebugFragmentRecord(name, getChildFragmentRecords(fragment)));
        }
    }

    @NonNull
    private CharSequence span(CharSequence name) {
        return name + " *";
    }
}
