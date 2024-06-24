package com.xueyiche.zjyk.jiakao.examtext.examfragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.examtext.kemub.SubjectbWeb;


public class SubjectBFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_daoche, ll_poting, ll_quxian, ll_zhijiao, ll_cefang,ll_must_pass;
    private TextView tv_keer_xuzhi,tv_must_pass;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.home_exam_subjectb, null);
        ll_daoche = (LinearLayout) view.findViewById(R.id.ll_daoche);
        ll_poting = (LinearLayout) view.findViewById(R.id.ll_poting);
        ll_quxian = (LinearLayout) view.findViewById(R.id.ll_quxian);
        ll_zhijiao = (LinearLayout) view.findViewById(R.id.ll_zhijiao);
        tv_must_pass = (TextView) view.findViewById(R.id.tv_must_pass);
        tv_keer_xuzhi = (TextView) view.findViewById(R.id.tv_keer_xuzhi);
        ll_cefang = (LinearLayout) view.findViewById(R.id.ll_cefang);
        ll_daoche.setOnClickListener(this);
        ll_poting.setOnClickListener(this);
        ll_quxian.setOnClickListener(this);
        tv_must_pass.setOnClickListener(this);
        ll_zhijiao.setOnClickListener(this);
        ll_cefang.setOnClickListener(this);
        tv_keer_xuzhi.setOnClickListener(this);
        return view;
    }


    @Override
    protected Object setLoadDate() {
        return "2121";
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(App.context, SubjectbWeb.class);
        switch (v.getId()) {
            case R.id.ll_daoche:
                intent.putExtra("TITLE","daoche_ruku");
                startActivity(intent);
                break;
            case R.id.ll_poting:
                intent.putExtra("TITLE","potingpoqi");
                startActivity(intent);
                break;
            case R.id.ll_cefang:
                intent.putExtra("TITLE","cefangtingche");
                startActivity(intent);
                break;
            case R.id.ll_quxian:
                intent.putExtra("TITLE","xuxianxingshi");
                startActivity(intent);
                break;
            case R.id.ll_zhijiao:
                intent.putExtra("TITLE","zhijiaozhuanwan");
                startActivity(intent);
                break;

        }
    }
}
