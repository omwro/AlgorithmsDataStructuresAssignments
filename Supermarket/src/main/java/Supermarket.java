/**
 * Supermarket Customer check-out and Cashier simulation
 *
 * @author hbo-ict@hva.nl
 */

import utils.SLF4J;
import utils.XMLParser;
import utils.XMLWriter;

import javax.xml.stream.XMLStreamConstants;
import java.rmi.UnexpectedException;
import java.time.LocalTime;
import java.util.*;

public class Supermarket {
    public String name;                 // name of the case for reporting purposes
    private Set<Product> products;      // a set of products that is being sold in the supermarket
    private List<Customer> customers;   // a list of customers that have visited the supermarket
    private List<Cashier> cashiers;     // the cashiers which have been configured to handle the customers

    private LocalTime openTime;         // start time of the simulation
    private LocalTime closingTime;      // end time of the simulation

    public Supermarket(String name, LocalTime openTime, LocalTime closingTime) {
        this.name = name;
        this.setOpenTime(openTime);
        this.setClosingTime(closingTime);
        this.cashiers = new ArrayList<>();
        this.products = new TreeSet<>();
        this.customers = new ArrayList<>();
    }

    public int getTotalNumberOfItems() {
        int totalItems = 0;
        for (Iterator<Customer> iter = this.customers.iterator(); iter.hasNext(); ) {
            Customer c = iter.next();
            totalItems += c.getNumberOfItems();
        }
        return totalItems;
    }

    /**
     * report statistics of the input data and results of the simulation
     */
    public void printCustomerStatistics() {
        System.out.printf("\nCustomer Statistics of '%s' between %s and %s\n",
                this.name, this.openTime, this.closingTime);
        if (this.customers == null || this.products == null ||
                this.customers.size() == 0 || this.products.size() == 0) {
            System.out.println("No products or customers have been set up...");
            return;
        }

        System.out.printf("%d customers have shopped %d items out of %d different products\n",
                this.customers.size(), this.getTotalNumberOfItems(), this.products.size());

        System.out.print("Revenues and most bought product per zip-code:\n\t");
        Map<String, Double> revenues = this.revenueByZipCode();
        Map<String, Product> populars = this.mostBoughtProductByZipCode();

        double totalRevenue = 0.0;
        int index = 0;
        for (String key: revenues.keySet()){
            index++;
            totalRevenue += revenues.get(key);

            System.out.printf("%s:%.2f(%s), ",key,revenues.get(key),populars.get(key));
            if((index + 1) % 3 == 0) System.out.print("\n\t");
        }

        System.out.printf("\nTotal Revenue = %.2f\n", totalRevenue);
    }

    /**
     * reports results of the cashier simulation
     */
    public void printSimulationResults() {
        System.out.printf("\nSimulation scenario results:\n");
        System.out.printf("Cashiers:\tn-customers:\tavg-wait-time:\tmax-wait-time:\tmax-queue-length:\tavg-check-out-time:\tidle-time:\n");
        for (Cashier chosenCashier: this.cashiers) {
            int numberCustomers = chosenCashier.waitingQueue.size();
            if (chosenCashier.getName().startsWith("FIFO")) {
                String nameFIFO = "FIFO";
                int totalTime = 0;
                int totalTimeOut = 0;
                int biggestNumber = 0;
                for (Customer customer : chosenCashier.waitingQueue) {
                    totalTime += customer.getActualWaitingTime();
                    totalTimeOut += customer.getActualCheckOutTime();
                    if (customer.getActualWaitingTime() > biggestNumber) {
                        biggestNumber = customer.getActualWaitingTime();
                    }
                }
                double averageTimeOut = ((double) totalTimeOut / chosenCashier.waitingQueue.size());
                double averageTime = ((double) totalTime / chosenCashier.waitingQueue.size());
                System.out.printf("%6s %12d %18.2f %13d %15d %22.2f %17d\n", nameFIFO, this.customers.size(), averageTime, biggestNumber, chosenCashier.maxQueueLength, averageTimeOut, chosenCashier.totalIdleTime);

            } else if (chosenCashier.getName().startsWith("PRIO")) {
                String namePRIO = "PRIO";
                int totalTime = 0;
                int biggestNumber = 0;
                int totalTimeOut = 0;
                for (Map.Entry<Customer, Integer> map : chosenCashier.waitTimeListCashier.entrySet()){
                    totalTime += map.getValue();
                    if (map.getValue() > biggestNumber){
                        biggestNumber = map.getValue();
                    }
                }
                for (Customer customer : chosenCashier.waitingQueue) {
                    totalTimeOut += customer.getActualCheckOutTime();
                }
                double averageTimeOut = ((double) totalTimeOut / chosenCashier.waitingQueue.size());
                double averageTime = ((double) totalTime / chosenCashier.waitingQueue.size());
                System.out.printf("%6s %12d %18.2f %13d %15d %22.2f %17d\n", namePRIO, this.customers.size(), averageTime, biggestNumber, chosenCashier.maxQueueLength, averageTimeOut, chosenCashier.totalIdleTime);
            }
        }

    }

