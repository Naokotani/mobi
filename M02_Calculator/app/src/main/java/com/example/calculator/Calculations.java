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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stores and manages a list of {@link Operation}
 */
public class Calculations {
    private final static int maxHist = 30;
    private List<Operation> operations = new ArrayList<>();
    private int currentIndex = 0;

    public void add(Operation operation) {
        if(currentIndex > 0) {
            operations = clearRest(currentIndex);
        } else if(operations.size() > maxHist - 1) {
            operations.remove(operations.size() - 1);
        }

        operations.add(0, operation);
    }

    public List<Operation> clearRest(int index) {
        currentIndex = 0;
        Operation[] arr = new Operation[operations.size() - (index + 1)];
        return Arrays.stream(operations.toArray(arr), index, operations.size())
                .collect(Collectors.toList());
    }

    public void forward() {
        if(currentIndex > 0) {
            currentIndex--;
        }
    }

    public void back() {
        if(currentIndex < operations.size() - 1) {
            currentIndex++;
        }
    }

    public String getHistoryString() {
        if(operations.isEmpty()) {
            return "0";
        }
        return operations.get(currentIndex).getHistory();
    }

    public String resultString() {
        if(operations.isEmpty()) {
            return "0";
        }

        return Double.toString(operations.get(currentIndex).getResult());
    }

    public double result() {
        if(operations.isEmpty()) {
            return 0;
        }

        return operations.get(currentIndex).getResult();
    }
}
