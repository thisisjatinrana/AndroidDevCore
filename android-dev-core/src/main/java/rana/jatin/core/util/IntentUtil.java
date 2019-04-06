package rana.jatin.core.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;


/**
 * Created by Jatin Rana on 15-10-2017.
 */

public class IntentUtil {

    public static int PICK_IMAGE_CODE = 1;
    public static int PICK_VIDEO_CODE = 2;
    public static int PICK_IMAGE_VIDEO_CODE = 3;

    private IntentUtil() {
        throw new UnsupportedOperationException(
                "Should not create instance of IntentUtil class. Please use as static..");
    }

    /**
     * Open app page in playstore.
     *
     * @param context the context
     */
    public static void openAppPageInPlaystore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Open url in browser.
     *
     * @param context the context
     * @param url     the url
     */
    public static void openUrlInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Send email.
     *
     * @param context the context
     * @param sendTo  the send to
     * @param subject the subject
     * @param body    the body
     */
    public static void sendEmail(Context context, String[] sendTo, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, sendTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, ""));
        }
    }


    /**
     * Share my app.
     *
     * @param context the context
     * @param subject the subject
     * @param message the message
     */
    public static void shareMyApp(Context context, String subject, String message) {
        try {
            String appUrl = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            String leadingText = "\n" + message + "\n\n";
            leadingText += appUrl + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, leadingText);
            context.startActivity(Intent.createChooser(i, "Share using"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Rate my app.
     *
     * @param context the context
     */
    public static void rateMyApp(Context context) {

        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName()));
            if (i.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(i);
            } else {
                Toast.makeText(context, "Playstore Unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Call.
     *
     * @param context the context
     * @param number  the number
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    public static void call(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, return
            return;
        }

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Send sms.
     *
     * @param context      the context
     * @param sendToNumber the send to number
     * @param message      the message
     */
    public static void sendSMS(Context context, String sendToNumber, String message) {
        Uri smsUri = Uri.parse("tel:" + sendToNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        intent.putExtra("address", sendToNumber);
        intent.putExtra("sms_body", message);
        intent.setType("vnd.android-dir/mms-sms");

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Show location in map.
     *
     * @param context   the context
     * @param latitude  the latitude
     * @param longitude the longitude
     * @param zoomLevel the zoom level
     */
    public static void showLocationInMap(Context context, String latitude, String longitude, String zoomLevel) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String data = String.format("geo:%s,%s", latitude, longitude);
        if (zoomLevel != null) {
            data = String.format("%s?z=%s", data, zoomLevel);
        }
        intent.setData(Uri.parse(data));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Show location in map.
     *
     * @param context      the context
     * @param locationName the location name
     */
    public static void showLocationInMap(Context context, String locationName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + locationName));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Share data.
     *
     * @param context  the context
     * @param dir      the dir
     * @param fileName the file name
     * @param type     the type
     * @param data     the data
     */
    public static void shareData(Context context, String dir, String fileName, String type, String data) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(type);
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/" + dir, fileName));
        intent.putExtra(Intent.EXTRA_STREAM, uri.toString());
        intent.putExtra("data", data);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "Share using"));
        }
    }

    /**
     * Share data.
     *
     * @param context the context
     * @param uri     the uri
     * @param type    the type
     * @param data    the data
     */
    public static void shareData(Context context, Uri uri, String type, String data) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_STREAM, uri.toString());
        intent.putExtra("data", data);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "Share using"));
        }
    }

    /**
     * Share text.
     *
     * @param activity the activity
     * @param title    the title
     * @param textData the text data
     */
    public static void shareText(Activity activity, String title, String textData) {
        ShareCompat.IntentBuilder.from(activity).setType("text/plain").setChooserTitle(title)
                .setText(textData).startChooser();
    }

    /* open android app settings */
    public static void openAppSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * Take a pic.
     *
     * @param context  the context
     * @param dir      the dir
     * @param fileName the file name
     */
    void takeAPic(Context context, String dir, String fileName) {
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/" + dir, fileName));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Take a pic.
     *
     * @param context the context
     * @param uri     the uri
     */
    void takeAPic(Context context, Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static Intent imageIntent(Activity activity, int requestCode) {
        Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
        pickPhoto.setType("*/*");
        String[] mimetypes = {"image/*"};
        pickPhoto.putExtra("android.intent.extra.MIME_TYPES", mimetypes);
        activity.startActivityForResult(pickPhoto, requestCode);
        return pickPhoto;
    }

    public static Intent videoIntent(Activity activity, int requestCode) {
        Intent pickVideo = new Intent(Intent.ACTION_GET_CONTENT);
        pickVideo.setType("*/*");
        String[] mimetypes = {"video/*"};
        pickVideo.putExtra("android.intent.extra.MIME_TYPES", mimetypes);
        activity.startActivityForResult(pickVideo, requestCode);
        return pickVideo;
    }

    public static Intent imageVideoIntent(Activity activity, int requestCode) {
        Intent imageVideo = new Intent(Intent.ACTION_GET_CONTENT);
        imageVideo.setType("*/*");
        String[] mimetypes = {"image/*", "video/*"};
        imageVideo.putExtra("android.intent.extra.MIME_TYPES", mimetypes);
        activity.startActivityForResult(imageVideo, requestCode);
        return imageVideo;
    }

   /* public static boolean pickGooglePlace(Activity activity,int requestCode)
    {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            activity.startActivityForResult(builder.build(activity), requestCode);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }*/

    public static void contactPicker(Activity activity, int requestCode) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        activity.startActivityForResult(contactPickerIntent, requestCode);
    }

    public static void emailIntent(Activity activity, String address, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", address, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void shareOnFacebook(Activity appCompatActivity, String urlToShare) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, !TextUtils.isEmpty(urlToShare) ? urlToShare : "");

        boolean facebookAppFound = false;
        List<ResolveInfo> matches = appCompatActivity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana") ||
                    info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.lite")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        if (facebookAppFound) {
            appCompatActivity.startActivity(intent);
        } else {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            appCompatActivity.startActivity(intent);
        }
    }
}
