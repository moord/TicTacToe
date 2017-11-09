package com.eqsian.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.N;


public class game extends AppCompatActivity implements View.OnClickListener {
    final int GRID_SIZE = 3;

    ConstraintLayout my_layout;
    TextView txtStatus;
    TextView txtScore;
    char origBoard[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    List<TicTacToeButton> btns = new ArrayList<>();

    Button btnReset;

    String win_state = "Победа";
    int block;
    int huScore, aiScore;
    CrossLine cross;

    ImageView imageViewHU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        block = 0;
        huScore = 0;
        aiScore = 0;
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtScore = (TextView) findViewById(R.id.txtScore);

        txtScore.setText(String.valueOf(huScore) + " : " + String.valueOf(aiScore));
        txtStatus.setText(R.string.hu_move);

        btnReset = (Button) findViewById(R.id.btnReset);

        imageViewHU = (ImageView) findViewById(R.id.imgHU);
        changeImageHU();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_game();
            }
        });


        int h_constraint[] = new int[GRID_SIZE - 1];
        int v_constraint[] = new int[GRID_SIZE - 1];

        my_layout = (ConstraintLayout) findViewById(R.id.constr_layout);

        ConstraintSet set = new ConstraintSet();

        // copy constraints settings from current ConstraintLayout to set
        set.clone(my_layout);

        for (int i = 0; i < GRID_SIZE - 1; i++) {
            View view = new View(this);

            view.setId(ViewIdGenerator.generateViewId());
            view.setBackgroundColor(getResources().getColor(R.color.grid));
            my_layout.addView(view);

            set.connect(view.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            set.connect(view.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            set.constrainWidth(view.getId(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            set.constrainHeight(view.getId(), ConstraintSet.MATCH_CONSTRAINT);

            h_constraint[i] = view.getId();

            view = new View(this);

            view.setId(ViewIdGenerator.generateViewId());
            view.setBackgroundColor(getResources().getColor(R.color.grid));
            my_layout.addView(view);

            set.connect(view.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
            set.connect(view.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

            set.constrainWidth(view.getId(), ConstraintSet.MATCH_CONSTRAINT);
            set.constrainHeight(view.getId(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));

            v_constraint[i] = view.getId();

        }

        set.createHorizontalChain(
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                h_constraint,
                null,
                ConstraintSet.CHAIN_SPREAD);

        set.createVerticalChain(
                ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,
                v_constraint,
                null,
                ConstraintSet.CHAIN_SPREAD);


        for (int k = 0; k < GRID_SIZE; k++) {
            for (int i = 0; i < GRID_SIZE; i++) {

                TicTacToeButton btn = new TicTacToeButton(this);

                btn.setId(ViewIdGenerator.generateViewId());
                //btn.setText(String.valueOf(origBoard[k * 3 + i]));
                btn.setTag(k * 3 + i);

                btns.add(btn);

                my_layout.addView(btn);

                if (i == 0) {
                    set.connect(btn.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    set.connect(btn.getId(), ConstraintSet.RIGHT, h_constraint[i], ConstraintSet.LEFT);
                } else if (i == GRID_SIZE - 1) {
                    set.connect(btn.getId(), ConstraintSet.LEFT, h_constraint[i - 1], ConstraintSet.RIGHT);
                    set.connect(btn.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

                } else {
                    set.connect(btn.getId(), ConstraintSet.LEFT, h_constraint[i - 1], ConstraintSet.RIGHT);
                    set.connect(btn.getId(), ConstraintSet.RIGHT, h_constraint[i], ConstraintSet.LEFT);
                }


                if (k == 0) {
                    set.connect(btn.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    set.connect(btn.getId(), ConstraintSet.BOTTOM, v_constraint[k], ConstraintSet.TOP);
                } else if (k == GRID_SIZE - 1) {
                    set.connect(btn.getId(), ConstraintSet.TOP, v_constraint[k - 1], ConstraintSet.BOTTOM);
                    set.connect(btn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else {
                    set.connect(btn.getId(), ConstraintSet.TOP, v_constraint[k - 1], ConstraintSet.BOTTOM);
                    set.connect(btn.getId(), ConstraintSet.BOTTOM, v_constraint[k], ConstraintSet.TOP);
                }

                set.constrainWidth(btn.getId(), ConstraintSet.MATCH_CONSTRAINT);
                set.constrainHeight(btn.getId(), ConstraintSet.MATCH_CONSTRAINT);

                btn.setOnClickListener(this);
            }
        }

        cross = new CrossLine(this);
        cross.setId(ViewIdGenerator.generateViewId());
        my_layout.addView(cross);

        set.connect(cross.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.connect(cross.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.connect(cross.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(cross.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

        set.applyTo(my_layout);
    }

    public void changeImageHU() {
        int scoreDiff = huScore - aiScore;
        if (scoreDiff < 0) {
            imageViewHU.setImageResource(R.drawable.level0);
        } else if (scoreDiff < 2) {
            imageViewHU.setImageResource(R.drawable.level1);
        } else if (scoreDiff < 4){
            imageViewHU.setImageResource(R.drawable.level2);
        } else {
            imageViewHU.setImageResource(R.drawable.level3);
        }

    }

    public void reset_game() {
        for (int i = 0; i < 9; i++) {
            origBoard[i] = (char) i;
        }
        for (TicTacToeButton btn : btns) {
            //btn.setText("");
            btn.setStatus((char) 0);
        }
        cross.setStatus(0);

        btnReset.setVisibility(View.GONE);
        txtScore.setText(String.valueOf(huScore) + " : " + String.valueOf(aiScore));

        block = 0; // новая игра
    }

    char huPlayer = 'X';
    //ai
    char aiPlayer = 'O';

    int fc;

    @Override
    public void onClick(View v) {
//        txtStatus.setText( String.valueOf(v.getTag()));

        if (((TicTacToeButton) v).getStatus() == (char) 0 && block == 0) {
            block = 1;


            // ((Button) v).setText(String.valueOf(huPlayer));
            ((TicTacToeButton) v).setStatus(huPlayer);
            // human

            // Нажата кнопка 'huPlayer'
            origBoard[(int) v.getTag()] = huPlayer;
//        char origBoard[] = {'O',1 ,'X','X',4 ,'X', 6 ,'O','O'};
            // Проверка на конец игры


            txtStatus.setText(R.string.ai_move);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    //my_button.setBackgroundResource(R.drawable.defaultcard);
//                    txtStatus.setText("прошло 2 сек");
                    if (!is_end()) {


                        int del_index = 10;
                        List<Integer> fullSpots = fullIndexies(origBoard);
                        if (fullSpots.size() > 2) {
                            Random random = new Random();
                            if ((random.nextInt(5)) > 2) {
                                del_index = random.nextInt(fullSpots.size());
                                Log.d("MY Logs", "i = " + String.valueOf(del_index));
                            }
                        }

                        fc = 0;
                        Move bestSpot = minimax(origBoard, aiPlayer);

                        // btns.get((byte) bestSpot.index).setText(String.valueOf(aiPlayer));
                        btns.get((byte) bestSpot.index).setStatus(aiPlayer);
                        origBoard[(byte) bestSpot.index] = aiPlayer;

                        txtStatus.setText(R.string.hu_move);
                        //txtStatus.setText( String.valueOf(fc) + ": Best move - " + String.valueOf((byte)bestSpot.index));

                        if (!is_end()) {
                            block = 0;

                            if (del_index != 10) {
                                btns.get(fullSpots.get(del_index)).setStatus((char) 0);
                                origBoard[fullSpots.get(del_index)] = (char) (fullSpots.get(del_index).intValue());
                            }
                        }
                    }

                }
            }, 750);

        }
    }


    private boolean is_end() {
        boolean rez = false;
        int win;
        if ((win = winning(origBoard, huPlayer)) != 0) {
            rez = true;
            win_state = getString(R.string.you_win);
            huScore++;
            cross.setStatus(win);
        } else if ((win = winning(origBoard, aiPlayer)) != 0) {
            rez = true;
            win_state = getString(R.string.you_lose);
            aiScore++;
            cross.setStatus(win);
        } else if (emptyIndexies(origBoard).size() == 0) {
            rez = true;
            win_state = getString(R.string.draw);
        }

        if (rez) {
            //MyDialogFragment myDialogFragment = new MyDialogFragment();
            //myDialogFragment.show(getFragmentManager(), "dialog");
            txtStatus.setText("");
            btnReset.setVisibility(View.VISIBLE);
            txtScore.setText(win_state);

            changeImageHU();

        }
        return rez;
    }

    // the main minimax function
    public Move minimax(char[] newBoard, char player) {
        // add one to function calls
        Move rez = new Move();
        fc++;

        // available spots
        List<Character> availSpots = emptyIndexies(newBoard);

        // checks for the terminal states such as win, lose, and tie and returning a value accordingly
        if (winning(newBoard, huPlayer) != 0) {
            rez.score = -10;
            return rez;
        } else if (winning(newBoard, aiPlayer) != 0) {
            rez.score = 10;
            return rez;
        } else if (availSpots.size() == 0) {
            rez.score = 0;
            return rez;
        }

        // an array to collect all the objects
        List<Move> moves = new ArrayList<>();

        // loop through available spots
        //for (int i = 0; i < availSpots.size(); i++) {

        Random random = new Random();

        while (availSpots.size() > 0) {
            int i;
            i = random.nextInt(availSpots.size());

            // create an object for each and store the index of that spot that was stored as a number in the object's index key
            Move move = new Move();
            move.index = newBoard[availSpots.get(i)];

            // set the empty spot to the current player
            newBoard[availSpots.get(i)] = player;

            // if collect the score resulted from calling minimax on the opponent of the current player
            if (player == aiPlayer) {
                Move result = minimax(newBoard, huPlayer);
                move.score = result.score;
            } else {
                Move result = minimax(newBoard, aiPlayer);
                move.score = result.score;
            }

            // reset the spot to empty
            newBoard[availSpots.get(i)] = move.index;

            // push the object to the array
            moves.add(move);

            availSpots.remove(i);
            //////////////////////////////////////////////////////////////////////////////////////////
            // Мое улучшение
            if ((player == aiPlayer && move.score == 10) || (player == huPlayer && move.score == -10)) {
                break;
//			availSpots.clear();
            }
            //////////////////////////////////////////////////////////////////////////////////////////
        }

        // if it is the computer's turn loop over the moves and choose the move with the highest score
        int bestMove = 0;
        if (player == aiPlayer) {
            int bestScore = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        } else {

            // else loop over the moves and choose the move with the lowest score
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }

        // return the chosen move (object) from the array to the higher depth
        return moves.get(bestMove);
    }

    public List<Character> emptyIndexies(char[] board) {

        List<Character> rez = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (board[i] != 'O' && board[i] != 'X') {
                rez.add(board[i]);
            }
        }
        return rez;
    }

    public List<Integer> fullIndexies(char[] board) {

        List<Integer> rez = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (board[i] == 'O' || board[i] == 'X') {
                rez.add(i);
            }
        }
        return rez;
    }

    // winning combinations using the board indexies for instace the first win could be 3 xes in a row
    public int winning(char[] board, char player) {
        int rez = 0;
        if (board[0] == player && board[1] == player && board[2] == player) {
            rez = 1;
        } else if (board[3] == player && board[4] == player && board[5] == player) {
            rez = 2;
        } else if (board[6] == player && board[7] == player && board[8] == player) {
            rez = 3;
        } else if (board[0] == player && board[3] == player && board[6] == player) {
            rez = 4;
        } else if (board[1] == player && board[4] == player && board[7] == player) {
            rez = 5;
        } else if (board[2] == player && board[5] == player && board[8] == player) {
            rez = 6;
        } else if (board[0] == player && board[4] == player && board[8] == player) {
            rez = 7;
        } else if (board[2] == player && board[4] == player && board[6] == player) {
            rez = 8;
        }
        return rez;
    }

}

class Move {
    public char index;
    public int score;
}

class ViewIdGenerator {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @SuppressLint("NewApi")
    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }

    }
}

