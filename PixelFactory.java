package com.example.nicho.myapplication2;

import android.graphics.Bitmap;
import android.graphics.PointF;

// Class contains bitmap warping functions
public class PixelFactory {
    // Methods loads whole bitmap
    public static int[] getFullBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[height*width];
        bitmap.getPixels(pixels, 0, width, 0, 0,
                width, height);
        return pixels;
    }
    // Methods loads partial bitmap !!!!!!!!!!!!!!!!!!!!INCOMPLETE!!!!!!!!!!!!!
    public static int[] getCustomBitmap(Bitmap bitmap, int x, int y){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[height*width];
        bitmap.getPixels(pixels, 0, width, 0, 0,
                width, height);
        //// code for loading sub
        final int[] subsetPixels = new int[width * height];
        for (int row = 0; row < height; row++) {
            System.arraycopy(pixels, (row * bitmap.getWidth()),
                    subsetPixels, row * width, width);
        }
        return subsetPixels;
    }
    public float distanceFromPointToLine(float pointX, float pointY, float headX, float headY, float tailX, float tailY){
        PointF normal = new PointF(-(tailY - headY), tailX - headX);
        PointF XP = new PointF(headX - pointX, headY - pointY);
        return (normal.x*XP.x + normal.y*XP.y)/(float)(Math.sqrt(normal.x*normal.x + normal.y*normal.y));
    }
    public float fractionalBisect(float pointX, float pointY, float headX, float headY, float tailX, float tailY){
        PointF PQ = new PointF(tailX - headX, tailY - headY);
        PointF PX = new PointF(pointX - headX, pointY - headY);
        float magPQ =(float)(Math.sqrt(PQ.x*PQ.x+PQ.y*PQ.y));
        float projPQPX = (PX.x*PQ.x+PX.y*PQ.y)/magPQ;
        return projPQPX/magPQ;
    }

    
}
