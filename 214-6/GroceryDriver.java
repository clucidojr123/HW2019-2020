import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents the GroceryDriver, which is the main driver for the item management system of the store.
 * It includes an interface for the user to add, process, and display items.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class GroceryDriver{
    /**
     * This is the main method for creating an interface between the user and the management system.
     * The menu will be shown after every selection unless the user selects "q" which terminates the program.
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        HashedGrocery costco = new HashedGrocery();
        boolean status = true;
        String menu =
                "\n(L) Load item catalog    \n" +
                "(A) Add items              \n" +
                "(B) Process Sales      \n" +
                "(C) Display all items\n" +
                "(N) Move to next business day  \n" +
                "(Q) Quit  ";
        System.out.println("Business Day 1.\n" + "Menu :");
        while(status) {
            System.out.println(menu + "\nEnter a selection: ");
            try {
                String pick = scan.nextLine().toUpperCase();
                switch (pick) {
                    case ("L"):
                        System.out.println("Enter a file to load: ");
                        String file = scan.nextLine();
                        costco.addItemCatalog(file);
                        break;
                    case ("A"):
                        System.out.println("Enter item code: ");
                        String code = scan.nextLine();
                        System.out.println("Enter item name: ");
                        String name = scan.nextLine();
                        System.out.println("Enter item quantity: ");
                        int qty = scan.nextInt();
                        if (qty <= 0)
                            throw new IllegalArgumentException();
                        System.out.println("Enter item average sales per day: ");
                        int average = scan.nextInt();
                        if (average <= 0)
                            throw new IllegalArgumentException();
                        System.out.println("Enter item price: ");
                        double price = scan.nextDouble();
                        if (price <= 0.0)
                            throw new IllegalArgumentException();
                        Item temp = new Item(code, name, qty, 0, 0, price, average);
                        costco.addItem(temp);
                        scan.nextLine();
                        break;
                    case ("B"):
                        System.out.println("Enter a file name: ");
                        String qtyFile = scan.nextLine();
                        costco.processSales(qtyFile);
                        break;
                    case ("C"):
                        System.out.println(costco.toString());
                        break;
                    case ("N"):
                        costco.nextBusinessDay();
                        break;
                    case ("Q"):
                        System.out.println("Program terminating normally...");
                        scan.close();
                        status = false;
                        break;
                    default:
                        System.out.println("Invalid menu option. Please try again.");
                }
            } catch (IllegalArgumentException w) {
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException w) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            } catch (ParseException e) {
                System.out.println("There was an error when parsing the file. Please try again.");
            } catch (IOException e) {
                System.out.println("The filename you are providing does not exist. Please try again.");
            }
        }
    }
}