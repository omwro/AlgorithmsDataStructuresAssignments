package model;

/**
 * @author Daan Molendijk
 * A class that makes an Freightwagon that extends from a wagon.
 */

public class FreightWagon extends model.Wagon {
    private int maxWeight;

    /**
     * The constructor of the freightwagon class
     *
     * @param wagonId   it is an integer that is used from the parent class.
     * @param maxWeight that is an integer that sets the maximum weight of the wagon.
     */
    public FreightWagon(int wagonId, int maxWeight) {
        super(wagonId);
        this.maxWeight = maxWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }
}
