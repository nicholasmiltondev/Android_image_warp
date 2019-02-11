package com.example.nicho.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static int RESULT_LOAD_IMAGE = 1;

    // Variables to store screentouch coordinates.
    PointF pointA = new PointF(0, 0);
    PointF pointB = new PointF(0, 0);
    private float x;
    private float y;
    private int indexC = -1;
    private int indexD = -1;

    private MyView mLineView;
    private MyView2 mLineView2;
    private bitmapHandler bh;

    LinearLayout ln;
    public Bitmap[] transitionBitmaps;
    private Bitmap start;
    private Bitmap end;
    private int bIndex;
    private int numframes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "Debug started");
        ln = (LinearLayout)findViewById(R.id.warpingLayout);
        ln.setVisibility(View.INVISIBLE);

        mLineView = findViewById(R.id.lineView);
        mLineView2 = findViewById(R.id.lineView2);
        mLineView.setPointA(pointA);
        mLineView.setPointB(pointB);
        mLineView.draw();
        mLineView2.draw2();

        mLineView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(getApplicationContext(), "Touch me in the morning", Toast.LENGTH_SHORT).show();

                x = event.getX();
                y = event.getY();
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    mLineView.setPointA(new PointF(x, y));
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    mLineView.setPointB(new PointF(x, y));
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    mLineView.draw();
                    mLineView2.draw2();
                }
                return true; // Need return type signals event handled.
            }
        });
        mLineView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(getApplicationContext(), "Touch me in the morning", Toast.LENGTH_SHORT).show();

                x = event.getX();
                y = event.getY();

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    indexC = mLineView2.getPointC(x, y);
                    indexD = mLineView2.getPointD(x, y);
                    //Toast.makeText(getApplicationContext(), "Touch me" + indexC + " " + indexD, Toast.LENGTH_SHORT).show();
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    pointA = new PointF(x, y);
                    //Toast.makeText(getApplicationContext(), "Moving" + x + " " + y, Toast.LENGTH_SHORT).show();
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(indexC != -1){
                        mLineView2.movePointC(pointA, indexC);
                    }
                    if(indexD != -1){
                        mLineView2.movePointD(pointA, indexD);
                    }
                    mLineView2.draw2();
                }
                return true; // Need return type signals event handled.
            }
        });
        // Button calls load picture menu for image 1.
        Button buttonLoadImage = findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });
        // Button calls load picture menu for image 2.
        Button buttonLoadImage2 = findViewById(R.id.buttonLoadPicture2);
        buttonLoadImage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 2);
            }
        });
        // Button clears lines.
        Button buttonClearLines = findViewById(R.id.buttonClearLines);
        buttonClearLines.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView imageView = findViewById(R.id.firstImage);
                ImageView imageView2 = findViewById(R.id.secondImage);
                imageView.setImageResource(R.drawable.compassimage);
                imageView2.setImageResource(R.drawable.compassimage);
                mLineView.clearArray();
                mLineView2.clearArray2();
                pointA = new PointF(0, 0);
                pointB = new PointF(0, 0);
                mLineView.setPointA(pointA);
                mLineView.setPointB(pointB);
                mLineView.draw();
                mLineView2.draw2();
            }
        });
        // Button activates the warping by calling bitmapHandler
        Button buttonPrevFrame = findViewById(R.id.buttonPrevFrame);
        buttonPrevFrame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText ed = findViewById(R.id.textNumberFrames);
                String value = ed.getText().toString();
                numframes = Integer.parseInt(value);
                Log.v(TAG, "string value: " + numframes);
                transitionBitmaps = new Bitmap[numframes];
                transitionBitmaps[0] = start;
                transitionBitmaps[numframes -1] = end;
                bh = new bitmapHandler(numframes, start, end);
                bh.createTransitionArrays();
                transitionBitmaps[1] = bh.createBitmapTransition(start);
                //bh.createTransitionArrays();
                //transitionBitmaps[2] = bh.createBitmapTransition(start);

                bIndex = 0;
                ImageView imageView = findViewById(R.id.warpingImage);
                imageView.setImageBitmap(transitionBitmaps[0]);

                ln.setVisibility(View.VISIBLE);
            }
        });

        //Button closes popup viewer.
        Button buttonClose = findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ln = (LinearLayout)findViewById(R.id.warpingLayout);
                ln.setVisibility(View.INVISIBLE);
            }
        });

        Button buttonNextImage = findViewById(R.id.buttonNextImage);
        buttonNextImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView imageView = findViewById(R.id.warpingImage);
                if(bIndex<numframes-1)
                    ++bIndex;
                imageView.setImageBitmap(transitionBitmaps[bIndex]);
            }
        });
        Button buttonBackImage = findViewById(R.id.buttonBackImage);
        buttonBackImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView imageView = findViewById(R.id.warpingImage);
                if(bIndex>0)
                    --bIndex;
                imageView.setImageBitmap(transitionBitmaps[bIndex]);
            }
        });
    }

    // Defunct method, need to delete.
    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    // Methods loads bitmap to image view 1.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.firstImage);
            File img = new File(picturePath);
            start = BitmapFactory.decodeFile(img.getAbsolutePath());
            imageView.setImageBitmap(start);
        }

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView2 = findViewById(R.id.secondImage);
            File img = new File(picturePath);
            end = BitmapFactory.decodeFile(img.getAbsolutePath());
            imageView2.setImageBitmap(end);
        }
    }
}