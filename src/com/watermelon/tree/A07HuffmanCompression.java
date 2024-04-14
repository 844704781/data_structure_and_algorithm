package com.watermelon.tree;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 赫夫曼压缩
 */
public class A07HuffmanCompression {

    public static void main(String[] args) {
        /**
         * 将下列文字进行哈夫曼压缩
         * 1. 统计要压缩的数据每个字符出现的次数，从小到大排序
         * 2. 以次数为权值构建Huffman树
         * 3. 计算每个huffman的叶子节点的前缀编码，向左为0，向右为1
         *    使用前缀查找的方式查找叶子节点，记录下查找路径，left为0，right为1
         *    得到 字符->前缀编码的哈希表
         * 4. 使用前缀编码表替换要压缩的数据
         */
        String str = "i like like like java do you like a java";
//        String str = "我是一个中国人，我热爱中国";
        List<Node> data = toList(str);
        Node huffman = toHuffmanTree(new ArrayList<>(data));
        System.out.println("----哈夫曼树------");
        huffman.preOrder();
        System.out.println("----------");

        Map<Byte, String> map = new HashMap<>(data.size());
        for (Node node : data) {
            //得到每个叶子节点
            String prefixCode = huffman.getPrefixCode(node);
            map.put(node.getValue(), prefixCode);
        }
        System.out.println("-------哈夫曼编码表:--------");
        System.out.println(map);//哈夫曼编码表
        System.out.println("---------------");
        List<String> result = new ArrayList<>();//哈夫曼编码后的字节字符串列表
        for (Character c : str.toCharArray()) {
            byte cByte = (byte) c.charValue();
            String prefixCode = map.get(cByte);
            result.add(prefixCode);
        }


        byte[] meta = str.getBytes(StandardCharsets.UTF_8);
        StringBuilder metaSb = new StringBuilder();
        for (int i = 0; i < meta.length; i++) {
            byte temp = meta[i];
            String binaryString = Integer.toBinaryString(temp);
            metaSb.append(binaryString);
        }
        System.out.println("压缩前的字符编码为:" + metaSb);
        System.out.println("压缩前的字符编码长度为:" + metaSb.length());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            sb.append(result.get(i));
        }
        System.out.println("原始字符串对应的赫夫曼字符串编码为:" + sb);
        System.out.println("原始字符串对应的赫夫曼字符串编码长度为:" + sb.length());


        byte[] zipData = zip(result);
        System.out.println("压缩前的字节数组为:" + Arrays.toString(meta));
        System.out.println("压缩前的字节数组长度为:" + meta.length);
        System.out.println("压缩后的字节数组为:" + Arrays.toString(zipData));
        System.out.println("压缩后的字节长度为:" + zipData.length);
        System.out.println("压缩率:" + (double) (meta.length - zipData.length) / meta.length);
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
            result[i] = Integer.valueOf(s, 2).byteValue();
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
