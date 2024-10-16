package com.example.m03_bounce;

import android.content.Context;

import java.util.List;

public class BallsList {
    private final List<Ball> balls;
    private final Context context;

    public BallsList(Context context) {
        this.context = context;
        this.balls = loadBalls();
    }

    public void addBall(Ball ball) {
        try (DBClass DBtest = new DBClass(context)) {
            ball.id = DBtest.save(new BallModel(ball));
        }
        balls.add(ball);
    }

    public void removeBall(Ball ball) {
        balls.removeIf(b -> b.id == ball.id && deleteBall(b.id));
    }

    private boolean deleteBall(Long id) {
        try (DBClass DBtest = new DBClass(context)) {
            DBtest.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Ball not removed from database." + e);
        }
        return true;
    }

    public void clear() {
        balls.forEach(b -> deleteBall(b.id));
        balls.clear();
    }

    public List<Ball> getBalls() {
        return balls;
    }

    private List<Ball> loadBalls() {
        try (DBClass DBtest = new DBClass(context)) {
            return DBtest.findAll();
        }
    }
}
