package container.impl;

import container.abstraction.ListContainer;

import java.util.*;

public class List<E> implements ListContainer<E> {

    protected Node<E> front;
    protected Node<E> rear;
    protected int size;

    public List() {
        front = rear = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        boundaryCheck(index);
        Node<E> node = travel(index);
        if(node == null) {
            throw new IllegalStateException("failed to find node in given index.");
        }
        return node.data;
    }

    @Override
    public void add(int index, E data) {
        if(index > size || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }

        if(index == size) {
            Node<E> newNode = new Node<>(data, rear, null);
            if(rear == null) {
                front = rear = newNode;
            } else {
                rear.next = newNode;
            }
            size++;
        } else {
            Node<E> next = travel(index);
            assert next != null;
            Node<E> previous = next.previous;
            Node<E> newNode = new Node<>(data, previous, next);
            next.previous = newNode;
            if(previous == null) {
                front = newNode;
            } else {
                previous.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void replace(int index, E data) {
        boundaryCheck(index);
        Node<E> node = travel(index);
        assert node != null;
        node.data = data;
    }

    @Override
    public E remove(int index) {
        boundaryCheck(index);
        Node<E> node = travel(index);
        assert node != null;
        E data = node.data;
        remove(node);
        return data;
    }

    @Override
    public int search(E data, int start, int end) {
        if(start > size - 1 || start < 0) {
            throw new IllegalArgumentException("start index out of bounds");
        }
        if(end > size || end < 0) {
            throw new IllegalArgumentException("end index out of bounds");
        }
        if(start >= end) {
            throw new IllegalArgumentException("start index >= end index");
        }
        Node<E> node = front;
        int i = 0;
        for (; i <= start; i++) {
            node = node.next;
        }

        for(; i < end; i++) {
            if(Objects.equals(node.data, data)) {
                return i;
            }
            node = node.next;
        }
        return - 1;
    }

    @Override
    public void reverse() {
        Node<E> newHead = null;
        Node<E> node = front;

        while (node != null) {
            Node<E> next = node.next;
            node.next = newHead;
            if(newHead != null) {
                newHead.previous = node;
            }
            newHead = node;
            node = next;
        }
    }

    @Override
    public ListContainer<E> slice(int start, int end) {
        if(start > size - 1 || start < 0) {
            throw new IllegalArgumentException("start index out of bounds");
        }
        if(end > size || end < 0) {
            throw new IllegalArgumentException("end index out of bounds");
        }
        if(start > end) {
            throw new IllegalArgumentException("start index > end index");
        }
        Node<E> node = front;
        int i = 0;
        for(;i <= start; i++) {
            node = node.next;
        }
        int size = end - start;
        if(size == 0) {
            return new List<>();
        }
        Node<E> newFront = new Node<>(node.data, null, null);
        Node<E> newRear = newFront;
        i++;
        node = node.next;

        Node<E> temp;
        for(;i < end; i++) {
            temp = new Node<>(node.data, newRear, null);
            newRear.next = temp;
            newRear = temp;
            node = node.next;
        }
        List<E> list = new List<>();
        list.size = size;
        list.front = newFront;
        list.rear = newRear;
        return list;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Node<E> next;
        while (front != null) {
            next = front.next;
            front.data = null;
            front.next = null;
            front.previous = null;
            front = next;
            size--;
        }
    }

    @Override
    public boolean add(E data) {
        if(size == 0) {
            front = rear = new Node<>(data, null, null);
            size++;
        } else {
            rear.next = new Node<>(data, rear, null);
            rear = rear.next;
            size++;
        }
        return true;
    }

    @Override
    public boolean remove(E data) {
        if(isEmpty()) {
            return false;
        }
        for(Node<E> node = front; node != null; node = node.next) {
            if(Objects.equals(node.data, data)) {
                remove(node);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(E data) {
        if(isEmpty()) {
            return false;
        };
        return search(data, 0, size) > -1;
    }

    @Override
    public ListIterator<E> iterator() {
        return new Iter(0);
    }


    protected void boundaryCheck(int index) {
        if(index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
    }


    protected Node<E> travel(int index) {
        Node<E> node = front;
        int counter = 0;
        while (node != null) {
            if(counter < index) {
                node = node.next;
                counter++;
            } else {
                break;
            }
        }
        return node;
    }

    protected void remove(Node<E> node) {
        Objects.requireNonNull(node);
        Node<E> previous = node.previous;
        Node<E> next = node.next;

        if(previous == null) {
            front = next;
        } else {
            previous.next = next;
            node.previous = null;
        }

        if(next == null) {
            rear = previous;
        } else {
            next.previous = previous;
            node.next = null;
        }
        node.data = null;
        size--;
    }


    protected void add(E data, Node<E> node) {
        Node<E> newNode = new Node<>(data, node.previous, node);
        if(newNode.previous == null) {
            front = newNode;
        } else {
            newNode.previous.next = newNode;
        }
        node.previous = newNode;
        size++;
    }

    public static class Node<E> {
        private E data;
        private Node<E> previous;
        private Node<E> next;

        public Node(E data, Node<E> previous, Node<E> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public E data() {
            return data;
        }

        public Node<E> previous() {
            return previous;
        }

        public Node<E> next() {
            return next;
        }
    }

    public class Iter implements ListIterator<E> {
        private int index;
        private Node<E> previous;
        private Node<E> cursor;

        public Iter(int index) {
            previous = null;
            this.cursor = travel(index);
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public E next() {
            if(hasNext()) {
                E data = cursor.data;
                cursor = cursor.next;
                index++;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return previous != null;
        }

        @Override
        public E previous() {
            if(hasPrevious()) {
                E data = previous.data;
                cursor = previous;
                previous = previous.previous;
                index--;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            // todo correct implementation
            if(hasPrevious()) {
                index--;
                List.this.remove(previous);
                previous = cursor.previous;
            }
            throw new NoSuchElementException();

        }

        @Override
        public void set(E e) {
            previous.data = e;
        }

        @Override
        public void add(E e) {
            List.this.add(e, cursor);
        }
    }
}

