package com.example.calculator;

import android.util.Log;

public class Calculate {
    private final Double number_one;
    private final Double number_two;
    private Double answer;

    public Calculate(Double number_one, Double number_two) {
        this.number_two = number_two;
        this.number_one = number_one;
    }

    public Double add() {
        try {
            this.answer = number_one + number_two;
        }
        catch (Exception e) {
            Log.w("Bad input", "Add Selected with no inputs ... " );
            return 0.0;
        }
        return answer;
    }

    public Double subtract() {
        try {
            this.answer = number_one - number_two;
        }
        catch (Exception e) {
            Log.w("Bad input", "Subtract Selected with no inputs ... " );
        }
        return answer;
    }

    public Double divide() {
        try {
            this.answer = number_one / number_two;
        }
        catch (Exception e) {
            Log.w("Bad input", "Divide Selected with no inputs ... " );
        }
        return answer;
    }

    public Double multiply() {
        try {
            this.answer = number_one * number_two;
        }
        catch (Exception e) {
            Log.w("Bad input", "Multiply Selected with no inputs ... " );
        }
        return answer;
    }
}
