package com.gxuwz.zy.exam.adapter.myresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.exam.entity.dos.MyGradeBean;

import java.util.List;

public class MyResultAdapter extends BaseAdapter {

    private final Context mContext;

    private final List<MyGradeBean> myGradeBeanList;

    public MyResultAdapter(Context mContext, List<MyGradeBean> myGradeBeanList) {
        this.mContext = mContext;
        this.myGradeBeanList = myGradeBeanList;
    }

    @Override
    public int getCount() {
        return myGradeBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return myGradeBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.myresult_item, null);
            viewHolder = new ViewHolder();
            viewHolder.score = convertView.findViewById(R.id.score);
            viewHolder.take_time = convertView.findViewById(R.id.take_time);
            viewHolder.un_answer = convertView.findViewById(R.id.un_answer);
            viewHolder.mistakes = convertView.findViewById(R.id.mistakes);
            viewHolder.exam_time = convertView.findViewById(R.id.exam_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyGradeBean myGradeBean = myGradeBeanList.get(position);

        viewHolder.score.setText("考试得分: " + myGradeBean.getScore());
        viewHolder.take_time.setText("总用时: " + myGradeBean.getTakeTime());
        viewHolder.un_answer.setText("未答题数: " + myGradeBean.getUnAnsweredNum());
        viewHolder.mistakes.setText("错题数: " + myGradeBean.getMistakesNum());
        viewHolder.exam_time.setText("考题时间: " + myGradeBean.getExamDate());

        return convertView;
    }


    public final class ViewHolder {
        public TextView score;
        public TextView take_time;
        public TextView un_answer;
        public TextView mistakes;
        public TextView exam_time;
    }


}
