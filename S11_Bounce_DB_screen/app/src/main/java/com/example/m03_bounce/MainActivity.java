package com.example.m03_bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

// Found tutorial to do put buttons over view here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

public class MainActivity extends AppCompatActivity {


    // bbView is our bouncing ball view
    private BouncingBallView bbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the view object so we can reference it later
        bbView = (BouncingBallView) findViewById(R.id.custView);
    }

    // button action

    public void onRussButtonClick(View v) {

        Log.d("MainActivity  BUTTON", "User tapped the  button ... MAIN");

        // get spinner values
        SeekBar seekbar_color = (SeekBar) findViewById(R.id.seekBar_Color);
        SeekBar seekbar_x = (SeekBar) findViewById(R.id.seekBar_X);
        SeekBar seekbar_y = (SeekBar) findViewById(R.id.seekBar_Y);
        SeekBar seekbar_dx = (SeekBar) findViewById(R.id.seekBar_DX);
        SeekBar seekbar_dy = (SeekBar) findViewById(R.id.seekBar_DY);

        int string_color = seekbar_color.getProgress();
        int string_x = seekbar_x.getProgress();
        int string_y = seekbar_y.getProgress();
        int string_dx = seekbar_dx.getProgress();
        int string_dy = seekbar_dy.getProgress();

        Log.d("MainActivity  BUTTON", "Color="+string_color+" X="+string_x+" Y="+string_y+" DX="+string_dx+" DY="+string_dy);

        // let the view do something
        bbView.RussButtonPressed();

    }
}