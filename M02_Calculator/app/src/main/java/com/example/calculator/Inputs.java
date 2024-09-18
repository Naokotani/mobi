package com.example.calculator;

import android.widget.EditText;

public class Inputs {
    private final EditText input_one;
    private final EditText input_two;
    private final EditText answer;

    public Inputs(EditText input_one, EditText input_two, EditText answer) {
        this.input_one = input_one;
        this.input_two = input_two;
        answer.setFocusable(false);
        this.answer = answer;
    }

    public double getInput_one() {
        String text = input_one.getText().toString();
        return !text.isEmpty() ? Double.parseDouble(text) : 0.0;
    }

    public double getInput_two() {
        String text = input_two.getText().toString();
        return !text.isEmpty() ? Double.parseDouble(text) : 0.0;
    }

    public EditText getAnswer() {
        return answer;
    }
}


