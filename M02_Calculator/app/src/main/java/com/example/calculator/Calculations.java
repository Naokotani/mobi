package com.example.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calculations {
    private final static int maxHist = 30;
    private List<Operation> operations;
    private int currentIndex = 0;
    public Calculations() {
        this.operations = new ArrayList<>();
    }

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

    public List<Operation> clearLast() {
        operations.remove(0);
        return operations;
    }

    public void allClear() {
        operations.clear();
        operations = new ArrayList<>();
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

    public double result() {
        if(operations.isEmpty()) {
            return 0;
        }

        return operations.get(currentIndex).getResult();
    }
}
