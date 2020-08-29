package container.abstraction;

import container.Container;
import container.Growable;

public interface StackContainer<E> extends Container<E> {
    public void push(E data);

    public E peek();

    public E pop();
}
