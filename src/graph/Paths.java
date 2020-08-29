package graph;

public abstract class Paths {

    public Paths(Graph graph, int source) {
        init(graph, source);
    }

    protected abstract void init(Graph graph, int source);

    public abstract boolean hashPathTo(int v);

    public abstract Iterable<Integer> pathTo(int v);

}
