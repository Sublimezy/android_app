package com.xueyiche.zjyk.xueyiche.daijia;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.daijia.SpeedDialog.dialog.SpeedDialog;
import com.xueyiche.zjyk.xueyiche.daijia.SpeedDialog.listener.OnInputDialogButtonClickListener;
import com.xueyiche.zjyk.xueyiche.daijia.addressdialog.BottomSelectorDialog;
import com.xueyiche.zjyk.xueyiche.daijia.addressdialog.City;
import com.xueyiche.zjyk.xueyiche.daijia.addressdialog.County;
import com.xueyiche.zjyk.xueyiche.daijia.addressdialog.OnAddressSelectedListener;
import com.xueyiche.zjyk.xueyiche.daijia.addressdialog.Province;
import com.xueyiche.zjyk.xueyiche.daijia.addressdialog.Street;
import com.xueyiche.zjyk.xueyiche.daijia.bean.BrandAllBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.CheXiBean;
import com.xueyiche.zjyk.xueyiche.daijia.bean.ProvinceAll;
import com.xueyiche.zjyk.xueyiche.daijia.picture.FullyGridLayoutManager;
import com.xueyiche.zjyk.xueyiche.daijia.picture.GlideEngine;
import com.xueyiche.zjyk.xueyiche.daijia.picture.GridImageAdapter;
import com.xueyiche.zjyk.xueyiche.daijia.picture.OnItemClickListener;
import com.xueyiche.zjyk.xueyiche.daijia.wheelpicker.BottomPopTools;
import com.xueyiche.zjyk.xueyiche.daijia.wheelpicker.PickerScrollView;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistSiJiActivity extends BaseActivity implements OnAddressSelectedListener {


    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ll_common_back)
    LinearLayout llCommonBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_btn)
    TextView tvRightBtn;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    @BindView(R.id.title_view_heng)
    View titleViewHeng;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_jialing)
    TextView tvJialing;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_jiashizheng_number)
    TextView tvJiashizhengNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.recycler_mianguan)
    RecyclerView recyclerMianguan;
    @BindView(R.id.sfj_zheng)
    RecyclerView recyclerSfjZheng;
    @BindView(R.id.recycler_sfj_fan)
    RecyclerView recyclerSfjFan;
    @BindView(R.id.recycler_jsz_zheng)
    RecyclerView recyclerJszZheng;
    @BindView(R.id.recycler_jsz_fan)
    RecyclerView recyclerJszFan;
    @BindView(R.id.recycler_xsz_zheng)
    RecyclerView recyclerXszZheng;
    @BindView(R.id.recycler_xsz_fan)
    RecyclerView recyclerXszFan;
    @BindView(R.id.recycler_car1)
    RecyclerView recyclerCar1;
    @BindView(R.id.recycler_car2)
    RecyclerView recyclerCar2;
    @BindView(R.id.recycler_car3)
    RecyclerView recyclerCar3;
    @BindView(R.id.recycler_car4)
    RecyclerView recyclerCar4;
    @BindView(R.id.cb_has_car)
    CheckBox cbHasCar;
    @BindView(R.id.ll_has_car)
    LinearLayout llHasCar;
    @BindView(R.id.tv_car_pinpai)
    TextView tvCarPinpai;
    @BindView(R.id.tv_car_chexi)
    TextView tvCarChexi;
    @BindView(R.id.ll_has_car_view)
    LinearLayout llHasCarView;
    @BindView(R.id.bottom)
    View bottom;

    private UploadManager uploadManager; //七牛云上传

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {

    }
    /**
     * 初始化View，执行findViewById操作
     */
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
    }
    /**
     * 初始化contentView
     *
     * @return 返回contentView的layout id
     */
    @Override
    protected int initContentView() {
        return R.layout.activity_regist_si_ji;
    }
    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        getDefaultStyle();
        llHasCarView.setVisibility(View.GONE);
        bottom.setVisibility(View.VISIBLE);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        initRecycler();
        initRecycler2();
        initRecycler3();
        initRecycler4();
        initRecycler5();
        initRecycler6();
        initRecycler7();
        initRecycler8();
        initRecycler9();
        initRecycler10();
        initRecycler11();


        //        // 注册广播
        BroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                ActionMianGuan, ActionSfjZheng, ActionSfzfan, ActionJszZheng, ActionJszfan, ActionXsjZheng, ActionXsjFan, ActionCar1, ActionCar2, ActionCar3, ActionCar4);
    }

    private String ActionMianGuan = "MianGuan";
    private String ActionSfjZheng = "SfjZheng";
    private String ActionSfzfan = "ActionSfzfan";
    private String ActionJszZheng = "ActionJszZheng";
    private String ActionJszfan = "ActionJszfan";
    private String ActionXsjZheng = "ActionXsjZheng";
    private String ActionXsjFan = "ActionXsjFan";
    private String ActionCar1 = "ActionCar1";
    private String ActionCar2 = "ActionCar2";
    private String ActionCar3 = "ActionCar3";
    private String ActionCar4 = "ActionCar4";
    private PictureParameterStyle mPictureParameterStyle;


    private final int CodeMianGuan = 101;
    private GridImageAdapter mAdapterMianGuan;
    private List<LocalMedia> selectListMianGuan;

    private void initRecycler() {

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);

        recyclerMianguan.setLayoutManager(manager);
        mAdapterMianGuan = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeMianGuan);
            }
        });
        mAdapterMianGuan.setSelectMax(1);
        recyclerMianguan.setAdapter(mAdapterMianGuan);
        mAdapterMianGuan.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterMianGuan.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionMianGuan);
                }
            }
        });
    }


    private final int CodeSfjZheng = 102;
    private GridImageAdapter mAdapterSfjZheng;
    private List<LocalMedia> selectListSfjZheng;

    private void initRecycler2() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerSfjZheng.setLayoutManager(manager);
        mAdapterSfjZheng = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeSfjZheng);
            }
        });
        mAdapterSfjZheng.setSelectMax(1);
        recyclerSfjZheng.setAdapter(mAdapterSfjZheng);
        mAdapterSfjZheng.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterSfjZheng.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionSfjZheng);
                }
            }
        });
    }


    private final int CodeSfzFan = 103;
    private GridImageAdapter mAdapterSfzFan;
    private List<LocalMedia> selectListSfzFan;

    private void initRecycler3() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerSfjFan.setLayoutManager(manager);
        mAdapterSfzFan = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeSfzFan);
            }
        });
        mAdapterSfzFan.setSelectMax(1);
        recyclerSfjFan.setAdapter(mAdapterSfzFan);
        mAdapterSfzFan.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterSfzFan.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionSfzfan);
                }
            }
        });
    }


    private final int CodeJszZheng = 104;
    private GridImageAdapter mAdapterJszZheng;
    private List<LocalMedia> selectListJszZheng;

    private void initRecycler4() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerJszZheng.setLayoutManager(manager);
        mAdapterJszZheng = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeJszZheng);
            }
        });
        mAdapterJszZheng.setSelectMax(1);
        recyclerJszZheng.setAdapter(mAdapterJszZheng);
        mAdapterJszZheng.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterJszZheng.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionJszZheng);
                }
            }
        });
    }


    private final int CodeJszFan = 105;
    private GridImageAdapter mAdapterJszFan;
    private List<LocalMedia> selectListJszFan;

    private void initRecycler5() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerJszFan.setLayoutManager(manager);
        mAdapterJszFan = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeJszFan);
            }
        });
        mAdapterJszFan.setSelectMax(1);
        recyclerJszFan.setAdapter(mAdapterJszFan);
        mAdapterJszFan.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterJszFan.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionJszfan);
                }
            }
        });
    }


    private final int CodeXszZheng = 106;
    private GridImageAdapter mAdapterXszZheng;
    private List<LocalMedia> selectListXszZheng;

    private void initRecycler6() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerXszZheng.setLayoutManager(manager);
        mAdapterXszZheng = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeXszZheng);
            }
        });
        mAdapterXszZheng.setSelectMax(1);
        recyclerXszZheng.setAdapter(mAdapterXszZheng);
        mAdapterXszZheng.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterXszZheng.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionXsjZheng);
                }
            }
        });
    }


    private final int CodeXszFan = 107;
    private GridImageAdapter mAdapterXszFan;
    private List<LocalMedia> selectListXszFan;

    private void initRecycler7() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerXszFan.setLayoutManager(manager);
        mAdapterXszFan = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeXszFan);
            }
        });
        mAdapterXszFan.setSelectMax(1);
        recyclerXszFan.setAdapter(mAdapterXszFan);
        mAdapterXszFan.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterXszFan.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionXsjFan);
                }
            }
        });
    }


    private final int CodeCar1 = 108;
    private GridImageAdapter mAdapterCar1;
    private List<LocalMedia> selectListCar1;

    private void initRecycler8() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerCar1.setLayoutManager(manager);
        mAdapterCar1 = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeCar1);
            }
        });
        mAdapterCar1.setSelectMax(1);
        recyclerCar1.setAdapter(mAdapterCar1);
        mAdapterCar1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterCar1.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionCar1);
                }
            }
        });
    }


    private final int CodeCar2 = 109;
    private GridImageAdapter mAdapterCar2;
    private List<LocalMedia> selectListCar2;

    private void initRecycler9() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerCar2.setLayoutManager(manager);
        mAdapterCar2 = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeCar2);
            }
        });
        mAdapterCar2.setSelectMax(1);
        recyclerCar2.setAdapter(mAdapterCar2);
        mAdapterCar2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterCar2.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionCar2);
                }
            }
        });
    }


    private final int CodeCar3 = 110;
    private GridImageAdapter mAdapterCar3;
    private List<LocalMedia> selectListCar3;

    private void initRecycler10() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerCar3.setLayoutManager(manager);
        mAdapterCar3 = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeCar3);
            }
        });
        mAdapterCar3.setSelectMax(1);
        recyclerCar3.setAdapter(mAdapterCar3);
        mAdapterCar3.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterCar3.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionCar3);
                }
            }
        });
    }


    private final int CodeCar4 = 111;
    private GridImageAdapter mAdapterCar4;
    private List<LocalMedia> selectListCar4;

    private void initRecycler11() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        recyclerCar4.setLayoutManager(manager);
        mAdapterCar4 = new GridImageAdapter(RegistSiJiActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                getPictureSelectionModel().forResult(CodeCar4);
            }
        });
        mAdapterCar4.setSelectMax(1);
        recyclerCar4.setAdapter(mAdapterCar4);
        mAdapterCar4.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapterCar4.getData();
                if (selectList.size() > 0) {
                    previewPicture(position, selectList, ActionCar4);
                }
            }
        });
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            Log.i("张斯佳", "张斯佳删除" + action);
            if (ActionSfjZheng.equals(action)) {
                // 外部预览删除按钮回调
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterSfjZheng.remove(position);
                    mAdapterSfjZheng.notifyItemRemoved(position);
                }
            }
            if (ActionMianGuan.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterMianGuan.remove(position);
                    mAdapterMianGuan.notifyItemRemoved(position);
                }
            }
            if (ActionSfzfan.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterSfzFan.remove(position);
                    mAdapterSfzFan.notifyItemRemoved(position);
                }
            }
            if (ActionJszZheng.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterJszZheng.remove(position);
                    mAdapterJszZheng.notifyItemRemoved(position);
                }
            }
            if (ActionJszfan.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterJszFan.remove(position);
                    mAdapterJszFan.notifyItemRemoved(position);
                }
            }
            if (ActionXsjZheng.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterXszZheng.remove(position);
                    mAdapterXszZheng.notifyItemRemoved(position);
                }
            }
            if (ActionXsjFan.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterXszFan.remove(position);
                    mAdapterXszFan.notifyItemRemoved(position);
                }
            }
            if (ActionCar1.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterCar1.remove(position);
                    mAdapterCar1.notifyItemRemoved(position);
                }
            }
            if (ActionCar2.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterCar2.remove(position);
                    mAdapterCar2.notifyItemRemoved(position);
                }
            }
            if (ActionCar3.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterCar3.remove(position);
                    mAdapterCar3.notifyItemRemoved(position);
                }
            }
            if (ActionCar4.equals(action)) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    mAdapterCar4.remove(position);
                    mAdapterCar4.notifyItemRemoved(position);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            BroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver, ActionMianGuan, ActionSfjZheng, ActionSfzfan, ActionJszZheng, ActionJszfan, ActionXsjZheng, ActionXsjFan, ActionCar1, ActionCar2, ActionCar3

            );
        }
    }


    private void previewPicture(int position, List<LocalMedia> selectList, String action) {
        PictureSelector.create(RegistSiJiActivity.this)
                .themeStyle(R.style.picture_default_style) // xml设置主题
                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .openExternalPreview(position, selectList, action);
    }


    HashMap<Integer, LocalMedia> selectedPhotos = new HashMap<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {


            case CodeMianGuan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListMianGuan = PictureSelector.obtainMultipleResult(data);
                if (selectListMianGuan.size() > 0) {
                    selectedPhotos.put(0, selectListMianGuan.get(0));
                }
                mAdapterMianGuan.setList(selectListMianGuan);
                mAdapterMianGuan.notifyDataSetChanged();
                break;
            case CodeSfjZheng:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListSfjZheng = PictureSelector.obtainMultipleResult(data);

                if (selectListSfjZheng.size() > 0) {
                    selectedPhotos.put(1, selectListSfjZheng.get(0));
                }
                mAdapterSfjZheng.setList(selectListSfjZheng);
                mAdapterSfjZheng.notifyDataSetChanged();
                break;
            case CodeSfzFan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListSfzFan = PictureSelector.obtainMultipleResult(data);

                if (selectListSfzFan.size() > 0) {
                    selectedPhotos.put(2, selectListSfzFan.get(0));
                }
                mAdapterSfzFan.setList(selectListSfzFan);
                mAdapterSfzFan.notifyDataSetChanged();
                break;
            case CodeJszZheng:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListJszZheng = PictureSelector.obtainMultipleResult(data);

                if (selectListJszZheng.size() > 0) {
                    selectedPhotos.put(3, selectListJszZheng.get(0));
                }
                mAdapterJszZheng.setList(selectListJszZheng);
                mAdapterJszZheng.notifyDataSetChanged();
                break;
            case CodeJszFan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListJszFan = PictureSelector.obtainMultipleResult(data);

                if (selectListJszFan.size() > 0) {
                    selectedPhotos.put(4, selectListJszFan.get(0));
                }
                mAdapterJszFan.setList(selectListJszFan);
                mAdapterJszFan.notifyDataSetChanged();
                break;
            case CodeXszZheng:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListXszZheng = PictureSelector.obtainMultipleResult(data);
                if (selectListXszZheng.size() > 0) {
                    selectedPhotos.put(5, selectListXszZheng.get(0));
                }
                mAdapterXszZheng.setList(selectListXszZheng);
                mAdapterXszZheng.notifyDataSetChanged();
                break;
            case CodeXszFan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListXszFan = PictureSelector.obtainMultipleResult(data);
                if (selectListXszFan.size() > 0) {
                    selectedPhotos.put(6, selectListXszFan.get(0));
                }
                mAdapterXszFan.setList(selectListXszFan);
                mAdapterXszFan.notifyDataSetChanged();
                break;
            case CodeCar1:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar1 = PictureSelector.obtainMultipleResult(data);
                if (selectListCar1.size() > 0) {
                    selectedPhotos.put(7, selectListCar1.get(0));
                }
                mAdapterCar1.setList(selectListCar1);
                mAdapterCar1.notifyDataSetChanged();
                break;
            case CodeCar2:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar2 = PictureSelector.obtainMultipleResult(data);
                if (selectListCar2.size() > 0) {
                    selectedPhotos.put(8, selectListCar2.get(0));
                }
                mAdapterCar2.setList(selectListCar2);
                mAdapterCar2.notifyDataSetChanged();
                break;
            case CodeCar3:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar3 = PictureSelector.obtainMultipleResult(data);
                if (selectListCar3.size() > 0) {
                    selectedPhotos.put(9, selectListCar3.get(0));
                }
                mAdapterCar3.setList(selectListCar3);
                mAdapterCar3.notifyDataSetChanged();
                break;
            case CodeCar4:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar4 = PictureSelector.obtainMultipleResult(data);
                if (selectListCar4.size() > 0) {
                    selectedPhotos.put(10, selectListCar4.get(0));
                }
                mAdapterCar4.setList(selectListCar4);
                mAdapterCar4.notifyDataSetChanged();
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private PictureSelectionModel getPictureSelectionModel() {
        return PictureSelector.create(RegistSiJiActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .isPreviewVideo(true)
                .isCamera(true)
                .isCompress(true)
                .compressQuality(80);
    }



    public Context getContext() {
        return this;
    }

    private void getDefaultStyle() {
        // 相册主题
        mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = false;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e");
        // 相册父容器背景色
        mPictureParameterStyle.pictureContainerBackgroundColor = ContextCompat.getColor(getContext(), R.color.app_color_black);
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up;
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down;
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 选择相册目录背景样式
        mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_new_item_select_bg;
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector;
        // 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(getContext(), R.color.picture_color_grey);
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval;
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_fa632d);
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_fa632d);
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(getContext(), R.color.picture_color_white);
        // 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(getContext(), R.color.picture_color_grey);
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_wechat_checkbox;
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(getContext(), R.color.app_color_white);
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e");

//        // 自定义相册右侧文本内容设置
//        mPictureParameterStyle.pictureRightDefaultText = "";
//        // 自定义相册未完成文本内容
//        mPictureParameterStyle.pictureUnCompleteText = "";
//        // 自定义相册完成文本内容
//        mPictureParameterStyle.pictureCompleteText = "";
//        // 自定义相册列表不可预览文字
//        mPictureParameterStyle.pictureUnPreviewText = "";
//        // 自定义相册列表预览文字
//        mPictureParameterStyle.picturePreviewText = "";
//
//        // 自定义相册标题字体大小
//        mPictureParameterStyle.pictureTitleTextSize = 18;
//        // 自定义相册右侧文字大小
//        mPictureParameterStyle.pictureRightTextSize = 14;
//        // 自定义相册预览文字大小
//        mPictureParameterStyle.picturePreviewTextSize = 14;
//        // 自定义相册完成文字大小
//        mPictureParameterStyle.pictureCompleteTextSize = 14;
//        // 自定义原图文字大小
//        mPictureParameterStyle.pictureOriginalTextSize = 14;

        // 裁剪主题
//        mCropParameterStyle = new PictureCropParameterStyle(
//                ContextCompat.getColor(getContext(), R.color.app_color_grey),
//                ContextCompat.getColor(getContext(), R.color.app_color_grey),
//                Color.parseColor("#393a3e"),
//                ContextCompat.getColor(getContext(), R.color.app_color_white),
//                mPictureParameterStyle.isChangeStatusBarFontColor);
    }

    @Override
    protected void initSavedins(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // 被回收
        } else {
            clearCache();
        }
    }

    /**
     * 清空缓存包括裁剪、压缩、AndroidQToPath所生成的文件，注意调用时机必须是处理完本身的业务逻辑后调用；非强制性
     */
    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
            PictureFileUtils.deleteAllCacheDirFile(this);
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }


    String name = ""; // 姓名
    String sex = "";//  性别
    String jialing = "";//驾龄
    String phone = ""; //电话号
    String jiashizhenghao = "";//驾驶证号码
    String type_car = "0"; //是否有车


    @OnClick({R.id.ll_common_back, R.id.tv_name, R.id.btn_submit, R.id.tv_sex, R.id.tv_jialing, R.id.tv_phone, R.id.tv_jiashizheng_number, R.id.tv_address, R.id.ll_has_car, R.id.tv_car_pinpai, R.id.tv_car_chexi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tv_name:
                SpeedDialog speedDialog = new SpeedDialog(this, SpeedDialog.INPUT_TYPE);
                speedDialog.setTitle("请填写姓名")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .setHint("请输入真实姓名")
                        .setInputDialogSureClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String inputText) {
                                tvName.setText(inputText);
                                name = inputText;
                            }
                        }).show();
                break;
            case R.id.tv_sex:
                ArrayList list = new ArrayList<>();
                list.add(new PickerScrollView.PickerList("男"));
                list.add(new PickerScrollView.PickerList("女"));


                BottomPopTools.showPop(view, this, list, new BottomPopTools.onSelectPickerListener() {
                    @Override
                    public void onSelect(PickerScrollView.PickerList pickers) {
                        tvSex.setText(pickers.getName());
                        sex = pickers.getName();
                    }
                });
                break;
            case R.id.tv_jialing:
                SpeedDialog speedDialog2 = new SpeedDialog(this, SpeedDialog.INPUT_TYPE);
                speedDialog2.setTitle("请填写驾龄")
                        .setHint("请填写驾龄")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setInputDialogSureClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String inputText) {
                                tvJialing.setText(inputText);
                                jialing = inputText;
                            }
                        }).show();
                break;
            case R.id.tv_phone:
                SpeedDialog speedDialog3 = new SpeedDialog(this, SpeedDialog.INPUT_TYPE);
                speedDialog3.setTitle("请填写电话")
                        .setHint("请输入联系电话")
                        .setInputType(InputType.TYPE_CLASS_PHONE)
                        .setInputDialogSureClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String inputText) {
                                tvPhone.setText(inputText);
                                phone = inputText;
                            }
                        }).show();
                break;
            case R.id.tv_jiashizheng_number:
                SpeedDialog speedDialog4 = new SpeedDialog(this, SpeedDialog.INPUT_TYPE);
                speedDialog4.setTitle("请填写驾驶证号")
                        .setHint("请填写驾驶证号")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .setInputDialogSureClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String inputText) {
                                tvJiashizhengNumber.setText(inputText);
                                jiashizhenghao = inputText;
                            }
                        }).show();
                break;
            case R.id.tv_address:
                //选择地区
                showPopupWindowCity();
                break;
            case R.id.ll_has_car:
                boolean checked = cbHasCar.isChecked();
                cbHasCar.setChecked(!checked);
                if (checked) {
                    llHasCarView.setVisibility(View.GONE);
                    bottom.setVisibility(View.VISIBLE);
                    type_car = "0";
                } else {
                    llHasCarView.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.GONE);
                    type_car = "1";
                }
                break;
            case R.id.tv_car_pinpai:
                showPopupWindowCar();
                break;
            case R.id.tv_car_chexi:
                if (TextUtils.isEmpty(carPinPaiId)) {
                    showToastShort("请先选择车辆品牌");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("brandID", carPinPaiId);
                MyHttpUtils.postHttpMessage("http://ttmag.xueyiche.vip:99/api/tatapublic/getCarSeries", params, CheXiBean.class, new RequestCallBack<CheXiBean>() {
                    @Override
                    public void requestSuccess(CheXiBean json) {
                        if (json.getStatus() == 200) {
                            List<CheXiBean.DataBean> data = json.getData();
                            List<City> list = new ArrayList<>();
                            for (int i = 0; i < data.size(); i++) {
                                City city = new City();
                                city.id = data.get(i).getSeriesid();
                                city.name = data.get(i).getSeriesname();
                                list.add(city);
                                dialog_select_car.getSelector().setCities(list);
                                dialog_select_car.show();
                            }
                        } else {
                            showToastShort(json.getMsg());
                        }
                    }

                    @Override
                    public void requestError(String errorMsg, int errorType) {
                        showToastShort("服务器内部错误");

                    }
                });
                break;
            case R.id.btn_submit:
                //提交
                if (TextUtils.isEmpty(name)) {
                    showToastShort("请输入名字");
                    return;
                }
                if (TextUtils.isEmpty(sex)) {
                    showToastShort("请选择性别");
                    return;
                }
                if (TextUtils.isEmpty(jialing)) {
                    showToastShort("请填写驾龄");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToastShort("请填写联系电话");
                    return;
                }
                if (TextUtils.isEmpty(jiashizhenghao)) {
                    showToastShort("请填写驾驶证号");
                    return;
                }
                if (TextUtils.isEmpty(shengshiqu)) {
                    showToastShort("请选择联系地址");
                    return;
                }

                if (selectListMianGuan == null || selectListMianGuan.size() == 0) {
                    showToastShort("请选择本人正面免冠照片");
                    return;
                }
                if (selectListSfjZheng == null || selectListSfjZheng.size() == 0) {
                    showToastShort("请选择身份证反面照片");
                    return;
                }
                if (selectListSfzFan == null || selectListSfzFan.size() == 0) {
                    showToastShort("请选择身份证正面照片");
                    return;
                }
                if (selectListJszZheng == null || selectListJszZheng.size() == 0) {
                    showToastShort("请选择驾驶证照片");
                    return;
                }
                if (selectListJszFan == null || selectListJszFan.size() == 0) {
                    showToastShort("请选择驾驶证副页照片");
                    return;
                }


                if ("1".equals(type_car)) {
                    //有车   判断有车内容是否为空
                    if (TextUtils.isEmpty(carPinPaiId) || TextUtils.isEmpty(carPinPaiName)) {
                        showToastShort("请选择车辆品牌");
                        return;
                    }
                    if (TextUtils.isEmpty(carCheXiId) || TextUtils.isEmpty(carCheXiName)) {
                        showToastShort("请选择车辆型号");
                        return;
                    }
                    if (selectListJszZheng == null || selectListJszZheng.size() == 0) {
                        showToastShort("请选择行驶证照片");
                        return;
                    }
                    if (selectListJszFan == null || selectListJszFan.size() == 0) {
                        showToastShort("请选择行驶证副页照片");
                        return;
                    }
                    if (selectListCar1 == null || selectListCar1.size() == 0) {
                        showToastShort("请选择车辆正面45度照片");
                        return;
                    }
                    if (selectListCar2 == null || selectListCar2.size() == 0) {
                        showToastShort("请选择车辆侧面照片");
                        return;
                    }
                    if (selectListCar3 == null || selectListCar3.size() == 0) {
                        showToastShort("请选择车辆后面照片");
                        return;
                    }
                    if (selectListCar4 == null || selectListCar4.size() == 0) {
                        showToastShort("请选择车辆驾驶室照片");
                        return;
                    }


                }
                uploadManager = new UploadManager();
//                showProgressDialog(false, "正在上传图片...");
                pictureMap = new HashMap<>();
                for (Map.Entry<Integer, LocalMedia> entry : selectedPhotos.entrySet()) {
                    Integer key = entry.getKey();
                    if ("1".equals(type_car)) {
                        //有车
                        Calendar calendar = Calendar.getInstance();
                        long timeInMillis = calendar.getTimeInMillis();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        UpLoadToQiNiu("fx_tuwen" + timeInMillis + ".jpg", key, 11, entry.getValue());

                    } else {
//                        无车
                        if (key > 4) {

                        } else {

                            Calendar calendar = Calendar.getInstance();
                            long timeInMillis = calendar.getTimeInMillis();
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            UpLoadToQiNiu("fx_tuwen" + timeInMillis + ".jpg", key, 5, entry.getValue());
                        }
                    }

                }


                break;
        }
    }

    Map<Integer, String> pictureMap = new HashMap<>();  //图片上传的顺序

    private void UpLoadToQiNiu(final String key, final int position, int total, LocalMedia media) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        MyHttpUtils.postHttpMessage(AppUrl.TOUXIANG, params, TokenBean.class, new RequestCallBack<TokenBean>() {
            @Override
            public void requestSuccess(TokenBean json) {
                if (json.getCode() == 200) {
                    String token = json.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        File fileCropUri;
                        if (TextUtils.isEmpty(media.getCompressPath())) {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                fileCropUri = new File(media.getAndroidQToPath());
                            } else {
                                fileCropUri = new File(media.getPath());
                            }
                        } else {
                            fileCropUri = new File(media.getCompressPath());
                        }

                        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    pictureMap.put(position, StringConstants.QiNiu + key);

                                    if (total == pictureMap.size()) {
                                        //全部上传成功
                                        stopProgressDialog();
                                        Iterator<Integer> iterator = pictureMap.keySet().iterator();
                                        while (iterator.hasNext()) {
                                            Log.i("张斯佳司机报名_", pictureMap.get(iterator.next()));
                                        }
                                    }
                                } else {
                                    stopProgressDialog();
                                    showToastShort("上传失败,请重新尝试!");
                                }


                            }
                        }, null);
                    }
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("连接服务器失败");
            }
        });
    }


    private BottomSelectorDialog dialog_select_car;
    private String carPinPaiId = "";
    private String carPinPaiName = "";
    private String carCheXiId = "";
    private String carCheXiName = "";

    private void showPopupWindowCar() {
        Map<String, String> params = new HashMap<>();
        MyHttpUtils.postHttpMessage("http://ttmag.xueyiche.vip:99/api/tatapublic/brandAll", params, BrandAllBean.class, new RequestCallBack<BrandAllBean>() {
            @Override
            public void requestSuccess(BrandAllBean json) {
                if (json.getStatus() == 200) {
                    dialog_select_car = new BottomSelectorDialog(RegistSiJiActivity.this);
                    dialog_select_car.setOnAddressSelectedListener(new OnAddressSelectedListener() {
                        @Override
                        public void onAddressSelected(Province province, City city, County county, Street street) {

                        }

                        @Override
                        public void onProvinceSelected(Province province) {
                            carPinPaiId = province.id;
                            carPinPaiName = province.name;
                            carCheXiId = "";
                            carCheXiName = "";
                            tvCarPinpai.setText(province.name);
                            tvCarChexi.setText("选择车系");
                            dialog_select_car.dismiss();
                        }

                        @Override
                        public void onCitySelected(City city) {
                            carCheXiId = city.id;
                            carCheXiName = city.name;
                            tvCarChexi.setText(city.name);
                            dialog_select_car.getSelector().setCountries(null);
                            dialog_select_car.dismiss();
                        }

                        @Override
                        public void onCountySelected(County county) {
                            dialog_select_car.getSelector().setStreets(null);
                        }
                    });
                    List<BrandAllBean.DataBean> data = json.getData();

                    List<Province> counties = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        Province province = new Province();
                        province.id = data.get(i).getId();
                        province.name = data.get(i).getName();
                        counties.add(province);
                        dialog_select_car.getSelector().setProvinces(counties);
                    }
                    dialog_select_car.show();
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("服务器内部错误");
            }
        });

    }

    private BottomSelectorDialog dialog_select_diqu;

    private void showPopupWindowCity() {
        Map<String, String> params = new HashMap<>();
        MyHttpUtils.postHttpMessage("http://ttmag.xueyiche.vip:99/api/tatapublic/provinceAll", params, ProvinceAll.class, new RequestCallBack<ProvinceAll>() {
            @Override
            public void requestSuccess(ProvinceAll json) {
                if (json.getStatus() == 200) {
                    dialog_select_diqu = new BottomSelectorDialog(RegistSiJiActivity.this);
                    dialog_select_diqu.setOnAddressSelectedListener(RegistSiJiActivity.this);
                    List<ProvinceAll.DataBean> data = json.getData();

                    List<Province> counties = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        Province province = new Province();
                        province.id = data.get(i).getAREA_ID();
                        province.name = data.get(i).getAREA_NAME();
                        counties.add(province);
                        dialog_select_diqu.getSelector().setProvinces(counties);
                    }
                    dialog_select_diqu.show();
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("服务器内部错误");
            }
        });

    }

    String shengshiqu = "";
    String sheng_id = "";
    String sheng_name = "";
    String shi_id = "";
    String shi_name = "";

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        shengshiqu = (province == null ? "" : province.name) + (city == null ? "" : city.name);
        sheng_id = province == null ? "" : "" + province.id;
        sheng_name = province == null ? "" : province.name;
        shi_id = city == null ? "" : "" + city.id;
        shi_name = city == null ? "" : city.name;
        dialog_select_diqu.dismiss();
        tvAddress.setText(shengshiqu);
    }

    @Override
    public void onProvinceSelected(Province province) {

        Map<String, String> params = new HashMap<>();
        params.put("cityID", province.id);
        MyHttpUtils.postHttpMessage("http://ttmag.xueyiche.vip:99/api/tatapublic/getCity", params, ProvinceAll.class, new RequestCallBack<ProvinceAll>() {
            @Override
            public void requestSuccess(ProvinceAll json) {
                if (json.getStatus() == 200) {
                    List<ProvinceAll.DataBean> data = json.getData();
                    List<City> list = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        City city = new City();
                        city.id = data.get(i).getAREA_ID();
                        city.name = data.get(i).getAREA_NAME();
                        list.add(city);
                        dialog_select_diqu.getSelector().setCities(list);
                    }
                } else {
                    showToastShort(json.getMsg());
                }
            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                showToastShort("服务器内部错误");
            }
        });
    }

    @Override
    public void onCitySelected(City city) {
        dialog_select_diqu.getSelector().setCountries(null);
    }

    @Override
    public void onCountySelected(County county) {
        dialog_select_diqu.getSelector().setStreets(null);
    }
}
