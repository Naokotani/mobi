package com.example.m03_bounce;

import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public abstract class Shape {
    protected static final float radius = 50;      // Ball's radius
    protected float x;                // Ball's center (x,y)
    protected float y;
    protected float speedX;           // Ball's speed
    protected float speedY;
    protected RectF bounds;   // Needed for Canvas.drawOval
    protected Paint paint;    // The paint style, color used for drawing
    protected int maxX;
    protected int minX;
    protected int maxY;
    protected int minY;
    private Impact impact;
    private static final float speedMultiplier = 1.05f;

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

    public boolean isTooFast() {
        return magnitude(speedX) + magnitude(speedY) > 100;
    }

    private float magnitude(float speed) {
        if(speed <= 0) {
            return -speed;
        } else {
            return speed;
        }
    }

    private boolean checkClip(Rectangle r) {
        return minX < r.maxX + radius && maxX > r.minX + radius && minY < r.maxY && maxY > r.minY;
    }

    private static class Direction {
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

    private void checkDirection(Rectangle r) {
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("right", magnitude(minX - r.maxX)));
        directions.add(new Direction("left", magnitude(maxX - r.minX)));
        directions.add(new Direction("up", magnitude(minY - r.maxY)));
        directions.add(new Direction("down", magnitude(maxY - r.minY)));

        Direction largest  = directions.stream().max(Comparator.comparing(Direction::getDistance)).get();

        switch(largest.direction) {
            case "left":
                Log.i("bounce", "bounce right side");
                x = r.maxX + radius + 20;
                bounceX(r);
                break;
            case "right":
                Log.i("bounce", "bounce left side");
                x = r.minX - radius + 20;
                bounceX(r);
                break;
            case "up":
                Log.i("bounce", "bounce top side");
                y = r.minY - radius + 20;
                bounceY(r);
                break;
            case "down":
                Log.i("bounce", "bounce bottom side");
                y = r.maxY + radius + 20;
                bounceY(r);
        }
    }

    protected void wallBounce(Box box) {
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
    }

    protected void move() {
        x += speedX;
        y += speedY;
    }

    public Impact moveWithCollisionDetection(Box box, ArrayList<Shape> shapes) {
        move();
        wallBounce(box);

        impact = Impact.MISS;

        List<Rectangle> rectangles = shapes.stream()
                .filter(Rectangle.class::isInstance)
                .map(Rectangle.class::cast)
                .collect(Collectors.toList());

        rectangles.stream().filter(this::checkClip)
                .findFirst().ifPresent(this::checkDirection);

        return impact;
}

    private void bounceY(Rectangle r) {
        speedY = (magnitude(r.speedY) > magnitude(speedY)) ? r.speedY: -speedY;
        speedY *= speedMultiplier;
        setImpact(r);
    }

    private void bounceX(Rectangle r) {
        speedX = (magnitude(r.speedX) > magnitude(speedX)) ? r.speedX: -speedX;
        speedX *= speedMultiplier;
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

