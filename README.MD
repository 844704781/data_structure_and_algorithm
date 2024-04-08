# 查找算法

## 线性查找

### Java

```java
    /**
     * 线性查找
     * @param data 要查找的数据
     * @param target 要查找的值
     * @return 要查找数据的索引值
     */
    @Override
    int search(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return i;
            }
        }
        return -1;
    }

    @Override
    int[] searchAll(int[] data, int target) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                list.add(i);
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
```



### Python

```python

```

## 二分查找法

### 思想

> int mid = left + (right - left) *1/2

递归条件：

* 如果左指针大于右指针，则停止递归。

* 如果找到了，则停止递归。

递归方式：

* 如果 target 小于当前位置的值，则代表没有找到，向左边找，注意右指针为 mid - 1。
* 如果 target 大于当前位置的值，则代表没有找到，向右边找，注意左指针为 mid +1。

### Java

```java
    /**
     * 二分查找法:
     * 递归条件:
     *  递归条件：如果左指针大于右指针，则停止递归
     *  如果找到了，则停止递归
     * 递归方式：
     *  如果target小于当前位置的值，则代表没有找到，向左边找，注意右指针为mid-1
     *  如果target大于当前位置的值，则代表没有找到，向左边找，注意左指针为mid+1
     *
     * @param data
     * @param target
     * @param left
     * @param right
     * @return
     */
    private static int binarySearch(int[] data, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midValue = data[mid];
        if (target == midValue) {
            return mid;
        } else if (target < midValue) {
            return binarySearch(data, target, left, mid - 1);
        } else {
            return binarySearch(data, target, mid + 1, right);
        }
    }

    /**
     * 二分查找法
     * 递归查找:
     * 递归条件：如果左指针大于右指针，则停止递归
     * 如果找到了，则再找找该位置左边有没有一样的值，右边有没有一样的值，将他们的位置都放入结果中返回结束递归
     * 递归方式：
     * 如果target小于当前位置的值，则代表没有找到，向左边找，注意右指针为mid-1
     * 如果target大于当前位置的值，则代表没有找到，向左边找，注意左指针为mid+1
     *
     * @param data   原序列
     * @param target 目标数据
     * @param left   左指针
     * @param right  右指针
     * @return 所有找到的索引位置
     */
    private static List<Integer> binarySearchAll(int[] data, int target, int left, int right) {
        List result = new ArrayList();
        if (left > right) {
            return result;
        }
        int mid = (left + right) / 2;
        int midValue = data[mid];
        if (target == midValue) {
            //找到了，向左右继续查找是否有一样的
            int l = mid - 1;
            while (l >= 0) {
                if (data[l] != target) {
                    break;
                }
                result.add(l--);
            }
            result.add(mid);
            int r = mid + 1;
            while (r <= data.length - 1) {
                if (data[r] != target) {
                    break;
                }
                result.add(r++);
            }
            return result;
        } else if (target < midValue) {
            return binarySearchAll(data, target, left, mid - 1);
        } else {
            return binarySearchAll(data, target, mid + 1, right);
        }
    }
```



### Python

```python
```



## 插值查找算法

### 思想

**插值查找算法**

>int mid = left + (right - left) * (target - data[left]) / (data[right] - data[left])

- 插值查找算法的思路与二分查找法一致。
- 区别在于：
  - 二分查找的 `mid` 为 `left + 1/2 * 序列长度`。
  - 插值查找的 `mid` 为 `left + 查找的值的范围在序列索引长度中的比例`。

注意:

插值查找，因为mid是根据查找值计算出来的，如果查找值用户输入的值不在要查找的序列范围中，则查找时可能会越界，所以每次查找时要判断当前值在查找的序列之中



### Java

```java
 @Override
    public int search(int[] data, int target) {

        return interpolationSearch(data, 0, data.length - 1, target);
    }

    private int interpolationSearch(int[] data, int left, int right, int target) {

        if (left > right
                || target < data[left]
                || target > data[right]) {
            return -1;
        }
        int mid = left + (right - left) * (target - data[left]) / (data[right] - data[left]);
        if (target == data[mid]) {
            return mid;
        } else if (target > data[mid]) {
            return interpolationSearch(data, mid + 1, right, target);
        } else {
            return interpolationSearch(data, left, mid - 1, target);
        }
    }


    @Override
    public int[] searchAll(int[] data, int target) {
        List<Integer> list = interpolationSearchAll(data, 0, data.length - 1, target);
        int result[] = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private List<Integer> interpolationSearchAll(int[] data, int left, int right, int target) {

        if (left > right
                || target < data[left]
                || target > data[right]) {
            return new ArrayList<>();
        }
        int mid = left + (right - left) * (target - data[left]) / (data[right] - data[left]);
        if (target == data[mid]) {
            List<Integer> result = new ArrayList();
            int midLeft = mid - 1;
            while (midLeft >= 0 && data[midLeft] == target) {
                result.add(midLeft--);
            }
            result.add(mid);
            int midRight = mid + 1;
            while (midRight <= data.length - 1 && data[midRight] == target) {
                result.add(midRight++);
            }
            return result;
        } else if (target > data[mid]) {
            return interpolationSearchAll(data, mid + 1, right, target);
        } else {
            return interpolationSearchAll(data, left, mid - 1, target);
        }
    }
```



### Python

```python
```





### 总结

