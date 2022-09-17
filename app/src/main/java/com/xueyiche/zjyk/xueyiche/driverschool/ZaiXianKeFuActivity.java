package com.xueyiche.zjyk.xueyiche.driverschool;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.MessageComeBack;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.ZaixianKefuListBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2018/3/20.
 */
public class ZaiXianKeFuActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_exam_back;
    private TextView tv_login_back;
    private ListView lv_kefu_zaixian;
    private EditText ed_zaixian_user;
    private TextView tv_send;
    private String user_id;

    @Override
    protected int initContentView() {
        return R.layout.kefu_zaixian_activity;
    }

    @Override
    protected void initView() {
        ll_exam_back = (LinearLayout) view.findViewById(R.id.message_include).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.message_include).findViewById(R.id.tv_login_back);
        lv_kefu_zaixian = (ListView) view.findViewById(R.id.lv_kefu_zaixian);
        tv_send = (TextView) view.findViewById(R.id.tv_send);
        ed_zaixian_user = (EditText) view.findViewById(R.id.ed_zaixian_user);
        textChange tc1 = new textChange();
        ed_zaixian_user.addTextChangedListener(tc1);
        tv_login_back.setText("在线客服");

        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        if (!TextUtils.isEmpty(user_id)) {
            getDataFromNet();
        }
    }

    public void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post()
                    .url(AppUrl.Zaixian_Kefu_List)
                    .addParams("user_id", user_id)
                    .addParams("device_id", LoginUtils.getId(this))
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    final String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ZaixianKefuListBean zaixianKefuListBean = JsonUtil.parseJsonToBean(string, ZaixianKefuListBean.class);
                                if (zaixianKefuListBean != null) {
                                    List<ZaixianKefuListBean.ContentBean> content = zaixianKefuListBean.getContent();
                                    if (content != null) {
                                        lv_kefu_zaixian.setAdapter(new ZaiXianAdapter(content));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_send:
                //提交信息
                String string = ed_zaixian_user.getText().toString();
                if (!TextUtils.isEmpty(string)) {
                    sendData(string);
                } else {
                    showToastShort("请填写聊天内容");
                }
                break;
        }
    }

    public void sendData(String string) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            OkHttpUtils.post()
                    .url(AppUrl.Zaixian_User_Push)
                    .addParams("user_id", user_id)
                    .addParams("receive_user_id", "81c7a79a5e3e11e8ba545254036244cf")
                    .addParams("msg_text", string)
                    .addParams("device_id", LoginUtils.getId(this))
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
                                    String msg = messageComeBack.getMsg();
                                    if (200 == code) {
                                        ed_zaixian_user.setText("");
                                        showToastShort(msg);
                                        getDataFromNet();
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            }
            String string = ed_zaixian_user.getText().toString();
            if (!TextUtils.isEmpty(string)) {
                sendData(string);
            } else {
                showToastShort("请填写聊天内容");
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private class ZaiXianAdapter extends BaseAdapter {
        private static final int TYPE_COUNT = 2;//item类型的总数
        private static final int TYPE_kefu = 0;//客服的item
        private static final int TYPE_user = 1;// 用户的item
        private int currentType;
        private List<ZaixianKefuListBean.ContentBean> content;

        public ZaiXianAdapter(List<ZaixianKefuListBean.ContentBean> content) {
            this.content = content;
        }

        @Override
        public int getItemViewType(int position) {
            ZaixianKefuListBean.ContentBean contentBean = content.get(position);
            String nickname = contentBean.getNickname();
            if ("学易车".equals(nickname)) {
                return TYPE_kefu;
            } else {
                return TYPE_user;
            }

        }

        @Override
        public int getViewTypeCount() {
            return TYPE_COUNT;
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            currentType = getItemViewType(i);
            ZaixianKefuListBean.ContentBean contentBean = content.get(i);
            if (contentBean != null) {
                String msg_text = contentBean.getMsg_text();
                String push_time = contentBean.getPush_time();
                String head_img = contentBean.getHead_img();
                if (currentType == TYPE_kefu) {
                    ViewHolderVideo viewHolder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(App.context).inflate(R.layout.kefu_zaixian_list_left_item, null);
                        viewHolder = new ViewHolderVideo(convertView);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolderVideo) convertView.getTag();
                    }
                    viewHolder.tv_kefu_zaixian.setText(msg_text);
                    viewHolder.tv_kefu_time.setText(push_time);
                    if (!TextUtils.isEmpty(head_img)) {
                        Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(viewHolder.ci_kefu_zaixian_head);
                    }
                } else if (currentType == TYPE_user) {
                    ViewHolderVideo viewHolder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(App.context).inflate(R.layout.kefu_zaixian_list_right_item, null);
                        viewHolder = new ViewHolderVideo(convertView);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolderVideo) convertView.getTag();
                    }
                        if (!TextUtils.isEmpty(head_img)) {
                            Picasso.with(App.context).load(head_img).placeholder(R.mipmap.mine_head).error(R.mipmap.mine_head).into(viewHolder.ci_user_zaixian_head);
                        }
                        viewHolder.tv_user_zaixian.setText(msg_text);
                        viewHolder.tv_user_time.setText(push_time);
                }
            }


            return convertView;
        }

        class ViewHolderVideo {
            private CircleImageView ci_kefu_zaixian_head;
            private CircleImageView ci_user_zaixian_head;
            private TextView tv_kefu_zaixian;
            private TextView tv_user_zaixian;
            private TextView tv_kefu_time;
            private TextView tv_user_time;
            private LinearLayout ll_zaixian_kefu;
            private RelativeLayout rl_zaixian_user;

            public ViewHolderVideo(View view) {
                ci_kefu_zaixian_head = (CircleImageView) view.findViewById(R.id.ci_kefu_zaixian_head);
                ci_user_zaixian_head = (CircleImageView) view.findViewById(R.id.ci_user_zaixian_head);
                tv_kefu_time = (TextView) view.findViewById(R.id.tv_kefu_time);
                tv_user_time = (TextView) view.findViewById(R.id.tv_user_time);
                tv_kefu_zaixian = (TextView) view.findViewById(R.id.tv_kefu_zaixian);
                tv_user_zaixian = (TextView) view.findViewById(R.id.tv_user_zaixian);
                ll_zaixian_kefu = (LinearLayout) view.findViewById(R.id.ll_zaixian_kefu);
                rl_zaixian_user = (RelativeLayout) view.findViewById(R.id.rl_zaixian_user);
            }
        }

    }


    class textChange implements TextWatcher {
        @Override

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (TextUtils.isEmpty(text)) {
                tv_send.setTextColor(Color.parseColor("#4f4f4f"));
                tv_send.setClickable(false);
            } else {
                tv_send.setTextColor(Color.parseColor("#ff5000"));
                tv_send.setClickable(true);
            }

        }

        @Override

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {

        }
    }
}
