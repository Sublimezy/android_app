package com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.activity.EndActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JieDanActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.JinXingActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.LiYouActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitActivity;
import com.xueyiche.zjyk.xueyiche.daijia.activity.WaitYuYueActivity;
import com.xueyiche.zjyk.xueyiche.discover.bean.SuccessDisCoverBackBean;
import com.xueyiche.zjyk.xueyiche.homepage.view.MapSelectDialog;
import com.xueyiche.zjyk.xueyiche.pay.AppPay;
import com.xueyiche.zjyk.xueyiche.receive.NetBroadcastReceiver;
import com.xueyiche.zjyk.xueyiche.submit.PracticeCarSubmitIndent;
import com.xueyiche.zjyk.xueyiche.submit.bean.DeleteIndentBean;
import com.xueyiche.zjyk.xueyiche.utils.AES;
import com.xueyiche.zjyk.xueyiche.utils.BaiduLocation;
import com.xueyiche.zjyk.xueyiche.utils.DialogUtils;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.LogUtil;
import com.xueyiche.zjyk.xueyiche.utils.LoginUtils;
import com.xueyiche.zjyk.xueyiche.utils.NetUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.DingDanBean;
import com.xueyiche.zjyk.xueyiche.xycindent.bean.PracticeWeiYueBean;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.CarLiveDetails;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.DriverSchoolTrainerListActivity;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.IndentDetailsOrderPractice;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.IndentDriverSchoolDetails;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.PingJia;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.PingJiaDriverSchool;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.PutStudentCardActivity;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.UsedCarIndentDetails;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent_content.WZIndentDetailsOrderPractice;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zhanglei on 2016/11/16.
 */
public class AllIndentFragment extends BaseFragment implements NetBroadcastReceiver.netEventHandler {
    private ListView lv_myindent;
    private List<DingDanBean.ContentBean> content;
    private List<DingDanBean.ContentBean> list = new ArrayList<>();
    private LinearLayout ll_not_indent;
    private NotificationManager notificationManager;
    private int pager = 1;
    private AllIndentAdapter allIndentAdapter;
    private String user_name;
    private View layout;
    private TextView ed_order_name;
    private TextView ed_order_phone;
    private EditText ed_card_number;
    private String user_id;
    private String order_status;
    private boolean isPrepared;
    private RefreshLayout refreshLayout;
    private String remark;

