package com.android.droozo.droozoapp;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.droozo.adapter.GridMartAdapter;
import com.android.droozo.util.Utility;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Show item details and choose ur requirement and change also
 * <p/>
 * Created by GSS-Vishnu Kanton 6/10/15.
 */
public class SingleItemActivity extends AppCompatActivity {

    private Activity _activity = this;
    private float width_device, height_device;
    private MyPagerAdapter myPagerAdapter;
    private RelativeLayout llViewPager;
    private TextView txtName, txtOrgPrice, txtPrePrice, txtSave;
    private GridView gridView;
    private static int pos;
    private Toolbar mToolbar;
    private TextView txtHeader;
    private ImageView imgCart;
    private TextView mTxtNoItemCart;
    private int HowManyIteminCart = 0;
    private int txtAddCartORGoToCart = 0;
    private TextView txtOnAddCartBtn;

    private LinearLayout llAddToCart, llBuyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_details);
        deviceHeightWid();
        toolBarMaintain();
        setBodyUI();
    }// end onCreate()----------------

    /**
     * toolbar view arrange according to page
     */
    private void toolBarMaintain() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setBackgroundColor(getResources().getColor(R.color.blueBG));
        txtHeader = (TextView) mToolbar
                .findViewById(R.id.toolbar_title);
        txtHeader.setText("DROOMART");

        mTxtNoItemCart = (TextView) mToolbar
                .findViewById(R.id.txtnoitemonCart);

        // check no. of item in cart if have then show otherwise invisible
        if (HowManyIteminCart > 0) {
            mTxtNoItemCart.setVisibility(View.VISIBLE);
            mTxtNoItemCart.setText("" + HowManyIteminCart);
        } else {
            mTxtNoItemCart.setVisibility(View.INVISIBLE);
        }


        imgCart = (ImageView) mToolbar
                .findViewById(R.id.imgRight);
        imgCart.setVisibility(View.VISIBLE);
        imgCart.setBackgroundResource(R.drawable.cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(_activity, MyCartActivity.class, "right");
            }
        });

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }// end toolBarMaintain()------------------


    /**
     * initialize view
     */
    private void setBodyUI() {

        txtName = (TextView) findViewById(R.id.txtName);
        txtOrgPrice = (TextView) findViewById(R.id.txtOrgPrice);

        txtPrePrice = (TextView) findViewById(R.id.txtPrePrice);
        txtPrePrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        txtPrePrice.setText("$150");

        // find view and check text will be Add To Cart or Go To Cart
        txtOnAddCartBtn = (TextView) findViewById(R.id.txtOnAddCartBtn);
        checkWhichTextAppearOnCartBtn();


        txtSave = (TextView) findViewById(R.id.txtSave);

        llViewPager = (RelativeLayout) findViewById(R.id.llViewPager);
        final ViewPager pager = (ViewPager) findViewById(R.id.myviewpager);
        llViewPager.getLayoutParams().height = ((int) width_device) - 100;
        llViewPager.getLayoutParams().width = (((int) width_device) - 30);
        myPagerAdapter = new MyPagerAdapter();
        pager.setAdapter(myPagerAdapter);

        CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new GridMartAdapter(_activity, 1));


        llAddToCart = (LinearLayout) findViewById(R.id.llAddCart);
        llBuyNow = (LinearLayout) findViewById(R.id.llBuyNow);

        llAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartProcess();
            }
        });

        llBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doStartActivityWithoutFinish(_activity, MyCartActivity.class, "right");
            }
        });

    }// end setBodyUI()--------------

    /**
     * perform action when click on AddtoCart btn and GoToCart
     */
    private void addToCartProcess() {
        if (txtAddCartORGoToCart == 0) {
            mTxtNoItemCart.setVisibility(View.VISIBLE);
            mTxtNoItemCart.setText("" + ++HowManyIteminCart);
            txtAddCartORGoToCart++;
            checkWhichTextAppearOnCartBtn();
        } else {
            Utility.doStartActivityWithoutFinish(_activity, MyCartActivity.class, "right");
        }
    }

    /**
     * Which text appear on Add to Cart Button
     */
    private void checkWhichTextAppearOnCartBtn() {
        if (txtAddCartORGoToCart == 0) {
            txtOnAddCartBtn.setText("ADD TO CART");
        } else {
            txtOnAddCartBtn.setText("GO TO CART");
        }
    }


    private void deviceHeightWid() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width_device = dm.widthPixels;
        height_device = dm.heightPixels;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.getUtilityInstance().doAnim(_activity, "left");
    }

    private class MyPagerAdapter extends PagerAdapter {

        public MyPagerAdapter() {
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int pos) {

            ImageView imageView = new ImageView(_activity);
            imageView.setBackgroundResource(R.drawable.largeimg);

            LayoutParams imageParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayout layout = new LinearLayout(_activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layout.setLayoutParams(layoutParams);
            layout.addView(imageView);

            // final int page = position;
            layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }// end MyPagerAdapter-----------

}// end main class--------------------------
