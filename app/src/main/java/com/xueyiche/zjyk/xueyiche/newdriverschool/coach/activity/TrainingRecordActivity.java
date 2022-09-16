package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.YuYueTrainingBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 练车记录
 */
public class TrainingRecordActivity extends NewBaseActivity {


    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
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
    private String coach_id;
    private TestYearQuickAdapter testYearQuickAdapter;
    private List<YuYueTrainingBean.ContentBean> content;

    @Override
    protected int initContentView() {
        return R.layout.activity_training_record;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
//        titleBarRl.setBackgroundColor(Color.parseColor("#000099"));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        bottom.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        coach_id = getIntent().getStringExtra("coach_id");

        testYearQuickAdapter = new TestYearQuickAdapter(R.layout.item_every_year_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(testYearQuickAdapter);



        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getDataFromNet();

            }
        });


        getDataFromNet();


        testYearQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                YuYueTrainingBean.ContentBean contentBean = content.get(position);

                String evaluation_type = contentBean.getEvaluation_type();
                if ("1".equals(evaluation_type)) {
                    Intent intent = new Intent(TrainingRecordActivity.this, TrainingDetailsActivity.class);

                    intent.putExtra("training_id", contentBean.getTraining_id());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void getDataFromNet() {
        showProgressDialog(true,"正在加载...");
        Map<String, String> params = new HashMap<>();
        params.put("coach_user_id", coach_id);
        MyHttpUtils.postHttpMessage(AppUrl.coachreservationlist, params, YuYueTrainingBean.class, new RequestCallBack<YuYueTrainingBean>() {

            @Override
            public void requestSuccess(YuYueTrainingBean json) {
                stopProgressDialog();
                refreshLayout.finishRefresh();
                if (json.getCode() == 200) {
                    content = json.getContent();
                    testYearQuickAdapter.setNewData(json.getContent());
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                refreshLayout.finishRefresh();
                showToastShort("连接服务器失败");
                stopProgressDialog();
            }
        });


    }


    @OnClick({R.id.title_bar_back, R.id.tv_lianche_manage, R.id.tv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.tv_lianche_manage:
                break;
            case R.id.tv_start:
                break;
        }
    }


    class TestYearQuickAdapter extends BaseQuickAdapter<YuYueTrainingBean.ContentBean, BaseViewHolder> {

        public TestYearQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, YuYueTrainingBean.ContentBean item) {


            String substring = item.getSys_time().substring(0, 10);
            String[] split = substring.split("-");


            TextView tv_year = helper.getView(R.id.tv_year);
            helper.setText(R.id.tv_year, split[0]);
            int adapterPosition = helper.getAdapterPosition();
            if (adapterPosition > 0) {
                String substring1 = content.get(adapterPosition - 1).getSys_time().substring(0, 4);
                if (substring1.equals(split[0])) {
                    tv_year.setVisibility(View.GONE);

                } else {
                    tv_year.setVisibility(View.VISIBLE);

                }

            }


            helper.setText(R.id.tv_month_day, split[1] + "月" + split[2] + "日");
            helper.setText(R.id.tv_kemu, item.getEntry_project());//科目二
            helper.setText(R.id.tv_name, item.getStu_name());

            helper.setText(R.id.tv_order_sn, item.getSelected_period());
            helper.setText(R.id.tv_phone, "联系方式：" + item.getTrainee_phone());
            //  待点评   练车中   代练车
            TextView tv_pingjia = helper.getView(R.id.tv_pingjia);
//            helper.setText(R.id.tv_pingjia, "");

            ImageView view = helper.getView(R.id.iv_call_phone);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + item.getTrainee_phone()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });

//            training_type
//            0:未评论 1:已评论
            String evaluation_type = item.getEvaluation_type();
            if ("1".equals(evaluation_type)) {
                tv_pingjia.setText("已评论");
                tv_pingjia.setTextColor(Color.parseColor("#FFAE00"));
            } else {
                tv_pingjia.setText("");

            }
        }


    }

}
