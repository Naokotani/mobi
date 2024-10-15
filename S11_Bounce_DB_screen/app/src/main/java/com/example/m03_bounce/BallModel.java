package com.example.m03_bounce;

public class BallModel {
    private long id;
    float x;
    float y;
    float dx;
    float dy;

    public BallModel(long id, Float x, Float y, Float dx, Float dy) {
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

    public float getModelX() {
        return this.x;
    }

    public float getModelY() {
        return this.y;
    }

    public float getModelDX() {
        return this.dx;
    }

    public float getModelDY() {
        return this.dx;
    }
}
