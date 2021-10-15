import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainTest {

    @Test
    public void reportPassengerTrainCorrectly() {
        Train train = new Train(new Locomotive(0, 0), "Haarlem", "Amsterdam");
        Wagon wagon = new PassengerWagon(0, 0);
        train.setFirstWagon(wagon);

        assertTrue(train.isPassengerTrain());
    }

    @Test
    public void returnsCorrectWagons() {
        Train train = new Train(new Locomotive(0,3), "Haarlem", "Amsterdam");
        Wagon first = new PassengerWagon(1, 10);
        Wagon second = new PassengerWagon(2, 2);
        Wagon third = new PassengerWagon(3, 30);

        train.setFirstWagon(first);
        first.setNextWagon(second);
        second.setNextWagon(third);


        System.out.println("Correct number of wagons attached: "+train.getNumberOfWagons());
        assertEquals(3, train.getNumberOfWagons());

    }

    @Test
    public void returnNumberOfSeats(){
        Train train = new Train(new Locomotive(0,3), "Haarlem", "Amsterdam");
        Wagon first = new PassengerWagon(1, 10);
        Wagon second = new PassengerWagon(2, 2);
        Wagon third = new PassengerWagon(3, 30);

        train.setFirstWagon(first);
        first.setNextWagon(second);
        second.setNextWagon(third);

        assertEquals(42, train.getNumberOfSeats());
    }

    @Test
    public void returnNumberOfWeight(){
        Train train = new Train(new Locomotive(0,3), "Haarlem", "Amsterdam");
        Wagon first = new FreightWagon(1, 126);
        Wagon second = new FreightWagon(2, 270);
        Wagon third = new FreightWagon(3, 285);

        train.setFirstWagon(first);
        first.setNextWagon(second);
        second.setNextWagon(third);

        assertEquals(681, train.getTotalMaxWeight());
    }

    @Test
    public void getPositionOfWagon(){
        Train train = new Train(new Locomotive(0,3), "Haarlem", "Amsterdam");
        Wagon first = new PassengerWagon(1, 10);
        Wagon second = new PassengerWagon(2, 2);
        Wagon third = new PassengerWagon(3, 30);

        train.setFirstWagon(first);
        first.setNextWagon(second);
        second.setNextWagon(third);

        assertEquals(second, train.getWagonOnPosition(2));
    }

    @Test
    public void getPositonOfWagonWithID(){
        Train train = new Train(new Locomotive(0,3), "Haarlem", "Amsterdam");
        Wagon first = new PassengerWagon(1, 10);
        Wagon second = new PassengerWagon(2, 2);
        Wagon third = new PassengerWagon(3, 30);
        Wagon fifth = new PassengerWagon(5, 24);

        train.setFirstWagon(first);
        first.setNextWagon(second);
        second.setNextWagon(third);
        third.setNextWagon(fifth);

        assertEquals(4, train.getPositionOfWagon(5));
    }

}
