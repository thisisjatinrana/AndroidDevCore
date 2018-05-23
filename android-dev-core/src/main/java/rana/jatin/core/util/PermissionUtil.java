package rana.jatin.core.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * Helper class for managing permissions at runtime
 */

public class PermissionUtil {

    public static final int AUDIO_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int PERMISSIONS_STORAGE_CAMERA_AUDIO_GROUP_CODE = 4;
    public static final int ACCESS_FINE_LOCATION_CODE = 5;
    public static final int ACCESS_COARSE_LOCATION_CODE = 6;
    public static final int READ_SMS_REQUEST_CODE = 7;
    public static final int WRITE_SMS_REQUEST_CODE = 8;
    public static final String[] STORAGE_CAMERA_AUDIO_PERMISSIONS_GROUP = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

    private Activity activity;

    public PermissionUtil(Activity baseActivity) {
        this.activity = baseActivity;
    }

    /* check if audio permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForAudio() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    /* check if WRITE_EXTERNAL_STORAGE permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForWriteExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* check if READ_EXTERNAL_STORAGE permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForReadExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* check if ACCESS_FINE_LOCATION permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForFineLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* check if ACCESS_COARSE_LOCATION permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForCoarseLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* check if CAMERA permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* check if READ_SMS permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForReadSms() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* check if SEND_SMS permission granted
     *  return true if permission granted
     *  return false if permission not granted
     */
    public boolean checkPermissionForSendSms() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /* request permission to READ_SMS */
    public boolean requestPermissionForReadSms() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, READ_SMS_REQUEST_CODE);
            return true;
        }
    }

    /* request permission to SEND_SMS */
    public boolean requestPermissionForSendSms() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.SEND_SMS)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, WRITE_SMS_REQUEST_CODE);
            return true;
        }
    }

    /* request permission to RECORD_AUDIO */
    public boolean requestPermissionForRecord() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
            return true;
        }
    }

    /* request permission to WRITE_EXTERNAL_STORAGE */
    public boolean requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            return true;
        }

    }

    /* request permission to ACCESS_FINE_LOCATION */
    public boolean requestPermissionForFineLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CODE);
            return false;
        }
    }

    /* request permission to ACCESS_COARSE_LOCATION */
    public boolean requestPermissionForCoarseLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION_CODE);
            return true;
        }
    }

    /* request permission to READ_EXTERNAL_STORAGE */
    public boolean requestPermissionForReadExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            return true;
        }
    }

    /* request permission to CAMERA */
    public boolean requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            return false;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            return true;
        }
    }


    /* check permission for multiple components
     * @param permissions_group array of permissions
     */
    public boolean hasPermissionsGroup(String[] permissions_group) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null && permissions_group != null) {
            for (String permission : permissions_group) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /* request permission for multiple components
     * @param permissions_group array of permissions
     * @param permissions_group_code request code for permissions
     */
    public void requestPermissionsGroup(String[] permissions_group, int permissions_group_code) {
        ActivityCompat.requestPermissions(activity, permissions_group, permissions_group_code);
    }

    /* check if requested permissions granted */
    public boolean isPermissionGranted(@NonNull int[] grantResults) {
        boolean permissionGranted = true;
        for (int count = 0; count < grantResults.length; count++) {
            if (grantResults[count] != PERMISSION_GRANTED)
                permissionGranted = false;
        }
        return permissionGranted;
    }
}