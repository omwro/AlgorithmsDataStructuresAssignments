package nl.hva.ict.se.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Place all your own tests for Archer in this class. Tests in any other class will be ignored!
 */
public class ExtendedArcherTest extends ArcherTest {

    private List<Archer> archers;
    private final int LIST_SIZE = 100;

    @BeforeEach
    private void archerSetup() {
        archers = Archer.generateArchers(LIST_SIZE);
    }

    @Test
    public void compareToArcherTest() {
        archerSetup();
        System.out.println(archers.get(0) + " | VS | " + archers.get(1));

        // Checks wich archer is the winner for the assertEquals.
        if (archers.get(0).getTotalScore() > archers.get(1).getTotalScore()
                || archers.get(0).getWeightedScore() > archers.get(1).getWeightedScore()
                || archers.get(0).getId() > archers.get(1).getId()) {

            // First archer in the list is the winner.
            assertEquals(archers.get(0), archers.get(0).compareTo(archers.get(1)));

        } else if (archers.get(0).getTotalScore() < archers.get(1).getTotalScore()
                || archers.get(0).getWeightedScore() < archers.get(1).getWeightedScore()
                || archers.get(0).getId() < archers.get(1).getId()) {

            // Second archer in the list is the winner.
            assertEquals(archers.get(1), archers.get(0).compareTo(archers.get(1)));

        }

        // Prints out the winner.
        System.out.println("Winner: " + archers.get(0).compareTo(archers.get(1)));
    }

    @Test
    public void generateArchersTest() {
        archerSetup();

        // Checks if the size of the list is equal to LIST_SIZE.
        assertEquals(LIST_SIZE, archers.size());

        // Prints out the list.
        System.out.println(archers.toString());
    }
}
