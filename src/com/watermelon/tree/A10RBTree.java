package com.watermelon.tree;

import java.util.*;

/**
 * 红黑树
 */
public class A10RBTree {
    public static void main(String[] args) {

        RBNode<String, String> node0 = new RBNode("000", "000", null);
        node0.color = RBTree.BLACK;

        RBNode<String, String> node3 = new RBNode("003", "003", null);
        node3.color = RBTree.RED;
        node3.parent = node0;

        RBNode<String, String> node9 = new RBNode("009", "009", null);
        node9.color = RBTree.BLACK;
        node9.parent = node0;

        node0.left = node3;
        node0.right = node9;

        RBNode<String, String> node2 = new RBNode("002", "002", null);
        node2.color = RBTree.BLACK;
        node2.parent = node3;
        node3.left = node2;

        RBTree tree = new RBTree();
        tree.root = node0;

        A10TreeOperation.show(tree.getRoot());

        tree.remove("009");
        A10TreeOperation.show(tree.getRoot());

//        testInsertOpt();
//        RBTree<String, Object> rbt = new RBTree<>();
//        List<Integer> nums = Arrays.asList(4, 2, 6, 1, 3, 5, 9, 7, 11, 8, 10, 12);
//        nums.forEach(num -> {
//            if (num < 10) {
//                rbt.put("00" + num, null);
//            } else if (num < 100) {
//                rbt.put("0" + num, null);
//            }
//        });
//        rbt.remove("002");
//        rbt.remove("005");
//        A10TreeOperation.show(rbt.getRoot());
//        System.out.println("-----------------------------------------------------------");
//        rbt.remove("001");
//        A10TreeOperation.show(rbt.getRoot());

    }


    /**
     * 测试插入操作
     */
    public static void testInsertOpt() {
        Scanner scanner = new Scanner(System.in);
        RBTree<String, Object> rbt = new RBTree<>();

        while (true) {
            System.out.println("请输入指令:");
            System.out.println("1. 增加节点");
            System.out.println("2. 删除节点");
            String command = scanner.next();
            String key = null;
            switch (command) {
                case "1":
                    System.out.println("请输入插入的节点内容");
                    key = scanner.next();
                    System.out.println();
                    //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
                    if (key.length() == 1) {
                        key = "00" + key;
                    } else if (key.length() == 2) {
                        key = "0" + key;
                    }
                    rbt.put(key, null);
                    System.out.println("--------------------------------------------------------------");
                    A10TreeOperation.show(rbt.getRoot());
                    System.out.println("--------------------------------------------------------------");
                    break;
                case "2":
                    if (rbt.root == null) {
                        System.out.println("节点为空");
                        break;
                    }
                    System.out.println("请输入删除的节点内容");
                    key = scanner.next();
                    System.out.println();
                    //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
                    if (key.length() == 1) {
                        key = "00" + key;
                    } else if (key.length() == 2) {
                        key = "0" + key;
                    }
                    rbt.remove(key);
                    System.out.println("--------------------------------------------------------------");
                    A10TreeOperation.show(rbt.getRoot());
                    System.out.println("--------------------------------------------------------------");
                    break;
                default:
                    break;
            }


        }
    }

    public static class RBTree<K extends Comparable<K>, V> {
        private RBNode root;
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        /**
         * 根据key从红黑树中删除对应节点，返回被删除的节点
         *
         * @param key
         * @return 被删除的节点
         */
        public RBNode<K, V> remove(K key) {
            if (key == null) {
                return null;
            }
            RBNode<K, V> node = getNode(key);
            if (node == null) {
                return null;
            }
            deleteNode(node);
            return node;
        }

