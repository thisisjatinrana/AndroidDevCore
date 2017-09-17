package rana.jatin.core.etc;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import rana.jatin.core.activity.BaseActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * Helper class for managing permissions at runtime
 */

public class PermissionHelper {

    private static PermissionHelper ourInstance;
    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int PERMISSIONS_STORAGE_CAMERA_GROUP_CODE = 4;
    public static final int ACCESS_FINE_LOCATION_CODE = 5;
    public static final int ACCESS_COARSE_LOCATION_CODE = 6;
    public static final int READ_SMS_REQUEST_CODE = 7;
    public static final String[] PERMISSIONS_STORAGE_CAMERA_GROUP = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private BaseActivity activity;

    public PermissionHelper(BaseActivity baseActivity) {
        this.activity = baseActivity;
    }

    public static PermissionHelper getInstance(FragmentActivity baseActivity) {
        ourInstance = new PermissionHelper((BaseActivity) baseActivity);
        return ourInstance;
    }

    public static PermissionHelper getInstance(Activity baseActivity) {
        ourInstance = new PermissionHelper((BaseActivity) baseActivity);
        return ourInstance;
    }

    /* check if audio permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForAudio() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* check if WRITE_EXTERNAL_STORAGE permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForWriteExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* check if READ_EXTERNAL_STORAGE permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForReadExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* check if ACCESS_FINE_LOCATION permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForFineLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* check if ACCESS_COARSE_LOCATION permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForCoarseLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* check if CAMERA permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* check if READ_SMS permission granted
    *  return true if permission granted
    *  return false if permission not granted
    */
    public boolean checkPermissionForReadSms() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /* request permission to READ_SMS */
    public void requestPermissionForReadSms() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
            activity.helper().viewHelper().showSnack("Microphone permission needed for recording. Please allow in App Settings for additional functionality", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS}, READ_SMS_REQUEST_CODE);
        }
    }

    /* request permission to RECORD_AUDIO */
    public void requestPermissionForRecord() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
            activity.helper().viewHelper().showSnack("Microphone permission needed for recording. Please allow in App Settings for additional functionality", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    /* request permission to WRITE_EXTERNAL_STORAGE */
    public void requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            activity.helper().viewHelper().showSnack("External Storage permission needed. Please allow in App Settings for additional functionality.", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    /* request permission to ACCESS_FINE_LOCATION */
    public void requestPermissionForFineLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            activity.helper().viewHelper().showSnack("External Storage permission needed. Please allow in App Settings for additional functionality.", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CODE);
        }
    }

    /* request permission to ACCESS_COARSE_LOCATION */
    public void requestPermissionForCoarseLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            activity.helper().viewHelper().showSnack("External Storage permission needed. Please allow in App Settings for additional functionality.", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION_CODE);
        }
    }

    /* request permission to READ_EXTERNAL_STORAGE */
    public void requestPermissionForReadExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            activity.helper().viewHelper().showSnack("External Storage permission needed. Please allow in App Settings for additional functionality.", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    /* request permission to CAMERA */
    public void requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            activity.helper().viewHelper().showSnack("Camera permission needed. Please allow in App Settings for additional functionality.", "Okay", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.helper().viewHelper().openAppSettings();
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
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
    public boolean isPermissionGranted(@NonNull int[] grantResults){
        boolean permissionGranted = true;
        for (int count = 0; count < grantResults.length; count++) {
            if (grantResults[count] != PERMISSION_GRANTED)
                permissionGranted = false;
        }
        return permissionGranted;
    }
}