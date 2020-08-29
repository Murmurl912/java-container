package container.abstraction;

import container.Container;
import container.Growable;

public interface BinaryHeapContainer<E> extends Container<E> {
    @Override
    default boolean remove(E data) {
        throw new UnsupportedOperationException();
    }

    public E remove();

    public boolean isMaxHeap();

    public boolean isMinHeap();

}
