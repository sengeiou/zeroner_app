package com.iwown.sport_module.ui.ecg;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.ecg.EcgDataNoteNet;
import com.iwown.data_link.ecg.EcgViewDataBean;
import com.iwown.data_link.ecg.ModuleRouterEcgService;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.view.ecg.EditTextDialog;
import com.iwown.sport_module.view.ecg.EditTextDialog.OnTextConfirmListener;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.Charsets;

public class EcgViewItemAdapter extends BaseQuickAdapter<EcgViewDataBean, EcgViewHolder> {
    List<Boolean> clickList = new ArrayList();
    List<Boolean> falseList = new ArrayList();
    private boolean flag;
    EcgOnclickListener listener;

    interface EcgOnclickListener {
        void onEcgClick(int i);
    }

    public static class EcgViewHolder extends BaseViewHolder {
        /* access modifiers changed from: private */
        public ConstraintLayout constraintLayout;
        /* access modifiers changed from: private */
        public ConstraintLayout constraintLayoutBottom;
        /* access modifiers changed from: private */
        public EditTextDialog dialog;
        /* access modifiers changed from: private */
        public TextView ecg_avg_value;
        /* access modifiers changed from: private */
        public EditText ecg_note_edit;
        private ImageView ecg_note_edit_icon;
        /* access modifiers changed from: private */
        public TextView ecg_test_time;
        /* access modifiers changed from: private */
        public ImageView userNote;

        public EcgViewHolder(View itemView) {
            super(itemView);
            this.ecg_test_time = (TextView) itemView.findViewById(R.id.ecg_test_time_1);
            this.userNote = (ImageView) itemView.findViewById(R.id.ecg_use_edit_icon);
            this.ecg_avg_value = (TextView) itemView.findViewById(R.id.ecg_heart_avg_1);
            this.ecg_note_edit_icon = (ImageView) itemView.findViewById(R.id.ecg_use_edit_note);
            this.ecg_note_edit = (EditText) itemView.findViewById(R.id.ecg_use_note);
            this.constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.top_layout_1);
            this.constraintLayoutBottom = (ConstraintLayout) itemView.findViewById(R.id.bottom_item_layout_1);
            this.dialog = new EditTextDialog(itemView.getContext(), "");
        }
    }

    public EcgOnclickListener getListener() {
        return this.listener;
    }

    public void setListener(EcgOnclickListener listener2) {
        this.listener = listener2;
    }

    public EcgViewItemAdapter(@Nullable List<EcgViewDataBean> data) {
        super(R.layout.sport_module_item_ecg_layout, data);
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                this.clickList.add(Boolean.valueOf(false));
                this.falseList.add(Boolean.valueOf(false));
            }
        }
    }

    public void onBindViewHolder(final EcgViewHolder holder, final int positions) {
        super.onBindViewHolder(holder, positions);
        if (((Boolean) this.clickList.get(positions)).booleanValue()) {
            holder.ecg_test_time.setTextColor(-1);
            holder.ecg_avg_value.setTextColor(-1);
            holder.constraintLayoutBottom.setVisibility(0);
        } else {
            holder.constraintLayoutBottom.setVisibility(8);
            holder.ecg_test_time.setTextColor(this.mContext.getResources().getColor(R.color.sport_module_ecg_text_1));
            holder.ecg_avg_value.setTextColor(this.mContext.getResources().getColor(R.color.sport_module_ecg_text_1));
        }
        holder.constraintLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (view.getId() == R.id.top_layout_1) {
                    if (holder.constraintLayoutBottom.getVisibility() == 0) {
                        holder.constraintLayoutBottom.setVisibility(8);
                    } else {
                        holder.constraintLayoutBottom.setVisibility(0);
                        holder.ecg_test_time.setTextColor(-1);
                        holder.ecg_avg_value.setTextColor(-1);
                    }
                    EcgViewItemAdapter.this.clickList.clear();
                    EcgViewItemAdapter.this.clickList.addAll(EcgViewItemAdapter.this.falseList);
                    EcgViewItemAdapter.this.clickList.set(positions, Boolean.valueOf(true));
                    EcgViewItemAdapter.this.notifyDataSetChanged();
                }
                if (EcgViewItemAdapter.this.listener != null) {
                    EcgViewItemAdapter.this.listener.onEcgClick(positions);
                }
            }
        });
        holder.dialog.setOnTextConfirmListener(new OnTextConfirmListener() {
            public void OnConfirm(String text) {
                holder.ecg_note_edit.setText(text);
                EcgViewDataBean ecgViewDataBean = (EcgViewDataBean) EcgViewItemAdapter.this.getData().get(positions);
                ecgViewDataBean.setNote(holder.ecg_note_edit.getText().toString());
                ModuleRouterEcgService.getInstance().saveEcgNote(ecgViewDataBean);
                if (!TextUtils.isEmpty(ecgViewDataBean.getNote())) {
                    EcgDataNoteNet enn = new EcgDataNoteNet();
                    enn.setUid(ecgViewDataBean.getUid());
                    enn.setData_from(ecgViewDataBean.getData_from());
                    enn.setStart_time(ecgViewDataBean.getDate());
                    enn.setHr(ecgViewDataBean.getHeartrate());
                    enn.setNote(ecgViewDataBean.getNote());
                    enn.setUrl(ecgViewDataBean.getUrl());
                    NetFactory.getInstance().getClient(new MyCallback() {
                        public void onSuccess(Object o) {
                        }

                        public void onFail(Throwable e) {
                        }
                    }).updateEcgNote(enn);
                }
                if (holder.constraintLayoutBottom.getVisibility() == 0) {
                    holder.constraintLayoutBottom.setVisibility(8);
                } else {
                    holder.constraintLayoutBottom.setVisibility(0);
                    holder.ecg_test_time.setTextColor(-1);
                    holder.ecg_avg_value.setTextColor(-1);
                }
                if (holder.dialog != null) {
                    holder.dialog.dismiss();
                }
            }

            public void OnTextChanged(String text) {
                if (TextUtils.isEmpty(text)) {
                    holder.dialog.setCanConfirm(true);
                } else if (text.getBytes(Charsets.UTF_8).length > 200) {
                    holder.dialog.setCanConfirm(false);
                } else {
                    holder.dialog.setCanConfirm(true);
                }
            }

            public void OnCancel() {
            }
        });
        if (positions == 0 && !this.flag) {
            this.flag = true;
            holder.constraintLayout.postDelayed(new Runnable() {
                public void run() {
                    holder.constraintLayout.performClick();
                }
            }, 300);
        }
    }

    /* access modifiers changed from: protected */
    public void convert(final EcgViewHolder helper, final EcgViewDataBean item) {
        helper.ecg_test_time.setText(new DateUtil(item.getUnixTime(), true).getHHmmDate());
        if (TextUtils.isEmpty(item.getNote())) {
            helper.userNote.setVisibility(8);
            helper.ecg_note_edit.setText("");
        } else {
            helper.userNote.setVisibility(0);
            KLog.i("------" + item.getNote());
            helper.ecg_note_edit.setText(item.getNote());
        }
        Spannable span = new SpannableString(this.mContext.getString(R.string.sport_module_page_ecg_5, new Object[]{Integer.valueOf(item.getHeartrate())}));
        span.setSpan(new RelativeSizeSpan(1.5f), 3, 6, 33);
        helper.ecg_avg_value.setText(span);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), helper.ecg_avg_value);
        helper.ecg_note_edit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                helper.dialog.show();
                helper.dialog.setEditHint(item.getNote());
            }
        });
    }
}
