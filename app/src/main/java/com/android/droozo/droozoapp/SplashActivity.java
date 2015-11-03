package com.android.droozo.droozoapp;

/**
 * Splash Screen
 *
 * @author GirnarSoft
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.droozo.util.CommonMethods;
import com.android.droozo.util.Utility;

public class SplashActivity extends Activity implements AnimationListener {

    private Activity _activitySplash;
    private ImageView splashCenterImgLogo;
    private Animation animFadein;
    private CommonMethods _commonMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        setBodyUI();

    }// end onCreate()------------------

    private void setBodyUI() {
        _activitySplash = this;
        _commonMethods = new CommonMethods(_activitySplash, this);
        splashCenterImgLogo = (ImageView) findViewById(R.id.splashCenterImgLogo);

        animFadein = AnimationUtils.loadAnimation(_activitySplash,
                R.anim.fade_in);
        animFadein.setAnimationListener(this);
        splashCenterImgLogo.startAnimation(animFadein);

    }// end setBodyUI()----------------

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        int user_id = Utility.getPreferencesInteger(_activitySplash, "USERID");

        if (user_id == 0) {
            Utility.getUtilityInstance().doStartActivityWithFinish(_activitySplash, IntroSlideActivity.class, "right");
        } else {
            Utility.getUtilityInstance().doStartActivityWithFinish(_activitySplash, WelcomeActivity.class, "right");
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub
    }

}// end main class------------------