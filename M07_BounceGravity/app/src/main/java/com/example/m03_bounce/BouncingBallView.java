package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BouncingBallView extends View implements SensorEventListener {
    private List<Ball> balls = new ArrayList<>();
    private final List<Rectangle> rectangles = new ArrayList<>();
    private int level = 1;
    private final Box box;
    private Goal goal;
    double ax = 0;
    double ay = 0;
    double az = 0;


    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        box = new Box(Color.BLACK);

        for(int i =0; i < level; i++) {
            rectangles.add(new Rectangle(Color.CYAN));
        }

        goal = new Goal();
        this.setFocusable(true);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        box.draw(canvas);
        goal.draw(canvas);
        for (Ball b : balls) {
            b.draw(canvas);
            b.moveWithCollisionDetection(box, rectangles);
            if(goal.score(b)) {
                Log.i("score", "Goal!");
            } else {
                Log.i("score", "Miss!");
            }
        }

        balls.stream().filter(b -> goal.score(b)).forEach(b-> newLevel());
        Log.i("balls", Integer.toString(balls.size()));

        balls = balls.stream()
                .filter(b -> b.getMaxY() < this.getMeasuredHeight())
                .collect(Collectors.toList());

        for(Rectangle r : rectangles) {
            if(!r.isPosSet()) {r.setRandomPos(canvas);}
            r.draw(canvas);
        }

        this.invalidate();
    }

    private void newLevel() {
        level += 1;
        rectangles.clear();

        for(int i =0; i < level; i++) {
            rectangles.add(new Rectangle(Color.CYAN));
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        box.set(0, 0, w, h);
        goal.setLocation(w, h);
    }

    Random rand = new Random();
    public void NotRussButtonPressed() {
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight/3);
        int dx = rand.nextInt(10) - 5;
        int dy = rand.nextInt(6) - 3;

        balls.add(new Ball(Color.RED, x, y, dx, dy));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax = -event.values[0];
            ay = event.values[1];
            az = event.values[2];

            for (Ball b : balls) {
                b.setAcc(ax/50, ay/50, az/50);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
