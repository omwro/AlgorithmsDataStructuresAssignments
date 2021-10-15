package graphalgorithms;

import model.TransportGraph;


public class DepthFirstPath extends AbstractPathSearch {

    public DepthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    private void depthFirstSearch(int vertex) {
        marked[vertex] = true;
        for (int x : graph.getAdjacentVertices(vertex)){
            if (!marked[x]){
                edgeTo[x] = vertex;
                nodesVisited.add(graph.getStation(x));
                if (x != endIndex){
                    depthFirstSearch(x);
                } else {
                    marked[x] = true;
                    pathTo(x);
                }
            }
        }
    }

    @Override
    public void search() {
        nodesVisited.add(graph.getStation(startIndex));
        depthFirstSearch(startIndex);
    }
}
