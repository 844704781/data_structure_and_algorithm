package com.watermelon.numeral;

public class A004Binary {
    public static void main(String[] args) {
        String a = Integer.toBinaryString(1);
        String b = Integer.toBinaryString(-1);
        System.out.println(a); //1
        System.out.println(b); //11111111111111111111111111111111 -->32个1

    }
}
