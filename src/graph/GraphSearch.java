package graph;

public abstract class GraphSearch {

    public GraphSearch(Graph graph, int source) {
        init(graph, source);
    }

    protected abstract void init(Graph graph, int source);

    public abstract boolean marked(int vertex);

    public abstract int count();

}
