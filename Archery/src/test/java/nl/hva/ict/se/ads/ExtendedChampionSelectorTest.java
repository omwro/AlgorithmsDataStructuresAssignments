package nl.hva.ict.se.ads;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Place all your own tests for ChampionSelector in this class. Tests in any other class will be ignored!
 */
public class ExtendedChampionSelectorTest extends ChampionSelectorTest {

    @Test
    public void collectionSort() {

        List<Archer> unsortedArchersForCollection = Archer.generateArchers(numberOfArchers);
        long startTime = System.currentTimeMillis();

        List<Archer> sortedArchersCollection = ChampionSelector.collectionSort(unsortedArchersForCollection, comparator);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("The elapsed time with " + numberOfArchers + " archers (Collection): " + elapsedTime + "ms");
        System.out.println(sortedArchersCollection);

    }

    @Test
    public void quickTest(){
        List<Archer> unsortedArchersForQuick = Archer.generateArchers(numberOfArchers);
        long startTime = System.currentTimeMillis();

        List<Archer> sortedArcherQuick = ChampionSelector.quickSort(unsortedArchersForQuick, comparator);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("The elapsed time with " + numberOfArchers + " archers (Quick): " + elapsedTime + "ms");
        System.out.println(sortedArcherQuick);

    }

    @Test
    public void insertionTest(){
        List<Archer> unsortedArchersForSelIns = Archer.generateArchers(numberOfArchers);
        long startTime = System.currentTimeMillis();

        List<Archer> sortedArchersSelIns = ChampionSelector.selInsSort(unsortedArchersForSelIns, comparator);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("The elapsed time with " + numberOfArchers + " archers (Insertion): " + elapsedTime + "ms");
        System.out.println(sortedArchersSelIns);
    }
}
