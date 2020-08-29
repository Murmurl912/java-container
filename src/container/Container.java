package container;

public interface Container<E> extends Iterable<E> {

    public int size();

    public void clear();

    public boolean add(E data);

    public boolean remove(E data);

    public boolean contains(E data);

    default boolean isEmpty() {
        return size() <= 0;
    }
}
