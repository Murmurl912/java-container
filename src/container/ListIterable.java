package container;

import java.util.Iterator;
import java.util.ListIterator;

public interface ListIterable<E> extends Iterable<E> {

    @Override
    ListIterator<E> iterator();

}
