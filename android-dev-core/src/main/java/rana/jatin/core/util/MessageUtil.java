package rana.jatin.core.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import rana.jatin.core.R;

/**
 * Contains set of utils for popups, toasts, snack bar etc
 */
public class MessageUtil {

    private int snackViewId;
    private int snackTxtColor = R.color.black;
    private int snackBackground = R.color.white;
    private int snackActionColor = R.color.red;
    private Activity activity;
    private Fragment fragment;
    private Snackbar snackbar;

    public MessageUtil(Activity activity) {
        this.activity = activity;
    }

    public MessageUtil(Activity activity, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
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
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
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
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
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
                            IntentUtil.openAppSettings(activity);
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
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
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
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
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
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
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
                    TextView textView = snackbarView.findViewById(R.id.snackbar_text);
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
    public MessageUtil makeSnackBar(int id) {
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
    public MessageUtil setSnackTextColor(int textColor) {
        snackTxtColor = textColor;
        if (snackbar != null) {
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(activity, textColor));
        }
        return this;
    }

    /*
     * set snack bar action text color
     * @param textColor color resource id
     * return self
     */
    public MessageUtil setSnackActionTextColor(int textColor) {
        snackActionColor = textColor;
        if (snackbar != null)
            snackbar.setActionTextColor(ContextCompat.getColor(activity, textColor));
        return this;
    }

    /*
     * dismiss snack bar
     * return self
     */
    public MessageUtil dismissSnack() {
        if (snackbar != null)
            snackbar.dismiss();
        return this;
    }

    /*
     * set snack bar background color
     * @param background color resource id
     * return self
     */
    public MessageUtil setSnackBackground(int background) {
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


}
