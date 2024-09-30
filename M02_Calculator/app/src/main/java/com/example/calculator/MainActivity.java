package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final Calculations calculations = new Calculations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        Button add = findViewById(R.id.b_Add);
        Button subtract = findViewById(R.id.b_subtract);
        Button divide =findViewById(R.id.b_divide);
        Button multiply = findViewById(R.id.b_multiply);
        Button clear = findViewById(R.id.b_clear);
        Button allClear = findViewById(R.id.b_allClear);
        Button back = findViewById(R.id.b_back);
        Button forward = findViewById(R.id.b_forward);
        Calculations calculations = new Calculations();

        Inputs inputs = new Inputs(
                    findViewById(R.id.num_one),
                    findViewById(R.id.num_two),
                    findViewById(R.id.answer));

        add.setOnClickListener(v -> {
            calculations.add(new Add(calculations.result(), inputs.getInput_one()));
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

        subtract.setOnClickListener(v -> {
            calculations.add(new Subtract(calculations.result(), inputs.getInput_one()));
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

        multiply.setOnClickListener(v -> {
            calculations.add(new Multiply(calculations.result(), inputs.getInput_one()));
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

        divide.setOnClickListener(v -> {
            calculations.add(new Divide(calculations.result(), inputs.getInput_one()));
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

        clear.setOnClickListener(v -> {
            inputs.clear();
        });

        allClear.setOnClickListener(v -> {
            calculations.allClear();
            inputs.allClear();
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

        back.setOnClickListener(v -> {
            calculations.back();
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

        forward.setOnClickListener(v -> {
            calculations.forward();
            inputs.getAnswer().setText(String.format("%s", calculations.result()));
        });

    }

}
