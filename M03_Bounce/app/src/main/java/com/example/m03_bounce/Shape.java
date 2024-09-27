package com.example.m03_bounce;

import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
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

    private float magnitude(float speed) {
        if(speed <= 0) {
            return -speed;
        } else {
            return speed;
        }
    }

    public boolean checkYClip(Shape r) {
        if(minY < r.maxY) {
//            y = r.maxY + radius;
            return true;
        }

        if (maxY > r.minY) {
//            y = r.minY + radius;
            return true;
        }
        return false;
    }

    public boolean checkXClip(Shape r) {
        if(minX > r.maxX) {
//            x = r.maxX + radius;
            return true;
        }

        if (maxX < r.minX) {
//            x = minX + radius;
            return true;
        }
        return false;
    }

    private boolean checkClip(Shape r) {
        return minX < r.maxX + radius && maxX > r.minX + radius && minY < r.maxY && maxY > r.minY;
    }

    private class Direction {
        public final String direction;
        public final float distance;

        private Direction(String direction, float distance) {
            this.direction = direction;
            this.distance = distance;
        }

        public float getDistance() {
            return distance;
        }
    }

    private void checkDirection(Shape r) {
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("right",  magnitude(minX - r.maxX)));
        directions.add(new Direction("left", magnitude(maxX - r.minX)));
        directions.add(new Direction("up", magnitude(minY - r.maxY)));
        directions.add(new Direction("down", magnitude(maxY - r.minY)));

        Direction largest  = directions.stream().max(Comparator.comparing(Direction::getDistance)).get();

        switch(largest.direction) {
            case "left":
                Log.i("bounce", "bounce right side");
                x = r.maxX + radius;
                bounceX(r);
                break;
            case "right":
                Log.i("bounce", "bounce left side");
                x = r.minX - radius;
                bounceX(r);
                break;
            case "up":
                Log.i("bounce", "bounce top side");
                y = r.minY - radius;
                bounceY(r);
                break;
            case "down":
                Log.i("bounce", "bounce bottom side");
                y = r.maxY + radius;
                bounceY(r);
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

        if (this instanceof Rectangle) {
            return Impact.MISS;
        }

        impact = Impact.MISS;

        List<Shape> rectangles = shapes.stream().filter(s -> (s instanceof Rectangle)).collect(Collectors.toList());
        rectangles.stream().filter(this::checkClip).findFirst().ifPresent(this::checkDirection);

        return impact;
}

    private void bounceY(Shape s) {
        Rectangle r = (Rectangle) s;
        float magnitutde = (magnitude(r.speedY) > magnitude(speedY))
                ? magnitude(r.speedY) - magnitude(speedY):
                magnitude(speedY) - magnitude(r.speedY);
            speedY = -speedY * 1.10f;
        setImpact(r);
    }

    private void bounceX(Shape s) {
        Rectangle r = (Rectangle) s;
        float magnitutde = (magnitude(r.speedX) > magnitude(speedX))
                ? magnitude(r.speedX) - magnitude(speedX):
                magnitude(speedX) - magnitude(r.speedX);
        speedX = -speedX * 1.10f;
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

