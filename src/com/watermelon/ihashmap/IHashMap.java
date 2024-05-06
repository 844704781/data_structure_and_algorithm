package com.watermelon.ihashmap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class IHashMap {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>(8);
        map.put("43K", "4");
        map.put("a01", "2");
        map.put("42j", "1");
        map.put("42j", "5");
        System.out.println(map);

        Map<String, String> newMap = Collections.synchronizedMap(map);

    }
//
//    /**
//     * 将一个map放到一个已经存在的HashMap中，如果没有，则构造一个HashMap
//     *
//     * @param m
//     * @param evict
//     */
//    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
//        /**
//         * 定义要加入map的数量s
//         */
//        int s = m.size();
//        if (s > 0) {
//            if (table == null) { // pre-size
//                /**
//                 * 如果已经存在的map数组为空，则已经存在的map还是个新的map
//                 * 则要计算出新map数组的容量
//                 * 新数组容量的计算分为两步
//                 *   1. 未来数据容量 = 原本map的数据容量/0.75 + 1
//                 *   2. 数组容量为 比 未来数据容量 大的最小的2的倍数
//                 * 为什么这个容量要加1，考虑原本map节点数量可能就处于临界值，如果这里不加1，
//                 * 这种情况，只要一使用通过这个方法创建的map就会发生扩容操作。
//                 * 比如 s=6
//                 *   在不加1的情况 得到的数组容量为8 (只要使用了就会触发扩容)
//                 *   在加1之后，得到的数组容量为16 (有足够的空间存储数据，不触发扩容)
//                 */
//                float ft = ((float) s / loadFactor) + 1.0F;
//                int t = ((ft < (float) MAXIMUM_CAPACITY) ?
//                        (int) ft : MAXIMUM_CAPACITY);
//                if (t > threshold)
//                    threshold = tableSizeFor(t);
//            } else if (s > threshold)
//            /**
//             * 已经存在的数组不为空，证明已经存在的map里是有数据的
//             * 有数据则会有临界值，如果要加入map数量比当前临界值大，则要扩容
//             * 然后遍历要加入的map中的数据，将其都加入到当前map中
//             */
//                resize();
//            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
//                K key = e.getKey();
//                V value = e.getValue();
//                putVal(hash(key), key, value, false, evict);
//            }
//        }
//    }

    /**
     * 获取key的hash值
     * 如果key的hash_code小于2的16次方，那这里就直接返回原本的hash_code
     * 如果key的hash_code大于等于2的16次方，那这里就返回约等于原本hash_code + hash_code无符号右移16位的值
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

//
//    /**
//     * 在容器中查找数据
//     * @param hash key对应的hash值
//     * @param key key
//     * @return 返回找到的节点，如果为null，则是没找到
//     */
//    final HashMap.Node<K,V> getNode(int hash, Object key) {
//        /**
//         * 定义tab指向数组
//         * 定义first 当前key所在索引位置的头结点
//         * 定义e 表示当前遍历的节点
//         * 定义n 表示数组容量
//         * 定义k 当前对比节点的key
//         */
//        HashMap.Node<K,V>[] tab; HashMap.Node<K,V> first, e; int n; K k;
//        if ((tab = table) != null && (n = tab.length) > 0 &&
//                (first = tab[(n - 1) & hash]) != null) {
//            /**
//             * 在数组中有数据且 当前 索引位置有数据时
//             */
//            if (first.hash == hash && // always check first node
//                    ((k = first.key) == key || (key != null && key.equals(k))))
//            /**
//             * 如果头结点就是要找的节点，直接返回头结点
//             */
//                return first;
//            if ((e = first.next) != null) {
//                /**
//                 * 头结点不是要找的节点，则是复杂数据结构
//                 */
//                if (first instanceof HashMap.TreeNode)
//                /**
//                 * 如果头结点是红黑树，则调用红黑树的查找方法进行查找
//                 */
//                    return ((HashMap.TreeNode<K,V>)first).getTreeNode(hash, key);
//                do {
//                    /**
//                     * 否则是链表，遍历链表，找到key一样的节点直接返回
//                     */
//                    if (e.hash == hash &&
//                            ((k = e.key) == key || (key != null && key.equals(k))))
//
//                        return e;
//                } while ((e = e.next) != null);
//            }
//        }
//        return null;
//    }


