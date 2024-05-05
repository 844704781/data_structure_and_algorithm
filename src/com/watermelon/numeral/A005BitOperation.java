package com.watermelon.numeral;

public class A005BitOperation {
    public static void main(String[] args) {

        System.out.println(8 >> 2); // 8 /4 = 2
        System.out.println(8 << 2); // 8 * 4 =32

    }

    public static void main1(String[] args) {
        int r1 = 3 & 4;
        System.out.println(r1); //0
        int r2 = -3 & -4;
        System.out.println(r2); //-4

        int r3 = 3 | 4;
        System.out.println(r3); //7
        int r4 = -3 | -4;
        System.out.println(r4); //-3


        int r5 = 3 ^ 4;
        System.out.println(r5); //7

        int _r5 = 4 ^ 3;
        System.out.println(_r5);//7

        int r6 = -3 ^ -4;
        System.out.println(r6); //1

        int _r6 = -4 ^ -3;
        System.out.println(_r6);//1

        int r7 = ~3;
        System.out.println(r7); //-4

        int r8 = ~-3;
        System.out.println(r8);//2

        int a = -2;
        int b = -3;
        System.out.println(a ^ b ^ b);//-2


        int c = -2;
        int d = -3;

        c = c ^ d;
        d = c ^ d; //c^d^d -->c
        c = c ^ d; //(c^d)^(c^d^d) --> c^d^c --> d^c^c -->d
        System.out.println(c);//-3
        System.out.println(d);//-2

        char e = 'e';
        char f = 'f';

        e = (char) (e ^ f);
        f = (char) (e ^ f);
        e = (char) (e ^ f);
        System.out.println(e);//f
        System.out.println(f);//e


        int r9 = 3 << 2;
        System.out.println(r9);//12

        int r10 = 12 >> 2;
        System.out.println(r10);//3

        int r11 = -12 >> 2;
        System.out.println(r11);//-3

        int r12 = 12 >>> 2;
        System.out.println(r12);//3

        int r13 = -12 >>> 2;
        System.out.println(r13);//1073741821


        int r14 = ~(0 | 0);
        System.out.println(r14);
    }
}
