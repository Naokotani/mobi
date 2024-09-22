package com.example.m03_bounce;

import java.lang.Math;
import android.graphics.Color;

public class RandomColor {
    private int color;

    public RandomColor() {
        int color = (int) Math.round(Math.random() * 9);
        switch(color) {
            case 0:
                this.color = Color.BLUE;
                break;
            case 1:
                this.color = Color.CYAN;
                break;
            case 2:
                this.color = Color.DKGRAY;
                break;
            case 3:
                this.color = Color.GRAY;
                break;
            case 4:
                this.color = Color.GREEN;
                break;
            case 5:
                this.color = Color.MAGENTA;
                break;
            case 6:
                this.color = Color.RED;
                break;
            case 7:
                this.color = Color.TRANSPARENT;
                break;
            case 8:
                this.color = Color.WHITE;
                break;
            case 9:
                this.color = Color.YELLOW;
                break;
            default:
                this.color = Color.BLACK;
        }
    }

    public int getColor() {
        return color;
    }
}