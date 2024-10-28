package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Goal {
    private final double height = 50;
    private final double width = 150;
    private double x;
    private double maxX;
    private double minX;
    private double y;
    private double maxY;
    private double minY;
    private final RectF bounds = new RectF();

    public Goal() {
        this.minX = x - width;
        this.maxX = x + width;
        this.maxY = y + height;
        this.minY = y - height;
    }

    public void setLocation(int screenWidth, int screenHeight) {
        this.y = screenHeight - height;
        this.x = (double)screenWidth/2;
        this.minX = x - width;
        this.maxX = x + width;
        this.maxY = y + height;
        this.minY = y - height;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        bounds.set((float) (x - width),
                (float) (y - height),
                (float) (x + width),
                (float) (y + height));
        canvas.drawRect(bounds, paint);
    }

    public boolean score(Ball ball) {
        return ball.getMinX() > minX && ball.getMaxX() < maxX && ball.getMaxY() > maxY;
    }
}