        /**
         * 从红黑树中删除指定节点
         *
         * @param node
         */
        private void deleteNode(RBNode<K, V> node) {
            if (node.left != null && node.right != null) {
                RBNode<K, V> successor = successor(node);
                node.key = successor.key;
                node.value = successor.value;
                node = successor;
                /**
                 * 如果进入了这个方法，那node一定不可能有两个子节点了
                 */
            }

            //处理叶子节点和叶子节点上一层
            RBNode<K, V> replaceNode = node.left != null ? node.left : node.right;
            if (replaceNode != null) {
                /**
                 * 叶子结点上一层
                 * 仅有一个子节点的节点(3节点，上黑下红)
                 * 1. 当前节点有右子节点(右子树)
                 * 2. 当前节点有左子节点(左子节点可能还会有右子树，但是不可能有左子树了)
                 */
                RBNode<K, V> parent = node.parent;
                if (parent.right == node) {
                    //有右子字节
                    parent.right = replaceNode;
                } else {
                    //有左子节点
                    parent.left = replaceNode;
                }
                replaceNode.parent = parent;

                node.left = node.right = node.parent = null;
                if (node.color == BLACK) {
                    //替代节点一定是红色
                    fixAfterDeletion(replaceNode);
                }
            } else if (node.parent == null) {
                //根节点 ，代表整棵树只有一个节点，就是根节点
                root = null;
            } else {
                //叶子节点
                if (node.color == BLACK) {
                    fixAfterDeletion(node);
                }
                if (node.parent != null) {
                    //这里还要判断node是否是根节点的原因，是因为上面调整完，当前节点有可能变成根节点
                    //叶子节点
                    if (node == node.parent.left) {
                        node.parent.left = null;
                    } else {
                        node.parent.right = null;
                    }
                    node.parent = null;
                }
            }
        }

        /**
         * 删除时调整红黑平衡
         *
         * @param x
         */
        private void fixAfterDeletion(RBNode<K, V> x) {
            while (x != root && colorOf(x) == BLACK) {
                if (x == leftOf(parentOf(x))) {
                    //删除的是父节点的左节点
                    RBNode sib = rightOf(parentOf(x));
                    //查看兄弟节点是否为红色
                    if (colorOf(sib) == RED) {
                        setColor(sib, BLACK);
                        //变色旋转，获取真正的兄弟节点
                        setColor(parentOf(x), RED);
                        leftRotate(parentOf(x));
                        sib = rightOf(parentOf(x));
                    }
                    //此时兄弟节点为黑色

                    if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                        //兄弟没有红孩子作为替补
                        //TODO
                        setColor(sib, RED);
                        x = parentOf(x);
                    } else {
                        //兄弟节点有红孩子作为替补
                        if (colorOf(rightOf(sib)) == BLACK) {
                            //兄弟有左红孩子，则旋转为右孩子作为真正的替补兄弟
                            setColor(leftOf(sib), BLACK);
                            setColor(sib, RED);
                            rightOf(sib);
                            sib = rightOf(parentOf(x));
                        }
                        //当前兄弟有右红孩子作为替补
                        setColor(sib, colorOf(parentOf(x)));
                        setColor(parentOf(x), BLACK);
                        setColor(rightOf(sib), BLACK);
                        leftRotate(parentOf(x));
                        x = root;
                    }
                } else {
                    //删除的是父节点的右节点
                    RBNode sib = leftOf(parentOf(x));//兄弟节点
                    if (colorOf(sib) == RED) {
                        setColor(sib, BLACK);
                        setColor(parentOf(x), RED);
                        rightRotate(parentOf(x));
                        sib = leftOf(parentOf(x));
                    }
                    //判断兄弟是否有孩子可借
                    if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                        // TODO 无孩子可借
                        setColor(sib, RED);
                        x = parentOf(x);
                    } else {
                        //判断是否有左孩子
                        if (colorOf(leftOf(sib)) == BLACK) {
                            //左孩子为空，右孩子旋转
                            setColor(rightOf(sib), BLACK);
                            setColor(sib, RED);
                            leftRotate(sib);
                            sib = leftOf(parentOf(x));
                        }
                        setColor(sib, colorOf(parentOf(x)));
                        setColor(parentOf(x), BLACK);
                        setColor(leftOf(sib), BLACK);
                        rightRotate(parentOf(x));
                        x = root;
                    }
                }
            }
            //根节点或者替补红节点
            setColor(x, BLACK);
        }

        /**
         * 根据key找到对应节点
         *
         * @param key
         * @return
         */
        private RBNode<K, V> getNode(K key) {
            RBNode current = this.root;
            while (current != null) {
                int cmp = current.getKey().compareTo(key);
                if (cmp == 0) {
                    return current;
                } else if (cmp > 0) {
                    if (current.left == null) {
                        return null;
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        return null;
                    }
                    current = current.right;
                }
            }
            return null;
        }


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
