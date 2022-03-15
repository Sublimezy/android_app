package com.xueyiche.zjyk.xueyiche.discover.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.xueyiche.zjyk.xueyiche.factory.FragmentFactory;

/**
 * Created by ZL on 2017/11/20.
 */
public class DiscoverFragmentAdapter extends FragmentPagerAdapter {
    public DiscoverFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.dicover(position);
    }

    @Override
    public int getCount() {
        return FragmentFactory.COUNTThree;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
