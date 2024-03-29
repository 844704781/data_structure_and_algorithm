package com.watermelon.data_structure;

/**
 * 双链表
 */
public class A05DoubleLinkedNode {
    public static void main(String[] args) {
        DoubleLinkedList list01 = new DoubleLinkedList();
        list01.addByOrder(new HeroNode(1111, "A"));
        list01.addByOrder(new HeroNode(111, "A"));
        list01.addByOrder(new HeroNode(11, "A"));
        list01.addByOrder(new HeroNode(1, "A"));
        list01.addByOrder(new HeroNode(2, "A"));
        list01.addByOrder(new HeroNode(11111, "A"));
        list01.list();


    }

    public static class DoubleLinkedList {
        private HeroNode head = new HeroNode(0, "");


        public void add(HeroNode node) {
            /**
             * 1. 查找到最后一个节点current
             * 2. current.next = node node.pre=current
             */
            HeroNode current = head;

            while (true) {
                if (current.getNo() == node.getNo()) {
                    throw new RuntimeException("存在一样的no");
                }

                if (current.getNext() == null) {
                    current.setNext(node);
                    node.setPre(current);
                    break;
                }
                current = current.getNext();
            }
        }

        public void addByOrder(HeroNode node) {
            /**
             * 节点no一样，报错冲突
             * 找到比当前节点的下一个节点更大的节点，current.next.no>node.no
             * current.next.pre = node
             * node.next = current.next
             * node.pre = current
             * current.next = node
             *
             */
            HeroNode current = head;
            while (true) {
                if (current.getNext() == null
                        || current.getNext().getNo() > node.getNo()) {
                    break;
                }
                if (current.getNext().getNo() == node.getNo()) {
                    throw new RuntimeException("Conflict no");
                }

                current = current.getNext();
            }
            if (current.getNext() != null) {
                current.getNext().setPre(node);
            }
            node.setNext(current.getNext());
            current.setNext(node);
            node.setPre(current);
        }

        public void delete(int no) {
            /**
             * 遍历节点
             * 如果当前节点current的no与传入的no相同
             * 则 current.pre.next = current.next
             *   current.next.pre = current.pre
             */

            HeroNode current = head.getNext();
            while (current != null) {
                if (current.getNo() == no) {
                    current.getPre().setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPre(current.getPre());
                    }
                }
                current = current.getNext();
            }
        }

        public void update(int no, String name) {
            HeroNode current = head.getNext();
            while (current != null) {
                if (current.getNo() == no) {
                    current.setName(name);
                }
                current = current.getNext();
            }
        }

        public void list() {
            System.out.println("----------------向后遍历-----------------");
            HeroNode current = head.getNext();
            while (current != null) {
                System.out.println(current);
                if (current.getNext() == null) {
                    break;
                } else {
                    current = current.getNext();
                }
            }
            System.out.println("----------------向后遍历-----------------");
            System.out.println("-----------------向前遍历----------------");
            while (current != null) {
                System.out.println(current);
                if (current.getPre().getNo() == 0) {
                    break;
                } else {
                    current = current.getPre();
                }
            }
            System.out.println("------------------向前遍历---------------");
        }
    }

    public static class HeroNode {
        private int no;
        private String name;
        private HeroNode next;
        private HeroNode pre;

        public HeroNode(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public int getNo() {
            return no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HeroNode getNext() {
            return next;
        }

        public void setNext(HeroNode next) {
            this.next = next;
        }

        public HeroNode getPre() {
            return pre;
        }

        public void setPre(HeroNode pre) {
            this.pre = pre;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
