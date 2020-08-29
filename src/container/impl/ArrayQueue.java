package container.impl;

import container.Growable;
import container.abstraction.QueueContainer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayQueue<E> implements QueueContainer<E>, Growable {

    protected int front;
    protected int rear;
    protected Object[] elements;
    public final static int INITIAL_CAPACITY = 10;
    public final static int MAXIMUM_CAPACITY = Integer.MAX_VALUE - 10;

    public ArrayQueue() {
        elements = new Object[INITIAL_CAPACITY];
    }

    public ArrayQueue(int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException("capacity must >= 0");
        }
        elements = new Object[capacity];
    }

    @Override
    public void offer(E data) {
        if(isFull()) {
            grow(elements.length + 1);
        }
        elements[rear++] = data;
        if(rear >= elements.length) {
            rear -= elements.length;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E take() {
        if(isEmpty()) {
            return null;
        }

        E data = (E)elements[front++];
        if(front >= elements.length) {
            front -= elements.length;
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if(isEmpty()) {
            return null;
        }
        return (E)elements[front];
    }

    @Override
    public int size() {
        int size = rear - front;
        if(size < 0) {
            size += elements.length;
        }
        return size;
    }

    @Override
    public void clear() {
        while (rear != front) {
            elements[front] = null;
            front = front + 1;
            if(front >= elements.length) {
                front -= elements.length;
            }
        }
    }

    @Override
    public boolean add(E data) {
        offer(data);
        return true;
    }

    @Override
    public boolean remove(E data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(E data) {
        for (int i = front;
             i < rear;
             i = i + 1 > elements.length ? i - elements.length : i) {
            if(Objects.equals(data, elements[i])) {
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
            throw new IllegalArgumentException("min: " + min + ", must larger than queue size: " + size());
        }
        int oldCapacity = elements.length;
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

        if(front > rear) {
            int moveSize = oldCapacity - front;
            int newFront = capacity - moveSize;
            System.arraycopy(elements, front, elements, newFront, moveSize);
            // gc
            for(int i = front; i < newFront; i++){
                elements[i] = null;
            }
            front = newFront;
        }
    }

    @Override
    public void trim(int min) {
        if(min < size() + 1) {
            throw new IllegalArgumentException("min must lager or equal than min size");
        }

        Object[] newArray = new Object[size()];
        for (int i = front, j = 0;
             i < rear;
             i = ++i > elements.length ? i - elements.length : i) {
            newArray[j] = elements[i];
        }
        elements = newArray;
    }


    @Override
    public Iterator<E> iterator() {
        return new ArrayQueueIterator();
    }

    public boolean isFull() {
        // (rear + 1) % length == front
        int i = rear + 1;
        if(i >= elements.length) {
            i -= elements.length;
        }
        return i == front;
    }

    private class ArrayQueueIterator implements Iterator<E> {
        private int cursor = front;

        @Override
        public boolean hasNext() {
            return cursor < rear;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if(hasNext()) {
                E data = (E) elements[cursor];
                cursor = ++cursor > elements.length ? cursor - elements.length : cursor;
                return data;
            }
            throw new NoSuchElementException();
        }
    }
}
