package com.xueyiche.zjyk.xueyiche.examtext;

import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.custom.RoundImageView;
import com.xueyiche.zjyk.xueyiche.driverschool.driverschool.TiJianActivity;
import com.xueyiche.zjyk.xueyiche.examtext.bean.PhoneUserIdBean;
import com.xueyiche.zjyk.xueyiche.examtext.bean.UserTypeBean;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity.YuYueTrainingActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.CoachListActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsBaoMingActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity.StudentsSelfTestActivity;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Created by ZL on 2019/7/12.
 */
public class TestDriverBookActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack, ll_baoming, ll_zhitongche, ll_test, ll_hospital, ll_kefu;
    private TextView tvTitle, tvEntrance;
    private LinearLayout ll_xuecheliucheng, ll_baoming_test;
    private TextView tv_wenxintishi;
    private ImageView ivEntrance ;
    private RoundImageView ivTop;
    private RecyclerView rv_baoming_qian, rv_baoming_hou;
    private String stu_coach;
    private String stu_sign_up;

    @Override
    protected int initContentView() {
        return R.layout.test_driver_book_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_wenxintishi);
        tv_wenxintishi.setVisibility(View.GONE);
        tv_wenxintishi.setText("");
        rv_baoming_qian = (RecyclerView) view.findViewById(R.id.rv_baoming_qian);
        rv_baoming_hou = (RecyclerView) view.findViewById(R.id.rv_baoming_hou);
        ivEntrance = view.findViewById(R.id.iv_entrance);
        ivTop = view.findViewById(R.id.ivTop);
        tvEntrance = view.findViewById(R.id.tv_entrance);
        ll_baoming_test = view.findViewById(R.id.ll_baoming_test);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_baoming_qian.setLayoutManager(linearLayoutManager1);
        rv_baoming_hou.setLayoutManager(linearLayoutManager2);
        ll_baoming = (LinearLayout) view.findViewById(R.id.ll_baoming);
        ll_zhitongche = (LinearLayout) view.findViewById(R.id.ll_zhitongche);
        ll_test = (LinearLayout) view.findViewById(R.id.ll_test);
        ll_hospital = (LinearLayout) view.findViewById(R.id.ll_hospital);
        ll_kefu = (LinearLayout) view.findViewById(R.id.ll_kefu);
        ll_xuecheliucheng = (LinearLayout) view.findViewById(R.id.ll_xuecheliucheng);
        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        ll_baoming.setOnClickListener(this);
        ll_zhitongche.setOnClickListener(this);
        ll_test.setOnClickListener(this);
        ll_hospital.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        ivTop.setOnClickListener(this);
        ll_xuecheliucheng.setOnClickListener(this);
        ll_baoming_test.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("考驾照");
        rv_baoming_qian.setAdapter(new BaoMingQianAdapter("qian"));
        rv_baoming_hou.setAdapter(new BaoMingQianAdapter("hou"));


    }

    @Override
    protected void onResume() {
        super.onResume();
        initEntrance();
    }

    private void initEntrance() {
//        学员，我的驾考
//        教练，教学管理
        Map<String,String> map = new HashMap<>();
        map.put("phone",PrefUtils.getParameter("phone"));
        MyHttpUtils.postHttpMessageNoToken("http://jknew.xueyiche.vip:90/api/user/userinfo", map, PhoneUserIdBean.class, new RequestCallBack<PhoneUserIdBean>() {
            @Override
            public void requestSuccess(PhoneUserIdBean json) {
                if (1==json.getCode()) {
                    PhoneUserIdBean.DataDTO data = json.getData();
                    String user_id = data.getUser_id();
                    PrefUtils.putParameter("user_id",user_id);
                    Log.e("user_id_new",""+user_id);
                    OkHttpUtils.post().url(AppUrl.usertype).addParams("user_id", user_id)
                            .build().execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    Log.e("usertype", string);

                                    if (!TextUtils.isEmpty(string)) {
                                        UserTypeBean userTypeBean = JsonUtil.parseJsonToBean(string, UserTypeBean.class);
                                        if (userTypeBean != null) {
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    int code = userTypeBean.getCode();
                                                    if (200 == code) {
                                                        UserTypeBean.ContentBean content = userTypeBean.getContent();
                                                        stu_coach = content.getStu_coach();
                                                        stu_sign_up = content.getStu_sign_up();
                                                        if ("1".equals(stu_coach)) {
                                                            ivEntrance.setImageResource(R.mipmap.jiaoxueguanli);
                                                            tvEntrance.setText("教学管理");
                                                        } else {
                                                            //学员
//            学员，点击我的驾考，报名了的进入那个进程的，没报名的就是报名页
                                                            ivEntrance.setImageResource(R.mipmap.new_jx_bm);
                                                            tvEntrance.setText("我的驾考");
                                                        }

                                                        PrefUtils.putParameter("coach_id", content.getCoach_id());
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
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent3 = new Intent(App.context, UrlActivity.class);
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.ivTop:
                openActivity(MiJiActivity.class);
                break;
            case R.id.tv_wenxintishi:
                if (DialogUtils.IsLogin()) {

                    String user_id = PrefUtils.getString(App.context, "user_id", "");
                    intent3.putExtra("url", "http://xueyiche.cn/xyc/paretime/mywork.html?user_id=" + user_id);
                    intent3.putExtra("type", "7");
                    startActivity(intent3);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }

                break;
            case R.id.ll_xuecheliucheng:
//                intent3.putExtra("url", "http://xueyiche.cn/xyc/kaojiazhao/xuecheliucheng.html");
//                intent3.putExtra("type", "8");
//                startActivity(intent3);
                break;
            case R.id.ll_baoming_test:
                openActivity(StudentsBaoMingActivity.class);
                break;
            case R.id.ll_baoming:
                if ("1".equals(stu_coach)) {
                    //教练
                    Intent intent = new Intent(App.context, YuYueTrainingActivity.class);
                    intent.putExtra("coach_id", PrefUtils.getParameter("user_id") + "");
                    startActivity(intent);
                } else {
                    //学员
                    openActivity(StudentsSelfTestActivity.class);

                }
                break;
            case R.id.ll_zhitongche:
                if (DialogUtils.IsLogin()) {
//                    openActivity(DirectDriverSchoolSubmitIndent.class);
                    openActivity(CoachListActivity.class);//教练列表
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.ll_test:
                openActivity(ExamActivity.class);
                break;
            case R.id.ll_hospital:
                openActivity(TiJianActivity.class);
                break;
            case R.id.ll_kefu:
                intent3.putExtra("url", "http://xueyiche.cn/xyc/kaojiazhao/baomingxuzhi.html");
                intent3.putExtra("type", "1");
                startActivity(intent3);
                break;
            default:

                break;
        }
    }

    public class BaoMingQianAdapter extends RecyclerView.Adapter<BaoMingQianAdapter.ViewHolder> {
        private String type;

        public BaoMingQianAdapter(String type) {
            this.type = type;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout iv_content;
            TextView tvTitle, tvContent;

            public ViewHolder(View v) {
                super(v);
            }

        }

        @Override
        public int getItemCount() {
            return 4;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.baoming_rv_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.iv_content = (LinearLayout) v.findViewById(R.id.iv_content);
            holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            holder.tvContent = (TextView) v.findViewById(R.id.tvContent);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            switch (position) {
                case 0:
                    holder.iv_content.setBackgroundResource(R.mipmap.rule_bg_01);
                    break;
                case 1:
                    holder.iv_content.setBackgroundResource(R.mipmap.rule_bg_02);
                    break;
                case 2:
                    holder.iv_content.setBackgroundResource(R.mipmap.rule_bg_03);
                    break;
                case 3:
                    holder.iv_content.setBackgroundResource(R.mipmap.rule_bg_04);
                    break;
            }
            if ("qian".equals(type)) {
                switch (position) {
                    case 0:
                        holder.tvTitle.setText("选择套餐");
                        holder.tvContent.setText("了解套餐区别，选择适合自己的套餐。");
                        break;
                    case 1:
                        holder.tvTitle.setText("填写报名信息");
                        holder.tvContent.setText("点击 报名 输入身份信息和联系方式。");
                        break;
                    case 2:
                        holder.tvTitle.setText("付学费，完成报名");
                        holder.tvContent.setText("选择支付方式，我们支持支付宝、微信支付。");
                        break;
                    case 3:
                        holder.tvTitle.setText("到驾校录入信息");
                        holder.tvContent.setText("在线报名后，驾校会与您取得联系。到驾校录入个人信息及所需材料。");
                        break;
                }
            } else {
                switch (position) {
                    case 0:
                        holder.tvTitle.setText("科目一");
                        holder.tvContent.setText("在APP进行科目一学习，测试过关可进行约考。");
                        break;
                    case 1:
                        holder.tvTitle.setText("科目二");
                        holder.tvContent.setText("学习科目二相关课程后，确保掌握操作与驾驶要领，可参加科目二考试。");
                        break;
                    case 2:
                        holder.tvTitle.setText("科目三");
                        holder.tvContent.setText("学习科目三相关驾驶训练结束后，可约考科目三。");
                        break;
                    case 3:
                        holder.tvTitle.setText("科目四");
                        holder.tvContent.setText("通过APP上的科目四学习&测试，可约考科目四。");
                        break;

                }
            }


        }


    }
}
