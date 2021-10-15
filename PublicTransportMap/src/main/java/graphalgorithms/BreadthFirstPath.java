package graphalgorithms;

import model.TransportGraph;

import java.util.PriorityQueue;
import java.util.Queue;

public class BreadthFirstPath extends AbstractPathSearch {

    public BreadthFirstPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        Queue<Integer> queue = new PriorityQueue();
        marked[startIndex] = true;
        queue.add(startIndex);
        nodesVisited.add(graph.getStation(startIndex));

        while (!queue.isEmpty()){
            int vertex = queue.poll();
            for (int w : graph.getAdjacentVertices(vertex)){
                if (w == endIndex){
                    queue.clear();
                    nodesVisited.add(graph.getStation(w));
                    edgeTo[w] = vertex;
                    marked[w] = true;
                    pathTo(w);
                }
                else if (!marked[w]){
                    nodesVisited.add(graph.getStation(w));
                    edgeTo[w] = vertex;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
    }
}
