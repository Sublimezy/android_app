package com.gxuwz.zy.mine.fragments;

import static com.gxuwz.zy.constants.App.context;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseFragment;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.constants.event.MyEvent;
import com.gxuwz.zy.login.activity.LoginFirstStepActivity;
import com.gxuwz.zy.login.entity.dos.LoginBean;
import com.gxuwz.zy.mine.activities.bianji.EditMySelfInfoActivity;
import com.gxuwz.zy.mine.activities.bianji.SetActivity;
import com.gxuwz.zy.mine.view.CircleImageView;
import com.gxuwz.zy.utils.DialogUtils;
import com.gxuwz.zy.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class MineFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.mine_head)
    CircleImageView mineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_setting)
    ImageView ivMineSetting;
    @BindView(R.id.ll_shared_app)
    LinearLayout llSharedApp;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @BindView(R.id.my_spinner)
    Spinner mySpinner;


    private String[] items;

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

        // 创建一个包含选项的数组
        items = new String[]{"a1", "a2", "b1", "b2", "c1", "c2"};

        // 创建 ArrayAdapter 并设置数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_model_spinner, items);

        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(this);


        String string = PrefUtils.getString(getContext(), "model", "c1");
        int defaultPosition = adapter.getPosition(string);
        mySpinner.setSelection(defaultPosition);
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
            , R.id.ll_shared_app, R.id.ll_about})
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
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        showToastShort("已切换为" + items[i] + "驾照");
        PrefUtils.putString(getContext(), "model", items[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
