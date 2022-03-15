package com.xueyiche.zjyk.xueyiche.usedcar.bean;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2018/7/17.
 */
public class UsedCarCheXiAdapter extends BaseAdapter {

    private List<UsedCarCheXiBean.DataBean> mAllCar;

    public UsedCarCheXiAdapter(List<UsedCarCheXiBean.DataBean> mAllCar) {
        this.mAllCar = mAllCar;
        if (mAllCar == null) {
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
        UsedCarCheXiBean.DataBean contentBean = mAllCar.get(position);
        holder.tv_car.setText(contentBean.getSystem_name());
        //ImageLoader.getInstance().displayImage(contentBean.getSerieslogo(),holder.iv_car);
        String system_img = contentBean.getSystem_img();
        if (!TextUtils.isEmpty(system_img)) {
            Picasso.with(App.context).load(system_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_car);
        }
        return convertView;
    }

    public static class ViewHolder {
        ImageView iv_car;
        TextView tv_car;
    }
}
