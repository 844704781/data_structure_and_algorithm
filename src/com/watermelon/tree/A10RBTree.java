package com.watermelon.tree;

import java.util.*;

/**
 * 红黑树
 */
public class A10RBTree {
    public static void main(String[] args) {
        RBTree<Integer, Integer> tree = new RBTree();
        List<Integer> numbers = Arrays.asList(5, 3, 8, 0, 2, 4, 6, 9, 7, 11);

        numbers.stream()
                .forEach(i -> {
                            Integer number = i.intValue();
                            tree.put(number, number);
                        }
                );
        RBNode node = tree.successor(tree.root.left.right.right);
        System.out.println(node);
    }


    /**
     * 测试插入操作
     */
    public static void testInsertOpt() {
        Scanner scanner = new Scanner(System.in);
        RBTree<String, Object> rbt = new RBTree<>();
        while (true) {
            System.out.println("请输入你要插入的节点:");
            String key = scanner.next();
            System.out.println();
            //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
            if (key.length() == 1) {
                key = "00" + key;
            } else if (key.length() == 2) {
                key = "0" + key;
            }
            rbt.put(key, null);
            A10TreeOperation.show(rbt.getRoot());
        }
    }

    public static class RBTree<K extends Comparable<K>, V> {
        private RBNode root;
        public static final boolean BLACK = true;
        public static final boolean RED = false;


        /**
         * 左旋
         */
        public void leftRotate(RBNode p) {
            if (p != null) {
                //1. 确定p.right为支点r
                RBNode r = p.right;

                //2. 处理p的右指针(冲突的左孩变友右孩)
                p.right = r.left;
                if (r.left != null) {
                    r.left.parent = p;
                }
                //3. 处理r的父节点
                r.parent = p.parent;
                if (p.parent == null) {
                    root = r;
                } else if (p.parent.left == p) {
                    p.parent.left = r;
                } else {
                    p.parent.right = r;
                }
                //4. 处理r的左指针
                r.left = p;
                p.parent = r;
            }
        }

        /**
         * 右旋
         */
        public void rightRotate(RBNode p) {
            if (p != null) {
                //1. 确定p.left为支点l
                RBNode l = p.left;
                //2. 处理p的左节点
                p.left = l.right;
                if (l.right != null) {
                    l.right.parent = p;
                }
                //3. 处理l的父节点
                l.parent = p.parent;
                if (p.parent == null) {
                    root = l;
                } else if (p.parent.left == p) {
                    p.parent.left = l;
                } else {
                    p.parent.right = l;
                }
                //4.处理l的右节点
                l.right = p;
                p.parent = l;
            }
        }

        /**
         * 新增节点
         *
         * @param key
         * @param value
         */
        public void put(K key, V value) {
            RBNode t = this.root;

            if (key == null) {
                throw new NullPointerException("Empty key");
            }
            if (value == null) {
                value = value == null ? (V) key : value;
            }
            /**
             * 判断是否加入的是根节点
             */
            if (t == null) {
                this.root = new RBNode(key, value, null);
                return;
            }
            /**
             * 判断加入的节点的位置
             * 如果有一个已知节点key相同，则替换value
             */
            RBNode parent;
            int cpr;
            do {
                parent = t;
                cpr = key.compareTo((K) t.key);
                if (cpr < 0) {
                    t = t.left;
                } else if (cpr > 0) {
                    t = t.right;
                } else {
                    //存在key
                    t.setValue(value);
                    return;
                }
            } while (t != null);
            RBNode<K, V> e = new RBNode<>(key, value, parent);
            if (cpr < 0) {
                parent.left = e;
            } else {
                parent.right = e;
            }
            fixAfterPut(e);
        }

        /**
         * 获取当前节点的前驱节点
         *
         * @return
         */
        private RBNode<K, V> predecessor(RBNode<K, V> node) {
            if (node == null) {
                return null;
            }
            if (node.left != null) {
                RBNode<K, V> childrenNode = node.left;
                while (childrenNode.right != null) {
                    childrenNode = childrenNode.right;
                }
                return childrenNode;
            } else {
                /**
                 * 	在当前节点的直系祖先节点中，找到离自
                 * 	己最近且其右子节点是当前节点的祖先节点的祖先节点，这个祖先节点就是当前节点的前驱节点。
                 */
                RBNode<K, V> parentNode = node.parent;
                RBNode<K, V> currentNode = node;
                while (parentNode != null && parentNode.left == currentNode) {
                    currentNode = parentNode;
                    parentNode = currentNode.parent;
                }
                return parentNode;
            }
        }

