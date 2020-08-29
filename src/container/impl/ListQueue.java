package container.impl;

import container.abstraction.QueueContainer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

public class ListQueue<E> implements QueueContainer<E> {

    protected Node<E> front;
    protected Node<E> rear;
    protected int size;

    public ListQueue() {
        front = rear = null;
        size = 0;
    }

    @Override
    public void offer(E data) {
        Node<E> newNode = new Node<>(data, null);
        rear.next = newNode;
        rear = newNode;
        size++;
    }

    @Override
    public E take() {
        if(isEmpty()) {
            return null;
        }
        Node<E> removeNode = front;
        front = removeNode.next;
        if(front == null) {
            rear = null;
        }
        removeNode.next = null;
        E data = removeNode.data;
        removeNode.data = null;
        size--;
        return data;
    }

    @Override
    public E peek() {
        if(isEmpty()) {
            return null;
        }

        return front.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Node<E> temp = null;
        while (front != null) {
            temp = front.next;
            front.data = null;
            front.next = null;
            front = temp;
            size--;
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
        for(Node<E> node = front; node != null; node = node.next) {
            if(Objects.equals(node.data, data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListQueueIterator();
    }

    private static class Node<E> {
        private Node<E> next;
        private E data;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        public Node<E> next() {
            return next;
        }

        public E data() {
            return data;
        }
    }

    private class ListQueueIterator implements Iterator<E> {
        private Node<E> cursor;

        public ListQueueIterator() {
            cursor = front;
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
                return data;
            }
            throw new NoSuchElementException();
        }
    }
}
