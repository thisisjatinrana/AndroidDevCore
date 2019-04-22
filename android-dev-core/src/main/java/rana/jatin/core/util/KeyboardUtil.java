package rana.jatin.core.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardUtil {

    private KeyboardListener keyboardListener;

    private Activity activity;
    private boolean keyboardOpen;

    public KeyboardUtil(Activity activity) {
        this.activity = activity;
    }

    /* show soft keyboard */
    public void showSoftInput(final EditText editText) {

        Handler mHandler = new Handler();
        mHandler.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                        editText.requestFocus();
                    }
                });
    }

    /* hide soft keyboard*/
    public void toggleSoftInput() {

        if (!keyboardOpen)
            return;
        Handler mHandler = new Handler();
        mHandler.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        keyboardOpen = false;
                    }
                });
    }

    // This utility method is used with fragment
    // view = getView().getRootView();
    // view = fragment.getView();
    public void hideKeyboardFrom(Context context, View view) {
        if (view == null)
            view = new View(activity);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
        keyboardOpen = false;
    }

    // This utility method ONLY works when called from an Activity
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
        keyboardOpen = false;
    }

    /*
     * return true if soft keyboardOpen open else false if soft keyboardOpen closed
     */
    public boolean isKeyboardOpen() {
        return keyboardOpen;
    }

    public void setKeyboardListener(final KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
        final View activityRootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = activityRootView.getRootView().getHeight();
                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboardOpen is opened
                    keyboardOpen = true;
                    keyboardListener.onKeyboardOpen();
                } else {
                    keyboardOpen = false;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            keyboardListener.onKeyboardClosed();
                        }
                    }, 100);
                }
            }
        });
    }

    public interface KeyboardListener {
        void onKeyboardOpen();

        void onKeyboardClosed();
    }

}
