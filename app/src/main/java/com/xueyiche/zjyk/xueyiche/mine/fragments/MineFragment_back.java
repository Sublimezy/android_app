package com.xueyiche.zjyk.xueyiche.mine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.AboutXueYiChe;
import com.xueyiche.zjyk.xueyiche.mine.activities.ConstantlyAddressActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.GetMyCarBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.MineNumberInformationBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.ChangeHeadActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.SettingActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.NewPlayActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.youhuiquan.YouHuiQuan;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.activities.IndentActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import de.greenrobot.event.EventBus;


/**
 * Created by Owner on 2016/9/21.
 */
public class MineFragment_back extends BaseFragment implements View.OnClickListener {
    private boolean isPrepared = true;
    //设置
    private ImageView iv_mine_setting;
    //头像
    private CircleImageView mine_head;
    //名字
    private TextView tv_mine_name;
    //我的积分
    private LinearLayout ll_my_wallet;
    //查看全部订单
    private TextView tv_mine_title;
    //头像   名字 的字段
    private String head_img, nickname;
    //电话号  id 的字段
    private String user_phone, user_id;
    private LinearLayout ll_shared_app, ll_kefu, ll_about;

    public static MineFragment_back newInstance(String tag) {
        Bundle bundle = new Bundle();
        MineFragment_back fragment = new MineFragment_back();
        bundle.putString("mine", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (DialogUtils.IsLogin()) {
            user_phone = PrefUtils.getString(App.context, "user_phone", "");
            user_id = PrefUtils.getString(App.context, "user_id", "");
            getNumberInformation();
        }
    }

    @Override
    protected View setInitView() {
        EventBus.getDefault().register(this);
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.mine_fragment_two_new, null);
        iv_mine_setting = (ImageView) viewHead.findViewById(R.id.iv_mine_setting);
        mine_head = (CircleImageView) viewHead.findViewById(R.id.mine_head);
        tv_mine_name = (TextView) viewHead.findViewById(R.id.tv_mine_name);
        tv_mine_title = (TextView) viewHead.findViewById(R.id.tv_mine_title);
        ll_shared_app = (LinearLayout) viewHead.findViewById(R.id.ll_shared_app);
        ll_about = (LinearLayout) viewHead.findViewById(R.id.ll_about);
        ll_kefu = (LinearLayout) viewHead.findViewById(R.id.ll_kefu);
        ll_my_wallet = (LinearLayout) viewHead.findViewById(R.id.ll_my_wallet);


        //点击事件
        iv_mine_setting.setOnClickListener(this);
        ll_my_wallet.setOnClickListener(this);
        mine_head.setOnClickListener(this);
        ll_shared_app.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        initData();
        return viewHead;
    }

