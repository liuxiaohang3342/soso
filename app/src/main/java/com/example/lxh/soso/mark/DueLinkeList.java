package com.example.lxh.soso.mark;

/**
 * Created by lxh on 2017/3/28.
 * 链式存储的双向链表，原理保存前后数据的引用
 */

public class DueLinkeList<T> {

    class Node {
        T element;
        Node nextNode;
        Node preNode;

        public Node() {
        }

        public Node(T element, Node nextNode, Node preNode) {
            this.element = element;
            this.nextNode = nextNode;
            this.preNode = preNode;
        }
    }

    private Node mFirtNode;

    private Node mEndNode;

    private int size = 0;

    public DueLinkeList() {
    }

    public DueLinkeList(T element) {
        mFirtNode = new Node(element, null, null);
        mEndNode = mFirtNode;
    }

    public void insert(T element, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            mFirtNode = new Node(element, null, null);
            mEndNode = mFirtNode;
        } else {
            if (index == 0) {
                mFirtNode = new Node(element, mFirtNode, null);
            } else {
                Node preNode = getNodeByIndex(index - 1);
                Node curNode;
                if (preNode.nextNode == null) {
                    curNode = new Node(element, null, preNode);
                } else {
                    curNode = new Node(element, preNode.nextNode, preNode);
                }
                preNode.nextNode = curNode;
            }
        }
        size++;
    }

    public void add(T element) {
        insert(element, size);
    }


    public Node getNodeByIndex(int index) {
        Node curNode;
        if (index < size / 2) {
            curNode = mFirtNode;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    break;
                }
                curNode = curNode.nextNode;
            }
        } else {
            curNode = mEndNode;
            for (int i = size - 1; i >= index; i--) {
                if (i == index) {
                    break;
                }
                curNode = curNode.preNode;
            }
        }
        return curNode;
    }

    @Override
    public String toString() {
        String str = "";
        Node curNode = mFirtNode;
        while (curNode != null) {
            str += curNode.element;
            curNode = curNode.nextNode;
        }
        return str;
    }
}
