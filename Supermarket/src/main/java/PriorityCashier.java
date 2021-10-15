import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class PriorityCashier extends FIFOCashier {
    private int maxNumPriorityItems;
    //public static Map<Customer, Integer> waitTimeList;

    public PriorityCashier(String name, int maxNumPriorityItems) {
        super(name);
        this.maxNumPriorityItems = maxNumPriorityItems;
        //waitTimeList = new TreeMap<>();
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
            if (waitingCustomer.getNumberOfItems() <= maxNumPriorityItems) {
                expectedWaitingTime += expectedCheckOutTime(waitingCustomer.getNumberOfItems());
            }
            expectedWaitingTime += expectedCheckOutTime(waitingCustomer.getNumberOfItems());
        }

        // Specific Customer
        expectedWaitingTime = 0;
        if (currentCustomer != null){
            int currentCustomerTimePassed = currentTime.getSecond() - currentCustomer.getQueuedAt().getSecond();
            expectedWaitingTime = expectedCheckOutTime(currentCustomer.getNumberOfItems()) - currentCustomerTimePassed;
        }
        for (Customer waitingCustomer : waitingQueue) {
            if (waitingCustomer == customer) {
                break;
            }
            if (waitingCustomer.getNumberOfItems() <= maxNumPriorityItems) {
                expectedWaitingTime += expectedCheckOutTime(waitingCustomer.getNumberOfItems());
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
                        doWork(targetTime);
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
                    doWork(targetTime);
                }
                // Als er geen Customers in de Wachtrij staan
                else {

                }
            }
            setCurrentTime(targetTime);
        }
    }

    public void doWork(LocalTime targetTime){
        currentCustomer = waitingQueue.poll(); // De eerste beste Customer pakken
        expectedWaitingTime(currentCustomer); // De actual checkout- & waitingtime updaten
        int waitedTime = targetTime.toSecondOfDay() - startWaitTimeList.get(currentCustomer).toSecondOfDay();
        waitTimeListCashier.put(currentCustomer, waitedTime);
    }
}
