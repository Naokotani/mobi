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
 * Defines an abstract operation which is extended by {@link Add} {@link Subtract}
 * {@link Multiply} {@link Divide}
 */
public abstract class Operation {
    public final double previous_num;
    public final double new_number;

    public Operation(double previous_num, double new_num) {
        this.previous_num = previous_num;
        this.new_number = new_num;
    }

    abstract public double getResult();
    abstract public String getHistory();
}
