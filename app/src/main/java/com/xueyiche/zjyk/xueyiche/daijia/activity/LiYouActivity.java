package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.homepage.view.AdListView;
import com.xueyiche.zjyk.xueyiche.practicecar.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2019/9/23.
 */
public class LiYouActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ll_exam_back;
    private TextView tv_login_back;
    private Button bt_ok;
    private AdListView lv_practice_content;
    private List<String> list = new ArrayList<>();
    private PracticeContentAdapter practiceContentAdapter;
    private ArrayList<String> liyou = new ArrayList<>();
    private String type_activity;

    @Override
    protected int initContentView() {
        return R.layout.liyou_activity;
    }

    @Override
    protected void initView() {
        tv_login_back = (TextView) view.findViewById(R.id.practice_content_include).findViewById(R.id.tv_title);
        ll_exam_back =  view.findViewById(R.id.practice_content_include).findViewById(R.id.iv_login_back);
        lv_practice_content = (AdListView) view.findViewById(R.id.lv_practice_content);
        bt_ok = (Button) view.findViewById(R.id.bt_ok);
        practiceContentAdapter = new PracticeContentAdapter();
        lv_practice_content.setAdapter(practiceContentAdapter);
    }

    @Override
    protected void initListener() {
        ll_exam_back.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        tv_login_back.setText("取消原因");
        liyou.add("司机到位太慢，等不及了");
        liyou.add("代驾收费太贵");
        liyou.add("司机要求取消订单");
        liyou.add("找到其他代驾");
        liyou.add("暂时不需要代驾了");
        lv_practice_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SparseBooleanArray checkedItemPositions = lv_practice_content.getCheckedItemPositions();
                boolean isChecked = checkedItemPositions.get(i);
                String s = liyou.get(i);
                if (isChecked) {
                    if (!list.contains(s)) {
                        list.add(s);
                    }
                } else {
                    if (list.contains(s)) {
                        list.remove(s);
                    }
                }
                practiceContentAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.bt_ok:
                if (list.size() > 0) {
                    quxiaoIndent();
                } else {
                    Toast.makeText(App.context, "请选择取消理由", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void quxiaoIndent() {
        String user_id = PrefUtils.getString(this, "user_id", "");
        String order_number = getIntent().getStringExtra("order_number");
        type_activity = getIntent().getStringExtra("type");
        String cancle_reason = "";
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (i == 0) {
                cancle_reason = s;
            } else {
                cancle_reason = cancle_reason + "," + s;
            }
        }
        OkHttpUtils.post().url(AppUrl.Quxiao_Indent)
                .addParams("order_number", order_number)
                .addParams("device_id", LoginUtils.getId(this))
                .addParams("user_id", user_id)
                .addParams("cancle_reason", cancle_reason)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                    if (successDisCoverBackBean != null) {
                        final int code = successDisCoverBackBean.getCode();
                        final String msg = successDisCoverBackBean.getMsg();
                        App.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showToastShort(msg);
                                if (200 == code) {
                                    if ("WaitYuYue".equals(type_activity)) {
                                        WaitYuYueActivity.instance.finish();
                                    } else if ("JieDan".equals(type_activity)) {
//                                        JieDanActivity.instance.finish();
                                    } else if ("DingDan".equals(type_activity)) {
                                        EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                                        EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                                    } else if ("DingDan".equals(type_activity)) {
                                        EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                                        EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                                    }
                                }
                                finish();
                            }
                        });

                    }
                }
                return string;
            }
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });

    }

    private class PracticeContentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return liyou.size();
        }

        @Override
        public Object getItem(int i) {
            return liyou.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            PracticeContentViewHolder practiceContentViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.practice_content_item, null);
                practiceContentViewHolder = new PracticeContentViewHolder(view);
                view.setTag(practiceContentViewHolder);
            } else {
                practiceContentViewHolder = (PracticeContentViewHolder) view.getTag();
            }
            String purpose_item_name = liyou.get(i);
            SparseBooleanArray checkedItemPositions = lv_practice_content.getCheckedItemPositions();
            boolean isChecked = checkedItemPositions.get(i);
            if (isChecked) {
                practiceContentViewHolder.tv_practice_content_item.setBackgroundDrawable(getResources().getDrawable(R.drawable.daijia_liyou_bg_shape));
                practiceContentViewHolder.tv_practice_content_item.setTextColor(getResources().getColor(R.color.colorwhite));
            } else {
                practiceContentViewHolder.tv_practice_content_item.setBackgroundDrawable(getResources().getDrawable(R.drawable.quxiao_bg));
                practiceContentViewHolder.tv_practice_content_item.setTextColor(getResources().getColor(R.color._3333));
            }
            if (!TextUtils.isEmpty(purpose_item_name)) {
                practiceContentViewHolder.tv_practice_content_item.setText(purpose_item_name);
            }

            return view;
        }

        class PracticeContentViewHolder {
            private TextView tv_practice_content_item;

            public PracticeContentViewHolder(View view) {
                tv_practice_content_item = (TextView) view.findViewById(R.id.tv_practice_content_item);
            }
        }
    }
}
