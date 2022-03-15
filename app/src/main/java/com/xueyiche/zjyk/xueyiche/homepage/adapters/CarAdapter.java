package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.bean.CarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2016/11/23.
 */
public class CarAdapter extends BaseAdapter{
    private List<CarBean.ContentBean> mAllCar;
    public CarAdapter(List<CarBean.ContentBean> mAllCar) {
        this.mAllCar = mAllCar;
        if (mAllCar == null){
            mAllCar = new ArrayList<>();
        }
    }



    @Override
    public int getCount() {
        return mAllCar.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(App.context, R.layout.pinpai_listview_erji_item, null);
            holder = new ViewHolder();
            holder.tv_car = (TextView) convertView.findViewById(R.id.tv_car);
            holder.iv_car = (ImageView) convertView.findViewById(R.id.iv_car);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        CarBean.ContentBean contentBean = mAllCar.get(position);
        holder.tv_car.setText(contentBean.getSeriesname());
        //ImageLoader.getInstance().displayImage(contentBean.getSerieslogo(),holder.iv_car);
        Picasso.with(App.context).load(contentBean.getSerieslogo()).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_car);
        return convertView;
    }

    public static class ViewHolder {
        ImageView iv_car;
        TextView tv_car;
    }
}
