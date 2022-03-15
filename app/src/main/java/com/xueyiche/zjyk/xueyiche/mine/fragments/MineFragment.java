package com.xueyiche.zjyk.xueyiche.mine.fragments;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.AboutXueYiChe;
import com.xueyiche.zjyk.xueyiche.mine.activities.ConstantlyAddressActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.MyCarActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.MyQuestionActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.GetMyCarBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.MineNumberInformationBean;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.ChangeHeadActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bianji.SettingActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.collection.MyCollection;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.NewPlayActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.my_wallet.MyWalletActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.myjifen.MyJiFenActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.youhuiquan.YouHuiQuan;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsBaoMingActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsIndentContentActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsIndentContentBaoMingSuccessActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsOrderContentActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsOrderRecordActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsSelfTestActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsStudyContentActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsStudyRecordActivity;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.StringUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.activities.IndentActivity;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.IndentDriverSchoolDetails;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import de.greenrobot.event.EventBus;


/**
 * Created by Owner on 2016/9/21.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private boolean isPrepared = true;
    //设置
    private ImageView iv_mine_setting, iv_add_my_car;
    //消息
    private ImageView iv_mine_message;
    //头像
    private CircleImageView mine_head;
    //名字
    private TextView tv_mine_name,  tv_wait_pay_number, tv_on_number;
    //我的积分
    private LinearLayout ll_mine_jifen, ll_my_wallet;
    private TextView tv_mine_jifen, tv_mine_youhuiquan;
    //我的足迹
    private LinearLayout ll_mine_zuji;
    private TextView tv_mine_zuji;
    //查看全部订单
    private TextView  tv_mine_title, tv_car_pro, tv_car_number;
    private RelativeLayout tv_mine_all_indent,tv_my_car_room;
    //待付款
    private LinearLayout ll_mine_indent_wait, ll_mine_indent_jinxingzhong, ll_mine_indent_end, ll_mine_indent_tuikuan;
    //头像   名字 的字段
    private String head_img, nickname;
    //电话号  id 的字段
    private String user_phone, user_id;
    private String mylovercar;
    private LinearLayout ll_usually_location, ll_question, ll_my_collection, ll_shared_app, ll_kefu, ll_about, ll_mine_youhuiquan, ll_my_love_car;

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
        iv_mine_message = (ImageView) viewHead.findViewById(R.id.iv_mine_message);
        iv_add_my_car = (ImageView) viewHead.findViewById(R.id.iv_add_my_car);
        mine_head = (CircleImageView) viewHead.findViewById(R.id.mine_head);
        tv_mine_name = (TextView) viewHead.findViewById(R.id.tv_mine_name);
        tv_on_number = (TextView) viewHead.findViewById(R.id.tv_on_number);
        tv_mine_title = (TextView) viewHead.findViewById(R.id.tv_mine_title);
        ll_mine_jifen = (LinearLayout) viewHead.findViewById(R.id.ll_mine_jifen);
        ll_my_collection = (LinearLayout) viewHead.findViewById(R.id.ll_my_collection);
        ll_mine_youhuiquan = (LinearLayout) viewHead.findViewById(R.id.ll_mine_youhuiquan);
        ll_my_love_car = (LinearLayout) viewHead.findViewById(R.id.ll_my_love_car);
        ll_shared_app = (LinearLayout) viewHead.findViewById(R.id.ll_shared_app);
        ll_about = (LinearLayout) viewHead.findViewById(R.id.ll_about);
        ll_kefu = (LinearLayout) viewHead.findViewById(R.id.ll_kefu);
        ll_question = (LinearLayout) viewHead.findViewById(R.id.ll_question);
        ll_mine_zuji = (LinearLayout) viewHead.findViewById(R.id.ll_mine_zuji);
        ll_my_wallet = (LinearLayout) viewHead.findViewById(R.id.ll_my_wallet);
        tv_mine_jifen = (TextView) viewHead.findViewById(R.id.tv_mine_jifen);
        tv_mine_zuji = (TextView) viewHead.findViewById(R.id.tv_mine_zuji);
        tv_my_car_room =  viewHead.findViewById(R.id.tv_my_car_room);
        tv_mine_youhuiquan = (TextView) viewHead.findViewById(R.id.tv_mine_youhuiquan);
        tv_mine_all_indent =  viewHead.findViewById(R.id.tv_mine_all_indent);
        tv_car_pro = (TextView) viewHead.findViewById(R.id.tv_car_pro);
        tv_car_number = (TextView) viewHead.findViewById(R.id.tv_car_number);
        tv_wait_pay_number = (TextView) viewHead.findViewById(R.id.tv_wait_pay_number);
        ll_mine_indent_wait = (LinearLayout) viewHead.findViewById(R.id.ll_mine_indent_wait);
        ll_mine_indent_jinxingzhong = (LinearLayout) viewHead.findViewById(R.id.ll_mine_indent_jinxingzhong);
        ll_mine_indent_end = (LinearLayout) viewHead.findViewById(R.id.ll_mine_indent_end);
        ll_usually_location = (LinearLayout) viewHead.findViewById(R.id.ll_usually_location);
        ll_mine_indent_tuikuan = (LinearLayout) viewHead.findViewById(R.id.ll_mine_indent_tuikuan);

        //点击事件
        iv_mine_setting.setOnClickListener(this);
        iv_mine_message.setOnClickListener(this);
        ll_my_wallet.setOnClickListener(this);
        mine_head.setOnClickListener(this);
        ll_mine_jifen.setOnClickListener(this);
        ll_mine_zuji.setOnClickListener(this);
        tv_mine_all_indent.setOnClickListener(this);
        ll_mine_indent_wait.setOnClickListener(this);
        ll_mine_indent_jinxingzhong.setOnClickListener(this);
        ll_mine_indent_end.setOnClickListener(this);
        ll_usually_location.setOnClickListener(this);
        ll_question.setOnClickListener(this);
        ll_my_collection.setOnClickListener(this);
        ll_shared_app.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        tv_my_car_room.setOnClickListener(this);
        ll_mine_youhuiquan.setOnClickListener(this);
        iv_add_my_car.setOnClickListener(this);
        ll_mine_indent_tuikuan.setOnClickListener(this);
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
                tv_wait_pay_number.setVisibility(View.VISIBLE);
                tv_on_number.setVisibility(View.VISIBLE);
            } else {
                mine_head.setImageResource(R.mipmap.mine_head);
                tv_mine_name.setVisibility(View.GONE);
                tv_wait_pay_number.setVisibility(View.GONE);
                tv_on_number.setVisibility(View.GONE);
                tv_mine_jifen.setText("--");
                tv_mine_zuji.setText("--");
                tv_mine_youhuiquan.setText("--");
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
        } else {
            iv_add_my_car.setVisibility(View.VISIBLE);
            ll_my_love_car.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(getActivity(), IndentActivity.class);
        Intent intent2 = new Intent(getActivity(), MyCollection.class);
        switch (v.getId()) {
            case R.id.mine_head:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(ChangeHeadActivity.class);
                }
//                openActivity(StudentsBaoMingActivity.class);
//                openActivity(StudentsSelfTestActivity.class);
//                openActivity(StudentsIndentContentActivity.class);
//                openActivity(StudentsStudyRecordActivity.class);
//                openActivity(StudentsOrderRecordActivity.class);
//                openActivity(StudentsStudyContentActivity.class);
//                openActivity(StudentsIndentContentBaoMingSuccessActivity.class);
//                openActivity(StudentsOrderContentActivity.class);
                break;
            case R.id.ll_mine_zuji:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent2.putExtra("kind_type", "2");
                    startActivity(intent2);
                }
                break;
            case R.id.ll_mine_jifen:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    openActivity(MyJiFenActivity.class);
//                    openActivity(IndentDriverSchoolDetails.class);
                }
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
                //充值中心
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    startActivity(new Intent(App.context, MyWalletActivity.class));
                }
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
                    Intent intent = new Intent(getActivity(), YouHuiQuan.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_question:
                openActivity(MyQuestionActivity.class);
                break;
            case R.id.ll_my_collection:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    intent2.putExtra("kind_type", "1");
                    startActivity(intent2);
                }
                break;
            case R.id.ll_shared_app:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.mine();
                break;
            case R.id.ll_kefu:
                openActivity(KeFuActivity.class);
                break;
            case R.id.ll_about:
                openActivity(AboutXueYiChe.class);
                break;
            case R.id.tv_my_car_room:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    if (!TextUtils.isEmpty(mylovercar)) {
                        Intent intent3 = new Intent(getActivity(), MyCarActivity.class);
                        intent3.putExtra("mylovercar", mylovercar);
                        startActivity(intent3);
                    }
                }
                break;
            case R.id.iv_add_my_car:
                if (!DialogUtils.IsLogin()) {
                    openActivity(LoginFirstStepActivity.class);
                } else {
                    if (!TextUtils.isEmpty(mylovercar)) {
                        Intent intent3 = new Intent(getActivity(), MyCarActivity.class);
                        intent3.putExtra("mylovercar", mylovercar);
                        startActivity(intent3);
                    }
                }
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
                                                        tv_car_pro.setText(pro);
                                                        tv_car_number.setText(num);
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
                                MineFragment.this.mylovercar = content.getMylovercar();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty("" + code)) {
                                            if (200 == code) {
                                                if (!TextUtils.isEmpty(mylovercar)) {
                                                    if ("0".equals(mylovercar)) {
                                                        iv_add_my_car.setVisibility(View.VISIBLE);
                                                        ll_my_love_car.setVisibility(View.GONE);
                                                    } else if ("1".equals(mylovercar)) {
                                                        iv_add_my_car.setVisibility(View.GONE);
                                                        ll_my_love_car.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                                if (!TextUtils.isEmpty(integral_num)) {
                                                    tv_mine_jifen.setText(integral_num);
                                                } else {
                                                    tv_mine_jifen.setText("0");
                                                }
                                                if (!TextUtils.isEmpty(foot)) {
                                                    tv_mine_zuji.setText(foot);
                                                } else {
                                                    tv_mine_zuji.setText("0");
                                                }
                                                if (!TextUtils.isEmpty(nickname)) {
                                                    tv_mine_name.setText(nickname);
                                                }
                                                if (!TextUtils.isEmpty(yhq_num)) {
                                                    tv_mine_youhuiquan.setText(yhq_num);
                                                }
                                                if (!TextUtils.isEmpty(dfk_num)) {
                                                    if ("0".equals(dfk_num)) {
                                                        tv_wait_pay_number.setVisibility(View.GONE);
                                                    } else {
                                                        tv_wait_pay_number.setVisibility(View.VISIBLE);
                                                        tv_wait_pay_number.setText(dfk_num);
                                                    }

                                                }
                                                if (!TextUtils.isEmpty(jxz_num)) {
                                                    if ("0".equals(jxz_num)) {
                                                        tv_on_number.setVisibility(View.GONE);
                                                    } else {
                                                        tv_on_number.setVisibility(View.VISIBLE);
                                                        tv_on_number.setText(jxz_num);
                                                    }

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
