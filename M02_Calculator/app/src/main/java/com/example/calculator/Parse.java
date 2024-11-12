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

import android.util.Log;

import java.util.Optional;

/**
 * Parse user input to find the appropriate {@link Operation}
 */
public class Parse {
    /**
     * Parses user input to choose the determine the appropriate operation.
     * @param input The user input.
     * @param total The Current running total.
     * @param operator The currently selected operator.
     * @return The {@link Operation} to be added to {@link Calculations}
     */
    public static Optional<Operation> operator(String input, double total, char operator) {
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
