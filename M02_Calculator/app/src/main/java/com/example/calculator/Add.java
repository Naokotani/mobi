package com.example.calculator;

public class Add extends Operation {
    private final double result;
    private final static char operator = '+';

    public Add(double previous_num, double new_num) {
        super(previous_num, new_num);
        this.result = previous_num + new_num;
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
