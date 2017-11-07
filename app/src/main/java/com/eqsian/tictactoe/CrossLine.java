package com.eqsian.tictactoe;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import static android.R.attr.path;

/**
 * Created by Андрей on 04.11.2017.
 */

public class CrossLine extends View {

    private Paint paint = new Paint();
    private int status = 0;

    public CrossLine(Context context) {
        super(context);
    }

    public void setStatus(int status) {
        if( this.status != status) {
            this.status = status;
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //вызывается при изменении размера view
/*
        mWidth = w; //запоминаем ширину view
        mHeight = h; //запоминаем высоту view
*/
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        int w = c.getWidth();
        int h = c.getHeight();

        c.drawColor(Color.TRANSPARENT);

        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        switch (status) {
            case 1:
                // 1 горизонтильная верх
                c.drawLine(5, h / 6, w - 5, h / 6, paint);
                break;
            case 2:
                // 2 горизонтильная середина
                c.drawLine(5, h / 2, w - 5, h / 2, paint);
                break;
            case 3:
                // 3 горизонтильная низ
                c.drawLine(5, h - h / 6, w - 5, h - h / 6, paint);
                break;
            case 4:
                // 4 вертикальная лев
                c.drawLine(w / 6, 5, w / 6, h - 5, paint);
                break;
            case 5:
                // 5 вертикальная середина
                c.drawLine(w / 2, 5, w / 2, h - 5, paint);
                break;
            case 6:
                // 6 вертикальная прав
                c.drawLine(w - w / 6, 5, w - w / 6, h - 5, paint);
                break;
            case 7:
                // 7 диагональнапя лев.верх - прав.низ
                c.drawLine(5, 5, w - 5, h - 5, paint);
                break;
            case 8:
                // 8 диагональнапя лев.низ - прав.верх
                c.drawLine(5, h - 5, w - 5, 5, paint);
                break;
        }
    }

}
