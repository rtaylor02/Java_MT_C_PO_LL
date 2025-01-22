package com.rtaylor02;

import com.math1.Adder;
import com.math1.Subtractor;
import com.other.Divider;
import com.other.Multiplier;

public class Main {
    public static void main(String[] args) {
        Adder adder = new Adder();
        System.out.println("add(1, 2) = " + adder.add(1, 2));

        Subtractor subtractor = new Subtractor();
        System.out.println("subtract(2, 1) = " + subtractor.subtract(2, 1));

        Divider divider = new Divider();
        System.out.println("divide(6, 2) = " + divider.divide(6, 2));

        Multiplier multiplier = new Multiplier();
        System.out.println("multiply(2, 2) = " + multiplier.multiply(2, 2));
    }
}
