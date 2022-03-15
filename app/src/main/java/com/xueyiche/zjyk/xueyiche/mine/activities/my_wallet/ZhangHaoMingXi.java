package com.xueyiche.zjyk.xueyiche.mine.activities.my_wallet;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.bean.ZhangHaoMingXiBean;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xw.repo.refresh.PullToRefreshLayout;

import java.util.List;

/**
 * Created by zhanglei on 2016/11/15.
 */
public class ZhangHaoMingXi extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle;
    private ListView pullListView;
    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected int initContentView() {
        return R.layout.activtiy_exam_zhuangxiang;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.zhanghu_mingxi_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.zhanghu_mingxi_include).findViewById(R.id.tv_login_back);
        pullListView = (ListView) view.findViewById(R.id.lv_zhuangxiang);
        llBack.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }
    @Override
    protected void initData() {
        tvTitle.setText("账户明细");
        Toast.makeText(ZhangHaoMingXi.this, "无结果", Toast.LENGTH_SHORT).show();
        String user_phone = PrefUtils.getString(App.context, "user_phone", "");
//        if (XueYiCheUtils.IsHaveInternet(this)&&!TextUtils.isEmpty(user_phone)) {
//            showProgressDialog(false);
//            OkHttpUtils.post().url(AppUrl.ZHMX)
//                    .addParams("user_phone", user_phone)
//                    .addParams("user_driver", "1")
//                    .build().execute(new Callback() {
//                @Override
//                public Object parseNetworkResponse(Response response) throws IOException {
//
//                    String string = response.body().string();
//                    if (!TextUtils.isEmpty(string)) {
//                        ZhangHaoMingXiBean zhangHaoMingXiBean = JsonUtil.parseJsonToBean(string, ZhangHaoMingXiBean.class);
//                        final List<ZhangHaoMingXiBean.ContentBean> content = zhangHaoMingXiBean.getContent();
//                        App.handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                pullListView.setAdapter(new JiFenMingXiAdapter(content));
//                            }
//                        });
//                    }
//                    return string;
//                }
//
//                @Override
//                public void onError(Request request, Exception e) {
//                    stopProgressDialog();
//                }
//                @Override
//                public void onResponse(Object response) {
//                    stopProgressDialog();
//                }
//            });
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_exam_back:
                finish();
                break;

        }
    }
    public class JiFenMingXiAdapter extends BaseAdapter {
        private List<ZhangHaoMingXiBean.ContentBean> content;
        public JiFenMingXiAdapter(List<ZhangHaoMingXiBean.ContentBean> content) {
            this.content = content;
        }

        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView==null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.mine_jifen_list_item,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ZhangHaoMingXiBean.ContentBean contentBean = content.get(position);
            String system_time = contentBean.getSystem_time();
            String money_in = contentBean.getMoney_in();
            String money_type = contentBean.getMoney_type();
            if (!TextUtils.isEmpty(system_time)) {
                viewHolder.tv_jifen_time.setText(system_time);
            }
//            if (!TextUtils.isEmpty(integral_type)) {
//                viewHolder.tv_mine_jifen_name.setText(integral_type);
//            }
//            if (!TextUtils.isEmpty(detail_num)) {
//                viewHolder.tv_jifen_number.setText(detail_num);
//            }

            return convertView;
        }


        class ViewHolder {
            private TextView tv_jifen_time, tv_jifen_number, tv_mine_jifen_name;

            public ViewHolder(View view) {
                tv_jifen_time = (TextView) view.findViewById(R.id.tv_jifen_time);
                tv_jifen_number = (TextView) view.findViewById(R.id.tv_jifen_number);
                tv_mine_jifen_name = (TextView) view.findViewById(R.id.tv_mine_jifen_name);

            }
        }
    }
}
