package com.eqsian.tictactoe;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnPlayClassic;
    Button btnPlayRandom;
    Button btnHelp;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnPlayClassic = (Button) findViewById(R.id.btnPlayClassic);
        btnPlayClassic.setOnClickListener(this);

        btnPlayRandom = (Button) findViewById(R.id.btnPlayRandom);
        btnPlayRandom.setOnClickListener(this);

        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DialogXO myDialogFragment;
        switch (v.getId()) {
            case R.id.btnPlayClassic:
                myDialogFragment = DialogXO.newInstance(game.CLASSIC_GAME);
                myDialogFragment.show(getFragmentManager(), "dialog");
                break;
            case R.id.btnPlayRandom:
                myDialogFragment = DialogXO.newInstance(game.RANDOM_GAME);
                myDialogFragment.show(getFragmentManager(), "dialog");
                break;
            case R.id.btnHelp:
                Toast.makeText(this,"АНЯ ПОМОГИ!!!",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

    }

}
