package com.xueyiche.zjyk.xueyiche.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.photoview.PhotoView;
import com.mob.MobSDK;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #            Created by 张磊 on 2020/6/15.                                       #
 */
public class SharedUtils {


    public static void ImageShowList(final Activity activity, final List<String> imageList, final int position, String imageString, String type) {
        final int[] selectPosition = {position};
        final PopupWindow pop = new PopupWindow(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.image_layout, null);
        final LinearLayout ll_popup = view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        ImageView tvDownLoad = view.findViewById(R.id.tvDownLoad);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                selectPosition[0] = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
                attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().setAttributes(attr);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        tvDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonwloadSaveImg.donwloadImg(activity, imageList.get(selectPosition[0]) + "+tt");
            }
        });
        viewPager.setAdapter(new ImageAdapter(imageList, imageString, type));
        viewPager.setCurrentItem(position);

        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                activity, R.anim.activity_translate_in));
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(lp);
    }

    public static class ImageAdapter extends PagerAdapter {
        private List<String> imageList;
        private String imageString;
        private String type;

        public ImageAdapter(List<String> imageList, String imageString, String type) {
            this.imageList = imageList;
            this.imageString = imageString;
            this.type = type;
        }

        @Override
        public int getCount() {
            if ("1".equals(type)) {
                return 1;
            } else if ("2".equals(type)) {
                return imageList.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            ImageView imageView = new ImageView(App.context);
            PhotoView imageView = new PhotoView(App.context);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if ("1".equals(type)) {
                Glide.with(App.context).load(imageString).placeholder(R.mipmap.about_xueyiche).error(R.mipmap.about_xueyiche).into(imageView);
            } else if ("2".equals(type)) {
                String address = imageList.get(position);
                Glide.with(App.context).load(address).placeholder(R.mipmap.about_xueyiche).error(R.mipmap.about_xueyiche).into(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
