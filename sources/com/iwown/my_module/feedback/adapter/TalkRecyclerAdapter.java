package com.iwown.my_module.feedback.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.entity.MyMedia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.my_module.R;
import com.iwown.my_module.feedback.PictureDisplayActivity;
import com.iwown.my_module.feedback.TalkWebActivity;
import com.iwown.my_module.feedback.data.TalkMsgEntity;
import com.iwown.my_module.feedback.network.response.QuestionData;
import com.iwown.my_module.feedback.view.GlideRoundTransform;
import com.socks.library.KLog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TalkRecyclerAdapter extends Adapter<ViewHolder> implements com.iwown.my_module.feedback.adapter.QuestionsAdapter.OnItemClickListener {
    public static final String MEDIA_LIST = "media_list";
    public static final String MEDIA_PATH = "media_path";
    public static final String POSITION = "position";
    /* access modifiers changed from: private */
    public List<TalkMsgEntity> coll;
    private Gson gson;
    private final int hyperlink = 5;
    private final int isSolved = 2;
    private final int left = 1;
    /* access modifiers changed from: private */
    public Context mContext;
    private int maxHeight = 1;
    private int maxWidth = 1;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;
    private final int picture = 6;
    private final int question = 100;
    private List<TalkMsgEntity> questionList;
    private final int right = 0;
    ArrayList<MyMedia> selectImg;
    private final int solved = 3;
    private final int unsolved = 4;

    class ChoseSolveHolder extends ViewHolder {
        TextView answerTxt;
        TextView solveTxt;
        TextView unsolvedTxt;

        public ChoseSolveHolder(View itemView) {
            super(itemView);
            this.solveTxt = (TextView) itemView.findViewById(R.id.talk_item_solve);
            this.unsolvedTxt = (TextView) itemView.findViewById(R.id.talk_item_unsolved);
            this.answerTxt = (TextView) itemView.findViewById(R.id.item_answer_txt);
        }
    }

    class LeftTxtHolder extends ViewHolder {
        TextView leftTxt;

        public LeftTxtHolder(View itemView) {
            super(itemView);
            this.leftTxt = (TextView) itemView.findViewById(R.id.talk_left_txt);
        }
    }

    public interface OnItemClickListener {
        void imgSendAgain(String str, int i, int i2);

        void onAnswerClick(int i, int i2, String str, String str2);
    }

    class PictureHolder extends ViewHolder {
        ImageView imageView;
        ImageView sendAgain;
        ImageView videoImg;
        TextView videoTime;

        public PictureHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.images_item);
            this.videoImg = (ImageView) itemView.findViewById(R.id.video_img);
            this.videoTime = (TextView) itemView.findViewById(R.id.video_time);
            this.sendAgain = (ImageView) itemView.findViewById(R.id.video_send_again);
        }
    }

    class QuestionHolder extends ViewHolder {
        ImageView pullImg;
        RecyclerView questionRec;

        public QuestionHolder(View itemView) {
            super(itemView);
            this.questionRec = (RecyclerView) itemView.findViewById(R.id.item_recycler);
            this.pullImg = (ImageView) itemView.findViewById(R.id.item_pull_img);
        }
    }

    class RightHolder extends ViewHolder {
        TextView rightTxt;

        public RightHolder(View itemView) {
            super(itemView);
            this.rightTxt = (TextView) itemView.findViewById(R.id.talk_right_txt);
        }
    }

    class SolveHolder extends ViewHolder {
        TextView solveTxt;

        public SolveHolder(View itemView) {
            super(itemView);
            this.solveTxt = (TextView) itemView.findViewById(R.id.item_left4_txt);
        }
    }

    class UnsolvedHolder extends ViewHolder {
        TextView unsolvedBtn;
        TextView unsolvedMsg;

        public UnsolvedHolder(View itemView) {
            super(itemView);
            this.unsolvedBtn = (TextView) itemView.findViewById(R.id.item_feedback_bt);
            this.unsolvedMsg = (TextView) itemView.findViewById(R.id.item_left3_txt);
            this.unsolvedMsg.setText(TalkRecyclerAdapter.this.mContext.getString(R.string.feedback_unsolved_msg1));
        }
    }

    public TalkRecyclerAdapter(Context mContext2, List<TalkMsgEntity> coll2, List<TalkMsgEntity> questionList2) {
        this.mContext = mContext2;
        this.coll = coll2;
        this.questionList = questionList2;
        this.gson = new Gson();
        WindowManager wm = (WindowManager) mContext2.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        this.maxWidth = (outMetrics.widthPixels * 2) / 5;
        this.maxHeight = (outMetrics.heightPixels * 2) / 7;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 100) {
            return new QuestionHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_left1, parent, false));
        }
        if (viewType == 0) {
            return new RightHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_right, parent, false));
        }
        if (viewType == 1 || viewType == 5) {
            return new LeftTxtHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_left2, parent, false));
        }
        if (viewType == 2) {
            return new ChoseSolveHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_left5, parent, false));
        }
        if (viewType == 3) {
            return new SolveHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_left4, parent, false));
        }
        if (viewType == 4) {
            return new UnsolvedHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_left3, parent, false));
        }
        if (viewType == 6) {
            return new PictureHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_image_item, parent, false));
        }
        return new LeftTxtHolder(LayoutInflater.from(this.mContext).inflate(R.layout.my_module_talk_item_left2, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        String msg = ((TalkMsgEntity) this.coll.get(position)).getMessage().toString();
        if (holder instanceof QuestionHolder) {
            final QuestionHolder questionHolder = (QuestionHolder) holder;
            questionHolder.questionRec.setLayoutManager(new LinearLayoutManager(this.mContext, 1, false));
            List<QuestionData> questions = (List) this.gson.fromJson(msg, new TypeToken<List<QuestionData>>() {
            }.getType());
            if (((TalkMsgEntity) this.coll.get(position)).getFirstType() == 2) {
                questionHolder.pullImg.setImageResource(R.mipmap.talk_pull_down3x);
            } else if (((TalkMsgEntity) this.coll.get(position)).getFirstType() == 1) {
                questionHolder.pullImg.setImageResource(R.mipmap.talk_pull_up3x);
            } else {
                questionHolder.pullImg.setImageResource(17170445);
            }
            final QuestionsAdapter questionsAdapter = new QuestionsAdapter(this.mContext, questions, ((TalkMsgEntity) this.coll.get(position)).getFirstType());
            questionsAdapter.setOnItemClickListener(this);
            questionHolder.questionRec.setNestedScrollingEnabled(false);
            questionHolder.questionRec.setAdapter(questionsAdapter);
            final int i = position;
            questionHolder.pullImg.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i)).getFirstType() == 2) {
                        ((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i)).setFirstType(1);
                        questionsAdapter.upAdapter(1);
                        questionHolder.pullImg.setImageResource(R.mipmap.talk_pull_up3x);
                    } else if (((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i)).getFirstType() == 1) {
                        ((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i)).setFirstType(2);
                        questionsAdapter.upAdapter(2);
                        questionHolder.pullImg.setImageResource(R.mipmap.talk_pull_down3x);
                    }
                }
            });
        } else if (holder instanceof RightHolder) {
            ((RightHolder) holder).rightTxt.setText(msg);
        } else if (holder instanceof LeftTxtHolder) {
            LeftTxtHolder txtHolder = (LeftTxtHolder) holder;
            txtHolder.leftTxt.setText(((TalkMsgEntity) this.coll.get(position)).getMessage());
            if (((TalkMsgEntity) this.coll.get(position)).getMegType() == 5) {
                final int i2 = position;
                txtHolder.leftTxt.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(TalkRecyclerAdapter.this.mContext, TalkWebActivity.class);
                        intent.putExtra("url", ((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i2)).getMessage().toString());
                        TalkRecyclerAdapter.this.mContext.startActivity(intent);
                    }
                });
            }
        } else if (holder instanceof ChoseSolveHolder) {
            ChoseSolveHolder choseSolveHolder = (ChoseSolveHolder) holder;
            choseSolveHolder.answerTxt.setText(msg);
            final int i3 = position;
            choseSolveHolder.solveTxt.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (i3 > 0) {
                        TalkRecyclerAdapter.this.clickItemAnswer(((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i3)).getMegType(), i3, "0", "");
                    }
                }
            });
            final int i4 = position;
            choseSolveHolder.unsolvedTxt.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (i4 > 0) {
                        TalkRecyclerAdapter.this.clickItemAnswer(((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i4)).getMegType(), i4, "1", "");
                    }
                }
            });
        } else if (holder instanceof SolveHolder) {
            ((SolveHolder) holder).solveTxt.setText(msg);
        } else if (holder instanceof UnsolvedHolder) {
            UnsolvedHolder unsolvedHolder = (UnsolvedHolder) holder;
            unsolvedHolder.unsolvedMsg.setText(msg);
            final int i5 = position;
            unsolvedHolder.unsolvedBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    TalkRecyclerAdapter.this.clickItemAnswer(((TalkMsgEntity) TalkRecyclerAdapter.this.coll.get(i5)).getMegType(), i5, "0", "");
                }
            });
        } else if (holder instanceof PictureHolder) {
            bindPictureHolder((PictureHolder) holder, position, ((TalkMsgEntity) this.coll.get(position)).isSend());
        }
    }

    public void bindPictureHolder(PictureHolder holder, final int position, boolean isSend) {
        final MyMedia media = (MyMedia) this.gson.fromJson(((TalkMsgEntity) this.coll.get(position)).getMessage().toString(), MyMedia.class);
        Uri uri = Uri.fromFile(new File(media.path));
        if (media.mediaType == 3) {
            if (media.width != 0) {
                setImageArea(media.width, media.height, holder.imageView);
            } else {
                setImageArea(720, 1280, holder.imageView);
            }
            Glide.with(this.mContext).load(uri).transform(new GlideRoundTransform(this.mContext)).into(holder.imageView);
            holder.videoImg.setVisibility(0);
            holder.videoTime.setVisibility(4);
        } else {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            if (BitmapFactory.decodeFile(media.path, options) == null) {
                KLog.d("获取到的bitmap为空", "no2855===");
            }
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;
            KLog.d("kuanggao ：", "no2855框: " + outHeight + " - " + outWidth);
            setImageArea(outWidth, outHeight, holder.imageView);
            Glide.with(this.mContext).load(uri).transform(new GlideRoundTransform(this.mContext)).into(holder.imageView);
            holder.videoImg.setVisibility(4);
            holder.videoTime.setVisibility(4);
        }
        if (isSend) {
            holder.sendAgain.setVisibility(4);
        } else {
            holder.sendAgain.setVisibility(0);
        }
        holder.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TalkRecyclerAdapter.this.mContext, PictureDisplayActivity.class);
                intent.putExtra("position", media.mediaType);
                intent.putExtra(TalkRecyclerAdapter.MEDIA_PATH, media.path);
                TalkRecyclerAdapter.this.mContext.startActivity(intent);
            }
        });
        holder.sendAgain.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TalkRecyclerAdapter.this.onItemClickListener != null) {
                    TalkRecyclerAdapter.this.onItemClickListener.imgSendAgain(media.path, media.mediaType, position);
                }
            }
        });
    }

    public int getItemCount() {
        if (this.coll == null) {
            return 0;
        }
        return this.coll.size();
    }

    public int getItemViewType(int position) {
        if (this.coll != null) {
            return ((TalkMsgEntity) this.coll.get(position)).getMegType();
        }
        return super.getItemViewType(position);
    }

    public void onQuestionClick(int viewType, int position, String answer, String message) {
        clickItemAnswer(viewType, position, answer, message);
    }

    public void clickItemAnswer(int viewType, int position, String answer, String message) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onAnswerClick(viewType, position, answer, message);
        }
    }

    private void setImageArea(int outWidth, int outHeight, ImageView imageView) {
        LayoutParams params = imageView.getLayoutParams();
        int num = Math.max(outWidth / this.maxWidth, outHeight / this.maxHeight);
        if (num <= 0) {
            num = 1;
        }
        if (outWidth / num > this.maxWidth || outHeight / num > this.maxHeight) {
            num++;
        }
        params.width = outWidth / num;
        params.height = outHeight / num;
        imageView.setLayoutParams(params);
    }

    public void setSelectImg(ArrayList<MyMedia> medias) {
        this.selectImg = medias;
    }
}
