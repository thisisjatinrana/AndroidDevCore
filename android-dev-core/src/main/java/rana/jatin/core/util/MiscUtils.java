package rana.jatin.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Misc utils.
 *
 * @author Nishant Srivastava
 */
public class MiscUtils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private MiscUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Check if permission granted boolean.
     *
     * @param context    the context
     * @param permission the permission
     * @return the boolean
     */
    public static boolean checkIfPermissionGranted(Context context, String permission) {
        return (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Disable screenshot functionality.
     *
     * @param activity the activity
     */
    public static void disableScreenshotFunctionality(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * Enable strict mode.
     *
     * @param enable the enable flag
     */
    public static void enableStrictMode(boolean enable) {
        if (enable) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    /**
     * Write String data to a csv file
     *
     * @param sFileName the s file name
     * @param data      the data
     * @throws IOException   the io exception
     * @throws JSONException the json exception
     */
    public static void generateCsvFile(String sFileName, String data)
            throws IOException, JSONException {
        JSONObject objectToWrite = new JSONObject(data);
        File folder = new File(Environment.getExternalStorageDirectory() + "/Folder");

        boolean var = false;
        if (!folder.exists()) {
            var = folder.mkdir();
        }

        System.out.println("" + var);

        final String filename = folder.toString() + "/" + sFileName;

        FileWriter writer = new FileWriter(filename, true);

        try {

            writer.append(objectToWrite.get("x").toString());
            writer.append(',');
            writer.append(objectToWrite.get("y").toString());
            writer.append(',');
            writer.append(objectToWrite.get("z").toString());
            writer.append('\n');
            writer.flush();
            writer.close();
        } catch (Exception e) {
            writer.flush();
            writer.close();
            e.printStackTrace();
        }
    }

    /**
     * Gets random number in range.
     *
     * @param min the min
     * @param max the max
     * @return the random number in range
     */
    public static int getRandomNumberInRange(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }

    /* create filter for max length to set on EditText of other views*/
    public static InputFilter getMaxLengthFilter(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        return filterArray[0];
    }

    /* create prefix filter to set on EditText of other views. ex: 91*/
    public static InputFilter getPrefixFilter(final String prefix) {
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
    public static int generateViewId() {

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