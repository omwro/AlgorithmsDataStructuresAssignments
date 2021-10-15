package model;

import java.util.Iterator;

/**
 * @author Daan Molendijk and Ömer Erdem
 * The function of this class is to make an train, add an firstwagon, check position of wagon,
 * get the total weight and total of seats.
 */

public class Train implements Iterable<Wagon> {
    private Locomotive engine;
    private Wagon firstWagon;
    private String destination;
    private String origin;
    private int numberOfWagons;

    public Train(Locomotive engine, String origin, String destination) {
        this.engine = engine;
        this.destination = destination;
        this.origin = origin;
    }

    public Wagon getFirstWagon() {
        return firstWagon;
    }

    public void setFirstWagon(Wagon firstWagon) {
        this.firstWagon = firstWagon;
    }

    /**
     * A method to reset the amount of wagons attached to the train.
     */
    public void resetNumberOfWagons() {
        if (firstWagon != null) {
            this.numberOfWagons = firstWagon.getNumberOfWagonsAttached() + 1;
        } else {
            this.numberOfWagons = 0;
        }
    }

    /**
     * A method that returns the number of wagons.
     *
     * @return the total amount of wagons attached.
     */
    public int getNumberOfWagons() {
        return numberOfWagons;
    }

    /* three helper methods that are usefull in other methods */

    public boolean hasNoWagons() {
        return (firstWagon == null);
    }

    public boolean isPassengerTrain() {
        return firstWagon instanceof PassengerWagon;
    }

    public boolean isFreightTrain() {
        return firstWagon instanceof FreightWagon;
    }


    /**
     * Method to check the position of a wagon with it's ID
     *
     * @param wagonId a integer that indicates the id of the wagon
     * @return a integer that is the position of the wagon on the train.
     */
    public int getPositionOfWagon(int wagonId) {
        /**
         * @author Nico Tromp & Omer Erdem
         * He gave us an example code and updated our previous code.
         */
        Wagon wagon = getFirstWagon();
        int position = 1;
        while (wagon != null) {
            if (wagon.getWagonId() == wagonId) {
                return position;
            }
            wagon = wagon.getNextWagon();
            position++;
        }
        return -1;
    }

    /**
     * Method to check the position of the wagon with its location
     *
     * @param position the position of the wagon.
     * @return the object thats on that position
     * @throws IndexOutOfBoundsException if the position is not in the array of trains throw an exception
     */
    public Wagon getWagonOnPosition(int position) throws IndexOutOfBoundsException {
        /**
         * @author Nico Tromp & Omer Erdem
         * He gave us an example code and updated our previous code.
         */
        Wagon wagon = getFirstWagon();

        if (position < 0) {
            throw new IllegalArgumentException("Position must be a positive number");
        } else if (position > this.getNumberOfWagons()) {
            throw new IndexOutOfBoundsException("Position is greater than the numbers attached");
        }
        while (--position > 0) {
            wagon = wagon.getNextWagon();
        }
        return wagon;
    }

    /**
     * A method to return the number of seats of a train.
     *
     * @return an integer with all the seats added together.
     */
    public int getNumberOfSeats() {
        Train train = this;
        int totalNumberOfSeats = 0;
        if (isPassengerTrain()) {
            if (train.hasNoWagons()) {
                return 0;
            } else {
                for (Wagon w : this) {
                    totalNumberOfSeats += ((PassengerWagon) w).getNumberOfSeats();
                }
            }
        }
        return totalNumberOfSeats;
    }

    /**
     * A method to return the number of weight of a train.
     *
     * @return an integer with the total weight of al wagons added together.
     */
    public int getTotalMaxWeight() {
        Train train = this;
        int totalNumberOfWeight = 0;
        if (isFreightTrain()) {
            if (train.hasNoWagons()) {
                return 0;
            } else {
                for (Wagon w : this) {
                    totalNumberOfWeight += ((FreightWagon) w).getMaxWeight();
                }
            }
        }
        return totalNumberOfWeight;
    }

    public Locomotive getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(engine.toString());
        Wagon next = this.getFirstWagon();
        while (next != null) {
            result.append(next.toString());
            next = next.getNextWagon();
        }
        result.append(String.format(" with %d wagons and %d seats from %s to %s", getNumberOfWagons(), getNumberOfSeats(), origin, destination));
        return result.toString();
    }

    @Override
    public Iterator<Wagon> iterator() {
        return new TrainIterator(firstWagon);
    }
}
