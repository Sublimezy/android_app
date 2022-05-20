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

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.controller.ControlWrapper;
import com.hjq.toast.ToastUtils;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.community.cache.PreloadManager;
import com.xueyiche.zjyk.xueyiche.community.view.LikeView;
import com.xueyiche.zjyk.xueyiche.constants.App;

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
                .placeholder(android.R.color.white)
                .into(holder.mThumb);
        holder.mTitle.setText(item.title);
        holder.mPosition = position;
        holder.likeview.setOnLikeListener(new LikeView.OnLikeListener() {
            @Override
            public void onLikeListener() {

                Log.i("喜欢","1111111111");
            }
        });
        holder.likeview.setOnPlayPauseListener(new LikeView.OnPlayPauseListener() {
            @Override
            public void onPlayOrPause() {
                Log.i("喜欢","暂停或者播放");
                holder.mTikTokView.playOrStop();
            }
        });
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

        ViewHolder(View itemView) {
            super(itemView);
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            mTitle = mTikTokView.findViewById(R.id.tv_title);
            mThumb = mTikTokView.findViewById(R.id.iv_thumb);
            likeview = mTikTokView.findViewById(R.id.likeview);
            mPlayerContainer = itemView.findViewById(R.id.container);
            itemView.setTag(this);
        }
    }
}
