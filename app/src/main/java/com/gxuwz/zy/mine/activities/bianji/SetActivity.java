package com.gxuwz.zy.mine.activities.bianji;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.allen.library.SuperTextView;
import com.gxuwz.zy.R;
import com.gxuwz.zy.base.BaseActivity;
import com.gxuwz.zy.constants.App;
import com.gxuwz.zy.constants.event.MyEvent;
import com.gxuwz.zy.mine.activities.DataCleanManager;
import com.gxuwz.zy.utils.ACache;
import com.gxuwz.zy.utils.AES;
import com.gxuwz.zy.utils.PrefUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SetActivity extends BaseActivity {

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
    @BindView(R.id.st_name)
    SuperTextView stName;
    @BindView(R.id.st_phone)
    SuperTextView stPhone;
    @BindView(R.id.st_bianji_card)
    SuperTextView stBianjiCard;
    @BindView(R.id.st_wechat)
    SuperTextView stWechat;
    @BindView(R.id.st_qq)
    SuperTextView stQq;
    @BindView(R.id.st_clean)
    SuperTextView stClean;
    @BindView(R.id.st_zhuxiao)
    SuperTextView st_zhuxiao;
    @BindView(R.id.st_exit)
    SuperTextView stExit;

    @Override
    protected int initContentView() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(rlTitle).statusBarDarkFont(true).init();
        tvTitle.setText("设置");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        AES mAes = new AES();
//
//        PrefUtils.putString(App.context, "user_phone", user_phone);
//        PrefUtils.putString(App.context, "user_name", user_name);
//        PrefUtils.putString(App.context, "user_cards", user_cards);

        String name = PrefUtils.getParameter("name");
        String card_num = PrefUtils.getParameter("card_num");
        stName.setRightString(name);
        String decrypt_user_cards = AES.decrypt(card_num);
        stBianjiCard.setRightString(decrypt_user_cards);
    }

    @OnClick({R.id.ll_common_back, R.id.st_name, R.id.st_zhuxiao, R.id.st_phone, R.id.st_bianji_card, R.id.st_wechat, R.id.st_qq, R.id.st_clean, R.id.st_exit})
    public void onClick(View view) {
        Intent intent1 = new Intent(App.context, InformationChangeActivity.class);
        switch (view.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.st_name:
                intent1.putExtra("change_information", "name");
                startActivity(intent1);
                break;
            case R.id.st_phone:
                break;
            case R.id.st_bianji_card:
                intent1.putExtra("change_information", "id_card");
                startActivity(intent1);
                break;
            case R.id.st_wechat:
                break;
            case R.id.st_qq:
                break;
            case R.id.st_clean:
                new AlertDialog.Builder(SetActivity.this)
                        .setTitle("清除缓存")
                        .setMessage("您是否清除缓存?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataCleanManager.clearAllCache(App.context);
                                ACache.get(SetActivity.this).clear();
                                showToastShort("您已成功清理缓存。");
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.st_exit:
                outLogin("是否退出登录？", "退出成功");
                break;
            case R.id.st_zhuxiao:
                outLogin("是否注销账号？", "注销成功");
                break;
        }
    }

    private void outLogin(String title, String toast) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.logo);
        builder.setTitle(title);
        //点击空白处弹框不消失
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //清空数据 退出登录
                PrefUtils.putBoolean(App.context, "ISLOGIN", false);
                PrefUtils.putString(App.context, "user_phone", "");
                PrefUtils.putString(App.context, "user_cards", "");
                PrefUtils.putString(App.context, "driver_cards", "");
                PrefUtils.putString(App.context, "nickname", "");
                PrefUtils.putString(App.context, "user_name", "");
                PrefUtils.putString(App.context, "order_status", "");
                PrefUtils.putString(App.context, "address", "");
                PrefUtils.putString(App.context, "randNum", "");
                PrefUtils.putString(App.context, "sex", "");
                PrefUtils.putString(App.context, "head_img", "");
                PrefUtils.putString(App.context, "user_id", "");
                PrefUtils.putString(App.context, "user_random", "");
                PrefUtils.clear(App.context);
                EventBus.getDefault().post(new MyEvent("刷新FragmentLogin"));
                EventBus.getDefault().post(new MyEvent("刷新Fragment"));
                ToastUtils.showToast(SetActivity.this, toast);
                finish();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.show();
    }
}