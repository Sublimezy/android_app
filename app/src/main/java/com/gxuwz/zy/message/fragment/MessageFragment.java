package com.gxuwz.zy.message.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gxuwz.zy.R;
import com.gxuwz.zy.base.module.BaseFragment;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.constants.AppUrl;
import com.gxuwz.zy.message.adapter.MessageAdapter;
import com.gxuwz.zy.message.entity.bean.MessageBean;
import com.gxuwz.zy.message.entity.dto.ReqMessageBean;
import com.gxuwz.zy.myhttp.MyHttpUtils;
import com.gxuwz.zy.myhttp.RequestCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private ListView listView;
    private MessageAdapter adapter;
    private TextView shuaXing;
    Integer pageNum = 1;
    Integer pageSize = 10000;

    private List<MessageBean> messageBeanList = new ArrayList<>();
    private int MessageBeansListSize;

    public static MessageFragment newInstance(String tag) {
        Bundle bundle = new Bundle();
        MessageFragment fragment = new MessageFragment();
        bundle.putString("message", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {

        View view = LayoutInflater.from(App.context).inflate(R.layout.fragment_message, null);

        listView = view.findViewById(R.id.listView);
        shuaXing = view.findViewById(R.id.shuaxing);
        shuaXing.setOnClickListener(this);

        initializeData(false);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedItem(position);
            }
        });

        return view;
    }

    @Override
    protected String setLoadDate() {
        return "123";
    }

    private void initializeData(boolean flag) {


        Map<String, String> params = new HashMap<>();

        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));


        MyHttpUtils.getHttpMessage(AppUrl.MESSAGE_LIST, params, ReqMessageBean.class, new RequestCallBack<ReqMessageBean>() {


            @Override
            public void requestSuccess(ReqMessageBean json) {
                messageBeanList = json.getRows();
                System.out.println(messageBeanList.toString());
                adapter = new MessageAdapter(getActivity(), messageBeanList);
                listView.setAdapter(adapter);
                if (flag) {
                    showToastShort("刷新成功！");
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.shuaxing) {
            initializeData(true);
        }
    }
}