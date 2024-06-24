package com.xueyiche.zjyk.jiakao.examtext.kemua;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.xueyiche.zjyk.jiakao.R;
import com.xueyiche.zjyk.jiakao.base.module.BaseActivity;
import com.xueyiche.zjyk.jiakao.base.module.DBManager;
import com.xueyiche.zjyk.jiakao.constants.App;


public class ZhuanXiangA extends BaseActivity {

    private ListView mLV_zhuangxiang;
    private String[] leixing;
    private String[] shuliang;
    private int[] imageResIDs;
    private LinearLayout mLL_exam_back;
    private TextView tv_title;

    @Override
    protected int initContentView() {
        return R.layout.activtiy_exam_zhuangxiang;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).titleBar(R.id.ll_title).statusBarDarkFont(true).init();
        mLV_zhuangxiang = (ListView) view.findViewById(R.id.lv_zhuangxiang);
        mLL_exam_back = (LinearLayout) view.findViewById(R.id.zhanghu_mingxi_include).findViewById(R.id.ll_exam_back);
        tv_title = (TextView) view.findViewById(R.id.zhanghu_mingxi_include).findViewById(R.id.tv_login_back);
        initData();
        tv_title.setText("专项训练");
        mLV_zhuangxiang.setAdapter(new ZhuangXiangAdapter());
        mLV_zhuangxiang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(App.context, ZhuanXiangContent.class);
                switch (position) {
                    case 0:
                        intent.putExtra("leixing", leixing[0]);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("leixing", leixing[1]);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("leixing", leixing[2]);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("leixing", leixing[3]);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("leixing", leixing[4]);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("leixing",leixing[5]);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.putExtra("leixing", leixing[6]);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.putExtra("leixing", leixing[7]);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.putExtra("leixing",leixing[8]);
                        startActivity(intent);
                        break;
                    case 9:
                        intent.putExtra("leixing", leixing[9]);
                        startActivity(intent);
                        break;
                    case 10:
                        intent.putExtra("leixing", leixing[10]);
                        startActivity(intent);
                        break;
                    case 11:
                        intent.putExtra("leixing", leixing[11]);
                        startActivity(intent);
                        break;
                    case 12:
                        intent.putExtra("leixing", leixing[12]);
                        startActivity(intent);
                        break;
                    case 13:
                        intent.putExtra("leixing", leixing[13]);
                        startActivity(intent);
                        break;
                }
            }
        });
        mLL_exam_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        DBManager dbManager = new DBManager(this);
        dbManager.copyDBFile();

        leixing = new String[]{"时间", "距离", "罚款", "速度", "标线", "标志", "手势", "信号灯", "记分", "酒驾", "灯光", "仪表", "装置", "路况"};
        shuliang = new String[]{
                dbManager.getZhuangXiangNumber("1",leixing[0]), dbManager.getZhuangXiangNumber("1",leixing[1]),
                dbManager.getZhuangXiangNumber("1",leixing[2]), dbManager.getZhuangXiangNumber("1",leixing[3]),
                dbManager.getZhuangXiangNumber("1",leixing[4]), dbManager.getZhuangXiangNumber("1",leixing[5]),
                dbManager.getZhuangXiangNumber("1",leixing[6]), dbManager.getZhuangXiangNumber("1",leixing[7]),
                dbManager.getZhuangXiangNumber("1",leixing[8]), dbManager.getZhuangXiangNumber("1",leixing[9]),
                dbManager.getZhuangXiangNumber("1",leixing[10]), dbManager.getZhuangXiangNumber("1",leixing[11]),
                dbManager.getZhuangXiangNumber("1",leixing[12]), dbManager.getZhuangXiangNumber("1",leixing[13])};
        imageResIDs = new int[]{R.drawable.zhuanxiang_a, R.drawable.zhuanxiang_b, R.drawable.zhuanxiang_c,
                R.drawable.zhuanxiang_d, R.drawable.zhuanxiang_e, R.drawable.zhuanxiang_f, R.drawable.zhuanxiang_g,
                R.drawable.zhuanxiang_h, R.drawable.zhuanxiang_i, R.drawable.zhuanxiang_j, R.drawable.zhuanxiang_k,
                R.drawable.zhuanxiang_l, R.drawable.zhuanxiang_m, R.drawable.zhuanxiang_n};
    }

    private class ZhuangXiangAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imageResIDs.length;
        }

        @Override
        public Object getItem(int position) {
            return imageResIDs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(App.context, R.layout.zhuanxiang_list_item, null);
            ImageView mIV_xuhao = (ImageView) view.findViewById(R.id.iv_xuhao);
            TextView mTV_leixing = (TextView) view.findViewById(R.id.tv_leixing);
            TextView mTV_shuliang = (TextView) view.findViewById(R.id.tv_shuliang);
            int imageResID = imageResIDs[position];
            String lx = leixing[position];
            String sl = shuliang[position];
            mIV_xuhao.setImageResource(imageResID);
            mTV_leixing.setText(lx);
            mTV_shuliang.setText(sl);
            return view;
        }
    }
}
