package graph;

public class DepthFirstSearch extends GraphSearch {
    protected Graph graph;
    protected int source;
    protected boolean[] marked;
    protected int count;

    public DepthFirstSearch(Graph graph, int source) {
        super(graph, source);
    }

    @Override
    protected void init(Graph graph, int source) {
        this.graph = graph;
        this.source = source;
        marked = new boolean[graph.vertex()];
    }

    private void dfs(Graph graph, int vertex) {
        marked[vertex] = true;
        count++;
        for(int w : graph.adj(vertex)) {
            if(!marked[w])
                dfs(graph, w);
        }
    }

    /**
     * if source is connected to vertex
     * @param vertex a vertex that its connection with source is to be tested
     * @return true if connected
     */
    @Override
    public boolean marked(int vertex) {
        return marked[vertex];
    }

    /**
     * count of connected vertexes
     * @return connected vertexes count
     */
    @Override
    public int count() {
        return count;
    }
}
