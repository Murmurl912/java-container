package search.tree;

public class RedBlackTree<Key, Value> {

    public static class Pair<Key, Value> {
        protected Key key;
        protected Value value;

        protected Pair(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

    }
    public static class Node<Key, Value> {
        protected Pair<Key, Value> pair;

        protected Color color;
        protected Node<Key, Value> parent;

        protected Node<Key, Value> left;
        protected Node<Key, Value> right;

        public static enum Color {
            RED, BLACK
        }
    }
}
