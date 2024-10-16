package com.example.m03_bounce;
import android.graphics.Color;

public class SliderColor {
    public static int getColor(int colorSlider) {
        int color;
        switch(colorSlider) {
            case 1:
                color = Color.CYAN;
                break;
            case 2:
                color = Color.DKGRAY;
                break;
            case 3:
                color = Color.GRAY;
                break;
            case 4:
                color = Color.GREEN;
                break;
            case 5:
                color = Color.MAGENTA;
                break;
            case 6:
                color = Color.RED;
                break;
            case 7:
                color = Color.WHITE;
                break;
            case 8:
                color = Color.YELLOW;
                break;
            case 9:
            case 10:
                color = Color.BLUE;
                break;
            default:
                color = Color.BLACK;
        }
        return color;
    }

}
