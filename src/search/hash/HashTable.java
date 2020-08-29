package search.hash;


import java.util.Arrays;
import java.util.Objects;

public class HashTable<Key, Value> {

    protected Node<Key, Value>[] elements;
    protected int size;
    protected int threshold;
    protected final float loadfactor;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final int MAXIMUM_CAPACITY = 1 << 30;
    public static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    public HashTable() {
        loadfactor = DEFAULT_LOAD_FACTOR;
        threshold = (int)(DEFAULT_INITIAL_CAPACITY * loadfactor);
    }

    public HashTable(int initialCapacity) {
        loadfactor = DEFAULT_LOAD_FACTOR;
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;

        this.threshold = tableSizeFor(initialCapacity);
    }

    public Value put(Key key, Value value) {
        if(elements == null || elements.length == 0) {
            resize();
        }
        int index = index(key, elements.length);
        Node<Key, Value> node = elements[index];

        while (node != null) {
            if(Objects.equals(node.pair.key, key)) {
                break;
            }
            node = node.next;
        }

        if(node == null) {
            // not found
            elements[index] = new Node<>(
                    hash(key),
                    new Pair<>(key, value),
                    elements[index]);
            if(++size > threshold) {
                resize();
            }
            return null;
        } else {
            Value oldValue = node.pair.value;
            node.pair.value = value;
            return oldValue;
        }

    }

    public Value get(Key key) {
        if(size < 1) {
            return null;
        }
        Node<Key, Value> node = elements[index(key, elements.length)];

        while (node != null) {
            if(Objects.equals(node.pair.key, key)) {
                break;
            }
            node = node.next;
        }
        return node == null ? null : node.pair.value;
    }

    public boolean contains(Key key) {
        return get(key) == null;
    }

    public Value remove(Key key) {
        if(size < 1) {
            return null;
        }
        int index = index(key, elements.length);
        Node<Key, Value> node = elements[index];
        Node<Key, Value> previous = null;
        while (node != null) {
            if(Objects.equals(node.pair.key, key)) {
                break;
            }
            previous = node;
            node = node.next;
        }

        if(node == null) {
            // not find
            return null;
        } else {
            Value oldValue = node.pair.value;
            if(previous == null) {
                elements[index] = node.next;
            } else {
                previous.next = node.next;
            }
            node.pair = null;
            node.next = null;
            size--;
            return oldValue;
        }
    }

    public void clear() {
        size = 0;
        if(elements != null)
            Arrays.fill(elements, null);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size < 1;
    }

    /**
     * adopt from java.util.HashMap
     * @param key key
     * @return hash code for hash table
     */
    private static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private static int index(Object key, int size) {
        return hash(key) % size;
    }

    private static int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<Key,Value>[] oldTab = elements;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) { // cannot grow
                threshold = Integer.MAX_VALUE;
                return;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadfactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        Node<Key,Value>[] newTab = (Node<Key, Value>[])new Node[newCap];
        // hash function changed
        // table need to be remapped
        remap(elements, newTab);
    }

    private void remap(Node<Key, Value>[] target,
                       Node<Key, Value>[] destination) {
        if(elements == null) {
            return;
        }

        Node<Key, Value> node;
        Node<Key, Value> previous;
        for (int i = 0, index; i < target.length; i++) {
            node = target[i];
            if(node == null) {
                continue;
            }

            target[i] = null;
            index = index(node.pair.key, destination.length);
            if(node.next == null) {
                destination[index] = node;
                node.hash = hash(node.pair.key);
            } else {
                // hand linked list
                while (node != null) {
                    // index of bin ceil
                    index = index(node.pair.key, destination.length);
                    previous = destination[index]; // save previous stored ceil
                    destination[index] = node;
                    node = node.next; // move to next node
                    destination[index].next = previous; // link previous ceil
                }
            }
        }
    }

    public static class Node<Key, Value> {
        protected int hash;
        protected Node<Key, Value> next;
        protected Pair<Key, Value> pair;

        public Node(int hash,
                    Pair<Key, Value> pair,
                    Node<Key, Value> next) {
            this.hash = hash;
            this.pair = pair;
            this.next = next;
        }
    }

    public static class Pair<Key, Value> {
        protected Key key;
        protected Value value;

        public Pair(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return key.equals(pair.key) &&
                    Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
