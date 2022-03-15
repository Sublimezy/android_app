package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.adapter.BaseCommonAdapter;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.view.BaseViewHolder;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.usedcar.activity.zhengxin.WriteInfomationActivity;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarBean;
import com.xueyiche.zjyk.xueyiche.usedcar.view.UsedCarFlyBanner;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Owner on 2018/6/27.
 */
public class UsedCarActivity extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler, EasyPermissions.PermissionCallbacks {

    private LinearLayout ll_exam_back;
    private EditText tv_search_title;
    private ImageView iv_saoma;
    private TextView tv_city, tv_allcar_number, tv_allcar_num,tv_search;
    private UsedCarFlyBanner fb_used_car;
    //买车，租车，卖车 新车
    private LinearLayout ll_one, ll_two, ll_three,ll_four,ll_five;
    private MyGridView gv_text_label, gv_image_label;
    private LinearLayout ll_show_all_usedcar, ll_show_all_usedca;
    private AdListView alv_hot_used_car;
    private ScrollView sv_used_car;
    private List<UsedCarBean.DataBean.BroadcastListBean> broadcastList;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private List<UsedCarBean.DataBean.CarSourceHotBean> carSourceHot;
    private List<UsedCarBean.DataBean.LevelHotBean> levelHot;
    private List<UsedCarBean.DataBean.RbHotBean> rbHot;
    private String usedcarcity;

