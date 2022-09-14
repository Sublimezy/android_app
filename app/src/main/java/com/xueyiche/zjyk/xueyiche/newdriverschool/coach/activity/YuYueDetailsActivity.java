package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.TrainingDetailsBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约详情
 */
public class YuYueDetailsActivity extends NewBaseActivity {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tv_kemu_2)
    TextView tvKemu2;
    @BindView(R.id.tv_lian_che_date)
    TextView tvLianCheDate;
    @BindView(R.id.tv_lianche_time)
    TextView tvLiancheTime;
    @BindView(R.id.tv_total_xueshi)
    TextView tvTotalXueshi;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_submit_time)
    TextView tvSubmitTime;
    @BindView(R.id.tv_btn)
    TextView tvBtn;

    @Override
    protected int initContentView() {
        return R.layout.activity_appointment_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleBarRl.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarIvBack.setImageDrawable(getResources().getDrawable(R.mipmap.white_iv_back));
        titleBarTitleText.setText("预约详情");
        titleBarTitleText.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        showProgressDialog(true,"正在加载...");
        String training_id = getIntent().getStringExtra("training_id");

        Map<String, String> params = new HashMap<>();
        params.put("training_id", training_id);
        MyHttpUtils.postHttpMessage(AppUrl.selectdrivingtraining, params, TrainingDetailsBean.class, new RequestCallBack<TrainingDetailsBean>() {
            @Override
            public void requestSuccess(TrainingDetailsBean json) {
                stopProgressDialog();
                if (json.getCode() == 200) {
                    TrainingDetailsBean.ContentBean content = json.getContent();


                    tvKemu2.setText(content.getEntry_project());

                    tvLianCheDate.setText(content.getPractice_time());
                    tvLiancheTime.setText(content.getSelected_period());
                    tvTotalXueshi.setText(content.getNum_class());


                    tvPhone.setText(content.getTrainee_phone());
                    tvName.setText(content.getStu_name());
                    tvSubmitTime.setText(content.getSys_time());



                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                stopProgressDialog();

            }
        });


    }



    @OnClick({R.id.title_bar_back, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.tv_btn:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvPhone.getText().toString().trim()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
