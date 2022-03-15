package com.xueyiche.zjyk.xueyiche.newdriverschool.students.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.qiniu.android.utils.Json;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.examtext.MoNiTestPage;
import com.xueyiche.zjyk.xueyiche.examtext.bean.UserTypeBean;
import com.xueyiche.zjyk.xueyiche.main.activities.login.LoginFirstStepActivity;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.StudentTrainTimeActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.YuYueKaoShiActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.student.YuYueLiancheDateActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.adapter.StudentSelfTestContentListAdapter;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.MyTestBean;
import com.xueyiche.zjyk.xueyiche.splash.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.AppUtils;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 张磊 on 2021/1/18.                                       #
 */
//我的驾考，科目一考试， 科目二
public class StudentsSelfTestActivity extends NewBaseActivity implements View.OnClickListener , OnRefreshListener {
    private LinearLayout llBAck;
    private TextView tvTitle, tvSelfOrder, hellworld, tvStudyRecord, tvKeFu, tvAllState, tvAllStateContent;
    private Button btNextStudy;
    private RecyclerView rv_one;
    private String subject_type;
    private RefreshLayout refreshLayout;
    private StudentSelfTestContentListAdapter studentSelfTestContentListAdapter;
    public static StudentsSelfTestActivity stance;


    @Override
    protected int initContentView() {
        return R.layout.my_test_activity;
    }

    @Override
    protected void initView() {
        stance = this;
        llBAck = view.findViewById(R.id.title_include).findViewById(R.id.ll_exam_back);
        tvTitle = view.findViewById(R.id.title_include).findViewById(R.id.tv_login_back);
        tvSelfOrder = view.findViewById(R.id.tvSelfOrder);
        hellworld = view.findViewById(R.id.hellworld);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        tvStudyRecord = view.findViewById(R.id.tvStudyRecord);
        tvKeFu = view.findViewById(R.id.tvKeFu);
        tvAllState = view.findViewById(R.id.tvAllState);
        tvAllStateContent = view.findViewById(R.id.tvAllStateContent);
        btNextStudy = view.findViewById(R.id.btNextStudy);
        rv_one = view.findViewById(R.id.rv_one);
        studentSelfTestContentListAdapter= new StudentSelfTestContentListAdapter(R.layout.self_jiakao_list_item_layout);

    }

