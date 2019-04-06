package rana.jatin.core.util;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class ColorUtil {
    private static final ColorUtil ourInstance = new ColorUtil();

    private ColorUtil() {
    }

    public static ColorUtil getInstance() {
        return ourInstance;
    }

    /* return color using ContextCompat*/
    public int getColor(Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }
}
