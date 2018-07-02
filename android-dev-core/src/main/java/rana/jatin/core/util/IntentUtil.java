package rana.jatin.core.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.List;


/**
 * Created by Jatin Rana on 15-10-2017.
 */

public class IntentUtil {

    public static int PICK_IMAGE_CODE = 1;
    public static int PICK_VIDEO_CODE = 2;
    public static int PICK_IMAGE_VIDEO_CODE = 3;

    private IntentUtil() {
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
