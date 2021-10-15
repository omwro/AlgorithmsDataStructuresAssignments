package graphalgorithms;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class AStar extends AbstractPathSearch{
    private IndexMinPQ<Double> pq;
    private double travelTime = 0.0;

    public AStar(TransportGraph graph, String start, String end) {
        super(graph, start, end);
        pq = new IndexMinPQ<>(graph.getNumberOfStations());
    }

    @Override
    public void search() {
        pq.insert(startIndex, 0.0);
        edgeTo[startIndex]--;

        int index = startIndex;
        while (!pq.isEmpty() && index != endIndex) {
            index = pq.delMin();
            marked[index] = true;
            nodesVisited.add(graph.getStation(index));

            for (int adjacentIndex : graph.getAdjacentVertices(index)) {
                if (!marked[adjacentIndex]) {
                    Location currentLocation = getLocation(this.graph.getStation(index));
                    Location nextLocation = getLocation(this.graph.getStation(adjacentIndex));
                    Location startLocation = getLocation(this.graph.getStation(startIndex));
                    Location endLocation = getLocation(this.graph.getStation(endIndex));

                    double travelTimeStart = Location.travelTime(currentLocation, startLocation);
                    double travelTimeToNextStation = Location.travelTime(endLocation, nextLocation);

                    if (!pq.contains(adjacentIndex)) {
                        weightPerStation[adjacentIndex] = travelTimeStart;
                        edgeTo[adjacentIndex] = index;
                        pq.insert(adjacentIndex, travelTimeStart + travelTimeToNextStation);
                    } else if (weightPerStation[adjacentIndex] > travelTimeStart) {
                    weightPerStation[adjacentIndex] = travelTimeStart;
                    edgeTo[adjacentIndex] = index;
                    }
                }
            }
        }

        pathTo(endIndex);
        calculateTotalTime(nodesInPath);
    }

    private Location getLocation(Station station){
        return TransportGraph.Builder.getLocationList()
                .stream()
                .filter(s -> s.getStation().equals(station))
                .findFirst()
                .get();
    }

    private void calculateTotalTime(List<Station> paths){
        ArrayList<Location> locationsList = new ArrayList<>();

        for (Station station : paths) {
            locationsList.add(getLocation(station));
        }

        for (int i = 0; i < locationsList.size() - 1; i++) {
            travelTime += Location.travelTime(locationsList.get(i), locationsList.get(i + 1));
        }
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder(String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)));
        resultString.append(nodesInPath).append(" with " + transfers + " transfers and a total traveltime of ").append(travelTime).append(" minutes ");
        return resultString.toString();
    }
}
