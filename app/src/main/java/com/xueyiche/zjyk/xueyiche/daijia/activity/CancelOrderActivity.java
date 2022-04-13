package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.daijia.bean.CancelOrderBean;
import com.xueyiche.zjyk.xueyiche.main.activities.main.BaseBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CancelOrderActivity extends BaseActivity {
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_giveup)
    TextView tvGiveup;
    private ResonAdapter resonAdapter;
    private String order_sn;
    private String type;

    @Override
    protected int initContentView() {
        return R.layout.activity_cancel_order;
    }

    public static void forward(Context context, String order_sn, String type) {
        Intent intent = new Intent(context, CancelOrderActivity.class);
        intent.putExtra("order_sn", "" + order_sn);
        intent.putExtra("type", "" + type);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        ImmersionBar.with(this).titleBar(rlTitle).keyboardEnable(true).init();
    }

    @Override
    protected void initData() {
        tvTitle.setText("取消原因");
        order_sn = getIntent().getStringExtra("order_sn");
        type = getIntent().getStringExtra("type");
        resonAdapter = new ResonAdapter(R.layout.item_cancel_reson);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(resonAdapter);
        List<ResonBean> data = new ArrayList<>();
        data.add(new ResonBean("司机到位太慢了，等不及了", false));
        data.add(new ResonBean("等待太久，不想等了", false));
        data.add(new ResonBean("代驾收费太高了", false));
        data.add(new ResonBean("司机要求取消订单", false));
        data.add(new ResonBean("找到其他代驾", false));
        data.add(new ResonBean("暂时不需要代驾了", false));

        resonAdapter.setNewData(data);
        resonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ResonBean> data1 = resonAdapter.getData();
                for (int i = 0; i < data1.size(); i++) {
                    if (i == position) {
                        data1.get(i).setSelected(true);
                    } else {
                        data1.get(i).setSelected(false);

                    }
                    resonAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @OnClick({R.id.ll_common_back, R.id.tv_right_btn, R.id.tv_cancel, R.id.tv_giveup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_right_btn:

                break;
            case R.id.tv_cancel:
                String quxiao_reason = "";
                List<ResonBean> data = resonAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    ResonBean resonBean = data.get(i);
                    if (resonBean.isSelected()) {
                        quxiao_reason = resonBean.getReson();
                    }
                }
                if (TextUtils.isEmpty(quxiao_reason)) {
                    showToastShort("请选择取消原因");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("order_sn", order_sn);
                params.put("quxiao_reason", quxiao_reason);
                params.put("quxiao_remarks", etInfo.getText().toString().trim());

                MyHttpUtils.postHttpMessage(AppUrl.cancelOrder, params, CancelOrderBean.class, new RequestCallBack<CancelOrderBean>() {
                    @Override
                    public void requestSuccess(CancelOrderBean json) {
                        showToastShort(json.getMsg());
                        if (json.getCode() == 1) {
                            if ("wait".equals(type)) {
                                WaitActivity.instance.finish();
                                PrefUtils.putInt(App.context, "start_time", 0);
                            }else if ("jiedan".equals(type)){
                                JieDanActivity.instance.finish();
                            }
                            finish();
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {

                    }
                });

                break;
            case R.id.tv_giveup:
                finish();
                break;
        }
    }

    class ResonAdapter extends BaseQuickAdapter<ResonBean, BaseViewHolder> {


        public ResonAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, ResonBean item) {
            helper.setText(R.id.tv_reson, item.getReson());
            if (item.isSelected()) {
                helper.setVisible(R.id.iv_selected, true);
            } else {
                helper.setVisible(R.id.iv_selected, false);

            }
        }
    }

    class ResonBean {
        private String reson;
        private boolean selected;

        public ResonBean(String reson, boolean selected) {
            this.reson = reson;
            this.selected = selected;
        }

        public String getReson() {
            return reson;
        }

        public void setReson(String reson) {
            this.reson = reson;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}