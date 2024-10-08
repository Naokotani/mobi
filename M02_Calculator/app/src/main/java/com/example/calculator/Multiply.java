package com.example.calculator;

public class Multiply extends Operation {
    public final double result;
    public final static char operator = '*';
    public final String history;

    public Multiply(double previous_num, double new_num) {
        super(previous_num, new_num);
        this.result = previous_num * new_num;
        if(previous_num == 0) {
            this.history = " X " + new_num;
        } else {
            this.history = previous_num  + " X " + new_num;
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
