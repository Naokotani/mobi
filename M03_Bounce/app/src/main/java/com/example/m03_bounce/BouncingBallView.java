package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {

    private ArrayList<Shape>shapes = new ArrayList<Shape>(); // list of Balls
    private Shape shape_1;  // use this to reference first ball in arraylist
    private Box box;

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // create the box
        box = new Box(Color.MAGENTA);  // ARGB

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

        for (Shape s : shapes) {
            if(s instanceof  Rectangle) {
               Rectangle r = (Rectangle) s;
               r.draw(canvas);
            } else {
                Ball b = (Ball) s;
                b.draw(canvas);
            }

            s.moveWithCollisionDetection(box);  // Update the position of the ball
        }


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
        float scalingFactor = 5.0f / ((box.xMax > box.yMax) ? box.yMax : box.xMax);
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
                totalSpeed = deltaY;
            } else {
                totalSpeed = deltaY - (deltaY * 2);
            }

            Log.w("BouncingBallLog", " Xspeed=" + shape_1.speedX + " Yspeed=" + shape_1.speedY);
            Log.w("BouncingBallLog", "x,y= " + previousX + " ," + previousY + "  Xdiff=" + deltaX + " Ydiff=" + deltaY);

            if(totalSpeed > 50) {
                shapes.add(new Ball(new RandomColor().getColor(), previousX, previousY, deltaX, deltaY));  // add ball at every touch event
            } else {
                shapes.add(new Rectangle(new RandomColor().getColor(), previousX, previousY, deltaX, deltaY));  // add ball at every touch event
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
    // called when button is pressed
    public void RussButtonPressed() {
        Log.d("BouncingBallView  BUTTON", "User tapped the  button ... VIEW");

        //get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();

        // make random x,y, velocity
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);
        int dx = rand.nextInt(50);
        int dy = rand.nextInt(20);

        shapes.add(new Ball(Color.RED, x, y, dx, dy));  // add ball at every touch event
    }
}
