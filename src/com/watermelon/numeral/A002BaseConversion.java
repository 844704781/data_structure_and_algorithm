package com.watermelon.numeral;

public class A002BaseConversion {
    public static void main(String[] args) {

        otherToOther();
    }

    /**
     * 十进制转其它进制
     */
    public static void decimalToOrder() {
        Byte decimalByte = 2;
        //Byte没有转换的方法
        Short decimalShort = 2;
        //Short没有转换方法

        String binaryString = Integer.toBinaryString(2); //10进制转2进制
        System.out.println(binaryString);//10

        String octalString = Integer.toOctalString(8);//10进制转8进制
        System.out.println(octalString);//10

        String hexString = Integer.toHexString(16);//10进制转16进制
        System.out.println(hexString);//10


        String longBinaryString = Long.toBinaryString(2L); //10进制转2进制
        System.out.println(longBinaryString);//10

        String longOctalString = Long.toOctalString(8L); //10进制转2进制
        System.out.println(longOctalString);//10

        String longHexString = Long.toHexString(16L); //16进制转2进制
        System.out.println(longHexString);//10


        long f = Double.doubleToLongBits(0.3125d);//将小数转换成2进制形式的long值
        String fBinaryString = Long.toBinaryString(f);
        System.out.println(fBinaryString);
    }

    /**
     * 其它进制互转
     */
    public static void otherToOther() {
        int decimal = Integer.parseInt("10", 2);//2进制转10进制
        System.out.println(decimal);//2
        int oct = Integer.parseInt("10", 8);//8进制转10进制
        System.out.println(oct);//8
        int hex = Integer.parseInt("10", 16);//16进制转10进制
        System.out.println(hex);//16

        /**
         * 16进制转8进制
         * 16进制先转10进制，再转8进制
         */
        int decimal1 = Integer.parseInt("1A", 16);
        System.out.println(decimal1);//26
        String octNum = Integer.toOctalString(decimal1);
        System.out.println(octNum);//32

    }
}
