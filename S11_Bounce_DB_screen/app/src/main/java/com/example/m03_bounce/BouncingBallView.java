package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {
    private final BallsList balls;
    private final Box box = new Box(Color.BLACK);
    private float previousX;
    private float previousY;

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.balls = new BallsList(context);

        if(balls.getBalls().isEmpty()) {
            balls.addBall(new Ball(Color.YELLOW, 23, 24, 4, 5));
        }

        this.setFocusable(true);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("ball", balls.getBalls().size()+"");
        box.draw(canvas);
        balls.getBalls().forEach(b -> {b.moveWithCollisionDetection(box); b.draw(canvas);});
        this.invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        box.set(0, 0, w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            deltaX = currentX - previousX;
            deltaY = currentY - previousY;
            balls.addBall(new Ball(Color.BLUE, previousX, previousY, deltaX, deltaY));

            if (balls.getBalls().size() > 20) {
                balls.clear();
                balls.addBall(new Ball(Color.RED));
            }
        }
        previousX = currentX;
        previousY = currentY;

        return true;
    }

    Random rand = new Random();

    public void newBallButton(int color, float x, float y, float dx, float dy) {
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();
        balls.addBall(new Ball(SliderColor.getColor(color), viewWidth/x, viewHeight/y, dx, dy));
    }

    public void clearBalls() {
        balls.clear();
    }
}