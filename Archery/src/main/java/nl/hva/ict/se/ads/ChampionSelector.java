package nl.hva.ict.se.ads;

import java.util.*;

/**
 * @author Unknown, Screencut, Ludisposed, Ömer Erdem & Daan Molendijk
 * Given a list of Archer's this class can be used to sort the list using one of three sorting algorithms.
 */
public class ChampionSelector {
    /**
     * This method uses either selection sort or insertion sort for sorting the archers.
     */
    public static List<Archer> selInsSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        /**
         * @source https://www.codexpedia.com/java/java-comparator-with-insertion-sort/
         * We got some help from @source with completing the Insertion sort with a implementation of comparators
         */
        // number of values in place
        int sortedNumber = 1;
        // general index
        int index;
        // length of the array
        int length = archers.size();
        while (sortedNumber < length) {
            // take the first unsorted value
            Archer temp = archers.get(sortedNumber);
            // ...and insert it among the sorted:
            for (index = sortedNumber; index > 0; index--) {
                if (scoringScheme.compare(temp, archers.get(index - 1)) < 0) {
                    archers.set(index, archers.get(index - 1));
                } else {
                    break;
                }
            }
            // reinsert values
            archers.set(index, temp);
            sortedNumber++;
        }
        return archers;
    }

    /**
     * This method uses quick sort for sorting the archers.
     */
    public static List<Archer> quickSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        /**
         * @source https://codereview.stackexchange.com/questions/181391/sorting-an-arraylist-of-vehicles-using-quick-sort
         * We got the method from @source that helped us understand and make the quicksort method.
         */

        // Checks if the array is more than one.
        if(archers.size() <= 1){
            return archers;
        }

        // Makes the individual arrays
        List<Archer> sorted = new ArrayList<>();
        List<Archer> lesser = new ArrayList<>();
        List<Archer> greater = new ArrayList<>();

        // Asigns the pivot.
        Archer pivot = archers.get(archers.size()-1);

        // Checks if a archer is greater or lesser than the pivot.
        for (int i = 0; i < archers.size() - 1; i++) {
            if(scoringScheme.compare(archers.get(i), pivot) < 0){
                lesser.add(archers.get(i));
            } else {
                greater.add(archers.get(i));
            }
        }

        // quicksorts the lesser and greater arrayLists
        lesser = quickSort(lesser, scoringScheme);
        greater = quickSort(greater, scoringScheme);

        // Adds the pivot and greater arrayList to the lesser arrayList
        lesser.add(pivot);
        lesser.addAll(greater);

        // The sorted arrayList is set to the lesser arrayList.
        sorted = lesser;

        // Returns the sorted arrayList.
        return sorted;
    }

    /**
     * This method uses the Java collections sort algorithm for sorting the archers.
     */
    public static List<Archer> collectionSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        archers.sort(scoringScheme);
        return archers;
    }

    /**
     * This method uses quick sort for sorting the archers in such a way that it is able to cope with an Iterator.
     *
     * <b>THIS METHOD IS OPTIONAL</b>
     */
    public static Iterator<Archer> quickSort(Iterator<Archer> archers, Comparator<Archer> scoringScheme) {
        return null;
    }

}
