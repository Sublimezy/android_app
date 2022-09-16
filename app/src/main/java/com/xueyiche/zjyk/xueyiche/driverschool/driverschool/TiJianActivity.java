package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.driverschool.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.TiJianBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2019/3/13.
 */
public class TiJianActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_back, ll_empty;
    private TextView tv_title, tv_empty;
    private ListView lv_tijian;
    private RefreshLayout refreshLayout;
    private YiYuanAdapter yiYuanAdapter;
    private List<TiJianBean.ContentBean> content;
    private String area_id;
    private ImageView iv_caidan;

    @Override
    protected int initContentView() {
        return R.layout.tijian_activity;
    }

    @Override
    protected void initView() {
        ll_back = (LinearLayout) findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_title = (TextView) findViewById(R.id.title).findViewById(R.id.tv_login_back);
        iv_caidan = (ImageView) findViewById(R.id.title).findViewById(R.id.iv_caidan);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        lv_tijian = (ListView) view.findViewById(R.id.lv_tijian);
        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
        area_id = PrefUtils.getString(App.context, "area_id", "");
        yiYuanAdapter = new YiYuanAdapter();


    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        iv_caidan.setVisibility(View.VISIBLE);
        iv_caidan.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        iv_caidan.setImageDrawable(ContextCompat.getDrawable(App.context, R.mipmap.sy_kefu));
        tv_title.setText("体检医院");
        getDataFromNet();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    // 请求网络数据
    public void getDataFromNet() {
        String longitude_user = PrefUtils.getString(App.context, "x", "");
        String latitude_user = PrefUtils.getString(App.context, "y", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.TiJian_List)
                    .addParams("area_id", area_id)
                    .addParams("longitude_user ", longitude_user)
                    .addParams("latitude_user ", latitude_user)
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        final TiJianBean studentListBean = JsonUtil.parseJsonToBean(string, TiJianBean.class);
                        if (studentListBean != null) {
                            final int code = studentListBean.getCode();
                            App.handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (200 == code) {
                                        content = studentListBean.getContent();
                                        lv_tijian.setAdapter(yiYuanAdapter);

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
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.iv_caidan:
                //客服
                openActivity(KeFuActivity.class);
                break;
        }
    }

    private class YiYuanAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (content != null) {
                return content.size();
            } else {
                return 0;
            }


        }

        @Override
        public Object getItem(int position) {
            return content.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            XueYuanViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.tijian_list_item, null);
                holder = new XueYuanViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (XueYuanViewHolder) convertView.getTag();
            }
            if (content!=null&&content.size()>0) {
                TiJianBean.ContentBean contentBean = content.get(position);
                if (contentBean!=null) {
                    final String address = contentBean.getAddress();
                    String image = contentBean.getImage();
                    String from_user = contentBean.getFrom_user();
                    final String hospital_name = contentBean.getHospital_name();
                    final String latitude = contentBean.getLatitude();
                    final String tel_phone = contentBean.getTel_phone();
                    final String longitude = contentBean.getLongitude();
                    String tijian_date = contentBean.getTijian_date();
                    if (!TextUtils.isEmpty(address)) {
                        holder.tv_name.setText(hospital_name);
                    } if (!TextUtils.isEmpty(from_user)) {
                        holder.tv_juli.setText(from_user);
                    }
                    if (!TextUtils.isEmpty(address)) {
                        holder.tv_location.setText(address);
                    }
                    if (!TextUtils.isEmpty(tijian_date)) {
                        holder.tv_time.setText("体检时间：" + tijian_date);
                    }
                    if (!TextUtils.isEmpty(image)) {
                        Picasso.with(App.context).load(image).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(holder.riv_head);
                    }
                    if (!TextUtils.isEmpty(hospital_name) && !TextUtils.isEmpty(tel_phone)) {
                        holder.iv_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                XueYiCheUtils.CallPhone(TiJianActivity.this, hospital_name, tel_phone);
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
                        holder.iv_location.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                XueYiCheUtils.getDiaLocation(TiJianActivity.this, latitude, longitude, hospital_name, address);
                            }
                        });

                    }
                }
            }
            return convertView;
        }

        class XueYuanViewHolder {
            private  RoundImageView riv_head;
            private ImageView iv_phone, iv_location;
            private TextView tv_name, tv_time, tv_location,tv_juli;

            public XueYuanViewHolder(View view) {
                tv_juli = (TextView) view.findViewById(R.id.tv_juli);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                tv_time = (TextView) view.findViewById(R.id.tv_order_sn);
                tv_location = (TextView) view.findViewById(R.id.tv_location);
                iv_phone = (ImageView) view.findViewById(R.id.iv_phone);
                iv_location = (ImageView) view.findViewById(R.id.iv_location);
                riv_head = (RoundImageView) view.findViewById(R.id.riv_head);

            }
        }
    }
}
