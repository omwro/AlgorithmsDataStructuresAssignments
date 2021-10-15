/**
 * Supermarket Customer check-out and Cashier simulation
 * @author  hbo-ict@hva.nl
 */

import java.time.LocalTime;
import java.util.*;

public abstract class Cashier{

    private String name;                    // name of the cashier, for results identification
    protected Queue<Customer> waitingQueue; // queue of waiting customers
    protected LocalTime currentTime;        // tracks time for the cashier during simulation
    protected int totalIdleTime;            // tracks cumulative seconds when there was no work for the cashier
    protected int maxQueueLength;           // tracks the maximum number of customers at the cashier at any time
                                    // during simulation. Includes both waiting customers and the customer being served
    protected Customer currentCustomer;
    public Map<Customer, LocalTime> startWaitTimeList;
    public Map<Customer, Integer> waitTimeListCashier;

    protected Cashier(String name) {
        this.name = name;
        this.waitingQueue = new PriorityQueue<>();
        this.totalIdleTime = 0;
        this.maxQueueLength = 0;
        this.currentCustomer = null;
        startWaitTimeList = new TreeMap<>();
        waitTimeListCashier = new TreeMap<>();
    }

    /**
     * restart the state if simulation of the cashier to initial time
     * with empty queues
     * @param currentTime
     */
    public void reStart(LocalTime currentTime) {
        this.waitingQueue.clear();
        this.currentTime = currentTime;
        this.totalIdleTime = 0;
        this.maxQueueLength = 0;
    }

    /**
     * calculate the expected nett checkout time of a customer with a given number of items
     * this may be different for different types of Cashiers
     * @param numberOfItems
     * @return
     */
    public abstract int expectedCheckOutTime(int numberOfItems);

    /**
     * calculate the currently expected waiting time of a given customer for this cashier.
     * this may depend on:
     * a) the type of cashier,
     * b) the remaining work of the cashier's current customer(s) being served
     * c) the position that the given customer may obtain in the queue
     * d) and the workload of the customers in the waiting queue in front of the given customer
     * @param customer
     * @return
     */
    public abstract int expectedWaitingTime(Customer customer);

    /**
     * proceed the cashier's work until the given targetTime has been reached
     * this work may involve:
     * a) continuing or finishing the current customer(s) begin served
     * b) serving new customers that are waiting on the queue.
     * c) sitting idle, taking a break until time has reached targetTime,
     *      after which new customers may arrive.
     * @param targetTime
     */
    public abstract void doTheWorkUntil(LocalTime targetTime);

    /**
     * add a new customer to the queue of the cashier
     * the position of the new customer in the queue will depend on the priority configuration of the queue
     * @param customer
     */
    public void add(Customer customer) {
        if (customer.getNumberOfItems() > 0 ) {
            if (this.getName().startsWith("FIFO")){ // Als het een FIFO Cashier is
                this.waitingQueue.add(customer);
                this.maxQueueLength = waitingQueue.size();
            }
            if (this.getName().startsWith("PRIO")){ // Als het een PRIO Cashier is
                ArrayList<Customer> prioritylist = new ArrayList<>();
                ArrayList<Customer> nonprioritylist = new ArrayList<>();
                for (Customer waitingCustomer : waitingQueue) {
                    if (waitingCustomer.getNumberOfItems() <= 5){
                        prioritylist.add(waitingCustomer);
                    } else {
                        nonprioritylist.add(waitingCustomer);
                    }
                }
                this.waitingQueue.clear();
                startWaitTimeList.put(customer, currentTime);
                if (customer.getNumberOfItems() <= 5) {
                    prioritylist.add(customer);
                } else {
                    nonprioritylist.add(customer);
                }
                this.waitingQueue.addAll(prioritylist);
                this.waitingQueue.addAll(nonprioritylist);
                maxQueueLength = waitingQueue.size();
            }
        } else {
            this.maxQueueLength = waitingQueue.size() - 1;
        }
    }


    public int getTotalIdleTime() {
        return totalIdleTime;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public String getName() {
        return name;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }
    public void setTotalIdleTime(int totalIdleTime) {
        this.totalIdleTime = totalIdleTime;
    }

    public Queue<Customer> getWaitingQueue() {
        return waitingQueue;
    }

}
