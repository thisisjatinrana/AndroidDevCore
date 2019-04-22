package rana.jatin.core.util;

import android.content.Context;
import android.os.PowerManager;

/**
 * The type Wake lock utils.
 *
 * @author Nishant Srivastava
 */
public class WakeLockUtils {

    /**
     * The Wake lock.
     */
    private static PowerManager.WakeLock wakeLock;

    private WakeLockUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static void holdWakeLock(Context context) {
        holdWakeLockTimed(context, 3600);
    }

    /**
     * Hold wake lock.
     *
     * @param context the context
     */
    public static void holdWakeLockTimed(Context context, long time) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert powerManager != null;
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AndroidDevCore:MyWakeLock");
        wakeLock.acquire(time);
    }

    /**
     * Release wake lock.
     */
    public static void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}