import java.util.Comparator;

public class sortByQueuedAtComparator implements Comparator<Customer> {

    @Override
    public int compare(Customer customer, Customer t1) {
        if(customer.getQueuedAt().toSecondOfDay() > t1.getQueuedAt().toSecondOfDay()) {
            return 1;
        } else if (customer.getQueuedAt().toSecondOfDay() < t1.getQueuedAt().toSecondOfDay()) {
            return -1;
        }
        return 0;
    }
}
