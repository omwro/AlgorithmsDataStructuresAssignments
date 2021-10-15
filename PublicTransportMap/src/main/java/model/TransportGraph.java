package model;

import java.util.*;

public class TransportGraph {

    private int numberOfStations; // Number of stations
    private int numberOfConnections; // number of connections
    private List<Station> stationList; // list of station
    private Map<String, Integer> stationIndices; // Station name & index of stationList
    private List<Integer>[] adjacencyLists; // <Index of stationList> [Indexes of the Stations]
    private Connection[][] connections; // [Index of start station] [Index of end station]

    public TransportGraph (int size) {
        this.numberOfStations = size;

        this.stationList = new ArrayList<>(size);
        this.stationIndices = new HashMap<>();

        this.connections = new Connection[size][size];
        this.adjacencyLists = (List<Integer>[]) new List[size];

        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     * The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        boolean stationAlreadyExistInTheList =  this.stationList.stream()
                .noneMatch(x -> x.getStationName().equals(vertex.getStationName()));
        if (stationAlreadyExistInTheList){
            stationList.add(vertex);
            stationIndices.put(vertex.getStationName(), stationList.indexOf(vertex));
        }
    }


    /**
     * Method to add an edge to a adjancencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The grap is bidirected, so edge(to, from) should also be added.
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        // Adds 1 to numberOfConnection for each visit.
        this.numberOfConnections++;

        // Checks if the from or to is bigger or equal than 0.
        // If that is true add them to the adjacencyLists.
        if(from >= 0 || to >= 0) {
            this.adjacencyLists[from].add(to);
            this.adjacencyLists[to].add(from);
        }
    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connecion to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        // Get the names from the from & to Station.
        String fromName = connection.getFrom().getStationName();
        String toName = connection.getTo().getStationName();

        // Check if the indexes of the stations are bigger or equal than zero.
        if (this.getIndexOfStationByName(fromName) >= 0 && this.getIndexOfStationByName(toName) >= 0){

            // Use a method to make the from and to into integers.
            int from = this.getIndexOfStationByName(fromName);
            int to = this.getIndexOfStationByName(toName);

            // Call the addEdge method with the integers as parameters.
            this.addEdge(from, to);

            // Add the connections between stations.
            this.connections[from][to] = connection;
            this.connections[to][from] = connection;
        }
    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-" );
            }
            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private static Set<Connection> connectionSet;
        private double[][] connectionForWeight;
        private static List<Location> locationList;

        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
            connectionForWeight = new double[5][];
            locationList = new LinkedList<>();
        }

        public static Set<Connection> getConnectionSet() {
            return connectionSet;
        }

        public static List<Location> getLocationList() {
            return locationList;
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition) {
            // Make an Line Object with String array and add them to the ArrayList.
            Line transportLine = new Line(lineDefinition[1], lineDefinition[0]);
            lineList.add(transportLine);

            // Loop through the String Array and for each String after the 2nd string in the array,
            // Make an Station Object out of that String and add them to the Line.
            for (int i = 2; i < lineDefinition.length; i++) {
                Station station = new Station(lineDefinition[i]);
                transportLine.addStation(station);
            }
            return this;
        }


        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         * @return
         */
        public Builder buildStationSet() {
            // Make a list for the initial stations. The HashSet is to get rid of duplicates.
            List<Station> stationList = new ArrayList<>();
            HashSet<Station> hashSet = new HashSet<>();

            /*
            Loop through the lineList and through the stations of each line For each station of that list add them to
            the first ArrayList. After that add All the stations into the HashSet.
            */
            for (Line transportLine : this.lineList) {
                for (Station station : transportLine.getStationsOnLine()) {
                    stationList.add(station);
                    hashSet.addAll(stationList);
                }
            }
            this.stationSet.addAll(hashSet);

            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         * @return
         */
        public Builder addLinesToStations() {
            // Loop through the stations of the set, then loop through the lines of lineList.
            // After that check if the line contains the station and add the line to the station.
            for (Station station : this.stationSet) {
                for (Line line : this.lineList) {
                    if(line.getStationsOnLine().contains(station)) {
                        station.addLine(line);
                    }
                }
            }
            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         * @return
         */
        public Builder buildConnections() {
            /*
            Loop through each line, then in a for loop check if the next index is not outside the List.
            The make a new connection with the current index station and with the next index station.
            Add them to the connectionSet.
            */
            if(this.connectionForWeight[0] == null) {
                for (Line line : this.lineList) {
                    for (int i = 0; i < line.getStationsOnLine().size(); i++) {
                        if(!((i + 1) == line.getStationsOnLine().size())) {
                            Connection connection = new Connection(line.getStationsOnLine().get(i), line.getStationsOnLine().get(i + 1));
                            connection.setLine(line);
                            this.connectionSet.add(connection);
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.lineList.size(); i++) {
                    for (int j = 0; j < this.lineList.get(i).getStationsOnLine().size(); j++) {
                        if(!((j + 1) == lineList.get(i).getStationsOnLine().size())) {
                            Connection connection = new Connection(this.lineList.get(i).getStationsOnLine().get(j),
                                    this.lineList.get(i).getStationsOnLine().get(j + 1), this.connectionForWeight[i][j],
                                    this.lineList.get(i));
                            this.connectionSet.add(connection);
                        }
                    }
                }
            }
            return this;
        }

        public void setConnectionsForWeight(double[][] doubleArray){
            this.connectionForWeight = doubleArray;
        }

        public void setLocationList(List<Location> locationList){
            this.locationList = locationList;
        }

        /**
         * Method that builds the graph.
         * All stations of the stationSet are addes as vertices to the graph.
         * All connections of the connectionSet are addes as edges to the graph.
         * @return
         */
        public TransportGraph build() {
            TransportGraph graph = new TransportGraph(stationSet.size());
            for (Station station : this.stationSet) {
                graph.addVertex(station);
            }

            for(Connection connection : this.connectionSet) {
                graph.addEdge(connection);
            }
            return graph;
        }

    }
}
