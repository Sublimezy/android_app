package com.xueyiche.zjyk.xueyiche.homepage.activities.yibi_shop;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.bean.YiBiBean;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview.ShareCardView;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/10/18.
 */
public class YiBiShop extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tv_login_back;
    private TextView tv_wenxintishi;
    private MyGridView recycler_view;
    private YiBiShopAdapter homeYouHuiAdapter;
    private List<YiBiBean.ContentBean.GoodslistBean> content1;
    private List<YiBiBean.ContentBean.ImagelistBean> content2;
    private ShareCardView shareCardview;

    @Override
    protected int initContentView() {
        return R.layout.yibi_shop;
    }

    @Override
    protected void initView() {

        llBack = (LinearLayout) view.findViewById(R.id.yibi_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.yibi_include).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.yibi_include).findViewById(R.id.tv_wenxintishi);
        recycler_view = (MyGridView) view.findViewById(R.id.gv_yibi_content);
        shareCardview = (ShareCardView) view.findViewById(R.id.shareCardview);
        tv_login_back.setText("易币商城");
        tv_wenxintishi.setTextColor(getResources().getColor(R.color.test_color));
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setText("订单");
        tv_wenxintishi.setTextColor(getResources().getColor(R.color.test_color));
        llBack.setOnClickListener(this);
        tv_wenxintishi.setOnClickListener(this);
        getDataFromNet();
    }

    @Override
    protected void initListener() {
//        fb_yibi_shop.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (content2!=null) {
//                    MineNumberInformationBean.ContentBean.ImagelistBean imagelistBean = content2.get(position);
//                    if (imagelistBean!=null) {
//                        String em_id = imagelistBean.getEm_id();
//                        String em_goods_money = imagelistBean.getEm_goods_money();
//                        String em_pic_url = imagelistBean.getEm_pic_url();
//                        String em_name = imagelistBean.getEm_name();
//                        Intent intent = new Intent(App.context, YiBiShopContent.class);
//                        intent.putExtra("em_id",em_id);
//                        intent.putExtra("em_name",em_name);
//                        intent.putExtra("em_pic_url",em_pic_url);
//                        intent.putExtra("em_goods_money",em_goods_money);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });

    }

    @Override
    public void onPause() {
        super.onPause();
//        fb_yibi_shop.stopAutoPlay();

    }


    @Override
    protected void initData() {

        homeYouHuiAdapter = new YiBiShopAdapter();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_wenxintishi:
                //判定有没有登录和过期
                if (DialogUtils.IsLogin()) {
                    openActivity(YiBiIndent.class);
                } else {
                    openActivity(LoginFirstStepActivity.class);
                }
                break;
        }
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.YIBISHOP)
                    .addParams("equipment_type", LoginUtils.getId(YiBiShop.this))
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final List<String> imageurls = new ArrayList<String>();
                                YiBiBean viewPager_youZheng = JsonUtil.parseJsonToBean(string, YiBiBean.class);
                                if (viewPager_youZheng != null) {
                                    int code = viewPager_youZheng.getCode();
                                    if (0==code) {
                                        content1 = viewPager_youZheng.getContent().getGoodslist();
                                        content2 = viewPager_youZheng.getContent().getImagelist();
                                        for (YiBiBean.ContentBean.ImagelistBean contentBean : content2) {
                                            String imgUrl = contentBean.getEm_pic_url();
                                            imageurls.add(imgUrl);
                                        }
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
//                                                fb_yibi_shop.setImagesUrl(imageurls);
                                                recycler_view.setAdapter(homeYouHuiAdapter);
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

    public class YiBiShopAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            if (content1 != null) {
                return content1.size();
            } else {
                return 0;
            }


        }

        @Override
        public Object getItem(int position) {
            return content1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.yibishop_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            YiBiBean.ContentBean.GoodslistBean goodslistBean = content1.get(position);
            if (content1 != null) {
                final String em_pic_url = goodslistBean.getEm_pic_url();
                final String em_goods_money = goodslistBean.getEm_goods_money();
                final String em_name = goodslistBean.getEm_name();
                final String em_id = goodslistBean.getEm_id();

                if (!TextUtils.isEmpty(em_pic_url)) {
                    Picasso.with(App.context).load(em_pic_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_good_head);
                }
                if (!TextUtils.isEmpty(em_goods_money)) {
                    viewHolder.tv_yibi_money.setText(em_goods_money + "易币");
                }
                if (!TextUtils.isEmpty(em_name)) {
                    viewHolder.tv_good_name.setText(em_name);
                }
                viewHolder.ll_all_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(App.context, YiBiShopContent.class);
                        intent.putExtra("em_id",em_id);
                        intent.putExtra("em_name",em_name);
                        intent.putExtra("em_pic_url",em_pic_url);
                        intent.putExtra("em_goods_money",em_goods_money);
                        startActivity(intent);
                    }
                });
            }

            return convertView;
        }

        class ViewHolder {
            //点击的地方
            public LinearLayout ll_all_content;
            //头像的图片的位置
            public ImageView iv_good_head;
            //商品名字
            public TextView tv_good_name;
            //价格
            public TextView tv_yibi_money;

            //
            public ViewHolder(View v) {
                ll_all_content = (LinearLayout) v.findViewById(R.id.ll_all_content);
                iv_good_head = (ImageView) v.findViewById(R.id.iv_good_head);
                tv_good_name = (TextView) v.findViewById(R.id.tv_good_name);
                tv_yibi_money = (TextView) v.findViewById(R.id.tv_yibi_money);
            }
        }
    }
}
