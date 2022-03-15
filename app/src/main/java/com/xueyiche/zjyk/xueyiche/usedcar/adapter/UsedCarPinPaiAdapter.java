package com.xueyiche.zjyk.xueyiche.usedcar.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.utils.PinyinUtils;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarPinPaiBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Owner on 2018/7/17.
 */
public class UsedCarPinPaiAdapter extends BaseAdapter {
    private final String[] sections;
    private HashMap<String, Integer> letterIndexes;
    private List<UsedCarPinPaiBean.DataBean.BrandListBean> brandList;
    public UsedCarPinPaiAdapter(List<UsedCarPinPaiBean.DataBean.BrandListBean> brandList) {
        this.brandList = brandList;
        if (brandList == null){
            brandList = new ArrayList<>();
        }

        int size = brandList.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前品牌拼音首字母
            String currentLetter = brandList.get(index).getMark();
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(brandList.get(index - 1).getMark()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }

    }
    @Override
    public int getCount() {
        return brandList.size();
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
            convertView = View.inflate(App.context, R.layout.pinpai_listview_yiji_item, null);
            holder = new ViewHolder();
            holder.tv_car_pinpai = (TextView) convertView.findViewById(R.id.tv_car);
            holder.tv_item_pinpai_listview_letter = (TextView) convertView.findViewById(R.id.tv_item_pinpai_listview_letter);
            holder.iv_car_pinpai = (ImageView) convertView.findViewById(R.id.iv_car);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String city = brandList.get(position).getBrand_name();
        holder.tv_car_pinpai.setText(city);
        String currentLetter = brandList.get(position).getMark();
        String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(brandList.get(position - 1).getMark()) : "";
        if (!TextUtils.equals(currentLetter, previousLetter)){
            holder.tv_item_pinpai_listview_letter.setVisibility(View.VISIBLE);
            holder.tv_item_pinpai_listview_letter.setText(currentLetter);
            holder.tv_item_pinpai_listview_letter.setClickable(false);

        }else{
            holder.tv_item_pinpai_listview_letter.setVisibility(View.GONE);
        }
        Picasso.with(App.context).load(brandList.get(position).getBrand_img()).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_car_pinpai);
        return convertView;
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    public static class ViewHolder {
        ImageView iv_car_pinpai;
        TextView tv_car_pinpai;
        TextView tv_item_pinpai_listview_letter;
    }
}
