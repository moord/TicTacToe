package com.eqsian.tictactoe;
 
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
/**
 * Created by Андрей on 02.11.2017.
 */

public class DialogXO extends DialogFragment implements OnClickListener {

//  private int gameType;

  public static DialogXO newInstance(int gameType) {
    
    Bundle args = new Bundle();
    args.putInt("gameType", gameType);
    DialogXO fragment = new DialogXO();
    fragment.setArguments(args);
    return fragment;
  }
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    View v = inflater.inflate(R.layout.dialog_xo, null);
    v.findViewById(R.id.btnX).setOnClickListener(this);
    v.findViewById(R.id.btnO).setOnClickListener(this);
    // Get back arguments
//    gameType = getArguments().getInt("gameType", game.CLASSIC_GAME);

    return v;
  }
 
  public void onClick(View v) {
    Intent intent = new Intent(getActivity(), game.class);
    switch(v.getId()){
      case R.id.btnX:
        intent.putExtra("huPlayer",'X');
        intent.putExtra("aiPlayer",'O');
        break;
      case R.id.btnO:
        intent.putExtra("huPlayer",'O');
        intent.putExtra("aiPlayer",'X');
    }
    intent.putExtra("gameType", getArguments().getInt("gameType", game.CLASSIC_GAME));
    startActivity(intent);
    dismiss();
  }
 
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    //Log.d(LOG_TAG, "Dialog 1: onDismiss");
  }
 
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
   // Log.d(LOG_TAG, "Dialog 1: onCancel");
  }
}