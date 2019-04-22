package rana.jatin.core.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import rana.jatin.core.custom.StringSignature;


public class GlideHelper {

    private GlideHelper() {
    }

    public static void loadFromFilePath(Context context, String path, @DrawableRes int placeholder, ImageView view) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            StringSignature signature = new StringSignature(String.valueOf(file.lastModified()));
            Glide.with(context)
                    .load(Uri.parse("file:///" + path))
                    .apply(new RequestOptions()
                            .placeholder(placeholder))
                    .apply(new RequestOptions().signature(signature))
                    .into(view);
        } else {
            view.setImageResource(placeholder);
        }
    }

    public static void loadFromFile(Context context, File file, @DrawableRes int placeholder, ImageView view) {
        if (file != null) {
            StringSignature signature = new StringSignature(String.valueOf(file.lastModified()));
            Glide.with(context)
                    .load(Uri.fromFile(file))
                    .apply(new RequestOptions()
                            .placeholder(placeholder))
                    .apply(new RequestOptions().signature(signature))
                    .into(view);
        } else {
            view.setImageResource(placeholder);
        }
    }

    public static void loadFromUrl(Context context, String url, @DrawableRes int placeholder, ImageView view) {
        if (url != null && !url.isEmpty()) {
            Glide.with(context)
                    .load(url)
                    .thumbnail(Glide.with(context).load(placeholder))
                    .apply(new RequestOptions()
                            .placeholder(placeholder))
                    .into(view);
        } else {
            view.setImageResource(placeholder);
        }
    }
}
