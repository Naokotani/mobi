package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
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
    private final List<Ball> balls;
    private final Box box = new Box(Color.BLACK);
    private float previousX;
    private float previousY;

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try(DBClass DBtest = new DBClass(context)){
            this.balls = DBtest.findAll();
        }

        if(balls.isEmpty()) {
            balls.add(new Ball(Color.YELLOW, 23, 24, 4, 5));
        }

        this.setFocusable(true);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        box.draw(canvas);

        for (Ball b : balls) {
            b.draw(canvas);
            b.moveWithCollisionDetection(box);
        }

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
        float scalingFactor = 5.0f / ((box.xMax > box.yMax) ? box.yMax : box.xMax);
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            deltaX = currentX - previousX;
            deltaY = currentY - previousY;
            balls.add(new Ball(Color.BLUE, previousX, previousY, deltaX, deltaY));

            if (balls.size() > 20) {
                balls.clear();
                balls.add(new Ball(Color.RED));
            }
        }
        previousX = currentX;
        previousY = currentY;


        return true;
    }

    Random rand = new Random();

    public void newBallButton() {
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);
        int dx = rand.nextInt(50);
        int dy = rand.nextInt(20);

        balls.add(new Ball(Color.RED, x, y, dx, dy));
    }
}
