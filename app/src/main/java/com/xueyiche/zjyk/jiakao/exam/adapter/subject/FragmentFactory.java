package com.xueyiche.zjyk.jiakao.exam.adapter.subject;

import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.jiakao.exam.fragment.subject.subject_ad.SubjectADFragment;
import com.xueyiche.zjyk.jiakao.exam.fragment.subject.subject_b.SubjectBFragment;
import com.xueyiche.zjyk.jiakao.exam.fragment.subject.subject_c.SubjectCFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int COUNT = 3;

    public static Map<Integer, Fragment> map = new HashMap<>();


    public static Fragment getExamInstance(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new SubjectADFragment();
                    break;
                case 1:
                    fragment = new SubjectBFragment();
                    break;
                case 2:
                    fragment = new SubjectCFragment();
                    break;
            }
        }
        return fragment;
    }

}
