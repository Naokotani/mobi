package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Particle extends Shape {
    private static final int radius = 10;
    private float x;
    private float y;
    private float speedY = 0;
    private float speedX;
    protected RectF bounds;
    protected Paint paint;


    public Particle(float x, float y, int color) {
        super();
        float deltaX = x + new Random().nextInt(200) - 400;
        this.x = deltaX;
        this.y = y + new Random().nextInt(200) - 400;

        this.speedX = deltaX / 10;

        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawRect(bounds, paint);
    }

    public void move() {
        speedX /= 1.2F;

        speedY += 1;

        y += speedY;
        x += speedX;
    }
}