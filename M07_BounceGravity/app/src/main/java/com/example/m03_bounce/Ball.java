package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Ball {
    double radius = 50;
    double x;
    double y;
    private double maxX;
    private double minX;
    private double maxY;
    private double minY;
    double speedX;
    double speedY;
    double speed_resistance = 0.99f;
    double acc_resistance = 0.99f;
    private RectF bounds;
    private Paint paint;

    private double ax, ay, az = 0;

    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    Random r = new Random();

    public Ball(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    public Ball(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    private boolean checkClip(Rectangle r) {
        return minX < r.getMaxX() && maxX > r.getMinX() && minY < r.getMinY() && maxY > r.getMinY();
    }

    private static class Direction {
        public final String direction;
        public final double distance;

        private Direction(String direction, double distance) {
            this.direction = direction;
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }
    }

    private double magnitude(double speed) {
        if(speed <= 0) {
            return -speed;
        } else {
            return speed;
        }
    }

    private void bounce(Rectangle r) {
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("right", magnitude(x - r.getMaxX())));
        directions.add(new Direction("left", magnitude(x - r.getMinX())));
        directions.add(new Direction("up", magnitude(y - r.getMaxY())));
        directions.add(new Direction("down", magnitude(y - r.getMinY())));

        Direction smallest  = directions.stream()
                .min(Comparator.comparing(Direction::getDistance)).orElse(null);

        switch(Objects.requireNonNull(smallest).direction) {
            case "left":
                x = r.getMinX() - 60;
                speedX += 5;
                bounceX();
                break;
            case "right":
                x = r.getMaxX() + 60;
                speedX -= 5;
                bounceX();
                break;
            case "up":
                y = r.getMaxY() + radius + 1;
                bounceY();
                break;
            case "down":
                y = r.getMinY() - radius - 1;
                bounceY();
        }
    }

    private void bounceY() {
        speedY = -speedY;
    }

    private void bounceX() {
        speedX = -speedX;
    }

    public void moveWithCollisionDetection(Box box, List<Rectangle> rectangles) {
        x += speedX;
        y += speedY;
        maxX = x + radius;
        minX = x - radius;
        maxY = y + radius;
        minY = y - radius;
        speedX = (speedX) * speed_resistance + ax * acc_resistance;
        speedY = (speedY) * speed_resistance + ay * acc_resistance;

        rectangles.stream().filter(this::checkClip)
                .findAny().ifPresent(this::bounce);

        if (maxX > box.xMax) {
            speedX = -speedX;
            x = box.xMax - radius;
        } else if (minX < box.xMin) {
            speedX = -speedX;
            x = box.xMin + radius;
        }
        if (maxY > box.yMax) {
            speedY = -speedY;
            y = box.yMax - radius;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set((float) (minX),
                (float) (maxY),
                (float) (maxX),
                (float) (minY));
        canvas.drawOval(bounds, paint);
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
