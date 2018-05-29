package rana.jatin.core.util.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Calendar;

import rana.jatin.core.R;
import rana.jatin.core.widget.recyclerview.RecyclerViewClickListener;

/**
 * Helper class to create basic dialogs and popups
 */

public class DialogUtil {

    private Context context;

    public DialogUtil(Context context) {
        this.context = context;
    }

    /* create and show vertical list dialog*/
    public Dialog showListDialog(final ArrayList<DialogData> dialogData, final DialogListener dialogListener) { // disply designing your popoup window
        final Dialog listDialog = new Dialog(context);
        listDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.pop_up_window);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(listDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        listDialog.getWindow().setAttributes(lp);
        listDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ListDialogAdapter listDialogAdapter = new ListDialogAdapter(context);
        RecyclerView recyclerView = listDialog.findViewById(R.id.rv_pop_up);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listDialogAdapter);
        listDialogAdapter.setData(dialogData);
        listDialogAdapter.OnItemClickListener(new RecyclerViewClickListener() {

            @Override
            public void onClick(View v, View viewHolder, int position) {
                listDialog.setOnDismissListener(null);
                listDialog.dismiss();
                dialogListener.onCallback(listDialog, v, position);
            }
        });

        listDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogListener.onDismiss();
            }
        });

        listDialog.show();
        return listDialog;
    }

    /* create and show vertical PopupWindow*/
    public PopupWindow showListPopUp(ArrayList<DialogData> dialogData, final PopUpListener popUpListener) { // disply designing your popoup window
        final PopupWindow popupWindow = new PopupWindow(context); // inflet your layout or diynamic add view
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.conetxt_pop_up_window, null);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setContentView(view);

        PopUpWindowAdapter popUpWindowAdapter = new PopUpWindowAdapter(context);
        RecyclerView recyclerView = view.findViewById(R.id.rv_pop_up);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(popUpWindowAdapter);
        popUpWindowAdapter.setData(dialogData);
        popUpWindowAdapter.OnItemClickListener(new RecyclerViewClickListener() {

            @Override
            public void onClick(View v, View viewHolder, int position) {
                popupWindow.setOnDismissListener(null);
                popupWindow.dismiss();
                popUpListener.onCallback(popupWindow, v, position);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popUpListener.onDismiss();
            }
        });
        //popupWindow.showAsDropDown(view,-40, 18);
        return popupWindow;
    }

    public AlertDialog showAlertDialog(String title, String message, String setPositiveButton, String setNegativeButton, final AlertDialogListener dialogCallback) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(setPositiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialogCallback.onPositiveButton(dialog);
                    }
                })
                .setNegativeButton(setNegativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialogCallback.onNegativeButton(dialog);

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogCallback.onDismiss();
            }
        });
        // show it
        alertDialog.show();
        return alertDialog;
    }

    /* create and show Dialog
     *  @param title of the dialog
     *  @param view to set as content view of dialog
     */
    public Dialog showDialog(String title, View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setTitle(title);
        dialog.show();
        return dialog;
    }

    /* create and show Dialog
     *  @param title of the dialog
     *  @param viewId to set as content view of dialog
     */
    public Dialog showDialog(String title, @LayoutRes int view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setTitle(title);
        dialog.show();
        return dialog;
    }

    /* create and show DatePickerDialog
     *  @param listener on the dialog
     *  @param theme of dialog. see {@link android.app.AlertDialog}
     */
    public DatePickerDialog showDatePicker(DatePickerDialog.OnDateSetListener listener, int theme) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerdialog = new DatePickerDialog(context, theme, listener, year, month, day);
        datepickerdialog.show();
        return datepickerdialog;
    }

    /* create and show TimePickerDialog
     *  @param listener on the dialog
     */
    public TimePickerDialog showTimePicker(TimePickerDialog.OnTimeSetListener listener, boolean is24HourView) {
        // Get Current time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(context, listener, hour, minute, is24HourView);

    }
}
