package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;

import java.util.List;

/**
 * Created by Owner on 2016/11/2.
 */
public class YiJiCaiDanAdapter extends BaseAdapter{
    private Context context;
    private List<String> carType;
    private int selectedPosition = -1;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public YiJiCaiDanAdapter(Context context, List<String> carType) {
        this.context = context;
        this.carType = carType;
    }

    @Override
    public int getCount() {
        return carType.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // 把一个布局xml文件转化成view对象
            convertView = View.inflate(context, R.layout.caidan_shaixuan_item, null);
        }
        TextView cheliang_leixing = (TextView) convertView.findViewById(R.id.cheliang_leixing);
        cheliang_leixing.setText(carType.get(position));
        LinearLayout item_layout = (LinearLayout) convertView.findViewById(R.id.root_item);
        if(selectedPosition == position){
            cheliang_leixing.setTextColor(Color.parseColor("#FF5B11"));
        }else {
            cheliang_leixing.setTextColor(Color.parseColor("#333333"));
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {


        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