    /**
     * calculates a map of aggregated revenues per zip code that is also ordered by zip code
     * @return
     */
    public Map<String, Double> revenueByZipCode() {
        Map<String, Double> revenues = new TreeMap<>();

        for (Iterator<Customer> citer = this.customers.iterator(); citer.hasNext(); ) {
            double totalrevenue = 0.0;
            Customer c = citer.next();
            for (Purchase p : c.getItems()) {
                totalrevenue += p.getAmount() * p.getProduct().getPrice();
            }
            if (revenues.containsKey(c.getZipCode())) {
                totalrevenue += revenues.get(c.getZipCode());
            }
            revenues.put(c.getZipCode(), totalrevenue);
        }

        return revenues;
    }

    /**
     * (DIFFICULT!!!)
     * calculates a map of most bought products per zip code that is also ordered by zip code
     * if multiple products have the same maximum count, just pick one.
     * @return
     */
    public Map<String, Product> mostBoughtProductByZipCode() {
        Map<String, Product> mostBoughtProduct = new TreeMap<>();
        Map<String, Integer> mostBoughtAmount = new TreeMap<>();
        Map<String, Product> mostBoughtSorted = new TreeMap<>();

        for (Customer customer : customers){
            if (!mostBoughtProduct.containsKey(customer.getZipCode())){
                mostBoughtProduct.put(customer.getZipCode(), null);
                mostBoughtAmount.put(customer.getZipCode(), 0);
            }
            for (Purchase item : customer.getItems()){
                if (item.getAmount() > mostBoughtAmount.get(customer.getZipCode())){
                    mostBoughtProduct.put(customer.getZipCode(), item.getProduct());
                    mostBoughtAmount.put(customer.getZipCode(), item.getAmount());
                }
            }
        }

        mostBoughtSorted.putAll(mostBoughtProduct);
        return mostBoughtSorted;
    }