    @Override
    protected int initContentView() {
        return R.layout.used_car_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_search_title =view.findViewById(R.id.tv_search_title);
        iv_saoma = (ImageView) view.findViewById(R.id.title).findViewById(R.id.iv_saoma);
        tv_city = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_city);
        usedcarcity = PrefUtils.getString(App.context, "usedcarcity", "");
        if (TextUtils.isEmpty(usedcarcity)) {
            usedcarcity = "哈尔滨";
            PrefUtils.putString(App.context, "usedcarcity", "哈尔滨");
        }
        tv_city.setText(usedcarcity);
        //全部车辆数
        tv_allcar_number = (TextView) view.findViewById(R.id.tv_allcar_number);
        tv_allcar_num = (TextView) view.findViewById(R.id.tv_allcar_num);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        //轮播图
        fb_used_car = (UsedCarFlyBanner) view.findViewById(R.id.fb_used_car);
        ll_one = (LinearLayout) view.findViewById(R.id.ll_one);
        ll_two = (LinearLayout) view.findViewById(R.id.ll_two);
        ll_three = (LinearLayout) view.findViewById(R.id.ll_three);
        ll_four = (LinearLayout) view.findViewById(R.id.ll_four);
        ll_five = (LinearLayout) view.findViewById(R.id.ll_five);
        gv_text_label = (MyGridView) view.findViewById(R.id.gv_text_label);
        gv_image_label = (MyGridView) view.findViewById(R.id.gv_image_label);
        ll_show_all_usedcar = (LinearLayout) view.findViewById(R.id.ll_show_all_usedcar);
        ll_show_all_usedca = (LinearLayout) view.findViewById(R.id.ll_show_all_usedca);
        alv_hot_used_car = (AdListView) view.findViewById(R.id.alv_hot_used_car);
        sv_used_car = (ScrollView) view.findViewById(R.id.sv_used_car);
        alv_hot_used_car.setVerticalScrollBarEnabled(false);
        alv_hot_used_car.setFastScrollEnabled(false);
        alv_hot_used_car.setFocusable(false);
        sv_used_car.smoothScrollBy(0, 0);
        sv_used_car.smoothScrollTo(0, 0);
        NetBroadcastReceiver.mListeners.add(this);
    }


    @Override
    protected void initListener() {
        tv_city.setOnClickListener(this);
        ll_show_all_usedcar.setOnClickListener(this);
        ll_show_all_usedca.setOnClickListener(this);
        ll_one.setOnClickListener(this);
        ll_two.setOnClickListener(this);
        ll_three.setOnClickListener(this);
        ll_four.setOnClickListener(this);
        iv_saoma.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        ll_five.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        fb_used_car.setOnItemClickListener(new UsedCarFlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (broadcastList != null) {
                    UsedCarBean.DataBean.BroadcastListBean broadcastListBean = broadcastList.get(position);
                    if (broadcastListBean != null) {
                        String url = broadcastListBean.getUrl();
                        if (!TextUtils.isEmpty(url)&&!"#".equals(url)) {
                            Intent intent = new Intent(UsedCarActivity.this, UrlActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("type", "2");
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        alv_hot_used_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UsedCarBean.DataBean.CarSourceHotBean carSourceHotBean = carSourceHot.get(i);
                if (carSourceHotBean != null) {
                    int id = carSourceHotBean.getId();
                    String htmlUrl = carSourceHotBean.getHtmlUrl();
                    if (!TextUtils.isEmpty("" + id)) {
                        Intent intent1 = new Intent(App.context, UsedCarContentActivity.class);
                        intent1.putExtra("carsource_id", id + "");
                        intent1.putExtra("htmlUrl",htmlUrl);
                        intent1.putExtra("cartype", "buy");
                        intent1.putExtra("qu_time_date", "");
                        intent1.putExtra("huan_time_date", "");
                        intent1.putExtra("store_id_usedcar", "");
                        intent1.putExtra("duration", "");
                        intent1.putExtra("qu_city_usedcar", "");
                        intent1.putExtra("huan_city_usedcar", "");
                        intent1.putExtra("shangMenQu", "");
                        intent1.putExtra("shangMenHuan", "");
                        intent1.putExtra("qu_latitude_usedcar", "");
                        intent1.putExtra("qu_longitude_usedcar", "");
                        intent1.putExtra("qu_name_usedcar", "");
                        intent1.putExtra("huan_latitude_usedcar", "");
                        intent1.putExtra("huan_longitude_usedcar", "");
                        intent1.putExtra("huan_name_usedcar", "");
                        startActivity(intent1);
                    }
                }
            }
        });
        gv_image_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int level_id = levelHot.get(i).getId();
                Intent intent = new Intent(UsedCarActivity.this, BuyCarActivity.class);
                intent.putExtra("rbhot_type", "");
                intent.putExtra("rbhot_id", "");
                intent.putExtra("level_id", level_id + "");
                intent.putExtra("carSourceSearch", "");
                startActivity(intent);
            }
        });
        gv_text_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int rbhot_id = rbHot.get(i).getRbhot_id();
                int rbhot_type = rbHot.get(i).getRbhot_type();
                Intent intent = new Intent(UsedCarActivity.this, BuyCarActivity.class);
                intent.putExtra("rbhot_type", rbhot_type + "");
                intent.putExtra("rbhot_id", rbhot_id + "");
                intent.putExtra("level_id", "");
                intent.putExtra("carSourceSearch", "");
                startActivity(intent);
            }
        });
    }

    private void lunbotu(List<UsedCarBean.DataBean.BroadcastListBean> broadcastList) {
        List<String> imageurls = new ArrayList<String>();
        if (broadcastList != null) {
            for (UsedCarBean.DataBean.BroadcastListBean broadcastListBean : broadcastList) {
                String broadcast_pic = broadcastListBean.getBroadcast_pic();
                if (!TextUtils.isEmpty(broadcast_pic)) {
                    imageurls.add(broadcast_pic);
                }
            }
            fb_used_car.setImagesUrl(imageurls);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fb_used_car.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        fb_used_car.stopAutoPlay();

    }

    @Override
    protected void initData() {
        setCity();
        getdate();
    }

    private void getdate() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            String id = LoginUtils.getId(this);
            OkHttpUtils.post().url(AppUrl.Used_Car_ShouYe)
                    .addParams("device_id", id)
                    .addParams("area", usedcarcity)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final UsedCarBean usedCarBean = JsonUtil.parseJsonToBean(string, UsedCarBean.class);
                        if (usedCarBean != null) {
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    UsedCarBean.DataBean data = usedCarBean.getData();
                                    if (data != null) {
                                        int carSourceAll = data.getCarSourceAll();
                                        tv_allcar_number.setText(carSourceAll + "");
                                        tv_allcar_num.setText(carSourceAll + "");
                                        //热门推荐车辆
                                        carSourceHot = data.getCarSourceHot();
                                        //热门标签
                                        rbHot = data.getRbHot();
                                        //热门图片
                                        levelHot = data.getLevelHot();
                                        //轮播图
                                        broadcastList = data.getBroadcastList();
                                        if (carSourceHot != null) {
                                            HotUserCarAdapter hotUserCarAdapter = new HotUserCarAdapter(carSourceHot, App.context, R.layout.used_car_list_item);
                                            alv_hot_used_car.setAdapter(hotUserCarAdapter);
                                        }
                                        if (rbHot != null) {
                                            GridTextlableAdapter gridTextlableAdapter = new GridTextlableAdapter(rbHot, App.context, R.layout.gv_text_label_item);
                                            gv_text_label.setAdapter(gridTextlableAdapter);
                                        }
                                        if (levelHot != null) {
                                            GridImagelableAdapter gridImagelableAdapter = new GridImagelableAdapter(levelHot, App.context, R.layout.gv_image_label_item);
                                            gv_image_label.setAdapter(gridImagelableAdapter);
                                        }
                                        if (broadcastList != null) {
                                            lunbotu(broadcastList);
                                        }
                                        sv_used_car.smoothScrollBy(0, 0);
                                    }
                                }
                            });

                        }
                        return string;
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
        }

    }

    private void setCity() {

    }

    @Override
    protected void onStart() {
        requestCodeQRCodePermissions();
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(UsedCarActivity.this, UsedCarScanActivity.class);
        Intent intent1 = new Intent(App.context, UsedCarSearchActivity.class);
        Intent intent2 = new Intent(UsedCarActivity.this, BuyCarActivity.class);
        Intent intent3 = new Intent(this,BuyCarActivity.class);
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_search:
                String trim = tv_search_title.getText().toString();
                intent3.putExtra("carSourceSearch", trim);
                intent3.putExtra("rbhot_type", "");
                intent3.putExtra("rbhot_id", "");
                intent3.putExtra("level_id", "");
                startActivity(intent3);
                break;
            case R.id.iv_saoma:
                //扫描二维码
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_five:
                if (DialogUtils.IsLogin()) {
                    Intent intentZheng = new Intent(UsedCarActivity.this, WriteInfomationActivity.class);
                    startActivity(intentZheng);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
            case R.id.tv_city:
                //选择开通城市
                openActivity(UsedCarLocationActivity.class);
                finish();
                break;
            case R.id.ll_show_all_usedcar:
                //车辆列表
                intent2.putExtra("rbhot_type", "");
                intent2.putExtra("rbhot_id", "");
                intent2.putExtra("level_id", "");
                intent2.putExtra("carSourceSearch", "");
                startActivity(intent2);
                break;
            case R.id.ll_show_all_usedca:
                //车辆列表
                intent2.putExtra("rbhot_type", "");
                intent2.putExtra("rbhot_id", "");
                intent2.putExtra("level_id", "");
                intent2.putExtra("carSourceSearch", "");
                startActivity(intent2);
                break;
            case R.id.ll_one:
                //买
                intent2.putExtra("rbhot_type", "");
                intent2.putExtra("rbhot_id", "");
                intent2.putExtra("level_id", "");
                intent2.putExtra("carSourceSearch", "");
                startActivity(intent2);
                break;
            case R.id.ll_two:
                //租
                openActivity(RentCarActivity.class);
                break;
            case R.id.ll_three:
                //卖
                openActivity(SellCarActivity.class);
                break;
            case R.id.ll_four:
                //汽车报价
                Intent intent12 = new Intent(App.context, UrlActivity.class);
                intent12.putExtra("url", "https://auto.news18a.com/m/price/select_brands/brand/?ina_from=xueyiche");
                intent12.putExtra("type", "5");
                startActivity(intent12);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        String code = data.getStringExtra("code");
        String content = data.getStringExtra("content");
        switch (requestCode) {
            case 0:
                if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(content)) {
                    if ("0" == code) {
//                        DialogUtils.showTuiJianResult(true,MyDaiLi.this,false,content);
//                        getNumber();
                    } else {
//                        DialogUtils.showTuiJianResult(false,MyDaiLi.this,false,content);
                    }
                }
                break;

        }
    }

    @Override
    public void onNetChange() {
        getdate();
    }


    private class GridTextlableAdapter extends BaseCommonAdapter {

        public GridTextlableAdapter(List<UsedCarBean.DataBean.RbHotBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarBean.DataBean.RbHotBean rbHotBean = (UsedCarBean.DataBean.RbHotBean) item;
            String rbhot_name = rbHotBean.getRbhot_name();
            viewHolder.setText(R.id.tv_label_content, rbhot_name);
        }
    }

    private class GridImagelableAdapter extends BaseCommonAdapter {
        public GridImagelableAdapter(List<UsedCarBean.DataBean.LevelHotBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarBean.DataBean.LevelHotBean levelHotBean = (UsedCarBean.DataBean.LevelHotBean) item;
            String level_img = levelHotBean.getLevel_img();
            viewHolder.setPic(R.id.iv_label, level_img);
        }
    }

    private class HotUserCarAdapter extends BaseCommonAdapter {
        public HotUserCarAdapter(List<UsedCarBean.DataBean.CarSourceHotBean> mDatas, Context context, int layoutId) {
            super(mDatas, context, layoutId);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Object item) {
            UsedCarBean.DataBean.CarSourceHotBean similarBean = (UsedCarBean.DataBean.CarSourceHotBean) item;
            String just_side_img = similarBean.getJust_side_img();
            int status = similarBean.getStatus();
            String car_allname = similarBean.getCar_allname();
            //月供
            String loan_month = similarBean.getLoan_month();
            //首付
            String loan_first = similarBean.getLoan_first();
            //上牌时间公里数
            String last_mileage = similarBean.getLast_mileage();
            //售价
            String car_price = similarBean.getCar_price();
            //新车售价
            String new_car_price = similarBean.getNew_car_price();
            //平台租车价
            String rent_price = similarBean.getRent_price();
            //新车租车价
            String new_rent_price = similarBean.getNew_rent_price();
            //车辆是否租
            int rent_status = similarBean.getRent_status();
            viewHolder.setPicRound(R.id.iv_used_car_photo, just_side_img);
            if (1 == status) {
                //出租中
                viewHolder.changeImageVisible(R.id.iv_used_car_state);
            } else {
                viewHolder.changeImageGone(R.id.iv_used_car_state);
            }
            viewHolder.setText(R.id.tv_usedcar_title, car_allname);
            viewHolder.setText(R.id.tv_money_buy_loan, loan_first + "  " + loan_month);
            viewHolder.setText(R.id.tv_shangpan_gongli,"行驶里程："+last_mileage);
            viewHolder.setText(R.id.tv_car_price, car_price);
            viewHolder.setText(R.id.tv_new_car_price,  new_car_price);
            viewHolder.setText(R.id.tv_rent_price, rent_price);
            viewHolder.setText(R.id.tv_new_rent_price, new_rent_price );
            if (0 == rent_status) {
                viewHolder.changeLinearLayoutInVisible(R.id.ll_rent_content);
            } else if (1 == rent_status) {
                viewHolder.changeLinearLayoutVisible(R.id.ll_rent_content);
            }
        }
    }
}
