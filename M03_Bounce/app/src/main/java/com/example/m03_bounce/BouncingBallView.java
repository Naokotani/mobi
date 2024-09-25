package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {
    private final ArrayList<Shape>shapes = new ArrayList<Shape>(); // list of Balls
    private Shape shape_1;  // use this to reference first ball in arraylist
    private final Box box;
    private int player_score;
    private int enemy_score;
    private final Paint scorePaint;

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;


    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // create the box
        box = new Box(Color.LTGRAY);  // ARGB

        scorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(75);

        player_score = 0;
        enemy_score = 0;

        shape_1 = new Ball(Color.GREEN);
        shapes.add(new Ball(Color.GREEN));
        shape_1 = shapes.get(0);  // points ball_1 to the first; (zero-ith) element of list
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        //ball_2 = new Ball(Color.CYAN);
        shapes.add(new Ball(Color.CYAN));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        Log.v("BouncingBallView", "onDraw");

        // Draw the components
        box.draw(canvas);
        //canvas.drawARGB(0,25,25,25);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        shapes.removeIf(s -> s.speedX > 100 || s.speedX < -100);
        shapes.removeIf(s -> s.speedY > 100 || s.speedY < -100);

        for (Shape s : shapes) {
            if(s instanceof  Square) {
                s.updateBounds();
               Square r = (Square) s;
               r.draw(canvas);
            } else if(s instanceof Ball) {
                s.updateBounds();
                Ball b = (Ball) s;
                b.draw(canvas);
            } else {
                Rectangle r = (Rectangle) s;
                r.updateBounds();
                r.draw(canvas);
            }
            Impact impact = s.moveWithCollisionDetection(box, shapes);

            if(impact == Impact.HIT_PLAYER){
                player_score++;
            } else if(impact == Impact.HIT_ENEMY &&
            enemy_score >= 0) {
                enemy_score++;
            }
                // Update the position of the ball
        }
        canvas.drawText("Player Score: " + player_score, 20, 200, scorePaint);
        canvas.drawText("Enemy Score: " + enemy_score, 20, 100, scorePaint);

        // Delay on UI thread causes big problems!
        // Simulates doing busy work or waits on UI (DB connections, Network I/O, ....)
        //  I/Choreographer? Skipped 64 frames!  The application may be doing too much work on its main thread.
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//        }

        // Check what happens if you draw the box last
        //box.draw(canvas);

        // Check what happens if you don't call invalidate (redraw only when stopped-started)
        // Force a re-draw
        this.invalidate();
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }

    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY, totalSpeed;
        float scalingFactor = 5.0f / (Math.min(box.xMax, box.yMax));
        if (event.getAction() == MotionEvent.ACTION_MOVE) {// Modify rotational angles according to movement
            deltaX = currentX - previousX;
            deltaY = currentY - previousY;
            shape_1.speedX += deltaX * scalingFactor;
            shape_1.speedY += deltaY * scalingFactor;

            if(deltaX >= 0) {
                totalSpeed = deltaX;
            } else {
                totalSpeed = deltaX - (deltaX * 2);
            }

            if(deltaY >= 0) {
                totalSpeed += deltaY;
            } else {
                totalSpeed += deltaY - (deltaY * 2);
            }

            Log.w("BouncingBallLog", " Xspeed=" + shape_1.speedX + " Yspeed=" + shape_1.speedY);
            Log.w("BouncingBallLog", "x,y= " + previousX + " ," + previousY + "  Xdiff=" + deltaX + " Ydiff=" + deltaY);

            if(totalSpeed > 50) {
                shapes.add(new Ball(new RandomColor().getColor(), previousX, previousY, deltaX / 100, deltaY / 100));  // add ball at every touch event
            } else {
                shapes.add(new Square(new RandomColor().getColor(), previousX, previousY, deltaX / 100, deltaY / 100));  // add ball at every touch eventa
            }

            // A way to clear list when too many balls
            if (shapes.size() > 20) {
                // leave first ball, remove the rest
                Log.v("BouncingBallLog", "too many balls, clear back to 1");
                shapes.clear();
                shapes.add(new Ball(Color.RED));
                shape_1 = shapes.get(0);  // points ball_1 to the first (zero-ith) element of list
            }
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;

        // Try this (remove other invalidate(); )
//        this.invalidate();

        return true;  // Event handled
    }

    Random rand = new Random();
    public void createRectangle() {
        //get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();

        // make random x,y, velocity
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);
//        int dx = rand.nextInt(10) + 5;
//        int dy = rand.nextInt(5) + 5;

        int dx = 3;
        int dy = 3;

        Rectangle[] recs = {
                new Rectangle(Color.RED, x, y, -dx, -dy, true),
                new Rectangle(Color.GRAY, x, y, dx, dy, false)
        };

        shapes.addAll(Arrays.asList(recs));  // add ball at every touch event
    }

    public void reset() {
        player_score = 0;
        enemy_score = 0;
        shapes.clear();
    }
}
