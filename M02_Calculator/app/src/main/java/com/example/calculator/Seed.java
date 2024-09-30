package com.example.calculator;

public class Seed extends Operation {
    private final char seedOperator;

    public Seed(double previous_num, double new_num, char operator) {
        super(previous_num, new_num);
        this.seedOperator = operator;
    }
    @Override
    public double getResult() {
        return new_number;
    }

    @Override
    public char getOperator() {
        return seedOperator;
    }

    @Override
    public String getHistory() {
        return Double.toString(new_number);
    }
}
