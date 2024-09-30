package com.example.calculator;

import java.util.Optional;

public class Parse {
    public Optional<Operation> operator(String input, double total, char operator) throws Exception {
        double value = Double.parseDouble(input);
        Optional<Operation> o;
         switch(operator) {
             case 's': o = Optional.of(new Seed(total, value, operator)); break;
             case '+': o = Optional.of(new Add(total, value)); break;
             case '-': o = Optional.of(new Subtract(total, value)); break;
             case '/': o = Optional.of(new Divide(total, value)); break;
             case 'X': o = Optional.of(new Multiply(total, value)); break;
            default: o = Optional.empty();
         }

         return o;
    }
}
