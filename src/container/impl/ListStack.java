package container.impl;

import container.abstraction.StackContainer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ListStack<E> implements StackContainer<E> {

    protected int size;
    protected Node<E> top;

    public ListStack() {
        top = null;
        size = 0;
    }

    @Override
    public void push(E data) {
        top = new Node<>(data, top);
        size++;
    }

    @Override
    public E peek() {
        if(top == null) {
            return null;
        }
        return top.data;
    }

    @Override
    public E pop() {
        if(top == null) {
            return null;
        }
        E data = top.data;
        Node<E> temp = top;
        top = top.next;
        temp.data = null;
        temp.next = null;
        size--;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Node<E> nextTop = null;
        while (top != null) {
            nextTop = top.next;
            top.data = null;
            top.next = null;
            top = nextTop;
            size--;
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
        for(Node<E> node = top; node != null; node = node.next) {
            if(Objects.equals(node.data, data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkStackIterator();
    }

    private static class Node<E> {
        private Node<E> next;
        private E data;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        public E data() {
            return data;
        }

        public Node<E> next() {
            return next;
        }
    }

    private class LinkStackIterator implements Iterator<E> {
        private Node<E> cursor = top;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public E next() {
            if(cursor != null) {
                E data = cursor.data;
                cursor = cursor.next;
                return data;
            }
            throw new NoSuchElementException();
        }
    }
}
