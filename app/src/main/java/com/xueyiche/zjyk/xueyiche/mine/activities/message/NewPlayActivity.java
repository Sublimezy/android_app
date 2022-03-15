package com.xueyiche.zjyk.xueyiche.mine.activities.message;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.xueyiche.zjyk.xueyiche.constants.bean.MessageComeBack;
import com.xueyiche.zjyk.xueyiche.constants.UrlActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.MessageListBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZL on 2018/3/15.
 */
public class NewPlayActivity extends BaseActivity {
    private ListView lv_new_activity;
    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private String user_id;
    private LinearLayout ll_not_indent;

    @Override
    protected int initContentView() {
        return R.layout.new_play_activity;
    }

    @Override
    protected void initView() {
        lv_new_activity = (ListView) view.findViewById(R.id.lv_new_activity);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.new_activity_include).findViewById(R.id.ll_exam_back);
        ll_not_indent  = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        tv_login_back = (TextView) view.findViewById(R.id.new_activity_include).findViewById(R.id.tv_login_back);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        tv_login_back.setText("我的消息");
        user_id = PrefUtils.getString(App.context, "user_id", "");
        if (!TextUtils.isEmpty(user_id)) {
            getDataFromNet();
        }
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(true);
            OkHttpUtils.post()
                    .url(AppUrl.Message_List)
                    .addParams("receive_user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MessageListBean messageListBean = JsonUtil.parseJsonToBean(string, MessageListBean.class);
                                if (messageListBean != null) {
                                    List<MessageListBean.ContentBean> content = messageListBean.getContent();
                                    if (content != null) {
                                        if (content.size()>0) {
                                            ll_not_indent.setVisibility(View.GONE);
                                        }else {
                                            ll_not_indent.setVisibility(View.VISIBLE);
                                        }
                                        lv_new_activity.setAdapter(new NewActivityAdapter(content));
                                    }
                                }
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

    private class NewActivityAdapter extends BaseAdapter {
        private List<MessageListBean.ContentBean> content;
        public NewActivityAdapter(List<MessageListBean.ContentBean> content) {
            this.content = content;
        }

        @Override
        public int getCount() {
            return content.size();
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
            NewActivityViewHolder newActivityViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.new_activity_list_item, null);
                newActivityViewHolder = new NewActivityViewHolder(view);
                view.setTag(newActivityViewHolder);
            } else {
                newActivityViewHolder = (NewActivityViewHolder) view.getTag();
            }
            MessageListBean.ContentBean contentBean = content.get(i);
            if (contentBean!=null) {
                String img_url = contentBean.getImg_url();
                String receive_time = contentBean.getReceive_time();
                String title = contentBean.getTitle();
                final String web_url = contentBean.getWeb_url();
                String read_status = contentBean.getRead_status();
                final int receive_id = contentBean.getReceive_id();
                if ("0".equals(read_status)) {
                    newActivityViewHolder.tv_new_activity_name.setTextColor(Color.parseColor("#4f4f4f"));
                    newActivityViewHolder.tv_new_activity_look.setTextColor(Color.parseColor("#ffb10c"));
                }else {
                    newActivityViewHolder.tv_new_activity_name.setTextColor(Color.parseColor("#cccccc"));
                    newActivityViewHolder.tv_new_activity_look.setTextColor(Color.parseColor("#cccccc"));
                }
                newActivityViewHolder.tv_new_activity_time.setText(receive_time);
                newActivityViewHolder.tv_new_activity_name.setText(title);
                Picasso.with(App.context).load(img_url).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(newActivityViewHolder.iv_new_activity_picture);
                newActivityViewHolder.ll_message_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goMessageContent(web_url,receive_id+"");

                    }


                });
            }

            return view;
        }
    }

    @Override
    protected void onResume() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        if (!TextUtils.isEmpty(user_id)) {
            getDataFromNet();
        }
        super.onResume();
    }

    private void goMessageContent(final String web_url, String receive_id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post()
                    .url(AppUrl.Message_Content_Read)
                    .addParams("receive_user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .addParams("receive_id", receive_id)
                    .addParams("type", "0")
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MessageComeBack messageComeBack = JsonUtil.parseJsonToBean(string, MessageComeBack.class);
                                if (messageComeBack != null) {
                                    int code = messageComeBack.getCode();
                                    if (200==code) {
                                        Intent intent = new Intent(App.context, UrlActivity.class);
                                        intent.putExtra("url",web_url);
                                        intent.putExtra("type","2");
                                        startActivity(intent);
                                    }
                                }
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

    class NewActivityViewHolder {
        private TextView tv_new_activity_time;
        private TextView tv_new_activity_name;
        private ImageView iv_new_activity_picture;
        private TextView tv_new_activity_look;
        private LinearLayout ll_message_content;

        public NewActivityViewHolder(View view) {
            ll_message_content = (LinearLayout) view.findViewById(R.id.ll_message_content);
            tv_new_activity_time = (TextView) view.findViewById(R.id.tv_new_activity_time);
            tv_new_activity_look = (TextView) view.findViewById(R.id.tv_new_activity_look);
            tv_new_activity_name = (TextView) view.findViewById(R.id.tv_new_activity_name);
            iv_new_activity_picture = (ImageView) view.findViewById(R.id.iv_new_activity_picture);

        }
    }
}
