package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Rectangle {
    private final double height = 50;
    private final double width = 150;
    private double x;
    private double maxX;
    private double minX;
    private double y;
    private double maxY;
    private double minY;
    private final RectF bounds;
    private final Paint paint;
    private final Random r = new Random();
    private boolean posSet = false;

    public Rectangle(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
    }

    public Rectangle(double x, double y, int color) {
        this.x = x;
        this.y = y;
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
        setMaxMin();
    }

    private void setMaxMin() {
        this.maxX = x + width;
        this.maxY = y + height;
        this.minX = x - width;
        this.minY = y - height;
    }

    public void setRandomPos(Canvas canvas) {
        posSet = true;
        x = r.nextInt(canvas.getWidth() - (int) width);
        y = (double) canvas.getHeight() /2 + r.nextInt((canvas.getHeight() - 750)/2);
        setMaxMin();
    }

    public void draw(Canvas canvas) {
        bounds.set((float) minX,
                (float) maxY,
                (float) maxX,
                (float) minY);
        canvas.drawRect(bounds, paint);
    }

    public boolean isPosSet() {
        return posSet;
    }

    public double getHeight() {
        return height;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinY() {
        return minY;
    }
}

