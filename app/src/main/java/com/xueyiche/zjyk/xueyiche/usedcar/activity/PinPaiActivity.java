package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.homepage.view.QuickIndexBar;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.usedcar.adapter.UsedCarPinPaiAdapter;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarCheXiAdapter;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarCheXiBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarPinPaiBean;
import com.xueyiche.zjyk.xueyiche.usedcar.view.MyDrawerLayout;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2016/11/23.
 */
public class PinPaiActivity extends BaseActivity implements View.OnClickListener, NetBroadcastReceiver.netEventHandler {
    //选品牌
    private ListView listview_all_car;
    private ListView right_listview;
    private QuickIndexBar mLetterBar;
    private TextView tv_car_title, tv_Title;
    private UsedCarPinPaiAdapter pinPaiYiJiAdapter;
    private MyDrawerLayout drawerlayout;
    private LinearLayout ll_cehua;
    private LinearLayout llBack;
    private RecyclerView rvHotPinPai;
    private GridLayoutManager mLayoutManage;
    private UsedCarPinPaiBean.DataBean data;
    private List<UsedCarCheXiBean.DataBean> data1;
    private String pinpai_id = "";
    private String brandname;
    ;

    @Override
    protected int initContentView() {
        return R.layout.practice_pinpai_card_two;
    }

