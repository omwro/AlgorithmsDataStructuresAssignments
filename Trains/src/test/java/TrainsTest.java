import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class TrainsTest {

    private ArrayList<PassengerWagon> pwList;
    private ArrayList<FreightWagon> pwList2;
    private Train firstPassengerTrain;
    private Train secondPassengerTrain;
    private Train firstFreightTrain;
    private Train secondFreightTrain;

    @BeforeEach
    private void makeListOfPassengerWagons() {
        pwList = new ArrayList<>();
        pwList.add(new PassengerWagon(3, 100));
        pwList.add(new PassengerWagon(24, 100));
        pwList.add(new PassengerWagon(17, 140));
        pwList.add(new PassengerWagon(32, 150));
        pwList.add(new PassengerWagon(38, 140));
        pwList.add(new PassengerWagon(11, 100));

        pwList2 = new ArrayList<>();
        pwList2.add(new FreightWagon(101, 1000));
        pwList2.add(new FreightWagon(102, 2000));
        pwList2.add(new FreightWagon(103, 3000));
        pwList2.add(new FreightWagon(104, 4000));
        pwList2.add(new FreightWagon(105, 5000));
        pwList2.add(new FreightWagon(106, 6000));
        pwList2.add(new FreightWagon(107, 5000));
        pwList2.add(new FreightWagon(108, 6000));
        pwList2.add(new FreightWagon(109, 5000));
        pwList2.add(new FreightWagon(110, 6000));
    }

    private void makeTrains() {
        Locomotive thomas = new Locomotive(2453, 7);
        Locomotive gordon = new Locomotive(5277, 8);
        Locomotive emily = new Locomotive(4383, 11);
        Locomotive rebecca = new Locomotive(2275, 4);

        firstPassengerTrain = new Train(thomas, "Amsterdam", "Haarlem");
        for (Wagon w : pwList) {
            Shunter.hookWagonOnTrainRear(firstPassengerTrain, w);
        }
        secondPassengerTrain = new Train(gordon, "Joburg", "Cape Town");

        firstFreightTrain = new Train(emily, "Moskow", "Vladivostok");
        for (Wagon w : pwList2) {
            Shunter.hookWagonOnTrainRear(firstFreightTrain, w);
        }

        secondFreightTrain = new Train(rebecca, "Los Angelos", "Road Island");
    }

    @Test
    public void checkNumberOfWagonsAttachedToFirstWagon() {
        makeTrains();
        assertEquals(5, firstPassengerTrain.getFirstWagon().getNumberOfWagonsAttached(), "Firstwagon should have 5 wagons attached to it");
        System.out.println("Number of wagons attached to first wagon(firstPtrain): " + firstPassengerTrain.getFirstWagon().getNumberOfWagonsAttached());

        assertEquals(9, firstFreightTrain.getFirstWagon().getNumberOfWagonsAttached(), "Firstwagon should have 9 wagons attached to it");
        System.out.println("Number of wagons attached to first wagon(firstFtrain): " + firstFreightTrain.getFirstWagon().getNumberOfWagonsAttached());
    }

    @Test
    public void checkNumberOfWagonsOnPassengerTrain() {
        makeTrains();
        assertEquals(6, firstPassengerTrain.getNumberOfWagons(), "Train should have 6 wagons");
        System.out.println("Number of wagons(firstPtrain): " + firstPassengerTrain.getNumberOfWagons());

        assertEquals(0, secondPassengerTrain.getNumberOfWagons(), "Train should have 0 wagons");
        System.out.println("Number of wagons(secondPtrain): " + secondPassengerTrain.getNumberOfWagons());
    }

    @Test
    public void checkNumberOfWagonsOnFreightTrain(){
        makeTrains();
        assertEquals(10, firstFreightTrain.getNumberOfWagons(), "Train should have 10 wagons");
        System.out.println("Number of wagons(firstFtrain): " + firstFreightTrain.getNumberOfWagons());

        assertEquals(0, secondFreightTrain.getNumberOfWagons(), "Train should have 0 wagons");
        System.out.println("Number of wagons(secondFtrain): " + secondFreightTrain.getNumberOfWagons() + "\n");
    }

    @Test
    public void checkNumberOfSeatsOnTrain() {
        makeTrains();
        assertEquals(730, firstPassengerTrain.getNumberOfSeats());
        System.out.println("Total number of seats on train(firstPtrain): " + firstPassengerTrain.getNumberOfSeats());

        assertEquals(0, secondPassengerTrain.getNumberOfSeats());
        System.out.println("Total number of seats on train(secondPtrain): " + secondPassengerTrain.getNumberOfSeats() + "\n");
    }

    @Test
    public void checkNumberOfWeightOnTrain() {
        makeTrains();
        assertEquals(43000, firstFreightTrain.getTotalMaxWeight());
        System.out.println("Total number of weight on train(firstFtrain): " + firstFreightTrain.getTotalMaxWeight());

        assertEquals(0, secondFreightTrain.getTotalMaxWeight());
        System.out.println("Total number of weight on train(secondFtrain): " + secondPassengerTrain.getTotalMaxWeight() + "\n");
    }

    @Test
    public void checkPositionOfWagons() {
        makeTrains();
        int position = 1;
        for (PassengerWagon pw : pwList) {
            assertEquals(position, firstPassengerTrain.getPositionOfWagon(pw.getWagonId()));
            System.out.println("Position of wagon(firstPtrain): " + firstPassengerTrain.getPositionOfWagon(pw.getWagonId()));

            assertEquals(-1, secondPassengerTrain.getPositionOfWagon(pw.getWagonId()));
            System.out.println("Position of wagon(secondPtrain): " + secondPassengerTrain.getPositionOfWagon(pw.getWagonId()));
            position++;
        }
        System.out.println("");
    }

    @Test
    public void checkHookOneWagonOnPassengerTrainFront() {
        makeTrains();

        System.out.println("Old number of wagons attached(firstPtrain): " + firstPassengerTrain.getNumberOfWagons());
        Shunter.hookWagonOnTrainFront(firstPassengerTrain, new PassengerWagon(21, 140));

        assertEquals(7, firstPassengerTrain.getNumberOfWagons(), "Train should have 7 wagons");
        System.out.println("New number of wagons attached(firstPtrain): " + firstPassengerTrain.getNumberOfWagons());

        assertEquals(1, firstPassengerTrain.getPositionOfWagon(21));
        System.out.println("Location of first wagon(firstPtrain): " + firstPassengerTrain.getPositionOfWagon(21));
    }

    @Test
    public void checkHookOneWagonFreightTrainFront(){
        makeTrains();

        System.out.println("Old number of wagons attached(firstFtrain): " + firstFreightTrain.getNumberOfWagons());
        Shunter.hookWagonOnTrainFront(firstFreightTrain, new FreightWagon(21, 140));

        assertEquals(11, firstFreightTrain.getNumberOfWagons(), "Train should have 11 wagons");
        System.out.println("New number of wagons attached(firstFtrain): " + firstFreightTrain.getNumberOfWagons());

        assertEquals(1, firstFreightTrain.getPositionOfWagon(21));
        System.out.println("Location of first wagon(firstFtrain): " + firstFreightTrain.getPositionOfWagon(21) + "\n");
    }

    @Test
    public void checkHookRowWagonsOnPassengerTrainRearFalse() {
        makeTrains();

        Wagon w1 = new PassengerWagon(11, 100);

        Shunter.hookWagonOnWagon(w1, new PassengerWagon(43, 140));
        Shunter.hookWagonOnTrainRear(firstPassengerTrain, w1);

        assertEquals(6, firstPassengerTrain.getNumberOfWagons(), "Train should have still have 6 wagons, capacity reached");
        System.out.println("After failed attempt to add wagon with id 43(firstPtrain): " + firstPassengerTrain.getNumberOfWagons());

        assertEquals(-1, firstPassengerTrain.getPositionOfWagon(43));
        System.out.println("Position after failed attempt(firstPtrain): " + firstPassengerTrain.getPositionOfWagon(43));
    }

    @Test
    public void checkHookRowWagonsOnFreightTrainRearFalse(){
        makeTrains();

        Wagon w2 = new FreightWagon(11, 100);

        Shunter.hookWagonOnWagon(w2, new FreightWagon(28, 500));
        Shunter.hookWagonOnTrainRear(firstFreightTrain, w2);

        assertEquals(10, firstFreightTrain.getNumberOfWagons(), "Train should have still have 10 wagons");
        System.out.println("After failed attempt to add wagon with id 28(firstFtrain): " + firstFreightTrain.getNumberOfWagons());

        assertEquals(-1, firstFreightTrain.getPositionOfWagon(28));
        System.out.println("Position after failed attempt(firstFtrain): " + firstFreightTrain.getPositionOfWagon(28) + "\n");
    }

    @Test
    public void checkMoveOnePassengerWagon() {
        makeTrains();
        Shunter.moveOneWagon(firstPassengerTrain, secondPassengerTrain, pwList.get(3));

        assertEquals(5, firstPassengerTrain.getNumberOfWagons(), "Train should have 5 wagons");
        System.out.println("Check amount of wagons after move(firstPtrain): " + firstPassengerTrain.getNumberOfWagons());

        assertEquals(-1, firstPassengerTrain.getPositionOfWagon(32));
        System.out.println("Position of wagon 32(firstPtrain): " + firstPassengerTrain.getPositionOfWagon(32));

        assertEquals(1, secondPassengerTrain.getNumberOfWagons(), "Train should have 1 wagon");
        System.out.println("Check amount of wagons after move(secondPtrain): " + secondPassengerTrain.getNumberOfWagons());

        assertEquals(1, secondPassengerTrain.getPositionOfWagon(32));
        System.out.println("Position of wagon 32(secondPtrain): " + secondPassengerTrain.getPositionOfWagon(32));
    }

    @Test
    public void checkMoveOneFreightWagon() {
        makeTrains();

        Shunter.moveOneWagon(firstFreightTrain, secondFreightTrain, pwList2.get(3));
        assertEquals(9, firstFreightTrain.getNumberOfWagons(), "Train should have 9 wagons");
        System.out.println("Check amount of wagons after move(firstFtrain): " + firstFreightTrain.getNumberOfWagons());

        assertEquals(-1, firstFreightTrain.getPositionOfWagon(104));
        System.out.println("Position of wagon 32(firstFtrain): " + firstFreightTrain.getPositionOfWagon(104));

        assertEquals(1, secondFreightTrain.getNumberOfWagons(), "Train should have 1 wagon");
        System.out.println("Check amount of wagons after move(secondFtrain): " + secondFreightTrain.getNumberOfWagons());

        assertEquals(1, secondFreightTrain.getPositionOfWagon(104));
        System.out.println("Position of wagon 32(secondFtrain): " + secondFreightTrain.getPositionOfWagon(104) + "\n");
    }

    @Test
    public void checkMoveAllFromPassangerTrain() {
        makeTrains();

        System.out.println("Number of wagons before detach (firstPtrain): " + firstPassengerTrain.getNumberOfWagons());
        Shunter.detachAllFromTrain(firstPassengerTrain, pwList.get(0));

        assertEquals(0, firstPassengerTrain.getNumberOfWagons(), "Train should have 0 wagons");
        System.out.println("Number of wagons after detach (firstPtrain): " + firstPassengerTrain.getNumberOfWagons());
    }

    @Test
    public void checkMoveAllFromFreightTrain() {
        makeTrains();

        System.out.println("Number of wagons before detach (firstFtrain): " + firstFreightTrain.getNumberOfWagons());
        Shunter.detachAllFromTrain(firstFreightTrain, pwList2.get(0));

        assertEquals(0, firstFreightTrain.getNumberOfWagons(), "Train should have 0 wagons");
        System.out.println("Number of wagons after detach(firstFtrain): " + firstFreightTrain.getNumberOfWagons() + "\n");
    }

    @Test
    public void checkMoveOneWagonFromTrain() {
        makeTrains();

        System.out.println("Number of wagons before detach (firstPtrain): " + firstPassengerTrain.getNumberOfWagons());
        Shunter.detachOneWagon(firstPassengerTrain, firstPassengerTrain.getWagonOnPosition(6));

        assertEquals(5, firstPassengerTrain.getNumberOfWagons());
        System.out.println("Number of wagons after detach (firstPtrain): " + firstPassengerTrain.getNumberOfWagons());
    }

    @Test
    public void checkMoveRowOfPassengerWagons() {
        makeTrains();
        Wagon w1 = new PassengerWagon(11, 100);
        Shunter.hookWagonOnWagon(w1, new PassengerWagon(43, 140));

        Shunter.hookWagonOnTrainRear(secondPassengerTrain, w1);
        Shunter.moveAllFromTrain(firstPassengerTrain, secondPassengerTrain, pwList.get(2));

        assertEquals(2, firstPassengerTrain.getNumberOfWagons(), "Train should have 2 wagons");
        System.out.println("Number of wagons (firstPtrain): " + firstPassengerTrain.getNumberOfWagons());

        assertEquals(2, firstPassengerTrain.getPositionOfWagon(24));
        System.out.println("Position of wagon with id 24 (firstPtrain): " + firstPassengerTrain.getPositionOfWagon(24));

        assertEquals(6, secondPassengerTrain.getNumberOfWagons(), "Train should have 6 wagons");
        System.out.println("Number of wagons (secondPtrain): " + secondPassengerTrain.getNumberOfWagons());

        assertEquals(4, secondPassengerTrain.getPositionOfWagon(32));
        System.out.println("Position of wagon with id 32 (secondPtrain): " + secondPassengerTrain.getPositionOfWagon(32));
    }

    @Test
    public void checkMoveRowofFreightWagons() {
        makeTrains();

        Wagon w2 = new FreightWagon(11, 100);
        Shunter.hookWagonOnWagon(w2, new FreightWagon(43, 140));

        Shunter.hookWagonOnTrainRear(secondFreightTrain, w2);
        Shunter.moveAllFromTrain(firstFreightTrain, secondFreightTrain, pwList2.get(2));

        assertEquals(2, firstFreightTrain.getNumberOfWagons(), "Train should have 2 wagons");
        System.out.println("Number of wagons (firstFtrain): " + firstFreightTrain.getNumberOfWagons());

        assertEquals(2, firstFreightTrain.getPositionOfWagon(102));
        System.out.println("Position of wagon with id 102 (firstFtrain): " + firstFreightTrain.getPositionOfWagon(102));

        assertEquals(10, secondFreightTrain.getNumberOfWagons(), "Train should have 10 wagons");
        System.out.println("Number of wagons (secondFtrain): " + secondFreightTrain.getNumberOfWagons());

        assertEquals(4, secondFreightTrain.getPositionOfWagon(104));
        System.out.println("Position of wagon with id 102 (secondFtrain): " + secondFreightTrain.getPositionOfWagon(104) + "\n");
    }
}
