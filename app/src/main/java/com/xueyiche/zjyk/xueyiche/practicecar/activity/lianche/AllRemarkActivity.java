package com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.bean.DriverDetails;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;

import java.util.List;

/**
 * Created by ZL on 2020/2/14.
 */
public class AllRemarkActivity extends BaseActivity {
    private ImageView iv_Back;
    private AdListView lv_all_remark;
    private TextView tv_title;
    private List<DriverDetails.ContentBean.PinglunBean> pinglun;

    @Override
    protected int initContentView() {
        return R.layout.all_remark_list_activity;
    }

    @Override
    protected void initView() {
        iv_Back = view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_title = view.findViewById(R.id.title).findViewById(R.id.tv_title);
        lv_all_remark = view.findViewById(R.id.lv_all_remark);
    }

    @Override
    protected void initListener() {
        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        tv_title.setText("全部评价");
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        if (!TextUtils.isEmpty(json)) {
            DriverDetails driverDetails = JsonUtil.parseJsonToBean(json, DriverDetails.class);
            if (driverDetails != null) {
                final DriverDetails.ContentBean content = driverDetails.getContent();
                if (content != null) {
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pinglun = content.getPinglun();
                            lv_all_remark.setAdapter(new RemarkAdapter());
                        }
                    });
                }
            }
        }
    }

    private class RemarkAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pinglun.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RemarkViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.practice_remark_list_item, null);
                holder = new RemarkViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (RemarkViewHolder) view.getTag();
            }
            DriverDetails.ContentBean.PinglunBean pinglunBean = pinglun.get(i);
            if (pinglunBean != null) {
                String all_evaluate = pinglunBean.getAll_evaluate();
                String head_img = pinglunBean.getHead_img();
                String nickname = pinglunBean.getNickname();
                String content = pinglunBean.getContent();
                String content_time = pinglunBean.getContent_time();
                holder.rb_remark_grade.setStepSize(0.5f);
                if (!TextUtils.isEmpty(all_evaluate)) {
                    float star = Float.parseFloat(all_evaluate);
                    holder.rb_remark_grade.setRating(star);
                }
                if (!TextUtils.isEmpty(head_img)) {
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.cv_remark_head);
                }
                if (!TextUtils.isEmpty(nickname)) {
                    holder.tv_name.setText(nickname);
                }
                if (!TextUtils.isEmpty(content)) {
                    holder.tv_content.setText(content);
                }
                if (!TextUtils.isEmpty(content_time)) {
                    holder.tv_time.setText(content_time);
                }
                if (!TextUtils.isEmpty(all_evaluate)) {
                    holder.tv_start_grade.setText("\"" + all_evaluate + "\"");
                }

            }

            return view;
        }

        class RemarkViewHolder {
            private CircleImageView cv_remark_head;
            private TextView tv_name, tv_time, tv_start_grade, tv_content;
            private RatingBar rb_remark_grade;

            public RemarkViewHolder(View view) {
                cv_remark_head = view.findViewById(R.id.cv_remark_head);
                tv_time = view.findViewById(R.id.tv_order_sn);
                rb_remark_grade = view.findViewById(R.id.rb_remark_grade);
                tv_start_grade = view.findViewById(R.id.tv_start_grade);
                tv_content = view.findViewById(R.id.tv_content);
                tv_name = view.findViewById(R.id.tv_name);
            }
        }
    }
}
