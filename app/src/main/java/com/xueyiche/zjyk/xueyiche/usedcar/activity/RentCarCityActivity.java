package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.QuickIndexBar;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarOpenCityBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2018/7/19.
 */
public class RentCarCityActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;
    private LinearLayout ll_exam_back;
    private ListView listview_all_city;
    private List<String> content;
    private QuickIndexBar mLetterBar;
    private CityAdapter cityAdapter;
    private RefreshLayout refreshLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_homepager_location;
    }

    @Override
    protected void initView() {
        XueYiCheUtils.getNowLocation(RentCarCityActivity.this);

        tv_title = (TextView) view.findViewById(R.id.papertest_include).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.papertest_include).findViewById(R.id.ll_exam_back);
        listview_all_city = (ListView) view.findViewById(R.id.listview_all_city);
        listview_all_city.setDivider(new ColorDrawable(getResources().getColor(R.color.all_line_color)));
        listview_all_city.setDividerHeight(1);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        ll_exam_back.setOnClickListener(this);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String id1 = LoginUtils.getId(RentCarCityActivity.this);
                        getDataFromNet(id1);
                        refreshLayout.finishRefresh();
                    }
                }, 1500);
            }
        });
        listview_all_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String s = content.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(RentCarCityActivity.this);
                builder.setIcon(R.mipmap.logo);
                builder.setTitle("是否选择" + s);
                //点击空白处弹框不消失
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("city", s);
                        String jiesong_type = getIntent().getStringExtra("jiesong_type");
                        if ("1".equals(jiesong_type)) {
                            setResult(444, intent);
                        }else if ("2".equals(jiesong_type)){
                            setResult(555, intent);
                        }
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
//                }
            }
        });
    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("city", "哈尔滨");
            String jiesong_type = getIntent().getStringExtra("jiesong_type");
            if ("1".equals(jiesong_type)) {
                setResult(444, intent);
            }else if ("2".equals(jiesong_type)){
                setResult(555, intent);
            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String id1 = LoginUtils.getId(this);
        getDataFromNet(id1);
    }

    @Override
    protected void initData() {
        tv_title.setText("选择城市");

    }

    public void getDataFromNet(String id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.Open_City)
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
        UsedCarOpenCityBean usedCarOpenCityBean = JsonUtil.parseJsonToBean(string, UsedCarOpenCityBean.class);
        content = usedCarOpenCityBean.getData();
        if (content.size() != 0) {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    cityAdapter = new CityAdapter();
                    listview_all_city.setAdapter(cityAdapter);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                Intent intent = new Intent();
                intent.putExtra("city", "哈尔滨");
                String jiesong_type = getIntent().getStringExtra("jiesong_type");
                if ("1".equals(jiesong_type)) {
                    setResult(444, intent);
                }else if ("2".equals(jiesong_type)){
                    setResult(555, intent);
                }
                finish();
                break;
        }
    }

    public class CityAdapter extends BaseAdapter {
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
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(App.context, R.layout.city_listview_yiji_item, null);
                holder = new ViewHolder();
                holder.tv_item_city = (TextView) convertView.findViewById(R.id.tv_item_city);
                holder.tv_item_city_listview_letter = (TextView) convertView.findViewById(R.id.tv_item_city_listview_letter);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String s = content.get(position);
            if (!TextUtils.isEmpty(s)) {
                holder.tv_item_city.setText(s);
            }
            return convertView;
        }

        public class ViewHolder {
            TextView tv_item_city_listview_letter;
            TextView tv_item_city;
        }
    }

}
