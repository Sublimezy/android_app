package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import androidx.appcompat.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;

/**
 * Created by ZL on 2018/6/28.
 */
public class LoanStagingSecondActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack,ll_chaxun_after;
    private TextView tvTitle,tv_tijiao,tv_gusuan;
    private AdListView lv_daikuan;
    private ScrollView scroll_view;
    private View view_line;
    @Override
    protected int initContentView() {
        return R.layout.loanstaging_second_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.fenjidaikuan_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.fenjidaikuan_include).findViewById(R.id.tv_login_back);
        ll_chaxun_after = (LinearLayout) view.findViewById(R.id.ll_chaxun_after);
        tv_gusuan = (TextView) view.findViewById(R.id.tv_gusuan);
        view_line = view.findViewById(R.id.view_line);
        lv_daikuan = (AdListView) view.findViewById(R.id.lv_daikuan);
        tv_tijiao = (TextView) view.findViewById(R.id.tv_tijiao);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        scroll_view.scrollTo(0,0);
        lv_daikuan.setFocusable(false);

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_tijiao.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        tvTitle.setText("贷款分期");
        lv_daikuan.setAdapter(new LoanStagingAdapter());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_tijiao:
                showDialog();
                break;

        }
    }

    private void showDialog() {
        View viewDia = LayoutInflater.from(App.context).inflate(R.layout.usedcar_tanchuang_layout, null);
        TextView bt_know = (TextView) viewDia.findViewById(R.id.tv_ok);
        TextView tv_no = (TextView) viewDia.findViewById(R.id.tv_no);
        View view_line_dia =  viewDia.findViewById(R.id.view_line);
        tv_no.setVisibility(View.GONE);
        view_line_dia.setVisibility(View.GONE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoanStagingSecondActivity.this, R.style.Dialog).setView(viewDia);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth*2/3;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_gusuan.setVisibility(View.GONE);
                view_line.setVisibility(View.GONE);
                tv_tijiao.setVisibility(View.GONE);
                ll_chaxun_after.setVisibility(View.VISIBLE);
                lv_daikuan.setAdapter(new TuiJianCarAdapter());
                dialog01.dismiss();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }
    private class TuiJianCarAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 8;
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
            HotViewHolder hotViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.used_car_list_item, null);
                hotViewHolder = new HotViewHolder(view);
                view.setTag(hotViewHolder);
            } else {
                hotViewHolder = (HotViewHolder) view.getTag();
            }
            return view;
        }

        class HotViewHolder {
            private ImageView iv_used_car_photo, iv_used_car_state;

            public HotViewHolder(View view) {
                iv_used_car_photo = (ImageView) view.findViewById(R.id.iv_used_car_photo);
                iv_used_car_state = (ImageView) view.findViewById(R.id.iv_used_car_state);

            }
        }

    }
    private class LoanStagingAdapter extends BaseAdapter{
        private String[] name = {"职业身份","月收入","信用卡还款","社保","房产情况"};

        @Override
        public int getCount() {
            return name.length;
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
            LoanStagingViewHolder loanStagingViewHolder = null;
            if (view==null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.daikuan_item,null);
                loanStagingViewHolder= new LoanStagingViewHolder(view);
                view.setTag(loanStagingViewHolder);
            }else {
                loanStagingViewHolder = (LoanStagingViewHolder) view.getTag();
            }
            loanStagingViewHolder.tvName.setText(name[i]);
            loanStagingViewHolder.rk_item_onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return view;
        }
        class LoanStagingViewHolder{
           private TextView  tvName;
           private TextView  tv_content;
            private RelativeLayout rk_item_onclick;

            public LoanStagingViewHolder(View view){
                tv_content = (TextView) view.findViewById(R.id.tv_content);
                tvName = (TextView) view.findViewById(R.id.tvName);
                rk_item_onclick = (RelativeLayout) view.findViewById(R.id.rk_item_onclick);
            }
        }
    }
}
