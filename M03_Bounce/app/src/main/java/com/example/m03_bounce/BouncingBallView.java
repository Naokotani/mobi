package com.example.m03_bounce;

import android.annotation.SuppressLint;
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
    private final ArrayList<Shape>shapes = new ArrayList<>(); // list of Balls
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

        shapes.add(new Ball(Color.GREEN));
        shapes.add(new Ball(Color.CYAN));

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
        shapes.removeIf(Shape::isTooFast);

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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY, totalSpeed;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {// Modify rotational angles according to movement
            deltaX = currentX - previousX;
            deltaY = currentY - previousY;

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
                shapes.add(new Ball(new RandomColor().getColor()));
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
        int dx = rand.nextInt(7) + 3;
        int dy = rand.nextInt(3) + 2;

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
