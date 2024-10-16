package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Ball {
    private static final float radius = 50;
    public long id;
    float x;
    float y;
    float speedX;
    float speedY;
    int color;
    private final RectF bounds = new RectF();
    private final Paint paint = new Paint();
    private double ax, ay;

    Random r = new Random();

    public Ball(BallModel ballModel) {
        this.color = ballModel.color;
        paint.setColor(color);
        this.id = ballModel.id;
        this.x = ballModel.x;
        this.y = ballModel.y;
        this.speedX = ballModel.dx;
        this.speedY = ballModel.dy;
    }

    public Ball(int color) {
        this.color = color;
        paint.setColor(color);
        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }
    public Ball(int color, float x, float y, float speedX, float speedY) {
        this.color = color;
        paint.setColor(color);

        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void moveWithCollisionDetection(Box box) {
        x += speedX;
        y += speedY;

        if (x + radius > box.xMax) {
            speedX = -speedX;
            x = box.xMax - radius;
        } else if (x - radius < box.xMin) {
            speedX = -speedX;
            x = box.xMin + radius;
        }
        if (y + radius > box.yMax) {
            speedY = -speedY;
            y = box.yMax - radius;
        } else if (y - radius < box.yMin) {
            speedY = -speedY;
            y = box.yMin + radius;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        Log.i("ball", String.format("%f, %f, %f, %f", x, y, speedX, speedY));
        Log.i("ball", paint.toString());
        Log.i("ball", bounds.toString());
        canvas.drawOval(bounds, paint);
    }

}
