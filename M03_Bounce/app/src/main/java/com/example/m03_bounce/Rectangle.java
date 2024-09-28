package com.example.m03_bounce;
import android.graphics.Canvas;
import java.util.ArrayList;

public class Rectangle extends Shape {
    private final float height;
    private final float width;
    public boolean player;

    public Rectangle(int color, float x, float y, float speedX, float speedY, boolean player) {
        super(color, x, y, speedX, speedY);
        this.height = radius;
        this.width = radius * 2;
        this.player = player;
    }

    public void draw(Canvas canvas) {
        super.
        bounds.set(x - width, y - height, x + width, y + height);
        canvas.drawRect(bounds, paint);
    }

    @Override
    public Impact moveWithCollisionDetection(Box box, ArrayList<Shape> shapes) {
        move();
        wallBounce(box);

        return Impact.MISS;
    }

    public void updateBounds() {
        maxX = (int) (x + width);
        minX = (int) (x - width);
        maxY = (int) (y + height);
        minY = (int) (y - height);
    }


}