其实二分查找和插值查找的本质思想是一样的。区别在于二分查找确定 `mid` 位置在序列的一半，而插值查找的基本原理是根据目标元素与数组首尾元素的差值比例，估计目标元素在数组中的大致位置，并根据该估计来动态调整搜索范围。



## 斐波那契查找

### 思想


- 插值查找算法的思路与二分查找法一致。
- 区别在于：
  - 二分查找的 `mid` 为 `left + 1/2 * 序列长度`。
  - 斐波那契查找的 `mid` 为 `left + 当前序列长度的斐波那契分割点-1`。

因为斐波那契数列前后值的比例符合黄金分割点

所以我们每次查找序列时，都要获取到当前序列的长度，然后去斐波那契数列中找到和这个长度接近的值，然后去找这个值前面的值作为黄金分割点即可



### 递归法

#### 思路

**递归算法思想:**

- 插值查找算法的思路与二分查找法一致。
- 区别在于：
  - 二分查找的 `mid` 为 `left + 1/2 * 序列长度`。
  - 斐波那契查找的 `mid` 为 `left + 当前序列长度的斐波那契分割点-1`。

因为斐波那契数列前后值的比例符合黄金分割点

所以我们每次查找序列时，都要获取到当前序列的长度，然后去斐波那契数列中找到和这个长度接近的值，然后去找这个值前面的值作为黄金分割点即可



#### Java

```java
    /**
     * fibonacci查找
     * 按照斐波那契数列分割
     *
     * @param data   要查找的数据
     * @param target 要查找的值
     * @return
     */
    @Override
    public int search(int[] data, int target) {
        //构建斐波那契数列
        int[] f = fibs(data.length);
        return fibonacciSearch(data, f, 0, data.length - 1, target);
    }


    private int fibonacciSearch(int[] data, int[] f, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
				
      	//长度对应的斐波那契值前面的数就是黄金分割点
        int fibIndex = 0;
        int currentLength = right - left + 1;
        for (int i = 0; i < f.length; i++) {
            if (f[i] >= currentLength) {
                fibIndex = i;
                break;
            }
        }
        int fibStep = 1;//黄金分割点
        if (fibIndex != 0) {
            fibStep = f[fibIndex - 1];
        }
        int mid = left + fibStep - 1;
        if (target == data[mid]) {
            return mid;
        } else if (target < data[mid]) {
            return fibonacciSearch(data, f, left, mid - 1, target);
        } else {
            return fibonacciSearch(data, f, mid + 1, right, target);
        }
    }

    /**
     * 根据序列长度构建斐波那契数列
     *
     * @param size
     * @return
     */
    private static int[] fibs(int size) {
        List<Integer> fibs = new ArrayList<Integer>() {{
            this.add(0, 1);
            this.add(1, 1);
        }};
        int i = 2;
        while (true) {
            fibs.add(i, fibs.get(i - 1) + fibs.get(i - 2));
            if (fibs.get(i) >= size) {
                break;
            }
            i = i + 1;
        }
        int result[] = new int[fibs.size()];
        for (int j = 0; j < fibs.size(); j++) {
            result[j] = fibs.get(j);
        }
        return result;
    }

```



#### Python

```python
```



### 遍历法

#### 思路

思路和递归法一致

将是将递归循环调用的逻辑放到while循环中做，当left>right时停止循环，找到了也停止循环

循环结束还没找到，则返回-1

#### Java

```java
    /**
     * fibonacci查找
     * 按照斐波那契数列分割
     *
     * @param data   要查找的数据
     * @param target 要查找的值
     * @return
     */
    @Override
    public int search(int[] data, int target) {
        //构建斐波那契数列
        int[] f = fibs(data.length);
        return fibonacciSearch(data, f, target);
    }


    private int fibonacciSearch(int[] data, int[] f, int target) {
        int left = 0;
        int right = data.length - 1;


        while (left <= right) {
            /**
             * 计算斐波那契偏移量：
             * 拿到当前长度对应的斐波那契值，当前值的前面那个值就是斐波那契偏移量
             * 拿到斐波那契偏移量，当前left+偏移量-1就是当前mid
             */
            int fibIndex = 0;
            int currentLength = right - left + 1;
            for (int i = 0; i < f.length; i++) {
                if (f[i] >= currentLength) {
                    fibIndex = i;
                    break;
                }
            }
            int fibStep = 1; //斐波那契偏移量
            if (fibIndex != 0) {
                fibStep = f[fibIndex - 1];
            }
            int mid = left + fibStep - 1;
            if (target == data[mid]) {
                return mid;
            } else if (target < data[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    @Override
    int[] searchAll(int[] data, int target) {
        return new int[0];
    }


    /**
     * 根据序列长度构建斐波那契数列
     *
     * @param size
     * @return
     */
    private static int[] fibs(int size) {
        List<Integer> fibs = new ArrayList<Integer>() {{
            this.add(0, 1);
            this.add(1, 1);
        }};
        int i = 2;
        while (true) {
            fibs.add(i, fibs.get(i - 1) + fibs.get(i - 2));
            if (fibs.get(i) >= size) {
                break;
            }
            i = i + 1;
        }
        int result[] = new int[fibs.size()];
        for (int j = 0; j < fibs.size(); j++) {
            result[j] = fibs.get(j);
        }
        return result;
    }
```



#### Python

```python

```

