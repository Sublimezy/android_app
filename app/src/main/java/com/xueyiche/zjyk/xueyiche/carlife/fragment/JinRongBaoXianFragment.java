package com.xueyiche.zjyk.xueyiche.carlife.fragment;

import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.carlife.carbaoxian.CarBaoXianNew;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CarLifeServiceBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyListview;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJF on 2020/2/20.
 */
public class JinRongBaoXianFragment extends BaseFragment implements View.OnClickListener {


    private BaoXianAdapter baoXianAdapter;
    private MyListview lv_jinrong;
    private List<CarLifeServiceBean.ContentBean> content = new ArrayList<>();
    private CarLifrRecyclerViewAdapter carLifrRecyclerViewAdapter;

    public static Fragment newInstance() {
        JinRongBaoXianFragment fragment = new JinRongBaoXianFragment();
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.jinrongbaoxian_fragment, null);
        lv_jinrong = (MyListview) view.findViewById(R.id.lv_jinrong);
        baoXianAdapter = new BaoXianAdapter();
        getDataFromNet();
        return view;
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }


    @Override
    public void onClick(View v) {


    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            String area_id = PrefUtils.getString(App.context, "area_id", "0");
            String id = LoginUtils.getId(getActivity());
            showProgressDialog(getActivity(), false);
            if (!TextUtils.isEmpty(area_id) && !TextUtils.isEmpty(id)) {
                OkHttpUtils.post()
                        .url(AppUrl.Czh_Baoxian_List)
                        .addParams("device_id", id)
                        .addParams("area_id", area_id)
                        .build()
                        .execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws IOException {
                                String string = response.body().string();
                                if (!TextUtils.isEmpty(string)) {
                                    CarLifeServiceBean carLifeServiceBean = JsonUtil.parseJsonToBean(string, CarLifeServiceBean.class);
                                    if (carLifeServiceBean != null) {
                                        content= carLifeServiceBean.getContent();
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                    lv_jinrong.setAdapter(baoXianAdapter);
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

        } else {
            Toast.makeText(App.context, StringConstants.CHECK_NET, Toast.LENGTH_SHORT).show();
        }


    }


    public class BaoXianAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            if (content != null && content.size() > 0) {
                return  content.size() ;
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
            convertView = LayoutInflater.from(App.context).inflate(R.layout.carlife_service_item, null);
            TextView tv_service_title = (TextView) convertView.findViewById(R.id.tv_service_title);
            if (content != null) {
                CarLifeServiceBean.ContentBean contentBean = content.get(position);
                if (contentBean != null) {
                    String service_type_name = contentBean.getService_type_name();
                    if (!TextUtils.isEmpty(service_type_name)) {
                        tv_service_title.setText(service_type_name);
                    }
                    List<CarLifeServiceBean.ContentBean.CarLifeServiceTypesBean> carLifeServiceTypes = contentBean.getCarLifeServiceTypes();
                    RecyclerView rv_carlife = (RecyclerView) convertView.findViewById(R.id.rv_carlife);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv_carlife.setLayoutManager(gridLayoutManager);
                    if (carLifeServiceTypes != null) {
                        carLifrRecyclerViewAdapter = new CarLifrRecyclerViewAdapter(carLifeServiceTypes);
                    }
                    rv_carlife.setAdapter(carLifrRecyclerViewAdapter);
                }

            }

            return convertView;
        }

    }


    public class CarLifrRecyclerViewAdapter extends RecyclerView.Adapter<CarLifrRecyclerViewAdapter.ViewHolder> {
        private List<CarLifeServiceBean.ContentBean.CarLifeServiceTypesBean> carLifeServiceTypes;

        public CarLifrRecyclerViewAdapter(List<CarLifeServiceBean.ContentBean.CarLifeServiceTypesBean> carLifeServiceTypes) {
            this.carLifeServiceTypes = carLifeServiceTypes;

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_heng_title;
            ImageView iv_heng_bg;

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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_heng_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.iv_heng_bg = (ImageView) v.findViewById(R.id.iv_heng_bg);
            holder.tv_heng_title = (TextView) v.findViewById(R.id.tv_heng_title);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (carLifeServiceTypes != null) {
                CarLifeServiceBean.ContentBean.CarLifeServiceTypesBean carLifeServiceTypesBean = carLifeServiceTypes.get(position);
                if (carLifeServiceTypesBean != null) {
                    final String service_name = carLifeServiceTypesBean.getService_name();
                    final String service_url = carLifeServiceTypesBean.getService_url();
                    final int service_id = carLifeServiceTypesBean.getService_id();
                    final String web_skip = carLifeServiceTypesBean.getWeb_skip();
                    final String web_url = carLifeServiceTypesBean.getWeb_url();
                    if (!TextUtils.isEmpty(service_name)) {
                        holder.tv_heng_title.setText(service_name);
                    }
                    if (!TextUtils.isEmpty(service_url)) {
                        Picasso.with(App.context).load(service_url).error(R.mipmap.lunbotu).placeholder(R.mipmap.lunbotu).into(holder.iv_heng_bg);
                    }

                    holder.iv_heng_bg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(service_name) && !TextUtils.isEmpty(service_id + "")) {
                                if ("1".equals(web_skip)) {
                                    //工行
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    intent.setData(Uri.parse(web_url));
                                    JinRongBaoXianFragment.this.startActivity(intent);
                                } else if ("4".equals(web_skip)) {
                                    //汽车保险
                                    Intent intent = new Intent(App.context, CarBaoXianNew.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
