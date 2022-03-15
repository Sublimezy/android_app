package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.PingJiaBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Owner on 2016/12/20.
 */
public class PingJia extends BaseActivity implements View.OnClickListener , RadioGroup.OnCheckedChangeListener{
    private LinearLayout llBack;
    private TextView tvTitle, tvNumber;
    private EditText edPingJia;
    private final int charMaxNum = 80; // 允许输入的字数
    private RatingBar rb_pingjia_star;
    private int i1, i2, i3;
    private String opinion, all_evaluate, service_attitude, technological_level, order_number;
    private String pingjia_type;
    private String user_id;
    //头像
    private ImageView iv_driver_head;
    //教练名字
    private TextView tv_driver_name;
    //教练的情况
    private TextView tv_driver_miaoshu;
    private CheckBox rb_one, rb_two, rb_three, rb_four;
    private String[] bad = {"服务不好", "态度很不好", "体验非常不好", "太差了"};
    private String[] yiban = {"服务一般", "态度一般", "体验一般", "技艺平平"};
    private String[] manyi = {"服务态度满意", "体验不错", "氛围很好", "以后还会选择"};
    private String[] very_manyi = {"服务态度非常棒", "体验棒极了", "技术非常高超", "帝王服务"};
    private String[] qianglietuijian = {"史无前例的好", "五星级帝王服务", "会告诉身边朋友", "最愉快的一次体验"};
    private String[] all = new String[]{};
    private String content;
    private ImageView bt_submit;
    private List<CheckBox> checkBoxList = new ArrayList<>();
    //差，一般，满意，非常满意，强烈推荐
    /**
     *
     很差
     服务不好，态度很不好，体验非常不好，太差了，

     一般
     服务一般，态度一般，体验一般，技艺平平

     满意
     服务态度满意，体验不错，氛围很好，以后还会选择

     非常满意
     服务态度非常棒，体验棒极了，技术非常高超，帝王服务

     强烈推荐

     史无前例的好，五星级帝王服务，会告诉身边朋友，最愉快的一次体验
     * @return
     */