    public AllIndentFragment(String i) {
        this.order_status = i;
    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.all_indent, null);
        isPrepared = true;
        lv_myindent = (ListView) view.findViewById(R.id.lv_myindent);
        ll_not_indent = (LinearLayout) view.findViewById(R.id.ll_not_indent);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        notificationManager = (NotificationManager) App.context.getSystemService(Context.NOTIFICATION_SERVICE);
        NetBroadcastReceiver.mListeners.add(this);
        allIndentAdapter = new AllIndentAdapter(list, getContext());
        initData();
        lazyLoad();
        return view;
    }

    private void initData() {
        user_id = PrefUtils.getString(App.context, "user_id", "");
        user_name = PrefUtils.getString(App.context, "user_name", "");
        getDataFromNet();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (XueYiCheUtils.IsHaveInternet(App.context)) {
                            pager = 1;
                            content = null;
                            getDataFromNet();
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.setNoMoreData(false);
                            Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 1500);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                @Override
                                                public void onLoadMore(final RefreshLayout refreshLayout) {
                                                    refreshLayout.getLayout().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (XueYiCheUtils.IsHaveInternet(App.context)) {
                                                                if (content != null && content.size() == 0) {
                                                                    showToastShort(StringConstants.MEIYOUSHUJU);
                                                                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                                                                } else {
                                                                    pager += 1;
                                                                    getMoreDataFromNet();
                                                                    refreshLayout.finishLoadMore();
                                                                }
                                                            } else {
                                                                refreshLayout.finishLoadMore();
                                                                Toast.makeText(App.context, "请检查网络连接", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }, 1500);
                                                }
                                            }

        );
    }


    public void onEvent(MyEvent event) {

        String msg = event.getMsg();

        if (TextUtils.equals("刷新全部订单", msg)) {
            getDataFromNet();
        }

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getDataFromNet();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getDataFromNet();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }


    private void getDataFromNet() {
        OkHttpUtils.post().url(AppUrl.All_Order)
                .addParams("user_id", user_id)
                .addParams("order_status", order_status)
                .addParams("pager", pager + "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                LogUtil.e("666666666666", string);
                if (!TextUtils.isEmpty(string)) {
                    processData(string, false);
                }
                return string;
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(Object response) {
            }
        });

        OkHttpUtils.post().url(AppUrl.WeiYue_GuiZe)
                .addParams("device_id", LoginUtils.getId(getActivity()))
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws IOException {
                String string = response.body().string();
                if (!TextUtils.isEmpty(string)) {
                    PracticeWeiYueBean practiceWeiYueBean = JsonUtil.parseJsonToBean(string, PracticeWeiYueBean.class);
                    if (practiceWeiYueBean != null) {
                        PracticeWeiYueBean.ContentBean content = practiceWeiYueBean.getContent();
                        if (content != null) {
                            remark = content.getRemark();
                        }
                    }
                }
                return string;
            }

            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(Object response) {
            }
        });
    }


    private void getMoreDataFromNet() {
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            showProgressDialog(getActivity(), false);
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            if (!"".equals(user_phone)) {
                OkHttpUtils.post().url(AppUrl.All_Order)
                        .addParams("user_id", user_id)
                        .addParams("order_status", order_status)
                        .addParams("pager", pager + "")
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            processData(string, true);
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


    private void processData(String string, final boolean isMore) {
        final DingDanBean dingDanBean = JsonUtil.parseJsonToBean(string, DingDanBean.class);
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (dingDanBean != null) {
                    if (!isMore) {
                        if (list.size() != 0) {
                            list.clear();
                        }
                        List<DingDanBean.ContentBean> content = dingDanBean.getContent();
                        if (content != null && content.size() != 0) {
                            list.addAll(content);
                            lv_myindent.setAdapter(allIndentAdapter);
                            ll_not_indent.setVisibility(View.GONE);
                        } else {
                            ll_not_indent.setVisibility(View.VISIBLE);
                        }
                        allIndentAdapter.notifyDataSetChanged();
                    } else {
                        content = dingDanBean.getContent();
                        if (content != null) {
                            list.addAll(content);//追加更多数据
                            allIndentAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetWorkState(App.context) == NetUtil.NETWORN_NONE) {
            showToastShort(StringConstants.CHECK_NET);
            stopProgressDialog();
        } else {
            getDataFromNet();
        }
    }

    public class AllIndentAdapter extends BaseAdapter {
        private List<DingDanBean.ContentBean> date;
        private Context context;
        private final String user_id;

        public AllIndentAdapter(List<DingDanBean.ContentBean> list, Context context) {
            user_id = PrefUtils.getString(context, "user_id", "");
            this.date = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return date.size();
        }

        @Override
        public Object getItem(int position) {
            return date.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(App.context).inflate(R.layout.indent_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final DingDanBean.ContentBean contentBean = date.get(position);
            final String order_number = contentBean.getOrder_number();
            final String refer_name = contentBean.getRefer_name();
            final String ew_code = contentBean.getEw_code();
            //支付价格
            String pay_price = contentBean.getPay_price();
            //服务
            String service_name = contentBean.getService_name();
            //经纬度
            final String latitude = contentBean.getLatitude();
            final String longitude = contentBean.getLongitude();
            //店铺地址名称
            final String shop_place_name = contentBean.getShop_place_name();
            //店铺电话
            final String shop_phone = contentBean.getShop_phone();
            //‘1’学生证或‘2’准考证‘0’不需要
            final String need_photo = contentBean.getNeed_photo();
            //0:代付款1:进行中2:已完成  3 待学员确认  5:待返现6:待接单 7:退款中，8 返现中 9 待练车
            final String order_status = contentBean.getOrder_status();
            String order_system_time = contentBean.getOrder_system_time();
            //0有证练车，1车生活 ，2 驾校 ，3 积分充值  4二手车 5 驾校拼团活动 6无证练车 7直通车
            final String order_type = contentBean.getOrder_type();
            final String order_receiving = contentBean.getOrder_receiving();

            //1：教练点击开始练车；2：结束，默认：0
            final String start_type = contentBean.getStart_type();
            //order_category默认.0：默认普通类型订单，1：开团，2：参团
            if (!TextUtils.isEmpty(order_number)) {
                holder.tv_number_indent.setText(order_number);
            }
            if (!TextUtils.isEmpty(refer_name)) {
                holder.tv_shopname_indent.setText(refer_name);
            }
            if (!TextUtils.isEmpty(pay_price)) {
                holder.tv_money_indent.setText(pay_price);
            }
            if (!TextUtils.isEmpty(service_name)) {
                if (service_name.contains("m,g")) {
                    String replace = service_name.replace("m,g", "\n");
                    holder.tv_service_indent.setText(replace);
                } else {
                    holder.tv_service_indent.setText(service_name);
                }
            }
            if (!TextUtils.isEmpty(order_number)) {
                String substring = order_number.substring(0, 1);
                if ("E".equals(substring)) {
                    holder.iv_indent_erweima.setVisibility(View.VISIBLE);
                } else if ("Z".equals(substring)) {
                    holder.iv_indent_erweima.setVisibility(View.GONE);
                } else if ("D".equals(substring)) {
                    holder.iv_indent_erweima.setVisibility(View.GONE);
                }
            }

            holder.tv_date_indent.setText(order_system_time);
            holder.tv_buttwo_indent.setVisibility(View.GONE);
            holder.tv_butthree_indent.setVisibility(View.VISIBLE);
            holder.tv_butfour_indent.setVisibility(View.VISIBLE);
            holder.iv_indent_erweima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(ew_code)) {
                        DialogUtils.showQuanma((Activity) context, ew_code, "券码 " + ew_code);
                    }
                }
            });
            if ("D".equals(order_type)) {
                //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                if ("0".equals(order_status)) {
                    holder.tv_state_indent.setText("待接单");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setText("取 消");
                }
                if ("1".equals(order_status)) {
                    holder.tv_state_indent.setText("待到达");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    holder.tv_butthree_indent.setText("取 消");
                    holder.tv_butfour_indent.setText("联 系");
                }
                if ("2".equals(order_status)) {
                    holder.tv_state_indent.setText("已到达");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    holder.tv_butthree_indent.setText("取 消");
                    holder.tv_butfour_indent.setText("联 系");
                }
                if ("3".equals(order_status)) {
                    holder.tv_state_indent.setText("行程中");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.INVISIBLE);
                }
                if ("4".equals(order_status)) {
                    holder.tv_state_indent.setText("已完成");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setText("去评论");
                }
                if ("5".equals(order_status)) {
                    holder.tv_state_indent.setText("已评价");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.INVISIBLE);
                }
                if ("6".equals(order_status)) {
                    holder.tv_state_indent.setText("已关闭");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.INVISIBLE);
                }
            } else {
                if ("0".equals(order_status)) {
                    holder.tv_state_indent.setText("待付款");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    holder.tv_butthree_indent.setText("取 消");
                    holder.tv_butfour_indent.setText("支 付");
                }
                if ("1".equals(order_status)) {
                    holder.tv_state_indent.setText("进行中");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    if ("1".equals(order_type)) {
                        //车生活
                        holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                        holder.tv_buttwo_indent.setText("取 消");
                        holder.tv_butthree_indent.setText("导 航");
                    } else if ("0".equals(order_type) || "6".equals(order_type)) {
                        //有证练车
                        holder.tv_state_indent.setText("练车中");
                        holder.tv_butfour_indent.setText("联 系");
                        holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                        if ("0".equals(start_type)) {
                            //未开始练车可以取消
                            holder.tv_butthree_indent.setText("取 消");
                        } else {
                            holder.tv_butthree_indent.setVisibility(View.GONE);
                        }


                    } else if ("2".equals(order_type)) {
                        //驾校
                        //0：未交尾款，1：已交尾款
                        String tail_pay = contentBean.getTail_pay();
                        if ("0".equals(tail_pay)) {
                            holder.tv_buttwo_indent.setText("尾 款");
                            holder.tv_buttwo_indent.setVisibility(View.VISIBLE);
                            holder.tv_butone_indent.setText("退 款");
                            holder.tv_butone_indent.setVisibility(View.INVISIBLE);
                        }else {
                            holder.tv_buttwo_indent.setVisibility(View.GONE);
                            holder.tv_butone_indent.setVisibility(View.GONE);
                        }
                        holder.tv_butthree_indent.setText("导 航");
                        holder.tv_butthree_indent.setVisibility(View.VISIBLE);


                    } else if ("4".equals(order_type)) {
                        //二手车
                        holder.tv_butthree_indent.setText("取 消");
                    }
                    holder.tv_butfour_indent.setText("联 系");
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                }
                if ("2".equals(order_status)) {
                    if ("2".equals(order_type)) {
                        String order_category = contentBean.getOrder_category();
                        if (!"0".equals(order_category)) {
                            holder.tv_buttwo_indent.setVisibility(View.VISIBLE);
                            holder.tv_buttwo_indent.setText("分 享");
                        }
                    }
                    holder.tv_state_indent.setText("已完成");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    if ("0".equals(order_receiving)) {
                        holder.tv_state_indent.setText("已完成");
                        holder.tv_butfour_indent.setText("去评论");
                    } else if ("1".equals(order_receiving)) {
                        holder.tv_state_indent.setText("已评论");
                        holder.tv_butfour_indent.setText("删除");
                        holder.tv_butfour_indent.setVisibility(View.INVISIBLE);
                    }
                }
                if ("3".equals(order_status)) {
                    holder.tv_state_indent.setText("待确认");
                    holder.tv_butthree_indent.setVisibility(View.GONE);
                    holder.tv_butfour_indent.setVisibility(View.GONE);
                }
                if ("5".equals(order_status)) {
                    if ("2".equals(order_type)) {
                        String order_category = contentBean.getOrder_category();
                        if (!"0".equals(order_category)) {
                            holder.tv_buttwo_indent.setVisibility(View.VISIBLE);
                            holder.tv_buttwo_indent.setText("分 享");
                        }
                    }
                    holder.tv_state_indent.setText("待返现");
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setText("返 现");
                }
                if ("6".equals(order_status)) {
                    holder.tv_state_indent.setText("待接单");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setText("取 消");
                }
                if ("7".equals(order_status)) {
                    holder.tv_state_indent.setText("退款中");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.INVISIBLE);
                }
                if ("8".equals(order_status)) {
                    if ("2".equals(order_type)) {
                        String order_category = contentBean.getOrder_category();
                        if (!"0".equals(order_category)) {
                            holder.tv_buttwo_indent.setVisibility(View.VISIBLE);
                            holder.tv_buttwo_indent.setText("分 享");
                        }
                    }
                    holder.tv_state_indent.setText("返现中");
                    holder.tv_buttwo_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butthree_indent.setVisibility(View.INVISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.INVISIBLE);
                }
                if ("9".equals(order_status)) {
                    //待练车
                    holder.tv_state_indent.setText("已接单");
                    holder.tv_butthree_indent.setText("取 消");
                    holder.tv_butfour_indent.setText("联 系");
                    holder.tv_butthree_indent.setVisibility(View.VISIBLE);
                    holder.tv_butfour_indent.setVisibility(View.VISIBLE);
                }
            }

            holder.tv_butone_indent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("1".equals(order_status)) {
                        if ("2".equals(order_type)) {
                            //退100定金
                            goDsTuiQIan();
                        }
                    }
                }
            });
            holder.tv_buttwo_indent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("1".equals(order_status)) {
                        if ("2".equals(order_type)) {
                            //补交尾款
                            String tail_money = contentBean.getTail_money();
                            String order_number2 = contentBean.getOrder_number2();
                            BigDecimal a = new BigDecimal(tail_money);
                            BigDecimal b = new BigDecimal(100);
                            Intent intent = new Intent(App.context, AppPay.class);
                            intent.putExtra("order_number", order_number2);
                            intent.putExtra("subscription",tail_money);
                            intent.putExtra("jifen",  a.multiply(b)+"");
                            intent.putExtra("pay_style", "driver_school_er");
                            startActivity(intent);
                        }
                    }
                }
            });

            holder.tv_butthree_indent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("D".equals(order_type)) {
                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                        if ("1".equals(order_status) || "2".equals(order_status)) {
                            //取消订单
                            quxiaoDJ(order_number, "是否取消订单");
                        }
                    } else {
                        if ("0".equals(order_status)) {
                            //有证练车代付款取消订单
                            if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                                cancelIndentFree(getActivity(), AppUrl.Delete_Indent_Practice, user_id, order_number, "是否取消订单？");
                            }
                        }
                        if ("1".equals(order_status)) {
                            if ("4".equals(order_type)) {
                                //二手车退款取消订单
                                showToastShort("二手车退款取消订单");

                            } else {
                                //导航
                                if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
                                    showToastShort("经纬度异常");
                                    return;
                                }
                                if (TextUtils.isEmpty(shop_place_name)) {
                                    showToastShort("无商店名称");
                                    return;
                                }
                                daoHang(latitude, longitude, shop_place_name);
                            }
                        }
                        if ("9".equals(order_status)) {
                            //有证练车待练车 取消
                            if ("0".equals(order_type)) {
                                //有证练车进行中取消订单赔付违约金
                                if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                                    cancelIndentMoney(getActivity(), order_number);
                                }
                            }

                        }
                    }
                }
            });
            final ViewHolder finalHolder = holder;
            holder.tv_butfour_indent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("D".equals(order_type)) {
                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                        if ("0".equals(order_status)) {
                            //取消订单
                            quxiaoDJ(order_number, "是否取消订单");
                        }
                        if ("1".equals(order_status) || "2".equals(order_status)) {
                            //打电话
                            if (TextUtils.isEmpty(shop_phone)) {
                                showToastShort("电话号码为空");
                                return;
                            }
                            if (shop_phone.length() > 11) {
                                String decrypt = AES.decrypt(shop_phone);
                                XueYiCheUtils.CallPhone(getActivity(), "是否拨打代驾电话", decrypt);
                            } else {
                                XueYiCheUtils.CallPhone(getActivity(), "是否拨打代驾电话", shop_phone);
                            }
                        }
                        if ("4".equals(order_status)) {
                            //去评价
                            Intent intent = new Intent(context, EndActivity.class);
                            intent.putExtra("order_number", order_number);
                            context.startActivity(intent);
                        }
                    } else {
                        if ("0".equals(order_status) && "0".equals(order_type)) {
                            //有证练车代付款去支付
                            Intent intent = new Intent(context, PracticeCarSubmitIndent.class);
                            intent.putExtra("order_number", order_number);
                            intent.putExtra("type", "indent");
                            startActivity(intent);
                        } else if ("0".equals(order_status) && "6".equals(order_type)) {
                            //无证练车代付款去支付
                            Intent intent = new Intent(context, WZIndentDetailsOrderPractice.class);
                            intent.putExtra("order_number", order_number);
                            startActivity(intent);
                        }
                        if ("1".equals(order_status)) {

                            //联系商家
                            //显示拨打电话的dialog
                            String content = "";
                            if ("2".equals(order_type)) {
                                //驾校
                                content = "拨打驾校电话";
                            } else if ("1".equals(order_type)) {
                                //车生活
                                content = "拨打店铺电话";
                            } else if ("0".equals(order_type) || "6".equals(order_type)) {
                                //有证练车
                                content = "拨打教练电话";
                            } else if ("4".equals(order_type)) {
                                //二手车
                                content = "拨打店铺电话";
                            }
                            if (TextUtils.isEmpty(shop_phone)) {
                                showToastShort("电话号码为空");
                                return;
                            }
                            XueYiCheUtils.CallPhone(getActivity(), content, shop_phone);
                        }
                        if ("2".equals(order_status)) {
                            //已完成
//                        if ("7".equals(order_type)) {
//                            XueYiCheUtils.CallPhone(getActivity(), "拨打商家电话", shop_phone);
//                            return;
//                        }
                            if ("0".equals(order_receiving)) {
                                //未评论0有证练车，1车生活 ，2 驾校
                                if ("2".equals(order_type)) {
                                    //驾校教练的评价
                                    pingjiaTrainer(order_number);
                                } else if ("4".equals(order_type)) {
                                    cancelIndentFree(getActivity(), AppUrl.Delete_Indent_UsedCar, user_id, order_number, "是否删除订单？");
                                } else if ("6".equals(order_type)) {
                                    //无证练车的评价
                                    Intent intent = new Intent();
                                    intent.setClass(App.context, PingJiaDriverSchool.class);
                                    intent.putExtra("order_number", order_number);
                                    intent.putExtra("evaluate_type", "3");
                                    startActivity(intent);
                                } else if ("7".equals(order_type)) {

                                } else {
                                    Intent intent = new Intent();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setClass(App.context, PingJia.class);
                                    intent.putExtra("order_number", order_number);
                                    App.context.startActivity(intent);
                                }
                            } else {
                                //已评论  删除
                                if ("1".equals(order_type)) {
                                    //车生活已完成订单
                                    if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                                        cancelIndentFree(getActivity(), AppUrl.Delete_Indent_CarLive, user_id, order_number, "是否删除订单？");
                                    }
                                } else if ("2".equals(order_type)) {
                                    if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                                        cancelIndentFree(getActivity(), AppUrl.Delete_Indent_DriverSchool, user_id, order_number, "是否删除订单？");
                                    }
                                } else if ("0".equals(order_type)) {
                                    if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                                        cancelIndentFree(getActivity(), AppUrl.Delete_Indent_Practice, user_id, order_number, "是否删除订单？");
                                    }
                                }
                            }

                        }
                        if ("5".equals(order_status)) {
                            //驾校待返现状态
                            if ("0".equals(need_photo)) {
                                //不需要传照片
                                showDriverSchoolFanXian((Activity) context, order_number);
                            } else {
                                putStudentCard(order_number);
                            }
                        }
                        if ("6".equals(order_status)) {
                            //有证练车代付款取消订单
                            if (!TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(order_number)) {
                                cancelIndentFree(getActivity(), AppUrl.Delete_Indent_Practice, user_id, order_number, "是否取消订单？");
                            }
                        }
                        if ("9".equals(order_status)) {
                            if ("0".equals(order_type)) {
                                //有证练车待练车 联系
                                if (TextUtils.isEmpty(shop_phone)) {
                                    showToastShort("电话号码为空");
                                    return;
                                }
                                String decrypt = AES.decrypt(shop_phone);
                                XueYiCheUtils.CallPhone(getActivity(), "是否拨打教练电话", decrypt);
                            }
                        }
                    }
                }
            });
            holder.ll_xiangqin_indent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    if ("0".equals(order_type)) {
                        //0有证练车
                        //0:代付款1:进行中2:已完成  3 待学员确认  5:待返现6:待接单 7:退款中，8 返现中 9 待练车
                        //0:代付款 支付1.进行中2.已完成3.待确认4.已取消6.待接单7.退款中9.已接单
                        if ("0".equals(order_status) || "6".equals(order_status) || "2".equals(order_status)) {
                            intent = new Intent(context, PracticeCarSubmitIndent.class);
                            intent.putExtra("order_receiving", order_receiving);
                            intent.putExtra("type", "indent");
                        } else {
                            intent = new Intent(context, IndentDetailsOrderPractice.class);
                        }
                    } else if ("1".equals(order_type)) {
                        //2车生活
                        intent = new Intent(context, CarLiveDetails.class);
                    } else if ("2".equals(order_type)) {
                        //3驾校订单
                        intent = new Intent(context, IndentDriverSchoolDetails.class);
                    } else if ("4".equals(order_type)) {
                        //4二手车租车订单
                        intent = new Intent(context, UsedCarIndentDetails.class);
                    } else if ("6".equals(order_type)) {
                        //6无证练车
                        intent = new Intent(context, WZIndentDetailsOrderPractice.class);
                    } else if ("D".equals(order_type)) {
                        //代驾订单
                        //0：待接单，1：待到达，2：已到达，3：行程中，4：行程结束，5：已评价，6：已关闭
                        if ("0".equals(order_status)) {
                            if ("日常代驾".equals(refer_name)) {
                                intent = new Intent(context, WaitActivity.class);
                            } else if ("预约代驾".equals(refer_name)) {
                                intent = new Intent(context, WaitYuYueActivity.class);
                            } else if ("代叫即时".equals(refer_name)) {
                                intent = new Intent(context, WaitActivity.class);
                            } else if ("代叫预约".equals(refer_name)) {
                                intent = new Intent(context, WaitYuYueActivity.class);
                            }
                        }
                        if ("1".equals(order_status) || "2".equals(order_status)) {
                            intent = new Intent(context, JieDanActivity.class);
                        }
                        if ("3".equals(order_status)) {
                            intent = new Intent(context, JinXingActivity.class);
                        }
                        if ("4".equals(order_status) || "5".equals(order_status)) {
                            intent = new Intent(context, EndActivity.class);
                        }
//                        if ("6".equals(order_status)) {
//                            intent = new Intent(context, EndActivity.class);
//                        }
                    }
                    if (intent != null) {
                        intent.putExtra("order_number", order_number);
                        context.startActivity(intent);
                    }
                }
            });
            return convertView;
        }

        private void pingjiaTrainer(final String order_number) {

            View view = LayoutInflater.from(App.context).inflate(R.layout.jiaolian_pingjia_dia, null);
            TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
            TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
            tv_queren.setText("去评价");
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Dialog).setView(view);
            final AlertDialog dialog01 = builder.show();
            //设置弹窗的宽度，高度
            DisplayMetrics dm = new DisplayMetrics();
            //获取屏幕信息
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeigh = dm.heightPixels;
            WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();//获取dialog信息
            params.width = screenWidth * 2 / 3;
            params.height = screenHeigh / 2;
            dialog01.getWindow().setAttributes(params);//设置大小
            tv_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog01.dismiss();
                }
            });
            tv_queren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new MyEvent("刷新全部订单"));
                    EventBus.getDefault().post(new MyEvent("刷新进行中订单"));
                    EventBus.getDefault().post(new MyEvent("刷新代付款订单"));
                    EventBus.getDefault().post(new MyEvent("刷新已完成订单"));
                    EventBus.getDefault().post(new MyEvent("全部订单"));
                    Intent intent = new Intent();
                    intent.setClass(App.context, DriverSchoolTrainerListActivity.class);
                    intent.putExtra("order_number", order_number);
                    intent.putExtra("style", "1");
                    intent.putExtra("id", "");
                    startActivity(intent);
                    dialog01.dismiss();

                }
            });
            //点击空白处弹框不消失
            dialog01.setCancelable(false);
            dialog01.show();
        }

        //免费取消订单
        public void cancelIndentFree(final Activity activity, final String Url, final String user_id, final String order_number, String content) {
            //免费取消订单
            View viewDia = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
            TextView tv_quxiao = (TextView) viewDia.findViewById(R.id.tv_quxiao);
            TextView tv_queren = (TextView) viewDia.findViewById(R.id.tv_queren);
            TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
            tv_content.setText(content);
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(viewDia);
            final AlertDialog dialog01 = builder.show();
            //设置弹窗的宽度，高度
            DisplayMetrics dm = new DisplayMetrics();
            //获取屏幕信息
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeigh = dm.heightPixels;
            WindowManager.LayoutParams params =
                    dialog01.getWindow().getAttributes();//获取dialog信息
            params.width = screenWidth * 2 / 3;
            params.height = screenHeigh / 2;
            dialog01.getWindow().setAttributes(params);//设置大小
            tv_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog01.dismiss();
                }
            });
            tv_queren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OkHttpUtils.post().url(Url)
                            .addParams("device_id", LoginUtils.getId(activity))
                            .addParams("user_id", user_id)
                            .addParams("order_number", order_number)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        DeleteIndentBean deleteIndentBean = JsonUtil.parseJsonToBean(string, DeleteIndentBean.class);
                                        if (deleteIndentBean != null) {
                                            final int code = deleteIndentBean.getCode();
                                            final String msg = deleteIndentBean.getMsg();
                                            if (!TextUtils.isEmpty("" + code)) {
                                                App.handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (200 == code) {
                                                            getDataFromNet();
                                                        }
                                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                        dialog01.dismiss();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    return null;
                                }

                                @Override
                                public void onError(Request request, Exception e) {
                                }

                                @Override
                                public void onResponse(Object response) {
                                }
                            });
                }
            });
            //点击空白处弹框不消失
            dialog01.setCancelable(false);
            dialog01.show();
        }

        //赔付违约金取消订单
        public void cancelIndentMoney(final Activity activity, final String order_number) {
            View viewDia = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
            TextView tv_quxiao = (TextView) viewDia.findViewById(R.id.tv_quxiao);
            TextView tv_queren = (TextView) viewDia.findViewById(R.id.tv_queren);
            TextView tv_content = (TextView) viewDia.findViewById(R.id.tv_content);
            if (!TextUtils.isEmpty(remark)) {
                String br = remark.replaceAll("br", "\n");
                tv_content.setText(br);
            }
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(viewDia);
            final AlertDialog dialog01 = builder.show();
            //设置弹窗的宽度，高度
            DisplayMetrics dm = new DisplayMetrics();
            //获取屏幕信息
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeigh = dm.heightPixels;
            WindowManager.LayoutParams params =
                    dialog01.getWindow().getAttributes();//获取dialog信息
            params.width = screenWidth * 2 / 3;
            params.height = screenHeigh / 2;
            dialog01.getWindow().setAttributes(params);//设置大小
            tv_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog01.dismiss();
                }
            });
            tv_queren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OkHttpUtils.post().url(AppUrl.Delete_Indent_Practice)
                            .addParams("device_id", LoginUtils.getId(activity))
                            .addParams("order_number", order_number)
                            .addParams("user_id", user_id)
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        DeleteIndentBean deleteIndentBean = JsonUtil.parseJsonToBean(string, DeleteIndentBean.class);
                                        if (deleteIndentBean != null) {
                                            final int code = deleteIndentBean.getCode();
                                            final String msg = deleteIndentBean.getMsg();
                                            if (!TextUtils.isEmpty("" + code)) {
                                                App.handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (200 == code) {
                                                            getDataFromNet();
                                                        }
                                                        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                                                        dialog01.dismiss();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                    return null;
                                }

                                @Override
                                public void onError(Request request, Exception e) {
                                }

                                @Override
                                public void onResponse(Object response) {
                                }
                            });
                }
            });
            //点击空白处弹框不消失
            dialog01.setCancelable(false);
            dialog01.show();
        }

        private void putStudentCard(final String order_number) {
            View view = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
            TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
            TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_content.setText("上传完成后，请您耐心等待预计审核通过时间为3-7个工作日");
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Dialog).setView(view);
            final AlertDialog dialog01 = builder.show();
            tv_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog01.dismiss();
                }
            });
            tv_queren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(App.context, PutStudentCardActivity.class);
                    intent.putExtra("order_number", order_number);
                    startActivity(intent);
                    dialog01.dismiss();
                }
            });
            //点击空白处弹框不消失
            dialog01.setCancelable(false);
            dialog01.show();
        }

        class ViewHolder {
            private TextView tv_number_indent, tv_shopname_indent, tv_service_indent,
                    tv_money_indent, tv_date_indent, tv_butone_indent, tv_state_indent,
                    tv_butthree_indent, tv_buttwo_indent, tv_butfour_indent, tv_indent_nember;
            private LinearLayout ll_xiangqin_indent;
            private ImageView iv_indent_erweima;

            public ViewHolder(View view) {
                ll_xiangqin_indent = (LinearLayout) view.findViewById(R.id.ll_xiangqin_indent);
                tv_number_indent = (TextView) view.findViewById(R.id.tv_number_indent);
                tv_state_indent = (TextView) view.findViewById(R.id.tv_state_indent);
                tv_indent_nember = (TextView) view.findViewById(R.id.tv_indent_nember);
                tv_shopname_indent = (TextView) view.findViewById(R.id.tv_shopname_indent);
                tv_service_indent = (TextView) view.findViewById(R.id.tv_service_indent);
                tv_money_indent = (TextView) view.findViewById(R.id.tv_money_indent);
                tv_date_indent = (TextView) view.findViewById(R.id.tv_date_indent);
                tv_butone_indent = (TextView) view.findViewById(R.id.tv_butone_indent);
                tv_buttwo_indent = (TextView) view.findViewById(R.id.tv_buttwo_indent);
                tv_butthree_indent = (TextView) view.findViewById(R.id.tv_butthree_indent);
                tv_butfour_indent = (TextView) view.findViewById(R.id.tv_butfour_indent);
                iv_indent_erweima = (ImageView) view.findViewById(R.id.iv_indent_erweima);
            }
        }

        //驾校申请返现
        public void showDriverSchoolFanXian(final Activity activity, final String order_number) {
            View view = LayoutInflater.from(App.context).inflate(R.layout.get_jifen_dialog, null);
            TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
            TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_content.setText("申请成功5-7个工作日内，返现金额会返还到支付所用银行卡、支付宝或微信账号中，请您耐心等待");
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Dialog).setView(view);
            final AlertDialog dialog01 = builder.show();
            tv_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog01.dismiss();
                }
            });
            tv_queren.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OkHttpUtils.post()
                            .url(AppUrl.DriverSchool_FanXian)
                            .addParams("device_id", LoginUtils.getId((Activity) context))
                            .addParams("user_id", user_id)
                            .addParams("order_number", order_number)
                            .addParams("photo_url", "")
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response) throws IOException {
                                    String string = response.body().string();
                                    if (!TextUtils.isEmpty(string)) {
                                        SuccessDisCoverBackBean successDisCoverBackBean = JsonUtil.parseJsonToBean(string, SuccessDisCoverBackBean.class);
                                        if (successDisCoverBackBean != null) {
                                            final int code = successDisCoverBackBean.getCode();
                                            final String msg = successDisCoverBackBean.getMsg();
                                            App.handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!TextUtils.isEmpty("" + code)) {
                                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                                        dialog01.dismiss();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    return string;
                                }

                                @Override
                                public void onError(Request request, Exception e) {

                                }

                                @Override
                                public void onResponse(Object response) {
                                    getDataFromNet();
                                }
                            });


                }
            });
            //点击空白处弹框不消失
            dialog01.setCancelable(false);
            dialog01.show();
        }
    }

    /**
     * 驾校退定金
     */
    private void goDsTuiQIan() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_tishi_content = (TextView) view.findViewById(R.id.tv_tishi_content);
        tv_tishi_content.setText("是否拨打电话联系客服退款");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 400;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri telUri = Uri.parse("tel:" +" 0451-51068980");
                Intent intent = new Intent(Intent.ACTION_DIAL, telUri);
                startActivity(intent);
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();

    }

    private void quxiaoDJ(final String order_number, String tishi_content) {

        View view = LayoutInflater.from(App.context).inflate(R.layout.quxiao_indent_dialog, null);
        TextView tv_quxiao = (TextView) view.findViewById(R.id.tv_quxiao);
        TextView tv_queren = (TextView) view.findViewById(R.id.tv_queren);
        TextView tv_tishi_content = (TextView) view.findViewById(R.id.tv_tishi_content);
        tv_tishi_content.setText(tishi_content);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog).setView(view);
        final AlertDialog dialog01 = builder.show();
        //设置弹窗的宽度，高度
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params =
                dialog01.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 400;
        params.height = screenHeigh / 2;
        dialog01.getWindow().setAttributes(params);//设置大小
        tv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), LiYouActivity.class);
                intent1.putExtra("order_number", order_number);
                intent1.putExtra("type", "DingDan");
                dialog01.dismiss();
                startActivity(intent1);
            }
        });
        //点击空白处弹框不消失
        dialog01.setCancelable(false);
        dialog01.show();
    }

    private void daoHang(String latitude, String longitude, String shop_place_name) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 13);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 13);
                    }
                } else {
                    openMap(latitude, longitude, shop_place_name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            openMap(latitude, longitude, shop_place_name);
        }

    }

    private void openMap(String latitude, String longitude, String shop_place_name) {
        if (XueYiCheUtils.IsHaveInternet(App.context)) {
            BaiduLocation baidu = new BaiduLocation();
            baidu.baiduLocation();
            String x = PrefUtils.getString(App.context, "x", "");
            String y = PrefUtils.getString(App.context, "y", "");
            showDialog(latitude, longitude, shop_place_name, x, y);
        } else {
            showToastShort("请检查网络");
        }
    }

    private void showDialog(String latitude, String longitude, String address, String x, String y) {
        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(x) && !TextUtils.isEmpty(y)) {
            MapSelectDialog dialog = new MapSelectDialog(getActivity(), R.style.testDialog, latitude, longitude, address, x, y, address);
            dialog.show();
        } else {
            showToastShort("目的地位置异常");
        }
    }

}

