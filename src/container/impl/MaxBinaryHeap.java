package container.impl;

import container.Growable;
import container.abstraction.BinaryHeapContainer;
import util.Test;
import util.UnitTest;

import java.util.*;

public class MaxBinaryHeap<E> implements BinaryHeapContainer<E>, Growable {

    protected Object[] elements;
    protected int size;
    protected final Comparator<E> comparator;

    public static final int DEFAULT_CAPACITY = 10;
    public static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE - 10;

    public MaxBinaryHeap(Comparator<E> comparator) {
        Objects.requireNonNull(comparator);
        this.comparator = comparator;
        elements = new Object[DEFAULT_CAPACITY];
    }

    public MaxBinaryHeap(int capacity, Comparator<E> comparator) {
        if(capacity < 0) {
            throw new IllegalArgumentException("capacity must >= 0");
        }
        Objects.requireNonNull(comparator);
        this.comparator = comparator;
        elements = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove() {
        if(isEmpty()) {
            return null;
        }
        Object data = elements[0];
        elements[0] = elements[--size];
        elements[size] = null;
        sink(0);
        return (E)data;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        // boundary check
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }

        E data = (E)elements[index];
        // remove last
        if(index == size - 1) {
            elements[--size] = null;
        } else {
            elements[index] = elements[--size];
            if(lager(index, parent(index))) {
                // replace node lager than parent
                swim(index);
            } else {
                sink(index);
            }
        }

        return data;
    }

    @Override
    public boolean isMaxHeap() {
        if(size == 0) {
            return true;
        }

        for (int i = 0; i < size; i++) {
            int left = left(i);
            int right = right(i);
            if(left < size) {
                if(less(i, left)) {
                    return false;
                }
            }

            if(right < size) {
                if(less(i, right)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isMinHeap() {
        if(size == 0) {
            return true;
        }

        for (int i = 0; i < size; i++) {
            int left = left(i);
            int right = right(i);
            if(left < size) {
                if(lager(i, left)) {
                    return false;
                }
            }

            if(right < size) {
                if(lager(i, right)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        while (size > 0) {
            elements[--size] = null;
        }
    }

    @Override
    public boolean add(E data) {
        if(size == elements.length) {
            grow(elements.length + 1);
        }
        elements[size++] = data;
        swim(size - 1);
        return true;
    }

    @Override
    public boolean contains(E data) {
        for (int i = 0; i < size; i++) {
            if(Objects.equals(data, elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MaxBinaryHeapIterator();
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

    private void swim(int k) {
        for(int parent = parent(k);
            k > 0 && less(parent, k);
            k = parent, parent = parent(k)) {
            swap(parent, k);
        }
    }

    private void sink(int k) {
        for(int left = left(k), right = right(k), lager = left;
            left < size;
            k = lager, left = left(k), right = right(k), lager = left
            ) {
            if(right < size && less(left, right)) {
                // left < right
                lager = right;
            }
            if(!less(k, lager)) {
                // parent >= lager child
                break;
            }
            swap(k, lager);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean less(int a, int b) {
        return comparator.compare((E)elements[a], (E)elements[b]) < 0;
    }

    @SuppressWarnings("unchecked")
    private boolean lager(int a, int b) {
        return comparator.compare((E)elements[a], (E)elements[b]) > 0;
    }

    @SuppressWarnings("unchecked")
    private boolean equal(int a, int b) {
        return comparator.compare((E)elements[a], (E)elements[b]) == 0;
    }

    private void swap(int a, int b) {
        Object temp = elements[a];
        elements[a] = elements[b];
        elements[b] = temp;
    }

    private static int left(int parent) {
        return 2 * parent + 1;
    }

    private static int right(int parent) {
        return 2 * parent + 2;
    }

    private static int parent(int child) {
        return (child - 1) / 2;
    }

    private class MaxBinaryHeapIterator implements Iterator<E> {
        private int cursor = 0;
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            if(hasNext()) {
                return (E) elements[cursor++];
            }
            throw new NoSuchElementException();
        }
    }

    public static class Driver {
        public static void main(String[] args) {
            UnitTest.run(new Driver());
        }

        @Test
        public void kthLargest() {
            int k = 5;
            int size = 20;
            Random random = new Random();
            int[] array = random.ints(size, 0, 100).toArray();
            System.out.println("array: " + Arrays.toString(array));
            MaxBinaryHeap<Integer> heap = new MaxBinaryHeap<>(Integer::compareTo);
            for (int i = 0; i < array.length; i++) {
                heap.add(array[i]);
            }
            Arrays.sort(array);
            System.out.println("sorted: " + Arrays.toString(array));
            for (int i = array.length - 1, max; i > array.length - k - 1; i--) {
                if((max = heap.remove()) != array[i]) {
                    throw new RuntimeException("Test Failed");
                }
                System.out.println("max: " + max);
            }
            System.out.println("Test Result: " + heap.isMaxHeap());
        }

        @Test
        public void randomRemove() {
            Random random = new Random();
            int[] array = random.ints(100, 0, 100).toArray();
            System.out.println("array: " + Arrays.toString(array));
            MaxBinaryHeap<Integer> heap = new MaxBinaryHeap<>(Integer::compareTo);
            for (int i = 0; i < array.length; i++) {
                heap.add(array[i]);
            }

            for(int i = 0; i < array.length / 2; i++) {
                System.out.println("Remove At " + i + " : " + heap.remove(random.nextInt(heap.size)));
            }
            Arrays.sort(array);
            System.out.println("Sorted: " + Arrays.toString(array));
            System.out.println("Test Result: " + heap.isMaxHeap());

            System.out.println("Heap: ");
            while (!heap.isEmpty()) {
                System.out.println(heap.remove());
            }
        }
    }

}
