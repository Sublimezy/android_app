package com.xueyiche.zjyk.xueyiche.base.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.xueyiche.zjyk.xueyiche.factory.FragmentFactory;


/**
 * Created by Owner on 2016/9/13.
 */
public class MyBasePagerAdapter extends FragmentPagerAdapter {

    public MyBasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getInstance(position);
    }

    @Override
    public int getCount() {
        return FragmentFactory.COUNT;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
