package com.example.m03_bounce;

import android.graphics.Canvas;


/**
 * Created by Russ on 08/04/2014.
 */
public class Square extends Shape implements Drawable {
    public Square(int color, float x, float y, float speedX, float speedY) {
        super(color, x, y, speedX, speedY);
    }

    public void draw(Canvas canvas) {
        super.
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawRect(bounds, paint);
    }
}
