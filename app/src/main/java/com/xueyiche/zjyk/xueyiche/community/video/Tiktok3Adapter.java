package com.xueyiche.zjyk.xueyiche.community.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.community.OnVideoControllerListener;
import com.xueyiche.zjyk.xueyiche.community.cache.PreloadManager;
import com.xueyiche.zjyk.xueyiche.community.view.IconFontTextView;
import com.xueyiche.zjyk.xueyiche.community.view.LikeView;

import java.util.List;

public class Tiktok3Adapter extends RecyclerView.Adapter<Tiktok3Adapter.ViewHolder> {

    /**
     * 数据源
     */
    private List<TiktokBean> mVideoBeans;

    public Tiktok3Adapter(List<TiktokBean> videoBeans) {
        this.mVideoBeans = videoBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tok, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Context context = holder.itemView.getContext();
        TiktokBean item = mVideoBeans.get(position);
        //开始预加载
        PreloadManager.getInstance(context).addPreloadTask(item.videoDownloadUrl, position);
        Glide.with(context)
                .load(item.coverImgUrl)
                .placeholder(android.R.color.black)
                .into(holder.mThumb);
        holder.mTitle.setText(item.title);
        holder.mPosition = position;
        holder.likeview.setOnLikeListener(new LikeView.OnLikeListener() {
            @Override
            public void onLikeListener() {

                if (!item.isLiked) {

                    Log.i("喜欢", "1111111111");
                    like(item, holder.ivLike, holder.animationView, context);
                }
            }
        });
        holder.likeview.setOnPlayPauseListener(new LikeView.OnPlayPauseListener() {
            @Override
            public void onPlayOrPause() {
                holder.mTikTokView.playOrStop();
            }
        });
        holder.animationView.setAnimation("like.json");
        //点赞状态
        if (item.isLiked) {
            holder.ivLike.setTextColor(context.getResources().getColor(R.color.color_FF0041));
        } else {
            holder.ivLike.setTextColor(context.getResources().getColor(R.color.white));
        }

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                like(item, holder.ivLike, holder.animationView, context);
            }
        });
        holder.tvLikecount.setText("0");
        holder.tvCommentcount.setText("0");
        holder.tvSharecount.setText("0");
        holder.iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener == null) {
                    return;
                }

                listener.onCommentClick();
            }
        });
    }
    private OnVideoControllerListener listener;

    public void setListener(OnVideoControllerListener listener) {
        this.listener = listener;
    }

    /**
     * 点赞动作
     *
     * @param ivLike
     * @param animationView
     * @param context
     */
    public void like(TiktokBean item, IconFontTextView ivLike, LottieAnimationView animationView, Context context) {

        if (!item.isLiked) {
            //点赞
            animationView.setVisibility(View.VISIBLE);
            animationView.playAnimation();
            ivLike.setTextColor(context.getResources().getColor(R.color.color_FF0041));
        } else {
            //取消点赞
            animationView.setVisibility(View.INVISIBLE);
            ivLike.setTextColor(context.getResources().getColor(R.color.white));
        }
        item.isLiked = !item.isLiked;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        TiktokBean item = mVideoBeans.get(holder.mPosition);
        //取消预加载
        PreloadManager.getInstance(holder.itemView.getContext()).removePreloadTask(item.videoDownloadUrl);
    }

    @Override
    public int getItemCount() {
        return mVideoBeans != null ? mVideoBeans.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int mPosition;
        public TextView mTitle;//标题
        public ImageView mThumb;//封面图
        public TikTokView mTikTokView;
        public FrameLayout mPlayerContainer;
        public LikeView likeview;
        IconFontTextView ivLike;
        IconFontTextView iv_comment;
        LottieAnimationView animationView;

        public TextView tvLikecount;
        public TextView tvCommentcount;
        public TextView tvSharecount;

        ViewHolder(View itemView) {
            super(itemView);
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            mTitle = mTikTokView.findViewById(R.id.tv_title);
            mThumb = mTikTokView.findViewById(R.id.iv_thumb);
            likeview = mTikTokView.findViewById(R.id.likeview);
            mPlayerContainer = itemView.findViewById(R.id.container);
            ivLike = mTikTokView.findViewById(R.id.iv_like);
            animationView = mTikTokView.findViewById(R.id.lottie_anim);
            iv_comment = mTikTokView.findViewById(R.id.iv_comment);

            tvLikecount = mTikTokView.findViewById(R.id.tv_likecount);
            tvCommentcount = mTikTokView.findViewById(R.id.tv_commentcount);
            tvSharecount = mTikTokView.findViewById(R.id.tv_sharecount);

            itemView.setTag(this);
        }
    }
}