        /**
         * 获取当前节点的后继节点
         *
         * @return
         */
        private RBNode<K, V> successor(RBNode<K, V> node) {
            if (node == null) {
                return null;
            }
            if (node.right != null) {
                RBNode<K, V> currentNode = node.right;
                while (currentNode.left != null) {
                    currentNode = currentNode.left;
                }
                return currentNode;
            } else {
                RBNode<K, V> parentNode = node.parent;
                RBNode<K, V> currentNode = node;
                while (parentNode != null && parentNode.right == currentNode) {
                    currentNode = parentNode;
                    parentNode = currentNode.parent;
                }
                return parentNode;
            }

        }

        /**
         * 按照红黑树规则调整
         * 包括旋转和变色
         *
         * @param x 要调整的节点
         */
        private void fixAfterPut(RBNode<K, V> x) {
            x.color = RED;
            /**
             * 当前节点为空，当前节点是根节点，当前节点的父节点为黑色时不需要调整
             */
            while (x != null && x != root && parentOf(x).color == RED) {
                /**
                 * 当前节点是红色,爷爷节点肯定存在
                 */
                if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                    //当前父亲是左节点
                    RBNode y = rightOf(parentOf(parentOf(x)));//叔叔节点
                    if (colorOf(y) == RED) {
                        //4节点
                        setColor(parentOf(x), BLACK);
                        setColor(y, BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        //爷爷是红的,爷爷的爸爸也可能是红的，则要向上递归操作
                        x = parentOf(parentOf(x));
                    } else {
                        //3结点
                        if (parentOf(x).right == x) {
                            x = parentOf(x);
                            leftRotate(x);
                        }
                        setColor(parentOf(x), BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        rightRotate(parentOf(parentOf(x)));
                    }
                } else {
                    //当前父亲是右节点
                    RBNode y = leftOf(parentOf(parentOf(x)));//叔叔节点
                    if (colorOf(y) == RED) {
                        //4节点
                        setColor(parentOf(x), BLACK);
                        setColor(y, BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        //爷爷是红的,爷爷的爸爸也可能是红的，则要向上递归操作
                        x = parentOf(parentOf(x));
                    } else {
                        //3结点
                        if (parentOf(x).left == x) {
                            x = parentOf(x);
                            rightRotate(x);
                        }

                        setColor(parentOf(x), BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        leftRotate(parentOf(parentOf(x)));
                    }
                }
            }
            /**
             * 到这里就到根节点了，根节点一定是黑的
             */
            this.root.color = BLACK;
        }

        private RBNode parentOf(RBNode<K, V> x) {
            if (x == null) {
                return null;
            }
            return x.getParent();
        }

        private RBNode leftOf(RBNode<K, V> x) {
            if (x == null) {
                return null;
            }
            return x.getLeft();
        }

        private RBNode rightOf(RBNode<K, V> x) {
            if (x == null) {
                return null;
            }
            return x.getRight();
        }

        private boolean colorOf(RBNode<K, V> x) {
            if (x == null) {
                return BLACK;
            }
            return x.color;
        }

        private void setColor(RBNode<K, V> x, boolean color) {
            if (x == null) {
                return;
            }
            x.setColor(color);
        }

        public RBNode getRoot() {
            return root;
        }
    }

    public static class RBNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;

        public RBNode(K key, V value, RBNode parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.color = RBTree.BLACK;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RBNode<?, ?> rbNode = (RBNode<?, ?>) o;
            return color == rbNode.color && Objects.equals(key, rbNode.key) && Objects.equals(value, rbNode.value) && Objects.equals(parent, rbNode.parent) && Objects.equals(left, rbNode.left) && Objects.equals(right, rbNode.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value, parent, left, right, color);
        }

        @Override
        public String toString() {
            return "RBNode{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
