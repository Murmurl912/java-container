package container.abstraction;

import container.Container;
import container.Growable;

public interface QueueContainer<E>
        extends Container<E> {

    public void offer(E data);

    public E take();

    public E peek();

}
