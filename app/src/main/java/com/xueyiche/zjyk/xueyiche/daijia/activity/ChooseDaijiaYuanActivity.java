package com.xueyiche.zjyk.xueyiche.daijia.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.base.GDLocation;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.daijia.DaiJiaActivity;
import com.xueyiche.zjyk.xueyiche.daijia.bean.DrivingListBean;
import com.xueyiche.zjyk.xueyiche.mine.view.CircleImageView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.DensityUtils;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseDaijiaYuanActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ItemAdapter itemAdapter;
    int pager = 1;

    @Override
    protected int initContentView() {
        return R.layout.activity_choose_daijia_yuan;
    }

    public static void forward(Activity activity, int code) {
        Intent intent = new Intent(activity, ChooseDaijiaYuanActivity.class);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("选择代驾员");
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getDataNet();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(ChooseDaijiaYuanActivity.this));
        itemAdapter = new ItemAdapter(R.layout.item_daijia_yuan);
        itemAdapter.setEmptyView(LayoutInflater.from(App.context).inflate(R.layout.empty_layout, null));
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DrivingListBean.DataBean.RowsBean rowsBean = (DrivingListBean.DataBean.RowsBean) adapter.getItem(position);
                String driving_id = rowsBean.getDriving_id();
                String name = rowsBean.getName();
                String user_number = rowsBean.getUser_number();
                Intent intent = new Intent();
                intent.putExtra("driving_id", ""+driving_id);
                intent.putExtra("name", ""+name);
                intent.putExtra("user_number", ""+user_number);
                setResult(444, intent);
                finish();
            }
        });
        getDataNet();
    }

    private void requestQrCodeScan(Activity ctx) {
        AndPermission.with(ChooseDaijiaYuanActivity.this)
                .permission(
                        Manifest.permission.CAMERA)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent = new Intent(ctx, ScannerActivity.class);
                        // 设置扫码框的宽
                        intent.putExtra(StringConstants.EXTRA_SCANNER_FRAME_WIDTH, DensityUtils.dip2px(ctx, 200));
                        // 设置扫码框的高
                        intent.putExtra(StringConstants.EXTRA_SCANNER_FRAME_HEIGHT, DensityUtils.dip2px(ctx, 200));
                        // 设置扫码框距顶部的位置
                        intent.putExtra(StringConstants.EXTRA_SCANNER_FRAME_TOP_PADDING, DensityUtils.dip2px(ctx, 100));
                        // 可以从相册获取
                        intent.putExtra(StringConstants.EXTRA_IS_ENABLE_SCAN_FROM_PIC, false);
                        ctx.startActivityForResult(intent, 888);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).start();

    }

    public void getDataNet() {
        Map<String, String> params = new HashMap<>();
        params.put("user_lng", PrefUtils.getParameter("lon"));
        params.put("user_lat", PrefUtils.getParameter("lat"));
        params.put("search", "" + etSearch.getText().toString().trim());
        params.put("pageNumber", pager + "");
        MyHttpUtils.postHttpMessage(AppUrl.drivingList, params, DrivingListBean.class, new RequestCallBack<DrivingListBean>() {
            @Override
            public void requestSuccess(DrivingListBean json) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (json.getCode() == 1) {
                    if (pager == 1) {
                        itemAdapter.setNewData(json.getData().getRows());
                    } else {
                        itemAdapter.addData(json.getData().getRows());
                    }

                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
                case -1:
                    String result = data.getExtras().getString(Constant.EXTRA_RESULT_CONTENT);
                    if (TextUtils.isEmpty(result)) {
                        return;
                    }
                    etSearch.setText("" + result);
                    etSearch.setSelection(result.length());
                    getDataNet();
//                    ToastUtils.showToast(ChooseDaijiaYuanActivity.this,""+result);
                    break;

            }
        }

    }

    //监听手机的物理按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("driving_id", "");
            intent.putExtra("name", "");
            intent.putExtra("user_number", "");
            setResult(444, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ll_common_back, R.id.iv_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                Intent intent = new Intent();
                intent.putExtra("driving_id", "");
                intent.putExtra("name", "");
                intent.putExtra("user_number", "");
                setResult(444, intent);
                finish();
                break;
            case R.id.iv_scan:
                // 扫一扫
                requestQrCodeScan(ChooseDaijiaYuanActivity.this);
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pager++;
        getDataNet();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pager = 1;
        getDataNet();
    }

    class ItemAdapter extends BaseQuickAdapter<DrivingListBean.DataBean.RowsBean, BaseViewHolder> {
        public ItemAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, DrivingListBean.DataBean.RowsBean item) {
            helper.setText(R.id.tv_name, "" + item.getName());
            helper.setText(R.id.tv_bianhao, "" + item.getUser_number());
            helper.setText(R.id.tv_location, "距您" + item.getJuli() + "km");
            CircleImageView view = helper.getView(R.id.civ_head);
            Glide.with(ChooseDaijiaYuanActivity.this).load(item.getHead_img()).into(view);
            String driving_id = item.getDriving_id();//司机id


        }
    }
}