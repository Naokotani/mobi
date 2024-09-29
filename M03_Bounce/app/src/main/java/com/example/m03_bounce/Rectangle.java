package com.example.m03_bounce;
import android.graphics.Canvas;
import java.util.ArrayList;

public class Rectangle extends Collider {
    private static final int height = 50;
    private static final int width = 100;
    public boolean player;

    public Rectangle(int color, float x, float y, float speedX, float speedY, boolean player) {
        super(color, x, y, speedX, speedY);
        this.player = player;
    }

    public void draw(Canvas canvas) {
        super.
        bounds.set(x - width, y - height, x + width, y + height);
        canvas.drawOval(bounds, paint);
    }

    @Override
    public Impact moveWithCollisionDetection(Box box, ArrayList<Collider> colliders) {
        move();
        wallBounce(box);

        return Impact.MISS;
    }

    @Override
    public Rectangle updateBounds() {
        maxX = (int) (x + width);
        minX = (int) (x - width);
        maxY = (int) (y + height);
        minY = (int) (y - height);
        return this;
    }
}
