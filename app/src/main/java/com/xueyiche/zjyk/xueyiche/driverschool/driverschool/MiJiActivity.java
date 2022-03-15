package com.xueyiche.zjyk.xueyiche.driverschool.driverschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.CommonBean;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.driverschool.adapter.MiJiAdapter;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.MiJIListBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.CommonSearchBean;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.splash.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
 * #            Created by 張某人 on 6/21/21/1:28 PM .
 * #            com.xueyiche.zjyk.xueyiche.driverschool.driverschool
 * #            xueyiche3.0
 */
public class MiJiActivity extends BaseActivity implements OnRefreshListener {
    @BindView(R.id.ll_exam_back)
    LinearLayout llExamBack;
    @BindView(R.id.tv_login_back)
    TextView tvLoginBack;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rb_subject0)
    RadioButton rbSubject0;
    @BindView(R.id.rb_subject1)
    RadioButton rbSubject1;
    @BindView(R.id.rb_subject2)
    RadioButton rbSubject2;
    @BindView(R.id.rb_subject3)
    RadioButton rbSubject3;
    @BindView(R.id.rb_subject4)
    RadioButton rbSubject4;
    private MiJiAdapter miJiAdapter;
    private CommonBean commonBean = new CommonBean();

    @Override
    protected int initContentView() {
        return R.layout.miji_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        refreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MiJiActivity.this,2));
        miJiAdapter = new MiJiAdapter(R.layout.item_miji_layout);
        mRecyclerView.setAdapter(miJiAdapter);
    }

    @Override
    protected void initListener() {
        miJiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MiJIListBean.ContentBean contentBean = (MiJIListBean.ContentBean) adapter.getItem(position);
                if (contentBean != null) {
//                    Intent intent = new Intent(App.context, MiJiContentActivity.class);
                    Intent intent = new Intent(App.context, MiJiContentActivityTwo.class);
                    intent.putExtra("url", contentBean.getSecret_video_url());
//                    if (position==0) {
//                        intent.putExtra("url", "http://xychead.xueyiche.vip/1624436390320263.mp4");
//                    }else if (position==1){
//                        intent.putExtra("url", "http://xychead.xueyiche.vip/rurcpub5f3cfu9ff8jkjhixso8qb8mc5");
//                    }
                    intent.putExtra("title", contentBean.getTitle());
                    intent.putExtra("img", contentBean.getSecret_img_url());
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void initData() {
        tvLoginBack.setText("驾考秘籍");
        commonBean.setType("");
        getDataFromNet();
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            showProgressDialog(false);
            Map<String, String> map = new HashMap<>();
            map.put("secret_type", commonBean.getType());
            MyHttpUtils.postHttpMessage(AppUrl.selectdrivingsecret, map, MiJIListBean.class, new RequestCallBack<MiJIListBean>() {
                @Override
                public void requestSuccess(MiJIListBean json) {
                    stopProgressDialog();
                    if (200 == json.getCode()) {
                        List<MiJIListBean.ContentBean> content = json.getContent();
                        if (content != null && content.size() > 0) {

                            miJiAdapter.setNewData(content);

                        } else {
                            miJiAdapter.getData().clear();
                            miJiAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
                            miJiAdapter.notifyDataSetChanged();
                        }

                    } else {
                        miJiAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));

                    }
                    miJiAdapter.notifyDataSetChanged();
                }

                @Override
                public void requestError(String errorMsg, int errorType) {
                    stopProgressDialog();
                }
            });
        } else {
            stopProgressDialog();
            ToastUtil.show(StringConstants.CHECK_NET);
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getDataFromNet();
        refreshLayout.finishRefresh(1500);
    }


    @OnClick({R.id.ll_exam_back, R.id.rb_subject0, R.id.rb_subject1, R.id.rb_subject2, R.id.rb_subject3, R.id.rb_subject4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.rb_subject0:
                commonBean.setType("");
                getDataFromNet();
                break;
            case R.id.rb_subject1:
                commonBean.setType("1");
                getDataFromNet();
                break;
            case R.id.rb_subject2:
                commonBean.setType("2");
                getDataFromNet();
                break;
            case R.id.rb_subject3:
                commonBean.setType("3");
                getDataFromNet();
                break;
            case R.id.rb_subject4:
                commonBean.setType("4");
                getDataFromNet();
                break;
        }
    }
}
