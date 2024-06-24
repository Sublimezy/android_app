package com.xueyiche.zjyk.jiakao.factory;
import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.jiakao.examtext.examfragment.SubjectAFragment;
import com.xueyiche.zjyk.jiakao.examtext.examfragment.SubjectBFragment;
import com.xueyiche.zjyk.jiakao.examtext.examfragment.SubjectCFragment;
import com.xueyiche.zjyk.jiakao.examtext.examfragment.SubjectDFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int COUNT = 4;
    public static final int COUNT1 = 2;
    public static Map<Integer, Fragment> map = new HashMap<>();





    public static Fragment getExamInstance(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new SubjectAFragment();
                    break;
//                case 1:
//                    fragment = new SubjectBFragment();
//
//                    break;
//                case 2:
//                    fragment = new SubjectCFragment();
//                    break;
                case 1:
                    fragment = new SubjectDFragment();
                    break;
            }
        }
        return fragment;
    }

}
