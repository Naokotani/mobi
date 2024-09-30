package com.example.calculator;

import android.annotation.SuppressLint;
import android.widget.TextView;

public class Input {
    private TextView input;
    private TextView history;

    public Input(TextView input, TextView history) {
        this.input = input;
        this.history = history;
        input.setText("0");
        history.setText("0");
    }

    public String getInput() {
        return !input.getText().toString().isEmpty() ? input.getText().toString() : "0";
    }

    @SuppressLint("SetTextI18n")
    public void setInput(char c) {
        if(input.getText() == "0") {
            input.setText("");
        }
        String oldText = input.getText().toString();
        input.setText(oldText + c);
    }

    @SuppressLint("SetTextI18n")
    public void prependInput(char c) {
        if(input.getText() == "0") {
            input.setText("");
        }
        String oldText = input.getText().toString();
        input.setText(c + oldText);
    }

    public void setInput(String s) {
        input.setText(s);
    }

    public void setHistory(String history) {
        this.history.setText(history);
    }

    public void clear() {
        input.setText("0");
    }

    public void allClear() {
        input.setText("0");
        history.setText("0");
    }
}