//    /**
//     * 删除节点
//     * 思路总结:
//     * 1. 定位到key所在的数组索引位置
//     * 2. 在数组索引位置的数据结构中找到key一样的节点node
//     * 3. 然后再对应的数据结构中将这个节点删除，并返回被删除的节点
//     * @param hash 节点hash值
//     * @param key 节点的key
//     * @param value 节点的value可以为null
//     * @param matchValue 只在节点value和传入的value相同时才删除？
//     * @param movable 红黑树相关参数
//     * @return
//     */
//    final HashMap.Node<K,V> removeNode(int hash, Object key, Object value,
//                                       boolean matchValue, boolean movable) {
//        /**
//         * 定义局部变量tab指向数组
//         * 定义p
//         *  如果这个位置只有一个节点，则p是头结点
//         *  如果这个位置是红黑树，则p是红黑树头结点
//         *  如果这个位置是链表，则p是当前要删除节点的上一个节点
//         * 定义n为数组容量
//         * 定义index为数组索引
//         */
//        HashMap.Node<K,V>[] tab; HashMap.Node<K,V> p; int n, index;
//        if ((tab = table) != null && (n = tab.length) > 0 &&
//                (p = tab[index = (n - 1) & hash]) != null) {
//            /**
//             * 当前数组中有桶且 当前hash计算的索引位置有数据
//             * 定义node 为当前要找的key一样的节点
//             * 定义e 为当前遍历到的节点
//             * 定义当前节点的key为k
//             * 定义当前节点的value为v
//             */
//            HashMap.Node<K,V> node = null, e; K k; V v;
//            if (p.hash == hash &&
//                    ((k = p.key) == key || (key != null && key.equals(k))))
//            /**
//             * 当前桶的头结点就是要找的节点
//             */
//                node = p;
//            else if ((e = p.next) != null) {
//                /**
//                 * 头节点不是要找的节点，则看头结点是否是红黑树
//                 */
//                if (p instanceof HashMap.TreeNode)
//                /**
//                 * 是红黑树，则在红黑树中找
//                 */
//                    node = ((HashMap.TreeNode<K,V>)p).getTreeNode(hash, key);
//                else {
//                    /**
//                     * 是链表，则从头到尾遍历链表，找是否有hash一样且key一样的节点
//                     */
//                    do {
//                        if (e.hash == hash &&
//                                ((k = e.key) == key ||
//                                        (key != null && key.equals(k)))) {
//                            node = e;
//                            break;
//                        }
//                        p = e;
//                    } while ((e = e.next) != null);
//                }
//            }
//            if (node != null && (!matchValue || (v = node.value) == value ||
//                    (value != null && value.equals(v)))) {
//                /**
//                 * 如果找到了key一样的节点node
//                 *   如果要匹配值，则要判断找到节点的value是否一样，一样才开始正式删除操作
//                 *   否则不用匹配值，直接进行下面的操作
//                 *
//                 * 如果节点是红黑树，则调用红黑树的节点删除功能
//                 * 如果节点是头结点，则直接数组索引位置房当前节点的下一个节点
//                 * 否则是链表非头结点，则将上一个节点.next指向要删除节点.next
//                 */
//                if (node instanceof HashMap.TreeNode)
//                    ((HashMap.TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
//                else if (node == p)
//                    tab[index] = node.next;
//                else
//                    p.next = node.next;
//                /**
//                 * 修改次数自增
//                 * 节点数量减1
//                 * 删除后的回调
//                 * 返回被删除的节点
//                 */
//                ++modCount;
//                --size;
//                afterNodeRemoval(node);
//                return node;
//            }
//        }
//        /**
//         * 当前容器中没数据，返回null
//         */
//        return null;
//    }

    /**
     * 扩容函数
     * @return
     */
