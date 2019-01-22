package com.example.nicho.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static int RESULT_LOAD_IMAGE = 1;

    PointF pointA = new PointF(10, 100);
    PointF pointB = new PointF(500, 400);
    private float x;
    private float y;

    private MyView mLineView;
    private MyView2 mLineView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.firstImage);
            ImageView imageView2 = findViewById(R.id.secondImage);
            File img = new File(picturePath);
            imageView.setImageBitmap(BitmapFactory.decodeFile(img.getAbsolutePath()));
            imageView2.setImageBitmap(BitmapFactory.decodeFile(img.getAbsolutePath()));
        }

    }
}
