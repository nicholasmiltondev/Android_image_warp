package com.example.nicho.myapplication2;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

// Class contains bitmap warping functions
public class PixelFactory {

    private static final String TAG = "MyActivity";
    public int[] pixels;
    public int[] finalPixels;
    int width;
    int height;

    // Methods loads whole bitmap and sets width and height
    public void loadBitmap(Bitmap bitmap){
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int[height*width];
        bitmap.getPixels(pixels, 0, width, 0, 0,
                width, height);
    }
    public Bitmap warpBitmap() {
        Bitmap warpedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        changePixels();
        warpedBitmap.setPixels(finalPixels, 0, width, 0, 0, width, height);
        return warpedBitmap;
    }

    public void changePixels(){
        finalPixels = new int[height*width];
        for (int i = 0; i < height*width; i++) {
            PointF f = findSourcePoint(i%width, i/width);
            if(f.x < 0)
                f.x = 0;
            if(f.x >= width)
                f.x = width - 1;
            if(f.y < 0)
                f.y = 0;
            if(f.y >= height)
                f.y = height - 1;
            int x = Math.round(f.x);
            int y = Math.round(f.y);
            finalPixels[i] = pixels[y*width + x];
            Log.v(TAG, "finalpixels " + i);
        }
    }

    //question 1
    public float distanceFromPointToLine(float pointX, float pointY, float headX, float headY, float tailX, float tailY){
        PointF normal = new PointF(-(tailY - headY), tailX - headX);
        PointF XP = new PointF(headX - pointX, headY - pointY);
        return (normal.x*XP.x + normal.y*XP.y)/(float)(Math.sqrt(normal.x*normal.x + normal.y*normal.y));
    }
    //question 2
    public float fractionalBisect(float pointX, float pointY, float headX, float headY, float tailX, float tailY){
        PointF PQ = new PointF(tailX - headX, tailY - headY);
        PointF PX = new PointF(pointX - headX, pointY - headY);
        float magPQ =(float)(Math.sqrt(PQ.x*PQ.x+PQ.y*PQ.y));
        float projPQPX = (PX.x*PQ.x+PX.y*PQ.y)/magPQ;
        return projPQPX/magPQ;
    }
    //question 3
    public PointF sourcePointFromTheLine(float headX, float headY, float tailX, float tailY, float fractBisect, float distance){
        PointF PQ = new PointF(tailX - headX, tailY - headY);
        PointF pointOnLine = new PointF(headX + (float)(fractBisect*PQ.x), headY + (float)(fractBisect*PQ.y));
        PointF normal = new PointF(-(PQ.y), PQ.x);
        float normalLength = (float)Math.sqrt(normal.x*normal.x + normal.y*normal.y);
        normal.x = normal.x/normalLength;
        normal.y = normal.y/normalLength;
        return new PointF(Math.round(pointOnLine.x - distance*normal.x), Math.round(pointOnLine.y - distance*normal.y));
    }

    public float findWeight(float distance){
        return (float)(1/Math.pow(distance + 0.01, 2));
    }

    // Method combines above steps and question 4
    public PointF findSourcePoint(int destX, int destY){
        float destXF = (float)destX;
        float destYF = (float)destY;

        float weightTotal = 0;
        PointF wDeltaTotal = new PointF(0, 0);

        for(int i = 2; i < MyView2.pointListC.size(); i++) {
            float dist = distanceFromPointToLine(destXF, destYF, MyView2.pointListC.get(i).x, MyView2.pointListC.get(i).y, MyView2.pointListD.get(i).x, MyView2.pointListD.get(i).y);
            float w = findWeight(dist);
            weightTotal += w;

            float fb = fractionalBisect(destXF, destYF, MyView2.pointListC.get(i).x, MyView2.pointListC.get(i).y, MyView2.pointListD.get(i).x, MyView2.pointListD.get(i).y);
            PointF temp = sourcePointFromTheLine(MyView.pointListA.get(i).x, MyView.pointListA.get(i).y, MyView.pointListB.get(i).x, MyView.pointListB.get(i).y, fb, dist);
             temp.x -= destXF;
            temp.y -= destYF;

            temp.x = temp.x*w;
            temp.y = temp.y*w;
            wDeltaTotal.x += temp.x;
            wDeltaTotal.y += temp.y;
        }
        wDeltaTotal.x = wDeltaTotal.x/weightTotal + destXF;
        wDeltaTotal.y = wDeltaTotal.y/weightTotal + destYF;

        //Log.v(TAG, "pixel at " + wDeltaTotal.x + " " + wDeltaTotal.y);
        return wDeltaTotal;
    }
}
