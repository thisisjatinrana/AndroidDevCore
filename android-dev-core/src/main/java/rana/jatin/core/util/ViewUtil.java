package rana.jatin.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rana.jatin.core.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Contains set of utils for popups, toasts, snack bar etc
 */
public class ViewUtil {

    private int snackViewId;
    private int snackTxtColor = R.color.black;
    private int snackBackground = R.color.white;
    private int snackActionColor = R.color.red;
    private boolean keyboardOpen;
    private Activity activity;
    private Fragment fragment;
    private Snackbar snackbar;
    private KeyboardListener keyboardListener;

    public ViewUtil(Activity activity) {
        this.activity = activity;
    }

    public ViewUtil(Activity activity, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    /* open android app settings */
    public void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
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

    /* show a toast message
     *  @param message to show
     */
    public void toast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    /* show a toast message
     *  @param message string resource id
     */
    public void toast(@StringRes int message) {
        Toast.makeText(activity, activity.getString(message), Toast.LENGTH_LONG).show();
    }

    /* fire sharing intent
     * @param sharebody share subject
     * @param shareSub  share extra text
     */
    public synchronized void showShareSheet(String shareSub, String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    /*
     * show snack bar
     * @param message to show
     */
    public synchronized void showSnack(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    snackbar = Snackbar
                            .make(activity.findViewById(snackViewId), message, Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    setSnackBackground(snackBackground);
                    setSnackActionTextColor(snackActionColor);
                    setSnackTextColor(snackTxtColor);
                    snackbar.show();
                } catch (Exception e) {
                    toast(message);
                }
            }
        });

    }

    /*
     * show snack bar
     * @param string resource id of message to show
     */
    public synchronized void showSnack(@StringRes final int message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    snackbar = Snackbar
                            .make(activity.findViewById(snackViewId), activity.getString(message), Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    setSnackBackground(snackBackground);
                    setSnackActionTextColor(snackActionColor);
                    setSnackTextColor(snackTxtColor);
                    snackbar.show();
                } catch (Exception e) {
                    toast(activity.getString(message));
                }
            }
        });

    }

    /*
     * show permission snack bar with action button to open app settings
     */
    public synchronized void showPermissionSnack() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showSnack(activity.getString(R.string.message_permissions_reject), activity.getString(R.string.settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openAppSettings();
                        }
                    });
                } catch (Exception e) {
                    toast(R.string.message_permissions_reject);
                }
            }
        });

    }

    /*
     * show snack bar
     * @param message to show
     * @param actionText action button text
     * @param onClickListener click listener for action button click
     */
    public synchronized void showSnack(final String message, final String actionText, final View.OnClickListener onClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    snackbar = Snackbar
                            .make(activity.findViewById(snackViewId), message, Snackbar.LENGTH_LONG)
                            .setAction(actionText, onClickListener);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    setSnackBackground(snackBackground);
                    setSnackActionTextColor(snackActionColor);
                    setSnackTextColor(snackTxtColor);
                    snackbar.show();
                } catch (Exception e) {
                    toast(message);
                }
            }
        });
    }

    /*
     * show snack bar with custom duration
     * @param message to show
     * @param actionText action button text
     * @param onClickListener click listener for action button click
     */
    public synchronized void showSnackWithCustomTiming(final String message, final String actionText, final int duration, final View.OnClickListener onClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    snackbar = Snackbar
                            .make(activity.findViewById(snackViewId), message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(actionText, onClickListener);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    setSnackBackground(snackBackground);
                    setSnackActionTextColor(snackActionColor);
                    setSnackTextColor(snackTxtColor);
                    snackbar.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            snackbar.dismiss();
                        }
                    }, duration);
                } catch (Exception e) {
                    toast(message);
                }
            }
        });

    }

    /*
     * show snack bar until dismissed by user
     * @param message to show
     * @param actionText action button text
     * @param onClickListener click listener for action button click
     */
    public synchronized void showSnackIndefinite(final String message, final String actionText, final View.OnClickListener onClickListener) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    snackbar = Snackbar
                            .make(activity.findViewById(snackViewId), message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(actionText, onClickListener);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    setSnackBackground(snackBackground);
                    setSnackActionTextColor(snackActionColor);
                    setSnackTextColor(snackTxtColor);
                    snackbar.show();
                } catch (Exception e) {
                    toast(message);
                }
            }
        });

    }

    /*
     * show snack bar until dismissed by user
     * @param message to show
     */
    public synchronized void showSnackIndefinite(final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    snackbar = Snackbar
                            .make(activity.findViewById(snackViewId), message, Snackbar.LENGTH_INDEFINITE);
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(5);
                    setSnackBackground(snackBackground);
                    setSnackActionTextColor(snackActionColor);
                    setSnackTextColor(snackTxtColor);
                    snackbar.show();
                } catch (Exception e) {
                    toast(message);
                }
            }
        });
    }

    /*
     * set snack bar view id
     * @param id of view on which to make snack bar ( in most cases coordinator layout )
     */
    public ViewUtil makeSnackBar(int id) {
        snackViewId = id;
        return this;
    }

    /* return snack bar*/
    public Snackbar getSnackBar() {
        return snackbar;
    }

    /*
     * set snack bar text view color
     * @param textColor color resource id
     * return self
     */
    public ViewUtil setSnackTextColor(int textColor) {
        snackTxtColor = textColor;
        if (snackbar != null) {
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(activity, textColor));
        }
        return this;
    }

    /*
     * set snack bar action text color
     * @param textColor color resource id
     * return self
     */
    public ViewUtil setSnackActionTextColor(int textColor) {
        snackActionColor = textColor;
        if (snackbar != null)
            snackbar.setActionTextColor(ContextCompat.getColor(activity, textColor));
        return this;
    }

    /*
     * set snack bar background color
     * @param background color resource id
     * return self
     */
    public ViewUtil setSnackBackground(int background) {
        snackBackground = background;
        if (snackbar != null)
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, background));
        return this;
    }

    public boolean isActivityDead(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Do something for lollipop and above versions
            return activity == null || activity.isFinishing() || activity.isDestroyed();
        } else {
            // do something for phones running an SDK before lollipop
            return activity == null || activity.isFinishing();
        }

    }

    public boolean isFragmentDead(Fragment fragment) {
        if (fragment == null)
            return true;
        Activity activity = fragment.getActivity();
        return activity == null || !fragment.isAdded() || fragment.isRemoving() || fragment.isDetached();
    }

    public interface KeyboardListener {
        void onKeyboardOpen();

        void onKeyboardClosed();
    }
}
