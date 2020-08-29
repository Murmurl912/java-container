package container.abstraction;

import container.Container;
import container.Growable;

public interface SetContainer<E> extends Container<E>, Growable {

    public void put(E data);

}
