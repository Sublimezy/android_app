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
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
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
import com.xueyiche.zjyk.xueyiche.main.activities.main.BaseBean;
import com.xueyiche.zjyk.xueyiche.mine.bean.EntranceSaveBean;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.ImageCompressEngine;

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
        tvTitle.setText("司机报名");
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
        selectorStyle = new PictureSelectorStyle();
        PictureWindowAnimationStyle defaultAnimationStyle = new PictureWindowAnimationStyle();
        defaultAnimationStyle.setActivityEnterAnimation(R.anim.ps_anim_enter);
        defaultAnimationStyle.setActivityExitAnimation(R.anim.ps_anim_exit);
        selectorStyle.setWindowAnimationStyle(defaultAnimationStyle);
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


    }

    private PictureSelectorStyle selectorStyle;
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


    private final int CodeMianGuan = 101;
    private GridImageAdapter mAdapterMianGuan;
    private ArrayList<LocalMedia> selectListMianGuan;

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
                    previewPicture(position, mAdapterMianGuan);
                }
            }
        });
    }


    private final int CodeSfjZheng = 102;
    private GridImageAdapter mAdapterSfjZheng;
    private ArrayList<LocalMedia> selectListSfjZheng;

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
                    previewPicture(position, mAdapterSfjZheng);

                }
            }
        });
    }

    /**
     * 预览图片
     *
     * @param position
     * @param mAdapter
     */
    private void previewPicture(int position, GridImageAdapter mAdapter) {
        PictureSelector.create(RegistSiJiActivity.this)
                .openPreview()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectorUIStyle(selectorStyle)
                .isPreviewFullScreenMode(true)
                .setExternalPreviewEventListener(new MyExternalPreviewEventListener(mAdapter))
                .startActivityPreview(position, true, mAdapter.getData());
    }

    /**
     * 外部预览监听事件
     */
    private static class MyExternalPreviewEventListener implements OnExternalPreviewEventListener {
        private final GridImageAdapter adapter;

        public MyExternalPreviewEventListener(GridImageAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onPreviewDelete(int position) {
            adapter.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public boolean onLongPressDownload(LocalMedia media) {
            return false;
        }
    }

    private final int CodeSfzFan = 103;
    private GridImageAdapter mAdapterSfzFan;
    private ArrayList<LocalMedia> selectListSfzFan;

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
                    previewPicture(position, mAdapterSfzFan);
                }
            }
        });
    }


    private final int CodeJszZheng = 104;
    private GridImageAdapter mAdapterJszZheng;
    private ArrayList<LocalMedia> selectListJszZheng;

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
                    previewPicture(position, mAdapterJszZheng);
                }
            }
        });
    }


    private final int CodeJszFan = 105;
    private GridImageAdapter mAdapterJszFan;
    private ArrayList<LocalMedia> selectListJszFan;

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
                    previewPicture(position, mAdapterJszFan);
                }
            }
        });
    }


    private final int CodeXszZheng = 106;
    private GridImageAdapter mAdapterXszZheng;
    private ArrayList<LocalMedia> selectListXszZheng;

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
                    previewPicture(position, mAdapterXszZheng);
                }
            }
        });
    }


    private final int CodeXszFan = 107;
    private GridImageAdapter mAdapterXszFan;
    private ArrayList<LocalMedia> selectListXszFan;

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
                    previewPicture(position, mAdapterXszFan);
                }
            }
        });
    }


    private final int CodeCar1 = 108;
    private GridImageAdapter mAdapterCar1;
    private ArrayList<LocalMedia> selectListCar1;

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
                    previewPicture(position, mAdapterCar1);
                }
            }
        });
    }


    private final int CodeCar2 = 109;
    private GridImageAdapter mAdapterCar2;
    private ArrayList<LocalMedia> selectListCar2;

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
                    previewPicture(position, mAdapterCar2);
                }
            }
        });
    }


    private final int CodeCar3 = 110;
    private GridImageAdapter mAdapterCar3;
    private ArrayList<LocalMedia> selectListCar3;

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
                    previewPicture(position, mAdapterCar3);
                }
            }
        });
    }


    private final int CodeCar4 = 111;
    private GridImageAdapter mAdapterCar4;
    private ArrayList<LocalMedia> selectListCar4;

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
                    previewPicture(position, mAdapterCar4);
                }
            }
        });
    }


    HashMap<Integer, LocalMedia> selectedPhotos = new HashMap<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {


            case CodeMianGuan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListMianGuan = PictureSelector.obtainSelectorList(data);
                if (selectListMianGuan.size() > 0) {
                    selectedPhotos.put(0, selectListMianGuan.get(0));
                }
                mAdapterMianGuan.setList(selectListMianGuan);
                mAdapterMianGuan.notifyDataSetChanged();
                break;
            case CodeSfjZheng:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListSfjZheng = PictureSelector.obtainSelectorList(data);

                if (selectListSfjZheng.size() > 0) {
                    selectedPhotos.put(1, selectListSfjZheng.get(0));
                }
                mAdapterSfjZheng.setList(selectListSfjZheng);
                mAdapterSfjZheng.notifyDataSetChanged();
                break;
            case CodeSfzFan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListSfzFan = PictureSelector.obtainSelectorList(data);

                if (selectListSfzFan.size() > 0) {
                    selectedPhotos.put(2, selectListSfzFan.get(0));
                }
                mAdapterSfzFan.setList(selectListSfzFan);
                mAdapterSfzFan.notifyDataSetChanged();
                break;
            case CodeJszZheng:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListJszZheng = PictureSelector.obtainSelectorList(data);

                if (selectListJszZheng.size() > 0) {
                    selectedPhotos.put(3, selectListJszZheng.get(0));
                }
                mAdapterJszZheng.setList(selectListJszZheng);
                mAdapterJszZheng.notifyDataSetChanged();
                break;
            case CodeJszFan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListJszFan = PictureSelector.obtainSelectorList(data);

                if (selectListJszFan.size() > 0) {
                    selectedPhotos.put(4, selectListJszFan.get(0));
                }
                mAdapterJszFan.setList(selectListJszFan);
                mAdapterJszFan.notifyDataSetChanged();
                break;
            case CodeXszZheng:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListXszZheng = PictureSelector.obtainSelectorList(data);
                if (selectListXszZheng.size() > 0) {
                    selectedPhotos.put(5, selectListXszZheng.get(0));
                }
                mAdapterXszZheng.setList(selectListXszZheng);
                mAdapterXszZheng.notifyDataSetChanged();
                break;
            case CodeXszFan:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListXszFan = PictureSelector.obtainSelectorList(data);
                if (selectListXszFan.size() > 0) {
                    selectedPhotos.put(6, selectListXszFan.get(0));
                }
                mAdapterXszFan.setList(selectListXszFan);
                mAdapterXszFan.notifyDataSetChanged();
                break;
            case CodeCar1:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar1 = PictureSelector.obtainSelectorList(data);
                if (selectListCar1.size() > 0) {
                    selectedPhotos.put(7, selectListCar1.get(0));
                }
                mAdapterCar1.setList(selectListCar1);
                mAdapterCar1.notifyDataSetChanged();
                break;
            case CodeCar2:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar2 = PictureSelector.obtainSelectorList(data);
                if (selectListCar2.size() > 0) {
                    selectedPhotos.put(8, selectListCar2.get(0));
                }
                mAdapterCar2.setList(selectListCar2);
                mAdapterCar2.notifyDataSetChanged();
                break;
            case CodeCar3:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar3 = PictureSelector.obtainSelectorList(data);
                if (selectListCar3.size() > 0) {
                    selectedPhotos.put(9, selectListCar3.get(0));
                }
                mAdapterCar3.setList(selectListCar3);
                mAdapterCar3.notifyDataSetChanged();
                break;
            case CodeCar4:
                // 图片选择结果回调
                //     selectList = mAdapter.getData();
                selectListCar4 = PictureSelector.obtainSelectorList(data);
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
                .openGallery(SelectMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectorUIStyle(selectorStyle)// 动态自定义相册主题
                .setMaxSelectNum(9)// 最大图片选择数量
                .setMinSelectNum(1)// 最小选择数量
                .setImageSpanCount(4)// 每行显示个数
                .isPreviewImage(true) // 是否支持预览图片
                .isPreviewVideo(true) // 是否支持预览视频
                .isDisplayCamera(false)// 是否显示拍照按钮
                .setCompressEngine(new ImageCompressEngine());
    }


    public Context getContext() {
        return this;
    }


    String name = ""; // 姓名
    String sex = "";//  性别
    String jialing = "";//驾龄
    String phone = ""; //电话号
    String jiashizhenghao = "";//驾驶证号码
    String type_car = "0"; //是否有车  是否有车 0无 1有


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
                                fileCropUri = new File(media.getSandboxPath());
                            } else {
                                fileCropUri = new File(media.getRealPath());
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
                                        Map<String, String> params1 = new HashMap<>();
                                        params1.put("driver_name",name);      //姓名
                                        params1.put("sex","男".equals(sex) ? "1" : "0");      //性别
                                        params1.put("driving_year",jialing);     //驾龄
                                        params1.put("phone",phone);        //联系电话
                                        params1.put("driving_number",jiashizhenghao);       //驾驶证号
                                        params1.put("province",sheng_name);     //省份
                                        params1.put("city",shi_name);     //城市
                                        params1.put("provinceid",sheng_id);       //省份id
                                        params1.put("cityid",shi_id);       //城市id
                                        params1.put("head_img",pictureMap.get(0));     //免冠照片
                                        params1.put("id_front",pictureMap.get(2));     //身份证正面
                                        params1.put("id_back",pictureMap.get(1));      //身份证反面
                                        params1.put("driving_license_main",pictureMap.get(3));     //驾驶证正面
                                        params1.put("driving_license_vice",pictureMap.get(4));     //驾驶证反面
                                        params1.put("type_car",type_car);     //是否有车 0无 1有
                                        params1.put("travel_permit_url",pictureMap.get(5));        //行驶证正面
                                        params1.put("travel_permit_back",pictureMap.get(6));       //行驶证反面
                                        params1.put("brand_id",carPinPaiId);     //品牌ID
                                        params1.put("series_id",carCheXiId);        //系列ID
                                        params1.put("grace_45",pictureMap.get(7));     //正面45
                                        params1.put("grace_ce",pictureMap.get(8));     //侧面
                                        params1.put("grace_hou",pictureMap.get(9));        //后面
                                        params1.put("grace_jiashi ",pictureMap.get(10));        //驾驶室




                                        MyHttpUtils.postHttpMessage(AppUrl.entranceSave, params1, EntranceSaveBean.class, new RequestCallBack<EntranceSaveBean>() {
                                            @Override
                                            public void requestSuccess(EntranceSaveBean json) {

                                                if(json.getStatus() == 200){


                                                    finish();
                                                }
                                                showToastShort(json.getMsg());
                                            }

                                            @Override
                                            public void requestError(String errorMsg, int errorType) {
                                                showToastShort("连接服务器失败");
                                            }
                                        });


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
