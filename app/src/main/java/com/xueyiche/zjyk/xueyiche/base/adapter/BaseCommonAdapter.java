package com.xueyiche.zjyk.xueyiche.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;

import java.util.List;

/**
 * Created by ZL on 2017/3/18.
 */

/**
 * 基类的适配器
 * @param <T>
 */
public abstract class BaseCommonAdapter<T> extends BaseAdapter {
    protected List<T> mDatas;
    protected Context context;
    private int layoutId;



    public BaseCommonAdapter(List<T> mDatas, Context context, int layoutId) {
        this.mDatas = mDatas;
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (mDatas!=null) {
            return mDatas.size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getItem(int i) {
        if (mDatas!=null) {
            return mDatas.get(i);
        }else {
            return i;
        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHoder = BaseViewHolder.getViewHolder(context, position, convertView, parent, layoutId);
        if (mDatas!=null&&mDatas.size()>0) {
            convert(viewHoder, mDatas.get(position));
        }
        return viewHoder.getmConvertView();
    }

    protected abstract void convert(BaseViewHolder viewHolder, Object item);

}
