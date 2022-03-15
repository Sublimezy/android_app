package com.xueyiche.zjyk.xueyiche.mine.activities.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xueyiche.zjyk.xueyiche.factory.FragmentFactory;

/**
 * Created by ZL on 2017/7/21.
 */
public class CollectionFragmentAdapter extends FragmentPagerAdapter{
    private String kind_type;
    public CollectionFragmentAdapter(FragmentManager fm,String kind_type) {
        super(fm);
        this.kind_type = kind_type;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.collection(position,kind_type);
    }

    @Override
    public int getCount() {
        return FragmentFactory.COUNT;
    }
}
