package com.watermelon.tree;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 赫夫曼压缩
 */
public class A07HuffmanCompression {
    /**
     * huffman编码表
     * key为原字符对应的字节
     * value为huffman编码
     */
    private static Map<Byte, String> huffmanMap = new HashMap<>();

    public static void main(String[] args) {

        String str = "i like like like java do you like a java";
//        String str = "我是一个中国人，我热爱中国";
        List<Node> data = toList(str);

        byte[] meta = str.getBytes(StandardCharsets.UTF_8);
        byte[] zipData = huffmanZip(meta);
        System.out.println("压缩前的字节数组为:" + Arrays.toString(meta));
        System.out.println("压缩前的字节数组长度为:" + meta.length);
        System.out.println("压缩后的字节数组为:" + Arrays.toString(zipData));
        System.out.println("压缩后的字节长度为:" + zipData.length);
        System.out.println("压缩率:" + (double) (meta.length - zipData.length) / meta.length);

        byte[] unzipData = huffmanUnzip(zipData);
        System.out.println("解压后数据的字节数组为:" + Arrays.toString(unzipData));
        System.out.println("解压后数据为:" + new String(unzipData, StandardCharsets.UTF_8));
    }

    /**
     * 将压缩的字节进行解码，获取原字节
     * 1. 将压缩后的字节转成二进制的形式，长度保持不变
     * 2. 将二进制按每一位存放到list中
     * 3. 创建sbf,遍历list，sbf拼接list中的每一项，直到sbf中的内容与哈夫曼编码表中的value有一致的
     * 则将sbf对应哈夫曼编码表中的key存入新List[Byte]中，直至遍历完毕
     * 4. List[Byte]转成->byte[]，直接new String(byte)得到压缩前的数据
     *
     * @param meta
     * @return
     */
    public static byte[] huffmanUnzip(byte[] meta) {
        StringBuilder sbf = new StringBuilder();
        for (int i = 0; i < meta.length; i++) {
            byte data = meta[i];
            /**
             * 正数需要补位，最后一位不管是什么，都不需要补位
             */
            boolean should = data >= 0;
            if (i == meta.length - 1) {
                should = false;
            }
            String huffmanBinary = to8BitBinaryString(should, data);
            sbf.append(huffmanBinary);
        }

        List<String> huffmanTable = huffmanMap.entrySet().stream()
                .map(entry -> entry.getValue()).collect(Collectors.toList());
        Map<String, Byte> valueMapKey = huffmanMap.entrySet().stream().collect(Collectors.toMap(x -> x.getValue(), x -> x.getKey()));

        char[] binaryChar = sbf.toString().toCharArray();
        StringBuilder temp = new StringBuilder();
        List<Byte> resultList = new ArrayList<>();
        for (int i = 0; i < binaryChar.length; i++) {
            char c = binaryChar[i];
            temp.append(c);
            /**
             * 判断c是否被哈夫曼表的值中有，如果有，则翻译成原字节
             */
            if (huffmanTable.contains(temp.toString())) {
                Byte primaryByte = valueMapKey.get(temp.toString());
                resultList.add(primaryByte);
                temp.setLength(0);//清空temp
            }
        }
        byte[] result = new byte[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }


    /**
     * 将字节转成8位的二进制
     *
     * @param should 是否需要补位
     * @param data
     * @return
     */
    private static String to8BitBinaryString(boolean should, int data) {
        /**
         * 获取压缩后数据的二进制，压缩的时候是八位八位压缩的，这里得到的是32为的二进制
         * 所以先32为字节要转成8位的字节
         * 所以索引0留下-->符号位
         * 索引len-7到len-1要留下
         */

        if (should) {
            data = data | 256;//做按位或运算，转成二进制后，同位有一个为1，则结果为1，这样做可以确保正数前面可以补0
        }
        String binaryString = Integer.toBinaryString(data);
        String result;
        if (binaryString.length() > 8) {
            result = binaryString.substring(binaryString.length() - 8);
        } else {
            result = binaryString;
        }
        System.out.println(result);
        return result;
    }

    /**
     * 将下列文字进行哈夫曼压缩
     * 1. 统计要压缩的数据每个字符出现的次数，从小到大排序
     * 2. 以次数为权值构建Huffman树
     * 3. 计算每个huffman的叶子节点的前缀编码，向左为0，向右为1
     * 使用前缀查找的方式查找叶子节点，记录下查找路径，left为0，right为1
     * 得到 字符->前缀编码的哈希表
     * 4. 使用前缀编码表替换要压缩的数据
     */
    public static byte[] huffmanZip(byte[] meta) {

        /**
         * 打印原数据字节组成的字符串
         */
        StringBuilder metaSb = new StringBuilder();
        for (int i = 0; i < meta.length; i++) {
            byte temp = meta[i];
            String binaryString = Integer.toBinaryString(temp);
            metaSb.append(binaryString);
        }
        System.out.println("压缩前的字符编码为:" + metaSb);
        System.out.println("压缩前的字符编码长度为:" + metaSb.length());


        String str = new String(meta, StandardCharsets.UTF_8);
        List<Node> data = toList(str);
        Node huffman = toHuffmanTree(new ArrayList<>(data));
        System.out.println("----哈夫曼树------");
        huffman.preOrder();
        System.out.println("----------");

        for (Node node : data) {
            //得到每个叶子节点
            String prefixCode = huffman.getPrefixCode(node);
            huffmanMap.put(node.getValue(), prefixCode);
        }
        System.out.println("-------哈夫曼编码表:--------");
        System.out.println(huffmanMap);//哈夫曼编码表
        System.out.println("---------------");
        /**
         * 构成哈夫曼编码后的字节字符串列表
         */
        List<String> result = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            byte cByte = (byte) c.charValue();
            String prefixCode = huffmanMap.get(cByte);
            result.add(prefixCode);
        }
        /**
         * 将压缩后的字符串列表拼接成字符串
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            sb.append(result.get(i));
        }
        System.out.println("原始字符串对应的赫夫曼字符串编码为:" + sb);
        System.out.println("原始字符串对应的赫夫曼字符串编码长度为:" + sb.length());

        /**
         * 将哈夫曼字节列表压缩，原本长度是40，然后压缩后会变成17
         */
        byte[] zipData = zip(result);
        return zipData;
    }

