package com.example.lxh.soso.mark;

/**
 * Created by lxh on 2017/3/28.
 * 链式存储的集合，原理保存下一个数据的引用
 */

public class LinkedList<T> {

    class Node {
        T mElement;
        Node mNext;

        public Node() {
        }

        public Node(T element, Node next) {
            mElement = element;
            mNext = next;
        }
    }

    private int size = 0;

    private Node mFirstNode;

    private Node mEndNode;

    public LinkedList() {
    }

    public LinkedList(T element) {
        mFirstNode = new Node(element, null);
        mEndNode = mFirstNode;
        size++;
    }

    public int getSize() {
        return size;
    }

    public void insert(T element, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 0) {
            mFirstNode = new Node(element, null);
            mEndNode = mFirstNode;
        } else {
            if (index == 0) {
                mFirstNode = new Node(element, mFirstNode);
            } else {
                Node preNode = getNodeByIndex(index - 1);
                Node curNode = new Node(element, preNode.mNext);
                if (preNode.mNext == null) {
                    mEndNode = curNode;
                }
                preNode.mNext = curNode;
            }
        }
        size++;
    }

    public void add(T element) {
        insert(element, size);
    }


    public Node getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = mFirstNode;
        for (int i = 0; i < size; i++, current = current.mNext) {
            if (index == i) {
                return current;
            }

        }
        return null;
    }

    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node preNode = getNodeByIndex(index - 1);
        Node curNode;
        if (preNode == null) {
            curNode = mFirstNode;
            preNode = mFirstNode;
        } else {
            curNode = preNode.mNext;
        }

        preNode.mNext = curNode.mNext;
        curNode.mNext = null;
        size--;
        return curNode.mElement;
    }

    @Override
    public String toString() {
        String str = "";
        Node curNode = mFirstNode;
        while (curNode != null) {
            str += curNode.mElement;
            curNode = curNode.mNext;
        }
        return str;
    }
}
