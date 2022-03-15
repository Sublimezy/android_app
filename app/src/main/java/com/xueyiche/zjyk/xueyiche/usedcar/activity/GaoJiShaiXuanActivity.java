package com.xueyiche.zjyk.xueyiche.usedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.usedcar.adapter.CustomizeLVBaseAdapter;
import com.xueyiche.zjyk.xueyiche.usedcar.adapter.LeftAdapter;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.ShaiXuanNumberBean;
import com.xueyiche.zjyk.xueyiche.usedcar.bean.UsedCarShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.usedcar.db.DBUsedCarManager;
import com.xueyiche.zjyk.xueyiche.usedcar.view.HaveHeaderListView;
import com.xueyiche.zjyk.xueyiche.usedcar.view.MyDrawerLayout;
import com.xueyiche.zjyk.xueyiche.usedcar.view.RangeSeekbar;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2018/6/28.
 */
public class GaoJiShaiXuanActivity extends BaseActivity implements View.OnClickListener {
    //左边的ListView
    private ListView lv_left;
    //左边ListView的Adapter
    private LeftAdapter leftAdapter;
    //左边的数据存储
    private List<String> leftStr;
    //左边数据的标志
    private List<Boolean> flagArray;
    //右边的ListView
    private HaveHeaderListView lv_right;
    //右边的ListView的Adapter
    private RightAdapter rightAdapter;
    //右边的数据存储
    private List<List<String>> rightStr;
    //是否滑动标志位
    private Boolean isScroll = false;
    private LinearLayout llBack;
    private TextView tv_login_back;
    private TextView tv_wenxintishi;
    private String[] titile = {"车型", "变速箱", "车龄", "里程", "排量", "排放标准", "颜色", "燃料类型", "产地", "座位数", "国别"};
    private MyDrawerLayout drawerlayout;
    private LinearLayout ll_cehua;
    private TextView tv_usedcar_number, tv_affirm;
    private DBUsedCarManager dbUsedCarManager;
    private TextView tv_chongzhi;
    private int width;
    private String url = "";
    private String qu_time_date,huan_city,huan_time_date,qu_city,store_id,shangMenQu,shangMenHuan,duration;

    @Override
    protected int initContentView() {
        return R.layout.gaojishaixuan_activity;
    }

