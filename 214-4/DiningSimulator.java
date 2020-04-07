import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * This class represents the dining simulator that includes a the interface between the user and the
 * station to perform the simulation. After inputting valid arguments, the simulation will run and
 * display statistics after the final simulation unit.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class DiningSimulator{
    private LinkedList<Restaurant> restaurants = new LinkedList<>();
    private int chefs;
    static int efficiencyMin = 0;
    private int duration;
    private double arrivalProb;
    private int maxCustomerSize;
    private int numRestaurants;
    private int customersLost;
    private int totalServiceTime;
    private int customersServed;
    private int profit;

    /**
     * Constructor for the DiningSimulator class.
     * @param chefs
     * The number of chefs for each Restaurant.
     * @param duration
     * The number of simulation units to run.
     * @param max
     * The maximum number of Customers a Restaurant can hold.
     * @param numRestaurants
     * The number of Restaurants.
     * @param prob
     * The probability of a new Customer entering a Restaurant.
     */
    public DiningSimulator(int chefs, int duration, int max, int numRestaurants, double prob){
        this.chefs = chefs;
        this.duration = duration;
        this.maxCustomerSize = max;
        this.numRestaurants = numRestaurants;
        this.arrivalProb = prob;
        int temp = chefs - 3;
        //The amount of minutes to add/subtract to each Customer's timeToServe is determined below
        efficiencyMin = temp == 1 ? -5 : temp >= 2 ? -10 : temp == -1 ? 5 : temp <= -2 ? 10 : 0;
        for(int i = 0; i < numRestaurants; i++)
            restaurants.add(new Restaurant(i + 1));
    }
    /**
     * This is the main method for interacting with the user and running simulations.
     * At first the user will input variables for the simulation.
     * After the simulation ends and statistics are printed, the interface will ask the user if they want to
     * run another simulation again. If the user enters invalid inputs, error messages will appear,
     * bringing the user back to the start of the input sequence.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Starting simulator...");
        boolean simStatus = true;
        while (simStatus) {
            try {
                System.out.println("Enter the number of restaurants: ");
                int numRest = scan.nextInt();
                if(numRest <= 0)
                    throw new IllegalArgumentException();
                System.out.println("Enter the maximum number of customers a restaurant can serve: ");
                int max = scan.nextInt();
                if(max <= 0)
                    throw new IllegalArgumentException();
                System.out.println("Enter the arrival probability of a customer: ");
                double prob = scan.nextDouble();
                if(prob <= 0 || prob > 1.0)
                    throw new IllegalArgumentException();
                System.out.println("Enter the number of chefs: ");
                int chefs = scan.nextInt();
                if(chefs < 1)
                    throw new IllegalArgumentException();
                System.out.println("Enter the number of simulation units: ");
                int sims = scan.nextInt();
                if(sims <= 0)
                    throw new IllegalArgumentException();
                DiningSimulator sim = new DiningSimulator(chefs, sims, max, numRest, prob);
                for (int i = 1; i <= sims; i++)
                    sim.simulate(i);
                System.out.println("\nSimulation ending...\n");
                double average = sim.totalServiceTime / (double) sim.customersServed;
                System.out.printf("Total customer time: %d minutes\n" +
                                "Total customers served: %d\n" +
                                "Average customer time lapse: %.2f minutes per order\n" +
                                "Total Profit: $%d\n" +
                                "Customers that left: %d\n", sim.totalServiceTime,
                        sim.customersServed, average, sim.profit, sim.customersLost);
                boolean askStatus = true;
                while (askStatus) {
                    System.out.println("\nDo you want to try another simulation? (y/n): ");
                    char choice = scan.next().toLowerCase().charAt(0);
                    if (choice == 'n') {
                        askStatus = false;
                        simStatus = false;
                        System.out.println("Program terminating normally...");
                    } else if (choice == 'y') {
                        askStatus = false;
                        Customer.setTotalCustomers(0);
                    } else
                        System.out.println("Invalid input. Please try again.\n");
                }
            } catch (IllegalArgumentException w) {
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException w) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            }
        }
    }

    /**
     *This method runs the simulation for a number of simulation units determined by the parameter.
     * @param rounds
     * The number of simulation units to evaluate.
     */
    public void simulate(int rounds){
        //An iterator is needed to avoid getting a ConcurrentModificationException when trying to remove
        // an element from a list while iterating the list.
        Iterator<Restaurant> iter = restaurants.iterator();
        Iterator<Customer> tempRest;
        System.out.println("Time: " + rounds);
        // Sequence for when a customer is fully served
        while(iter.hasNext()) {
            Restaurant r = iter.next();
            tempRest = r.iterator();
            while(tempRest.hasNext()) {
                Customer c = tempRest.next();
                c.changeTime();
                if(c.getTimeToServe() == 0) {
                    System.out.println("Customer #" + c.getOrderNumber() +
                            " has enjoyed their food! $" + c.getPriceOfFood() + " profit.");
                    this.profit += c.getPriceOfFood();
                    this.customersServed++;
                    this.totalServiceTime += c.getOriginalTimeToServe();
                    tempRest.remove();
                }
            }
        }
        // Sequence for simulating if new customers arrive to a restaurant
        Restaurant holder = new Restaurant(0);
        for(Restaurant r : restaurants) {
            for (int i = 0; i < 3; i++) {
                if (Math.random() < this.arrivalProb) {
                    Customer temp = new Customer();
                    holder.enqueue(temp);
                    r.enqueue(temp);
                    System.out.println("Customer #" + temp.getOrderNumber() +
                            " has entered Restaurant " + r.getNumber() + ".");
                }
            }
        }
        // Removing customers if the restaurant's size exceeds the max allowed
        iter = restaurants.iterator();
        while(iter.hasNext()){
            Restaurant r = iter.next();
            for(int i = r.size(); i > this.maxCustomerSize; i--) {
                Customer temp = r.removeLast();
                holder.remove(temp);
                System.out.println("Customer #" + temp.getOrderNumber() + " cannot be seated! " +
                        "They have left the restaurant.");
                this.customersLost++;
            }
        }
        // Printing successful addition to restaurants
        while(!holder.isEmpty()) {
            Customer temp = holder.dequeue();
            System.out.println("Customer #" + temp.getOrderNumber() + " has been seated with order \"" +
                    temp.getFood() + ".\"");
        }
        // Printing each restaurant's customers
        for(Restaurant r : restaurants){
            System.out.println(r.toString());
        }
        System.out.println();
    }
}