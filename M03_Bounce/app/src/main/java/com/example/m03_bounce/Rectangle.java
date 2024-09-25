package com.example.m03_bounce;

import android.graphics.Canvas;


/**
 * Created by Russ on 08/04/2014.
 */
public class Rectangle extends Shape implements Drawable {
    private final float height;
    private final float width;
    public boolean player;

    public Rectangle(int color, float x, float y, float speedX, float speedY, boolean player) {
        super(color, x, y, speedX, speedY);
        this.height = radius;
        this.width = radius * 2;
        this.player = player;
    }

    public void draw(Canvas canvas) {
        super.
        bounds.set(x - width, y - height, x + width, y + height);
        canvas.drawRect(bounds, paint);
    }

    public void updateBounds() {
        maxX = (int) (x + width);
        minX = (int) (x - width);
        maxY = (int) (y + height);
        minY = (int) (y - height);
    }


}
