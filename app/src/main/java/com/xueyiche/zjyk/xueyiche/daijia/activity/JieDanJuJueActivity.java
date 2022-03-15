package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;

/**
 * Created by ZL on 2020/2/15.
 */
public class JieDanJuJueActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_no, iv_ok, ivBack;
    private TextView tv_time, tv_title;
    private String order_number;
    private String type;
    private String cancle_remark;
    public static JieDanJuJueActivity instance;

    @Override
    protected int initContentView() {
        return R.layout.jiedan_jujue_activity;
    }

    @Override
    protected void initView() {
        instance = this;
        iv_no = view.findViewById(R.id.iv_no);
        iv_ok = view.findViewById(R.id.iv_ok);
        tv_time = view.findViewById(R.id.tv_time);
        tv_title = view.findViewById(R.id.title).findViewById(R.id.tv_title);
        ivBack = view.findViewById(R.id.title).findViewById(R.id.iv_login_back);

    }

    @Override
    protected void initListener() {
        iv_no.setOnClickListener(this);
        iv_ok.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_title.setText("取消订单");
        Intent intent = getIntent();
        order_number = intent.getStringExtra("order_number");
        cancle_remark = intent.getStringExtra("cancle_remark");
        tv_time.setText(cancle_remark);
        type = intent.getStringExtra("type");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.iv_no:
                finish();
                break;
            case R.id.iv_ok:
                //取消订单
                Intent intent1 = new Intent(JieDanJuJueActivity.this, LiYouActivity.class);
                intent1.putExtra("order_number", order_number);
                intent1.putExtra("type",type);
                startActivity(intent1);
                finish();
                break;
            default:

                break;
        }
    }
}
