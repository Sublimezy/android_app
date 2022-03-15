package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

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
 * 练车详情
 */
public class TrainingDetailsActivity extends NewBaseActivity {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tv_kemu_2)
    TextView tvKemu2;
    @BindView(R.id.tv_submit_date)
    TextView tvSubmitDate;
    @BindView(R.id.tv_train_time)
    TextView tvTrainTime;
    @BindView(R.id.tv_total_xueshi)
    TextView tvTotalXueshi;
    @BindView(R.id.tv_lianche_time)
    TextView tvLiancheTime;
    @BindView(R.id.tv_study_info)
    TextView tvStudyInfo;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_get_in_time)
    TextView tvGetInTime;
    @BindView(R.id.tv_get_in_location)
    TextView tvGetInLocation;
    @BindView(R.id.tv_get_out_time)
    TextView tvGetOutTime;
    @BindView(R.id.tv_get_out_location)
    TextView tvGetOutLocation;
    @BindView(R.id.tv_dianping)
    TextView tvDianping;
    @BindView(R.id.tv_dianping_detail)
    TextView tvDianpingDetail;
    @BindView(R.id.tv_xueyuan_pingjia)
    TextView tvXueyuanPingjia;
    @BindView(R.id.tv_xueyuan_pingjia_detail)
    TextView tvXueyuanPingjiaDetail;
    @BindView(R.id.tv_student_name)
    TextView tvStudentName;

    @Override
    protected int initContentView() {
        return R.layout.activity_training_details;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleBarRl.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarIvBack.setImageDrawable(getResources().getDrawable(R.mipmap.white_iv_back));
        titleBarTitleText.setText("练车详情");
        titleBarTitleText.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        showProgressDialog(true, "正在加载...");
        String training_id = getIntent().getStringExtra("training_id");

        Map<String, String> params = new HashMap<>();
        params.put("training_id", training_id);
        MyHttpUtils.postHttpMessage(AppUrl.selectdrivingtraining, params, TrainingDetailsBean.class, new RequestCallBack<TrainingDetailsBean>() {
            @Override
            public void requestSuccess(TrainingDetailsBean json) {
                stopProgressDialog();
                if (json.getCode() == 200) {
                    TrainingDetailsBean.ContentBean content = json.getContent();

                    tvStudentName.setText(content.getStu_name());

                    tvKemu2.setText(content.getEntry_project());

                    tvSubmitDate.setText(content.getSys_time());
                    tvTrainTime.setText(content.getPractice_time());
                    tvTotalXueshi.setText(content.getNum_class());
                    tvLiancheTime.setText(content.getSelected_period());
                    tvStudyInfo.setText(content.getDriving_practice());
                    tvPhone.setText(content.getTrainee_phone());
                    tvGetInTime.setText(content.getBoarding_time());
                    tvGetInLocation.setText(content.getPick_up_location());
                    tvGetOutTime.setText(content.getGet_off_time());
                    tvGetOutLocation.setText(content.getDrop_off_location());


                    String coach_to_student = "";
//                    教练评价学员(0:优秀 1:熟练 2:较好 3:生疏)
                    switch (content.getCoach_to_student()) {
                        case "0":
                            coach_to_student = "优秀";
                            break;
                        case "1":
                            coach_to_student = "熟练";
                            break;
                        case "2":
                            coach_to_student = "较好";
                            break;
                        case "3":
                            coach_to_student = "生疏";
                            break;


                    }


                    tvDianping.setText(coach_to_student);
                    tvDianpingDetail.setText(content.getCoach_to_student_detail());


                    String student_to_coach = "";
//                    教练评价学员(0:优秀 1:熟练 2:较好 3:生疏)
                    switch (content.getCoach_to_student()) {
                        case "0":
                            student_to_coach = "极好";
                            break;
                        case "1":
                            student_to_coach = "良好";
                            break;
                        case "2":
                            student_to_coach = "较差";
                            break;
                        case "3":
                            student_to_coach = "极差";
                            break;


                    }

                    tvXueyuanPingjia.setText(student_to_coach);
                    tvXueyuanPingjiaDetail.setText(content.getStudent_to_coach_detail());


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


    @OnClick({R.id.title_bar_back, R.id.title_bar_title_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_title_text:
                break;
        }
    }
}
