package com.xueyiche.zjyk.xueyiche.factory;
import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent.AllIndentFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int COUNT = 4;
    public static final int COUNTThree = 3;

    public static Map<Integer, Fragment> map = new HashMap<>();


    public static Fragment getIndentInstance(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new AllIndentFragment("0");
                    break;
                case 1:
                    fragment =new AllIndentFragment("1");

                    break;
                case 2:
                    fragment = new AllIndentFragment("2");
                    break;
                case 3:
                    fragment = new AllIndentFragment("3");
                    break;
            }
        }
        return fragment;
    }





}