    @Override
    protected void initView() {
        listview_all_car = (ListView) view.findViewById(R.id.listview_all_car);
        right_listview = (ListView) view.findViewById(R.id.right_listview);
        TextView overlay = (TextView) view.findViewById(R.id.tv_letter);
        drawerlayout = (MyDrawerLayout) view.findViewById(R.id.drawerlayout);
        llBack = (LinearLayout) view.findViewById(R.id.pinpai_include).findViewById(R.id.ll_exam_back);
        tv_Title = (TextView) view.findViewById(R.id.pinpai_include).findViewById(R.id.tv_login_back);
        drawerlayout.setScrimColor(Color.TRANSPARENT);
        ll_cehua = (LinearLayout) findViewById(R.id.ll_cehua);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = ll_cehua.getLayoutParams();//获取drawerlayout的布局
        para.width = width1 / 3 * 2;//修改宽度
        para.height = height1;//修改高度
        ll_cehua.setLayoutParams(para); //设置修改后的布局。
        tv_car_title = (TextView) view.findViewById(R.id.tv_car_title);
        mLetterBar = (QuickIndexBar) view.findViewById(R.id.bar_pinpai);
        mLetterBar.setOverlay(overlay);
        NetBroadcastReceiver.mListeners.add(this);
        right_listview.setDivider(new ColorDrawable(getResources().getColor(R.color.all_line_color)));
        View viewHead = LayoutInflater.from(App.context).inflate(R.layout.pinpai_head, null);
        rvHotPinPai = (RecyclerView) viewHead.findViewById(R.id.rvHotPinPai);
        mLayoutManage = new GridLayoutManager(this, 4);
        mLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        rvHotPinPai.setLayoutManager(mLayoutManage);
        listview_all_car.addHeaderView(viewHead);
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_Title.setText("品牌选择");
        listview_all_car.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerlayout.closeDrawers();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    @Override
    protected void initData() {
        getDataFromNet();
        listview_all_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getCarStyle(position - 1, false);
            }
        });
        right_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long d) {
                if (XueYiCheUtils.IsHaveInternet(PinPaiActivity.this)) {
                    if (data1 != null) {
                        UsedCarCheXiBean.DataBean dataBean = data1.get(position);
                        if (dataBean != null) {
                            int id = dataBean.getId();
                            String system_name = dataBean.getSystem_name();
                            Intent intent = new Intent();
                            intent.putExtra("brand_id", pinpai_id);
                            intent.putExtra("carSystem_id", id + "");
                            intent.putExtra("system_name", system_name);
                            intent.putExtra("brandname", brandname);
                            setResult(123, intent);
                            finish();
                        }
                    }
                } else {
                    showToastShort(StringConstants.CHECK_NET);
                }

            }
        });
    }

    //请求车系
    private void getCarStyle(int position, boolean isHot) {
        if (data != null) {
            List<UsedCarPinPaiBean.DataBean.BrandListBean> brandList = data.getBrandList();
            List<UsedCarPinPaiBean.DataBean.BrandhotBean> brandhot = data.getBrandhot();
            if (brandList != null && brandList.size() != 0 && brandhot != null && brandhot.size() != 0) {
                brandname = "";
                if (isHot) {
                    UsedCarPinPaiBean.DataBean.BrandhotBean brandhotBean = brandhot.get(position);
                    if (brandhotBean != null) {
                        int id = brandhotBean.getId();
                        pinpai_id = id + "";
                        brandname = brandhotBean.getBrand_name();
                    }
                } else {
                    UsedCarPinPaiBean.DataBean.BrandListBean brandListBean = brandList.get(position);
                    if (brandListBean != null) {
                        brandname = brandListBean.getBrand_name();
                        int id = brandListBean.getId();
                        pinpai_id = id + "";
                    }
                }
                tv_car_title.setText(brandname);
                if (XueYiCheUtils.IsHaveInternet(App.context)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(AppUrl.Used_Car_CarSystem)
                            .addParams("device_id", LoginUtils.getId(PinPaiActivity.this))
                            .addParams("brand_id", pinpai_id)
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                process(string);
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
        }
    }

    private void process(String asString) {
        UsedCarCheXiBean usedCarCheXiBean = JsonUtil.parseJsonToBean(asString, UsedCarCheXiBean.class);
        if (usedCarCheXiBean != null) {
            int code = usedCarCheXiBean.getCode();
            if (200 == code) {
                data1 = usedCarCheXiBean.getData();
                if (data1 != null && data1.size() != 0) {
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            drawerlayout.openDrawer(Gravity.RIGHT);
                            pinPaiYiJiAdapter.notifyDataSetChanged();
                            right_listview.setAdapter(new UsedCarCheXiAdapter(data1));
                        }
                    });
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                Intent intent = new Intent();
                intent.putExtra("brand_id", "");
                intent.putExtra("carSystem_id", "");
                intent.putExtra("system_name", "");
                intent.putExtra("brandname", "");
                setResult(123, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("brand_id", "");
            intent.putExtra("carSystem_id", "");
            intent.putExtra("brandname", "");
            intent.putExtra("system_name", "");
            setResult(123, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(PinPaiActivity.this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Used_Car_Brand)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        processData(string);
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


    private void processData(String string) {
        UsedCarPinPaiBean usedCarPinPaiBean = JsonUtil.parseJsonToBean(string, UsedCarPinPaiBean.class);
        if (usedCarPinPaiBean != null) {
            data = usedCarPinPaiBean.getData();
            if (data != null) {
                final List<UsedCarPinPaiBean.DataBean.BrandListBean> brandList = data.getBrandList();
                if (brandList.size() != 0) {
                    App.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pinPaiYiJiAdapter = new UsedCarPinPaiAdapter(brandList);
                            mLetterBar.setOnLetterChangedListener(new QuickIndexBar.OnLetterChangedListener() {
                                @Override
                                public void onLetterChanged(String letter) {
                                    int position = pinPaiYiJiAdapter.getLetterPosition(letter);
                                    listview_all_car.setSelection(position + 1);
                                }
                            });

                            listview_all_car.setAdapter(pinPaiYiJiAdapter);
                            rvHotPinPai.setAdapter(new HotAdapter(App.context));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            getDataFromNet();
        }
    }

    public class HotAdapter extends RecyclerView.Adapter<HotAdapter.TraceViewHolder> {
        private Context mContext;
        private LayoutInflater inflater;

        public HotAdapter(Context mContext) {
            this.mContext = mContext;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getItemCount() {
            return data.getBrandhot().size();
        }

        @Override
        public TraceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TraceViewHolder(inflater.inflate(R.layout.hot_pinpai_item, parent, false));
        }

        @Override
        public void onBindViewHolder(TraceViewHolder holder, final int position) {
            //设置相关数据
            UsedCarPinPaiBean.DataBean.BrandhotBean brandhotBean = data.getBrandhot().get(position);
            String logo = brandhotBean.getBrand_img();
            String name = brandhotBean.getBrand_name();
            if (!TextUtils.isEmpty(name)) {
                holder.tv_item_name.setText(name);
            }
            if (!TextUtils.isEmpty(logo)) {
                Picasso.with(App.context).load(logo).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(holder.iv_item_head);
            }
            holder.ll_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCarStyle(position, true);
                }
            });
        }


        public class TraceViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_item_name;
            private ImageView iv_item_head;
            private LinearLayout ll_all;

            public TraceViewHolder(View itemView) {
                super(itemView);
                tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
                iv_item_head = (ImageView) itemView.findViewById(R.id.iv_item_head);
                ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
            }
        }
    }
}
