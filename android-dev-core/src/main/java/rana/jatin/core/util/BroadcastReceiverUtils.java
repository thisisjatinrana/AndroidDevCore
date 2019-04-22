package rana.jatin.core.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import rana.jatin.core.base.BaseIntent;

public class BroadcastReceiverUtils {

    private BroadcastReceiverUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Broadcast data as a message
     *
     * @param context          the context
     * @param data             the data
     * @param action           the action
     * @param custompermission the custompermission
     */
    public static void broadcastData(Context context, String data, String action,
                                     String custompermission) {
        BaseIntent i = new BaseIntent();
        i.putExtra("data", data);
        i.setAction(action); // action ~ "com.example.android.action"
        context.sendBroadcast(i,
                custompermission); // custompermisson ~ "com.example.permission.MY_PERMISSION"
    }

    /**
     * Enable/Disable Broadcast Receiver
     *
     * @param context the context
     * @param brClass the br class
     * @param enabled the enabled
     */
    public static void setStateOfReceiver(Context context, Class<?> brClass, boolean enabled) {
        ComponentName receiverName = new ComponentName(context, brClass.getName());
        PackageManager pm = context.getPackageManager();

        int newstate;
        if (enabled) {
            newstate = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } else {
            newstate = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        }

        pm.setComponentEnabledSetting(receiverName, newstate, PackageManager.DONT_KILL_APP);
    }
}