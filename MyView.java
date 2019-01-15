package com.example.nicho.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        invalidate();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        invalidate();
    }

    public MyView(Context context) {
        super(context);
        invalidate();
    }

    private Paint paint = new Paint();
    private PointF pointA, pointB;

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y, paint);
        super.onDraw(canvas);
    }

    public void setPointA(PointF point){
        pointA = point;
    }
    public void setPointB(PointF point){
        pointB = point;
    }
    public void draw(){
        invalidate();
        requestLayout();
    }
}
