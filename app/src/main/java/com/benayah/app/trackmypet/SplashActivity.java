package com.benayah.app.trackmypet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.benayah.app.trackmypet.Utils.Constants;
import com.benayah.app.trackmypet.Utils.PreferanceHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Benayah on 28/6/2017.
 */

public class SplashActivity extends AppCompatActivity {

    public static final int SPLASH_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.splash_screen);

        if(!checkPlayServices())
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.google_play_update),Toast.LENGTH_SHORT).show();
        }
        //checkPermission();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent homeIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(homeIntent);

            }
        },SPLASH_TIME);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
//          	Log.i(this.getClass().getCanonicalName(), "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
