package container.impl;

import container.Growable;
import container.abstraction.StackContainer;

import java.util.*;

public class ArrayStack<E> implements StackContainer<E>, Growable {

    protected int top = -1;
    protected Object[] elements;
    public static final int INITIAL_CAPACITY = 10;
    public static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE - 10;

    public ArrayStack() {
        elements = new Object[INITIAL_CAPACITY];
    }

    public ArrayStack(int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException("capacity must >= 0");
        }
        elements = new Object[capacity];
    }

    @Override
    public void push(E data) {
        if(top == elements.length) {
            grow(top + 1);
        }
        elements[++top] = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if(isEmpty()) {
            return null;
        }
        return (E)elements[top];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E pop() {
        if(isEmpty()) {
            return null;
        }
        return (E)elements[top--];
    }


    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public void clear() {
        while (top > 0) {
            elements[top--] = null;
        }
    }

    @Override
    public boolean add(E data) {
        push(data);
        return true;
    }

    @Override
    public boolean remove(E data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(E data) {
        for (int i = 0; i < top + 1; i++) {
            if(Objects.equals(elements[top], data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void grow(int min) {
        if(min < 0) {
            throw new OutOfMemoryError();
        }
        if(min < size()) {
            throw new IllegalArgumentException("min: " + min + ", must larger than stack size: " + size());
        }

        int capacity = (elements.length >> 1) + elements.length;
        if(capacity < min) {
            capacity = min;
        } else {
            if(capacity > MAXIMUM_CAPACITY) {
                if(min > MAXIMUM_CAPACITY) {
                    capacity = Integer.MAX_VALUE;
                } else {
                    capacity = min;
                }
            }
        }
        elements = Arrays.copyOf(elements, capacity);
    }

    @Override
    public void trim(int min) {
        if(min < size()) {
            throw new IllegalArgumentException("min: " + min + ", must larger than stack size: " + size());
        }

        elements = Arrays.copyOf(elements, min);
    }


    @Override
    public Iterator<E> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E> {
        private int cursor = top;

        @Override
        public boolean hasNext() {
            return cursor > -1;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return (E)elements[cursor--];
        }
    }
}
