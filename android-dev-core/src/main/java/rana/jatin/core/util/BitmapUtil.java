package rana.jatin.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * CoreUtil class for bitmap operations
 */

public class BitmapUtil {
    private static final BitmapUtil ourInstance = new BitmapUtil();

    public static BitmapUtil getInstance() {
        return ourInstance;
    }

    private BitmapUtil() {
    }

    /*  Create bitmap from vector drawable */
    public Bitmap getBitmapFromVectorDrawable(Context context, int drawableId, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /*  Create bitmap from drawable */
    public  Bitmap getBitmapFromDrawable(Context context, int drawableId,int width,int height) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();

        Bitmap bitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /*  Create bitmap from drawable based on drawable type */
    private  Bitmap getBitmap(Context context, int drawableId,int width,int height) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return getBitmapFromDrawable(context,drawableId,width,height);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmapFromVectorDrawable(context,drawableId,width,height);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }
}
