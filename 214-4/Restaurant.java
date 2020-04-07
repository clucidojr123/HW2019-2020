import java.util.LinkedList;
/**
 * This class represents a Restaurant, which holds a linked list / queue of Customers. Restaurants are also nodes
 * for a linked list in the DiningSimulator.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class Restaurant extends LinkedList<Customer> {
    private int number;

    /**
     * The constructor for the Restaurant class.
     * @param number
     * The number of the restaurant.
     */
    public Restaurant(int number){
        this.number = number;
    }

    /**
     * Adding a Customer to the end of the queue.
     * @param c
     * Customer to add.
     */
    public void enqueue(Customer c){
        super.add(c);
    }

    /**
     * Removing the first Customer from the queue
     * @return
     * Returns the removed Customer.
     */
    public Customer dequeue() {
        return super.remove();
    }

    /**
     * Retrieves, but does not remove the first Customer in the queue.
     * @return
     * Returns the first Customer in the queue.
     */
    public Customer peek(){
        return super.peek();
    }

    /**
     * @return
     * Returns the size of the queue.
     */
    public int size(){
        return super.size();
    }

    /**
     * Checks whether or not the Restaurant is empty.
     * @return
     * Returns true if Restaurant is empty, false otherwise.
     */
    public boolean isEmpty(){
        return super.isEmpty();
    }

    /**
     * @return
     * Returns a textual representation of the Restaurant object.
     */
    public String toString(){
        String temp = "";
        for (Customer c : this) {
            if (c == null) break;
            temp = temp + c.toString() + ", ";
        }
        return "{" + temp + "}";
    }

    /**
     * @return
     * Returns the number of the Restaurant.
     */
    public int getNumber(){
        return number;
    }

}