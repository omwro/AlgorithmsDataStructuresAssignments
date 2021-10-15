package model;

/**
 * @author Ömer Erdem & Daan Molendijk
 * This class function is to make a locomotive
 */

public class Locomotive {
    private int locNumber;
    private int maxWagons;

    /**
     * This is the constructor of the locomotive class
     *
     * @param locNumber is the number of the locomotive
     * @param maxWagons is the maximum of wagons it can pull.
     */
    public Locomotive(int locNumber, int maxWagons) {
        this.locNumber = locNumber;
        this.maxWagons = maxWagons;
    }

    public int getMaxWagons() {
        return maxWagons;
    }

    @Override
    public String toString() {
        return String.format("{Loc %d}", locNumber);
    }
}
