package com.iwown.my_module.feedback.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.iwown.my_module.R;
import com.iwown.my_module.feedback.network.response.QuestionData;
import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends Adapter<MyHolder> {
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private List<QuestionData> questionList;
    /* access modifiers changed from: private */
    public List<QuestionData> showList = new ArrayList();
    private int type = 1;

    class MyHolder extends ViewHolder {
        TextView txtView;

        public MyHolder(View itemView) {
            super(itemView);
            this.txtView = (TextView) itemView.findViewById(R.id.question_item_txt);
        }
    }

    public interface OnItemClickListener {
        void onQuestionClick(int i, int i2, String str, String str2);
    }

    public QuestionsAdapter(Context mContext2, List<QuestionData> questionList2, int type2) {
        this.mContext = mContext2;
        this.questionList = questionList2;
        if (type2 != 2 || questionList2 == null || questionList2.size() <= 5) {
            this.showList.addAll(questionList2);
        } else {
            this.showList.addAll(questionList2.subList(0, 5));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    /* access modifiers changed from: private */
    public void setClickCallback(int viewType, int position, String answer, String message) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onQuestionClick(viewType, position, answer, message);
        }
    }

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_qusetion_item, parent, false));
    }

    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.txtView.setText(((QuestionData) this.showList.get(position)).getQuestion());
        holder.txtView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                QuestionsAdapter.this.setClickCallback(100, position, ((QuestionData) QuestionsAdapter.this.showList.get(position)).getAnswerCode(), ((QuestionData) QuestionsAdapter.this.showList.get(position)).getQuestion());
            }
        });
    }

    public int getItemCount() {
        if (this.showList == null) {
            return 0;
        }
        return this.showList.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    public void upAdapter(int type2) {
        this.type = type2;
        if (this.questionList != null) {
            this.showList.clear();
            if (type2 != 2 || this.questionList.size() <= 5) {
                this.showList.addAll(this.questionList);
            } else {
                this.showList.addAll(this.questionList.subList(0, 5));
            }
        }
        notifyDataSetChanged();
    }
}
