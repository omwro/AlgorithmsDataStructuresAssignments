package controller;

import graphalgorithms.AStar;
import graphalgorithms.BreadthFirstPath;
import graphalgorithms.DepthFirstPath;
import graphalgorithms.DijkstraShortestPath;
import model.Location;
import model.TransportGraph;

import java.util.*;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        System.out.println("ASSIGNMENT  A");
        String[] redLineA = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLineA = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLineA = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLineA = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        String start = "Marken";
        String end = "Coltrane Cirkel";

        TransportGraph.Builder builderA = new TransportGraph.Builder();
        builderA.addLine(redLineA).addLine(blueLineA).addLine(greenLineA).addLine(yellowLineA);
        TransportGraph transportGraphA = builderA.buildStationSet().addLinesToStations().buildConnections().build();

        System.out.println(transportGraphA);

//        Uncommented to test the DepthFirstPath algorithm
        System.out.println("Result of DepthFirstSearch:");
        DepthFirstPath dfpTestA = new DepthFirstPath(transportGraphA, "A", "J");
        dfpTestA.search();
        System.out.println(dfpTestA);
        dfpTestA.printNodesInVisitedOrder();
        System.out.println();

//        Uncommented to test the BreadthFirstPath algorithm
        System.out.println("Result of BreadthFirstSearch:");
        BreadthFirstPath bfsTestA = new BreadthFirstPath(transportGraphA, "A", "J");
        bfsTestA.search();
        System.out.println(bfsTestA);
        bfsTestA.printNodesInVisitedOrder();
        System.out.println("_____________________________________________________________");


        System.out.println("ASSIGNMENT  B");
        String[] redLineB = {"red", "metro", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        String[] blueLineB = {"blue", "metro", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        String[] purpleLineB = {"purple", "metro", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};
        String[] greenLineB = {"green", "metro", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        String[] yellowLineB = {"yellow", "bus", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};


        double[][] doubleValue = new double[5][];

        doubleValue[0] = new double[]{4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        doubleValue[1] = new double[]{6.0, 5.3, 5.1, 3.3};
        doubleValue[2] = new double[]{6.2, 5.2, 3.8, 3.6};
        doubleValue[3] = new double[]{5.0, 3.7, 6.9, 3.9, 3.4};
        doubleValue[4] = new double[]{26.0, 19.0, 37.0, 25.0, 22.0, 28.0};


        TransportGraph.Builder builderB = new TransportGraph.Builder();
        builderB.addLine(redLineB).addLine(blueLineB).addLine(purpleLineB).addLine(greenLineB).addLine(yellowLineB).setConnectionsForWeight(doubleValue);
        TransportGraph transportGraphB = builderB.buildStationSet().addLinesToStations().buildConnections().build();

        System.out.println(transportGraphB);

        // Uncommented to test DijkstraShortestPath algorithm
        System.out.println("Result of DijkstraShortestPath:");
        DijkstraShortestPath dijkstraSP = new DijkstraShortestPath(transportGraphB, start, end);
        dijkstraSP.search();
        System.out.println(dijkstraSP);
        dijkstraSP.printNodesInVisitedOrder();
        System.out.println("_____________________________________________________________");


        System.out.println("ASSIGNMENT  C");

        List<Location> locations = new LinkedList<>();
        locations.add(new Location(0, 11, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Oostvaarders"))));
        locations.add(new Location(14, 1, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Haven"))));
        locations.add(new Location(12, 3, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Marken"))));
        locations.add(new Location(10, 5, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Steigerplein"))));
        locations.add(new Location(7, 6, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Coltrane Cirkel"))));
        locations.add(new Location(6, 12, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Robijnpark"))));
        locations.add(new Location(6, 9, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Meridiaan"))));
        locations.add(new Location(9, 0, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Ymeerdijk"))));
        locations.add(new Location(4, 6, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Grootzeil"))));
        locations.add(new Location(11, 11, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Bachgracht"))));
        locations.add(new Location(3, 10, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Dukdalf"))));
        locations.add(new Location(2, 3, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Grote Sluis"))));
        locations.add(new Location(10, 9, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Swingstraat"))));
        locations.add(new Location(5, 14, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Violetplantsoen"))));
        locations.add(new Location(12, 13, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Nobelplein"))));
        locations.add(new Location(8, 8, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Centrum"))));
        locations.add(new Location(9, 3, transportGraphB.getStation(transportGraphB.getIndexOfStationByName("Trojelaan"))));

        TransportGraph.Builder builderC = new TransportGraph.Builder();
        builderC.addLine(redLineB).addLine(blueLineB).addLine(purpleLineB).addLine(greenLineB).addLine(yellowLineB);
        builderC.setLocationList(locations);
        builderC.setConnectionsForWeight(doubleValue);
        TransportGraph transportGraphC = builderC.buildStationSet().addLinesToStations().buildConnections().build();

        // Uncommented to test DijkstraShortestPath algorithm
        System.out.println("Result of A*:");
        AStar aStar = new AStar(transportGraphC, start, end);
        aStar.search();
        System.out.println(aStar);
        aStar.printNodesInVisitedOrder();

    }
}
