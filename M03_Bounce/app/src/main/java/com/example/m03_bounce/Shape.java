package com.example.m03_bounce;

import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Shape {

    float radius = 50;      // Ball's radius
    float x;                // Ball's center (x,y)
    float y;
    float speedX;           // Ball's speed
    float speedY;
    protected RectF bounds;   // Needed for Canvas.drawOval
    protected Paint paint;    // The paint style, color used for drawing
    int maxX;
    int minX;
    int maxY;
    int minY;
    Impact impact;

    // Add accelerometer
    // Add ... implements SensorEventListener
    private double ax, ay = 0; // acceration from different axis

    public void setAcc(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    Random r = new Random();  // seed random number generator

    // Constructor
    public Shape(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // random position and speed
        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    // Constructor
    public Shape(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // use parameter position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void updateBounds() {
        maxX = (int) (x + radius);
        minX = (int) (x - radius);
        maxY = (int) (y + radius);
        minY = (int) (y - radius);
    }

    private float speedMagnitude(float speed) {
        if(speed <= 0) {
            return -speed;
        } else {
            return speed;
        }
    }

    public boolean checkTopClip(Shape r) {
        return minY < r.maxY && x < r.maxX && x > r.minX;
    }

//        return minY < r.maxY && x < r.maxX && x > r.minX && y > r.minY;

    public boolean checkBotClip(Shape r) {
        return maxY < r.minY && x < r.maxX && x > r.minX;
    }

//        return maxY < r.minY && x < r.maxX && x > r.minX && y > r.minY;

    public boolean checkLeftClip(Shape r) {
        return minY < r.maxY && x < r.maxX && x > r.minX;
    }

//        return minY < r.maxY && x < r.maxX && x > r.minX && y > r.minY;

    public boolean checkRightClip(Shape r) {
        return maxX > r.minX && y < r.maxY && y > r.minY;
    }
//        return maxX > r.minX && y < r.maxY && y > r.minY && y > r.minX;
    public Impact moveWithCollisionDetection(Box box, ArrayList<Shape> shapes) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Detect collision and react
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

        if (this instanceof Rectangle) {
            return Impact.MISS;
        }

        impact = Impact.MISS;

        List<Shape> rectangles = shapes.stream().filter(s -> (s instanceof Rectangle)).collect(Collectors.toList());
        rectangles.stream().filter(this::checkTopClip).findFirst().or(() ->
                rectangles.stream().filter(this::checkBotClip).findFirst()
        ).stream().findFirst().ifPresent(this::bounceVertical);

        rectangles.stream().filter(this::checkLeftClip).findFirst().or(() ->
                rectangles.stream().filter(this::checkRightClip).findFirst()
        ).stream().findFirst().ifPresent(this::bounceHorizontal);

        return impact;
}

    private void bounceVertical(Shape s) {
        Rectangle r = (Rectangle) s;

        if(speedMagnitude(speedY) < speedMagnitude(r.speedY)) {
            if(r.speedY >= 0) {
                y = r.minY + radius + r.speedY;
            } else {
                y = r.maxY + radius + (-r.speedY);
            }
            speedY = r.speedY;
            speedX *= 1.10f;
        } else {
            speedY = -speedY;
            speedX *= 1.05f;
        }
        setImpact(r);

    }

    private void bounceHorizontal(Shape s) {
        Rectangle r = (Rectangle) s;

        if(speedMagnitude(speedX) < speedMagnitude(r.speedX)) {
            if(r.speedX >= 0) {
                y = r.maxY + radius + (-r.speedX);
            } else {
                y = r.minY + radius + r.speedX;
            }
            speedY = r.speedY;
            speedY *= 1.10f;
        } else {
            speedX = -speedX;
            speedX *= 1.05f;
        }
        setImpact(r);
    }

    private void setImpact(Rectangle r) {
        if(r.player) {
            impact = Impact.HIT_PLAYER;
        } else {
            impact = Impact.HIT_ENEMY;
        }
    }
}

