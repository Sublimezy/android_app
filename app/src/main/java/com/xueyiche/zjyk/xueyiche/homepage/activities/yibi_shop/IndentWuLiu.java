package com.xueyiche.zjyk.xueyiche.homepage.activities.yibi_shop;

import android.content.ClipboardManager;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.homepage.bean.WuLiuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/10/18.
 */
public class IndentWuLiu extends BaseActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private TextView tvTitle;
    private TextView  tv_wuliu_company, tv_wuliu_number;
    private Button bt_fuzhi;
    private RecyclerView rv_liucheng;
    private List<WuLiuBean> mWuLiuBeanList; //物流追踪列表的数据源
    private WuLiuAdapter mAdapter;

    @Override
    protected int initContentView() {
        return R.layout.indent_wuliu;
    }

    @Override
    protected void initView() {
        llBack = (LinearLayout) view.findViewById(R.id.yibi_wuliu_include).findViewById(R.id.ll_exam_back);
        tvTitle = (TextView) view.findViewById(R.id.yibi_wuliu_include).findViewById(R.id.tv_login_back);
        tv_wuliu_company = (TextView) view.findViewById(R.id.tv_wuliu_company);
        bt_fuzhi = (Button) view.findViewById(R.id.bt_fuzhi);
        tv_wuliu_number = (TextView) view.findViewById(R.id.tv_wuliu_number);
        rv_liucheng = (RecyclerView) view.findViewById(R.id.rv_liucheng);
        llBack.setOnClickListener(this);
        bt_fuzhi.setOnClickListener(this);
    }

    @Override
    protected void initListener() {
        mWuLiuBeanList = new ArrayList<>();
        mWuLiuBeanList.add(new WuLiuBean("0", "2017年6月18日 上午12:04:01", "在湖北武汉洪山区光谷公司长江社区便民服务站进行签收扫描，快件已被 已签收 签收"));
        mWuLiuBeanList.add(new WuLiuBean("1", "2017年6月18日 上午11:57:25", "在湖北武汉洪山区光谷公司长江社区便民服务站进行派件扫描；派送业务员：老王；联系电话：17786550311在湖北武汉洪山区光谷公司长江社区便民服务站进行派件扫描；派送业务员：老王；联系电话：17786550311"));
        mWuLiuBeanList.add(new WuLiuBean("1", "2017年6月17日 下午4:43:29", "在湖北武汉洪山区光谷公司进行快件扫描，将发往：湖北武汉洪山区光谷公司长江社区便民服务站"));
        mWuLiuBeanList.add(new WuLiuBean("1", "2017年6月17日 上午9:11:21", "从湖北武汉分拨中心发出，本次转运目的地：湖北武汉洪山区光谷公司"));
        mWuLiuBeanList.add(new WuLiuBean("1", "2017年6月17日 上午1:53:14", "在湖南长沙分拨中心进行装车扫描，即将发往：湖北武汉分拨中心"));
        mWuLiuBeanList.add(new WuLiuBean("1", "2017年6月17日 上午1:50:18", "在分拨中心湖南长沙分拨中心进行称重扫描"));
        mWuLiuBeanList.add(new WuLiuBean("1", "2017年6月16日 上午11:27:58", "在湖南隆回县公司进行到件扫描"));
    }

    @Override
    protected void initData() {
        tvTitle.setText("物流详情");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        mAdapter = new WuLiuAdapter(this, mWuLiuBeanList);
        rv_liucheng.setLayoutManager(layoutManager);
        rv_liucheng.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                finish();
                break;
            case R.id.bt_fuzhi:
                ClipboardManager copy = (ClipboardManager) IndentWuLiu.this
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText("18346012117");
                showToastShort("复制成功");
                break;
        }
    }

    public class WuLiuAdapter extends RecyclerView.Adapter<WuLiuAdapter.TraceViewHolder> {

        private static final String TYPE_CURR = "0"; //当前
        private static final String TYPE_NORMAL ="1"; //历史记录
        private Context mContext;
        private List<WuLiuBean> mList;
        private LayoutInflater inflater;

        public WuLiuAdapter(Context mContext, List<WuLiuBean> mList) {
            this.mContext = mContext;
            this.mList = mList;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public TraceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TraceViewHolder(inflater.inflate(R.layout.wuliu_item, parent, false));
        }

        @Override
        public void onBindViewHolder(TraceViewHolder holder, int position) {
            //设置相关数据
            WuLiuBean wuLiuBean = mList.get(position);
            String type = wuLiuBean.getType();
            if (type.equals(TYPE_CURR)) {
                holder.acceptStationTv.setTextColor(mContext.getResources().getColor(R.color.colorOrange));
                holder.acceptTimeTv.setTextColor(mContext.getResources().getColor(R.color.colorOrange));
                holder.dotIv.setImageResource(R.mipmap.wuliu_point);
            } else if (type.equals(TYPE_NORMAL)) {
                holder.acceptStationTv.setTextColor(mContext.getResources().getColor(R.color.test_color));
                holder.dotIv.setImageResource(R.mipmap.indent_blcak);
            }
            holder.acceptTimeTv.setText(wuLiuBean.getAcceptTime());
            holder.acceptStationTv.setText(wuLiuBean.getAcceptStation());
            if (position == mList.size() - 1) {
                //最后一条数据，隐藏时间轴的竖线和水平的分割线
                holder.timeLineView.setVisibility(View.INVISIBLE);
                holder.dividerLineView.setVisibility(View.INVISIBLE);
            }
        }


        public class TraceViewHolder extends RecyclerView.ViewHolder {

            private TextView acceptTimeTv;  //接收时间
            private TextView acceptStationTv;  //接收地点
            private ImageView dotIv; //当前位置
            private View dividerLineView; //时间轴的竖线
            private View timeLineView; //水平的分割线
            public TraceViewHolder(View itemView) {
                super(itemView);
                acceptTimeTv = (TextView) itemView.findViewById(R.id.accept_time_tv);
                acceptStationTv = (TextView) itemView.findViewById(R.id.accept_station_tv);
                dotIv = (ImageView) itemView.findViewById(R.id.dot_iv);
                dividerLineView = itemView.findViewById(R.id.divider_line_view);
                timeLineView = itemView.findViewById(R.id.time_line_view);
            }
        }
    }
}
