package com.xueyiche.zjyk.xueyiche.driverschool.driverschool.fenqi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.homepage.view.BasicPopup;

/**
 * Created by ZL on 2018/10/9.
 */
public class FenQiTestDriverBookActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvTitle, tv_choose_qishu;
    private LinearLayout llBack;

    @Override
    protected int initContentView() {
        return R.layout.fenqi_testdriverbook_activity;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_choose_qishu = (TextView) view.findViewById(R.id.tv_choose_qishu);

    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_choose_qishu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText("分期考驾照");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.tv_choose_qishu:
               ChooseQishu();
                break;
            default:

                break;
        }
    }

    public void ChooseQishu() {
        BasicPopup basicPopup = new BasicPopup(FenQiTestDriverBookActivity.this) {
            @Override
            protected View makeContentView() {
                View view = LayoutInflater.from(App.context).inflate(R.layout.choose_qishu_pop, null);
                RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
                final ListView list_view = (ListView) view.findViewById(R.id.list_view);

//                list_view.setItemChecked(i, true);
                list_view.setAdapter(new ChooseAdapter());
                list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int checkedItemPosition = list_view.getCheckedItemPosition();
                        Toast.makeText(FenQiTestDriverBookActivity.this, "点击" + checkedItemPosition, Toast.LENGTH_SHORT).show();

//                        dismiss();
                    }
                });
                parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();

                    }
                });
                return view;
            }
        };
        basicPopup.show();
    }

    private class ChooseAdapter extends BaseAdapter {
        private String[] qishu = {"12期", "9期", "6期", "3期"};


        @Override
        public int getCount() {
            return qishu.length;
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
            ChooseViewHolder chooseViewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(App.context).inflate(R.layout.choose_qishu_item, null);
                chooseViewHolder = new ChooseViewHolder(view);
                view.setTag(chooseViewHolder);
            } else {
                chooseViewHolder = (ChooseViewHolder) view.getTag();
            }
            chooseViewHolder.tv_qishu.setText(qishu[i]);

            return view;
        }
        private class ChooseViewHolder {
            private TextView tv_qishu, tv_money, tv_shouxufei;
            public ChooseViewHolder(View view) {
                tv_qishu = (TextView) view.findViewById(R.id.tv_qishu);
                tv_shouxufei = (TextView) view.findViewById(R.id.tv_shouxufei);
                tv_money = (TextView) view.findViewById(R.id.tv_money);
            }
        }

    }
}
