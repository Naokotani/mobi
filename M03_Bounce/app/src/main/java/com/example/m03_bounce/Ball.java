package com.example.m03_bounce;

import android.graphics.Canvas;

public class Ball extends Collider {
    private static final int radius = 50;

    public Ball(int color, float x, float y, float speedX, float speedY) {
        super(color, x, y, speedX, speedY);
    }

    public Ball(int color) {
        super(color);
    }

    @Override
    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawOval(bounds, paint);
    }
}
