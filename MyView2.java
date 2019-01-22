package com.example.nicho.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class MyView2 extends View {
    private static ArrayList<PointF> pointListC;
    private static ArrayList<PointF> pointListD;

    // Default constructors
    public MyView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        invalidate();
        pointListD = new ArrayList<PointF>();
        pointListC = new ArrayList<PointF>();
    }

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        invalidate();
        pointListD = new ArrayList<PointF>();
        pointListC = new ArrayList<PointF>();
    }

    public MyView2(Context context) {
        super(context);
        invalidate();
        pointListD = new ArrayList<PointF>();
        pointListC = new ArrayList<PointF>();
    }

    public static void addPoints(PointF pointC, PointF pointD){
        pointListC.add(pointC);
        pointListD.add(pointD);
    }

    private Paint paint = new Paint();
    private PointF pointC, pointD;

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);

        int i = 0;
        for(PointF p : pointListC){
            PointF m = pointListD.get(i);
            canvas.drawLine(p.x,p.y,m.x,m.y, paint);
            i++;
        }

//        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y, paint);
//        pointListA.add(pointA);
//        pointListB.add(pointB);
        super.onDraw(canvas);
    }
    
    public void setPointC(PointF point){
        pointC = point;
    }
    public void setPointD(PointF point){
        pointD = point;
    }
    public void draw2(){
        invalidate();
        requestLayout();
    }
}