    @Override
    protected void initView() {
        dbUsedCarManager = new DBUsedCarManager(this);
        dbUsedCarManager.copyDBFile();

        lv_left = (ListView) findViewById(R.id.lv_left);
        llBack = (LinearLayout) view.findViewById(R.id.title).findViewById(R.id.ll_exam_back);
        tv_login_back = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_login_back);
        tv_wenxintishi = (TextView) view.findViewById(R.id.title).findViewById(R.id.tv_wenxintishi);
        lv_right = (HaveHeaderListView) findViewById(R.id.lv_right);
        drawerlayout = (MyDrawerLayout) view.findViewById(R.id.drawerlayout);
        ll_cehua = (LinearLayout) view.findViewById(R.id.ll_cehua);
        tv_usedcar_number = (TextView) view.findViewById(R.id.tv_usedcar_number);
        tv_affirm = (TextView) view.findViewById(R.id.tv_affirm);
        tv_chongzhi = (TextView) view.findViewById(R.id.tv_chongzhi);
        drawerlayout.openDrawer(Gravity.RIGHT);
        drawerlayout.setScrimColor(Color.TRANSPARENT);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = ll_cehua.getLayoutParams();//获取drawerlayout的布局
        para.width = width1 / 3 * 2;//修改宽度
        para.height = height1;//修改高度
        ll_cehua.setLayoutParams(para); //设置修改后的布局。
        leftStr = new ArrayList<>();
        flagArray = new ArrayList<>();
        leftAdapter = new LeftAdapter(GaoJiShaiXuanActivity.this, leftStr, flagArray);
        rightStr = new ArrayList<List<String>>();
        lv_left.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter(GaoJiShaiXuanActivity.this, leftStr, rightStr);
        lv_right.setAdapter(rightAdapter);
        initShuJu();
        width = width1 / 3 * 2 - 30;
    }

    @Override
    protected void initListener() {
        llBack.setOnClickListener(this);
        tv_chongzhi.setOnClickListener(this);
        tv_affirm.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initShuJu() {
        //左边相关数据
        for (int i = 0; i < titile.length; i++) {
            leftStr.add(titile[i]);
            if (i == 0) {
                flagArray.add(true);
            } else {
                flagArray.add(false);
            }
            leftAdapter.notifyDataSetChanged();
            List<String> food1 = new ArrayList<>();
            food1.add("888");
            rightStr.add(food1);
            rightAdapter.notifyDataSetChanged();
        }
    }

    private void setCity() {
        tv_wenxintishi.setVisibility(View.VISIBLE);
        tv_wenxintishi.setText("哈尔滨");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String cartype = intent.getStringExtra("cartype");
        if ("rent".equals(cartype)) {
            url = AppUrl.Used_Car_Rent;
        } else if ("buy".equals(cartype)) {
            url = AppUrl.Used_Car_Buy;
        }
        shangMenQu = intent.getStringExtra("shangMenQu");
        shangMenHuan = intent.getStringExtra("shangMenHuan");
        qu_time_date = intent.getStringExtra("qu_time_date");
        huan_time_date = intent.getStringExtra("huan_time_date");
        qu_city = intent.getStringExtra("qu_city_usedcar");
        huan_city = intent.getStringExtra("huan_city_usedcar");
        store_id = intent.getStringExtra("store_id_usedcar");
        duration = intent.getStringExtra("duration");
        getUsedCarNumber();
        setCity();
        tv_login_back.setText("高级筛选");
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isScroll = false;
                for (int i = 0; i < leftStr.size(); i++) {
                    if (i == position) {
                        flagArray.set(i, true);
                    } else {
                        flagArray.set(i, false);
                    }
                }
                //更新
                leftAdapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    //查找
                    rightSection += rightAdapter.getCountForSection(i) + 1;
                }
                //显示到rightSection所代表的标题
                lv_right.setSelection(rightSection);
            }
        });
        lv_right.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lv_right.getLastVisiblePosition() == (lv_right.getCount() - 1)) {
                            lv_left.setSelection(ListView.FOCUS_DOWN);
                        }
                        // 判断滚动到顶部
                        if (lv_right.getFirstVisiblePosition() == 0) {
                            lv_left.setSelection(0);
                        }
                        break;
                }
            }

            int y = 0;
            int x = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < rightStr.size(); i++) {
                        if (i == rightAdapter.getSectionForPosition(lv_right.getFirstVisiblePosition())) {
                            flagArray.set(i, true);
                            //获取当前标题的标志位
                            x = i;
                        } else {
                            flagArray.set(i, false);
                        }
                    }
                    if (x != y) {
                        leftAdapter.notifyDataSetChanged();
                        //将之前的标志位赋值给y，下次判断
                        y = x;
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            dbUsedCarManager.cleanAllState();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_exam_back:
                dbUsedCarManager.cleanAllState();
                dbUsedCarManager.changeFanWei("车龄", "0", "A");
                dbUsedCarManager.changeFanWei("里程", "0", "A");
                dbUsedCarManager.changeFanWei("排量", "0", "A");
                finish();
                break;
            case R.id.tv_chongzhi:
                dbUsedCarManager.cleanAllState();
                dbUsedCarManager.changeFanWei("车龄", "0", "A");
                dbUsedCarManager.changeFanWei("里程", "0", "A");
                dbUsedCarManager.changeFanWei("排量", "0", "A");
                rightAdapter = new RightAdapter(GaoJiShaiXuanActivity.this, leftStr, rightStr);
                lv_right.setAdapter(rightAdapter);
                break;
            case R.id.tv_affirm:
                finish();
                break;
        }
    }

    public class RightAdapter extends CustomizeLVBaseAdapter {
        //上下文
        private Context mContext;
        //标题
        private List<String> leftStr;
        //内容
        private List<List<String>> rightStr;
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;
        final int TYPE_Price_Text = 0;
        final int TYPE_ImageAndText = 1;
        final int TYPE_SeekBar = 2;

        private String[] cheling = {"0", "1", "2", "3", "4", "5", "不限"};
        private String[] licheng = {"0", "3", "6", "9", "12", "不限"};
        private String[] pailiang = {"0", "1.0", "2.0", "3.0", "4.0", "不限"};
        private String[] all = {};

        public RightAdapter(Context mContext, List<String> leftStr, List<List<String>> rightStr) {
            this.mContext = mContext;
            this.leftStr = leftStr;
            this.rightStr = rightStr;
            //系统服务
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getItemViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int section, int position) {
            if (section == 0) {

                return TYPE_ImageAndText;
            } else if (section == 2 || section == 3 || section == 4) {
                return TYPE_SeekBar;
            } else {
                return TYPE_Price_Text;
            }
        }

        @Override
        public Object getItem(int section, int position) {
            return rightStr.get(section).get(position);
        }

        @Override
        public long getItemId(int section, int position) {
            return position;
        }

        @Override
        public int getSectionCount() {
            return leftStr.size();
        }

        @Override
        public int getCountForSection(int section) {
            return rightStr.get(section).size();
        }

        @Override
        public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
            int itemViewType = getItemViewType(section, position);
            GridViewHolder holder = null;
            if (convertView == null) {
                switch (itemViewType) {
                    case TYPE_Price_Text:
                        //加载
                        convertView = inflater.inflate(R.layout.shaixuan_item_gridview, parent, false);
                        holder = new GridViewHolder(convertView);
                        //绑定
                        convertView.setTag(holder);
                        //设置内容
                        break;
                    case TYPE_ImageAndText:
                        //加载
                        convertView = inflater.inflate(R.layout.shaixuan_item_gridview, parent, false);
                        holder = new GridViewHolder(convertView);
                        //绑定
                        convertView.setTag(holder);
                        //设置内容

                        break;
                    case TYPE_SeekBar:
                        //加载
                        convertView = inflater.inflate(R.layout.seek_bar_layout, parent, false);
                        holder = new GridViewHolder(convertView);
                        //绑定
                        convertView.setTag(holder);
                        //设置内容
                        break;
                }

            } else {
                switch (itemViewType) {

                    case TYPE_Price_Text:
                        holder = (GridViewHolder) convertView.getTag();
                        break;
                    case TYPE_ImageAndText:
                        holder = (GridViewHolder) convertView.getTag();
                        break;
                    case TYPE_SeekBar:
                        holder = (GridViewHolder) convertView.getTag();
                        break;
                    default:

                        break;
                }
            }
            if (holder != null) {
                switch (itemViewType) {
                    case TYPE_Price_Text:
                        if (section == 1) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("变速箱"));
                        } else if (section == 5) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("排放标准"));
                        } else if (section == 6) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("颜色"));
                        } else if (section == 7) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("燃料类型"));
                        } else if (section == 8) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("产地"));
                        } else if (section == 9) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("座位数"));
                        } else if (section == 10) {
                            holder.gv_shaixuan.setAdapter(new GridItemContentAdapter("国别"));
                        }
                        break;
                    case TYPE_ImageAndText:
                        holder.gv_shaixuan.setAdapter(new GridImageViewItemContentAdapter());
                        break;
                    case TYPE_SeekBar:
                        if (section == 2) {
                            final List<UsedCarShaiXuanBean> cheling_list = dbUsedCarManager.findTypeList("车龄");
                            holder.rangeSeekbar.setTextMarks("0", "1", "2", "3", "4", "5", "不限");
                            String name = cheling_list.get(0).getName();
                            LogUtil.e("车龄name", name);
                            final String[] state = {cheling_list.get(0).getState()};
                            LogUtil.e("车龄state", state[0]);
                            if (!TextUtils.isEmpty(name)) {
                                holder.rangeSeekbar.setLeftSelection(Integer.valueOf(name));
                            }
                            if (!TextUtils.isEmpty(state[0])) {
                                if ("A".equals(state[0])) {
                                    holder.rangeSeekbar.setRightSelection(6);
                                } else {
                                    holder.rangeSeekbar.setRightSelection(Integer.valueOf(state[0]));
                                }
                            }

                            all = cheling;
                            for (int i = 0; i < all.length; i++) {
                                TextView imageView = new TextView(App.context);
                                imageView.setTextColor(getResources().getColor(R.color.test_color));
                                imageView.setTextSize(15.0f);
                                int i1 = width / all.length;
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(i1, 50);
                                if (i == cheling.length - 1) {
                                    params.rightMargin = 12;
                                }
                                imageView.setLayoutParams(params);
                                imageView.setText(all[i]);
                                holder.ll_heng.addView(imageView);
                            }


                            final String[] left = new String[1];
                            final String[] right = new String[1];

                            holder.rangeSeekbar.setOnCursorChangeListener(new RangeSeekbar.OnCursorChangeListener() {
                                @Override
                                public void onLeftCursorChanged(int location, String textMark) {
                                    Log.e("车龄左面选择", textMark);
                                    String name = cheling_list.get(0).getName();
                                    String state = cheling_list.get(0).getState();
                                    left[0] = textMark;
                                    LogUtil.e("车龄state左边选择时候的右边的值", TextUtils.isEmpty(right[0]) ? state : right[0]);
                                    dbUsedCarManager.changeFanWei("车龄", TextUtils.isEmpty(left[0]) ? name : left[0], TextUtils.isEmpty(right[0]) ? state : right[0]);
                                    getUsedCarNumber();
                                }

                                @Override
                                public void onRightCursorChanged(int location, String textMark) {
                                    Log.e("车龄右面选择", textMark);
                                    String name = cheling_list.get(0).getName();
                                    right[0] = textMark;
                                    LogUtil.e("车龄state右边选择时候", TextUtils.isEmpty(left[0]) ? name : left[0]);
                                    dbUsedCarManager.changeFanWei("车龄", TextUtils.isEmpty(left[0]) ? name : left[0], textMark.equals("不限") ? "A" : right[0]);
                                    getUsedCarNumber();
                                }
                            });

                        } else if (section == 3) {
                            holder.rangeSeekbar.setTextMarks("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "不限");
                            final List<UsedCarShaiXuanBean> licheng_list = dbUsedCarManager.findTypeList("里程");
                            String name = licheng_list.get(0).getName();
                            LogUtil.e("里程name", name);
                            String state = licheng_list.get(0).getState();
                            LogUtil.e("里程state", state);
                            if (!TextUtils.isEmpty(name)) {
                                holder.rangeSeekbar.setLeftSelection(Integer.valueOf(name));
                            }
                            if (!TextUtils.isEmpty(state)) {
                                if ("A".equals(state)) {
                                    holder.rangeSeekbar.setRightSelection(15);
                                } else {
                                    holder.rangeSeekbar.setRightSelection(Integer.valueOf(state));
                                }
                            }
                            all = licheng;
                            commonArray(holder);
                            final String[] left = new String[1];
                            final String[] right = new String[1];
                            holder.rangeSeekbar.setOnCursorChangeListener(new RangeSeekbar.OnCursorChangeListener() {
                                @Override
                                public void onLeftCursorChanged(int location, String textMark) {
                                    Log.e("里程左面选择", textMark);
                                    String name = licheng_list.get(0).getName();
                                    String state = licheng_list.get(0).getState();
                                    left[0] = textMark;
                                    LogUtil.e("里程state左边选择时候的右边的值", TextUtils.isEmpty(right[0]) ? state : right[0]);
                                    dbUsedCarManager.changeFanWei("里程", TextUtils.isEmpty(left[0]) ? name : left[0], TextUtils.isEmpty(right[0]) ? state : right[0]);
                                    getUsedCarNumber();
                                }

                                @Override
                                public void onRightCursorChanged(int location, String textMark) {
                                    Log.e("里程右面选择", textMark);
                                    String name = licheng_list.get(0).getName();

                                    right[0] = textMark;
                                    LogUtil.e("里程state右边选择时候", TextUtils.isEmpty(left[0]) ? name : left[0]);
                                    dbUsedCarManager.changeFanWei("里程", TextUtils.isEmpty(left[0]) ? name : left[0], textMark.equals("不限") ? "A" : right[0]);
                                    getUsedCarNumber();
                                }
                            });
                        } else if (section == 4) {
                            holder.rangeSeekbar.setTextMarks("0", "1", "2", "3", "4", "不限");
                            final List<UsedCarShaiXuanBean> pailiang_list = dbUsedCarManager.findTypeList("排量");
                            String name = pailiang_list.get(0).getName();
                            LogUtil.e("排量name", name);
                            String state = pailiang_list.get(0).getState();
                            LogUtil.e("排量state", state);
                            if (!TextUtils.isEmpty(name)) {
                                holder.rangeSeekbar.setLeftSelection(Integer.valueOf(name));
                            }
                            if (!TextUtils.isEmpty(state)) {
                                if ("A".equals(state)) {
                                    holder.rangeSeekbar.setRightSelection(5);
                                } else {
                                    holder.rangeSeekbar.setRightSelection(Integer.valueOf(state));
                                }
                            }
                            all = pailiang;
                            commonArray(holder);
                            final String[] left = new String[1];
                            final String[] right = new String[1];
                            holder.rangeSeekbar.setOnCursorChangeListener(new RangeSeekbar.OnCursorChangeListener() {
                                @Override
                                public void onLeftCursorChanged(int location, String textMark) {
                                    Log.e("排量左面的选择", textMark);
                                    String name = pailiang_list.get(0).getName();
                                    String state = pailiang_list.get(0).getState();
                                    left[0] = textMark;
                                    LogUtil.e("排量state左边选择时候的右边的值", TextUtils.isEmpty(right[0]) ? state : right[0]);
                                    dbUsedCarManager.changeFanWei("排量", TextUtils.isEmpty(left[0]) ? name : left[0], TextUtils.isEmpty(right[0]) ? state : right[0]);
                                    getUsedCarNumber();
                                }

                                @Override
                                public void onRightCursorChanged(int location, String textMark) {
                                    Log.e("排量右面的选择", textMark);
                                    String name = pailiang_list.get(0).getName();
                                    right[0] = textMark;
                                    LogUtil.e("排量state右边选择时候的左边的值", TextUtils.isEmpty(left[0]) ? name : left[0]);
                                    dbUsedCarManager.changeFanWei("排量", TextUtils.isEmpty(left[0]) ? name : left[0], textMark.equals("不限") ? "A" : right[0]);
                                    getUsedCarNumber();
                                }
                            });
                        }

                        break;
                }
            }
            return convertView;
        }

        private void commonArray(GridViewHolder holder) {
            for (int i = 0; i < all.length; i++) {
                TextView imageView = new TextView(App.context);
                imageView.setTextColor(getResources().getColor(R.color.test_color));
                imageView.setTextSize(15.0f);
                int i1 = width / all.length;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(i1, 50);
                imageView.setLayoutParams(params);
                imageView.setText(all[i]);
                holder.ll_heng.addView(imageView);
            }
        }

        @Override
        public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
            HeaderViewHolder holder = null;
            if (convertView == null) {
                holder = new HeaderViewHolder();
                //加载
                convertView = inflater.inflate(R.layout.lv_customize_item_header, parent, false);
                //绑定
                holder.lv_customize_item_header_text = (TextView) convertView.findViewById(R.id.lv_customize_item_header_text);
                convertView.setTag(holder);
            } else {
                holder = (HeaderViewHolder) convertView.getTag();
            }
            //不可点击
            convertView.setClickable(false);
            //设置标题

            holder.lv_customize_item_header_text.setText(leftStr.get(section));
            return convertView;
        }

        class GridViewHolder {
            private GridView gv_shaixuan;
            private RangeSeekbar rangeSeekbar;
            private LinearLayout ll_heng;

            public GridViewHolder(View v) {
                gv_shaixuan = (GridView) v.findViewById(R.id.gv_shaixuan);
                rangeSeekbar = (RangeSeekbar) v.findViewById(R.id.seekbar);
                ll_heng = (LinearLayout) v.findViewById(R.id.ll_heng);
            }
        }

        class HeaderViewHolder {
            //标题
            private TextView lv_customize_item_header_text;
        }

        private class GridItemContentAdapter extends BaseAdapter {
            private String type;
            private List<UsedCarShaiXuanBean> typeList = new ArrayList<>();

            public GridItemContentAdapter(String type) {
                typeList = dbUsedCarManager.findTypeList(type);
                this.type = type;
            }

            @Override
            public int getCount() {
                if (typeList != null) {
                    return typeList.size();
                } else {
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
                SearchViewHolder gridViewHolder = null;
                if (view == null) {
                    view = LayoutInflater.from(App.context).inflate(R.layout.used_shaixuan_gv_item, null);
                    gridViewHolder = new SearchViewHolder(view);
                    view.setTag(gridViewHolder);
                } else {
                    gridViewHolder = (SearchViewHolder) view.getTag();
                }
                if (gridViewHolder != null) {
                    typeList = dbUsedCarManager.findTypeList(type);
                    UsedCarShaiXuanBean usedCarShaiXuanBean = typeList.get(i);
                    if (usedCarShaiXuanBean != null) {
                        final String name = usedCarShaiXuanBean.getName();
                        gridViewHolder.bt_gv_content.setText(name);
                        String state = usedCarShaiXuanBean.getState();
                        if ("0".equals(state)) {
                            //未选择
                            gridViewHolder.bt_gv_content.setChecked(false);
                        } else {
                            gridViewHolder.bt_gv_content.setChecked(true);
                        }
                        final SearchViewHolder finalGridViewHolder = gridViewHolder;
                        gridViewHolder.bt_gv_content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boolean checked = finalGridViewHolder.bt_gv_content.isChecked();
                                if (checked) {
                                    dbUsedCarManager.changeState(name, "1");
                                } else {
                                    dbUsedCarManager.changeState(name, "0");
                                }
                                getUsedCarNumber();
                            }
                        });

                    }
                }
                return view;
            }
        }

        private class GridImageViewItemContentAdapter extends BaseAdapter {
            private int[] logo = {R.mipmap.usedcar_sanxiang, R.mipmap.usedcar_suv, R.mipmap.usedcar_jiaoche, R.mipmap.usedcar_mpv, R.mipmap.usedcar_paoche
                    , R.mipmap.usedcar_mianbaoche, R.mipmap.usedcar_pika};

            @Override
            public int getCount() {
                return logo.length;
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
                SearchViewHolder gridViewHolder = null;
                if (view == null) {
                    view = LayoutInflater.from(App.context).inflate(R.layout.used_shaixuan_gv_image_item, null);
                    gridViewHolder = new SearchViewHolder(view);
                    view.setTag(gridViewHolder);
                } else {
                    gridViewHolder = (SearchViewHolder) view.getTag();
                }
                if (gridViewHolder != null) {
                    List<UsedCarShaiXuanBean> chexing = dbUsedCarManager.findTypeList("车型");
                    UsedCarShaiXuanBean usedCarShaiXuanBean = chexing.get(i);
                    if (usedCarShaiXuanBean != null) {
                        final String name = usedCarShaiXuanBean.getName();
                        String state = usedCarShaiXuanBean.getState();
                        gridViewHolder.rb_chexing_content.setText(name);
                        Drawable drawable = mContext.getResources().getDrawable(logo[i]);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        gridViewHolder.rb_chexing_content.setCompoundDrawables(null, drawable, null, null);
                        if ("1".equals(state)) {
                            gridViewHolder.rb_chexing_content.setChecked(true);
                        } else {
                            gridViewHolder.rb_chexing_content.setChecked(false);
                        }
                        final SearchViewHolder finalGridViewHolder = gridViewHolder;
                        gridViewHolder.rb_chexing_content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                boolean checked = finalGridViewHolder.rb_chexing_content.isChecked();
                                if (checked) {
                                    dbUsedCarManager.changeState(name, "1");
                                } else {
                                    dbUsedCarManager.changeState(name, "0");
                                }
                                getUsedCarNumber();
                            }
                        });
                    }
                }


                return view;
            }
        }


        class SearchViewHolder {
            private CheckBox bt_gv_content, rb_chexing_content;

            public SearchViewHolder(View view) {
                bt_gv_content = (CheckBox) view.findViewById(R.id.bt_gv_content);
                rb_chexing_content = (CheckBox) view.findViewById(R.id.rb_chexing_content);
            }
        }
    }

    private void getUsedCarNumber() {
        if (dbUsedCarManager != null) {
            String[] titile = {"车龄", "里程", "排量", "车型", "变速箱", "排放标准", "颜色", "燃料类型", "产地", "座位数", "国别"};
            List<String> shaixuanList = new ArrayList<>();
            for (int i = 0; i < titile.length; i++) {
                List<UsedCarShaiXuanBean> typeList = dbUsedCarManager.findTypeList(titile[i]);
                String s = "";
                if (typeList != null && typeList.size() > 0) {
                    if (i > 3) {
                        for (int j = 0; j < typeList.size(); j++) {
                            UsedCarShaiXuanBean usedCarShaiXuanBean = typeList.get(j);
                            String state = usedCarShaiXuanBean.getState();
                            String name = usedCarShaiXuanBean.getName();
                            if ("1".equals(state)) {
                                s += name + ",";
                            }
                        }
                        if (s.length() > 0) {
                            s = s.substring(0, s.length() - 1);
                        }
                        shaixuanList.add(s);
                    } else {
                        UsedCarShaiXuanBean usedCarShaiXuanBean = typeList.get(0);
                        String state = usedCarShaiXuanBean.getState();
                        String name = usedCarShaiXuanBean.getName();
                        s = name + "-" + state;
                        shaixuanList.add(s);
                    }
                }
            }
            if (shaixuanList.size() == 11) {
                String cheling = shaixuanList.get(0);
                String licheng = shaixuanList.get(1);
                String pailiang = shaixuanList.get(2);
                String chexing = shaixuanList.get(3);
                String biansu = shaixuanList.get(4);
                String paifang = shaixuanList.get(5);
                String yanse = shaixuanList.get(6);
                String ranliao = shaixuanList.get(7);
                String chandi = shaixuanList.get(8);
                String zuowei = shaixuanList.get(9);
                String guobie = shaixuanList.get(10);
                if (XueYiCheUtils.IsHaveInternet(this)) {
                    showProgressDialog(false);
                    OkHttpUtils.post().url(url)
                            .addParams("device_id", LoginUtils.getId(this))
                            .addParams("car_age", cheling)
                            .addParams("mileage", licheng)
                            .addParams("displacement", pailiang)
                            .addParams("level_name", chexing)
                            .addParams("transmission_case", biansu)
                            .addParams("emission_standard", paifang)
                            .addParams("color", yanse)
                            .addParams("fuel_type", ranliao)
                            .addParams("production_address", chandi)
                            .addParams("pedestal", zuowei)
                            .addParams("different_countries", guobie)
                            .addParams("take_city", qu_city)
                            .addParams("still_city", huan_city)
                            .addParams("merchant_id", store_id)
                            .addParams("take_status", shangMenQu)
                            .addParams("still_status", shangMenHuan)
                            .addParams("rent_start_time", qu_time_date)
                            .addParams("rent_end_time", huan_time_date)
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws IOException {
                            String string = response.body().string();
                            if (!TextUtils.isEmpty(string)) {
                                final ShaiXuanNumberBean shaiXuanNumberBean = JsonUtil.parseJsonToBean(string, ShaiXuanNumberBean.class);
                                if (shaiXuanNumberBean != null) {
                                    App.handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int data = shaiXuanNumberBean.getData();
                                            tv_usedcar_number.setText(data + "");
                                        }
                                    });

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
                }
            }
        }
    }
}