    private void initData() {
        user_phone = PrefUtils.getString(App.context, "user_phone", "");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        head_img = PrefUtils.getString(App.context, "head_img", "");
        nickname = PrefUtils.getString(App.context, "nickname", "");

        if (!TextUtils.isEmpty(nickname)) {
            tv_mine_name.setText(nickname);
            tv_mine_title.setText("Hi~，学易车用户");
            tv_mine_name.setVisibility(View.VISIBLE);
        } else {
            if (!TextUtils.isEmpty(user_phone)) {
                String decrypt = AES.decrypt(user_phone);
                if (!TextUtils.isEmpty(decrypt)) {
                    String maskNumber = StringUtils.phoneHide(decrypt);
                    tv_mine_title.setText("Hi~，学易车用户");
                    tv_mine_name.setVisibility(View.VISIBLE);
                    tv_mine_name.setText(maskNumber);
                }
            }
        }
        if (!TextUtils.isEmpty(head_img)) {
            Picasso.with(getContext()).load(head_img).into(mine_head);

        } else {
            mine_head.setImageResource(R.mipmap.mine_head);

        }
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if ("刷新FragmentLogin".equals(msg)) {
            if (DialogUtils.IsLogin()) {
                user_phone = PrefUtils.getString(App.context, "user_phone", "");
                user_id = PrefUtils.getString(App.context, "user_id", "");
                head_img = PrefUtils.getString(App.context, "head_img", "");
                Picasso.with(getContext()).load(head_img).into(mine_head);
                nickname = PrefUtils.getString(App.context, "nickname", "");
                if (TextUtils.isEmpty(nickname)) {
                    if (!TextUtils.isEmpty(user_phone)) {
                        String decrypt = AES.decrypt(user_phone);
                        if (!TextUtils.isEmpty(decrypt)) {
                            String maskNumber = StringUtils.phoneHide(decrypt);
                            tv_mine_name.setText(maskNumber);
                        }
                    }

                } else {
                    tv_mine_name.setText(nickname);
                }
                tv_mine_title.setText("Hi~，学易车用户");
                tv_mine_name.setVisibility(View.VISIBLE);
            } else {
                mine_head.setImageResource(R.mipmap.mine_head);
                tv_mine_name.setVisibility(View.GONE);
                tv_mine_title.setText("点击头像登录");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DialogUtils.IsLogin()) {
            user_phone = PrefUtils.getString(App.context, "user_phone", "");
            user_id = PrefUtils.getString(App.context, "user_id", "");
            getNumberInformation();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(getActivity(), IndentActivity.class);
        switch (v.getId()) {
            case R.id.mine_head:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(ChangeHeadActivity.class);
                }
                break;
            case R.id.ll_mine_jifen:
                break;
            case R.id.iv_mine_setting:
                if (DialogUtils.IsLogin()) {
                    openActivity(SettingActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.iv_mine_message:
                if (DialogUtils.IsLogin()) {
                    openActivity(NewPlayActivity.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;

            case R.id.tv_mine_all_indent:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent1.putExtra("position", 0);
                    startActivity(intent1);
                }
                break;
            case R.id.ll_mine_indent_wait:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent1.putExtra("position", 1);
                    startActivity(intent1);
                }
                break;
            case R.id.ll_mine_indent_jinxingzhong:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent1.putExtra("position", 2);
                    startActivity(intent1);
                }
                break;
            case R.id.ll_mine_indent_end:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent1.putExtra("position", 3);
                    startActivity(intent1);
                }
                break;

            case R.id.ll_my_wallet:
                Intent intent = new Intent(getActivity(), UrlActivity.class);
                intent.putExtra("url", "http://xueyiche.cn/xyc/daijia/index.html");
                intent.putExtra("type", "10");
                startActivity(intent);
                break;
            case R.id.ll_usually_location:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(ConstantlyAddressActivity.class);
                }
                break;
            case R.id.ll_mine_youhuiquan:
                //优惠券
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    Intent intent11 = new Intent(getActivity(), YouHuiQuan.class);
                    startActivity(intent11);
                }
                break;
            case R.id.ll_shared_app:

                break;
            case R.id.ll_kefu:
                XueYiCheUtils.CallPhone(getActivity(), "拨打客服电话", "0451-58627471");
                MobclickAgent.onEvent(getContext(), "kefu_phone");
                break;
            case R.id.ll_about:
                openActivity(AboutXueYiChe.class);
                break;
            case R.id.ll_mine_indent_tuikuan:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent1.putExtra("position", 3);
                    startActivity(intent1);
                }
                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    public void getNumberInformation() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            //获取当前易币
            OkHttpUtils.post().url(AppUrl.Get_BangDing_Car)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(getActivity()))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final GetMyCarBean weiZhangPostBean = JsonUtil.parseJsonToBean(string, GetMyCarBean.class);
                                if (weiZhangPostBean != null) {
                                    final int success = weiZhangPostBean.getCode();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200 == success) {
                                                GetMyCarBean.ContentBean content = weiZhangPostBean.getContent();
                                                if (content != null) {
                                                    String licenseno = content.getLicenseno();
                                                    if (!TextUtils.isEmpty(licenseno)) {
                                                        String pro = licenseno.substring(0, 1);
                                                        String num = licenseno.substring(1, licenseno.length());
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                            return string;
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                        }

                        @Override
                        public void onResponse(Object response) {
                        }
                    });
            OkHttpUtils.post().url(AppUrl.Mine_Infrmation)
                    .addParams("device_id", TextUtils.isEmpty(PrefUtils.getParameter("device_id")) ? "123456" : PrefUtils.getParameter("device_id"))
                    .addParams("user_id", !TextUtils.isEmpty(user_id) ? user_id : "").build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        MineNumberInformationBean yiBiBean = JsonUtil.parseJsonToBean(string, MineNumberInformationBean.class);
                        final int code = yiBiBean.getCode();
                        if (yiBiBean != null) {
                            MineNumberInformationBean.ContentBean content = yiBiBean.getContent();
                            if (content != null) {
                                final String foot = content.getFoot();
                                final String integral_num = content.getIntegral_num();
                                final String head_img = content.getHead_img();
                                final String nickname = content.getNickname();
                                final String dfk_num = content.getDfk_num();
                                final String jxz_num = content.getJxz_num();
                                final String yhq_num = content.getYhq_num();
                                final String mylovercar = content.getMylovercar();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty("" + code)) {
                                            if (200 == code) {
                                                if (!TextUtils.isEmpty(nickname)) {
                                                    tv_mine_name.setText(nickname);
                                                }
                                                if (!TextUtils.isEmpty(head_img)) {
                                                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(mine_head);
                                                }


                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {
                }

                @Override
                public void onResponse(Object response) {

                }
            });
        }
    }
}
