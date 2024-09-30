package com.example.calculator;

import java.util.Optional;

public class Parse {
    public Optional<Operation> operator(String input, double total) throws Exception {
        char operator = input.charAt(0);
        double value = 0;
        if(input.length() > 1) {
            value = Double.parseDouble(input.substring(1));
        }
        Optional<Operation> o;
         switch(operator) {
             case '+': o = Optional.of(new Add(value, total)); break;
             case '-': o = Optional.of(new Subtract(value, total)); break;
             case '/': o = Optional.of(new Divide(value, total)); break;
             case 'X': o = Optional.of(new Multiply(total, value)); break;
            default: o = Optional.empty();
         }

         return o;
    }
}
