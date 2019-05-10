package com.menard.moodtracker;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.menard.moodtracker.controller.MainActivity;

public class AlertDialogComment  {

    /**
     * Show an alert dialog
     * @param mainActivity The context
     */
    public static void showAlertDialo(MainActivity mainActivity){

        Dialog dialog = new Dialog(mainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog_comment);

        TextView txtTitle = dialog.findViewById(R.id.alert_dialog_title);
        Button btnBack = dialog.findViewById(R.id.alert_dialog_btn_return);
        Button btnValidate = dialog.findViewById(R.id.alert_dialog_btn_ok);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
    }

}
