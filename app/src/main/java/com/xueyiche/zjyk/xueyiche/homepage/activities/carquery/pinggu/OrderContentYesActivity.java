package com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.pinggu;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.DriverShcoolBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.activities.carquery.bean.PingGuYesBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Owner on 2019/5/31.
 */
public class OrderContentYesActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private MaxRecyclerView lv_list;
    private List<DriverShcoolBean.ContentBean> list = new ArrayList<>();
    private CarLifrRecyclerViewAdapter carLifrRecyclerViewAdapter;
    private TextView tv_number,tv_tradeprice,tv_accessprice;

    @Override
    protected int initContentView() {
        return R.layout.pinggu_order_content_yes_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        lv_list = (MaxRecyclerView) view.findViewById(R.id.lv_list);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_tradeprice = (TextView) view.findViewById(R.id.tv_tradeprice);
        tv_accessprice = (TextView) view.findViewById(R.id.tv_accessprice);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OrderContentYesActivity.this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_list.setLayoutManager(gridLayoutManager);
        lv_list.setLayoutManager(new GridLayoutManager(OrderContentYesActivity.this, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        lv_list.setFocusable(false);

    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tv_login_back.setText("订单详情");
        String tid = getIntent().getStringExtra("tid");
        getDataFromNet(tid);
    }


    public void getDataFromNet(String tid) {
        String user_phone = PrefUtils.getString(App.context, "user_phone", "");
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.PingGu_Yes)
                    .addParams("tid", tid)
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

    private void processData(final String string) {
        final PingGuYesBean pingGuYesBean = JsonUtil.parseJsonToBean(string, PingGuYesBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (pingGuYesBean != null) {
                    int success = pingGuYesBean.getSuccess();
                    if (1 == success) {
                        List<PingGuYesBean.MessageBean> message = pingGuYesBean.getMessage();
                        if (message != null && message.size() > 0) {
                            PingGuYesBean.MessageBean messageBean = message.get(0);
                            if (messageBean!=null) {
                                int id = messageBean.getId();
                                int tradeprice = messageBean.getTradeprice();
                                int accessprice = messageBean.getAccessprice();
                                tv_number.setText(id+"");
                                tv_tradeprice.setText(tradeprice+"");
                                tv_accessprice.setText(accessprice+"");
                                List<PingGuYesBean.MessageBean.FileInfoBean> content = messageBean.getFile_info();
                                carLifrRecyclerViewAdapter = new CarLifrRecyclerViewAdapter(content);
                                lv_list.setAdapter(carLifrRecyclerViewAdapter);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
        }
    }


    public class CarLifrRecyclerViewAdapter extends RecyclerView.Adapter<CarLifrRecyclerViewAdapter.ViewHolder> {
        private List<PingGuYesBean.MessageBean.FileInfoBean> carLifeServiceTypes;

        public CarLifrRecyclerViewAdapter(List<PingGuYesBean.MessageBean.FileInfoBean> carLifeServiceTypes) {
            this.carLifeServiceTypes = carLifeServiceTypes;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_title;
            RoundImageView iv_bg;

            public ViewHolder(View v) {
                super(v);
            }
        }

        @Override
        public int getItemCount() {
            if (carLifeServiceTypes != null) {
                return carLifeServiceTypes.size();
            } else {
                return 0;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinggu_image_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.iv_bg = (RoundImageView) v.findViewById(R.id.iv_bg);
            holder.tv_title = (TextView) v.findViewById(R.id.tv_title);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (carLifeServiceTypes != null) {
                PingGuYesBean.MessageBean.FileInfoBean fileInfoBean = carLifeServiceTypes.get(position);
                if (fileInfoBean != null) {
                    final String service_name = fileInfoBean.getTag();
                    String ibackground = fileInfoBean.getUrl();
                    int itype = fileInfoBean.getItype();
                    if (!TextUtils.isEmpty(service_name)) {
                        holder.tv_title.setText(service_name);
                    }
                    if (1==itype) {
                        if (!TextUtils.isEmpty(ibackground)) {
                            Picasso.with(App.context).load(ibackground).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(holder.iv_bg);
                        }
                    }else if (2==itype){
                        Bitmap bitmap = getNetVideoBitmap(ibackground);
                        holder.iv_bg.setImageBitmap(bitmap);
                    }

                }
            }
        }
    }
    //获取视频url的第一帧
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
