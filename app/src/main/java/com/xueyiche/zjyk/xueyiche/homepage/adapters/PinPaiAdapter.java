package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.bean.PinPaiBean;
import com.xueyiche.zjyk.xueyiche.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ZL on 2017/10/27.
 */
public class PinPaiAdapter extends BaseAdapter{
    private final String[] sections;
    private HashMap<String, Integer> letterIndexes;
    private List<PinPaiBean.ContentBean> mAllPinPai;

    public PinPaiAdapter(List<PinPaiBean.ContentBean> mAllPinPai) {
        this.mAllPinPai = mAllPinPai;
        if (mAllPinPai == null){
            mAllPinPai = new ArrayList<>();
        }

        int size = mAllPinPai.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前品牌拼音首字母
            String currentLetter = mAllPinPai.get(index).getPinyin();
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mAllPinPai.get(index - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    @Override
    public int getCount() {
        return mAllPinPai.size();
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
        final String city = mAllPinPai.get(position).getName();
        holder.tv_car_pinpai.setText(city);
        String currentLetter = mAllPinPai.get(position).getPinyin();
        String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mAllPinPai.get(position - 1).getPinyin()) : "";
        if (!TextUtils.equals(currentLetter, previousLetter)){
            holder.tv_item_pinpai_listview_letter.setVisibility(View.VISIBLE);
            holder.tv_item_pinpai_listview_letter.setText(currentLetter);
            holder.tv_item_pinpai_listview_letter.setClickable(false);

        }else{
            holder.tv_item_pinpai_listview_letter.setVisibility(View.GONE);
        }
        Picasso.with(App.context).load(mAllPinPai.get(position).getLogo()).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.iv_car_pinpai);
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
