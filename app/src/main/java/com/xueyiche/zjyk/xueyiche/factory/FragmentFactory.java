package com.xueyiche.zjyk.xueyiche.factory;

import androidx.fragment.app.Fragment;

import com.xueyiche.zjyk.xueyiche.carlife.fragment.XiCheFragment;
import com.xueyiche.zjyk.xueyiche.discover.fragment.DiscoverFragment;
import com.xueyiche.zjyk.xueyiche.discover.fragment.discover.SheQuFragment;
import com.xueyiche.zjyk.xueyiche.discover.fragment.discover.VideoFragment;
import com.xueyiche.zjyk.xueyiche.discover.fragment.discover.WenDaFragment;
import com.xueyiche.zjyk.xueyiche.examtext.examfragment.SubjectAFragment;
import com.xueyiche.zjyk.xueyiche.examtext.examfragment.SubjectBFragment;
import com.xueyiche.zjyk.xueyiche.examtext.examfragment.SubjectCFragment;
import com.xueyiche.zjyk.xueyiche.examtext.examfragment.SubjectDFragment;
import com.xueyiche.zjyk.xueyiche.homepage.fragments.HomePageFragment;
import com.xueyiche.zjyk.xueyiche.mine.fragments.MineFragment;
import com.xueyiche.zjyk.xueyiche.mine.fragments.collection.DriverCollection;
import com.xueyiche.zjyk.xueyiche.mine.fragments.collection.DriverSchoolCollection;
import com.xueyiche.zjyk.xueyiche.mine.fragments.collection.ShopCollection;
import com.xueyiche.zjyk.xueyiche.mine.fragments.collection.UsedCarCollection;
import com.xueyiche.zjyk.xueyiche.xycindent.fragments.indent.AllIndentFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
    public static final int COUNT = 4;
    public static final int COUNTThree = 3;

    public static Map<Integer, Fragment> map = new HashMap<>();

    public static Fragment getInstance(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomePageFragment();
                    break;
                case 1:
                    fragment = new XiCheFragment();
                    break;
                case 2:
                    fragment = new DiscoverFragment();
                    break;
                case 3:
                    fragment = new MineFragment();
                    break;
            }
        }
        return fragment;
    }

    public static Fragment getExamInstance(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new SubjectAFragment();
                    break;
                case 1:
                    fragment = new SubjectBFragment();

                    break;
                case 2:
                    fragment = new SubjectCFragment();
                    break;
                case 3:
                    fragment = new SubjectDFragment();
                    break;
            }
        }
        return fragment;
    }

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


    public static Fragment collection(int position,String kind_type) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new DriverSchoolCollection(kind_type);
                    break;
                case 1:
                    fragment = new DriverCollection(kind_type);
                    break;
                case 2:
                    fragment = new ShopCollection(kind_type);
                    break;
                case 3:
                    fragment = new UsedCarCollection(kind_type);
                    break;

            }
        }
        return fragment;
    }

    public static Fragment dicover(int position) {
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new SheQuFragment();
                    break;
                case 1:
                    fragment = new VideoFragment();
                    break;
                case 2:
                    fragment = new WenDaFragment();
                    break;

            }
        }
        return fragment;
    }


}
