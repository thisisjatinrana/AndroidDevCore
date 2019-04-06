package rana.jatin.core.util.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rana.jatin.core.R;
import rana.jatin.core.adapter.GenericRecyclerViewAdapter;
import rana.jatin.core.adapter.GenericViewHolder;
import rana.jatin.core.viewHolder.DialogItemViewHolder;
import rana.jatin.core.viewHolder.PopUpViewHolder;

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


        GenericRecyclerViewAdapter<DialogData> listDialogAdapter = new GenericRecyclerViewAdapter<>("listDialogAdapter", context, dialogData, new GenericRecyclerViewAdapter.GenericRecyclerViewAdapterBridge() {
            @Override
            public GenericViewHolder createViewHolder(View view, String tag, int layoutId, GenericRecyclerViewAdapter adapter) {
                return new DialogItemViewHolder(view, adapter);
            }

            @Override
            public int getLayoutId(int position, String tag) {
                return R.layout.rv_dialog_item;
            }

            @Override
            public void onViewHolderClick(String tag, int action, int position, View view, GenericViewHolder viewHolder) {
                listDialog.setOnDismissListener(null);
                listDialog.dismiss();
                dialogListener.onCallback(listDialog, view, position);
            }
        });
        RecyclerView recyclerView = listDialog.findViewById(R.id.rv_pop_up);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listDialogAdapter);

        listDialog.setOnDismissListener(dialogInterface -> dialogListener.onDismiss());

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

        GenericRecyclerViewAdapter<DialogData> popUpWindowAdapter = new GenericRecyclerViewAdapter<>("popUpWindowAdapter", context, dialogData, new GenericRecyclerViewAdapter.GenericRecyclerViewAdapterBridge() {
            @Override
            public GenericViewHolder createViewHolder(View view, String tag, int layoutId, GenericRecyclerViewAdapter adapter) {
                return new PopUpViewHolder(view, adapter);
            }

            @Override
            public int getLayoutId(int position, String tag) {
                return R.layout.rv_pop_up_item;
            }

            @Override
            public void onViewHolderClick(String tag, int action, int position, View view, GenericViewHolder viewHolder) {
                popupWindow.setOnDismissListener(null);
                popupWindow.dismiss();
                popUpListener.onCallback(popupWindow, view, position);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_pop_up);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(popUpWindowAdapter);

        popupWindow.setOnDismissListener(() -> popUpListener.onDismiss());
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
