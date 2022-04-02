package com.xueyiche.zjyk.xueyiche.practicecar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.practicecar.PracticeCarContentAdapter;
import com.xueyiche.zjyk.xueyiche.practicecar.view.CustomShapeImageView;

import java.util.ArrayList;
import java.util.List;

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
 * #            Created by 張某人 on 2022/3/17/6:18 下午 .
 * #            com.xueyiche.zjyk.xueyiche.practicecar.activity
 * #            xueyiche5.0
 */
public class PracticeCarContentActivity extends BaseActivity {
    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CustomShapeImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tvJiaLing)
    TextView tvJiaLing;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_have_money)
    TextView tvHaveMoney;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    private PracticeCarContentAdapter practiceCarContentAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_practice_car_content;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        practiceCarContentAdapter = new PracticeCarContentAdapter(R.layout.item_image_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(PracticeCarContentActivity.this));
        recyclerView.setAdapter(practiceCarContentAdapter);
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        practiceCarContentAdapter.setNewData(list);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.iv_login_back, R.id.tvOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                break;
            case R.id.tvOrder:
                break;
        }
    }
}
