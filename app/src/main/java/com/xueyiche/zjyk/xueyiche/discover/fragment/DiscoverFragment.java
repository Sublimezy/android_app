package com.xueyiche.zjyk.xueyiche.discover.fragment;

import androidx.fragment.app.FragmentManager;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.discover.adapter.DiscoverFragmentAdapter;
import com.xueyiche.zjyk.xueyiche.main.view.NoScrollViewPager;

public class DiscoverFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private boolean isPrepared = true;
    private RadioGroup rg_all;
    private NoScrollViewPager viewPager;
    private ImageView iv_zixun,iv_shipin,iv_wenda;
    private RadioButton rb_shequ, rb_video, rb_wenda;
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.discover_fragment, null);
        viewPager = (NoScrollViewPager) view.findViewById(R.id.czh_view_pager);
        viewPager.setOffscreenPageLimit(0);
        rg_all = (RadioGroup) view.findViewById(R.id.rg_all);
        rg_all.setOnCheckedChangeListener(this);
        iv_zixun = view.findViewById(R.id.iv_zixun);
        iv_shipin = view.findViewById(R.id.iv_shipin);
        iv_wenda = view.findViewById(R.id.iv_wenda);
        iv_zixun.setVisibility(View.VISIBLE);
        iv_shipin.setVisibility(View.GONE);
        iv_wenda.setVisibility(View.GONE);
        rb_shequ = view.findViewById(R.id.rb_shequ);
        rb_video = view.findViewById(R.id.rb_video);
        rb_wenda = view.findViewById(R.id.rb_wenda);

        rb_shequ.setOnClickListener(this);
        rb_video.setOnClickListener(this);
        rb_wenda.setOnClickListener(this);

        rb_shequ.setTypeface(null, Typeface.BOLD);
        rb_video.setTypeface(null, Typeface.NORMAL);
        rb_wenda.setTypeface(null, Typeface.NORMAL);
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        viewPager.setAdapter(new DiscoverFragmentAdapter(supportFragmentManager));
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_shequ:
                iv_zixun.setVisibility(View.VISIBLE);
                iv_shipin.setVisibility(View.GONE);
                iv_wenda.setVisibility(View.GONE);

                rb_shequ.setTypeface(null, Typeface.BOLD);
                rb_video.setTypeface(null, Typeface.NORMAL);
                rb_wenda.setTypeface(null, Typeface.NORMAL);
                viewPager.setCurrentItem(0,false);//禁用动画
                break;
            case R.id.rb_video:
                iv_zixun.setVisibility(View.GONE);
                iv_shipin.setVisibility(View.VISIBLE);
                iv_wenda.setVisibility(View.GONE);
                rb_shequ.setTypeface(null, Typeface.NORMAL);
                rb_video.setTypeface(null, Typeface.BOLD);
                rb_wenda.setTypeface(null, Typeface.NORMAL);
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.rb_wenda:
                iv_zixun.setVisibility(View.GONE);
                iv_shipin.setVisibility(View.GONE);
                iv_wenda.setVisibility(View.VISIBLE);
                rb_shequ.setTypeface(null, Typeface.NORMAL);
                rb_video.setTypeface(null, Typeface.NORMAL);
                rb_wenda.setTypeface(null, Typeface.BOLD);
                viewPager.setCurrentItem(2,false);
                break;
        }
    }

    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_shequ:
                iv_zixun.setVisibility(View.VISIBLE);
                iv_shipin.setVisibility(View.GONE);
                iv_wenda.setVisibility(View.GONE);

                rb_shequ.setTypeface(null, Typeface.BOLD);
                rb_video.setTypeface(null, Typeface.NORMAL);
                rb_wenda.setTypeface(null, Typeface.NORMAL);
                viewPager.setCurrentItem(0,false);//禁用动画
                break;
            case R.id.rb_video:
                iv_zixun.setVisibility(View.GONE);
                iv_shipin.setVisibility(View.VISIBLE);
                iv_wenda.setVisibility(View.GONE);
                rb_shequ.setTypeface(null, Typeface.NORMAL);
                rb_video.setTypeface(null, Typeface.BOLD);
                rb_wenda.setTypeface(null, Typeface.NORMAL);
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.rb_wenda:
                iv_zixun.setVisibility(View.GONE);
                iv_shipin.setVisibility(View.GONE);
                iv_wenda.setVisibility(View.VISIBLE);
                rb_shequ.setTypeface(null, Typeface.NORMAL);
                rb_video.setTypeface(null, Typeface.NORMAL);
                rb_wenda.setTypeface(null, Typeface.BOLD);
                viewPager.setCurrentItem(2,false);
                break;
        }
    }
}
