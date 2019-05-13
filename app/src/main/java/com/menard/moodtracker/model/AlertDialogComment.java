package com.menard.moodtracker.model;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.menard.moodtracker.R;
import com.menard.moodtracker.controller.MainActivity;

public class AlertDialogComment  {



    /**
     * Show an alert dialog
     * @param mainActivity The context
     */
    public static void showAlertDialog(final MainActivity mainActivity){

        final Dialog dialog = new Dialog(mainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialog_comment);

        final EditText txtComment = dialog.findViewById(R.id.alert_dialog_edit_txt);
        Button btnBack = dialog.findViewById(R.id.alert_dialog_btn_return);
        Button btnValidate = dialog.findViewById(R.id.alert_dialog_btn_ok);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mComment = txtComment.getText().toString();
                Day.saveComment(mComment);
                Toast.makeText(mainActivity, "Commentaire mis à jour", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
