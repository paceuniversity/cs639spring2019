package com.pace.cs639spring.hw4;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by kachi on 3/8/18.
 */

public class Util {

    public static int dpToPx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }
}
