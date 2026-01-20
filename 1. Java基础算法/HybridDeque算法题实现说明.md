> 除特别表明已实现的函数，其余函数均需要你完成

## 双端队列整体需要实现的函数

- `addFirst(x)`：把元素 `x` 插入到队列最前端

- `addLast(x)`： 把元素 `x` 插入到队列最后端

- `removeFirst()`： 删除并返回队列最前端元素；若队列为空则抛 `NoSuchElementException`

- `removeLast()`：删除并返回队列最后端元素；若队列为空则抛 `NoSuchElementException`

> 提示：以上函数最后请调用 `rebalance()` 函数

- `peekFirst()`：返回队列最前端元素但不删除；若队列为空返回 `null`

- `peekLast()`：返回队列最后端元素但不删除；若队列为空返回 `null`

- `size()` ✅（已实现）：返回队列总元素个数

- `isEmpty()` ✅（已实现）：当 `size() == 0` 返回 `true`，否则返回 `false`

- `rebalance()`：

  - 目的：保持两部分大小尽量平衡，使 `|frontSize - backSize| <= 1`
    - 规则：
      - 若 `frontSize > backSize + 1`：把链表尾部元素搬到数组头部
      - 若 `backSize > frontSize + 1`：把数组头部元素搬到链表尾部

  - 搬运必须保持队列整体顺序不变

------

## 链表段需要实现的 4 个基础函数

- `listAddFirst(x)`：在链表头部插入新元素 `x`

- `listAddLast(x)`：在链表尾部插入新元素 `x`

- `listRemoveFirst()`：删除并返回链表首元素

- `listRemoveLast()`：删除并返回链表尾元素

------

## 数组段已实现的函数

- `arrayAddFirst(x)`✅（已实现） ：在数组段头部插入 `x`

- `arrayAddLast(x)` ✅（已实现）：在数组段尾部插入 `x`

- `arrayRemoveFirst()` ✅（已实现）：删除并返回数组段头元素 `arr[0]`

- `arrayRemoveLast()` ✅（已实现）：删除并返回数组段尾元素 `arr[backSize-1]`

- `arrayGet(i)` ✅（已实现）：返回数组段第 `i` 个元素（`0 <= i < backSize`），不修改结构

## (可选) 实现环形数组存储

> 提示：你可以增加一个数组指示字段 headIdx,指示环形数组头元素的下标

- 重写数组部分的所有函数
- 你可以自行增加任意工具方法, 但不允许修改已有方法签名



## 测试用例示例

- 正常用例

```java
HybridDeque<Integer> dq = new HybridDeque<>(4);

// 1) 初始：空
System.out.println(dq.size());      // 0
System.out.println(dq.peekFirst()); // null
System.out.println(dq.peekLast());  // null

// 2) 插入：混合 addFirst/addLast
dq.addFirst(2);  // [2]
dq.addFirst(1);  // [1,2]
dq.addLast(3);   // [1,2,3]
dq.addLast(4);   // [1,2,3,4]

// 3) 检查 peek
System.out.println(dq.peekFirst()); // 1
System.out.println(dq.peekLast());  // 4
System.out.println(dq.size());      // 4

// 4) 删除：两端各删一次
System.out.println(dq.removeFirst()); // 1  -> [2,3,4]
System.out.println(dq.removeLast());  // 4  -> [2,3]

// 5) 再插入
dq.addFirst(9);   // [9,2,3]
dq.addLast(10);   // [9,2,3,10]

// 6) 逐个弹出
System.out.println(dq.removeFirst()); // 9
System.out.println(dq.removeFirst()); // 2
System.out.println(dq.removeFirst()); // 3
System.out.println(dq.removeFirst()); // 10
System.out.println(dq.isEmpty());     // true
```

- 边界用例

```java
HybridDeque<Integer> dq = new HybridDeque<>(4);
try {
    dq.removeFirst();
    System.out.println("ERROR: should have thrown");
} catch (NoSuchElementException e) {
    System.out.println("OK removeFirst empty");
}
```

