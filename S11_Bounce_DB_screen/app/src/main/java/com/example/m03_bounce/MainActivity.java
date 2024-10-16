package com.example.m03_bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

// Found tutorial to do put buttons over view here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

public class MainActivity extends AppCompatActivity {
    // bbView is our bouncing ball view
    private BouncingBallView bbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bbView = (BouncingBallView) findViewById(R.id.custView);
    }

    public void onRussButtonClick(View v) {
        SeekBar colorSeek = findViewById(R.id.seekBar_Color);
        SeekBar xSeek = findViewById(R.id.seekBar_X);
        SeekBar ySeek = findViewById(R.id.seekBar_Y);
        SeekBar dxSeek = findViewById(R.id.seekBar_DX);
        SeekBar dySeek = findViewById(R.id.seekBar_DY);
        bbView.newBallButton(colorSeek.getProgress(), xSeek.getProgress(),
                ySeek.getProgress(), dxSeek.getProgress(), dySeek.getProgress());
    }

    public void onCleanButtonClick(View v) {
        bbView.clearBalls();
    }
}