package com.xueyiche.zjyk.jiakao.homepage.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.constants.App;

import com.xueyiche.zjyk.jiakao.homepage.bean.MyResultBean;
import com.xueyiche.zjyk.jiakao.homepage.db.MyResultDB;

import java.util.List;


public class MyResultAdapterD extends BaseAdapter {
    private List<MyResultBean>  list;
    private MyResultDB db = new MyResultDB(App.context);
    @Override
    public int getCount() {
       list = db.findAllResultD();
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView ==null) {
            convertView = LayoutInflater.from(App.context).inflate(R.layout.my_result_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String s = list.get(position).getShijian();
        if (!TextUtils.isEmpty(s)) {
            s = s.substring(0,s.length()-3);
        }

        holder.tvDate.setText(s);
        holder.tvGrade.setText(list.get(position).getFenshu()+"分");
        holder.tvTime.setText(list.get(position).getJishi());
        return convertView;
    }
    class ViewHolder{
        private TextView tvDate,tvGrade,tvTime;
        public ViewHolder(View view){
            tvDate = (TextView) view.findViewById(R.id.my_result_date);
            tvGrade = (TextView) view.findViewById(R.id.my_result_grade);
            tvTime = (TextView) view.findViewById(R.id.my_result_time);

        }
    }
}
