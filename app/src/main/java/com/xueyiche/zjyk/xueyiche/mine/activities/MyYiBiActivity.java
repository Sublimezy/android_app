package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

/**
 * Created by zhanglei on 2016/10/29.
 */
public class MyYiBiActivity extends BaseActivity implements View.OnClickListener {
    private String user_phone;
    private ImageView iv_caidan;
    private TextView tv_login_back;
    private ListView lv_jifen_mingxi;
    private RadioButton iv_qiandao_kaiguan;
    private RadioButton rb_qiandao;
    private LinearLayout ll_qiandao_tixing;
    private LinearLayout ll_exam_back;
    private boolean isTiShi;

    @Override
    protected int initContentView() {
        return R.layout.my_yibi_activity;
    }

    @Override
    protected void initView() {
        user_phone = PrefUtils.getString(App.context, "user_phone", "");
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        iv_caidan = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_caidan);
        lv_jifen_mingxi = (ListView) view.findViewById(R.id.lv_jifen_mingxi);
        iv_qiandao_kaiguan = (RadioButton) view.findViewById(R.id.iv_qiandao_kaiguan);
        rb_qiandao = (RadioButton) view.findViewById(R.id.rb_qiandao);
        ll_qiandao_tixing = (LinearLayout) view.findViewById(R.id.ll_qiandao_tixing);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        lv_jifen_mingxi.setAdapter(new MineJiFenAdapter());
        iv_caidan.setVisibility(View.VISIBLE);
        iv_caidan.setImageResource(R.mipmap.mine_jifen_tishi);
        tv_login_back.setText("我的易币");

        isTiShi = true;
    }

    @Override
    protected void initListener() {
        iv_caidan.setOnClickListener(this);
        iv_qiandao_kaiguan.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        rb_qiandao.setOnClickListener(this);
    }

    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_caidan:

                break;

            case R.id.iv_qiandao_kaiguan:
                //签到提醒
                if (isTiShi) {
                    iv_qiandao_kaiguan.setChecked(false);
                    isTiShi = false;
                }else {
                    isTiShi = true;
                    iv_qiandao_kaiguan.setChecked(true);
                }
                break;
            case R.id.rb_qiandao:
                rb_qiandao.setChecked(false);
                rb_qiandao.setClickable(false);
                rb_qiandao.setText("已签到");
                break;
        }
    }


    private class MineJiFenAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
            MineViewHolder mineViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.mine_jifen_list_item, null);
                mineViewHolder = new MineViewHolder(view);
                view.setTag(mineViewHolder);
            } else {
                mineViewHolder = (MineViewHolder) view.getTag();

            }
            return view;
        }

        class MineViewHolder {
            private TextView tv_jifen_time,tv_jifen_number,tv_mine_jifen_name;

            public MineViewHolder(View view) {
                tv_jifen_time = (TextView) view.findViewById(R.id.tv_jifen_time);
                tv_jifen_number = (TextView) view.findViewById(R.id.tv_jifen_number);
                tv_mine_jifen_name = (TextView) view.findViewById(R.id.tv_mine_jifen_name);

            }
        }

    }
}
