package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;

import java.math.BigDecimal;

/**
 * Created by ZL on 2018/6/28.
 */
public class FeiYongMingXiActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle, tv_car_hostling, tv_peccancy_deposit, tv_car_deposit, tv_indent_total_price;
    private AdListView lv_shuoming;
    private ScrollView scroll_view;

    @Override
    protected int initContentView() {
        return R.layout.feiyong_mingxi_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.fenjidaikuan_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.fenjidaikuan_include).findViewById(R.id.tv_login_back);
        lv_shuoming = (AdListView) view.findViewById(R.id.lv_shuoming);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        tv_car_hostling = (TextView) view.findViewById(R.id.tv_car_hostling);
        tv_peccancy_deposit = (TextView) view.findViewById(R.id.tv_peccancy_deposit);
        tv_car_deposit = (TextView) view.findViewById(R.id.tv_car_deposit);
        tv_indent_total_price = (TextView) view.findViewById(R.id.tv_indent_total_price);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("费用明细");
        Intent intent = getIntent();
        //违章押金
        String violated_deposit = intent.getStringExtra("violated_deposit");
        //车辆押金
        String car_deposit = intent.getStringExtra("car_deposit");
        //天
        String duration = intent.getStringExtra("duration");
        //单价
        String rent_price = intent.getStringExtra("rent_price");
        if (!TextUtils.isEmpty(duration) && !TextUtils.isEmpty(rent_price)) {
            BigDecimal tian = new BigDecimal(duration);
            BigDecimal danjia = new BigDecimal(rent_price);
            BigDecimal multiply = tian.multiply(danjia);
            tv_car_hostling.setText(rent_price+" x "+duration+" = ¥" + multiply);
        }
        if (!TextUtils.isEmpty(violated_deposit)) {
            tv_peccancy_deposit.setText("违章押金（可退）¥" + violated_deposit);
        }
        if (!TextUtils.isEmpty(car_deposit)) {
            tv_car_deposit.setText("车辆押金（可退）¥" + car_deposit);
        }

        lv_shuoming.setAdapter(new ShuoMingAdapter());
        scroll_view.smoothScrollTo(0, 0);
        scroll_view.smoothScrollBy(0, 0);
        lv_shuoming.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }

    private class ShuoMingAdapter extends BaseAdapter {
        private String[] content = {"1、考驾照学易车驾校联盟，在线报名学车，统一价格，同意服务标准，就近学车、练车"
                , "2、有证练车新模式=一键下单，车型、教练随意选；满意后确认线上支付，安全快捷"
                , "3、车生活：全新车生活；洗车美容、加油"
                , "4、维修保养、汽车配件、轮胎服务等更多优惠等你来"
                , "5、学易车让“免费”伴随汽车人生，开启汽车消费“0”元时代"};

        @Override
        public int getCount() {
            return content.length;
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
            ShuoMingViewHolder shuoMingViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.usedcard_feiyongshuoming_item, null);
                shuoMingViewHolder = new ShuoMingViewHolder(view);
                view.setTag(shuoMingViewHolder);
            } else {
                shuoMingViewHolder = (ShuoMingViewHolder) view.getTag();
            }
            shuoMingViewHolder.tv_content.setText(content[i]);
            return view;
        }

        private class ShuoMingViewHolder {
            private TextView tv_content;

            public ShuoMingViewHolder(View view) {
                tv_content = (TextView) view.findViewById(R.id.tv_content);

            }
        }
    }
}
