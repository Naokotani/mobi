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

/**
 * Defines a multiplication operation.
 */
public class Multiply extends Operation {
    public final double result;
    public final static char operator = '*';
    public final String history;

    public Multiply(double previous_num, double new_num) {
        super(previous_num, new_num);
        this.result = previous_num * new_num;
        if(previous_num == 0) {
            this.history = " X " + new_num;
        } else {
            this.history = previous_num  + " X " + new_num;
        }
    }

    @Override
    public double getResult() {
        return result;
    }

    @Override
    public String getHistory() {
        return history;
    }
}
