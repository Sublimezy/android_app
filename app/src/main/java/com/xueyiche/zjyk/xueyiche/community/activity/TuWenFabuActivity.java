package com.xueyiche.zjyk.xueyiche.community.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.utils.SandboxTransformUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.BaseActivity;
import com.xueyiche.zjyk.xueyiche.community.bean.CallSuccessBean;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.constants.StringConstants;
import com.xueyiche.zjyk.xueyiche.constants.bean.TokenBean;
import com.xueyiche.zjyk.xueyiche.constants.event.MyEvent;
import com.xueyiche.zjyk.xueyiche.daijia.picture.DragListener;
import com.xueyiche.zjyk.xueyiche.daijia.picture.FullyGridLayoutManager;
import com.xueyiche.zjyk.xueyiche.daijia.picture.GlideEngine;
import com.xueyiche.zjyk.xueyiche.daijia.picture.GridImageAdapter;
import com.xueyiche.zjyk.xueyiche.daijia.picture.NormalGridImageAdapter;
import com.xueyiche.zjyk.xueyiche.daijia.picture.OnItemClickListener;
import com.xueyiche.zjyk.xueyiche.myhttp.MyHttpUtils;
import com.xueyiche.zjyk.xueyiche.myhttp.RequestCallBack;
import com.xueyiche.zjyk.xueyiche.utils.ImageCompressEngine;
import com.xueyiche.zjyk.xueyiche.utils.ScreenUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class TuWenFabuActivity extends BaseActivity {


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
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_delete_text)
    TextView tvDeleteText;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    private UploadManager uploadManager;

    @Override
    protected int initContentView() {
        return R.layout.activity_tu_wen_fabu;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initListener() {

    }

    private PictureSelectorStyle selectorStyle;

    @Override
    protected void initData() {
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("发布图文");
        tvRightBtn.setVisibility(View.GONE);
        tvRemark.setVisibility(View.VISIBLE);
        tvRemark.setText("发布");

        uploadManager = new UploadManager();
        selectorStyle = new PictureSelectorStyle();

        PictureWindowAnimationStyle defaultAnimationStyle = new PictureWindowAnimationStyle();
        defaultAnimationStyle.setActivityEnterAnimation(R.anim.ps_anim_enter);
        defaultAnimationStyle.setActivityExitAnimation(R.anim.ps_anim_exit);
        selectorStyle.setWindowAnimationStyle(defaultAnimationStyle);
        initRecycler();
        tvRemark.setClickable(true);
    }


    @OnClick({R.id.ll_common_back, R.id.tvRemark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.tvRemark:
                String trim = etName.getText().toString().trim();
                if (TextUtils.isEmpty(trim) && selectList.size() == 0) {
                    showToastShort("请输入内容或选择图片");
                    return;
                }
                if (selectList.size() == 0) {
                    tvRemark.setClickable(false);
                    Map<String, String> params = new HashMap<>();
                    params.put("typedata", "2");
                    params.put("title", trim);
                    params.put("content", trim);
                    MyHttpUtils.postHttpMessage(AppUrl.community_up, params, CallSuccessBean.class, new RequestCallBack<CallSuccessBean>() {
                        @Override
                        public void requestSuccess(CallSuccessBean json) {
                            stopProgressDialog();
                            if (1 == json.getCode()) {
                                showToastShort(json.getMsg());
                                EventBus.getDefault().post(new MyEvent("图文发布成功", "刷新话题"));
//                                                    tvRemark2.setClickable(true);
                                finish();
                            } else {
                                showToastShort(json.getMsg());
                                tvRemark.setClickable(true);
                            }
                        }

                        @Override
                        public void requestError(String errorMsg, int errorType) {
                            stopProgressDialog();

                            tvRemark.setClickable(true);
                        }
                    });
                } else {

                    for (int i = 0; i < selectList.size(); i++) {
                        Calendar calendar = Calendar.getInstance();
                        long timeInMillis = calendar.getTimeInMillis();
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tvRemark.setClickable(false);
                        upLoadPicToQiNiu("fx_tuwen" + timeInMillis + ".jpg", i, trim);

                    }
                }


                break;
        }
    }

    Map<Integer, String> pictureMap = new HashMap<>();  //图片上传的顺序

    private void upLoadPicToQiNiu(final String key, final int i, String title) {
        showProgressDialog(false, "正在发布...");
        Map<String, String> params = new HashMap<>();
        params.put("key", key);
        MyHttpUtils.postHttpMessage(AppUrl.TOUXIANG, params, TokenBean.class, new RequestCallBack<TokenBean>() {
            @Override
            public void requestSuccess(TokenBean json) {
                if (json.getCode() == 200) {
                    String token = json.getContent().getToken();
                    if (!TextUtils.isEmpty(token)) {
                        File fileCropUri;

//                        if (TextUtils.isEmpty(selectList.get(i).getCompressPath())) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {

                            fileCropUri = new File(selectList.get(i).getSandboxPath());

                        } else {

                            fileCropUri = new File(selectList.get(i).getRealPath());
                        }
//                        } else {
//
//                            fileCropUri = new File(selectList.get(i).getCompressPath());
//                        }
                        uploadManager.put(fileCropUri, key, token, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    pictureMap.put(i, StringConstants.QiNiu + key);
                                    Log.e("qiniu", "Upload Success" + key);
                                    if (selectList.size() == pictureMap.size()) {
                                        String multi_graph = "";
                                        Iterator<Integer> iterator = pictureMap.keySet().iterator();
                                        while (iterator.hasNext()) {
                                            multi_graph += pictureMap.get(iterator.next()) + ",";
                                        }
                                        String substring = multi_graph.substring(0, multi_graph.length() - 1);
                                        //全部上传完了  发送数据
                                        Map<String, String> params = new HashMap<>();
                                        params.put("typedata", "2");
                                        params.put("title", title);
                                        params.put("images", substring);
//                                        params.put("video_file", circle_id);
                                        params.put("content", title);
                                        MyHttpUtils.postHttpMessage(AppUrl.community_up, params, CallSuccessBean.class, new RequestCallBack<CallSuccessBean>() {
                                            @Override
                                            public void requestSuccess(CallSuccessBean json) {
                                                stopProgressDialog();
                                                if (1 == json.getCode()) {
                                                    showToastShort(json.getMsg());
                                                    EventBus.getDefault().post(new MyEvent("图文发布成功", "刷新话题"));
//                                                    tvRemark2.setClickable(true);
                                                    finish();
                                                } else {
                                                    showToastShort(json.getMsg());
                                                    tvRemark.setClickable(true);
                                                }
                                            }

                                            @Override
                                            public void requestError(String errorMsg, int errorType) {
                                                stopProgressDialog();

                                                tvRemark.setClickable(true);
                                            }
                                        });
                                    }
                                } else {
                                    tvRemark.setClickable(true);
                                    stopProgressDialog();
                                    String error = info.error;
                                    Log.e("qiniu", "Upload 失败" + key + "response" + response);
                                    showToastShort("上传失败,请重新尝试!");
                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                }
                            }

                        }, null);
                    }
                }

            }

            @Override
            public void requestError(String errorMsg, int errorType) {
                tvRemark.setClickable(true);
                stopProgressDialog();
                Log.e("qiniu", "errorMsg 失败" + key + "   errorMsg   " + errorMsg);
//                String error = info.error;
                showToastShort("上传失败,请重新尝试2!");
            }
        });
    }


    private NormalGridImageAdapter mAdapter;

    /**
     * 可以拖拽的 相册选择
     */
    private void initRecycler() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, ScreenUtils.dip2px(TuWenFabuActivity.this, 8), false));
        mAdapter = new NormalGridImageAdapter(TuWenFabuActivity.this, onAddPicClickListener);

        mAdapter.setSelectMax(9);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                List<LocalMedia> selectList = mAdapter.getData();
                if (selectList.size() > 0) {
                    //预览
                    PictureSelector.create(TuWenFabuActivity.this)
                            .openPreview()
                            .setImageEngine(GlideEngine.createGlideEngine())
                            .setSelectorUIStyle(selectorStyle)
                            .isPreviewFullScreenMode(true)
                            .setExternalPreviewEventListener(new MyExternalPreviewEventListener(mAdapter))
                            .startActivityPreview(position, true, mAdapter.getData());

                }

            }
        });

        mAdapter.setItemLongClickListener((holder, position, v) -> {
            //如果item不是最后一个，则执行拖拽
            needScaleBig = true;
            needScaleSmall = true;
            int size = mAdapter.getData().size();
            if (size != 9) {
                mItemTouchHelper.startDrag(holder);
                return;
            }
            if (holder.getLayoutPosition() != size - 1) {
                mItemTouchHelper.startDrag(holder);
            }
        });

        mDragListener = new DragListener() {
            @Override
            public void deleteState(boolean isDelete) {
                if (isDelete) {
                    tvDeleteText.setText(getString(R.string.app_let_go_drag_delete));
                    tvDeleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_let_go_delete, 0, 0);
                } else {
                    tvDeleteText.setText(getString(R.string.app_drag_delete));
                    tvDeleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.picture_icon_delete, 0, 0);
                }

            }

            @Override
            public void dragState(boolean isStart) {
                int visibility = tvDeleteText.getVisibility();
                if (isStart) {
                    if (visibility == View.GONE) {
                        tvDeleteText.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
                        tvDeleteText.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (visibility == View.VISIBLE) {
                        tvDeleteText.animate().alpha(0).setDuration(300).setInterpolator(new AccelerateInterpolator());
                        tvDeleteText.setVisibility(View.GONE);
                    }
                }
            }
        };
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(0.7f);
                }
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到item原来的position
                try {
                    int fromPosition = viewHolder.getAdapterPosition();
                    //得到目标position
                    int toPosition = target.getAdapterPosition();
                    int itemViewType = target.getItemViewType();
                    if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                        if (fromPosition < toPosition) {
                            for (int i = fromPosition; i < toPosition; i++) {
                                Collections.swap(mAdapter.getData(), i, i + 1);
                            }
                        } else {
                            for (int i = fromPosition; i > toPosition; i--) {
                                Collections.swap(mAdapter.getData(), i, i - 1);
                            }
                        }
                        mAdapter.notifyItemMoved(fromPosition, toPosition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    if (null == mDragListener) {
                        return;
                    }
                    if (needScaleBig) {
                        //如果需要执行放大动画
                        viewHolder.itemView.animate().scaleXBy(0.1f).scaleYBy(0.1f).setDuration(100);
                        //执行完成放大动画,标记改掉
                        needScaleBig = false;
                        //默认不需要执行缩小动画，当执行完成放大 并且松手后才允许执行
                        needScaleSmall = false;
                    }
                    int sh = recyclerView.getHeight() + tvDeleteText.getHeight();
                    int ry = tvDeleteText.getBottom() - sh;
                    if (dY >= ry) {
                        //拖到删除处
                        mDragListener.deleteState(true);
                        if (isUpward) {
                            //在删除处放手，则删除item
                            viewHolder.itemView.setVisibility(View.INVISIBLE);
                            mAdapter.delete(viewHolder.getAdapterPosition());
                            resetState();
                            return;
                        }
                    } else {//没有到删除处
                        if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {
                            //如果viewHolder不可见，则表示用户放手，重置删除区域状态
                            mDragListener.dragState(false);
                        }
                        if (needScaleSmall) {//需要松手后才能执行
                            viewHolder.itemView.animate().scaleXBy(1f).scaleYBy(1f).setDuration(100);
                        }
                        mDragListener.deleteState(false);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                int itemViewType = viewHolder != null ? viewHolder.getItemViewType() : GridImageAdapter.TYPE_CAMERA;
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && mDragListener != null) {
                        mDragListener.dragState(true);
                    }
                    super.onSelectedChanged(viewHolder, actionState);
                }
            }

            @Override
            public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
                needScaleSmall = true;
                isUpward = true;
                return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(1.0f);
                    super.clearView(recyclerView, viewHolder);
                    mAdapter.notifyDataSetChanged();
                    resetState();
                }
            }
        });

        // 绑定拖拽事件
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    /**
     * 外部预览监听事件
     */
    private static class MyExternalPreviewEventListener implements OnExternalPreviewEventListener {
        private final NormalGridImageAdapter adapter;

        public MyExternalPreviewEventListener(NormalGridImageAdapter adapter) {
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

    /**
     * 重置
     */
    private void resetState() {
        if (mDragListener != null) {
            mDragListener.deleteState(false);
            mDragListener.dragState(false);
        }
        isUpward = false;
    }

    private boolean isUpward;
    private boolean needScaleBig = true;
    private boolean needScaleSmall = true;
    private ItemTouchHelper mItemTouchHelper;
    private DragListener mDragListener;
    private NormalGridImageAdapter.onAddPicClickListener onAddPicClickListener = new NormalGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(TuWenFabuActivity.this)
                    .openGallery(SelectMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .setSelectorUIStyle(selectorStyle)// 动态自定义相册主题
                    .setMaxSelectNum(9)// 最大图片选择数量
                    .setMinSelectNum(1)// 最小选择数量
                    .setImageSpanCount(4)// 每行显示个数
                    .isPreviewImage(true) // 是否支持预览图片
                    .isPreviewVideo(true) // 是否支持预览视频
                    .isDisplayCamera(false)// 是否显示拍照按钮
//                    .setCompressEngine(new ImageCompressEngine())
                    .setSandboxFileEngine(new UriToFileTransformEngine() {
                        @Override
                        public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
                            if (call != null) {
                                String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType);
                                call.onCallback(srcPath, sandboxPath);
                            }
                        }
                    })
                    .setSelectedData(mAdapter.getData())// 是否传入已选图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };
    private ArrayList<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                selectList = PictureSelector.obtainSelectorList(data);
                mAdapter.setList(selectList);
                mAdapter.notifyDataSetChanged();


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}