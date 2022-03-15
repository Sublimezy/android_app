package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.homepage.view.MyGridView;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.BrandHotBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZL on 2018/7/2.
 */
public class UsedCarSearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText tv_search_title;
    private TextView iv_title_search;
    private List<String> usedcar_huancun = new ArrayList<>();
    private boolean isSearch = false;
    private MyGridView gv_search_content;
    private MyGridView gv_history_search_content;
    private ImageView iv_clean_history;
    private HistoryAdapter historyAdapter;
    private String type;
    private List<BrandHotBean.DataBean> data;

    @Override
    protected int initContentView() {
        return R.layout.used_car_search_activity;
    }

    @Override
    protected void initView() {
        tv_search_title = (EditText) view.findViewById(R.id.tv_search_title);
        iv_title_search = (TextView) view.findViewById(R.id.iv_title_search);
        iv_clean_history = (ImageView) view.findViewById(R.id.iv_clean_history);
        gv_search_content = (MyGridView) view.findViewById(R.id.gv_search_content);
        gv_history_search_content = (MyGridView) view.findViewById(R.id.gv_history_search_content);
    }

    @Override
    protected void initListener() {
        iv_title_search.setOnClickListener(this);
        iv_clean_history.setOnClickListener(this);
//EditText内容的监听
        tv_search_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = tv_search_title.getText().length();
                if (length > 0) {
                    iv_title_search.setText("搜索");
                    isSearch = true;
                } else {
                    iv_title_search.setText("取消");
                    isSearch = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        historyAdapter = new HistoryAdapter();
        usedcar_huancun = PrefUtils.getStrListValue(App.context, "usedcar_list_huancun");

        gv_history_search_content.setAdapter(historyAdapter);
        //item点击事件
        itemOnclick();

        getDataFromNet();

    }

    private void getDataFromNet() {
        OkHttpUtils.post().url(AppUrl.Used_Car_BrandHot).addParams("device_id", LoginUtils.getId(this))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            BrandHotBean brandHotBean = JsonUtil.parseJsonToBean(string, BrandHotBean.class);
                            if (brandHotBean!=null) {
                                int code = brandHotBean.getCode();
                                if (200==code) {
                                    data = brandHotBean.getData();
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            gv_search_content.setAdapter(new GridSearchContentAdapter(data));
                                        }
                                    });

                                }
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

    private void itemOnclick() {
        gv_history_search_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = usedcar_huancun.get(i);
                tv_search_title.setText(s);
                searchVoid();
//                Toast.makeText(UsedCarSearchActivity.this, i + "", Toast.LENGTH_SHORT).show();
            }
        });
        gv_search_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String brand_name = data.get(i).getBrand_name();
                tv_search_title.setText(brand_name);
                searchVoid();
//                Toast.makeText(UsedCarSearchActivity.this, i + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<String> getNewList(List<String> list) {
        List<String> myList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (!myList.contains(list.get(i))) {
                myList.add(list.get(i));

            }
        }

        return myList;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_search:
                searchVoid();
                break;
            case R.id.iv_clean_history:
                if (usedcar_huancun.size() > 0) {
                    showDialog();
                } else {
                    Toast.makeText(UsedCarSearchActivity.this, "无历史记录可清理", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void searchVoid() {
        String trim = tv_search_title.getText().toString().trim();
        if ("1".equals(type)) {
            if (isSearch) {
                Intent intent = new Intent(this,BuyCarActivity.class);
                intent.putExtra("carSourceSearch", trim);
                intent.putExtra("rbhot_type", "");
                intent.putExtra("rbhot_id", "");
                intent.putExtra("level_id", "");
                usedcar_huancun.add(trim);
                Collections.reverse(usedcar_huancun);
                List<String> distinctList = getNewList(usedcar_huancun);
                PrefUtils.putStrListValue(App.context, "usedcar_list_huancun", distinctList);
                historyAdapter.notifyDataSetChanged();
                startActivity(intent);
            }else {
                finish();
            }
        }else {
            Intent intent = new Intent();
            if (isSearch) {
                usedcar_huancun.add(trim);
                Collections.reverse(usedcar_huancun);
                List<String> distinctList = getNewList(usedcar_huancun);
                PrefUtils.putStrListValue(App.context, "usedcar_list_huancun", distinctList);
                historyAdapter.notifyDataSetChanged();
                intent.putExtra("carSourceSearch", trim);
            } else {
                intent.putExtra("carSourceSearch", "");
            }
            setResult(111, intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent();
                intent.putExtra("carSourceSearch", "");
                setResult(111, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog() {
        View viewDia = LayoutInflater.from(App.context).inflate(R.layout.usedcar_tanchuang_layout, null);
        TextView bt_know = (TextView) viewDia.findViewById(R.id.tv_ok);
        TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
        TextView tv_no = (TextView) viewDia.findViewById(R.id.tv_no);
        bt_know.setTextColor(getResources().getColor(R.color._3232));
        tv_content.setText("是否清除搜索历史？");
        final AlertDialog.Builder builder = new AlertDialog.Builder(UsedCarSearchActivity.this, R.style.Dialog).setView(viewDia);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth * 2 / 3;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        //点击空白处弹框不消失
        bt_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.removeStrList(App.context, "usedcar_list_huancun");
                usedcar_huancun.clear();
                gv_history_search_content.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
                dialog01.dismiss();
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private class GridSearchContentAdapter extends BaseAdapter {
      private   List<BrandHotBean.DataBean> data;

        public GridSearchContentAdapter(List<BrandHotBean.DataBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
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
            SearchViewHolder gridViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.gv_search_item, null);
                gridViewHolder = new SearchViewHolder(view);
                view.setTag(gridViewHolder);
            } else {
                gridViewHolder = (SearchViewHolder) view.getTag();
            }
            String brand_name = data.get(i).getBrand_name();
            gridViewHolder.tv_gv_content.setText(brand_name);

            return view;
        }
    }

    private class HistoryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (usedcar_huancun.size() > 0) {
                return usedcar_huancun.size();
            } else {
                return 0;
            }
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
            SearchViewHolder gridViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.gv_search_item, null);
                gridViewHolder = new SearchViewHolder(view);
                view.setTag(gridViewHolder);
            } else {
                gridViewHolder = (SearchViewHolder) view.getTag();
            }
            String content = usedcar_huancun.get(i);
            if (!TextUtils.isEmpty(content)) {
                gridViewHolder.tv_gv_content.setText(content);
            }
            return view;
        }
    }

    class SearchViewHolder {
        private TextView tv_gv_content;

        public SearchViewHolder(View view) {
            tv_gv_content = (TextView) view.findViewById(R.id.tv_gv_content);
        }
    }

}
