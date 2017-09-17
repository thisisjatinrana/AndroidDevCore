package rana.jatin.core.util.dialog;

import android.app.Dialog;
import android.view.View;

/**
 * Created by jatin on 6/29/2017.
 */

public interface DialogCallback {
    void onCallback(Dialog dialog, View v, int position);
}
