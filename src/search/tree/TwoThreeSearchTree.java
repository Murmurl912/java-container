package search.tree;

import java.util.Comparator;

public class TwoThreeSearchTree<Key, Value> {

    protected Node<Key, Value> root;
    protected int size;
    protected int height;
    protected Comparator<Key> comparator;



    protected Node<?, ?>[] node(Key key) {

        if(root == null) {
            return null;
        }

        int first = 0;
        int second = 0;
        Node<Key, Value> previous = null;
        for(Node<Key, Value> node = root; node != null;) {
            assert node.first != null && node.left != null;
            first = comparator.compare(key, node.first.key);
            if(first < 0) {
                previous = node;
                node = node.left;
            } else if(first == 0) {
                return new Node<?, ?>[]{previous, node};
            }

            if(node.second == null) {
                return new Node<?, ?>[]{previous, null};
            }

            second = comparator.compare(key, node.second.key);

            if(second < 0) {
                previous = node;
                node = node.middle;
            } else if(second == 0) {
                return new Node<?, ?>[]{previous, node};
            } else {
                node = node.right;
            }
        }

        return new Node<?, ?>[]{previous, null};
    }

    public void insert(Key key, Value value) {

    }

    public static class Pair<Key, Value> {

        protected Key key;
        protected Value value;

        public Pair(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Key key() {
            return key;
        }

        public Value value() {
            return value;
        }

    }

    public static class Node<Key, Value> {

        protected Pair<Key, Value> first, second;
        protected Node<Key, Value> parent;
        protected Node<Key, Value> left, middle, right;
        protected int height;

        public Node(Pair<Key, Value> first,
                    Pair<Key, Value> second,
                    Node<Key, Value> parent,
                    Node<Key, Value> left,
                    Node<Key, Value> middle,
                    Node<Key, Value> right,
                    int height) {
            this.first = first;
            this.second = second;
            this.parent = parent;
            this.left = left;
            this.middle = middle;
            this.right = right;
            this.height = height;
        }
    }
}
