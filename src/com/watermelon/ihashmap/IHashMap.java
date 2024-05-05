package com.watermelon.ihashmap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class IHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap
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


//    final HashMap.Node<K,V>[] resize() {
//        /**
//         * 记录旧的数组 oldTab
//         * 记录旧的数组容量 oldCap 为当前数组长度
//         * 记录旧的临界值 oldThr 为当前临界值threshold
//         * 创建新的数组容量newCap,新的临界值newThr
//         */
//        HashMap.Node<K,V>[] oldTab = table;
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
//            }
//            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
//                    oldCap >= DEFAULT_INITIAL_CAPACITY)
//            /**
//             * 总结: 符合扩容条件，则 数组容量*2 ，临界值*2
//             * 当 旧的容量oldCap乘以2 小于 最大容量2的30次方 且 旧的容量oldCap大于等于16时
//             *   新的容量 = 旧的容量 乘以 2
//             *   新的临界值 = 旧的临界值 乘以 2
//             */
//                newThr = oldThr << 1; // double threshold
//        }
//        else if (oldThr > 0) // initial capacity was placed in threshold
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
//            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
//        }
//        if (newThr == 0) {
//            /**
//             * newThr==0则代表，用户自己设置了初始容量
//             * 其中负载因子(默认0.75)用户也可以自己自定义了
//             * 计算newThr为 数组真实容量newCap的newThr(默认0.75)倍
//             */
//            float ft = (float)newCap * loadFactor;
//            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
//                    (int)ft : Integer.MAX_VALUE);
//        }
//        /**
//         * 到了这里，则计算出了当前的节点数量临界值，赋值到全局变量threshold中
//         */
//        threshold = newThr;
//        /**
//         * 创建新的节点数组newTab
//         * 将其先关联到全局数组变量table中
//         */
//        @SuppressWarnings({"rawtypes","unchecked"})
//        HashMap.Node<K,V>[] newTab = (HashMap.Node<K,V>[])new HashMap.Node[newCap];
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
//                HashMap.Node<K,V> e;
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
//                        ((HashMap.TreeNode<K,V>)e).split(this, newTab, j, oldCap);
//                    else { // preserve order
//                        /**
//                         * 当前节点是链表头结点
//                         * 定义
//                         *
//                         * next为当前节点的下一个节点
//                         */
//                        HashMap.Node<K,V> loHead = null, loTail = null;
//                        HashMap.Node<K,V> hiHead = null, hiTail = null;
//                        HashMap.Node<K,V> next;
//                        do {
//
//                            next = e.next;
//                            if ((e.hash & oldCap) == 0) {
//                                /**
//                                 * 如果当前节点在旧数组的 0 位置
//                                 */
//                                if (loTail == null)
//                                    loHead = e;
//                                else
//                                    loTail.next = e;
//                                loTail = e;
//                            }
//                            else {
//                                if (hiTail == null)
//                                    hiHead = e;
//                                else
//                                    hiTail.next = e;
//                                hiTail = e;
//                            }
//                        } while ((e = next) != null);
//                        if (loTail != null) {
//                            loTail.next = null;
//                            newTab[j] = loHead;
//                        }
//                        if (hiTail != null) {
//                            hiTail.next = null;
//                            newTab[j + oldCap] = hiHead;
//                        }
//                    }
//                }
//            }
//        }
//        return newTab;
//    }

}
