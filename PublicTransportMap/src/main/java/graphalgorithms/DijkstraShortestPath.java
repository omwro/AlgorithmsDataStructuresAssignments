package graphalgorithms;

import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraShortestPath extends AbstractPathSearch {
    private IndexMinPQ<Double> pq;
    private double totalWeight = 0.0;
    private double travelTime = 0.0;

    public DijkstraShortestPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        pq = new IndexMinPQ<>(graph.getNumberOfStations());
    }

    @Override
    public void search() {
        pq.insert(startIndex, 0.0);
        edgeTo[startIndex]--;

        int index = startIndex;
        while (!pq.isEmpty() && index != endIndex){
            index = pq.delMin();
            marked[index] = true;
            nodesVisited.add(graph.getStation(index));

            for (int adjacentIndex : graph.getAdjacentVertices(index)) {
                if (!marked[adjacentIndex]){
                    if (pq.contains(adjacentIndex)){
                        double startWeight = weightPerStation[index] +
                                 graph.getConnection(index, adjacentIndex).getWeight();
                        if (weightPerStation[adjacentIndex] > startWeight){
                            weightPerStation[adjacentIndex] = startWeight;
                            edgeTo[adjacentIndex] = index;
                        }
                    } else {
                        double startWeight = weightPerStation[index] +
                                graph.getConnection(index, adjacentIndex).getWeight();
                        weightPerStation[adjacentIndex] = startWeight;
                        edgeTo[adjacentIndex] = index;
                        pq.insert(adjacentIndex, startWeight);
                    }
                }
            }
        }

        pathTo(endIndex);
        calculateWeight(nodesInPath);

    }

    public void calculateWeight(List<Station> paths){
        ArrayList<Connection> connectionList = new ArrayList<>();

        for (int i = 0; i < paths.size() - 1; i++) {
            connectionList.add(getConnection(paths.get(i), paths.get(i + 1)));
        }

        for (Connection connection : connectionList) {
            this.totalWeight += connection.getWeight();
        }
    }

    private Connection getConnection(Station first, Station second){
        return TransportGraph.Builder.getConnectionSet()
                .stream()
                .filter(connection ->(connection.getFrom().equals(first) && connection.getTo().equals(second)) || (connection.getTo().equals(first) && connection.getFrom().equals(second)))
                .findFirst()
                .get();
    }

    @Override
    public String toString() {
        return String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)) +
                nodesInPath + " with " + transfers + " transfers and a total weight of " + totalWeight;
    }
}
