import java.io.*;
import java.util.Hashtable;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class represents a HashedGrocery, which contains all the methods that will allow
 * employees to perform different functions on the inventory of the grocery store.
 * Here, the item code of every item is the key of the hash table.
 */
public class HashedGrocery {
    private int businessDay = 1;
    Hashtable<String, Item> hashtable = new Hashtable<String, Item>();

    /**
     * Constructor for the HashedGrocery class
     */
    public HashedGrocery(){

    }

    /**
     * Method to add a user-inputted item into the hashtable. If the new item has a code already within the table,
     * it is not inserted.
     * @param item
     * The item to add to the hashtable.
     */
    public void addItem(Item item){
        if(hashtable.containsKey(item.getItemCode()))
            System.out.println(item.getItemCode() + ": Cannot add item as item code already exists.");
        else {
            hashtable.put(item.getItemCode(), item);
            System.out.println(item.getItemCode() + ": " + item.getName() + " is added to inventory.");
        }
    }

    /**
     * Method to change the quantity of an item within the store
     * @param item
     * The item to change
     * @param adjustByQty
     * The amount to change by
     */
    public void updateItem(Item item, int adjustByQty) {
        item.setQtyInStore(-1 * adjustByQty);
    }

    /**
     * Method to add a collection of items from a JSON .txt file to the hashtable
     * @param filename
     * The filename to add items from
     * @throws IOException
     * Thrown if the given file does not exist
     * @throws ParseException
     * Thrown when there is an error parsing the JSON data
     */
    public void addItemCatalog(String filename) throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray objects = (JSONArray) parser.parse(isr);
        JSONObject obj;
        String code = "";
        for (Object object : objects) {
            obj = (JSONObject) object;
            code = (String) obj.get("itemCode");
            if(hashtable.containsKey(code))
                System.out.println(code + ": Cannot add item as item code already exists.");
            else {
                Item temp = new Item(code,(String) obj.get("itemName"),
                        Integer.parseInt((String) obj.get("qtyInStore")),
                        Integer.parseInt((String) obj.get("amtOnOrder")),
                        0, Double.parseDouble((String) obj.get("price")),
                        Integer.parseInt((String) obj.get("avgSales")) );
                hashtable.put(code,temp);
                System.out.println(code + ": " + temp.getName() + " is added to inventory.");
            }
        }
    }

    /**
     * Method to process item sales in the store using data from a JSON .txt file
     * @param filename
     * The filename to get sales data from
     * @throws IOException
     * Thrown if the given file does not exist
     * @throws ParseException
     * Thrown when there is an error parsing the JSON data
     */
    public void processSales(String filename) throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray objects = (JSONArray) parser.parse(isr);
        JSONObject obj;
        String code = "";
        String message = "";
        int qty;
        for(Object object: objects){
            obj = (JSONObject) object;
            code = (String) obj.get("itemCode");
            qty = Integer.parseInt((String) obj.get("qtySold"));
            if(hashtable.containsKey(code)) {
                Item temp = hashtable.get(code);
                if(temp.getQtyInStore() < qty)
                    message = code + ": Not enough stock for sale. Not updated.";
                else {
                    updateItem(temp,qty);
                    message = code + ": " + qty + " units of " + temp.getName() + " are sold.";
                    if(temp.getQtyInStore() < temp.getAverageSalesPerDay() * 3 && temp.getOnOrder() == 0){
                        temp.placeOrder(businessDay);
                        message += " Order has been placed for " + temp.getOnOrder() + " units.";
                    }
                }
                System.out.println(message);
            } else
                System.out.println(code + ": Cannot buy as it is not in the grocery store.");
        }
    }

    /**
     * Method to move to the next business day and check whether there are any
     * incoming orders from items within the hashtable.
     */
    public void nextBusinessDay(){
        businessDay++;
        String message = "Advancing business day...\nBusiness Day " + businessDay + ".";
        String updates = "";
        Set<String> codes = hashtable.keySet();
        boolean arrive = false;
        for(String code: codes){
            if(hashtable.get(code).getOnArrivalDay() == businessDay) {
                arrive = true;
                updateItem(hashtable.get(code), -1 * hashtable.get(code).getOnOrder());
                updates += code + ": " + hashtable.get(code).getOnOrder() + " units of "
                        + hashtable.get(code).getName() + "\n";
                hashtable.get(code).resetOrder();
            }
        }
        if(arrive)
            message += "\n\nOrders have arrived for:\n\n" + updates;
        else
            message += " No orders have arrived.\n";
        System.out.println(message);
    }

    /**
     * @return
     * Creates and returns a textual representation of the HashedGrocery object.
     */
    public String toString(){
        String first = String.format("%-12s%-20s%-6s%-11s%-8s%-6s%12s\n",
                "Item Code", "Name", "Qty", "AvgSales", "Price","OnOrder","ArrOnBusDay");
        for(int i = 0; i < 76; i++)
            first = first.concat("-");
        first = first.concat("\n");
        Set<String> codes = hashtable.keySet();
        for(String code: codes)
            first += hashtable.get(code).toString() + "\n";
        return first;
    }
}