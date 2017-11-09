package com.eqsian.tictactoe;
 
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * Created by Андрей on 02.11.2017.
 */

public class DialogXO extends DialogFragment implements OnClickListener {
 
  final String LOG_TAG = "myLogs";

  private int gameType;

  public static DialogXO newInstance(int gameType) {
    
    Bundle args = new Bundle();
    args.putInt("gameType", gameType);
    DialogXO fragment = new DialogXO();
    fragment.setArguments(args);
    return fragment;
  }
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    getDialog().setTitle("Title!");
    View v = inflater.inflate(R.layout.dialog_xo, null);
    v.findViewById(R.id.btnX).setOnClickListener(this);
    v.findViewById(R.id.btnO).setOnClickListener(this);
    // Get back arguments
    gameType = getArguments().getInt("gameType", game.CLASSIC_GAME);

    return v;
  }
 
  public void onClick(View v) {
    Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
    dismiss();
  }
 
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    Log.d(LOG_TAG, "Dialog 1: onDismiss");
  }
 
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    Log.d(LOG_TAG, "Dialog 1: onCancel");
  }
}
/*
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

  */