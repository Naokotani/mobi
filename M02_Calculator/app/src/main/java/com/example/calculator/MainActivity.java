package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    private boolean resetInput(Input input, boolean result) {
        if(result) {
            input.setInput("");
        }
        return false;
    }

    private Calculations calculate(Input input, Calculations calculations, double total) {
        Optional<Operation> parse = parseInput(input, total);
        parse.ifPresent(calculations::add);
        parse.ifPresent(p -> input.setInput(calculations.resultString()));
        return calculations;
    }

    private Optional<Operation> parseInput(Input input, double total) {
        try {
            return  new Parse().operator(input.getInput(), total);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        Button add = findViewById(R.id.b_Add);
        Button subtract = findViewById(R.id.b_subtract);
        Button divide = findViewById(R.id.b_divide);
        Button multiply = findViewById(R.id.b_multiply);
        Button clear = findViewById(R.id.b_clear);
        Button allClear = findViewById(R.id.b_allClear);
        Button back = findViewById(R.id.b_back);
        Button forward = findViewById(R.id.b_forward);
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
        AtomicReference<Calculations> calculations = new AtomicReference<>(new Calculations());
        AtomicBoolean result = new AtomicBoolean(false);
        double total = 0;

        Input input = new Input(findViewById(R.id.input),
                findViewById(R.id.history));

        add.setOnClickListener(v -> {
            input.prependInput('+');
            calculations.set(calculate(input, calculations.get(), total));
            result.set(true);
        });

        subtract.setOnClickListener(v -> {
            input.prependInput('-');
        });

        multiply.setOnClickListener(v -> {
            input.prependInput('X');
        });

        divide.setOnClickListener(v -> {
            input.prependInput('/');
        });

        clear.setOnClickListener(v -> {
            input.clear();
        });

        allClear.setOnClickListener(v -> {
            calculations.get().allClear();
            input.allClear();
            input.allClear();
        });

        back.setOnClickListener(v -> {
            calculations.get().back();
            input.setInput(calculations.get().resultString());
            input.setHistory(calculations.get().getHistoryString());
        });

        forward.setOnClickListener(v -> {
            calculations.get().forward();
            input.setInput(calculations.get().resultString());
            input.setHistory(calculations.get().getHistoryString());
        });

        one.setOnClickListener(v -> {
            if(result.get()) {
                input.clear();
            }
            input.setInput('1');
        });

        two.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('2');
        });

        three.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('3');
        });

        four.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('4');
        });

        five.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('5');
        });

        six.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('6');
        });
        seven.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('7');
        });
        eight.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('8');
        });
        nine.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('9');
        });
        zero.setOnClickListener(v -> {
            calculations.set(calculate(input, calculations.get(), total));
            input.setInput('0');
        });
        decimal.setOnClickListener(v -> {
            input.setInput('.');
        });


    }
}
