package graph;

import java.util.Iterator;
import java.util.stream.IntStream;

public interface Graph {

    public int vertex();

    public int edge();

    public Iterable<Integer> adj(int vertex);

}
