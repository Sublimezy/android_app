package com.xueyiche.zjyk.xueyiche.carlife.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.carlife.CarLifeContentActivity;
import com.xueyiche.zjyk.xueyiche.carlife.CarLifeSearchActivity;
import com.xueyiche.zjyk.xueyiche.carlife.adapter.ErJiCaiDanTwoAdapter;
import com.xueyiche.zjyk.xueyiche.carlife.adapter.ListAdapter;
import com.xueyiche.zjyk.xueyiche.carlife.adapter.YiJiCaiDanTwoAdapter;
import com.xueyiche.zjyk.xueyiche.carlife.bean.CarLifeListBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.SelectRegionsListBean;
import com.xueyiche.zjyk.xueyiche.carlife.bean.StreetListBean;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.bean.ListShaiXuanBean;
import com.xueyiche.zjyk.xueyiche.driverschool.view.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.bean.KaiTongCityBean;
import com.xueyiche.zjyk.xueyiche.homepage.adapters.YiJiCaiDanAdapter;
import com.xueyiche.zjyk.xueyiche.mine.activities.message.KeFuActivity;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.splash.ToastUtil;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YJF on 2020/2/20.
 */
public class XiCheFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    private RadioButton ll_choice_paixu, ll_choice_type, ll_choice_diqu;
    private RadioGroup ll_shaixuantiaojian;
    private RefreshLayout refreshLayout;
    private RecyclerView lv_content;
    private ListShaiXuanBean listShaiXuanBean = new ListShaiXuanBean();
    private String longitude;
    private String latitude;
    private int pager = 1;
    private List<String> city, paixu, type;
    private ListView yijicaidanListView;
    private YiJiCaiDanAdapter yijicaidanAdapter;
    private boolean isOpenPop;
    private PopupWindow popupWindow;
    private ListAdapter listAdapter;
    private ImageView iv_kefu;
    private RelativeLayout rl_title_search;
    private YiJiCaiDanTwoAdapter yiJiCaiDanTwoAdapter;
    private ListView erjicaidan;
    private ErJiCaiDanTwoAdapter erJiCaiDanTwoAdapter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.xiche_fragment, null);
        ll_choice_paixu = (RadioButton) view.findViewById(R.id.ll_choice_paixu);
        ll_choice_diqu = (RadioButton) view.findViewById(R.id.ll_choice_diqu);
        ll_choice_type = (RadioButton) view.findViewById(R.id.ll_choice_type);
        ll_shaixuantiaojian = (RadioGroup) view.findViewById(R.id.ll_shaixuantiaojian);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        iv_kefu = (ImageView) view.findViewById(R.id.iv_kefu);
        rl_title_search = (RelativeLayout) view.findViewById(R.id.rl_title_search);
        ll_choice_diqu.setOnClickListener(this);
        ll_choice_paixu.setOnClickListener(this);
        ll_choice_type.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        iv_kefu.setOnClickListener(this);
        rl_title_search.setOnClickListener(this);
        lv_content = (RecyclerView) view.findViewById(R.id.recycler_homeyouhui_content);
        lv_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        String area_id = PrefUtils.getString(App.context, "area_id", "");
        listShaiXuanBean.setArea_id(area_id);
        listShaiXuanBean.setSearch_name("标准洗车");
        listShaiXuanBean.setService_id("1");
        listAdapter = new ListAdapter(R.layout.xiche_list_item);
        lv_content.setAdapter(listAdapter);
        initData();
        listener();
        return view;
    }

    private void listener() {
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String goods_id = "";
                CarLifeListBean.ContentBean contentBean = (CarLifeListBean.ContentBean) adapter.getItem(position);
                Intent intent = new Intent(App.context, CarLifeContentActivity.class);
                if (contentBean.getGood_list() != null && contentBean.getGood_list().size() > 0) {
                    goods_id = "" + (contentBean.getGood_list().get(0).getGoods_id());
                } else {
                    goods_id = "";
                }
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("shop_id", "" + contentBean.getShop_id());
                intent.putExtra("service_id", "1");
                startActivity(intent);
            }
        });
    }

    private void initData() {
        paixu = new ArrayList<String>();
        city = new ArrayList<String>();
        type = new ArrayList<String>();
        city.add("不限");
        paixu.add("默认排序");
        paixu.add("距离最近");
        paixu.add("人气优先");
        paixu.add("好评优先");
        type.add("洗车");
        type.add("维修保养");
        type.add("4S店");
        type.add("轮胎服务");
        type.add("汽车配饰");
        type.add("KTV");
        type.add("美食");
        type.add("金融保险");
        longitude = PrefUtils.getString(App.context, "x", "0");
        latitude = PrefUtils.getString(App.context, "y", "0");
        listShaiXuanBean.setLuli("");
        getDataFromNet();
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_choice_diqu:
                changPopState(ll_choice_diqu, city);
                break;
            case R.id.ll_choice_paixu:
                changPopState(ll_choice_paixu, paixu);
                break;
            case R.id.ll_choice_type:
                changPopState(ll_choice_type, type);
                break;
            case R.id.iv_kefu:
                openActivity(KeFuActivity.class);
                break;
            case R.id.rl_title_search:
                Intent intent = new Intent(App.context, CarLifeSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void changPopState(View v, List<String> yiji) {
        isOpenPop = !isOpenPop;
        if (isOpenPop) {
            showBottomMenu(v, yiji);
        } else {
            dismissBottomMenu();
        }
    }

    private void showBottomMenu(View v, List<String> yiji) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.caidan_listview, null);
        yijicaidanListView = (ListView) view.findViewById(R.id.yijicandan);
        erjicaidan = (ListView) view.findViewById(R.id.erjicaidan);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        yijicaidanAdapter = new YiJiCaiDanAdapter(getActivity(), yiji);

        //区域街道的筛选
        if (v == ll_choice_diqu) {
            erjicaidan.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
            map.put("area_id", PrefUtils.getParameter("area_id"));
            MyHttpUtils.postHttpMessage(AppUrl.selectRegionsByAreaId2, map, SelectRegionsListBean.class, new RequestCallBack<SelectRegionsListBean>() {
                @Override
                public void requestSuccess(SelectRegionsListBean json) {
                    if (200 == json.getCode()) {
                        List<SelectRegionsListBean.ContentBean> content = json.getContent();
                        if (content!=null&&content.size()>0) {
                            yiJiCaiDanTwoAdapter = new YiJiCaiDanTwoAdapter(getContext(),content);
                            yijicaidanListView.setAdapter(yiJiCaiDanTwoAdapter);
                            int click_area = PrefUtils.getInt(App.context, "click_area", 0);
                            yiJiCaiDanTwoAdapter.setSelectedPosition(click_area);
                            Map<String,String> stringMap = new HashMap<>();
                            stringMap.put("open_area_id",""+content.get(click_area).getOpen_area_id());
                            getStreet(stringMap);
                            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    yiJiCaiDanTwoAdapter.setSelectedPosition(position);
                                    PrefUtils.putInt(App.context,"click_area",position);
                                    yiJiCaiDanTwoAdapter.notifyDataSetChanged();
                                    Map<String,String> stringMap11 = new HashMap<>();
                                    stringMap11.put("open_area_id",""+content.get(position).getOpen_area_id());
                                    getStreet(stringMap11);
                                }
                            });
                        }
                    }

                }

                @Override
                public void requestError(String errorMsg, int errorType) {

                }
            });

        }
        //正常排序
        if (v == ll_choice_paixu) {
            yijicaidanListView.setAdapter(yijicaidanAdapter);
            int click_paixu = PrefUtils.getInt(App.context, "click_paixu", 0);
            yijicaidanAdapter.setSelectedPosition(click_paixu);
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ll_choice_paixu.setText(paixu.get(position));
                    yijicaidanAdapter.setSelectedPosition(position);
                    PrefUtils.putInt(App.context,"click_paixu",position);
                    TextView cheliang_leixing = (TextView) view.findViewById(R.id.cheliang_leixing);
                    cheliang_leixing.setTextColor(Color.parseColor("#ff5000"));
                    dismissBottomMenu();
                    listShaiXuanBean.setSort_method(position + "");
                    getDataFromNet();
                    ll_shaixuantiaojian.clearCheck();
                }
            });
        }
        //类型
        if (v == ll_choice_type) {
            yijicaidanListView.setAdapter(yijicaidanAdapter);
            int click_type = PrefUtils.getInt(App.context, "click_type", 0);
            yijicaidanAdapter.setSelectedPosition(click_type);
            yijicaidanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ll_choice_type.setText(type.get(position));
                    yijicaidanAdapter.setSelectedPosition(position);
                    yijicaidanAdapter.notifyDataSetChanged();
                    PrefUtils.putInt(App.context,"click_type",position);
                    switch (position) {
                        case 0:
                            listShaiXuanBean.setSearch_name("标准洗车");
                            listShaiXuanBean.setService_id("1");
                            break;
                        case 1:
                            listShaiXuanBean.setSearch_name("维修保养");
                            listShaiXuanBean.setService_id("2");
                            break;
                        case 2:
                            listShaiXuanBean.setSearch_name("4S店");
                            listShaiXuanBean.setService_id("31");
                            break;
                        case 3:
                            listShaiXuanBean.setSearch_name("轮胎服务");
                            listShaiXuanBean.setService_id("25");
                            break;
                        case 4:
                            listShaiXuanBean.setSearch_name("精品用品");
                            listShaiXuanBean.setService_id("26");
                            break;
                        case 5:
                            listShaiXuanBean.setSearch_name("KTV");
                            listShaiXuanBean.setService_id("10");
                            break;
                        case 6:
                            listShaiXuanBean.setSearch_name("美食");
                            listShaiXuanBean.setService_id("9");
                            break;
                        case 7:
                            ToastUtil.show("金融保险");
                            break;
                    }
                    dismissBottomMenu();
                    getDataFromNet();
                    ll_shaixuantiaojian.clearCheck();
                }
            });
        }
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ll_shaixuantiaojian.clearCheck();
                isOpenPop = false;
            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismissBottomMenu();
                    return true;
                }
                return false;
            }
        });
    }

    private void getStreet(Map<String, String> stringMap) {
        MyHttpUtils.postHttpMessage(AppUrl.selectRegionsByAreaIdStreet, stringMap, StreetListBean.class, new RequestCallBack<StreetListBean>() {
            @Override
            public void requestSuccess(StreetListBean json) {
                if (200==json.getCode()) {
                    List<StreetListBean.ContentBean> content = json.getContent();
                    if (content!=null&&content.size()>0) {

                        erJiCaiDanTwoAdapter = new ErJiCaiDanTwoAdapter(getActivity(),content);
                        erjicaidan.setAdapter(erJiCaiDanTwoAdapter);
                        int click_street = PrefUtils.getInt(App.context, "click_street", 0);
                        erJiCaiDanTwoAdapter.setSelectedPosition(click_street);
                        erjicaidan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                dismissBottomMenu();

                                erJiCaiDanTwoAdapter.setSelectedPosition(position);
                                erJiCaiDanTwoAdapter.notifyDataSetChanged();
                                PrefUtils.putInt(App.context,"click_street",position);
                                listShaiXuanBean.setLuli(""+content.get(position).getStreet_name());
                                ll_choice_diqu.setText(""+content.get(position).getStreet_name());
                                getDataFromNet();
                                ll_shaixuantiaojian.clearCheck();
                            }
                        });
                    }
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {

            }
        });
    }

    private void dismissBottomMenu() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();

        }
    }

    private void getDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getActivity())) {
            String user_id = PrefUtils.getString(App.context, "user_id", "");
            showProgressDialog(getActivity(), false);
            Map<String, String> map = new HashMap<>();
            map.put("pager", pager + "");
            map.put("device_id", LoginUtils.getId(getActivity()));
            map.put("longitude_user", longitude);
            map.put("latitude_user", latitude);
//            map.put("area_id", listShaiXuanBean.getArea_id());
            map.put("sort_method", listShaiXuanBean.getSort_method());
            map.put("user_id", user_id);
            map.put("juli", listShaiXuanBean.getLuli());
            map.put("service_id", listShaiXuanBean.getService_id());
//            map.put("search_name", listShaiXuanBean.getSearch_name());
            MyHttpUtils.postHttpMessage(AppUrl.Carlife_Goods, map, CarLifeListBean.class, new RequestCallBack<CarLifeListBean>() {
                @Override
                public void requestSuccess(CarLifeListBean json) {
                    stopProgressDialog();
                    if (200 == json.getCode()) {
                        if (pager == 1) {
                            if (json.getContent().size() > 0) {
                                listAdapter.setNewData(json.getContent());
                            } else {
                                listAdapter.getData().clear();
                                listAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
                                listAdapter.notifyDataSetChanged();
                            }
                        } else {
                            listAdapter.addData(json.getContent());
                        }
                    }
                }

                @Override
                public void requestError(String errorMsg, int errorType) {
                    stopProgressDialog();
                }
            });
        }


    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pager = 1;
        getDataFromNet();
        refreshLayout.finishRefresh(1000);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pager++;
        getDataFromNet();
        refreshLayout.finishLoadMore(1000);

    }
}
