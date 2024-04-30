package com.watermelon.leetcode;


import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
    public static void main(String[] args) {
        AtomicInteger counter = new AtomicInteger();
        System.out.println(counter.get());
        System.out.println(counter.incrementAndGet());
    }
}
