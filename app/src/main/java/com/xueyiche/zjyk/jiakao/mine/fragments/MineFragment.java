package com.xueyiche.zjyk.jiakao.mine.fragments;

import static com.xueyiche.zjyk.jiakao.constants.App.context;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseFragment;
import com.xueyiche.zjyk.jiakao.constants.App;
import com.xueyiche.zjyk.jiakao.constants.event.MyEvent;
import com.xueyiche.zjyk.jiakao.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.jiakao.main.bean.LoginBean;
import com.xueyiche.zjyk.jiakao.mine.activities.bianji.EditMySelfInfoActivity;
import com.xueyiche.zjyk.jiakao.mine.activities.bianji.SetActivity;
import com.xueyiche.zjyk.jiakao.mine.view.CircleImageView;
import com.xueyiche.zjyk.jiakao.utils.DialogUtils;
import com.xueyiche.zjyk.jiakao.utils.PrefUtils;
import com.xueyiche.zjyk.jiakao.utils.XueYiCheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_head)
    CircleImageView mineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_setting)
    ImageView ivMineSetting;
    @BindView(R.id.ll_shared_app)
    LinearLayout llSharedApp;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    String avatar11;

    public static MineFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        MineFragment fragment = new MineFragment();
        bundle.putString("mine", tag);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        showToastShort("我的");
    }

    @Override
    protected View setInitView() {
        EventBus.getDefault().register(this);
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.mine_fragment_two_new, null);
        ButterKnife.bind(this, viewHead);


        return viewHead;
    }

    @Override
    public void onStart() {
        super.onStart();
//        PrefUtils.clear(context);
        if (PrefUtils.getParameter("userinfo") != null) {


            LoginBean.DataBean.DistanceUser distanceUser = PrefUtils.getParameter(context, "userinfo", LoginBean.DataBean.DistanceUser.class);
            if (DialogUtils.IsLogin()) {
                String token = PrefUtils.getParameter("token");
                Log.e("接受参数！！！！", token);
                if (distanceUser != null) {
                    Log.e("接受参数-userId", String.valueOf(distanceUser.getUserId()));
                    Log.e("接受参数-email", distanceUser.getEmail());
                    Log.e("接受参数-username", distanceUser.getUserName());
                    tvMineName.setText(distanceUser.getUserName());
                }
            }


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 从 Intent 中获取数据
            if (data != null) {
                String value = data.getStringExtra("key");  // 根据键获取传递的数据
                System.out.println(value);
                // 在这里处理从 C Activity 返回的数据
            }
        }
    }

    @Override
    protected Object setLoadDate() {


        return "xyc";
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if ("刷新FragmentLogin".equals(msg)) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    String nickname11;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.mine_head, R.id.tv_mine_name, R.id.iv_mine_setting
            , R.id.ll_shared_app, R.id.ll_kefu, R.id.ll_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_head:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(EditMySelfInfoActivity.class);
                }
                break;
            case R.id.tv_mine_name:
                break;
            case R.id.iv_mine_setting:
                if (DialogUtils.IsLogin()) {
                    openActivity(SetActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_shared_app:

                break;
            case R.id.ll_kefu:
                XueYiCheUtils.CallPhone(getActivity(), "拨打客服电话", "1111-11111111");
                break;
        }
    }
}
