package model;

import java.util.Iterator;

/**
 * @author Ömer Erdem
 * This class is a implementation of the Iterator interface for the train class.
 */

public class TrainIterator implements Iterator<Wagon> {
    private Wagon currentWagon;

    public TrainIterator(Wagon currentWagon) {
        this.currentWagon = currentWagon;
    }

    /**
     * A method that checks if a train has a nextwagon.
     *
     * @return a boolean value to indicate if the method successfully executed.
     */
    @Override
    public boolean hasNext() {
        return currentWagon != null;
    }

    /**
     * A method that gets the next wagon of the current wagon.
     *
     * @return the next wagon of the current wagon.
     */
    @Override
    public Wagon next() {
        Wagon temp = currentWagon;
        currentWagon = currentWagon.getNextWagon();
        return temp;
    }
}
