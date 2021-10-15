import com.sun.source.tree.IfTree;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.Map;
import java.util.TreeMap;

public class FIFOCashier extends Cashier {
    public int checkoutTimePerCustomer;
    public int checkoutTimePerItem;

    public FIFOCashier(String name) {
        super(name);
        this.checkoutTimePerCustomer = 20;
        this.checkoutTimePerItem = 2;
    }

    @Override
    public int expectedCheckOutTime(int numberOfItems) {
        if (numberOfItems > 0) {
            return numberOfItems * this.checkoutTimePerItem + checkoutTimePerCustomer;
        }
        return 0;
    }

    @Override
    public int expectedWaitingTime(Customer customer) {
        int expectedWaitingTime;
        // All Customers
        expectedWaitingTime = 0;
        if (currentCustomer != null){ // er is een klant aan de beurt
            int currentCustomerTimePassed = currentTime.getSecond() - currentCustomer.getQueuedAt().getSecond();
            expectedWaitingTime = expectedCheckOutTime(currentCustomer.getNumberOfItems()) - currentCustomerTimePassed;
            currentCustomer.setActualCheckOutTime(expectedCheckOutTime(currentCustomer.getNumberOfItems()));
            currentCustomer.setActualWaitingTime(expectedWaitingTime);
        }
        for (Customer waitingCustomer : waitingQueue) {
            waitingCustomer.setActualCheckOutTime(expectedCheckOutTime(waitingCustomer.getNumberOfItems()));
            waitingCustomer.setActualWaitingTime(expectedWaitingTime);
            expectedWaitingTime += expectedCheckOutTime(waitingCustomer.getNumberOfItems());
        }

        // Specific Customer
        expectedWaitingTime = 0;
        if (currentCustomer != null){ // er is een klant aan de beurt
            int currentCustomerTimePassed = currentTime.getSecond() - currentCustomer.getQueuedAt().getSecond();
            expectedWaitingTime = expectedCheckOutTime(currentCustomer.getNumberOfItems()) - currentCustomerTimePassed;
        }
        for (Customer waitingCustomer : waitingQueue) {
            if (waitingCustomer == customer) {
                break;
            }
            expectedWaitingTime += expectedCheckOutTime(waitingCustomer.getNumberOfItems());
        }
        return expectedWaitingTime;
    }

    @Override
    public void doTheWorkUntil(LocalTime targetTime) {
        if(this.currentTime.isBefore(targetTime)){
            // Als er een Customer aan de beurt is
            if (currentCustomer != null) {
                LocalTime queuedAtPlusActualCheckoutTime = currentCustomer.getQueuedAt().plusSeconds(currentCustomer.getActualCheckOutTime());
                // Als er tijd over is
                if (targetTime.toSecondOfDay() < queuedAtPlusActualCheckoutTime.toSecondOfDay()){
                    // Er is tijd over
                    int timeLeft = queuedAtPlusActualCheckoutTime.toSecondOfDay() - targetTime.toSecondOfDay();
                }
                // Als de tijd op is
                else {
                    // Als er een volgende klant is
                    if (waitingQueue.peek() != null){
                        doWork();
                    }
                    // Als er geen volgende klant is
                    else {
                        // Er is geen klant bij de kassa
                        currentCustomer = null;
                    }
                }
            }
            // Als er geen Customer aan de beurt is
            else {
                Duration duration = Duration.between(currentTime, targetTime);
                setTotalIdleTime(getTotalIdleTime () + (int) duration.toSeconds());
                // Als er Customers in de Wachtrij staan
                if (waitingQueue.peek() != null){
                    doWork();
                }
                // Als er geen Customers in de Wachtrij staan
                else {

                }
            }
            setCurrentTime(targetTime);
            /*Duration duration = Duration.between(currentTime, targetTime);
            totalIdleTime = (int) duration.getSeconds() - expectedWaitingTime(currentCustomer);*/
        }
    }

    public void doWork(){
        currentCustomer = waitingQueue.poll(); // De eerste beste Customer pakken
        expectedWaitingTime(currentCustomer); // De actual checkout- & waitingtime updaten
    }
}
