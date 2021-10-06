package com.example.uppgift1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView galaxyImage;
    TextView posText;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        galaxyImage = (ImageView) findViewById(R.id.galaxyImg);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override public void onAccuracyChanged(Sensor sensor, int i) {}
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    getAccelerometer(sensorEvent);
                }
            }
        };
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

    }

    private void getAccelerometer(SensorEvent sensorEvent) {
        posText = (TextView) findViewById(R.id.posString);
        float[] values = sensorEvent.values;
        float X_Axis = values[0];
        float Y_Axis = values[1];
        float Z_Axis = values[2];
        double angle = Math.atan2(X_Axis, Y_Axis) / (Math.PI / 180);
        posText.setText("Angle: " + String.valueOf((int) angle) + "°");
        galaxyImage.setRotation((float) angle);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if(width<X_Axis*-100*2||-width>X_Axis*-100*2){
            if(width<X_Axis*-100*2){
                galaxyImage.setX(width/2);
            }else if (-width>X_Axis*-100*2) {
                galaxyImage.setX(-width/2);
            }
        }else{
        galaxyImage.setX(X_Axis*-100);
        }
        if(height<=Y_Axis*-100*4||-height>Y_Axis*-100*2){
            if(height<=Y_Axis*-100*4){
                galaxyImage.setY(-height/4);
            }else if(-height>Y_Axis*-100*2){
                galaxyImage.setY(height/2);
            }
        }else{
        galaxyImage.setY(Y_Axis*100);
        }

    }

}