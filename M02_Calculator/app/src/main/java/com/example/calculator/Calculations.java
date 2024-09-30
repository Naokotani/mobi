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

    public Calculations add(Operation operation) {
        if(currentIndex > 0) {
            operations = clearRest(currentIndex);
        } else if(operations.size() > maxHist - 1) {
            operations.remove(operations.size() - 1);
        }

        operations.add(0, operation);
        return this;
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

    public int size() {
        return operations.size();
    }

    public char getSeedOperator() {
        return operations.get(0).getOperator();
    }

    public double getSeedValue() {
        return operations.get(0).getResult();
    }
}
