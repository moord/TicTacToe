package com.eqsian.tictactoe;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {


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
        Intent intent;
        switch (v.getId()) {
            case R.id.btnPlayClassic:
                intent = new Intent(this, game.class);
                intent.putExtra("huPlayer",'X');
                intent.putExtra("aiPlayer",'O');
                intent.putExtra("gameType", game.CLASSIC_GAME);
                startActivity(intent);
                break;
            case R.id.btnPlayRandom:
                intent = new Intent(this, game.class);
                intent.putExtra("huPlayer",'X');
                intent.putExtra("aiPlayer",'O');
                intent.putExtra("gameType", game.RANDOM_GAME);
                startActivity(intent);
                break;
            case R.id.btnHelp:
//                DialogFragment myDialogFragment = new DialogXO();
                DialogXO myDialogFragment = DialogXO.newInstance(game.RANDOM_GAME);
                myDialogFragment.show(getFragmentManager(), "dialog");
                Toast.makeText(this,"АНЯ ПОМОГИ!!!",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

    }

}
