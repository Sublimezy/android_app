package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.NewBaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.BaseBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.bean.ProjectListBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.newdriverschool.students.bean.MyTestBean;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 练车详情 _ 待评价
 */
public class WaitPingJiaTrainingDetailActivity extends NewBaseActivity {


    @BindView(R.id.title_bar_iv_back)
    ImageView titleBarIvBack;
    @BindView(R.id.title_bar_back)
    RelativeLayout titleBarBack;
    @BindView(R.id.title_bar_title_text)
    TextView titleBarTitleText;
    @BindView(R.id.title_bar_right_text)
    TextView titleBarRightText;
    @BindView(R.id.title_bar_rl)
    RelativeLayout titleBarRl;
    @BindView(R.id.tv_kemu_2)
    TextView tvKemu2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_total_xueshi)
    TextView tvTotalXueshi;
    @BindView(R.id.tv_lianxi_xiangmu)
    TextView tvLianxiXiangmu;
    @BindView(R.id.recycler_item)
    RecyclerView recyclerItem;
    @BindView(R.id.ll_lianxi_xiangmu)
    LinearLayout llLianxiXiangmu;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.rg_all)
    RadioGroup rgAll;
    @BindView(R.id.et_pingjia)
    EditText etPingjia;
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    private ProjectAdapter projectAdapter;
    private String training_id;

    @Override
    protected int initContentView() {
        return R.layout.activity_wait_pingjia_training_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        titleBarRl.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        ImmersionBar.with(this).titleBar(titleBarRl).statusBarDarkFont(true).keyboardEnable(true).init();
        titleBarIvBack.setImageDrawable(getResources().getDrawable(R.mipmap.white_iv_back));
        titleBarTitleText.setText("待评价");
        titleBarTitleText.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        training_id = getIntent().getStringExtra("training_id");
        String entry_project = getIntent().getStringExtra("entry_project");//科目二
        String stu_name = getIntent().getStringExtra("stu_name");//姓名
        String num_class = getIntent().getStringExtra("num_class");//共2学时
        String phone = getIntent().getStringExtra("phone");//共2学时


        tvName.setText(stu_name);
        tvPhone.setText(phone);
        tvTotalXueshi.setText(num_class);
        tvKemu2.setText(entry_project);


        projectAdapter = new ProjectAdapter(R.layout.item_project_kemu);
        recyclerItem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerItem.setAdapter(projectAdapter);

        List<ProjectListBean> projectList = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("stu_user_id", "" + PrefUtils.getParameter("user_id"));
        MyHttpUtils.postHttpMessage(AppUrl.selectstudyprocess, params, MyTestBean.class, new RequestCallBack<MyTestBean>() {
            @Override
            public void requestSuccess(MyTestBean json) {
                if (json.getCode() == 200) {

                    List<MyTestBean.ContentBean.AllListBean> all_list = json.getContent().getAll_list();
                    int index;
                    if ("科目二".equals(entry_project)) {
                        index = 1;
                    } else {
                        index = 2;
                    }
                    List<MyTestBean.ContentBean.AllListBean.ListBean> list = all_list.get(index).getList();

                    for (int i = 0; i < list.size(); i++) {
                        MyTestBean.ContentBean.AllListBean.ListBean listBean = list.get(i);
                        ProjectListBean item = new ProjectListBean();
                        item.setCheck_or_not(false);
                        item.setSub_type(listBean.getSub_type());
                        item.setSubject(listBean.getSubject());
                        item.setSubject_id(listBean.getSubject_id());

                        projectList.add(item);

                    }

                    projectAdapter.setNewData(projectList);

                } else {
//                    finish();
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("服务器连接失败,稍后再试!");

            }
        });


    }


    @OnClick({R.id.title_bar_back, R.id.tv_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.tv_btn:
                List<ProjectListBean> data = projectAdapter.getData();
                String practice_content = "";
                for (int i = 0; i < data.size(); i++) {
                    ProjectListBean projectListBean = data.get(i);
                    if (projectListBean.isCheck_or_not()) {
                        practice_content += projectListBean.getSubject() + ",";
                    }
                }

                if (practice_content.length() > 0) {
                    practice_content = practice_content.substring(0, practice_content.length() - 1);
                }

                if (TextUtils.isEmpty(practice_content) || "".equals(practice_content)) {
                    showToastShort("请选择训练的项目");
                    return;
                }

                showProgressDialog(false, "正在提交");

                int checkedRadioButtonId = rgAll.getCheckedRadioButtonId();
                String coach_to_student = "";
                switch (checkedRadioButtonId) {
                    case R.id.rb_1:
                        coach_to_student = "0";
                        break;
                    case R.id.rb_2:
                        coach_to_student = "1";
                        break;
                    case R.id.rb_3:
                        coach_to_student = "2";
                        break;
                    case R.id.rb_4:
                        coach_to_student = "3";
                        break;


                }
                Map<String, String> params = new HashMap<>();
                params.put("practice_content", practice_content);
                params.put("training_id", training_id);
                params.put("coach_to_student", coach_to_student);//教练评价学员(0:优秀 1:熟练 2:较好 3:生疏)
                params.put("coach_to_student_detail", etPingjia.getText().toString().trim());// 	教练评价学员详情
                MyHttpUtils.postHttpMessage(AppUrl.evaluationboth, params, BaseBean.class, new RequestCallBack<BaseBean>() {
                    @Override
                    public void requestSuccess(BaseBean json) {
                        stopProgressDialog();
                        if (json.getCode() == 200) {
                            setResult(1234);
                            showToastShort("评价完成");
                            finish();
                        } else {
                            showToastShort(json.getMsg());

                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {
                        stopProgressDialog();
                        showToastShort("连接服务器失败");

                    }
                });


                break;
        }
    }

    class ProjectAdapter extends BaseQuickAdapter<ProjectListBean, BaseViewHolder> {

        public ProjectAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProjectListBean item) {
            CheckBox checkBox = helper.getView(R.id.check_box);
            checkBox.setText(item.getSubject());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setCheck_or_not(isChecked);
                }
            });
        }
    }
}
