package com.example.m03_bounce;

import android.graphics.Canvas;


/**
 * Created by Russ on 08/04/2014.
 */
public class Ball extends Shape {

    public Ball(int color, float x, float y, float speedX, float speedY) {
        super(color, x, y, speedX, speedY);
    }

    public Ball(int color) {
        super(color);
    }

    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawOval(bounds, paint);
    }

}
