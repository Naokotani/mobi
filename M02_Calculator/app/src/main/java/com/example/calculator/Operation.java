package com.example.calculator;

public abstract class Operation {
    public final double previous_num;
    public final double new_number;
    public boolean seed = false;

    public Operation(double previous_num, double new_num) {
        this.previous_num = previous_num;
        this.new_number = new_num;
    }

    abstract public double getResult();
    abstract public char getOperator();
    abstract public String getHistory();
}
