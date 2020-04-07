/**
 * This class represents a Customer, which each have an order number and randomly generated food order.
 * Customers are nodes for a linked list within a Restaurant.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class Customer{
    private static int totalCustomers = 0;
    private int orderNumber;
    private String food;
    private String foodABR;
    int priceOfFood;
    int timeArrived;
    int originalTimeToServe;
    int timeToServe;
    private static String[][] foodList = {{"Steak", "S"},{"Chicken Wings", "CW"},
            {"Cheeseburger", "CB"},{"Chicken Tenders", "CT"},{"Grilled Cheese", "GC"}};
    private static int[][] pricesTimes = {{25,20,15,10,10},{30,30,25,25,15}};

    /**
     * Constructor for the Customer class. Their food choice is determined randomly,
     * and their time to serve is dependant upon the number of chefs within the DiningSimulator class.
     */
    public Customer(){
        this.orderNumber = ++totalCustomers;
        int choice = (int)(Math.random() * 5);
        this.food = foodList[choice][0];
        this.foodABR = foodList[choice][1];
        this.priceOfFood = pricesTimes[0][choice];
        this.timeToServe = 15 + pricesTimes[1][choice] + DiningSimulator.efficiencyMin;
        this.originalTimeToServe = timeToServe;
    }

    /**
     * @return
     * Returns a textual representation of the Customer.
     */
    public String toString(){
        return "[#" + orderNumber + ", " + foodABR + ", " + timeToServe + " min.]";
    }

    /**
     * Getter for orderNumber.
     * @return
     * Returns the Customer's order number.
     */
    public int getOrderNumber(){
        return orderNumber;
    }
    /**
     * Getter for orderNumber.
     * @return
     * Returns the Customer's order number.
     */
    public String getFood(){
        return food;
    }
    /**
     * Adjusts the Customers timeToServe after each simulation unit (5 min).
     */
    public void changeTime(){
        timeToServe -= 5;
    }
    /**
     * Getter for the timeToServe.
     * @return
     * Returns the Customer's time to serve.
     */
    public int getTimeToServe(){
        return timeToServe;
    }
    /**
     * Getter for priceOfFood.
     * @return
     * Returns the Customer's price of their food selection.
     */
    public int getPriceOfFood(){
        return priceOfFood;
    }
    /**
     * Getter for originalTimeToServe.
     * @return
     * Returns the Customer's original time to serve.
     */
    public int getOriginalTimeToServe(){
        return originalTimeToServe;
    }
    /**
     * Resets the total customers within the simulator.
     * @param totalCustomers
     * The number to reset to.
     */
    public static void setTotalCustomers(int totalCustomers) {
        Customer.totalCustomers = totalCustomers;
    }
}