    /**
     * 重新编排字节数组
     *
     * @param data
     * @return
     */
    private static byte[] zip(List<String> data) {
        StringBuilder sb = new StringBuilder();
        for (String b : data) {
            sb.append(b);
        }
        /**
         * 将原来哈夫曼编码的二进制字符串每8位重新存储到一个新的字节数组中
         */
        int len;
        if (sb.length() % 8 == 0) {
            len = sb.length() / 8;
        } else {
            len = sb.length() / 8 + 1;
        }

        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            int start = i * 8;
            int end = i * 8 + 8;
            String s;
            if (end > sb.length()) {
                s = sb.substring(start);
            } else {
                s = sb.substring(start, end);
            }
//            System.out.println("-----");
//            System.out.println(s);
            result[i] = Integer.valueOf(s, 2).byteValue();
//            System.out.println(to8BitBinaryString(result[i]));
//            System.out.println("-----");
        }
        return result;
    }

    /**
     * 统计要压缩的数据每个字符出现的次数，从小到大排序
     *
     * @param str
     * @return
     */
    private static List<Node> toList(String str) {
        Map<Character, Integer> cti = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (cti.get(c) == null) {
                cti.put(c, 1);
            } else {
                cti.put(c, cti.get(c) + 1);
            }
        }
        List<Node> result = new LinkedList<>();
        for (Map.Entry<Character, Integer> keyValue : cti.entrySet()) {
            char c = keyValue.getKey();
            int weight = keyValue.getValue();
            result.add(new Node(c, (byte) c, weight));
        }
        Collections.sort(result);
        return result;
    }

    /**
     * 以次数为权值构建Huffman树
     *
     * @param data
     * @return
     */
    private static Node toHuffmanTree(List<Node> data) {
        while (data.size() > 1) {
            Collections.sort(data);
            Node left = data.get(0);
            Node right = data.get(1);
            data.remove(0);
            data.remove(0);
            Node parent = new Node(left.weight + right.weight);
            parent.setLeft(left);
            parent.setRight(right);
            data.add(parent);
        }

        return data.get(0);
    }

    public static class Node implements Comparable<Node> {
        /**
         * 放对应字符
         */
        private Byte value;
        private Character c;
        private Integer weight;
        private Node left;
        private Node right;

        public Node(int weight) {
            this.weight = weight;
        }

        public Node(Character c, Byte value, int weight) {
            this.c = c;
            this.value = value;
            this.weight = weight;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Integer getWeight() {
            return weight;
        }

        public Byte getValue() {
            return value;
        }

        public char getC() {
            return c;
        }

        public void setC(char c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + value +
                    ",c=" + c +
                    ", weight=" + weight +
                    '}';
        }


        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(value, node.value) && Objects.equals(weight, node.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, weight, left, right);
        }

        public void preOrder() {
            preOrder(this);
        }

        private static void preOrder(Node node) {
            System.out.println(node);
            if (node.left != null) {
                preOrder(node.left);
            }
            if (node.right != null) {
                preOrder(node.right);
            }
        }

        /**
         * 计算每个huffman的叶子节点的前缀编码，向左为0，向右为1
         * 使用前缀查找的方式查找叶子节点，记录下查找路径，left为0，right为1
         * 得到 字符->前缀编码的哈希表
         *
         * @param node
         * @return
         */
        public String getPrefixCode(Node node) {
            List<Boolean> trace = new ArrayList<>();
            track(this, node, trace);

            StringBuilder sbd = new StringBuilder();
            for (int i = 0; i < trace.size(); i++) {
                sbd.append(trace.get(i) ? "1" : "0");
            }
            return sbd.toString();
        }

        private boolean track(Node currentNode, Node targetNoe, List<Boolean> result) {
            if (currentNode.equals(targetNoe)) {
                return true;
            }
            if (currentNode.left != null) {
                result.add(false);
                if (track(currentNode.left, targetNoe, result)) {
                    return true;
                }
                result.remove(result.size() - 1);
            }
            if (currentNode.right != null) {
                result.add(true);
                if (track(currentNode.right, targetNoe, result)) {
                    return true;
                }
                result.remove(result.size() - 1);
            }
            return false;
        }
    }
}
