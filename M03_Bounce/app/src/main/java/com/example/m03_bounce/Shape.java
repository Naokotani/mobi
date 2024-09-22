package com.example.m03_bounce;

import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Random;

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

        for (Shape i : shapes) {
            if (!(this instanceof Rectangle)) {
                if (i instanceof Rectangle) {
                    if (minY < i.maxY && x < i.maxX && x > i.minX && y > i.minY) {
                        if(speedMagnitude(speedX) < speedMagnitude(i.speedX)) {
                            speedY = -i.speedY;
                            speedX = -i.speedX;
                        } else {
                            speedX = -speedX;
                        }
                        speedX *= 1.10f;

                        if(((Rectangle) i).player) {
                            return Impact.HIT_PLAYER;
                        } else {
                            return Impact.HIT_ENEMY;
                        }
                    } else if (maxY > i.minY && x < i.maxX && x > i.minX && y < i.maxY) {
                        if(speedMagnitude(speedX) < speedMagnitude(i.speedX)) {
                            speedX = -i.speedX;
                            speedY = -i.speedY;
                        } else {
                            speedX = -speedX;
                        }
                        speedX *= 1.10f;
                        if(((Rectangle) i).player) {
                            return Impact.HIT_PLAYER;
                        } else {
                            return Impact.HIT_ENEMY;
                        }
                    } else if (minX < i.maxX && y < i.maxY && y > i.minY && y > i.minX) {
                        if(speedMagnitude(speedY) < speedMagnitude(i.speedY)) {
                            speedY = -i.speedY;
                            speedX = -i.speedX;

                        } else {
                            speedY = -speedY;
                        }
                        speedY *= 1.10f;

                        if(((Rectangle) i).player) {
                            return Impact.HIT_PLAYER;
                        } else {
                            return Impact.HIT_ENEMY;
                        }
                    } else if (maxX > i.minX && y < i.maxY && y > i.minY && y > i.minX) {
                        if(speedMagnitude(speedY) < speedMagnitude(i.speedY)) {
                            speedY = -i.speedY;
                            speedX = -i.speedX;
                        } else {
                            speedY = -speedY;
                        }
                        speedY *= 1.10f;

                        if(((Rectangle) i).player) {
                            return Impact.HIT_PLAYER;
                        } else {
                            return Impact.HIT_ENEMY;
                        }
                    }
                }
            }
        }
        return Impact.MISS;
    }
}

