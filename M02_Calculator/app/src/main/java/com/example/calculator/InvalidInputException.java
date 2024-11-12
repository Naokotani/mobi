package com.example.calculator;

import android.util.Log;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super("Couldn't parse input string: "+ message);
        Log.e("input", super.getMessage(), this);
    }
}
