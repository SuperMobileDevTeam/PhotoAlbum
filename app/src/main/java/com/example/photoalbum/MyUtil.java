package com.example.photoalbum;

import android.content.Context;
import android.content.res.Configuration;

public class MyUtil {
    public static boolean isInNightMode(Context context){
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
}
