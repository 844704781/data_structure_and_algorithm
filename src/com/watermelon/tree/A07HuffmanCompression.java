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
        System.out.println(data);
        huffman.preOrder();
        System.out.println("---------------");
        Map<Integer, Byte> map = new HashMap<>(data.size());
        for (Node node : data) {
            //得到每个叶子节点
            byte prefixCode = huffman.getPrefixCode(node);
            map.put(Integer.valueOf(node.getC()), prefixCode);
        }
        System.out.println(map);
        System.out.println("---------------");
        List<Byte> bytes = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            byte prefixCode = map.get(Integer.valueOf(c));
            bytes.add(prefixCode);
        }
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        byte[] previous = str.getBytes(StandardCharsets.UTF_8);
        System.out.println("原有长度:" + previous.length + ",压缩后长度：" + result.length);
        System.out.println(Arrays.toString(previous));
        System.out.println(Arrays.toString(result));
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
            result.add(new Node(c, weight));
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
        private Character c;
        private Integer weight;
        private Node left;
        private Node right;

        public Node(int weight) {
            this.weight = weight;
        }

        public Node(char c, int weight) {
            this.c = c;
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

        public Character getC() {
            return c;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + c +
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
            return Objects.equals(c, node.c) && Objects.equals(weight, node.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, weight, left, right);
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
        public byte getPrefixCode(Node node) {
            List<Boolean> trace = new ArrayList<>();
            track(this, node, trace);

            StringBuilder sbd = new StringBuilder();
            for (int i = 0; i < trace.size(); i++) {
                sbd.append(trace.get(i) ? "1" : "0");
            }
            byte result = (byte) Integer.parseInt(sbd.toString(), 2);
            return result;
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
