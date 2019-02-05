package com.example.nicho.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class MyView extends View {
    public static ArrayList<PointF> pointListA;
    public static ArrayList<PointF> pointListB;

    // Default constructors
    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        invalidate();
        pointListA = new ArrayList<PointF>();
        pointListB = new ArrayList<PointF>();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        invalidate();
        pointListA = new ArrayList<PointF>();
        pointListB = new ArrayList<PointF>();
    }

    public MyView(Context context) {
        super(context);
        invalidate();
        pointListA = new ArrayList<PointF>();
        pointListB = new ArrayList<PointF>();
    }

    private Paint paint = new Paint();
    private PointF pointA, pointB;

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);

        int i = 0;
        for(PointF p : pointListA){
            PointF m = pointListB.get(i);
            canvas.drawLine(p.x,p.y,m.x,m.y, paint);
            paint.setTextSize(36);
            canvas.drawText( i + "a", p.x - 10, p.y - 10, paint);
            canvas.drawText(i + "b", m.x - 10, m.y - 10, paint);
            i++;
        }

        // New points created on touch, draw
        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y, paint);
        paint.setTextSize(36);
        canvas.drawText( i + "a", pointA.x - 10, pointA.y - 10, paint);
        canvas.drawText(i + "b", pointB.x - 10, pointB.y - 10, paint);
        // Then add them to the arraylists
        pointListA.add(pointA);
        pointListB.add(pointB);
        // Then add them to MyView2 arraylists
        MyView2.addPoints(pointA, pointB);

        super.onDraw(canvas);
    }

    // Methods for touchscreen setting of points
    public void setPointA(PointF point){
        pointA = point;
    }
    public void setPointB(PointF point){
        pointB = point;
    }

    // Method empties arraylists
    public void clearArray(){
        pointListA = new ArrayList<PointF>();
        pointListB = new ArrayList<PointF>();
    }

    public void draw(){
        invalidate();
        requestLayout();
    }
}
