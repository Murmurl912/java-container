package container.abstraction;

public interface DequeContainer<E> extends QueueContainer<E> {

    public void offerFront(E data);

    public void offerRear(E data);

    public E takeFront();

    public E takeRear();

    public E peekFront();

    public E peekRear();

}
