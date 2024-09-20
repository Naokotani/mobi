package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        Button add = findViewById(R.id.b_Add);
        Button subtract = findViewById(R.id.b_subtract);
        Button divide =findViewById(R.id.b_divide);
        Button multiply = findViewById(R.id.b_multiply);

        Inputs inputs = new Inputs(
                    findViewById(R.id.num_one),
                    findViewById(R.id.num_two),
                    findViewById(R.id.answer));

        add.setOnClickListener(v -> {
            Log.i("Button Press", "Add clicked");
            Calculate operation = new Calculate(inputs.getInput_one(),
                    inputs.getInput_two());

            // Set the Answer into the the answer field
            inputs.getAnswer().setText(String.format("%s", operation.add()));
        });

        subtract.setOnClickListener(v -> {
            Log.i("Button Press", "Subtract clicked");
            Calculate operation = new Calculate(inputs.getInput_one(),
                    inputs.getInput_two());

            // Set the Answer into the the answer field
            inputs.getAnswer().setText(String.format("%s", operation.subtract()));
        });

        multiply.setOnClickListener(v -> {
            Log.i("Button Press", "Multiply clicked");
            Calculate operation = new Calculate(inputs.getInput_one(),
                    inputs.getInput_two());

            // Set the Answer into the the answer field
            inputs.getAnswer().setText(String.format("%s", operation.multiply()));
        });

        divide.setOnClickListener(v -> {
            Log.i("Button Press", "Divide clicked");
            Calculate operation = new Calculate(inputs.getInput_one(),
                    inputs.getInput_two());

            // Set the Answer into the the answer field
            inputs.getAnswer().setText(String.format("%s", operation.divide()));
        });

    }

}
