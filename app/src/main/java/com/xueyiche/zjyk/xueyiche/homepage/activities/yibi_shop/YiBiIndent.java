package com.xueyiche.zjyk.xueyiche.homepage.activities.yibi_shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.bean.YiBiIndentBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZL on 2017/10/18.
 */
public class YiBiIndent extends BaseActivity {
    private LinearLayout llBack, ll_not_indent;
    private TextView tvTitle;
    private TextView tv_indent;
    private ListView list_view_shop;
    private YiBiIndentAdapter yiBiIndentAdapter;
    private String user_id;
    private List<YiBiIndentBean.ContentBean> content;

    @Override
    protected int initContentView() {
        return R.layout.yibi_indent;
    }

    @Override
    protected void initView() {

        llBack = (LinearLayout) view.findViewById(R.id.yibi_indent_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.yibi_indent_include).findViewById(R.id.tv_title);
        tv_indent = (TextView) view.findViewById(R.id.yibi_indent_include).findViewById(R.id.tv_indent);
        list_view_shop = (ListView) view.findViewById(R.id.list_view_shop);
        ll_not_indent = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        yiBiIndentAdapter = new YiBiIndentAdapter();

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText("订单");
        ll_not_indent.setVisibility(View.GONE);
        tv_indent.setVisibility(View.GONE);
        user_id = PrefUtils.getString(App.context, "user_id", "");
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.YIBISHOPINDENT).addParams("user_id", user_id).build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        YiBiIndentBean yiBiIndentBean = JsonUtil.parseJsonToBean(string, YiBiIndentBean.class);
                        if (yiBiIndentBean != null) {
                            int code = yiBiIndentBean.getCode();
                            final String msg = yiBiIndentBean.getMsg();
                            if (0==code) {
                                content = yiBiIndentBean.getContent();

                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (content.size()==0) {

                                            ll_not_indent.setVisibility(View.VISIBLE);
                                        }else {
                                            ll_not_indent.setVisibility(View.GONE);
                                        }
                                        list_view_shop.setAdapter(yiBiIndentAdapter);
                                    }
                                });
                            }else {
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToastShort(msg);
                                    }
                                });
                            }
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

    public class YiBiIndentAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return content.size();
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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.yibi_indent_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            YiBiIndentBean.ContentBean contentBean = content.get(position);
            if (contentBean != null) {
                String em_order_info_id = contentBean.getEm_order_info_id();
                String em_det_content = contentBean.getEm_det_content();
                String em_order_type = contentBean.getEm_order_type();
                String em_name = contentBean.getEm_name();
                String em_pic_url = contentBean.getEm_pic_url();
                if (!TextUtils.isEmpty(em_order_info_id)) {
                    viewHolder.tv_indent_number.setText("订单号：" + em_order_info_id);
                }else {
                    viewHolder.tv_indent_number.setText("订单号：" + "");
                }
                if (!TextUtils.isEmpty(em_name)) {
                    viewHolder.tv_indent_name.setText(em_name);
                }else {
                    viewHolder.tv_indent_name.setText("");
                }
                if (!TextUtils.isEmpty(em_det_content)) {
                    viewHolder.tv_indent_color.setText(em_det_content);
                }else {
                    viewHolder.tv_indent_color.setText("");
                }
                if (!TextUtils.isEmpty(em_pic_url)) {
                    Picasso.with(App.context).load(em_pic_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_indent_head);
                }
                if (!TextUtils.isEmpty(em_order_type)) {
                    if (em_order_type.equals("0")) {
                        viewHolder.tv_indent_zhuangtai.setText("待发货");
                        viewHolder.bt_look_wuliu.setVisibility(View.GONE);
                    }else if (em_order_type.equals("1")){
                        viewHolder.tv_indent_zhuangtai.setText("已发货");
                        viewHolder.bt_look_wuliu.setVisibility(View.VISIBLE);
                    }else if (em_order_type.equals("2")){
                        viewHolder.tv_indent_zhuangtai.setText("待签收");
                        viewHolder.bt_look_wuliu.setVisibility(View.VISIBLE);
                    }else if (em_order_type.equals("3")){
                        viewHolder.tv_indent_zhuangtai.setText("已签收");
                        viewHolder.bt_look_wuliu.setVisibility(View.VISIBLE);
                    }
                }
                viewHolder.bt_look_wuliu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(App.context, IndentWuLiu.class);
                        App.context.startActivity(intent);
                    }
                });
                viewHolder.rl_look.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(App.context, IndentWuLiu.class);
                        App.context.startActivity(intent);
                    }
                });
            }

            return convertView;
        }

        class ViewHolder {
            private TextView tv_indent_number, tv_indent_name, tv_indent_color, tv_indent_zhuangtai;
            private ImageView iv_indent_head;
            private Button bt_look_wuliu;
            private RelativeLayout rl_look;
            public ViewHolder(View view) {
                tv_indent_number = (TextView) view.findViewById(R.id.tv_indent_number);
                tv_indent_name = (TextView) view.findViewById(R.id.tv_indent_name);
                tv_indent_color = (TextView) view.findViewById(R.id.tv_indent_color);
                tv_indent_zhuangtai = (TextView) view.findViewById(R.id.tv_indent_zhuangtai);
                iv_indent_head = (ImageView) view.findViewById(R.id.iv_indent_head);
                bt_look_wuliu = (Button) view.findViewById(R.id.bt_look_wuliu);
                rl_look = (RelativeLayout) view.findViewById(R.id.rl_look);

            }
        }
    }

}
