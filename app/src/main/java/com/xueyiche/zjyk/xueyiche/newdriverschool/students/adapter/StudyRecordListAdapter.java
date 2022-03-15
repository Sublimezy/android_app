package com.xueyiche.zjyk.xueyiche.newdriverschool.students.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.StudyRecordBean;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 張某人 on 6/22/21/1:59 PM .
 * #            com.xueyiche.zjyk.xueyiche.newdriverschool.students.adapter
 * #            xueyiche3.0
 */
public class StudyRecordListAdapter extends BaseQuickAdapter<StudyRecordBean.ContentBean, BaseViewHolder> {
    public StudyRecordListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StudyRecordBean.ContentBean item) {
        TextView tvTitle = helper.getView(R.id.tvTitle);
        TextView tvContent = helper.getView(R.id.tvContent);
        TextView tvTime = helper.getView(R.id.tvTime);
        String sub_systime = item.getSub_systime();
        String subject_backup = item.getSubject_backup();
        String subject_name = item.getSubject_name();
        if (!TextUtils.isEmpty(subject_name)) {
            tvTitle.setText(subject_name);

        }
        if (!TextUtils.isEmpty(sub_systime)) {
            tvTime.setText(sub_systime);
        }
        if (!TextUtils.isEmpty(subject_backup)) {
            tvContent.setText(subject_backup);
        }
    }
}