//    final HashMap.Node<K, V>[] resize() {
//        /**
//         * 记录旧的数组 oldTab
//         * 记录旧的数组容量 oldCap 为当前数组长度
//         * 记录旧的临界值 oldThr 为当前临界值threshold
//         * 创建新的数组容量newCap,新的临界值newThr
//         */
//        HashMap.Node<K, V>[] oldTab = table;
//        int oldCap = (oldTab == null) ? 0 : oldTab.length;
//        int oldThr = threshold;
//        int newCap, newThr = 0;
//        /**
//         * 总结:
//         * 计算扩容后的数组容量newCap和节点临界值threshold
//         */
//        if (oldCap > 0) {
//            /**
//             * 处理不是第一次put的逻辑：
//             * 当数组有容量时，证明不是第一次put，之前已经创建好了数组
//             */
//            if (oldCap >= MAXIMUM_CAPACITY) {
//                /**
//                 * 当旧的容量大于等于2的30次方时:
//                 * 设置临界值threshold为int的最大值->2的31次方减1
//                 * 且直接返回，不再扩容
//                 */
//                threshold = Integer.MAX_VALUE;
//                return oldTab;
//            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
//                    oldCap >= DEFAULT_INITIAL_CAPACITY)
//            /**
//             * 总结: 符合扩容条件，则 数组容量*2 ，临界值*2
//             * 当 旧的容量oldCap乘以2 小于 最大容量2的30次方 且 旧的容量oldCap大于等于16时
//             *   新的容量 = 旧的容量 乘以 2
//             *   新的临界值 = 旧的临界值 乘以 2
//             */
//                newThr = oldThr << 1; // double threshold
//        } else if (oldThr > 0) // initial capacity was placed in threshold
//        /**
//         * 总结: 用户构造HashMap时设置了初始容量，这里获取真正的数组容量
//         * 处理第一次put，且HashMap创建时手动设置了初始容量initialCapacity，初始负载因子loadFactor的情况
//         *  将要创建的数组容量newCap设置为比initialCapacity大的2的最小倍数
//         *  此时的newThr依旧没有设置
//         */
//            newCap = oldThr;
//        else {               // zero initial threshold signifies using defaults
//            /**
//             * 处理第一次put,且HashMap在创建时没有手动设置初始容量
//             *  将要创建的数组容量newCap设置为16
//             *  将新的临界值newThr设置为 16*0.75 =12
//             */
//            newCap = DEFAULT_INITIAL_CAPACITY;
//            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
//        }
//        if (newThr == 0) {
//            /**
//             * newThr==0则代表，用户自己设置了初始容量
//             * 其中负载因子(默认0.75)用户也可以自己自定义了
//             * 计算newThr为 数组真实容量newCap的newThr(默认0.75)倍
//             */
//            float ft = (float) newCap * loadFactor;
//            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
//                    (int) ft : Integer.MAX_VALUE);
//        }
//        /**
//         * 到了这里，则计算出了当前的节点数量临界值，赋值到全局变量threshold中
//         */
//        threshold = newThr;
//        /**
//         * 创建新的节点数组newTab
//         * 将其先关联到全局数组变量table中
//         */
//        @SuppressWarnings({"rawtypes", "unchecked"})
//        HashMap.Node<K, V>[] newTab = (HashMap.Node<K, V>[]) new HashMap.Node[newCap];
//        table = newTab;
//        if (oldTab != null) {
//            /**
//             * 如果之前已经有数组了，这里就开始进行数据赋值操作
//             */
//            for (int j = 0; j < oldCap; ++j) {
//                /**
//                 * 遍历旧的数组
//                 * 将当前数组元素放到变量e中
//                 */
//                HashMap.Node<K, V> e;
//                if ((e = oldTab[j]) != null) {
//                    /**
//                     * 当e有数据时
//                     * 将旧的oldTab的当前位置设置为null
//                     */
//                    oldTab[j] = null;
//                    if (e.next == null)
//                    /**
//                     * 如果当前数组只有一个节点
//                     * 重新计算索引值将当前节点对象放到新数组的新索引位置
//                     */
//                        newTab[e.hash & (newCap - 1)] = e;
//                    else if (e instanceof HashMap.TreeNode)
//                    /**
//                     * 如果当前节点是红黑树节点，则对当前红黑树的节点打散切均匀的分布在多个红黑树中
//                     */
//                        ((HashMap.TreeNode<K, V>) e).split(this, newTab, j, oldCap);
//                    else { // preserve order
//                        /**
//                         * 当前节点是链表的头结点
//                         *
//                         * 定义:
//                         * loHead：低位链表头结点，loTail：低位链当前结点
//                         * hiHead：高位链表头结点，loTail：高位链当前结点
//                         * next为当前节点的下一个节点
//                         */
//                        HagshMap.Node<K, V> loHead = null, loTail = null;
//                        HashMap.Node<K, V> hiHead = null, hiTail = null;
//                        HashMap.Node<K, V> next;
//                        do {
//                            /**
//                             * 遍历整条链表
//                             * 将高位为0的放在低位链表
//                             * 将高位为1的放在高位链表
//                             */
//                            next = e.next;
//                            if ((e.hash & oldCap) == 0) {
//                                /**
//                                 * 计算高位是不是0，组成一条新链表
//                                 */
//                                if (loTail == null)
//                                    loHead = e;
//                                else
//                                    loTail.next = e;
//                                loTail = e;
//                            } else {
//                                /**
//                                 * 高位不是0，组成一条新链表
//                                 */
//                                if (hiTail == null)
//                                    hiHead = e;
//                                else
//                                    hiTail.next = e;
//                                hiTail = e;
//                            }
//                        } while ((e = next) != null);
//                        if (loTail != null) {
//                            /**
//                             * 还是在原来索引位置的链表
//                             */
//                            loTail.next = null;
//                            newTab[j] = loHead;
//                        }
//                        if (hiTail != null) {
//                            /**
//                             * 第二条链表放在原来索引位置+原来数组容量
//                             */
//                            hiTail.next = null;
//                            newTab[j + oldCap] = hiHead;
//                        }
//                    }
//                }
//            }
//        }
//        /**
//         * 返回新链表
//         */
//        return newTab;
//    }

}
