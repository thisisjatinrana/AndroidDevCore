package rana.jatin.core.util.dialog;

import android.content.DialogInterface;

/**
 * Created by jatin on 6/29/2017.
 */

public interface AlertDialogListener {
    void onPositiveButton(DialogInterface dialog);
    void onNegativeButton(DialogInterface dialog);
    void onDismiss();
}
