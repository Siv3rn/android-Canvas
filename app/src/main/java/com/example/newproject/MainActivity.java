package com.example.newproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();

    private static final int OFFSET =120;
    private int mOffset = OFFSET;
    private static final int MULTIPLIER = 100;


    private int mColorBackground;
    private int mColorRectangle;;
    private int mColorAccent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mColorBackground = ResourcesCompat.getColor(getResources(),R.color.colorBackground,null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),R.color.colorRectangle,null);
        mColorAccent = ResourcesCompat.getColor(getResources(),R.color.colorAccent,null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(),R.color.black,null));

        mPaintText.setTextSize(70);
        mImageView = findViewById(R.id.myImageVIew);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSomething(v);
            }
        });

    }

    private void drawSomething(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        int hawidth = width/2;
        int haheight = height/2;

        if (OFFSET == mOffset ){
            mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas =new Canvas(mBitmap);

            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping),100,100,mPaintText);
            mOffset += OFFSET;
        }
        else{
            if (mOffset < hawidth && mOffset < haheight){
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);

                mRect.set(mOffset,mOffset,width-mOffset,height-mOffset);

                mCanvas.drawRect(mRect,mPaint);
                mOffset += OFFSET;

            }
            else {
                mPaint.setColor(mColorAccent - MULTIPLIER * mOffset);
                mCanvas.drawCircle(hawidth,haheight,hawidth/5,mPaint);
                mOffset += OFFSET;


                String text = getString(R.string.done);
                mPaintText.getTextBounds(text,0,text.length(),mBounds);
                int x = hawidth - mBounds.centerX();
                int y = haheight - mBounds.centerY();
                mCanvas.drawText(text,x,y,mPaintText);



                Point a = new Point(hawidth - 50,haheight -50);
                Point b = new Point(hawidth + 50, haheight +50);
                Point c = new Point(hawidth,haheight + 250);

                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.lineTo(a.x,a.y);
                path.lineTo(b.x,b.y);
                path.lineTo(c.x,c.y);
                path.lineTo(a.x,a.y);
                path.close();

                mCanvas.drawPath(path,mPaint);
            }
        }



    }
}