package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xueyiche.zjyk.xueyiche.factory.FragmentFactory;

/**
 * Created by zhanglei on 2016/11/16.
 */
public class IndentViewAdapter extends FragmentPagerAdapter {
    public IndentViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getIndentInstance(position);
    }

    @Override
    public int getCount() {
        return FragmentFactory.COUNT;
    }
}
