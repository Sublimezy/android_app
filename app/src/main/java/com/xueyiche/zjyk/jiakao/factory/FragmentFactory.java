package com.xueyiche.zjyk.jiakao.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.jiakao.exam.fragment.SubjectADFragment;
import com.xueyiche.zjyk.jiakao.exam.fragment.SubjectBFragment;
import com.xueyiche.zjyk.jiakao.exam.fragment.SubjectCFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int COUNT = 4;
    //    public static final int COUNT1 = 2;
    public static Map<Integer, Fragment> map = new HashMap<>();


    public static Fragment getExamInstance(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            Bundle args = new Bundle();
            switch (position) {
                case 0:
                    fragment = new SubjectADFragment();
                    args.putLong("subject", 1);
                    break;
                case 1:
                    fragment = new SubjectBFragment();
                    args.putLong("subject", 2);
                    break;
                case 2:
                    fragment = new SubjectCFragment();
                    args.putLong("subject", 3);
                    break;
                case 3:
                    fragment = new SubjectADFragment();
                    args.putLong("subject", 4);
                    break;
            }
            args.putString("model", "c1");
            fragment.setArguments(args);
        }
        return fragment;
    }

}
