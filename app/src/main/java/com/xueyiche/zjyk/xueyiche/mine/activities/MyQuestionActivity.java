package com.xueyiche.zjyk.xueyiche.mine.activities;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.discover.activity.WenDaContent;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.mine.activities.bean.MyQuestionFaBuBean;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZL on 2019/2/21.
 */
public class MyQuestionActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_exam_back;
    private TextView title;
    private ListView lv_myquestion;
    private RadioButton rb_fabu, rb_huida;
    private List<MyQuestionFaBuBean.ContentBean> content;
    private FaBuAdapter faBuAdapter;
    private HuidaAdapter huidaAdapter;
    private String type = "0";

    @Override
    protected int initContentView() {
        return R.layout.my_question_activity;
    }

    @Override
    protected void initView() {
        title = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        ll_exam_back = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        lv_myquestion = (ListView) view.findViewById(R.id.lv_myquestion);
        rb_fabu = (RadioButton) view.findViewById(R.id.rb_fabu);
        rb_huida = (RadioButton) view.findViewById(R.id.rb_huida);
        faBuAdapter = new FaBuAdapter();
        huidaAdapter = new HuidaAdapter();

    }

    @Override
    protected void initListener() {
        rb_fabu.setOnClickListener(this);
        ll_exam_back.setOnClickListener(this);
        rb_huida.setOnClickListener(this);
        lv_myquestion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(MyQuestionActivity.this)
                        .setTitle("删除")
                        .setMessage("您是否删除?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = content.get(i).getId();
                                int comment_id = content.get(i).getComment_id();
                                delete(""+id,""+comment_id);
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
        lv_myquestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = content.get(i).getId();
                Intent intent = new Intent(App.context, WenDaContent.class);
                intent.putExtra("wenda_id",id+"");
                startActivity(intent);
            }
        });

    }

    private void delete(String id,String comment_id) {
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.My_Question_FaBu_Delete)
                    .addParams("id", id)
                    .addParams("comment_id", !TextUtils.isEmpty(comment_id)?comment_id:"")
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                SuccessDisCoverBackBean myQuestionFaBuBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                if (myQuestionFaBuBean != null) {
                                    int code = myQuestionFaBuBean.getCode();
                                    if (200 == code) {
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if ("0".equals(type)) {
                                                    getDataFromNet();
                                                }else if ("1".equals(type)){
                                                    getDataFromNetTwo();
                                                }
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
        } else {
            Toast.makeText(MyQuestionActivity.this, "请检查网络设置！！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void initData() {
        title.setText("我的问答");
        getDataFromNet();
    }

    private void getDataFromNet() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.My_Question_FaBu)
                    .addParams("user_id", user_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                MyQuestionFaBuBean myQuestionFaBuBean = JsonUtil.parseJsonToBean(string, MyQuestionFaBuBean.class);
                                if (myQuestionFaBuBean != null) {
                                    int code = myQuestionFaBuBean.getCode();
                                    if (200 == code) {
                                        content = myQuestionFaBuBean.getContent();
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                lv_myquestion.setAdapter(faBuAdapter);
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
        } else {
            Toast.makeText(MyQuestionActivity.this, "请检查网络设置！！", Toast.LENGTH_SHORT).show();
        }


    }
    private void getDataFromNetTwo() {
        String user_id = PrefUtils.getString(App.context, "user_id", "");
        if (XueYiCheUtils.IsHaveInternet(this)) {
            showProgressDialog(false);
            OkHttpUtils.post().url(AppUrl.My_Question_HuiDa)
                    .addParams("user_id", user_id)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                MyQuestionFaBuBean myQuestionFaBuBean = JsonUtil.parseJsonToBean(string, MyQuestionFaBuBean.class);
                                if (myQuestionFaBuBean != null) {
                                    int code = myQuestionFaBuBean.getCode();
                                    if (200 == code) {
                                        content = myQuestionFaBuBean.getContent();
                                        App.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                lv_myquestion.setAdapter(huidaAdapter);
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
        } else {
            Toast.makeText(MyQuestionActivity.this, "请检查网络设置！！", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rb_fabu:
                type = "0";
                getDataFromNet();
                break;
            case R.id.rb_huida:
                type = "1";
                getDataFromNetTwo();
                break;

            default:

                break;
        }
    }
    private class HuidaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (content.size()>0) {
                return content.size();
            }else {
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
            FaBuViweHolder viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.my_answer_item, null);
                viewHolder = new FaBuViweHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (FaBuViweHolder) view.getTag();
            }
            String title = content.get(i).getTitle();
            String represent = content.get(i).getRepresent();
            String comment = content.get(i).getComment();
            String image1 = content.get(i).getImage1();
            String image2 = content.get(i).getImage2();
            String image3 = content.get(i).getImage3();
            if (!TextUtils.isEmpty(title)) {
                viewHolder.tv_wenda_title.setText(title);
            }
            if (!TextUtils.isEmpty(represent)) {
                viewHolder.tv_wenda_content.setText(represent);
            }
            if (!TextUtils.isEmpty(comment)) {
                viewHolder.tv_wenda_answer.setText(comment);
            }
                if (TextUtils.isEmpty(image1)&&TextUtils.isEmpty(image2)&&TextUtils.isEmpty(image3)) {
                    viewHolder.ll_pic.setVisibility(View.GONE);
                }else {
                    if (TextUtils.isEmpty(image1)) {
                        viewHolder.iv_one.setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.with(App.context).load(image1).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_one);
                        viewHolder.iv_one.setVisibility(View.VISIBLE);
                    }
                    if (TextUtils.isEmpty(image2)) {
                        viewHolder.iv_two.setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.with(App.context).load(image2).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_two);
                        viewHolder.iv_two.setVisibility(View.VISIBLE);
                    }
                    if (TextUtils.isEmpty(image3)) {
                        viewHolder.iv_three.setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.with(App.context).load(image3).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_three);
                        viewHolder.iv_three.setVisibility(View.VISIBLE);
                    }

                }

            return view;
        }
    }
        private class FaBuAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                if (content.size()>0) {
                    return content.size();
                }else {
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
                FaBuViweHolder viewHolder = null;
                if (view == null) {
                    view = LayoutInflater.from(App.context).inflate(R.layout.my_question_item, null);
                    viewHolder = new FaBuViweHolder(view);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (FaBuViweHolder) view.getTag();
                }
                String title = content.get(i).getTitle();
                String represent = content.get(i).getRepresent();
                String image1 = content.get(i).getImage1();
                String image2 = content.get(i).getImage2();
                String image3 = content.get(i).getImage3();
                if (!TextUtils.isEmpty(title)) {
                    viewHolder.tv_wenda_title.setText(title);
                }
                if (!TextUtils.isEmpty(represent)) {
                    viewHolder.tv_wenda_content.setText(represent);
                }
                if (TextUtils.isEmpty(image1)&&TextUtils.isEmpty(image2)&&TextUtils.isEmpty(image3)) {
                    viewHolder.ll_pic.setVisibility(View.GONE);
                }else {
                    if (TextUtils.isEmpty(image1)) {
                        viewHolder.iv_one.setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.with(App.context).load(image1).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_one);
                        viewHolder.iv_one.setVisibility(View.VISIBLE);
                    }
                    if (TextUtils.isEmpty(image2)) {
                        viewHolder.iv_two.setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.with(App.context).load(image2).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_two);
                        viewHolder.iv_two.setVisibility(View.VISIBLE);
                    }
                    if (TextUtils.isEmpty(image3)) {
                        viewHolder.iv_three.setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.with(App.context).load(image3).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(viewHolder.iv_three);
                        viewHolder.iv_three.setVisibility(View.VISIBLE);
                    }

                }
                return view;
            }


        }

        public class FaBuViweHolder {
            private TextView tv_wenda_title,tv_wenda_content,tv_wenda_answer;
            private LinearLayout  ll_pic;
            private RoundImageView iv_one, iv_two, iv_three;


            public FaBuViweHolder(View view) {
                tv_wenda_title = (TextView) view.findViewById(R.id.tv_wenda_title);
                tv_wenda_content = (TextView) view.findViewById(R.id.tv_wenda_content);
                tv_wenda_answer = (TextView) view.findViewById(R.id.tv_wenda_answer);
                ll_pic = (LinearLayout) view.findViewById(R.id.ll_pic);
                tv_wenda_title = (TextView) view.findViewById(R.id.tv_wenda_title);
                iv_one = (RoundImageView) view.findViewById(R.id.iv_one);
                iv_two = (RoundImageView) view.findViewById(R.id.iv_two);
                iv_three = (RoundImageView) view.findViewById(R.id.iv_three);
            }
        }

}
