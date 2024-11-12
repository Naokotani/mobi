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

import android.annotation.SuppressLint;
import android.widget.TextView;

/**
 * Manages user input
 */
public class Input {
    private final TextView input;
    private final TextView history;

    public Input(TextView input, TextView history) {
        this.input = input;
        this.history = history;
        input.setText("0");
        history.setText("0");
    }

    public String getInput() {
        return !input.getText().toString().isEmpty() ? input.getText().toString() : "0";
    }

    @SuppressLint("SetTextI18n")
    public void setInput(char c) {
        if(input.getText() == "0") {
            input.setText("");
        }
        String oldText = input.getText().toString();
        input.setText(oldText + c);
    }

    @SuppressLint("SetTextI18n")
    public void prependInput(char c) {
        if(input.getText() == "0") {
            input.setText("");
        }
        String oldText = input.getText().toString();
        input.setText(c + oldText);
    }

    public void setInput(String s) {
        input.setText(s);
    }

    public void setHistory(String history) {
        this.history.setText(history);
    }

    public void clear() {
        input.setText("0");
    }

    public void allClear() {
        input.setText("0");
        history.setText("0");
    }
}


