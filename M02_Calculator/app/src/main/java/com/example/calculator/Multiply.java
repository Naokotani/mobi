package com.example.calculator;

public class Multiply extends Operation {
    public final double result;
    public final static char operator = '*';

    public Multiply(double previous_num, double new_num) {
        super(previous_num, new_num);
        this.result = previous_num * new_num;
    }

    @Override
    public double getResult() {
        return result;
    }

    @Override
    public char getOperator() {
        return operator;
    }
}
