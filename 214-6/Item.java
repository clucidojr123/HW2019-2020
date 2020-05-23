/**
 * This class represents an item type within a store. It has a unique itemCode to be used as a key within
 * a HashTable of the HashedGrocery class.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class Item{
    private String itemCode;
    private String name;
    private int qtyInStore;
    private int averageSalesPerDay;
    private int onOrder;
    private int onArrivalDay;
    private double price;

    /**
     * Constructor for the Item class.
     */
    public Item(String itemCode, String name, int qtyInStore, int onOrder, int onArrivalDay, double price,
                int averageSalesPerDay){
        this.itemCode = itemCode;
        this.name = name;
        this.qtyInStore = qtyInStore;
        this.onArrivalDay = onArrivalDay;
        this.onOrder = onOrder;
        this.price = price;
        this.averageSalesPerDay = averageSalesPerDay;
    }

    /**
     * Getter for the itemCode
     * @return
     * returns the itemCode of this item
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Getter for the item name
     * @return
     * returns the name of this item
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for the quantity of this item in the store
     * @return
     * returns the quantity of items
     */
    public int getQtyInStore() {
        return qtyInStore;
    }
    /**
     * Getter for the amount of items on order
     * @return
     * returns the number of this item on order
     */
    public int getOnOrder() {
        return onOrder;
    }
    /**
     * Getter for the day of arrival for ordered items
     * @return
     * returns the day of arrival
     */
    public int getOnArrivalDay() {
        return onArrivalDay;
    }

    /**
     * Getter for the average sales per day of the item
     * @return
     * returns the average sales per day
     */
    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    /**
     * Setter for the amount of this item on order
     * @param onOrder
     * The new amount to set on order
     */
    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    /**
     * Setter for the arrival day of ordered items
     * @param onArrivalDay
     * The new day to set arrival
     */
    public void setOnArrivalDay(int onArrivalDay) {
        this.onArrivalDay = onArrivalDay;
    }

    /**
     * Setter for the amount of items in the store
     * @param change
     * The amount to change the quantity by
     */
    public void setQtyInStore(int change){
        qtyInStore += change;
    }

    /**
     * Method for setting both the order and arrival of items
     * @param day
     * The day the order was placed
     */
    public void placeOrder(int day){
        onOrder = 2 * averageSalesPerDay;
        onArrivalDay = day + 3;
    }

    /**
     * Method to reset the order number and arrival after an item has been delivered
     */
    public void resetOrder(){
        onOrder = 0;
        onArrivalDay = 0;
    }

    /**
     * @return
     * Returns a textual representation of the item.
     */
    public String toString(){
        return String.format("%-12s%-21s%-11s%-2s%8s%10s%12s\n",
                itemCode, name, qtyInStore, averageSalesPerDay, price,onOrder,onArrivalDay);
    }
}