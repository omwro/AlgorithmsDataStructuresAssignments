package model;

/**
 * @author Daan Molendijk
 * A class that makes an Passengerwagon that extends from a wagon.
 */

public class PassengerWagon extends Wagon {
    private int numberOfSeats;

    /**
     * The constructor of the passengerwagon class.
     *
     * @param wagonId       it is an integer that is used from the parent class.
     * @param numberOfSeats it is an integer that is used to indicate
     *                      the amount of seats of a passengerwagon
     */
    public PassengerWagon(int wagonId, int numberOfSeats) {
        super(wagonId);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
