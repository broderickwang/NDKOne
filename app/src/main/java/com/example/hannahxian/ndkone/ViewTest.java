package com.example.hannahxian.ndkone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Broderick
 * User: hannahxian
 * Date: 2018/5/30
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class ViewTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }


    class MyView extends SurfaceView implements SurfaceHolder.Callback{

        private SurfaceHolder mSurfaceHolder;

        private MyThread mMyThread;


        public MyView(Context context) {
            super(context);
            mSurfaceHolder = this.getHolder();
            mSurfaceHolder.addCallback(this);
            mMyThread = new MyThread(mSurfaceHolder);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mMyThread.mIsRun = true;
            mMyThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mMyThread.mIsRun = false;
        }
    }

    class MyThread extends Thread{

        private SurfaceHolder mSurfaceHolder;
        public boolean mIsRun;

        public MyThread(SurfaceHolder holder) {
            this.mSurfaceHolder = holder;
            mIsRun = true;
        }

        @Override
        public void run() {
            int count = 0;
            while (mIsRun){
                Canvas canvas = null;
                try{
                    synchronized (mSurfaceHolder){
                        canvas = mSurfaceHolder.lockCanvas();
                        canvas.drawColor(Color.RED);
                        Paint p = new Paint();
                        p.setColor(Color.WHITE);
                        p.setTextSize(35);
                        int left = 100;
                        int top = 50;
                        int right = 300;
                        int bottom = 250;
                        Rect r = new Rect(left+count,top+count,right+count,bottom+count);
                        canvas.drawRect(r,p);
                        canvas.drawText("this is "+(count++)+" seconds",100,310,p);
                        Thread.sleep(1000);
                    }
                }catch (Exception e){

                }finally {
                    if(canvas!=null){
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
