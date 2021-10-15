package model;

/**
 * @author Ömer Erdem & Daan Molendijk
 * This class is the parent class of freightwagon and passengerwagon. It's function is to beable to set next- and
 * previous wagons and be able to get the number of wagons attached.
 */

public abstract class Wagon {
    private int wagonId;
    private Wagon previousWagon;
    private Wagon nextWagon;
    private int numberOfWagonsAttached;

    public Wagon(int wagonId) {
        this.wagonId = wagonId;
    }

    /**
     * This method gets the last wagon that is assigned to the train.
     *
     * @return the last wagon object.
     */
    public Wagon getLastWagonAttached() {
        if (!hasNextWagon()) {
            return this;
        }
        return getNextWagon().getLastWagonAttached();
    }

    public void setNextWagon(Wagon nextWagon) {
        if (nextWagon != null) {
            this.nextWagon = nextWagon;
            nextWagon.setPreviousWagon(this);
        } else {
            this.nextWagon = null;
        }
    }

    public Wagon getPreviousWagon() {
        return previousWagon;
    }

    public void setPreviousWagon(Wagon previousWagon) {
        this.previousWagon = previousWagon;
    }

    public Wagon getNextWagon() {
        return nextWagon;
    }

    public int getWagonId() {
        return wagonId;
    }

    /**
     * This method is a new method. It's function is to (re)calculate
     * the number of wagons attached.
     *
     * @return a number that indicates the amount of wagons.
     */
    public void calculateNumberOfWagonsAttached() {
        int number = 0;
        Wagon wagon = this;
        while (wagon.hasNextWagon()) {
            number++;
            wagon = wagon.getNextWagon();
        }
        this.numberOfWagonsAttached = number;
    }

    public int getNumberOfWagonsAttached() {
        calculateNumberOfWagonsAttached();
        return numberOfWagonsAttached;
    }

    public boolean hasNextWagon() {
        return !(nextWagon == null);
    }

    public boolean hasPreviousWagon() {
        return !(previousWagon == null);
    }

    public boolean isPassengerWagon() {
        return this instanceof PassengerWagon;
    }

    public boolean isFreightWagon() {
        return this instanceof FreightWagon;
    }

    public PassengerWagon passengerWagon() {
        return (PassengerWagon) this;
    }

    public FreightWagon freightWagon() {
        return (FreightWagon) this;
    }

    @Override
    public String toString() {
        return String.format("[Wagon %d]", wagonId);
    }
}
