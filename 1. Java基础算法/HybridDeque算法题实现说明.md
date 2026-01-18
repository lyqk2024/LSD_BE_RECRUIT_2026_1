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

