package com.eqsian.tictactoe;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Андрей on 03.11.2017.
 */

public class TicTacToeButton extends View {
    Path path;
    Paint paint;
    float length;
    private int mWidth; //ширина view
    private int mHeight; //высота view
    private char status;
    public Boolean animation;

    public TicTacToeButton(Context context) {
        super(context);
        status = 0;
        animation = true;
        init();
    }

    public TicTacToeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        status = 0;
        animation = true;
        init();
    }

    public TicTacToeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        status = 0;
        animation = true;
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();

        if (status == 'O') {
            paint.setColor(Color.BLUE);
            RectF rect = new RectF(20, 20, mWidth - 20, mHeight - 20);
            path.addArc(rect, 180, 90);
            path.addArc(rect, 0, 90);
            path.addArc(rect, 90, 90);
            path.addArc(rect, -90, 90);
        } else if (status == 'X') {
            paint.setColor(Color.RED);
            int centerX = mWidth / 2,
                    centerY = mHeight / 2;
            path.moveTo(centerX, centerY);
            path.lineTo(20, 20);
            path.moveTo(centerX, centerY);
            path.lineTo(mWidth - 20, 20);
            path.moveTo(centerX, centerY);
            path.lineTo(mWidth - 20, mHeight - 20);
            path.moveTo(centerX, centerY);
            path.lineTo(20, mHeight - 20);
        } else {
            path.reset();
        }

        // Measure the path
        if( animation) {
            PathMeasure measure = new PathMeasure(path, false);
            length = measure.getLength();

//            float[] intervals = new float[]{length, length};

            ObjectAnimator animator = ObjectAnimator.ofFloat(TicTacToeButton.this, "phase", 1.0f, 0.0f);
            animator.setDuration(500);
            animator.start();
        }
        else{
            paint.setPathEffect(new DiscretePathEffect(2, 1));
            invalidate();
        }
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        if (this.status != status) {
            this.status = status;
            init();
        }
    }

    //is called by animtor object
    public void setPhase(float phase) {
        paint.setPathEffect(new ComposePathEffect(new DiscretePathEffect(2, 1), createPathEffect(length, phase, 0.0f)));
        invalidate();//will calll onDraw
    }

    private PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
                Math.max(phase * pathLength, offset));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //вызывается при изменении размера view
        mWidth = w; //запоминаем ширину view
        mHeight = h; //запоминаем высоту view
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawPath(path, paint);
    }
}

