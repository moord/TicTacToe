package com.eqsian.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Андрей on 02.11.2017.
 */

public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = ((game)getActivity()).win_state;
        String button1String = "Новая игра";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);  // заголовок
        builder.setCancelable(false);
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((game)getActivity()).reset_game();
                dismiss();
            }
        });

        return builder.create();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (this.getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
        }
    }
}

