package container.abstraction;

import container.Container;

public interface MapContainer<K, V> extends Container<MapContainer.Entry<K, V>> {

    public V put(K key, V value);

    public V get(K key);

    public boolean set(K key, V value);

    public V delete(K value);

    public static class Entry<K, V> {
        private K key;
        private V value;
    }
}
