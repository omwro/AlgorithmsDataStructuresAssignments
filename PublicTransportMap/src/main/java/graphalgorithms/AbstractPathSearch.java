package graphalgorithms;

import model.Line;
import model.Station;
import model.TransportGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Abstract class that contains methods and attributes shared by the DepthFirstPath en BreadthFirstPath classes
 */
public abstract class AbstractPathSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int transfers = 0;
    protected List<Station> nodesVisited;
    protected List<Station> nodesInPath;
    protected LinkedList<Integer> verticesInPath;
    protected TransportGraph graph;
    protected final int startIndex;
    protected final int endIndex;
    protected double[] weightPerStation;

    public AbstractPathSearch(TransportGraph graph, String start, String end) {
        startIndex = graph.getIndexOfStationByName(start);
        endIndex = graph.getIndexOfStationByName(end);
        this.graph = graph;
        nodesVisited = new ArrayList<>();
        marked = new boolean[graph.getNumberOfStations()];
        edgeTo = new int[graph.getNumberOfStations()];
        weightPerStation = new double[graph.getNumberOfStations()];
    }

    public abstract void search();

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }


    /**
     * Method to build the path to the vertex, index of a Station.
     * First the LinkedList verticesInPath, containing the indexes of the stations, should be build, used as a stack
     * Then the list nodesInPath containing the actual stations is build.
     * Also the number of transfers is counted.
     * @param vertex The station (vertex) as an index
     */
    public void pathTo(int vertex) {
        // If a possible path is not found, then the method will stop.
        this.verticesInPath = new LinkedList<>();
        this.nodesInPath = new LinkedList<>();
        Stack<Station> stationPath = new Stack<>();

        if (!hasPathTo(vertex)) return;

        for (int z = vertex; z != startIndex; z = edgeTo[z]) {
            stationPath.push(graph.getStation(z));
        }

        stationPath.push(graph.getStation(startIndex));

        while (!stationPath.isEmpty()) {
            Station current = stationPath.pop();
            nodesInPath.add(current);
            verticesInPath.add(graph.getIndexOfStationByName(current.getStationName()));
        }
        /*
            In the for loop it checks if the currentLine == null. If that is true set the currentLine to the line
            that the Search method starts on. Then make a second line to check if the person transfers to another line.
            If the currentLine != newLine call the method countTransfers and ++ to the transfer.
         */
        Line line1 = null;
        for (int i = 0; i < this.nodesInPath.size() - 1; i++) {
            if(line1 == null) {
                line1 = graph.getConnection(verticesInPath.get(i), verticesInPath.get(i + 1)).getLine();
            }
            Line line2 = graph.getConnection(verticesInPath.get(i), verticesInPath.get(i + 1)).getLine();
            if(line1 != line2) {
                countTransfers();
                line1 = line2;
            }
        }
    }

    /**
     * Method to count the number of transfers in a path of vertices.
     * Uses the line information of the connections between stations.
     * If to consecutive connections are on different lines there was a transfer.
     */
    public void countTransfers() {
        this.transfers++;
    }


    /**
     * Method to print all the nodes that are visited by the search algorithm implemented in one of the subclasses.
     */
    public void printNodesInVisitedOrder() {
        System.out.print("Nodes in visited order: ");
        for (Station vertex : nodesVisited) {
            System.out.print(vertex.getStationName() + " ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder(String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)));
        resultString.append(nodesInPath).append(" with " + transfers).append(" transfers");
        return resultString.toString();
    }

}
