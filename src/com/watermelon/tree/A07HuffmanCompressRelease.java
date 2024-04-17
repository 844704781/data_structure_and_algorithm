package com.watermelon.tree;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * huffman压缩和解压
 */
public class A07HuffmanCompressRelease {

    private static Map<Integer, String> huffmanTable = new HashMap<>();

    public static void main(String[] args) {
        String str = "i like like like java do you like a java";
//        String str = "我是中国人，我爱中国";
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        System.out.println("原始数据字节:" + Arrays.toString(strBytes));
        System.out.println("原始数据字节长度:" + strBytes.length);
        byte[] zipData = zip(strBytes);
        System.out.println("压缩后数据字节:" + Arrays.toString(zipData));
        System.out.println("压缩后数据字节长度:" + zipData.length);
        System.out.println("压缩率:" + (double) (strBytes.length - zipData.length) / strBytes.length);
        byte[] unzipData = unzip(zipData);
        System.out.println("解压后数据字节:" + Arrays.toString(unzipData));
        System.out.println("解压后数据字节长度:" + unzipData.length);
        System.out.println("原数据为:" + new String(unzipData, StandardCharsets.UTF_8));
    }


    /**
     * huffman压缩
     *
     * @param data
     * @return
     */
    private static byte[] zip(byte[] data) {
        //1. 将原始数据的字符转成huffman树，构建huffman树
        huffmanTable = toHuffmanTable(data);
        System.out.println("huffman表:" + huffmanTable);
        //2. 根据huffman表和原数据构建huffman转义后的二进制字符串
        String huffmanStr = toHuffmanCode(huffmanTable, data);
        System.out.println("huffman字符串:" + huffmanStr);
        //3. 将二进制字符串按照8位转成bytes返回
        return toHuffmanBytes(huffmanStr);
    }

    /**
     * 将huffman编码字符串转成huffman编码的字节数组
     *
     * @param huffmanStr
     * @return
     */
    private static byte[] toHuffmanBytes(String huffmanStr) {
        List<String> binaryList = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < huffmanStr.length(); i++) {
            temp.append(huffmanStr.charAt(i));
            if (temp.length() == 8 || i == huffmanStr.length() - 1) {
                binaryList.add(temp.toString());
                temp.setLength(0);
            }

        }
        byte[] result = new byte[binaryList.size()];
        for (int i = 0; i < binaryList.size(); i++) {
            String binary = binaryList.get(i);
            Integer value = Integer.valueOf(binary, 2);
            result[i] = value.byteValue();
        }
        return result;
    }

    /**
     * 根据huffman表，将原字节数组转成huffman编码字符串
     *
     * @param huffmanTable
     * @param data
     * @return
     */
    private static String toHuffmanCode(Map<Integer, String> huffmanTable, byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte temp : data) {
            String huffmanCode = huffmanTable.get(Integer.valueOf(temp));
            sb.append(huffmanCode);
        }
        return sb.toString();
    }


    /**
     * 从huffman数中获取huffman值
     *
     * @param node
     * @param huffmanValue
     */
    private static boolean trace(Node currentNode, Node node, List<Boolean> huffmanValue) {

        if (currentNode.equals(node)) {
            return true;
        }
        if (currentNode.left != null) {
            huffmanValue.add(false);
            if (trace(currentNode.left, node, huffmanValue)) {
                return true;
            }
            huffmanValue.remove(huffmanValue.size() - 1);
        }
        if (currentNode.right != null) {
            huffmanValue.add(true);
            if (trace(currentNode.right, node, huffmanValue)) {
                return true;
            }
            huffmanValue.remove(huffmanValue.size() - 1);
        }
        return false;
    }

    /**
     * 构建huffman表
     *
     * @param data
     * @return
     */
    private static Map<Integer, String> toHuffmanTable(byte[] data) {
        Map<Byte, Integer> keyCountMap = new HashMap<>();
        for (byte key : data) {
            Integer count = keyCountMap.get(key);
            if (count == null) {
                keyCountMap.put(key, 1);
            } else {
                keyCountMap.put(key, count + 1);
            }
        }

        List<Node> nodes = keyCountMap.entrySet().stream()
                .map(entry ->
                        new Node(entry.getKey().intValue(),
                                (char) entry.getKey().byteValue(),
                                entry.getValue())).collect(Collectors.toList()
                );
        //构建huffman树
        Node huffmanTree = beTree(new ArrayList<>(nodes));
//        huffmanTree.preOrder();
        /**
         * 构建huffman表
         */
        Map<Integer, String> result = new HashMap<>();
        for (Node node : nodes) {
            List<Boolean> huffmanValue = new ArrayList<>();
            trace(huffmanTree, node, huffmanValue);
            StringBuilder sb = new StringBuilder();
            huffmanValue.forEach(value -> {
                sb.append(value == true ? "1" : "0");
            });
            result.put(node.data, sb.toString());
        }
        return result;
    }

    private static Node beTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            Node parent = new Node(null, null, left.weight + right.weight);
            nodes.remove(0);
            nodes.remove(0);
            parent.setLeft(left);
            parent.setRight(right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * huffman解压
     *
     * @param data
     * @return
     */
    private static byte[] unzip(byte[] data) {
        /**
         * 1.将字节data还原成哈夫曼编码字节
         * 2.根据huffman编码表，将哈夫曼字节还原成原始字节
         */
        StringBuilder huffmanStr = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            boolean flag = b > 0 ? true : false;
            if (i == data.length - 1) {
                flag = false;
            }

            String binary = to8BinaryString(flag, b);
            huffmanStr.append(binary);
        }
        System.out.println("huffman字符串:" + huffmanStr);
        Map<String, Integer> huffmanValueKey = huffmanTable.entrySet().stream().collect(Collectors.toMap(x -> x.getValue(), x -> x.getKey()));
        StringBuilder huffmanCode = new StringBuilder();

        List<Integer> sourceKeys = new ArrayList<>();
        for (char bit : huffmanStr.toString().toCharArray()) {
            huffmanCode.append(bit);
            Integer sourceKey = huffmanValueKey.get(huffmanCode.toString());
            if (sourceKey != null) {
                sourceKeys.add(sourceKey);
                huffmanCode.setLength(0);
            }
        }
        byte[] result = new byte[sourceKeys.size()];
        for (int i = 0; i < sourceKeys.size(); i++) {
            Integer sourceKey = sourceKeys.get(i);
            result[i] = sourceKey.byteValue();
        }
        return result;
    }

    /**
     * 获取byte的8位二进制
     *
     * @param flag 是否需要补位
     * @param data 十进制数
     * @return
     */
    private static String to8BinaryString(boolean flag, int data) {

        if (flag) {
            data |= 256;
        }
        String result = Integer.toBinaryString(data);

        if (result.length() > 8) {
            return result.substring(result.length() - 8);
        }
        return result;
    }

    public static class Node implements Comparable<Node> {
        private Integer data;
        private Character c;
        private Integer weight;
        private Node left;
        private Node right;

        public Node(Integer data, Character c, Integer weight) {
            this.data = data;
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

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", c=" + c +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(data, node.data) && Objects.equals(c, node.c) && Objects.equals(weight, node.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, c, weight);
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }

        public void preOrder() {
            this.preOrder(this);
        }

        private void preOrder(Node node) {
            System.out.println(node);
            if (node.left != null) {
                preOrder(node.left);
            }
            if (node.right != null) {
                preOrder(node.right);
            }
        }
    }
}
