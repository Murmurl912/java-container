package container.abstraction;

import container.Container;
import container.ListIterable;

public interface ListContainer<E>
        extends Container<E>,
        ListIterable<E> {

    public E get(int index);

    public void add(int index, E data);

    public void replace(int index, E data);

    public E remove(int index);

    public int search(E data, int start, int end);

    public void reverse();

    public ListContainer<E> slice(int start, int end);

}
