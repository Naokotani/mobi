package com.example.m03_bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

// Found tutorial to do put buttons over view here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor my_Sensor;
    private BouncingBallView bouncingBallView;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bouncingBallView = findViewById(R.id.custView);
        setupSensors();
    }

    private void setupSensors() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            my_Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Log.v("SENSORS", "my_Sensor=" + my_Sensor.toString() );
        }
        else{
            Log.v("SENSORS", "NO SENSOR TYPE?" );
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (bouncingBallView!=null) {
            Log.v("SENSORS", "onResume bouncingBallView=" + bouncingBallView.toString());
            if (my_Sensor !=null) {
                Log.v("SENSORS", "onResume my_Sensor=" + my_Sensor.toString());
                mSensorManager.registerListener((SensorEventListener) bouncingBallView, my_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            Log.v("SENSORS", "onResume bouncingBallView=null");
        }
        Log.v("SENSORS", "onResume ACCELLEROMETER" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) bouncingBallView);
        Log.v("SENSORS", "onPause ACCELLEROMETER" );
    }


    public void onRussButtonClick(View v) {
        Log.d("MainActivity  BUTTON", "User tapped the  button ... MAIN");
        bouncingBallView.NotRussButtonPressed();
    }
}