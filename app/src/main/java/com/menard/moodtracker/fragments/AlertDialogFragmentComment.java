package com.menard.moodtracker.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragmentComment extends DialogFragment {

    private Listener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (Listener) context;
    }

    public AlertDialogFragmentComment newInstance (){
        AlertDialogFragmentComment fragmentComment = new AlertDialogFragmentComment();
        return fragmentComment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final EditText editText = new EditText(getContext());

        return new AlertDialog.Builder(getContext())
        .setMessage("Commentaires")
        .setView(editText)
        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mComment = editText.getText().toString();
                mListener.onCommentSelected(mComment);
                Toast.makeText(getContext(), "Commentaire mis à jour", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        })
        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
