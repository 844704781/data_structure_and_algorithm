package com.watermelon.data_structure;

import java.util.Stack;

/**
 * 单链表
 */
public class A04LinkedNode {

    public static void main(String[] args) {
        LinkedList list1 = new LinkedList();
        list1.add(new HeroNode(2, "张三"));
        list1.add(new HeroNode(1, "李四"));
        list1.add(new HeroNode(3, "王老五"));
        LinkedList list2 = new LinkedList();
        list2.add(new HeroNode(5, "张三"));
        list2.add(new HeroNode(9, "李四"));
        list2.add(new HeroNode(4, "王老五"));
        LinkedList list4 = new LinkedList();
        list4.add(new HeroNode(8, "xx"));
        LinkedList list3 = LinkedList.merge(list1, list2, list4);
        list3.show();
//        list.show();
//        System.out.println("--------------");
//        list.update(1, "小明");
//        list.show();
//        System.out.println("--------------");
////        list.delete(3);
////        list.delete(2);
////        list.delete(1);
//        list.show();
////        System.out.println(LinkedList.getLength(list.getHead()));
//        System.out.println(LinkedList.findLastIndexNode(list.getHead(), 3));
//
//        list.show();
//        LinkedList.revert(list.getHead());
//        list.show();
//        System.out.println("------反转打印--------");
//        LinkedList.revertPrint(list.getHead());
    }

    public static class LinkedList {
        private HeroNode head = new HeroNode(0, "");

        public HeroNode getHead() {
            return head;
        }

        /**
         * 单链表方法---尾部添加节点
         * 使用while循环遍历，然后通过一个临时变脸表示当前遍历的节点，如果当前节点的下一个节点是空的，代表遍历到了尾部，则将要添加的节点放到当前节点的下一个节点中
         *
         * @param node
         */
        public void add(HeroNode node) {
            HeroNode temp = head;
            while (true) {
                if (temp.getNext() == null) {
                    temp.setNext(node);
                    break;
                } else {
                    temp = temp.getNext();
                }
            }
        }

        /**
         * 插入排序
         * 主要是要找到要插入的位置，找到位置之后，将旧节点分离，然后旧节点的前一个节点指向当前插入的节点，当前插入的节点的后一个节点指向旧节点的后一个节点
         *
         * @param node
         */
        public void addByOrder(HeroNode node) {
            if (node == null) {
                throw new RuntimeException("数据为空");
            }
            HeroNode currentNode = head;
            boolean exist = false;
            while (true) {
                //找到了尾结点
                if (currentNode.getNext() == null) {
                    break;
                }
                if (currentNode.getNo() == node.getNo()) {
                    exist = true;
                    break;
                }
                //当前节点的下一个节点如果比插入的元素更大，则代表找到了要插入的位置
                if (currentNode.getNext().getNo() > node.getNo()) {
                    break;
                }

                currentNode = currentNode.getNext();
            }

            if (exist) {
                throw new RuntimeException(String.format("No:%s节点已经存在", currentNode.getNo()));
            }
            //新节点元素.next = 当前节点的下一个元素
            //当前元素的下一个节点为当前节点
            node.setNext(currentNode.getNext());
            currentNode.setNext(node);
        }

        /**
         * 修改节点
         *
         * @param
         */
        public void update(int no, String name) {
            HeroNode temp = head;
            boolean found = false;
            while (true) {
                if (temp == null) {
                    break;
                }
                if (temp.getNo() == no) {
                    found = true;
                    break;
                }
                temp = temp.getNext();
            }
            if (!found) {
                throw new RuntimeException("元素未找到");
            }
            temp.setName(name);

        }

        /**
         * 删除节点
         *
         * @param no
         */
        public void delete(int no) {
            HeroNode temp = head;
            while (true) {
                if (temp.getNext() == null) {
                    break;
                }
                if (temp.getNext().getNo() == no) {
                    temp.setNext(temp.getNext().getNext());
                    break;
                }
                temp = temp.getNext();
            }
        }

        /**
         * 求链表中有效节点个数(不包括头结点)
         *
         * @return
         */
        public static int getLength(HeroNode head) {
            HeroNode temp = head;
            int length = 0;
            while (true) {

                temp = temp.getNext();
                if (temp != null) {
                    length++;
                } else {
                    break;
                }
            }
            return length;
        }

        /**
         * 查找单链表中的倒数第k个节点
         *
         * @param head
         * @param lastIndex
         * @return
         */
        public static HeroNode findLastIndexNode(HeroNode head, int lastIndex) {
            int length = getLength(head);
            if (lastIndex > length) {
                throw new RuntimeException("Invalid lastIndex");
            }
            if (lastIndex == 0) {
                throw new RuntimeException("Invalid lastIndex");
            }
            int index = length - lastIndex + 1;
            int start = 0;
            HeroNode temp = head;

            while (true) {
                if (start == index) {
                    return temp;
                }
                temp = temp.getNext();
                start++;
            }
        }

        /**
         * 将单链表反转
         * 创建存放反转的链表节点revert_node
         * 将数据反转后得到revert_node,将其放到head.next中即可
         *
         * @param head
         */
        public static void revert(HeroNode head) {
            //只有一个头节点，或者只有一个节点则无需反转

            if (head.getNext() == null || head.getNext().getNext() == null) {
                return;
            }

            HeroNode revertNode = new HeroNode(0, ""); //反转链表的头结点
            HeroNode current = head.getNext(); // 当前节点
            HeroNode next = null; //当前节点的下一个节点

            while (current != null) {
                /**
                 * 将当前指针指向的节点的下一个节点保存next
                 * 将当前指针指向的节点插入到新节点的头部
                 * 将指针指向刚刚保存的next
                 */
                next = current.getNext();

                current.setNext(revertNode.getNext());
                revertNode.setNext(current);
                current = next;
            }

            head.setNext(revertNode.getNext());
        }

        /**
         * 将单链表反转打印，不破坏原来的结构
         */
        public static void revertPrint(HeroNode node) {
            if (node.getNext() == null) {
                System.out.println("链表为空");
                return;
            }
            Stack<HeroNode> stack = new Stack<HeroNode>();
            //遍历链表，将节点压入栈中，先入后出
            HeroNode current = node.getNext();
            while (current != null) {
                stack.add(current);
                current = current.getNext();
            }

            //出栈，出一个元素打印一个元素
            while (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
        }

        /**
         * 合并多个链表，合并之后链表有序
         *
         * @param nodes
         * @reurn
         */
        public static LinkedList merge(LinkedList... nodes) {
            LinkedList result = new LinkedList();
            for (LinkedList list : nodes) {

                /**
                 * 遍历链表中的每一个元素，都有序插入到result节点中
                 */
                HeroNode head = list.getHead();
                if (getLength(head) == 0) {
                    continue;
                }
                HeroNode temp = head.getNext();
                while (temp != null) {
                    HeroNode copyNode = new HeroNode(temp.getNo(), temp.getName());
                    result.addByOrder(copyNode);
                    temp = temp.getNext();
                }
            }
            return result;
        }

        public void show() {
            HeroNode currentNode = head;

            while ((currentNode = currentNode.getNext()) != null) {
                System.out.println(currentNode);
            }
        }
    }


    public static class HeroNode {
        /**
         * 编号，比大小，用于排序
         */
        private Integer no;

        /**
         * 数据名称
         */
        private String name;

        /**
         * 指针
         */
        private HeroNode next;

        public HeroNode(Integer no, String name) {
            this.no = no;
            this.name = name;
        }

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
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

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
