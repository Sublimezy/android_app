package com.xueyiche.zjyk.jiakao.exam.adapter.subject;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


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
