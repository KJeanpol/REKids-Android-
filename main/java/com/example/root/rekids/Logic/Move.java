package com.example.root.rekids.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.example.root.rekids.R;

/**
 * Created by jose on 26/08/2017.
 */

public class Move extends Activity implements SensorEventListener2 {

    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float xMax, yMax;
    Bitmap airplane;
    SensorManager sensorManager;
    Draw ourView;

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        try{
//            Thread.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = sensorEvent.values[0];
            yAccel = -sensorEvent.values[1]; //LLLEVA -
            updateBit();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void updateBit() {
        float frameTime = 0.666f;
        xVel += (xAccel * frameTime);
        yVel += (yAccel * frameTime);

        float xS = (xVel / 10) * frameTime; //tHIS SHIT PROVIDE THE ANGULE OF THE POSITION
        float yS = (yVel / 10) * frameTime;  // WITH LESS DENOMINADOR THE BALL MOVE IS MORE DIFFICULT

        xPos -= xS;
        yPos -= yS;

        if (xPos > xMax) {
            xPos = xMax;
        } else if (xPos < 0) {
            xPos = 0;
        }

        if (yPos > yMax) {
            yPos = yMax;
        } else if (yPos < 0) { //THIS MOTHERFUCKER NIGGA BITCH IS NOT WORKING!!!!!!!!! THE BALL GETS OUT OF THE Y MIN CORDINATE
            yPos = 0;
        }


    }
    public class Draw extends SurfaceView implements Runnable {
        SurfaceHolder ourHolder;
        Thread ourThread = null;
        boolean isRunning = true;


        public Draw(Context context) {
            super(context);
            ourHolder = getHolder();
            Bitmap ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.airplane);
            final int dstWidth = 100;
            final int dstHeight = 100;
            airplane = Bitmap.createScaledBitmap(ballSrc, dstWidth, dstHeight, true);
        }
        public void pause(){
            isRunning = false;
            while (true){
                try{
                    ourThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            ourThread = null;
        }
        public void resume(){
            isRunning = true;
            ourThread = new Thread(this);
            ourThread.start();
        }
        @Override
        public void run(){
            while (isRunning){
                if(!ourHolder.getSurface().isValid())
                    continue;
                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.cielo),0,0,null);
                canvas.drawBitmap(airplane,xPos,yPos,null);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0){

            Sensor s = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 100;
        yMax = 750;

        airplane = BitmapFactory.decodeResource(getResources(), R.drawable.airplane);
        ourView = new Draw(this);
        ourView.resume();
        FrameLayout game = new FrameLayout(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        Button endGameButton = new Button(this);
        endGameButton.setWidth(200);
        endGameButton.setText("Start Game");
        gameWidgets.addView(endGameButton);
        game.addView(ourView);
        game.addView(gameWidgets);

        setContentView(game);

    }


}
