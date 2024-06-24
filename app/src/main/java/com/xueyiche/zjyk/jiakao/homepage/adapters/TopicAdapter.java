package com.xueyiche.zjyk.jiakao.homepage.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.homepage.bean.Questiona;

import java.util.List;

/**
 * Created by Owner on 2016/10/17.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder>{
    private Context mContext;
    private final LayoutInflater inflater;
    private final Resources resources;
    private List<Questiona> allQuestionC;

    public TopicAdapter(Context mContext,List<Questiona> allQuestionC) {
        this.mContext = mContext;
        this.allQuestionC = allQuestionC;
        inflater = LayoutInflater.from(mContext);
        resources = mContext.getResources();

    }
    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHolder holder, final int position) {

        holder.tv_id.setText((position + 1) + "");
        holder.tv_id.setTextColor(Color.parseColor("#b3afaf"));
        holder.tv_id.setBackgroundResource(R.drawable.bg_topic_no);
        Questiona questiona = allQuestionC.get(position);
        String a_state = questiona.getA_state();
        String b_state = questiona.getB_state();
        String c_state = questiona.getC_state();
        String d_state = questiona.getD_state();
        if (a_state!="1"||b_state!="1"||c_state!="1"||d_state!="1"){
            if (a_state.equals("2")) {
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_true);
            }else if (a_state.equals("3")){
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_flase);
            }
            if (b_state.equals("2")) {
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_true);
            }else if (b_state.equals("3")){
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_flase);
            }
            if (c_state.equals("2")) {
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_true);
            }else if (c_state.equals("3")){
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_flase);
            }
            if (d_state.equals("2")) {
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_true);
            }else if (d_state.equals("3")){
                holder.tv_id.setBackgroundResource(R.mipmap.bg_topic_flase);
            }
        }
        if (prePosition == position) {
            holder.tv_id.setTextColor(Color.parseColor("#b3afaf"));
        }
        if (curPosition == position) {
            holder.tv_id.setBackgroundResource(R.drawable.bg_topic_ok);
            holder.tv_id.setTextColor(Color.parseColor("#ffffff"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder, position);
            }
        });
    }

    private OnTopicClickListener listener;

    public void setOnTopicClickListener(OnTopicClickListener listener) {
        this.listener = listener;
    }

    private int curPosition;

    public void notifyCurPosition(int curPosition) {
        this.curPosition = curPosition;
        notifyItemChanged(curPosition);
    }

    private int prePosition;

    public void notifyPrePosition(int prePosition) {
        this.prePosition = prePosition;
        notifyItemChanged(prePosition);
    }


    public interface OnTopicClickListener {
        void onClick(TopicViewHolder holder, int position);
    }

    private int num;

    public void setDataNum(int num) {
        this.num = num;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return num;
    }


    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id;

        public TopicViewHolder(View itemView) {
            super(itemView);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
        }
    }
}
