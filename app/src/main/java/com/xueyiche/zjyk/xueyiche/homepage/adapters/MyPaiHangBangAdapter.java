package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.bean.PaiHangBean;

import java.util.List;

/**
 * Created by Owner on 2017/1/12.
 */
public class MyPaiHangBangAdapter extends BaseAdapter{
    private List<PaiHangBean.ContentBean> content;

    public MyPaiHangBangAdapter(List<PaiHangBean.ContentBean> content) {
        this.content = content;
    }

    @Override
    public int getCount() {
        return content.size();
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
        if (convertView ==null) {
            convertView = LayoutInflater.from(App.context).inflate(R.layout.my_paihang_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        PaiHangBean.ContentBean contentBean = content.get(position);
        String score = contentBean.getScore();
        String user_phone = contentBean.getUser_phone();
        String time_used = contentBean.getTime_used();
        if (!TextUtils.isEmpty(user_phone)) {
            String substring = user_phone.substring(0, 3);
            String substring1 = user_phone.substring(7, 11);
            String phone = substring + "****" + substring1;
            holder.tv_paihang_yonghu_item.setText(phone);
        }
        if (!TextUtils.isEmpty(time_used)) {
            long ms = Long.parseLong(time_used);
            int ss = 1000;
            int mi = ss * 60;
            long minute = ms / mi;
            long second = (ms - minute * mi) / ss;
            long milliSecond = ms - minute * mi - second * ss;
            String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
            String strSecond = second < 10 ? "0" + second : "" + second;//秒
            String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
            strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
            String substring = strMilliSecond.substring(0, 2);
            holder.tv_paihang_time_item.setText(strMinute + "分" + strSecond + "秒" + substring);
        }
        if (!TextUtils.isEmpty(score)) {
            holder.tv_paihang_fenshu_item.setText(score);
        }
        int i = position + 1;
        holder.tv_paihang_mingci_item.setText(i+"");
        return convertView;
    }

    class ViewHolder{
        private TextView tv_paihang_time_item,tv_paihang_fenshu_item,tv_paihang_yonghu_item,tv_paihang_mingci_item;
        public ViewHolder(View view){
            tv_paihang_time_item = (TextView) view.findViewById(R.id.tv_paihang_time_item);
            tv_paihang_fenshu_item = (TextView) view.findViewById(R.id.tv_paihang_fenshu_item);
            tv_paihang_yonghu_item = (TextView) view.findViewById(R.id.tv_paihang_yonghu_item);
            tv_paihang_mingci_item = (TextView) view.findViewById(R.id.tv_paihang_mingci_item);

        }
    }
}
