import java.util.NoSuchElementException;

/**
 * @author @厌。 (QQ:669518272)
 * @version 1.0
 */

public class HybridDeque<E> {

    // ===== 双向链表节点 =====
    private static class Node<E> {
        E val;
        Node<E> prev, next;
        Node(E v) { this.val = v; }
    }

    // ===== 前半段：双向链表（带哨兵）=====
    private final Node<E> head = new Node<>(null); // sentinel
    private final Node<E> tail = new Node<>(null); // sentinel
    private int frontSize = 0;

    // ===== 后半段：数组（已实现，你只需调用）=====
    private final Object[] arr;
    private int backSize = 0;

    public HybridDeque(int arrayCapacity) {
        if (arrayCapacity <= 0) throw new IllegalArgumentException();
        this.arr = new Object[arrayCapacity];
        head.next = tail;
        tail.prev = head;
    }

    public int size() { return frontSize + backSize; }
    public boolean isEmpty() { return size() == 0; }

    // ========= 需要你实现：对外操作 =========

    public void addFirst(E x) {
        // TODO: 插到链表头，然后 rebalance
    }

    public void addLast(E x) {
        // TODO: 插到数组尾，然后 rebalance
    }

    public E removeFirst() {
        // TODO: 空抛异常；优先删链表头，否则删数组头；rebalance
        return null;
    }

    public E removeLast() {
        // TODO: 空抛异常；优先删数组尾，否则删链表尾；rebalance
        return null;
    }

    public E peekFirst() {
        // TODO: 空返回 null；优先看链表头，否则看数组头
        return null;
    }

    public E peekLast() {
        // TODO: 空返回 null；优先看数组尾，否则看链表尾
        return null;
    }

    // ========= 需要你实现：rebalance =========
    private void rebalance() {
        // TODO: 保证 |frontSize - backSize| <= 1
        // frontSize > backSize + 1: move one from list tail -> array head
        // backSize > frontSize + 1: move one from array head -> list tail
    }

    // ========= 需要你实现：链表 4 个基本操作 =========
    private void listAddFirst(E x) { /* TODO */ }
    private void listAddLast(E x) { /* TODO */ }
    private E listRemoveFirst() { /* TODO */ return null; }
    private E listRemoveLast() { /* TODO */ return null; }

    // ========= 数组段操作：已实现（普通连续数组） =========
    private void arrayAddFirst(E x) {
        if (backSize == arr.length) throw new IllegalStateException("array part full");
        for (int i = backSize - 1; i >= 0; i--) arr[i + 1] = arr[i];
        arr[0] = x;
        backSize++;
    }

    private void arrayAddLast(E x) {
        if (backSize == arr.length) throw new IllegalStateException("array part full");
        arr[backSize++] = x;
    }

    @SuppressWarnings("unchecked")
    private E arrayRemoveFirst() {
        E v = (E) arr[0];
        for (int i = 1; i < backSize; i++) arr[i - 1] = arr[i];
        arr[backSize - 1] = null;
        backSize--;
        return v;
    }

    @SuppressWarnings("unchecked")
    private E arrayRemoveLast() {
        int idx = backSize - 1;
        E v = (E) arr[idx];
        arr[idx] = null;
        backSize--;
        return v;
    }

    @SuppressWarnings("unchecked")
    private E arrayGet(int i) {
        return (E) arr[i];
    }
}
