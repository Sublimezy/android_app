package com.xueyiche.zjyk.xueyiche.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.community.bean.CommunityListBean;
import com.xueyiche.zjyk.xueyiche.constants.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * #            Created by 張某人 on 2022/4/6/10:07 上午 .
 * #            com.xueyiche.zjyk.xueyiche.homepage
 * #            xueyiche5.0
 */
public class CommunityFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    public static CommunityFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        CommunityFragment fragment = new CommunityFragment();
        bundle.putString("community", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Object setLoadDate() {
        return "community";
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.fragment_community, null);

        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    List<CommunityListBean> data = new ArrayList<>();

    private void initData() {
        CommunityAdapter communityAdapter = new CommunityAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(communityAdapter);
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        data.add(new CommunityListBean());
        communityAdapter.notifyDataSetChanged();

    }

    class CommunityAdapter extends BaseMultiItemQuickAdapter<CommunityListBean, BaseViewHolder> {


        public CommunityAdapter(List<CommunityListBean> data) {
            super(data);
            addItemType(CommunityListBean.TEXT, R.layout.item_only_text);
            addItemType(CommunityListBean.TEXT_ONE_PIC, R.layout.item_text_one_pic);
            addItemType(CommunityListBean.TEXT_TWO_PIC, R.layout.item_text_two_pic);
            addItemType(CommunityListBean.TEXT_PICS, R.layout.item_text_pics);
            addItemType(CommunityListBean.TEXT_VIDEO, R.layout.item_text_video);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 1 || position == 7) {
                return CommunityListBean.TEXT;
            } else if (position == 4 || position == 5 || position == 6) {
                return CommunityListBean.TEXT_ONE_PIC;
            } else if (position == 2) {
                return CommunityListBean.TEXT_PICS;
            } else if (position == 0) {
                return CommunityListBean.TEXT_VIDEO;
            } else if (position == 3) {
                return CommunityListBean.TEXT_TWO_PIC;
            }
            return CommunityListBean.TEXT;
        }

        @Override
        protected void convert(BaseViewHolder helper, CommunityListBean item) {

            switch (helper.getItemViewType()) {
                case CommunityListBean.TEXT:

                    setOnlyText(helper, item);
                    break;
                case CommunityListBean.TEXT_ONE_PIC:
                    helper.setText(R.id.tv_quan_title, "这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                    break;
                case CommunityListBean.TEXT_TWO_PIC:
                    helper.setText(R.id.tv_quan_title, "这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                    break;
                case CommunityListBean.TEXT_PICS:
                    helper.setText(R.id.tv_quan_title, "这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                    break;
                case CommunityListBean.TEXT_VIDEO:
                    helper.setText(R.id.tv_quan_title, "这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                    break;

                default:

                    break;


            }
        }

        public void setOnlyText(BaseViewHolder helper, CommunityListBean item) {
            ExpandableTextView expandableTextView = helper.getView(R.id.tv_quan_title);
            expandableTextView.setText("这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈这是测试数据哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");

        }


    }

}
