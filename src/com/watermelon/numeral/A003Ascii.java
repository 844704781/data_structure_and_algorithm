package com.watermelon.numeral;

public class A003Ascii {
    public static void main(String[] args) {
        char c = '[';
        byte ascii = (byte) c;//输出字符的十进制ascii码
        System.out.println(ascii); //91

        byte asc = -128;
        char ct = (char) asc;
        System.out.println(ct);
    }
}
