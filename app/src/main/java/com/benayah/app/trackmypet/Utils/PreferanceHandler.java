package com.benayah.app.trackmypet.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Benayah on 29/6/2017.
 */

public class PreferanceHandler {

    SharedPreferences sh;
    public static PreferanceHandler preferanceHandler;

    private PreferanceHandler(Context context)
    {
        sh = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferanceHandler getInstance(Context context)
    {
        if(preferanceHandler == null)
        {
            preferanceHandler = new PreferanceHandler(context);
        }
        return preferanceHandler;
    }

    public void setUserLoginStatus(boolean loginStatus)
    {
        sh.edit().putBoolean(Constants.USER_LOGIN_STATUS,loginStatus).apply();
    }

    public boolean getUserLoginStatus()
    {
        return sh.getBoolean(Constants.USER_LOGIN_STATUS,false);
    }

    public void setUserName(String userEmail)
    {
        sh.edit().putString(Constants.USEREMAIL,userEmail).apply();
    }

    public String getUserName()
    {
        return sh.getString(Constants.USEREMAIL,"");
    }

    public void setPassword(String password)
    {
        sh.edit().putString(Constants.USERPASSWORD,password).apply();
    }
    public String getPassword()
    {
       return sh.getString(Constants.USERPASSWORD,"");
    }

    public void setUserId(String userId)
    {
        sh.edit().putString(Constants.USER_ID,userId).apply();
    }

    public String getUserId()
    {
        return sh.getString(Constants.USER_ID,"");
    }


}
