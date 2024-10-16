package com.example.m03_bounce;

public class BallModel {
    long id;
    float x;
    float y;
    float dx;
    float dy;
    int color;

    public BallModel(Ball ball) {
        this.color = ball.color;
        this.x = ball.x;
        this.y = ball.y;
        this.dx = ball.speedX;
        this.dy = ball.speedY;
    }

    public BallModel(long id, Float x, Float y, Float dx, Float dy, int color) {
        this.color = color;
        this.setId(id);
        this.setModelPosVel(x, y, dx, dy);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModelPosVel(Float x, Float y, Float dx, Float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
}