    @Override
    protected int initContentView() {
        return R.layout.activity_jiaolian_pingjia;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.driver_pay_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.driver_pay_include).findViewById(R.id.tv_login_back);
        iv_driver_head = (ImageView) view.findViewById(R.id.iv_driver_head);
        rb_pingjia_star = (RatingBar) view.findViewById(R.id.rb_pingjia_star);
        rb_one = (CheckBox) view.findViewById(R.id.rb_one);
        rb_two = (CheckBox) view.findViewById(R.id.rb_two);
        rb_three = (CheckBox) view.findViewById(R.id.rb_three);
        rb_four = (CheckBox) view.findViewById(R.id.rb_four);
        checkBoxList.add(rb_one);
        checkBoxList.add(rb_two);
        checkBoxList.add(rb_three);
        checkBoxList.add(rb_four);
        bt_submit = view.findViewById(R.id.bt_submit);
        edPingJia = (EditText) view.findViewById(R.id.ed_think_content);
        tv_driver_name = (TextView) view.findViewById(R.id.tv_driver_name);
        tv_driver_miaoshu = (TextView) view.findViewById(R.id.tv_driver_miaoshu);
        tvNumber = (TextView) view.findViewById(R.id.text_number);
        order_number = getIntent().getStringExtra("order_number");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        tvTitle.setText("评价");
        llBack.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        rb_pingjia_star.setRating(5f);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        all = qianglietuijian;
        rb_one.setText(all[0]);
        rb_two.setText(all[1]);
        rb_three.setText(all[2]);
        rb_four.setText(all[3]);
        content = "强烈推荐";
        edPingJia.addTextChangedListener(new EditChangedListener());
        tvNumber.setText(0 + "/" + charMaxNum);
        rb_pingjia_star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                i2 = (int) rb_pingjia_star.getRating();
                switch (i2){
                    case 0:
                        rb_pingjia_star.setRating(1f);
                        break;
                    case 1:
                        all = bad;
                        baioqianChoise();
                        content ="很差";
                        break;
                    case 2:
                        all = yiban;
                        baioqianChoise();
                        content ="一般";
                        break;
                    case 3:
                        all = manyi;
                        baioqianChoise();
                        content ="满意";
                        break;
                    case 4:
                        all = very_manyi;
                        baioqianChoise();
                        content ="非常满意";
                        break;
                    case 5:
                        all = qianglietuijian;
                        baioqianChoise();
                        content ="强烈推荐";
                        break;
                }


            }
        });

        getDataFromNet();
    }

    private void getDataFromNet() {

        OkHttpUtils.post().url(AppUrl.Indent_PingJia)
                .addParams("user_id",user_id)
                .addParams("device_id", LoginUtils.getId(PingJia.this))
                .addParams("order_number",order_number)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    com.xueyiche.zjyk.xueyiche.xycindent.bean.PingJiaBean pingJiaBean = JsonUtil.parseJsonToBean(string, com.xueyiche.zjyk.xueyiche.xycindent.bean.PingJiaBean.class);
                    if (pingJiaBean!=null) {
                        int code = pingJiaBean.getCode();
                        if (!TextUtils.isEmpty(""+code)) {
                            if (200==code) {
                                PingJiaBean.ContentBean content = pingJiaBean.getContent();
                                if (content!=null) {
                                    List<PingJiaBean.ContentBean.EvaluateBean> evaluate = content.getEvaluate();
                                    final String head_url = content.getHead_url();
                                    final String name = content.getName();
                                    final String place = content.getPlace();
                                    String refer_id = content.getRefer_id();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!TextUtils.isEmpty(head_url)) {
                                                Picasso.with(App.context).load(head_url).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(iv_driver_head);
                                            }
                                            if (!TextUtils.isEmpty(name)) {
                                                tv_driver_name.setText(name);
                                            }
                                            if (!TextUtils.isEmpty(place)) {
                                                tv_driver_miaoshu.setText(place);
                                            }
                                        }
                                    });


                                }

                            }
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

    //标签的选择
    private void baioqianChoise() {
        rb_one.setText(all[0]);
        rb_two.setText(all[1]);
        rb_three.setText(all[2]);
        rb_four.setText(all[3]);
        rb_one.setChecked(false);
        rb_two.setChecked(false);
        rb_three.setChecked(false);
        rb_four.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.bt_submit:
                //提交信息
                submitInfomation();
                break;
        }
    }

    private void submitInfomation() {
//        StringBuffer sb = new StringBuffer();
//        //遍历集合中的checkBox,判断是否选择，获取选中的文本
//        for (CheckBox checkbox : checkBoxList) {
//            if (checkbox.isChecked()){
//                sb.append(checkbox.getText().toString() + " ");
//            }
//        }
//        String s = sb.toString();
//        LogUtil.e("ss",s);
//        if (sb!=null && "".equals(s)){
//            Toast.makeText(getApplicationContext(), "请至少选择一个标签", Toast.LENGTH_SHORT).show();
//        }
        opinion = edPingJia.getText().toString();
        i1 = (int) rb_pingjia_star.getRating();
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Submit_PingLun)
                    .addParams("order_number", order_number)
                    .addParams("device_id", LoginUtils.getId(PingJia.this))
                    .addParams("content", TextUtils.isEmpty(opinion)?"":opinion)
                    .addParams("user_id", user_id)
                    .addParams("all_evaluate", ""+i1)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        SuccessDisCoverBackBean messageComeBack = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                        final String msg = messageComeBack.getMsg();
                        final int code = messageComeBack.getCode();
                        if (!TextUtils.isEmpty(""+code)) {
                            App.handler
                                    .post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (200==code) {
                                                EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                                                EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                                                finish();
                                            }else {
                                                showToastShort(msg);
                                            }
                                        }
                                    });
                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                    EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                    EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                }
            });

        }
    }



    class EditChangedListener implements TextWatcher {
        private CharSequence temp; // 监听前的文本
        private int editStart; // 光标开始位置
        private int editEnd; // 光标结束位置

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvNumber.setText((s.length()) + "/" + charMaxNum);
        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = edPingJia.getSelectionStart();
            editEnd = edPingJia.getSelectionEnd();
            if (temp.length() > charMaxNum) {
                s.delete(editStart - 1, editEnd);
                edPingJia.setText(s);
                edPingJia.setSelection(s.length());
                showToastShort("最多可输入80个字");

            }

        }
    }
}
