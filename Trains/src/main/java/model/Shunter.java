package model;

/**
 * @author Ömer Erdem and Daan Molendijk
 * This class takes care of adding wagons to trains, checking if the right wagon is added, detaching wagons &
 * Checking if a wagon is able to be attached due to the max amount of the trains engine.
 */

public class Shunter {


    /* four helper methods than are used in other methods in this class to do checks */

    /**
     * A static method that is used for the public methods, to check if a wagon is suitable to attach to a train
     *
     * @param train is checked if it's an passanger- or freightrain.
     * @param wagon is checked if it's an instance of a class.
     * @return
     */
    private static boolean isSuitableWagon(Train train, Wagon wagon) {
        /**
         * The method isPassengerWagon() and isFreightWagon() are new
         * methods and are basically instance of methods.
         */
        return (train.isPassengerTrain() && wagon.isPassengerWagon()) ||
                (train.isFreightTrain() && wagon.isFreightWagon());
    }

    /**
     * A static method that is used for the public methods, to check if wagon is suitable to attach to another wagon.
     *
     * @param one is checked if it's an instance of a class and is the same class as the second wagon.
     * @param two is checked if it's an instance of a class and is the same class as the first wagon.
     * @return a boolean value to indicate if the method successfully executed.
     */
    private static boolean isSuitableWagon(Wagon one, Wagon two) {
        /**
         * The method isPassengerWagon() and isFreightWagon() are new
         * methods and are basically instance of methods.
         */
        return (one.isPassengerWagon() && two.isPassengerWagon()) ||
                (one.isFreightWagon() && two.isFreightWagon());
    }

    /**
     * A static method that is used for the public methods, to check if train has place for wagons.
     *
     * @param train is the train that is checked for the max amount of wagons that can be attached to it.
     * @param wagon the wagon that is being checked to attach to the train.
     * @return a boolean value to indicate if the method successfully executed.
     */
    private static boolean hasPlaceForWagons(Train train, Wagon wagon) {
        // the engine of a train has a maximum capacity, this method checks for a row of wagons
        if (train.getEngine().getMaxWagons() > (train.getNumberOfWagons() + wagon.getNumberOfWagonsAttached())) {
            return true;
        }
        return false;
    }

    /**
     * A static method that is used for the public methods, to check if train has a place for one wagon.
     *
     * @param train is the train that is checked for the max amount of wagons that can be attached to it.
     * @param wagon the wagon that is being checked to attach to the train.
     * @return a boolean value to indicate if the method successfully executed.
     */
    private static boolean hasPlaceForOneWagon(Train train, Wagon wagon) {
        // the engine of a train has a maximum capacity, this method checks for one wagon
        if (train.getEngine().getMaxWagons() > (train.getNumberOfWagons() + 1)) {
            return true;
        }
        return false;
    }

    /**
     * A static method that attaches a wagon to the back of the train.
     *
     * @param train is the train that the wagon is going to be attached to
     * @param wagon is the wagon that is attached at the back of the train.
     * @return a boolean value to indicate if the method successfully executed.
     */
    public static boolean hookWagonOnTrainRear(Train train, Wagon wagon) {
         /* check if Locomotive can pull new number of Wagons
         check if wagon is correct kind of wagon for train
         find the last wagon of the train
         hook the wagon on the last wagon (see Wagon class)
         adjust number of Wagons of Train */
        if (train.hasNoWagons()) {
            train.setFirstWagon(wagon);
            train.resetNumberOfWagons();
            return true;
        } else if (isSuitableWagon(train, wagon) && hasPlaceForWagons(train, wagon)) {
            Wagon lastwagon = train.getFirstWagon().getLastWagonAttached();
            lastwagon.setNextWagon(wagon);
            train.resetNumberOfWagons();
            return true;
        }
        return false;

    }

    /**
     * A static method that attaches a wagon as the first wagon on the train.
     *
     * @param train is the train that the wagon is going to be attached to.
     * @param wagon is the wagon that is attached to the train.
     * @return a boolean value to indicate if the method successfully executed.
     */
    public static boolean hookWagonOnTrainFront(Train train, Wagon wagon) {
        /* check if Locomotive can pull new number of Wagons
         check if wagon is correct kind of wagon for train
         if Train has no wagons hookOn to Locomotive
         if Train has wagons hookOn to Locomotive and hook firstWagon of Train to lastWagon attached to the wagon
         adjust number of Wagons of Train */
        if (train.hasNoWagons()) {
            train.setFirstWagon(wagon);
            train.resetNumberOfWagons();
            return true;
        } else if (isSuitableWagon(train, wagon) && hasPlaceForWagons(train, wagon) || hasPlaceForOneWagon(train, wagon)) {
            Wagon firstwagon = train.getFirstWagon();
            train.setFirstWagon(wagon);
            train.getFirstWagon().getLastWagonAttached().setNextWagon(firstwagon);
            train.resetNumberOfWagons();
            return true;
        }
        return false;
    }