    /**
     * simulate the cashiers while handling all customers that enter their queues
     */
    public void simulateCashiers() {
        // TODO: create an appropriate data structure for the shoppingQueue
        //  and add all customers in the supermarket
        Queue<Customer> shoppingQueue = new PriorityQueue<>(customers.size(), new sortByQueuedAtComparator());
        shoppingQueue.addAll(this.customers);

        // all cashiers restart at open time
        for (Cashier c : this.cashiers) {
            c.reStart(this.openTime);
        }

        // poll the customers from the queue one by one
        // and redirect them to the cashier of their choice

        // TODO: get the first customer from the shoppingQueue;
        Customer nextCustomer = shoppingQueue.poll();

        while (nextCustomer != null) {

            // let all cashiers finish up their work before the given arrival time of the customer
            for (Cashier c : this.cashiers) {
                c.doTheWorkUntil(nextCustomer.getQueuedAt());
            }
            // ask the customer about his preferred cashier for the check-out
            Cashier selectedCashier = nextCustomer.selectCashier(this.cashiers);
            // redirect the customer to the selected cashier
            selectedCashier.add(nextCustomer);

            // TODO: next customer is arriving, get the next customer from the shoppingQueue
            nextCustomer = shoppingQueue.poll();
            selectedCashier.currentCustomer = nextCustomer;
        }

        // all customers have been handled;
        // cashiers finish their work until closing time + some overtime
        final int overtime = 15 * 60;
        for (Cashier c : this.cashiers) {
            c.doTheWorkUntil(this.closingTime.plusSeconds(overtime));
            // remove the overtime from the current time and the idle time of the cashier
            c.setCurrentTime(c.getCurrentTime().minusSeconds(overtime));
            c.setTotalIdleTime(c.getTotalIdleTime() - overtime);
        }
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    /**
     * Loads a complete supermarket configuration from an XML file
     * @param resourceName  the XML file name to be found in the resources folder
     * @return
     */
    public static Supermarket importFromXML(String resourceName) {
        XMLParser xmlParser = new XMLParser(resourceName);

        try {
            xmlParser.nextTag();
            xmlParser.require(XMLStreamConstants.START_ELEMENT, null, "supermarket");
            LocalTime openTime = LocalTime.parse(xmlParser.getAttributeValue(null, "openTime"));
            LocalTime closingTime = LocalTime.parse(xmlParser.getAttributeValue(null, "closingTime"));
            xmlParser.nextTag();

            Supermarket supermarket = new Supermarket(resourceName, openTime, closingTime);

            Product.importProductsFromXML(xmlParser, supermarket.products);
            Customer.importCustomersFromXML(xmlParser, supermarket.customers, supermarket.products);

            return supermarket;

        } catch (Exception ex) {
            SLF4J.logException("XML error in '" + resourceName + "'", ex);
        }

        return null;
    }

    /**
     * Exports the supermarket configuration to an xml configuration file
     * that can be shared and read in by a main
     * @param resourceName
     */
    public void exportXML(String resourceName) {
        XMLWriter xmlWriter = new XMLWriter(resourceName);

        try {
            xmlWriter.writeStartDocument();
            xmlWriter.writeStartElement("supermarket");
            xmlWriter.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            xmlWriter.writeAttribute("\n\txsi:noNamespaceSchemaLocation", "supermarket.xsd");
            xmlWriter.writeAttribute("\n\topenTime", this.openTime.toString().concat(":00").substring(0, 8));
            xmlWriter.writeAttribute("closingTime", this.closingTime.toString().concat(":00").substring(0, 8));
            if (this.products instanceof Collection && this.products.size() > 0) {
                xmlWriter.writeStartElement("products");
                for (Product p : this.products) {
                    p.exportToXML(xmlWriter);
                }
                xmlWriter.writeEndElement();
            }
            if (this.products instanceof Collection && this.customers.size() > 0) {
                xmlWriter.writeStartElement("customers");
                for (Customer c : this.customers) {
                    c.exportToXML(xmlWriter);
                }
                xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndDocument();
        } catch (Exception ex) {
            SLF4J.logException("XML writing error in '" + resourceName + "'", ex);
        }

        // update the name of the supermarket
        this.name = resourceName;
    }

    /**
     * adds a collection of random customers to the configuration with a random number of items
     * between 1 and 4 * averageNrItems.
     * the distribution ensures that on average each customer buys averageNrItems
     * arrival times are chosen well in advance of closingTime of the supermarket,
     * such that cashiers can be expected to be able to finish all work
     * (unless an extreme workload has been configured)
     * @param nCustomers
     * @param averageNrItems
     */
    public void addRandomCustomers(int nCustomers, int averageNrItems) {
        if (!(this.products instanceof Collection) ||
                !(this.customers instanceof Collection)
        ) return;

        // copy the product to an array for easy random selection
        Product[] prods = new Product[this.products.size()];
        prods = this.products.toArray(prods);

        // compute an arrival interval range of at least 60 seconds that ends one minute before closing time if possible
        int maxArrivalSeconds = Math.max(60, closingTime.toSecondOfDay() - openTime.toSecondOfDay() - 60);

        for (int i = 0; i < nCustomers; i++) {
            // create a random customer with random arrival time and zip code
            Customer c = new Customer(
                    this.openTime.plusSeconds(randomizer.nextInt(maxArrivalSeconds)),
                    generateRandomZIPCode());

            // select a random number of bought items
            int remainingNumberOfItems = selectRandomNrItems(averageNrItems);

            // build a random distribution of these items across available products
            int upper = prods.length;
            while (remainingNumberOfItems > 0) {
                int count = 1 + randomizer.nextInt(remainingNumberOfItems);
                // pick a random product that has not been used yet by this customer
                int pIdx = randomizer.nextInt(upper);
                Purchase pu = new Purchase(prods[pIdx], count);
                c.getItems().add(pu);
                // System.out.println(c.toString() + pu.toString());
                remainingNumberOfItems -= count;
                // move the product out of the range of available products for this customer
                upper--;
                Product pt = prods[upper];
                prods[upper] = prods[pIdx];
                prods[pIdx] = pt;
            }

            this.customers.add(c);
        }
    }

    private static Random randomizer = new Random();

    private static int selectRandomNrItems(int averageNrItems) {
        return 1 + (int) ((4 * averageNrItems - 1) * randomizer.nextDouble() * randomizer.nextDouble());
    }

    private static String generateRandomZIPCode() {
        int randomDigit = randomizer.nextInt(5);
        int randomChar1 = randomizer.nextInt(2);
        int randomChar2 = randomizer.nextInt(2);
        return String.valueOf(1013 + randomDigit) +
                (char) (randomDigit + 9 * randomChar1 + randomChar2 + 'A') +
                (char) (randomDigit + 3 * randomChar1 + 7 * randomChar2 + 'D');
    }
}
