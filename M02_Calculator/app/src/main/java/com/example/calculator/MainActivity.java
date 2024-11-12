/*
 * Copyright 2024

 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Main activity for the calculator view and operations
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Updates the {@link Calculations} list
     * @param input User input.
     * @param calculations The current state of the calculations list
     * @param total Total of the current running total.
     * @param operator The operator to apply for the most recent operation.
     * @return New updated calculations list
     * @throws InvalidInputException If unable to parse the input.
     */
    private Calculations calculate(Input input, Calculations calculations,
                                   double total, char operator) throws InvalidInputException {

        return Parse.operator(input.getInput(), total, operator).map(res -> {
            calculations.add(res);
            input.setInput(calculations.resultString());
            return calculations;
        }).orElseThrow(() -> new InvalidInputException(
                input.getInput()));
    }

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton add = findViewById(R.id.plus);
        ImageButton subtract = findViewById(R.id.minus);
        ImageButton divide = findViewById(R.id.divide);
        ImageButton multiply = findViewById(R.id.multiply);
        ImageButton back = findViewById(R.id.back);
        ImageButton forward = findViewById(R.id.forward);
        Button clear = findViewById(R.id.b_clear);
        Button allClear = findViewById(R.id.b_allClear);
        Button one = findViewById(R.id.input_one);
        Button two = findViewById(R.id.input_two);
        Button three = findViewById(R.id.input_three);
        Button four = findViewById(R.id.input_four);
        Button five = findViewById(R.id.input_five);
        Button six = findViewById(R.id.input_six);
        Button seven = findViewById(R.id.input_seven);
        Button eight = findViewById(R.id.input_eight);
        Button nine = findViewById(R.id.input_nine);
        Button zero = findViewById(R.id.input_zero);
        Button decimal = findViewById(R.id.input_decimal);
        Button equals = findViewById(R.id.b_equals);
        AtomicReference<Calculations> calculations = new AtomicReference<>(new Calculations());
        AtomicBoolean result = new AtomicBoolean(false);
        AtomicReference<Character> operator = new AtomicReference<>('s');
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        Input input = new Input(findViewById(R.id.input),
            findViewById(R.id.history));

    // TODO: Find a way to make these listener functions shorter.
    add.setOnClickListener(v -> {
            try{
                calculations.set(calculate(input, calculations.get(), total.get(), operator.get()));
            } catch(InvalidInputException e) {
                input.setInput(e.getMessage());
            }
            input.setInput(calculations.get().resultString());
            input.setHistory(calculations.get().getHistoryString());
            total.set(calculations.get().result());
            result.set(true);
            operator.set('+');
        });

        subtract.setOnClickListener(v -> {
            try{
                calculations.set(calculate(input, calculations.get(), total.get(), operator.get()));
            } catch(InvalidInputException e) {
                input.setInput(e.getMessage());
            }
            input.setInput(calculations.get().resultString());
            total.set(calculations.get().result());
            input.setHistory(calculations.get().getHistoryString());
            result.set(true);
            operator.set('-');
        });

        multiply.setOnClickListener(v -> {
            try{
                calculations.set(calculate(input, calculations.get(), total.get(), operator.get()));
            } catch(InvalidInputException e) {
                input.setInput(e.getMessage());
            }
            input.setInput(calculations.get().resultString());
            total.set(calculations.get().result());
            input.setHistory(calculations.get().getHistoryString());
            result.set(true);
            operator.set('X');
        });

        divide.setOnClickListener(v -> {
            try{
                calculations.set(calculate(input, calculations.get(), total.get(), operator.get()));
            } catch(InvalidInputException e) {
                input.setInput(e.getMessage());
            }
            input.setInput(calculations.get().resultString());
            total.set(calculations.get().result());
            input.setHistory(calculations.get().getHistoryString());
            result.set(true);
            operator.set('/');
        });

        clear.setOnClickListener(v -> input.clear());

        allClear.setOnClickListener(v -> {
            calculations.set(new Calculations());
            total.set((double) 0);
            operator.set('s');
            input.allClear();
        });

        back.setOnClickListener(v -> {
            calculations.get().back();
            operator.set('s');
            total.set(calculations.get().result());
            input.setInput(calculations.get().resultString());
            input.setHistory(calculations.get().getHistoryString());
        });

        forward.setOnClickListener(v -> {
            calculations.get().forward();
            operator.set('s');
            total.set(calculations.get().result());
            input.setInput(calculations.get().resultString());
            input.setHistory(calculations.get().getHistoryString());
        });

        equals.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total.get(), operator.get()));
            operator.set('s');
            input.setInput(calculations.get().resultString());
            total.set(calculations.get().result());
            input.setHistory(calculations.get().getHistoryString());
            result.set(true);
        });

        one.setOnClickListener(v -> {
            if(result.get()) {
                input.clear();
                result.set(false);
            }
            input.setInput('1');
        });

        two.setOnClickListener(v -> {
            if(result.get()) {
                input.clear();
                result.set(false);
            }
            input.setInput('2');
        });

        three.setOnClickListener(v -> {
            if(result.get()) {
                input.clear();
                result.set(false);
            }
            input.setInput('3');
        });

        four.setOnClickListener(v -> {
            if(result.get()) {
                input.clear();
                result.set(false);
            }
            input.setInput('4');
        });

        five.setOnClickListener(v -> {
            if(result.get()) {
                result.set(false);
                input.clear();
            }
            input.setInput('5');
        });

        six.setOnClickListener(v -> {
            if(result.get()) {
                result.set(false);
                input.clear();
            }
            input.setInput('6');
        });
        seven.setOnClickListener(v -> {
            if(result.get()) {
                result.set(false);
                input.clear();
            }
            input.setInput('7');
        });
        eight.setOnClickListener(v -> {
            if(result.get()) {
                result.set(false);
                input.clear();
            }
            input.setInput('8');
        });
        nine.setOnClickListener(v -> {
            if(result.get()) {
                result.set(false);
                input.clear();
            }
            input.setInput('9');
        });
        zero.setOnClickListener(v -> {
            if(result.get()) {
                result.set(false);
                input.clear();
            }
            input.setInput('0');
        });

        decimal.setOnClickListener(v -> {
            if(!input.getInput().contains(".")) {
                if(Objects.equals(input.getInput(), "0")) {
                    input.setInput("0.");
                } else {
                    input.setInput('.');
                }
            }
        });
    }
}

