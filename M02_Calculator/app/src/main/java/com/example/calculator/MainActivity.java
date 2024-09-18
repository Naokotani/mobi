package com.example.calculator;

import static java.lang.Double.parseDouble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        Button add = (Button) findViewById(R.id.b_Add);
        Button subtract = (Button) findViewById(R.id.b_subtract);
        Button divide = (Button) findViewById(R.id.b_divide);
        Button multiply = (Button) findViewById(R.id.b_multiply);

        Inputs inputs = new Inputs(
                    (EditText) findViewById(R.id.num_one),
                    (EditText) findViewById(R.id.num_two),
                    (EditText) findViewById(R.id.answer));

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Button Press", "Add clicked");
                Calculate operation = new Calculate(inputs.getInput_one(),
                        inputs.getInput_two());

                // Set the Answer into the the answer field
                inputs.getAnswer().setText(String.format("%s", operation.add()));
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Button Press", "Subtract clicked");
                Calculate operation = new Calculate(parseDouble(String.valueOf(inputs.getInput_one())),
                        parseDouble(String.valueOf(inputs.getInput_two())));

                // Set the Answer into the the answer field
                inputs.getAnswer().setText(String.format("%s", operation.subtract()));
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Button Press", "Multiply clicked");
                Calculate operation = new Calculate(parseDouble(String.valueOf(inputs.getInput_one())),
                        parseDouble(String.valueOf(inputs.getInput_two())));

                // Set the Answer into the the answer field
                inputs.getAnswer().setText(String.format("%s", operation.multiply()));
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Button Press", "Divide clicked");
                Calculate operation = new Calculate(parseDouble(String.valueOf(inputs.getInput_one())),
                        parseDouble(String.valueOf(inputs.getInput_two())));

                // Set the Answer into the the answer field
                inputs.getAnswer().setText(String.format("%s", operation.divide()));
            }
        });

    }

}
