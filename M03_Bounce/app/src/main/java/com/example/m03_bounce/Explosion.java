package com.example.m03_bounce;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Explosion {
    List<Particle> particles;

    public Explosion(float x, float y, int color) {
        this.particles = new ArrayList<>();
        int particleQuantity = new Random().nextInt(15 ) + 30;

        for(int i = 0; i < particleQuantity; i++) {
            this.particles.add(new Particle(x, y, color));
        }
    }

    public void draw(Canvas canvas) {
        for(Particle p: particles) {
            p.draw(canvas);
        }
    }

    public void move() {
        for(Particle p: particles) {
            p.move();
        }
    }

}
