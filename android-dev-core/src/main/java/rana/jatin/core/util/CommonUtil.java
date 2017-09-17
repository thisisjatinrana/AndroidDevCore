package rana.jatin.core.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Util class for various common tasks
 */

public class CommonUtil {

    private static final CommonUtil ourInstance = new CommonUtil();

    public static CommonUtil getInstance() {
        return ourInstance;
    }

    private CommonUtil() {
    }

    /* create model class from string response
    *  @param json string response
    *  @param cls type of model class
    *  return generic class
    */
    public <T> T fromJson(String json,Class<T> cls)
    {
        Gson gson = new Gson();
        if(json!=null && !json.isEmpty()) {
            return gson.fromJson(json,cls);
        }
        return null;
    }

    /* convert model class to string
    *  @param cls type of model class
    *  return string response
    */
    public <T> String toJson(Class<T> cls)
    {
        Gson gson = new Gson();
        return gson.toJson(cls);
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
    public int[] getHeightWidth(Activity context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int[] xy={height,width};
        return xy;
    }

    /* convert DP to PX */
    public  float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    /* return color using ContextCompat*/
    public int getColor(Context context,@ColorRes int color){
          return ContextCompat.getColor(context,color);
    }

    /* create filter for max length to set on EditText of other views*/
    public InputFilter getMaxLengthFilter(int length){
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        return filterArray[0];
    }

    /* create prefix filter to set on EditText of other views. ex: 91*/
    public InputFilter getPrefixFilter(final String prefix){
        InputFilter inputFilter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return dstart < prefix.length() ? dest.subSequence(dstart, dend) : null;
            }
        };
       return inputFilter;
    }
}
