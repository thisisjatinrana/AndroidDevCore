package rana.jatin.core.util;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

public class JSONUtils {

    private JSONUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Iterate over all keys of the JSON
     *
     * @param jsonObject the json object
     * @return the hash map
     */
    public static HashMap<String, String> iterateOverJSON(JSONObject jsonObject) {
        Iterator<String> iter = jsonObject.keys();
        HashMap<String, String> keyValueMap = new HashMap<>();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                String value = jsonObject.getString(key);
                keyValueMap.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return keyValueMap;
    }

    /**
     * Read and parse a JSON file stored in assets folder
     *
     * @param context  the context
     * @param filename the filename
     * @return the json object
     */
    public static JSONObject loadJSONFromAsset(Context context, String filename) {
        String json = null;
        JSONObject jsonObject = null;
        try {

            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            final int read = is.read(buffer);
            is.close();
            if (read > 0) {
                json = new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /* create model class from string response
     *  @param json string response
     *  @param cls type of model class
     *  return generic class
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        Gson gson = new Gson();
        if (json != null && !json.isEmpty()) {
            return gson.fromJson(json, cls);
        }
        return null;
    }

    /* convert model class to string
     *  @param cls type of model class
     *  return string response
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /* create model class from string response
     *  @param json string response
     *  @param type Type Token of model class
     *  return generic class
     */
    public static <T> T fromJson(String json, Type type) {
        Gson gson = new Gson();
        if (json != null && !json.isEmpty()) {
            return gson.fromJson(json, type);
        }
        return null;
    }
}