    @Override
    protected void initListener() {
        llBAck.setOnClickListener(this);
        tvSelfOrder.setOnClickListener(this);
        tvStudyRecord.setOnClickListener(this);
        btNextStudy.setOnClickListener(this);
        tvKeFu.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("我的驾考");
        rv_one.setHasFixedSize(true);
        rv_one.setNestedScrollingEnabled(false);
        rv_one.setFocusable(false);
        rv_one.setLayoutManager(new LinearLayoutManager(StudentsSelfTestActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.post().url(AppUrl.selectstudyprocess)
                .addParams("stu_user_id", "" + PrefUtils.getParameter("user_id"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        Log.e("selectstudyprocess", string);
                        if (!TextUtils.isEmpty(string)) {
                            MyTestBean myTestBean = JsonUtil.parseJsonToBean(string, MyTestBean.class);
                            if (myTestBean != null) {
                                int code = myTestBean.getCode();
                                App.handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (200 == code) {
                                            MyTestBean.ContentBean content = myTestBean.getContent();
                                            if (content != null) {
                                                String hello_world = content.getHello_world();
                                                subject_type = content.getSubject_type();
                                                if (!TextUtils.isEmpty(subject_type)) {
                                                    if ("0".equals(subject_type) || "1".equals(subject_type) || "2".equals(subject_type) || "3".equals(subject_type) || "4".equals(subject_type)) {
                                                        btNextStudy.setText("继续学习");
                                                    } else if ("5".equals(subject_type) || "6".equals(subject_type) || "7".equals(subject_type) || "8".equals(subject_type)) {
                                                        btNextStudy.setText("预约考试");
                                                    }else if ("9".equals(subject_type)){
                                                        btNextStudy.setText("已完成");
                                                    }else if ("10".equals(subject_type)){
                                                        btNextStudy.setText("报名");
                                                    }
                                                }
                                                if (!TextUtils.isEmpty(hello_world)) {
                                                    hellworld.setText(hello_world);
                                                }
                                                List<MyTestBean.ContentBean.AllListBean> all_list = content.getAll_list();
                                                if (all_list != null && all_list.size() > 0) {
                                                    rv_one.setAdapter(new AllAdapter(all_list));
                                                }

                                            }

                                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tvSelfOrder:
//                if ("10".equals(subject_type)) {
//                    showToastShort("您未报名，无预约记录");
//                    return;
//                }
                openActivity(StudentsOrderRecordActivity.class);
                break;
            case R.id.tvStudyRecord:
//                if ("10".equals(subject_type)) {
//                    showToastShort("您未报名，无学习记录");
//                    return;
//                }
                openActivity(StudentsStudyRecordActivity.class);
                break;
            case R.id.tvKeFu:
                //客服
                openActivity(KeFuActivity.class);
                break;
            case R.id.btNextStudy:
                if (!TextUtils.isEmpty(subject_type)) {
                    if ("0".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, MoNiTestPage.class);
                        intent6.putExtra("moni_style", "1");
                        startActivity(intent6);
                    } else if ("1".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, MoNiTestPage.class);
                        intent6.putExtra("moni_style", "1");
                        startActivity(intent6);
                    } else if ("2".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, YuYueLiancheDateActivity.class);
                        intent6.putExtra("driving_type", "0");
                        startActivity(intent6);
                    } else if ("3".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, YuYueLiancheDateActivity.class);
                        intent6.putExtra("driving_type", "1");
                        startActivity(intent6);
                    } else if ("4".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, MoNiTestPage.class);
                        intent6.putExtra("moni_style", "2");
                        startActivity(intent6);
                    } else if ("5".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, YuYueKaoShiActivity.class);
                        intent6.putExtra("yuyue_type", "1");
                        startActivity(intent6);
                    } else if ("6".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, YuYueKaoShiActivity.class);
                        intent6.putExtra("yuyue_type", "2");
                        startActivity(intent6);
                    } else if ("7".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, YuYueKaoShiActivity.class);
                        intent6.putExtra("yuyue_type", "3");
                        startActivity(intent6);
                    } else if ("8".equals(subject_type)) {
                        Intent intent6 = new Intent(App.context, YuYueKaoShiActivity.class);
                        intent6.putExtra("yuyue_type", "4");
                        startActivity(intent6);
                    } else if ("9".equals(subject_type)) {
                        showToastShort("已完成驾考");
                    }else if ("10".equals(subject_type)) {
                       openActivity(StudentsBaoMingActivity.class);
                    }
                }
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getDataFromNet();
        refreshLayout.finishRefresh(1500);
    }

    public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {
        List<MyTestBean.ContentBean.AllListBean> content;

        public AllAdapter(List<MyTestBean.ContentBean.AllListBean> content) {
            this.content = content;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            TextView tvNumber;
            TextView tvState;
            ImageView ivState;
            RecyclerView rv_content;
            LinearLayout llAll;

            public ViewHolder(View v) {
                super(v);
            }
        }

        @Override
        public int getItemCount() {
            return content.size();
        }

        @Override
        public AllAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_test_list_item_layout, null, false);
            AllAdapter.ViewHolder holder = new AllAdapter.ViewHolder(v);
            holder.tvTitle = v.findViewById(R.id.tvTitle);
            holder.tvNumber = v.findViewById(R.id.tvNumber);
            holder.tvState = v.findViewById(R.id.tvState);
            holder.ivState = v.findViewById(R.id.ivState);
            holder.rv_content = v.findViewById(R.id.rv_content);
            holder.llAll = v.findViewById(R.id.llAll);
            return holder;
        }

        @Override
        public void onBindViewHolder(AllAdapter.ViewHolder holder, final int position) {
            MyTestBean.ContentBean.AllListBean contentBean = content.get(position);
            if (contentBean != null) {
                String cross_subject = contentBean.getCross_subject();
                String sub_times = contentBean.getSub_times();
                String sub_type = contentBean.getSub_type();
                String subject = contentBean.getSubject();
                List<MyTestBean.ContentBean.AllListBean.ListBean> list = contentBean.getList();


                if (!TextUtils.isEmpty(sub_times)) {
                    if (!TextUtils.isEmpty(subject)) {
                        holder.tvTitle.setText(subject);
                        int kemusi_num = PrefUtils.getInt(App.context, "kemusi_num", 0);
                        int kemuyi_num = PrefUtils.getInt(App.context, "kemuyi_num", 0);

                        if ("科目一".equals(subject)) {
                            holder.tvNumber.setVisibility(View.VISIBLE);
                            holder.tvNumber.setText("（" + sub_times + "/3"+"  共"+kemuyi_num+"次）");

                        } else if ("科目二".equals(subject)) {
                            holder.tvNumber.setVisibility(View.INVISIBLE);
                        } else if ("科目三".equals(subject)) {
                            holder.tvNumber.setVisibility(View.INVISIBLE);
                        } else if ("科目四".equals(subject)) {
                            holder.tvNumber.setVisibility(View.VISIBLE);
                            holder.tvNumber.setText("（" + sub_times + "/3"+"  共"+kemusi_num+"次）");

                        }
                    }

                } else {
                    holder.tvNumber.setVisibility(View.INVISIBLE);
                }
                if ("10".equals(subject_type)) {
                    holder.llAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(subject)) {
                                holder.tvTitle.setText(subject);
                                if ("科目一".equals(subject)) {
                                    if ("0".equals(sub_times)||"1".equals(sub_times)||"2".equals(sub_times)) {
                                        DialogUtils.showTiShi(StudentsSelfTestActivity.this,"1");
                                    }else {
                                        new com.xueyiche.zjyk.xueyiche.newdriverschool.students.utils.DialogUtils.Builder(StudentsSelfTestActivity.this, false, false, "您还未报名，请先完成报名!", "报名", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                openActivity(StudentsBaoMingActivity.class);
                                                finish();
                                                dialog.dismiss();
                                            }
                                        }, "取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();

                                    }
                                } else if ("科目二".equals(subject)) {
                                    Intent intent6 = new Intent(App.context, YuYueLiancheDateActivity.class);
                                    intent6.putExtra("driving_type", "0");
                                    startActivity(intent6);
                                } else if ("科目三".equals(subject)) {
                                    Intent intent6 = new Intent(App.context, YuYueLiancheDateActivity.class);
                                    intent6.putExtra("driving_type", "1");
                                    startActivity(intent6);
                                } else if ("科目四".equals(subject)) {
                                    DialogUtils.showTiShi(StudentsSelfTestActivity.this,"4");
                                }
                            }
                        }
                    });
                }
                if (!TextUtils.isEmpty(cross_subject)) {
                    if ("0".equals(cross_subject)) {
                        holder.ivState.setVisibility(View.VISIBLE);
                        holder.tvState.setVisibility(View.GONE);
                    } else if ("1".equals(cross_subject)) {
                        holder.ivState.setVisibility(View.GONE);
                        holder.tvState.setVisibility(View.VISIBLE);
                        holder.tvState.setText("学习中");
                        holder.tvState.setTextColor(getResources().getColor(R.color.colorOrange));
                    } else if ("2".equals(cross_subject)) {
                        holder.ivState.setVisibility(View.GONE);
                        holder.tvState.setVisibility(View.VISIBLE);
                        holder.tvState.setText("已完成");
                        holder.tvState.setTextColor(getResources().getColor(R.color._9999));
                    }
                }
                if (list != null && list.size() > 0) {
                    holder.rv_content.setVisibility(View.VISIBLE);
                    holder.rv_content.setHasFixedSize(true);
                    holder.rv_content.setNestedScrollingEnabled(false);
                    holder.rv_content.setFocusable(false);
                    holder.rv_content.setLayoutManager(new LinearLayoutManager(StudentsSelfTestActivity.this));
                    holder.rv_content.setAdapter(studentSelfTestContentListAdapter);
                    studentSelfTestContentListAdapter.setNewData(list);
                } else {
                    holder.rv_content.setVisibility(View.GONE);
                }

            }
        }
    }

}
