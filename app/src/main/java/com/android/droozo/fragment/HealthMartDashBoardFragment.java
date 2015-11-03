package com.android.droozo.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.droozo.adapter.CustomBannerAdapter;
import com.android.droozo.droozoapp.R;
import com.android.droozo.fragment_mart.MartInnerFragment0;
import com.android.droozo.fragment_mart.MartInnerFragment1;
import com.android.droozo.fragment_mart.MartInnerFragment2;
import com.android.droozo.fragment_mart.MartInnerFragment3;
import com.android.droozo.fragment_mart.MartInnerFragment4;
import com.android.droozo.fragment_mart.MartInnerFragment5;
import com.android.droozo.fragment_mart.MartInnerFragment6;
import com.android.droozo.fragment_mart.MartInnerFragment7;
import com.android.droozo.fragment_mart.MartInnerFragment8;
import com.android.droozo.fragment_mart.MartInnerFragment9;
import com.android.droozo.util.NoSlideViewPager;
import com.astuetz.PagerSlidingTabStrip;
import com.viewpagerindicator.CirclePageIndicator;

public class HealthMartDashBoardFragment extends Fragment {
    private View view;
    private Toolbar mToolbar;
    private final Handler handler = new Handler();
    private PagerSlidingTabStrip tabs;
    private ViewPager pagerBanner;
    private NoSlideViewPager pager;
    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor = 0x66FF00;


    private static HealthMartDashBoardFragment fragment;

    public static HealthMartDashBoardFragment newInstance() {
        if (fragment == null) {
            fragment = new HealthMartDashBoardFragment();
        }

        return fragment;
    }

    public HealthMartDashBoardFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_health_mart_dash_board, container, false);
        setBodyUI();

        return view;
    }

    private void setBodyUI() {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.blueBG));
        TextView txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("DROOMART");

        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager = (NoSlideViewPager) view.findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        pager.setPagingEnabled(false);

        tabs.setViewPager(pager);
        tabs.setTextColor(getResources().getColor(R.color.txtGreyColor));
        tabs.setIndicatorColor(getResources().getColor(R.color.green_bg));


        pagerBanner = (ViewPager) view.findViewById(R.id.pagerBanner);
        pagerBanner.setAdapter(new CustomBannerAdapter(getActivity()));

        CirclePageIndicator mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        mIndicator.setViewPager(pagerBanner);
    }

    /**
     * used for set fragment on View Pager
     */
    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] TITLES = {"All", "Supplements", "Weight Loss", "Bones & Joints", "Supplements1", "Supplements2", "Supplements3", "Supplements4"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MartInnerFragment0.newInstance(position);
                case 1:
                    return MartInnerFragment1.newInstance(position);
                case 2:
                    return MartInnerFragment2.newInstance(position);
                case 3:
                    return MartInnerFragment3.newInstance(position);
                case 4:
                    return MartInnerFragment4.newInstance(position);
                case 5:
                    return MartInnerFragment5.newInstance(position);
                case 6:
                    return MartInnerFragment6.newInstance(position);
                case 7:
                    return MartInnerFragment7.newInstance(position);
                case 8:
                    return MartInnerFragment8.newInstance(position);
                case 9:
                    return MartInnerFragment9.newInstance(position);
                default:
                    throw new IllegalArgumentException("The item position should be less or equal to:" + 1);
            }
        }
    }//end MyPagerAdapter--------------------------
}
