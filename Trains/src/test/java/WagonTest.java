import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WagonTest {
    @Test
    public void passengerWagonShouldReportCorrectNumberOfSeats() {
        PassengerWagon wagon = new PassengerWagon(0, 13);

        assertEquals(13, wagon.getNumberOfSeats());
    }

    @Test
    public void firstOfThreeWagonsHasTwoAttachedWagons() {
        Wagon one = new PassengerWagon(1, 0);
        Wagon two = new PassengerWagon(2, 0);
        Wagon three = new PassengerWagon(3, 0);
        Wagon four = new PassengerWagon(4, 0);

        one.setNextWagon(two);
        two.setNextWagon(three);
        three.setNextWagon(four);

        assertEquals(3, one.getNumberOfWagonsAttached());
    }
}
