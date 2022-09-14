package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity.TrainingDateManageActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity.YuYueTrainingActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsOrderRecordActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsSelfTestActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.CoachListBean;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 教练列表
 */
public class CoachListActivity extends NewBaseActivity {


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
    private CoachListQuickAdapter testYearQuickAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_coach_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).init();
        titleBarTitleText.setText("教练列表");
        titleBarRightText.setVisibility(View.VISIBLE);
        titleBarRightText.setText("我的预约");

    }

    @Override
    protected void initListener() {

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                showProgressDialog(false, "正在加载...");

                getDataFromNet();
            }
        });
    }

    @Override
    protected void initData() {
        testYearQuickAdapter = new CoachListQuickAdapter(R.layout.item_coach_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(testYearQuickAdapter);

        showProgressDialog(false, "正在加载...");

        getDataFromNet();


        testYearQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                CoachListBean.ContentBean contentBean = testYearQuickAdapter.getData().get(position);
                Intent intent = new Intent(CoachListActivity.this, BuyKeShiDateActivity.class);
                intent.putExtra("coach_id", contentBean.getDriving_coach_id());
                intent.putExtra("coach_name", contentBean.getDriving_coach_name());
                startActivityForResult(intent, 1212);
            }
        });

    }

    private void getDataFromNet() {
        Map<String, String> params = new HashMap<>();
        MyHttpUtils.postHttpMessage(AppUrl.drivingcoachlist, params, CoachListBean.class, new RequestCallBack<CoachListBean>() {
            @Override
            public void requestSuccess(CoachListBean json) {
                refreshLayout.finishRefresh();
                stopProgressDialog();
                if (json.getCode() == 200) {
                    testYearQuickAdapter.setNewData(json.getContent());

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


    @OnClick({R.id.title_bar_back, R.id.title_bar_title_text, R.id.title_bar_right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_bar_title_text:
                break;
            case R.id.title_bar_right_text:
                openActivity(StudentsOrderRecordActivity.class);
                break;
        }
    }


    class CoachListQuickAdapter extends BaseQuickAdapter<CoachListBean.ContentBean, BaseViewHolder> {

        public CoachListQuickAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, CoachListBean.ContentBean item) {
// 1 女 2  男
            String sex = "";
            if (item.getCoach_sex().equals("1")) {
                sex = "女";
            } else if (item.getCoach_sex().equals("2")) {
                sex = "男";
            } else {
                sex = "未知";
            }
            helper.setText(R.id.tv_age, sex + "   " + item.getCoach_age() + "岁");
            helper.setText(R.id.tv_jia_ling, item.getCoach_years() + "年驾龄");
            helper.setText(R.id.coach_name, item.getDriving_coach_name());


            CircleImageView view = helper.getView(R.id.iv_head);
            Picasso.with(CoachListActivity.this).load(item.getCoach_head_img()).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1212 && resultCode == 1231) {
            finish();
        }
    }
}
