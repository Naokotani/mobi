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
 * Defines a seed operation which is the operation at index 0 of an operations list
 */
public class Seed extends Operation {
    private final char seedOperator;

    public Seed(double previous_num, double new_num, char operator) {
        super(previous_num, new_num);
        this.seedOperator = operator;
    }
    @Override
    public double getResult() {
        return new_number;
    }

    @Override
    public String getHistory() {
        return Double.toString(new_number);
    }
}
