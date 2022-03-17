package com.xueyiche.zjyk.xueyiche.practicecar.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.DriverDetails;
import com.xueyiche.zjyk.xueyiche.custom.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.ObservableScrollView;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.AllRemarkActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.activity.lianche.SubmitZhiJiePractice;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglei on 2016/11/2.
 */
public class ChoiceCardDetails extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    //教练详情
    private ImageView iv_Back;
    private TextView tv_title, tv_state, tv_drivers_name, tv_drivers_type, tv_drivers_distance, tv_service_number, tv_drivers_money, tv_drivers_describe, tv_drivers_work_time, tv_drivers_hours, tv_car_type, tv_look_all;
    private CustomShapeImageView iv_drivers_head;
    private AdListView rv_car_information;
    private AdListView lv_student_remark;
    private ImageView bt_order;
    private int SHOW_NUM = 1;
    private List<String> tempList = new ArrayList<>();
    private String driver_id, from_user;
    private CheckBox tv_look_more;
    private List<DriverDetails.ContentBean.PinglunBean> pinglun;
    private ObservableScrollView os_shop;
    private CarInformationImageAdapter carInformationImageAdapter;
    private String string;

    @Override
    protected int initContentView() {
        return R.layout.choice_card_details;
    }

    @Override
    protected void initView() {
        iv_drivers_head = view.findViewById(R.id.iv_drivers_head);
        iv_Back = view.findViewById(R.id.title).findViewById(R.id.iv_login_back);
        tv_title = view.findViewById(R.id.title).findViewById(R.id.tv_title);
        tv_drivers_name = view.findViewById(R.id.tv_drivers_name);
        tv_drivers_type = view.findViewById(R.id.tv_drivers_type);
        tv_service_number = view.findViewById(R.id.tv_service_number);
        tv_drivers_distance = view.findViewById(R.id.tv_drivers_distance);
        tv_drivers_money = view.findViewById(R.id.tv_drivers_money);
        tv_drivers_describe = view.findViewById(R.id.tv_drivers_describe);
        tv_drivers_work_time = view.findViewById(R.id.tv_drivers_work_time);
        tv_drivers_hours = view.findViewById(R.id.tv_drivers_hours);
        tv_car_type = view.findViewById(R.id.tv_car_type);
        rv_car_information = view.findViewById(R.id.rv_car_information);
        tv_look_more = view.findViewById(R.id.tv_look_more);
        tv_look_all = view.findViewById(R.id.tv_look_all);
        lv_student_remark = view.findViewById(R.id.lv_student_remark);
        tv_state = view.findViewById(R.id.tv_state);
        bt_order = view.findViewById(R.id.bt_order);
        os_shop = view.findViewById(R.id.os_shop);

    }

    @Override
    protected void initListener() {
        NetBroadcastReceiver.mListeners.add(this);
        iv_Back.setOnClickListener(this);
        tv_look_more.setOnClickListener(this);
        tv_look_all.setOnClickListener(this);
        bt_order.setOnClickListener(this);
        carInformationImageAdapter = new CarInformationImageAdapter();

        tv_look_more.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SHOW_NUM = tempList.size();
                    rv_car_information.setAdapter(carInformationImageAdapter);
                    carInformationImageAdapter.notifyDataSetChanged();
                    tv_look_more.setText("收起  ");
                } else {
                    SHOW_NUM = 1;
                    rv_car_information.setAdapter(carInformationImageAdapter);
                    carInformationImageAdapter.notifyDataSetChanged();
                    tv_look_more.setText("查看全部  ");
                }
            }
        });
        os_shop.smoothScrollTo(0, 0);
        os_shop.smoothScrollBy(0, 0);
        os_shop.scrollTo(0, 0);
        lv_student_remark.setFocusable(false);
        rv_car_information.setFocusable(false);
    }

    @Override
    protected void initData() {
        tv_title.setText("详情");
        Intent intent = getIntent();
        driver_id = intent.getStringExtra("driver_id");
        from_user = intent.getStringExtra("from_user");
        getDataFromNet();
    }

    public void getDataFromNet() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        showProgressDialog(false);
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post()
                    .url(AppUrl.Driver_Details)
                    .addParams("driver_id", driver_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("user_id", user_id)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                processData(string);
                            }
                        });
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
                }
            });
        } else {
            stopProgressDialog();
            showToastLong(StringConstants.CHECK_NET);
        }
    }

    private void processData(String string) {
        DriverDetails driverDetails = JsonUtil.parseJsonToBean(string, DriverDetails.class);
        if (driverDetails != null) {
            final DriverDetails.ContentBean content = driverDetails.getContent();
            if (content != null) {
                App.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String head_img = content.getHead_img();
                        String driver_name = content.getDriver_name();
                        String driving_year = content.getDriving_year();
                        String order_count = content.getOrder_count();
                        String seriesname = content.getSeriesname();
                        String hour_price = content.getHour_price();
                        String self_estimate = content.getSelf_estimate();
                        String jdsj = content.getJdsj();
                        String qysc = content.getQysc();
                        String brand_name = content.getBrand_name();
                        String hand_auto = content.getHand_auto();
                        String comment_count = content.getComment_count();
                        String on_off = content.getOn_off();

                        List<DriverDetails.ContentBean.FengcaiBean> fengcai = content.getFengcai();
                        pinglun = content.getPinglun();
                        if (pinglun.size() > 0 && pinglun != null) {
                            lv_student_remark.setAdapter(new RemarkAdapter());
                            tv_look_all.setText("全部（" + pinglun.size() + "）");
                        }
                        if (fengcai != null) {
                            for (int i = 0; i < fengcai.size(); i++) {
                                String car_url = fengcai.get(i).getCar_url();
                                tempList.add(car_url);
                            }
                        }
                        rv_car_information.setAdapter(new CarInformationImageAdapter());
                        //0:等待接单 1: 休息 2：进行中
                        if (!TextUtils.isEmpty(on_off)) {
                            if ("0".equals(on_off)) {
                                tv_state.setBackgroundResource(R.drawable.circle_green_shape);
                            } else if ("1".equals(on_off)) {
                                tv_state.setBackgroundResource(R.drawable.circle_white_gray_shape);
                            } else if ("2".equals(on_off)) {
                                tv_state.setBackgroundResource(R.drawable.circle_red_shape);
                            }
                        }
                        if (!TextUtils.isEmpty(head_img)) {
                            Picasso.with(App.context).load(head_img).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(iv_drivers_head);
                        }
                        if (!TextUtils.isEmpty(driver_name)) {
                            tv_drivers_name.setText(driver_name);
                        }
                        if (!TextUtils.isEmpty(driving_year)) {
                            tv_drivers_type.setText(driving_year);
                        }
                        if (!TextUtils.isEmpty(order_count)) {
                            tv_service_number.setText(order_count);
                        }
                        if (!TextUtils.isEmpty(from_user)) {
                            tv_drivers_distance.setText(from_user);
                        }
                        if (!TextUtils.isEmpty(hour_price)) {
                            tv_drivers_money.setText(hour_price + "/小时");
                        }
                        if (!TextUtils.isEmpty(self_estimate)) {
                            tv_drivers_describe.setText(self_estimate);
                        }
                        if (!TextUtils.isEmpty(jdsj)) {
                            tv_drivers_work_time.setText(jdsj);
                        }
                        if (!TextUtils.isEmpty(qysc)) {
                            tv_drivers_hours.setText(qysc);
                        }
                        if (!TextUtils.isEmpty(brand_name) && !TextUtils.isEmpty(seriesname) && !TextUtils.isEmpty(hand_auto)) {
                            tv_car_type.setText(brand_name + seriesname + "  " + hand_auto);
                        }
                    }
                });
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.tv_look_all:
                Intent intent1 = new Intent(App.context, AllRemarkActivity.class);
                intent1.putExtra("json", string);
                startActivity(intent1);
                break;
            case R.id.bt_order:
                Intent intent = new Intent(this, SubmitZhiJiePractice.class);
                intent.putExtra("driver_id", driver_id);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            initData();
        }
    }

    private class RemarkAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RemarkViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.practice_remark_list_item, null);
                holder = new RemarkViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (RemarkViewHolder) view.getTag();
            }
            DriverDetails.ContentBean.PinglunBean pinglunBean = pinglun.get(i);
            if (pinglunBean != null) {
                String all_evaluate = pinglunBean.getAll_evaluate();
                String head_img = pinglunBean.getHead_img();
                String nickname = pinglunBean.getNickname();
                String content = pinglunBean.getContent();
                String content_time = pinglunBean.getContent_time();
                holder.rb_remark_grade.setStepSize(0.5f);
                if (!TextUtils.isEmpty(all_evaluate)) {
                    float star = Float.parseFloat(all_evaluate);
                    holder.rb_remark_grade.setRating(star);
                }
                if (!TextUtils.isEmpty(head_img)) {
                    Picasso.with(App.context).load(head_img).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.cv_remark_head);
                }
                if (!TextUtils.isEmpty(nickname)) {
                    holder.tv_name.setText(nickname);
                }
                if (!TextUtils.isEmpty(content)) {
                    holder.tv_content.setText(content);
                }
                if (!TextUtils.isEmpty(content_time)) {
                    holder.tv_time.setText(content_time);
                }
                if (!TextUtils.isEmpty(all_evaluate)) {
                    holder.tv_start_grade.setText("\"" + all_evaluate + "\"");
                }

            }

            return view;
        }

        class RemarkViewHolder {
            private CircleImageView cv_remark_head;
            private TextView tv_name, tv_time, tv_start_grade, tv_content;
            private RatingBar rb_remark_grade;

            public RemarkViewHolder(View view) {
                cv_remark_head = view.findViewById(R.id.cv_remark_head);
                tv_time = view.findViewById(R.id.tv_time);
                rb_remark_grade = view.findViewById(R.id.rb_remark_grade);
                tv_start_grade = view.findViewById(R.id.tv_start_grade);
                tv_content = view.findViewById(R.id.tv_content);
                tv_name = view.findViewById(R.id.tv_name);
            }
        }
    }

    public class CarInformationImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return SHOW_NUM;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ItemViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.practice_remark_image_item, null);
                holder = new ItemViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ItemViewHolder) view.getTag();
            }
            if (tempList.size() > 0 && tempList != null) {
                Picasso.with(App.context).load(tempList.get(i)).into(holder.iv_picture);
            }
            return view;
        }

        class ItemViewHolder {
            RoundImageView iv_picture;

            public ItemViewHolder(View itemView) {
                iv_picture = itemView.findViewById(R.id.iv_picture);
            }
        }
    }
}
