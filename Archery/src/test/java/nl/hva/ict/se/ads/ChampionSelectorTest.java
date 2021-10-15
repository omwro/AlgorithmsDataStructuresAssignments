package nl.hva.ict.se.ads;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChampionSelectorTest {
    protected Comparator<Archer> comparator;
    protected int numberOfArchers = 100;

    @BeforeEach
    public void createComparator() {
        comparator = new ArcherComperator();
    }

    @Test
    public void voidCollectionInsertionQuickTest(){
        List<Archer> unsortedArchersForCollection = Archer.generateArchers(numberOfArchers);
        List<Archer> unsortedArchersForQuick = new ArrayList<>(unsortedArchersForCollection);
        List<Archer> unsortedArchersForSelIns = new ArrayList<>(unsortedArchersForCollection);

        List<Archer> sortedArchersCollection = ChampionSelector.collectionSort(unsortedArchersForCollection, comparator);
        List<Archer> sortedArcherQuick = ChampionSelector.quickSort(unsortedArchersForQuick, comparator);
        List<Archer> sortedArchersSelIns = ChampionSelector.selInsSort(unsortedArchersForSelIns, comparator);

        assertEquals(sortedArchersCollection, sortedArcherQuick);
        assertEquals(sortedArchersCollection, sortedArchersSelIns);
        assertEquals(sortedArcherQuick, sortedArchersSelIns);

    }
}