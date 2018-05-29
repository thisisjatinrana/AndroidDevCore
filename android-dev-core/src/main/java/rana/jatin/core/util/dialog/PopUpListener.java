package rana.jatin.core.util.dialog;

import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by jatin on 6/29/2017.
 */

public interface PopUpListener {
    void onCallback(PopupWindow popupWindow, View v, int position);
    void onDismiss();
}
