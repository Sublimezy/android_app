package com.xueyiche.zjyk.xueyiche.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;

/**
 * Created by ZL on 2017/3/18.
 */

/**
 * 基类的ViewHolder
 */
public class BaseViewHolder {
    private Context context;
    private SparseArray<View> mView;
    private int mPosition;
    private View mConvertView;

    public BaseViewHolder(Context context, int position, ViewGroup parent, int layoutId) {
        mPosition = position;
        mView = new SparseArray<View>();
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
    }

    public static BaseViewHolder getViewHolder(Context context, int position,
                                               View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new BaseViewHolder(context, position, parent, layoutId);
        } else {
            BaseViewHolder viewHolder = (BaseViewHolder) convertView.getTag();
            viewHolder.mPosition = position;

            return viewHolder;
        }
    }

    public <T extends View> T getView(int layoutId) {
        View view = mView.get(layoutId);
        if (view == null) {
            view = getmConvertView().findViewById(layoutId);
            mView.put(layoutId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    //TextView设置数据
    public BaseViewHolder setText(int viewId, String txt) {
        TextView mTextView = getView(viewId);
        if (TextUtils.isEmpty(txt)) {
            mTextView.setText(" ");
        } else {
            mTextView.setText(txt);
        }
        return this;
    }

    /**
     * 设置网络图片
     * @param viewId
     * @param url
     * @return
     */
    public BaseViewHolder setPic(int viewId, String url) {
        ImageView mImageView = getView(viewId);
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(App.context).load(url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(mImageView);
        }
        return this;
    }
    public BaseViewHolder setPicRound(int viewId, String url) {
        RoundImageView mImageView = getView(viewId);
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(App.context).load(url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(mImageView);
        }
        return this;
    }

    /**
     * 设置本地图片
     * @param viewId
     * @param pic
     * @return
     */
    public BaseViewHolder setLocalPic(int viewId,int pic) {
        ImageView mImageView = getView(viewId);
        mImageView.setImageResource(pic);
        return this;
    }
    public BaseViewHolder setPicCommon(int viewId, String url,int placeholder,int error) {
        ImageView mImageView = getView(viewId);
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(App.context).load(url).placeholder(placeholder).error(error).into(mImageView);
        }
        return this;
    }

    public BaseViewHolder setPicHead(int viewId, String url) {
        CircleImageView mImageView = getView(viewId);
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(App.context).load(url).placeholder(R.mipmap.choice_card_details_head).error(R.mipmap.choice_card_details_head).into(mImageView);
        }
        return this;
    }

    //更换购买人数图标
    public BaseViewHolder changeIV(int viewId) {
        ImageView mImageView = getView(viewId);
        mImageView.setImageResource(R.mipmap.car_buy_number);
        return this;
    }

    public BaseViewHolder changeImageGone(int viewId) {
        ImageView mImageView = getView(viewId);
        mImageView.setVisibility(View.GONE);
        return this;
    }
    public BaseViewHolder changeTextGone(int viewId) {
        TextView mImageView = getView(viewId);
        mImageView.setVisibility(View.GONE);
        return this;
    }

    public BaseViewHolder changeImageVisible(int viewId) {
        ImageView mImageView = getView(viewId);
        mImageView.setVisibility(View.VISIBLE);
        return this;
    }

    public BaseViewHolder changeLinearLayoutVisible(int viewId) {
        LinearLayout mLinearLayout = getView(viewId);
        mLinearLayout.setVisibility(View.VISIBLE);
        return this;
    }

    public BaseViewHolder changeLinearLayoutGone(int viewId) {
        LinearLayout mLinearLayout = getView(viewId);
        mLinearLayout.setVisibility(View.GONE);
        return this;
    }
    public BaseViewHolder changeLinearLayoutInVisible(int viewId) {
        LinearLayout mLinearLayout = getView(viewId);
        mLinearLayout.setVisibility(View.INVISIBLE);
        return this;
    }

    public BaseViewHolder textUpLine(int viewId) {
        TextView textview = getView(viewId);
        //中划线
        textview.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        return this;
    }


    public BaseViewHolder changeTextColor(int viewId,String color) {
        TextView textview = getView(viewId);
        textview.setTextColor(Color.parseColor(color));
        return this;
    }
}
