package com.rtaylor02;

import com.math1.Adder;
import com.math1.Subtractor;
import com.math2.Divider;
import com.math2.Multiplier;

public class Main {
    public static void main(String[] args) {
        Adder adder = new Adder();
        System.out.println("add(11, 21) = " + adder.add(11, 21));

        Subtractor subtractor = new Subtractor();
        System.out.println("subtract(21, 11) = " + subtractor.subtract(21, 11));

        Divider divider = new Divider();
        System.out.println("divide(60, 20) = " + divider.divide(60, 20));

        Multiplier multiplier = new Multiplier();
        System.out.println("multiply(21, 21) = " + multiplier.multiply(21, 21));
    }
}
