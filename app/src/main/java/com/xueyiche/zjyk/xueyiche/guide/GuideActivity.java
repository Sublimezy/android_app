 package com.xueyiche.zjyk.xueyiche.guide;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseActivity;
import com.xueyiche.zjyk.xueyiche.homepage.activities.location.LocationActivity;
import com.xueyiche.zjyk.xueyiche.splash.SplashActivity;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;

import java.util.ArrayList;

/**
 * Created by Owner on 2016/9/21.
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mVP_guide;
    private Button mBT_start;
    private ArrayList<ImageView> mImageList;
    // 到达最后一张
    private static final int TO_THE_END = 0;
    // 离开最后一张
    private static final int LEAVE_FROM_END = 1;


    @Override
    protected int initContentView() {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_guide;
    }
    @Override
    protected void initView() {
        XueYiCheUtils.getNowLocation(GuideActivity.this);
        mVP_guide = (ViewPager) findViewById(R.id.vp_guide);
        mBT_start = (Button) findViewById(R.id.bt_start);
        mBT_start.setOnClickListener(this);
        initData();
        mVP_guide.setAdapter(new GuideAdapter());
        mVP_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 页面选中
            @Override
            public void onPageSelected(int position) {
                if (position == mImageList.size() - 1) {
                    handler.sendEmptyMessageDelayed(TO_THE_END, 100);
                } else {
                    handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }
            // 状态改变
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TO_THE_END)
                mBT_start.setVisibility(View.VISIBLE);
            else if (msg.what == LEAVE_FROM_END)
                mBT_start.setVisibility(View.GONE);
        }
    };
    @Override
    protected void initListener() {
    }
    @Override
    protected void initData() {
//        int[] imageResIDs = {R.drawable.guide_1, R.drawable.guide_2,
//                R.drawable.guide_3, R.drawable.guide_4};
//        mImageList = new ArrayList<ImageView>();
//        for (int i = 0; i < imageResIDs.length; i++) {
//            ImageView image = new ImageView(this);
//            image.setBackgroundResource(imageResIDs[i]);// 注意设置背景, 才可以填充屏幕
//            mImageList.add(image);
//        }
    }
    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageList.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageList.get(position));
            return mImageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                // 开始体验
                PrefUtils.putBoolean(this, SplashActivity.PREF_IS_USER_GUIDE_SHOWED, true);// 记录已经展现过了新手引导页
                Intent intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

}
