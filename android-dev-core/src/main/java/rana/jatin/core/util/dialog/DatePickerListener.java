package rana.jatin.core.util.dialog;

import android.view.View;

/**
 * Created by jatin on 9/1/2017.
 */

public interface DatePickerListener {
    void DatePicker(View view, int year, int month, int day);
    void onDismiss();
}
