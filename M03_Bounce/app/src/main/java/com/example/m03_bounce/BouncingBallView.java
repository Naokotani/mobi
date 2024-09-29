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
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {
    private final ArrayList<Collider>colliders = new ArrayList<>(); // list of Balls
    private final Box box;
    private int player_score;
    private int enemy_score;
    private final Paint scorePaint;
    private final List<Explosion> explosions;

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

        colliders.add(new Ball(Color.GREEN));
        colliders.add(new Ball(Color.CYAN));

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
        this.explosions = new ArrayList<>();
    }

    private void incrementScore(Impact impact) {
        if(impact == Impact.HIT_PLAYER){
            player_score++;
        } else if(impact == Impact.HIT_ENEMY &&
                enemy_score >= 0) {
            enemy_score++;
        }
    }

    // Called back to draw the view. Also called after invalidate().
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Log.v("BouncingBallView", "onDraw");

        // Draw the components
        box.draw(canvas);

        List<Collider> explodeable = colliders.stream().filter(Collider::isTooFast).collect(Collectors.toList());

        if(!explodeable.isEmpty()) {
            for(Collider e: explodeable)  {
                explosions.add(new Explosion(e.x, e.y, e.paint.getColor()));
            }
        }

        colliders.removeIf(Collider::isTooFast);

        for (Explosion e :explosions) {
            e.move();
            e.draw(canvas);
        }

        colliders.stream().map(Collider::updateBounds)
                .map(s -> s.moveWithCollisionDetection(box, colliders))
                .forEach(this::incrementScore);

        colliders.forEach(s -> s.draw(canvas));

        // Update the position of the ball
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
                colliders.add(new Ball
                        (new RandomColor().getColor( ),
                                previousX, previousY,
                                deltaX / 50,
                                deltaY / 50));
            } else {
                colliders.add(new Square
                        (new RandomColor().getColor(),
                                previousX, previousY,
                                deltaX / 75, deltaY / 75));
            }

            // A way to clear list when too many balls
            if (colliders.size() > 20) {
                // leave first ball, remove the rest
                Log.v("BouncingBallLog", "too many balls, clear back to 1");
                colliders.clear();
                colliders.add(new Ball(new RandomColor().getColor()));
            }
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;


        return true;
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

        colliders.addAll(Arrays.asList(recs));  // add ball at every touch event
    }

    public void reset() {
        player_score = 0;
        enemy_score = 0;
        colliders.clear();
    }
}
