package model;

public class Location {

    private int x, y;
    private Station station;

    /**
     * This is the constructor of the Location class.
     * @param x - position on the roster.
     * @param y - position on the roster.
     * @param station the station that the location is for.
     */
    public Location(int x, int y, Station station) {
        this.x = x;
        this.y = y;
        this.station = station;
    }

    public Station getStation() {
        return station;
    }


    /**
     * This method calculates the travelTime.
     * @param locationOne is an Location object
     * @param locationTwo is an Location object
     * @return a double value.
     */
    public static double travelTime(Location locationOne, Location locationTwo) {
        double minutes = 0.0;

        // Comparing if one of the x number is bigger.
        if(locationOne.x > locationTwo.x) {
            minutes +=  (locationOne.x - locationTwo.x) * 1.5;
        } else if (locationOne.x < locationTwo.x) {
            minutes += (locationTwo.x - locationOne.x) * 1.5;
        }

        // Comparing if one of the y number is bigger.
        if(locationOne.y > locationTwo.y) {
            minutes += (locationOne.y - locationTwo.y) * 1.5;
        } else if (locationOne.y < locationTwo.y) {
            minutes += (locationTwo.y - locationOne.y) * 1.5;
        }

        return minutes;
    }

    @Override
    public String toString() {
        return "X:"+x+" Y:"+y+" Station:"+station;
    }
}
