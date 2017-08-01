package com.benayah.app.trackmypet.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Benayah on 29/6/2017.
 */

public class CommonAppMember {

    public static void hideSoftKeyboard(Activity activity, View view) {

        InputMethodManager imm = (InputMethodManager) (activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        // View view = getView();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





}
