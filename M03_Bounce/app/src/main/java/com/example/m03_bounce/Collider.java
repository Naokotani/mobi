package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Collider extends Shape {
    protected int maxX;
    protected int minX;
    protected int maxY;
    protected int minY;
    private Impact impact;
    Random r = new Random();  // seed random number generator

    public Collider(int color) {
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
    public Collider(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // use parameter position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public Collider updateBounds() {
        maxX = (int) (x + radius);
        minX = (int) (x - radius);
        maxY = (int) (y + radius);
        minY = (int) (y - radius);
        return this;
    }
    abstract public void draw(Canvas canvas);

    public boolean isTooFast() {
        return magnitude(speedX) + magnitude(speedY) > 50;
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

        List<Direction> multiDirection = directions.stream()
                .filter(d -> d.distance < 5)
                .collect(Collectors.toList());

        Log.i("corner", String.valueOf(multiDirection.size()));

        Direction smallest  = directions.stream()
                .min(Comparator.comparing(Direction::getDistance)).orElse(null);


        switch(Objects.requireNonNull(smallest).direction) {
            case "left":
                Log.i("bounce", "bounce right side");
                x = r.minX - radius + 20;
                bounceX(r);
                break;
            case "right":
                Log.i("bounce", "bounce left side");
                x = r.maxX + radius + 20;
                bounceX(r);
                break;
            case "up":
                Log.i("bounce", "bounce top side");
                y = r.maxY + radius + 20;
                bounceY(r);
                break;
            case "down":
                Log.i("bounce", "bounce bottom side");
                y = r.minY - radius + 20;
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

    public Impact moveWithCollisionDetection(Box box, ArrayList<Collider> colliders) {
        move();
        wallBounce(box);

        impact = Impact.MISS;

        List<Rectangle> rectangles = colliders.stream()
                .filter(Rectangle.class::isInstance)
                .map(Rectangle.class::cast)
                .collect(Collectors.toList());

        rectangles.stream().filter(this::checkClip)
                .findAny().ifPresent(this::checkDirection);

        return impact;
    }

    private void bounceY(Rectangle r) {
        speedY = (magnitude(r.speedY) > magnitude(speedY)) ? r.speedY: -speedY;
        speedY *= 1.10f;
        setImpact(r);
    }

    private void bounceX(Rectangle r) {
        speedX = (magnitude(r.speedX) > magnitude(speedX)) ? r.speedX: -speedX;
        speedX *= 1.10f;
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
