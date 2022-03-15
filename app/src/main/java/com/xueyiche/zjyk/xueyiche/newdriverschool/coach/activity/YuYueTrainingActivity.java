package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.lihang.ShadowLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.StartTrainBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.YuYueTrainingBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.LogUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约练车_教练端
 */
public class YuYueTrainingActivity extends NewBaseActivity {


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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_lianche_manage)
    TextView tvLiancheManage;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    private TrainingQuickAdapter trainingQuickAdapter;
    private String coach_id;

    @Override
    protected int initContentView() {
        return R.layout.activity_yu_yue_training;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarTitleText.setText("预约练车");
        titleBarRightText.setText("练车记录");
        titleBarRightText.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        coach_id = getIntent().getStringExtra("coach_id");
        trainingQuickAdapter = new TrainingQuickAdapter(R.layout.item_every_year_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(trainingQuickAdapter);


        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getDataFromNet();

            }
        });

        getDataFromNet();


//        trainingQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                YuYueTrainingBean.ContentBean contentBean = trainingQuickAdapter.getData().get(position);
//                Intent intent;
//                //            training_type
////            0:待练车 1:练车中 2:待点评 3:点评完成
//
//
//
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            getDataFromNet();

        }
    }

    private void getDataFromNet() {
        showProgressDialog(true, "正在加载...");
        Map<String, String> params = new HashMap<>();
        params.put("coach_user_id", coach_id);
        MyHttpUtils.postHttpMessage(AppUrl.coachreservationlist, params, YuYueTrainingBean.class, new RequestCallBack<YuYueTrainingBean>() {
            @Override
            public void requestSuccess(YuYueTrainingBean json) {
                stopProgressDialog();
                refreshLayout.finishRefresh();
                if (json.getCode() == 200) {
                    trainingQuickAdapter.setNewData(json.getContent());
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                refreshLayout.finishRefresh();
                stopProgressDialog();
                showToastShort("连接服务器失败");
            }
        });


    }


    @OnClick({R.id.title_bar_back, R.id.title_bar_right_text, R.id.tv_lianche_manage, R.id.tv_start})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_right_text:
                intent = new Intent(this, TrainingRecordActivity.class);
                intent.putExtra("coach_id", coach_id);
                startActivity(intent);
                break;
            case R.id.tv_lianche_manage:
                intent = new Intent(this, TrainingDateManageActivity.class);
                intent.putExtra("coach_id", coach_id);

                startActivity(intent);
                break;
            case R.id.tv_start:
                String lan = PrefUtils.getParameter("y");
                String lon = PrefUtils.getParameter("x");

                Map<String, String> params = new HashMap<>();
                params.put("coach_user_id", coach_id);
                params.put("training_type", "1");
                params.put("coach_boarding_lon", lon);
                params.put("coach_boarding_lat", lan);

                MyHttpUtils.postHttpMessage(AppUrl.startendpracticecar, params, StartTrainBean.class, new RequestCallBack<StartTrainBean>() {
                    @Override
                    public void requestSuccess(StartTrainBean json) {
                        if (json.getCode() == 200) {
                            Intent intent = new Intent(YuYueTrainingActivity.this, StartTrainActivity.class);
                            intent.putExtra("coach_id", coach_id);
                            startActivityForResult(intent,1111);
                        } else {
                            showToastShort(json.getMsg());
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {
                        showToastShort("连接服务器失败");
                    }
                });
                break;
        }
    }



    class TrainingQuickAdapter extends BaseQuickAdapter<YuYueTrainingBean.ContentBean, BaseViewHolder> {

        public TrainingQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, YuYueTrainingBean.ContentBean item) {

            String substring = item.getSys_time().substring(0, 10);
            String[] split = substring.split("-");


            helper.setText(R.id.tv_year, "");
            helper.setText(R.id.tv_month_day, split[1] + "月" + split[2] + "日");
            helper.setText(R.id.tv_kemu, item.getEntry_project());//科目二
            helper.setText(R.id.tv_name, item.getStu_name());

            helper.setText(R.id.tv_time, item.getSelected_period());
            helper.setText(R.id.tv_phone, "联系方式：" + item.getTrainee_phone());
            //  待点评   练车中   代练车
            TextView tv_pingjia = helper.getView(R.id.tv_pingjia);
//            helper.setText(R.id.tv_pingjia, "");

            ImageView view = helper.getView(R.id.iv_call_phone);
            TextView tv_year = helper.getView(R.id.tv_year);
            tv_year.setVisibility(View.GONE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AndPermission.with(YuYueTrainingActivity.this)
//                            .runtime()
//                            .permission(Permission.CALL_PHONE)
//                            .onGranted(permissions -> {
                    // Storage permission are allowed.
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + item.getTrainee_phone()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                            })
//                            .onDenied(permissions -> {
//                                // Storage permission are not allowed.
//                                showToastShort("权限被拒绝,无法拨打电话!");
//                            })
//                            .start();
                }
            });

//            evaluation_type
//            0:待练车 1:练车中 2:待点评 3:点评完成
            String evaluation_type = item.getEvaluation_type();
            if ("0".equals(evaluation_type)) {
                tv_pingjia.setText("待练车");
                tv_pingjia.setTextColor(Color.parseColor("#FFAE00"));
            } else if ("1".equals(evaluation_type)) {
                tv_pingjia.setText("练车中");
                tv_pingjia.setTextColor(Color.parseColor("#FF5000"));
            } else if ("2".equals(evaluation_type)) {
                tv_pingjia.setText("待点评");
                tv_pingjia.setTextColor(Color.parseColor("#3AA216"));
            } else if ("3".equals(evaluation_type)) {
                tv_pingjia.setText("点评完成");
                tv_pingjia.setTextColor(Color.parseColor("#ffffff"));
            } else {
                tv_pingjia.setText("");

            }


            ShadowLayout view1 = helper.getView(R.id.ll_item);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    String training_type = item.getTraining_type();
                    if ("0".equals(training_type)) {
                        intent = new Intent(YuYueTrainingActivity.this, YuYueDetailsActivity.class);
                        intent.putExtra("training_id", item.getTraining_id());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else if ("1".equals(training_type)) {

                    } else if ("2".equals(training_type)) {
                        intent = new Intent(YuYueTrainingActivity.this, WaitPingJiaTrainingDetailActivity.class);
                        intent.putExtra("training_id", item.getTraining_id());
                        intent.putExtra("entry_project", item.getEntry_project());//科目二
                        intent.putExtra("stu_name", item.getStu_name());//姓名
                        intent.putExtra("phone", item.getTrainee_phone());//电话
                        intent.putExtra("num_class", item.getNum_class());//共2学时
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 1111);

                    } else if ("3".equals(training_type)) {
                        intent = new Intent(YuYueTrainingActivity.this, TrainingDetailsActivity.class);
                        intent.putExtra("training_id", item.getTraining_id());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {


                    }
                }
            });
        }
    }
}
