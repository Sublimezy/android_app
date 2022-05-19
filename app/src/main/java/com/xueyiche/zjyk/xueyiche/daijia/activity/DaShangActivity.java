package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.AlertPopWindow;
import com.xueyiche.zjyk.xueyiche.utils.BigDecimalUtils;
import com.xueyiche.zjyk.xueyiche.utils.EditInputFilter;
import com.xueyiche.zjyk.xueyiche.utils.PayUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 打赏
 */
public class DaShangActivity extends BaseActivity {


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
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.btn_dashang)
    TextView btnDashang;
    private AlertPopWindow popWindow;

    @Override
    protected int initContentView() {
        return R.layout.activity_da_shang;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("打赏司机");
    }

    public static void forward(Context context, String order_sn) {
        Intent intent = new Intent(context, DaShangActivity.class);
        intent.putExtra("order_sn", order_sn);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        EditInputFilter editInputFilter = new EditInputFilter();
        InputFilter[] filters = {editInputFilter};
        etMoney.setFilters(filters);

    }


    @OnClick({R.id.ll_common_back, R.id.btn_dashang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.btn_dashang:
                String trim = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(trim) || BigDecimalUtils.compare("0", trim) == 0) {
                    showToastShort("请输入打赏金额");
                    return;
                }
//                EventBus.getDefault().post(new MyEvent("打赏支付成功"));
                String order_sn = getIntent().getStringExtra("order_sn");
                PayUtils.showPopupWindow(AppUrl.userReward, DaShangActivity.this, order_sn, "dashang",trim);
                break;
        }
    }


    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("打赏支付成功", msg)) {
            popWindow = new AlertPopWindow(DaShangActivity.this, R.layout.dashang_alert);
            View pop = popWindow.getmMenuView();
            popWindow.setFocusable(false);
            popWindow.setOutsideTouchable(false);
            popWindow.showAtLocation(btnDashang, Gravity.CENTER, 0, 0);
            Button back = pop.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(popWindow !=null && popWindow.isShowing()){

            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}