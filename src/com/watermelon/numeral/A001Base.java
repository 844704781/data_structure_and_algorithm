package com.watermelon.numeral;

public class A001Base {
    public static void main(String[] args) {
        int binary = 0b10; //将二进制放到变量中，输出是以十进制输出
        System.out.println(binary); //2
        int decimal = 2;//将十进制放到变量中
        System.out.println(decimal);
        int octal = 010;//将八进制放到变量中，以十进制输出
        System.out.println(octal);//8
        int hex = 0x10;//将十六进制放到变量中，以十进制输出
        System.out.println(hex);//16x

        byte c =  -128;
        System.out.println(c);

        float f = -99999999999999999999999999999999999999f;
        System.out.println(f);

        byte x = -1;
        int i = Byte.toUnsignedInt(x);
        System.out.println(i);

        long l = Byte.toUnsignedLong(x);
        System.out.println(l);
    }
}
