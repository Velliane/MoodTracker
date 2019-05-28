package com.menard.moodtracker.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menard.moodtracker.Database.BaseSQLite;
import com.menard.moodtracker.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

public class AlertDialogFragmentComment extends DialogFragment {

    private Listener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (Listener) context;
    }

    public AlertDialogFragmentComment newInstance (){
        return new AlertDialogFragmentComment();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final EditText editText = new EditText(getContext());
        final BaseSQLite baseSQLite = new BaseSQLite(getContext());

        String lastComment = baseSQLite.getComment(LocalDate.now(ZoneId.systemDefault()).toString());
        if(lastComment != null) {
            editText.setText(lastComment);
            editText.setSelection(lastComment.length());
        }

        return new AlertDialog.Builder(getContext())
        .setMessage(getResources().getString(R.string.alert_dialog_title))
        .setView(editText)
        .setPositiveButton(getResources().getString(R.string.alert_dialog_btn_validate), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mComment = editText.getText().toString();
                mListener.onCommentSelected(mComment);
                Toast.makeText(getContext(), getResources().getString(R.string.alert_dialog_toast), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        })
        .setNegativeButton(getResources().getString(R.string.alert_dialog_btn_back), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        })
        .create();

    }


    public interface Listener{
        void onCommentSelected(String comment);
    }



}
