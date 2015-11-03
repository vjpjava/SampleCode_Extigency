package com.android.droozo.droozoapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.droozo.adapter.MyFragment;
import com.android.droozo.adapter.MyPageAdapter;
import com.android.droozo.util.CustomViewPager;
import com.viewpagerindicator.CirclePageIndicator;

public class IntroSlideActivity extends FragmentActivity {

    public CustomViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Activity _activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        List<Fragment> fragments = getFragments();

        MyPageAdapter pageAdapter = new MyPageAdapter(
                getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pageAdapter);

        CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(MyFragment.newInstance("Fragment 1", 1));
        fList.add(MyFragment.newInstance("Fragment 2", 2));
        fList.add(MyFragment.newInstance("Fragment 3", 3));
        fList.add(MyFragment.newInstance("Fragment 4", 4));
        return fList;

    }

}