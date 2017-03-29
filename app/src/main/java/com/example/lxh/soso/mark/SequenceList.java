package com.example.lxh.soso.mark;

import java.util.Arrays;

/**
 * Created by lxh on 2017/3/28.
 * 顺序存储的List集合,原理就是数组的操作
 */

public class SequenceList<T> {
    private final int DEFAULT_SIZE = 16;

    private int capacity;

    private int size = 0;

    private Object[] mElementData;

    public SequenceList() {
        capacity = DEFAULT_SIZE;
        mElementData = new Object[capacity];
    }

    public SequenceList(T element) {
        this();
        mElementData[0] = element;
        size++;
    }

    public SequenceList(T element, int initSize) {
        capacity = 1;
        while (capacity < initSize) {
            capacity <<= 1;
        }
        mElementData = new Object[capacity];
        mElementData[0] = element;
        size++;
    }

    public int getSize() {
        return size;
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (mElementData[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) mElementData[index];
    }


    public void insert(T element, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(size + 1);
        System.arraycopy(mElementData, index, mElementData, index + 1, size - index);
        mElementData[index] = element;
        size++;
    }

    public void add(T element) {
        insert(element, size);
    }


    private void ensureCapacity(int minCapacity) {
        if (capacity < minCapacity) {
            capacity <<= 1;
        }
        mElementData = Arrays.copyOf(mElementData, capacity);
    }

    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = (T) mElementData[index];
        int value = size - index - 1;
        if (value > 0) {
            System.arraycopy(mElementData, index + 1, mElementData, index, size - index - 1);
        }
        mElementData[--size] = null;
        return oldValue;
    }

    public void remove() {
        delete(size - 1);
    }

    public void clear() {
        Arrays.fill(mElementData, null);
        size = 0;
    }

    @Override
    public String toString() {
        String str = "";
        for (Object e : mElementData) {
            str += e;
        }
        return str;
    }
}
