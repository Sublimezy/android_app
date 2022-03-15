package com.xueyiche.zjyk.xueyiche.base.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xueyiche.zjyk.xueyiche.factory.FragmentFactory;


/**
 * Created by Owner on 2016/9/13.
 */
public class MyExamAdapter extends FragmentPagerAdapter {




    public MyExamAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getExamInstance(position);
    }


    @Override
    public int getCount() {
        return FragmentFactory.COUNT;
    }
}
