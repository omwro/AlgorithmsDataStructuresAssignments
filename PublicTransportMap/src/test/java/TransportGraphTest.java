import model.Connection;
import model.Line;
import model.Station;
import model.TransportGraph;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransportGraphTest {

    private TransportGraph transportGraph;

    @Before
    public void setUp() {
        this.transportGraph = new TransportGraph(3);
        this.transportGraph.addVertex(new Station("Anna Pauwlona Station"));
        this.transportGraph.addVertex(new Station("Schagen Station"));
        this.transportGraph.addVertex(new Station("Alkmaar Centraal"));
    }

    @Test
    public void addVertex() {
        assertEquals(3, this.transportGraph.getStationList().size());
        assertEquals("Schagen Station", this.transportGraph.getStation(1).getStationName());
    }

    @Test
    public void addEdge() {
        this.transportGraph.addEdge(new Connection(
                this.transportGraph.getStation(0),
                this.transportGraph.getStation(1),
                5,
                new Line("Sprinter", "Anna naar Schagen")));
        this.transportGraph.addEdge(new Connection(
                this.transportGraph.getStation(0),
                this.transportGraph.getStation(2),
                10,
                new Line("Sprinter", "Anna naar Alkmaar")));
        this.transportGraph.addEdge(new Connection(
                this.transportGraph.getStation(1),
                this.transportGraph.getStation(2),
                12,
                new Line("Sprinter", "Schagen naar Alkmaar")));

        assertEquals("from Anna Pauwlona Station to Schagen Station on line Anna naar Schagen - Sprinter",
                this.transportGraph.getConnection(0, 1).toString());
        assertEquals("from Anna Pauwlona Station to Alkmaar Centraal on line Anna naar Alkmaar - Sprinter",
                this.transportGraph.getConnection(0, 2).toString());
        assertEquals("from Schagen Station to Alkmaar Centraal on line Schagen naar Alkmaar - Sprinter",
                this.transportGraph.getConnection(1, 2).toString());
    }
}
