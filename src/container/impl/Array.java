package container.impl;

import container.Growable;
import container.abstraction.ArrayContainer;

import java.util.*;
import java.util.function.Consumer;

public class Array<E> implements ArrayContainer<E>, Growable {

    protected int size;
    protected Object[] elements;
    public static final int DEFAULT_CAPACITY = 10;
    public static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE - 10;

    public Array() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public Array(int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException("capacity must greater or equal to 0");
        }
        elements = new Object[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        boundaryCheck(index);
        return (E)elements[index];
    }

    @Override
    public void add(int index, E data) {
        if(index > size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }

        if(size == elements.length) {
            grow(size + 1);
        }
        shift(index, index + 1);
        size++;
        elements[index] = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E replace(int index, E data) {
        boundaryCheck(index);
        Object temp = elements[index];
        elements[index] = data;
        return (E)temp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        boundaryCheck(index);

        Object data = elements[index];
        shift(index + 1, index);
        size--;
        return (E) data;
    }

    @Override
    public int search(E data, int start, int end) {
        rangeCheck(start, end);
        for (int i = start; i < end; i++) {
            if(Objects.equals(data, elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void reverse() {
        Object temp = null;
        for(int i = 0, j = size - 1; i < j; i++, j--) {
            temp = elements[i];
            elements[j] = elements[i];
            elements[j] = temp;
        }
    }

    @Override
    public ArrayContainer<E> slice(int start, int end) {
        rangeCheck(start, end);
        Array<E> array = new Array<>(end - start);
        array.size = end - start;
        System.arraycopy(elements, start, array.elements, 0, end - start);
        return array;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(;size > 0; size--) {
            elements[size - 1] = null;
        }
    }

    @Override
    public boolean add(E data) {
        if(size == elements.length) {
            grow(size + 1);
        }
        elements[size++] = data;
        return true;
    }

    @Override
    public boolean remove(E data) {
        int index = search(data, 0, size);
        if(index > -1 ) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(E data) {
        return search(data, 0, size) > -1;
    }

    @Override
    public ListIterator<E> iterator() {
        return new ArrayIterator(0);
    }

    public ListIterator<E> iterator(int index) {
        if(index > size || index < 0) {
            throw new IllegalArgumentException("index should in bound of [0, size]");
        }
        return new ArrayIterator(index);
    }

    @Override
    public E at(int index) {
        return get(index);
    }

    @Override
    public void grow(int min) {
        if(min < 0) {
            throw new OutOfMemoryError();
        }
        if(min < size) {
            throw new IllegalArgumentException("min: " + min + ", must larger than array size: " + size);
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
        if(min < size) {
            throw new IllegalArgumentException("min: " + min + ", must larger than array size: " + size);
        }

        elements = Arrays.copyOf(elements, min);
    }

    protected void boundaryCheck(int index) {
        if(index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    protected void shift(int from, int to) {
        assert to - from + size <= elements.length;
        if(from == to) {
            return;
        }
        System.arraycopy(elements, from, elements, to, size - from);
        if(from > to) {
            int movedStep = from - to;
            for(int i = size -1; i > size - movedStep - 1; i--) {
                elements[i] = null;
            }
        }
    }


    protected void rangeCheck(int start, int end) {
        if(start < 0 || start > size) {
            throw new IllegalArgumentException("start must between [0, " + size + "]");
        }

        if(end < 0 || end > size) {
            throw new IllegalArgumentException("end must between [0, " + size + "]");
        }

        if(start < end) {
            throw new IllegalArgumentException("start must less than end, start: " + start + ", end: " + end);
        }
    }

    private class ArrayIterator implements ListIterator<E> {

        private int cursor;
        private int previous;

        public ArrayIterator(int cursor) {
            this.cursor = cursor;
            previous = cursor - 1;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if(hasNext()) {
                E data = (E)elements[cursor];
                cursor++;
                previous = cursor - 1;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return previous > -1;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E previous() {
            if(hasPrevious()) {
                E data = (E) elements[previous];
                cursor = previous;
                previous--;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            assert previous == cursor - 1;
            return previous;
        }

        @Override
        public void remove() {
            if(hasPrevious()) {
                Array.this.remove(previous);
                cursor--;
                previous = cursor - 1;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void set(E e) {
            if(hasPrevious()) {
                elements[previous] = e;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void add(E e) {
            Array.this.add(cursor, e);
        }
    }
}
