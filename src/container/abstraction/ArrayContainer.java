package container.abstraction;

import container.Container;
import container.Growable;
import container.ListIterable;
import container.RandomAccessible;

public interface ArrayContainer<E>
        extends Container<E>,
        ListIterable<E>,
        RandomAccessible<E> {

    public E get(int index);

    public void add(int index, E data);

    public E replace(int index, E data);

    public E remove(int index);

    public int search(E data, int start, int end);

    public void reverse();

    public ArrayContainer<E> slice(int start, int end);

}
