package com.example.calculator;

public class Divide extends Operation {
    private final double result;
    private final static char operator = '+';
    private final String history;

    public Divide(double previous_num, double new_num) {
        super(previous_num, new_num);
        this.result = previous_num / new_num;
        if(previous_num == 0) {
            this.history = " / " + new_num;
        } else {
            this.history = previous_num  + " / " + new_num + " = " + result;
        }
    }

    @Override
    public double getResult() {
        return result;
    }

    @Override
    public String getHistory() {
        return history;
    }

    @Override
    public char getOperator() {
        return operator;
    }
}
