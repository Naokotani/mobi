package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;


/**
 * Created by Russ on 08/04/2014.
 */
public class Rectangle extends Shape implements Draw{
    public Rectangle(int color, float x, float y, float speedX, float speedY) {
        super(color, x, y, speedX, speedY);
    }

    public Rectangle(int color) {
        super(color);
    }

    public void draw(Canvas canvas) {
        super.
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawRect(bounds, paint);
    }
}
