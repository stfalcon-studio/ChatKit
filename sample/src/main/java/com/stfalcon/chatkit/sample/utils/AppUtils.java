package com.stfalcon.chatkit.sample.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

/*
 * Created by troy379 on 04.04.17.
 */
public class AppUtils {

    public static void showToast(Context context, @StringRes int text, boolean isLong) {
        showToast(context, context.getString(text), isLong);
    }

    public static void showToast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}