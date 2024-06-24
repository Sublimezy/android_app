package com.xueyiche.zjyk.jiakao.examtext;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xueyiche.zjyk.jiakao.factory.FragmentFactory;



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