    /**
     * A static method that attaches a wagon to a wagon.
     *
     * @param first  is the wagon that the second wagon is going to be attached to.
     * @param second is the wagon that is attached to the first wagon.
     * @return a boolean value to indicate if the method successfully executed.
     */
    public static boolean hookWagonOnWagon(Wagon first, Wagon second) {
        /* check if wagons are of the same kind (suitable)
         * if so make second wagon next wagon of first */
        if (isSuitableWagon(first, second)) {
            first.setNextWagon(second);
            return true;
        }
        return false;

    }

    /**
     * A static method that detaches all wagons from a train.
     *
     * @param train is the train were all the wagons are detached from
     * @param wagon is the wagon that the user uses to get all the other wagons.
     * @return a boolean value to indicate if the method successfully executed.
     */
    public static boolean detachAllFromTrain(Train train, Wagon wagon) {
        /* check if wagon is on the train
         detach the wagon from its previousWagon with all its successor
         recalculate the number of wagons of the train */

        if (!train.hasNoWagons()) {
            if (wagon.getPreviousWagon() == null) {
                train.setFirstWagon(null);
                train.resetNumberOfWagons();
                return true;
            } else {
                wagon.getPreviousWagon().setNextWagon(null);
                wagon.setPreviousWagon(null);
                train.resetNumberOfWagons();
                return true;
            }
        }
        return false;

    }

    /**
     * A static method that detaches one wagon from a train.
     *
     * @param train is the train were the wagon is detached from.
     * @param wagon is the wagon that is being detached
     * @return a boolean value to indicate if the method successfully executed
     */
    public static boolean detachOneWagon(Train train, Wagon wagon) {
        /* check if wagon is on the train
         detach the wagon from its previousWagon and hook the nextWagon to the previousWagon
         so, in fact remove the one wagon from the train
        */
        if (!train.hasNoWagons()) {
            if (wagon.getNextWagon() != null) {
                if (wagon.getPreviousWagon() == null) {
                    train.setFirstWagon(wagon.getNextWagon());
                    wagon.getNextWagon().setPreviousWagon(null);
                    wagon.setNextWagon(null);
                    train.resetNumberOfWagons();
                    return true;
                } else {
                    wagon.getNextWagon().setPreviousWagon(null);
                    wagon.getPreviousWagon().setNextWagon(wagon.getNextWagon());
                    wagon.setNextWagon(null);
                    wagon.setPreviousWagon(null);
                    train.resetNumberOfWagons();
                    return true;
                }
            } else {
                wagon.getPreviousWagon().setNextWagon(null);
                wagon.setPreviousWagon(null);
                train.resetNumberOfWagons();
            }
        }
        return false;
    }

    /**
     * A static method that moves all the wagons from one train to another train.
     *
     * @param from  is the train that the wagons are moved from
     * @param to    is the train that the wagons are moved to.
     * @param wagon is the wagon that the user uses to get all the other wagons.
     * @return a boolean value to indicate if the method successfully executed
     */
    public static boolean moveAllFromTrain(Train from, Train to, Wagon wagon) {
        /* check if wagon is on train from
         check if wagon is correct for train and if engine can handle new wagons
         detach Wagon and all successors from train from and hook at the rear of train to
         remember to adjust number of wagons of trains */
        if (detachAllFromTrain(from, wagon)) {
            if (to.hasNoWagons()) {
                to.setFirstWagon(wagon);
                to.resetNumberOfWagons();
                from.resetNumberOfWagons();
                return true;
            } else {
                Wagon lastWagon = to.getFirstWagon().getLastWagonAttached();
                lastWagon.setNextWagon(wagon);
                to.resetNumberOfWagons();
                from.resetNumberOfWagons();
                return true;
            }
        }
        return false;
    }

    /**
     * A static method that moves a wagon from a train to another train.
     *
     * @param from  is the train that the wagon is moved from.
     * @param to    is the train that the wagon is moved to.
     * @param wagon is the wagon that is being moved.
     * @return a boolean value to indicate if the method successfully executed
     */
    public static boolean moveOneWagon(Train from, Train to, Wagon wagon) {
        // detach only one wagon from train from and hook on rear of train to
        // do necessary checks and adjustments to trains and wagon
        if (detachOneWagon(from, wagon)) {
            if (hookWagonOnTrainRear(to, wagon)) {
                return true;
            }
            return true;
        }
        return false;
    }
}
