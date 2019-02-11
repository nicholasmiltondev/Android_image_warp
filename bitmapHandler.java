package com.example.nicho.myapplication2;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.util.ArrayList;

public class bitmapHandler {
    public PointF[] pointListG; // Difference per frame, start point.
    public PointF[] pointListH; // Difference per frame, end point.
    public ArrayList<PointF> pointListA; // Starting point, starting vector.
    public ArrayList<PointF> pointListB; // Ending point, starting vector.
    public ArrayList<PointF> pointListC; // Starting point, ending vector.
    public ArrayList<PointF> pointListD; // End point, ending vector.
    public ArrayList<PointF> pointListE; // Starting point, intermediate vector.
    public ArrayList<PointF> pointListF; // Ending point, intermediate vector.
    private int nf;
    PixelFactory px;

    public bitmapHandler(){

    };
    // Constructor sets local arrays.
    public bitmapHandler(int numFrames, Bitmap start, Bitmap end){
        nf = numFrames;
        pointListA = MyView.pointListA;
        pointListB = MyView.pointListB;
        pointListC = MyView2.pointListC;
        pointListD = MyView2.pointListD;
        pointListE = new ArrayList<PointF>();
        pointListF = new ArrayList<PointF>();
        pointListG = new PointF[pointListA.size()];
        pointListH = new PointF[pointListA.size()];
        px = new PixelFactory();

        for (int i = 0; i < pointListG.length; i++) {
            pointListG[i] = subPoints(MyView.pointListA.get(i), MyView2.pointListC.get(i), (float)(nf - 1));
            pointListH[i] = subPoints(MyView.pointListB.get(i), MyView2.pointListD.get(i), (float)(nf - 1));
        }
}

    public void createTransitionArrays(){
        for (int i = 0; i < pointListG.length; i++) {
            pointListE.add(addPoints(MyView.pointListA.get(i), pointListG[i]));
            pointListF.add(addPoints(MyView.pointListB.get(i), pointListH[i]));

        }
    }

    public Bitmap createBitmapTransition(Bitmap b){
        px.loadBitmap(b);
        Bitmap o = px.warpBitmap(pointListA, pointListB, pointListE, pointListF);
        return o;
    }

    public PointF addPoints(PointF a, PointF b){
        PointF x = new PointF();
        x.x = a.x + b.x;
        x.y = a.y + b.y;
        return x;
}
    public PointF subPoints(PointF a, PointF b, float f){
        PointF x = new PointF();
        x.x = (b.x - a.x)/f;
        x.y = (b.y - a.y)/f;
        return x;
    }
}