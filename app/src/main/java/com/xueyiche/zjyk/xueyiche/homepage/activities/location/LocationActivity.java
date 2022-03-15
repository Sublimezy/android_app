package com.xueyiche.zjyk.xueyiche.homepage.activities.location;

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
import com.xueyiche.zjyk.xueyiche.constants.bean.OpenCityBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.QuickIndexBar;
import com.xueyiche.zjyk.xueyiche.main.activities.main.MainActivity;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhanglei on 2016/9/2.
 */
public class LocationActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private LinearLayout ll_exam_back;
    private ListView listview_all_city;
    private List<OpenCityBean.ContentBean> content;
    private QuickIndexBar mLetterBar;
    private CityAdapter cityAdapter;
    private String substring;
    private RefreshLayout refreshLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_homepager_location;
    }

    @Override
    protected void initView() {
       XueYiCheUtils.getNowLocation(LocationActivity.this);
        String city = PrefUtils.getString(App.context, "city", "");
        if (!TextUtils.isEmpty(city)) {
            if (city.contains("市")) {
                substring = city.substring(0, city.length() - 1);
            }
        }
        tv_title = (TextView) view.findViewById(R.id.papertest_include).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.papertest_include).findViewById(R.id.ll_exam_back);
        listview_all_city = (ListView) view.findViewById(R.id.listview_all_city);
        listview_all_city.setDivider(new ColorDrawable(getResources().getColor(R.color.all_line_color)));
        TextView overlay = (TextView) findViewById(R.id.tv_letter_city);
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
                        String id1 = LoginUtils.getId(LocationActivity.this);
                        getDataFromNet(id1);
                        refreshLayout.finishRefresh();
                    }
                }, 1500);
            }
        });
        listview_all_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenCityBean.ContentBean contentBean = content.get(position);
//                String area_code = contentBean.getArea_code();
                final String area_id = contentBean.getArea_id();
                final String area_name = contentBean.getArea_name();
//                if ("0".equals(area_code)) {
//                    showToastShort("该城市暂未开通");
//                } else if ("1".equals(area_code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
                    builder.setIcon(R.mipmap.logo);
                    builder.setTitle("是否选择"+area_name);
                    //点击空白处弹框不消失
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PrefUtils.putBoolean(App.context, "choice_city", true);
                            PrefUtils.putString(App.context, "area_id", area_id);
                            PrefUtils.putString(App.context, "area_name", area_name);
                            Intent intent = new Intent(App.context, MainActivity.class);
                            startActivity(intent);
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
            boolean choice_city = PrefUtils.getBoolean(App.context, "choice_city", false);
            if (choice_city) {
                Intent intent = new Intent(App.context, MainActivity.class);
                intent.putExtra("position",0);
                startActivity(intent);
                finish();
            } else {
                showToastShort("请选择城市");
            }
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
                OkHttpUtils.post().url(AppUrl.KAITONGCHENGSHI)
                        .addParams("equipment_type", id)
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
        OpenCityBean kaiTongCityBean = JsonUtil.parseJsonToBean(string, OpenCityBean.class);
        content = kaiTongCityBean.getContent();
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
                boolean choice_city = PrefUtils.getBoolean(App.context, "choice_city", false);
                if (choice_city) {
                    Intent intent = new Intent(App.context, MainActivity.class);
                    intent.putExtra("position",0);
                    startActivity(intent);
                    finish();
                } else {
                    showToastShort("请选择城市");
                }
                break;
        }
    }

    public class CityAdapter extends BaseAdapter {
        private HashMap<String, Integer> letterIndexes;
        private final String[] sections;
        public CityAdapter() {
            int size = content.size();
            letterIndexes = new HashMap<>();
            sections = new String[size];
            for (int index = 0; index < size; index++){
//                //当前品牌拼音首字母
                String area_code = content.get(index).getArea_code();
                //上个首字母，如果不存在设为""
                String previousLetter = index >= 1 ? content.get(index - 1).getArea_code() : "";
                if (!TextUtils.equals(area_code, previousLetter)){
                    letterIndexes.put(area_code, index);
                    sections[index] = area_code;
                }
            }
        }


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
            final String area_name = content.get(position).getArea_name();
            String area_id = content.get(position).getArea_id();
            String area_code = content.get(position).getArea_code();
//            String previousLetter = position >= 1 ? content.get(position - 1).getArea_code() : "";
//            if (!TextUtils.equals(area_code, previousLetter)){
//                holder.tv_item_city_listview_letter.setVisibility(View.VISIBLE);
//                if ("1".equals(area_code)) {
//                    holder.tv_item_city_listview_letter.setText("已开通城市");
//                }else if ("0".equals(area_code)){
//                    holder.tv_item_city_listview_letter.setText("即将开通城市");
//                }
//            }else{
//                holder.tv_item_city_listview_letter.setVisibility(View.GONE);
//            }
//            if ("1".equals(area_code)) {
//                holder.tv_item_city.setTextColor(Color.parseColor("#323232"));
//            }else if ("0".equals(area_code)){
//                holder.tv_item_city.setTextColor(Color.parseColor("#999999"));
//            }
            if (!TextUtils.isEmpty(area_name)) {
                holder.tv_item_city.setText(area_name);
            }
            return convertView;
        }
        public class ViewHolder {
            TextView tv_item_city_listview_letter;
            TextView tv_item_city;
        }
    }



}
