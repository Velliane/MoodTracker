package com.menard.moodtracker.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menard.moodtracker.R;

public class AlertDialogFragmentComment extends DialogFragment {

    private Listener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (Listener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.alert_dialog_comment);

        final EditText txtComment = alertDialog.findViewById(R.id.alert_dialog_edit_txt);
        Button btnBack = alertDialog.findViewById(R.id.alert_dialog_btn_return);
        Button btnValidate = alertDialog.findViewById(R.id.alert_dialog_btn_ok);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mComment = txtComment.getText().toString();
                mListener.onCommentSelected(mComment);
                Toast.makeText(getContext(), "Commentaire mis à jour", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        //alertDialog.show();

        return alertDialog;
    }


    public interface Listener{
        void onCommentSelected(String comment);
    }




}
