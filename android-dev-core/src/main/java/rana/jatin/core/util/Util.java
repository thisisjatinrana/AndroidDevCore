package rana.jatin.core.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Util class for various common tasks
 */

public class Util {

    private static final Util ourInstance = new Util();
    private final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private Util() {
    }

    public static Util getInstance() {
        return ourInstance;
    }

    /* create model class from string response
    *  @param json string response
    *  @param cls type of model class
    *  return generic class
    */
    public <T> T fromJson(String json, Class<T> cls) {
        Gson gson = new Gson();
        if (json != null && !json.isEmpty()) {
            return gson.fromJson(json, cls);
        }
        return null;
    }

    /* convert model class to string
    *  @param cls type of model class
    *  return string response
    */
    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /* create model class from string response
    *  @param json string response
    *  @param type Type Token of model class
    *  return generic class
    */
    public <T> T fromJson(String json, Type type) {
        Gson gson = new Gson();
        if (json != null && !json.isEmpty()) {
            return gson.fromJson(json, type);
        }
        return null;
    }

    /* return height and width of device*/
    public int[] getHeightWidth(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int[] xy = {height, width};
        return xy;
    }

    /* convert DP to PX */
    public float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    /* return color using ContextCompat*/
    public int getColor(Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    /* create filter for max length to set on EditText of other views*/
    public InputFilter getMaxLengthFilter(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        return filterArray[0];
    }

    /* create prefix filter to set on EditText of other views. ex: 91*/
    public InputFilter getPrefixFilter(final String prefix) {
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return dstart < prefix.length() ? dest.subSequence(dstart, dend) : null;
            }
        };
        return inputFilter;
    }

    /**
     * Generate a value suitable for use in {@link android.view.View#setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public int generateViewId() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }

    }
}
