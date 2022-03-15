package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.WXZhiFuBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.BaseBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.BuyKeShiDateActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.BuyTrainTimeStudentActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.MyTestBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.PayStudyHourBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.OnItemClickLitener;
import com.xueyiche.zjyk.xueyiche.pay.bean.ZhiFuBaoBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PayResult;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 预约详情购买支付
 */
public class StudentsOrderBuyActivity extends NewBaseActivity {

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
    @BindView(R.id.tv_kemu_2)
    TextView tvKemu2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tvOne)
    TextView tvOne;
    @BindView(R.id.tvTwo)
    TextView tvTwo;
    @BindView(R.id.tvThree)
    TextView tvThree;
    @BindView(R.id.tv_lianxi_xiangmu)
    TextView tvLianxiXiangmu;
    @BindView(R.id.recycler_item)
    RecyclerView recyclerItem;
    @BindView(R.id.ll_lianxi_xiangmu)
    LinearLayout llLianxiXiangmu;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rg_all)
    RadioGroup rgAll;
    @BindView(R.id.tvPingTai)
    TextView tvPingTai;
    @BindView(R.id.tvYouHui)
    TextView tvYouHui;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    @BindView(R.id.tv_time_range)
    TextView tv_time_range;
    private List<String> selectDatas = new ArrayList<>();
    private XiangMuAdapter projectAdapter;
    private String payType = "0";
    private String driving_type;
    private String time_range;
    private String selected_period_id;
    private String selected_date;
    private String total_num;
    private String id;

    @Override
    protected int initContentView() {
        return R.layout.students_order_buy_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        recyclerItem.setLayoutManager(new LinearLayoutManager(StudentsOrderBuyActivity.this));
        titleBarRl.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).keyboardEnable(true).init();
        titleBarIvBack.setImageDrawable(getResources().getDrawable(R.mipmap.white_iv_back));
        titleBarTitleText.setText("预约详情");
        titleBarTitleText.setTextColor(getResources().getColor(R.color.white));

    }

    public void onEvent(MyEvent event) {
        String msg = event.getMsg();
        if (TextUtils.equals("支付成功", msg)) {
            showToastShort("购买学时成功！");
            BuyKeShiDateActivity.stance.finish();
            BuyTrainTimeStudentActivity.stance.finish();
            finish();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        intent.getStringExtra("coach_id");//教练id
        String coach_name = intent.getStringExtra("coach_name");//教练名字
        selected_period_id = intent.getStringExtra("selected_period_id");
        // 	0:科目二 1:科目三
        driving_type = intent.getStringExtra("driving_type");
        intent.getStringExtra("selected_date_id");
        //xxxx年xx月  星期x
        selected_date = intent.getStringExtra("selected_date");
        //共xx学时
        total_num = intent.getStringExtra("total_num");
        id = intent.getStringExtra("id");
        //9:00-10:00、10:00-11:00
        time_range = intent.getStringExtra("time_range");
        String TotalMoney = intent.getStringExtra("TotalMoney");//一共花了多少钱
        tvBtn.setText("合计：¥" + TotalMoney + " 确定支付");
        tvKemu2.setText(driving_type.equals("0") ? "科目二" : "科目三");
        tvTwo.setText(selected_date);
        tvThree.setText(total_num);
        tv_time_range.setText(time_range);
        tvName.setText(coach_name);
        getDataFromNet(driving_type);
    }

    private void getDataFromNet(String driving_type) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            OkHttpUtils.post().url(AppUrl.selectstudyprocess)
                    .addParams("stu_user_id", PrefUtils.getParameter("user_id"))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("asdasdad", "" + string);
                    if (!TextUtils.isEmpty(string)) {
                        MyTestBean myTestBean = JsonUtil.parseJsonToBean(string, MyTestBean.class);
                        if (myTestBean != null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int code = myTestBean.getCode();
                                    if (200 == code) {
                                        MyTestBean.ContentBean content = myTestBean.getContent();
                                        if (content != null) {
                                            List<MyTestBean.ContentBean.AllListBean> all_list = content.getAll_list();
                                            if (all_list != null && all_list.size() > 0) {
                                                List<MyTestBean.ContentBean.AllListBean.ListBean> list_er = all_list.get(1).getList();
                                                List<MyTestBean.ContentBean.AllListBean.ListBean> list_san = all_list.get(2).getList();
                                                if ("0".equals(driving_type)) {
                                                    projectAdapter = new XiangMuAdapter(list_er);
                                                } else if ("1".equals(driving_type)) {
                                                    projectAdapter = new XiangMuAdapter(list_san);
                                                }
                                                recyclerItem.setAdapter(projectAdapter);
                                                projectAdapter.setOnItemClickLitener(new OnItemClickLitener() {
                                                    @Override
                                                    public void onItemClick(View view, int position) {
                                                        if (!projectAdapter.isSelected.get(position)) {
                                                            projectAdapter.isSelected.put(position, true); // 修改map的值保存状态
                                                            projectAdapter.notifyDataSetChanged();
                                                            selectDatas.add("" + list_er.get(position).getSubject());

                                                        } else {
                                                            projectAdapter.isSelected.put(position, false); // 修改map的值保存状态
                                                            projectAdapter.notifyDataSetChanged();
                                                            selectDatas.remove("" + list_er.get(position).getSubject());
                                                        }

//                mTvCount.setText("已选中"+selectDatas.size()+"项");
                                                    }

                                                    @Override
                                                    public void onItemLongClick(View view, int position) {

                                                    }
                                                });
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
        } else {
            showToastShort("请检查网络连接");
        }
    }

    @OnClick({R.id.title_bar_back, R.id.rb_1, R.id.rb_2, R.id.tv_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.rb_1:
                payType = "0";
                break;
            case R.id.rb_2:
                payType = "1";
                break;
            case R.id.tv_btn:
                pay();
                break;
        }
    }

    private void pay() {
        if (selectDatas.size() == 0) {
            showToastShort("请选择练习项目");
            return;
        }
        String s = selectDatas.toString();
        int length = s.length();
        String chooseXiangMu = s.substring(1, length - 1);
        Log.e("chooseXiangMu", chooseXiangMu);
        Map<String, String> params = new HashMap<>();
        params.put("stu_user_id", PrefUtils.getParameter("user_id"));
        params.put("entry_project", driving_type.equals("0") ? "科目二" : "科目三");
        params.put("driving_practice", "驾驶练习");
        params.put("selected_period_id", selected_period_id);
        params.put("selected_period", time_range);
        params.put("id", id);
        params.put("practice_time", selected_date);
        params.put("num_class", total_num + "");
        params.put("buy_time", "1");
        params.put("practice_project", chooseXiangMu);

        MyHttpUtils.postHttpMessage(AppUrl.addpracticedriving, params, PayStudyHourBean.class, new RequestCallBack<PayStudyHourBean>() {
            @Override
            public void requestSuccess(PayStudyHourBean json) {
                if (json.getCode() == 200) {
                    String content = json.getContent();
                    if (!TextUtils.isEmpty(content)) {
                        if ("0".equals(payType)) {
                            wechat(content);
                        } else {
                            zfb(content);
                        }
                    }

                } else {
                    showToastShort(json.getMsg());

                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("连接是服务器失败");

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    private void zfb(String driving_order_number) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post().url(AppUrl.paytypedrivingorder)
                    .addParams("driving_order_number", driving_order_number)
                    .addParams("device_id", LoginUtils.getId(StudentsOrderBuyActivity.this))
                    .addParams("user_id", "" + PrefUtils.getParameter("user_id"))
                    .addParams("pay_type", "1")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                        if (zhiFuBaoBean != null) {
                            int code = zhiFuBaoBean.getCode();
                            if (200 == code) {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        final int SDK_PAY_FLAG = 1;
                                        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
                                            @SuppressWarnings("unused")
                                            public void handleMessage(Message msg) {
                                                switch (msg.what) {
                                                    case SDK_PAY_FLAG: {
                                                        @SuppressWarnings("unchecked")
                                                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                                                        /**
                                                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                                                         */
                                                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                                                        String resultStatus = payResult.getResultStatus();
                                                        // 判断resultStatus 为9000则代表支付成功
                                                        if (TextUtils.equals(resultStatus, "9000")) {
                                                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                                                            showToastShort("购买学时成功！");
                                                            BuyKeShiDateActivity.stance.finish();
                                                            BuyTrainTimeStudentActivity.stance.finish();
                                                            finish();
                                                        } else {
                                                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                                            Toast.makeText(StudentsOrderBuyActivity.this, "支付失败,请重新下单", Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    }
                                                }
                                            }

                                        };

                                        Runnable payRunnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!TextUtils.isEmpty(string)) {
                                                    ZhiFuBaoBean zhiFuBaoBean = JsonUtil.parseJsonToBean(string, ZhiFuBaoBean.class);
                                                    if (zhiFuBaoBean != null) {
                                                        String content = zhiFuBaoBean.getContent();
                                                        if (!TextUtils.isEmpty(content)) {
                                                            PayTask alipay = new PayTask(StudentsOrderBuyActivity.this);
                                                            Map<String, String> result = alipay.payV2(content, true);
                                                            Message msg = new Message();
                                                            msg.what = SDK_PAY_FLAG;
                                                            msg.obj = result;
                                                            mHandler.sendMessage(msg);
                                                        }
                                                    }
                                                }
                                            }
                                        };
                                        Thread payThread = new Thread(payRunnable);
                                        payThread.start();
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

    private void wechat(String driving_order_number) {
        if (XueYiCheUtils.isWeixinAvilible(this)) {
            wx(StudentsOrderBuyActivity.this, AppUrl.paytypedrivingorder, driving_order_number, "" + PrefUtils.getParameter("user_id"));

        } else {
            showToastShort("目前您的微信版本过低或未安装微信，需要安装微信才能使用");
        }
    }

    private void wx(Activity activity, String url, String order_number, String user_id) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String id = LoginUtils.getId(activity);
            final WXZhiFuBean.ContentBean[] content = new WXZhiFuBean.ContentBean[1];
            OkHttpUtils.post().url(url)
                    .addParams("driving_order_number", order_number)
                    .addParams("pay_type", "2")
                    .addParams("user_id", user_id)
                    .addParams("device_id", TextUtils.isEmpty(id) ? "" : id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("121212", "" + string);
                    Log.e("121212", "" + order_number);
                    Log.e("121212", "" + url);
                    Log.e("121212", "" + user_id);
                    if (!TextUtils.isEmpty(string)) {
                        WXZhiFuBean wxZhiFuBean = JsonUtil.parseJsonToBean(string, WXZhiFuBean.class);
                        if (wxZhiFuBean != null) {
                            content[0] = wxZhiFuBean.getContent();
                        }
                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {

                }

                @Override
                public void onResponse(Object response) {
                    if (content[0] != null) {
                        String appid = content[0].getAppid();
                        String partnerid = content[0].getPartnerid();
                        String prepayid = content[0].getPrepayid();
                        String noncestr = content[0].getNoncestr();
                        String timestamp = content[0].getTimestamp();
                        String packageValue = content[0].getPackageValue();
                        String sign = content[0].getSign();
                        PayReq req = new PayReq();
                        req.appId = appid;
                        req.partnerId = partnerid;
                        req.prepayId = prepayid;
                        req.nonceStr = noncestr;
                        req.timeStamp = timestamp;
                        req.packageValue = packageValue;
                        req.sign = sign;
                        App.wxapi.sendReq(req);
                    }
                }
            });
        } else {
            Toast.makeText(App.context, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class XiangMuAdapter extends RecyclerView.Adapter {

        private List<MyTestBean.ContentBean.AllListBean.ListBean> content;
        public HashMap<Integer, Boolean> isSelected;

        public XiangMuAdapter(List<MyTestBean.ContentBean.AllListBean.ListBean> content) {
            this.content = content;
            init();
        }

        // 初始化 设置所有item都为未选择
        public void init() {
            isSelected = new HashMap<Integer, Boolean>();
            for (int i = 0; i < content.size(); i++) {
                isSelected.put(i, false);
            }
        }

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zhifu_xiangmu_item_layout, parent, false);

            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MultiViewHolder) {
                final MultiViewHolder viewHolder = (MultiViewHolder) holder;
                viewHolder.itemView.setSelected(isSelected.get(position));
                if (content != null) {
                    MyTestBean.ContentBean.AllListBean.ListBean listBean = content.get(position);
                    String subject = listBean.getSubject();
                    viewHolder.mTvName.setText("" + subject);
                }
                Log.e("adasdasdsadasd", "" + (content.size()));
                if (isSelected.get(position)) {
                    viewHolder.ivCheck.setImageResource(R.mipmap.checkbox_selected);
                } else {
                    viewHolder.ivCheck.setImageResource(R.mipmap.checkbox_normal);

                }
                if (mOnItemClickLitener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                        }
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            if (content != null && content.size() > 0) {
                return content.size();
            } else {
                return 0;
            }
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {
            TextView mTvName;
            ImageView ivCheck;

            public MultiViewHolder(View itemView) {
                super(itemView);
                mTvName = itemView.findViewById(R.id.tvName);
                ivCheck = itemView.findViewById(R.id.ivCheck);
            }
        }

    }
}
