import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a General Ledger Manager that controls the interface between the user and the ledger.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class GeneralLedgerManager {
    /**
     * This is the main method for interacting with the user.
     * A menu is provided with multiple options for the user to select.
     * The menu will pop up after every menu interaction until the user selects q or Q,
     * which will terminate the program.
     * If the user enters invalid inputs, error messages will appear, bringing the user back to the menu.
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        GeneralLedger jack = new GeneralLedger();
        GeneralLedger backupJack = new GeneralLedger();
        boolean status = true;
        String menu = "\n(A) Add Transaction \n" +
                "(G) Get Transaction\n" +
                "(R) Remove Transaction\n" +
                "(P) Print Transactions in General Ledger\n" +
                "(F) Filter by Date\n" +
                "(L) Look for Transaction\n" +
                "(S) Size\n" +
                "(B) Backup\n" +
                "(PB) Print Transactions in Backup\n" +
                "(RB) Revert to Backup\n" +
                "(CB) Compare Backup with Current\n" +
                "(PF) Print Financial Information\n" +
                "(Q) Quit \n";
        while(status) {
            System.out.println(menu + "\nEnter a selection: ");
            try {
                String pick = scan.next();
                switch (pick) {
                    case ("a"):
                    case ("A"):
                        System.out.println("Enter Date (YYYY/MM/DD): ");
                        String newDate = scan.next();
                        // This checks if the string provided is valid
                        if(newDate.length() != 10 || newDate.charAt(4) != '/' || newDate.charAt(7) != '/'
                                || !Character.isDigit(newDate.charAt(0)) || !Character.isDigit(newDate.charAt(1))
                                || !Character.isDigit(newDate.charAt(2)) || !Character.isDigit(newDate.charAt(3))
                                || !Character.isDigit(newDate.charAt(5)) || !Character.isDigit(newDate.charAt(6))
                                || !Character.isDigit(newDate.charAt(8)) || !Character.isDigit(newDate.charAt(9)))
                            throw new IllegalArgumentException();
                        System.out.println("Enter amount ($): ");
                        double amount = scan.nextDouble();
                        scan.nextLine();
                        System.out.println("Enter description: ");
                        String describe = scan.nextLine();
                        Transaction addTemp = new Transaction(newDate, amount, describe);
                        jack.addTransaction(addTemp);
                        System.out.println("Transaction successfully added to the general ledger.");
                        break;
                    case ("g"):
                    case ("G"):
                        System.out.println("Enter a position: ");
                        int getPosition = scan.nextInt();
                        jack.printTransaction(getPosition);
                        break;
                    case ("r"):
                    case ("R"):
                        System.out.println("Enter a position: ");
                        int remPosition = scan.nextInt();
                        jack.removeTransaction(remPosition);
                        System.out.println("Transaction successfully removed from the general ledger.");
                        break;
                    case ("p"):
                    case ("P"):
                        jack.printAllTransactions();
                        break;
                    case ("f"):
                    case ("F"):
                        System.out.println("Enter Date (YYYY/MM/DD): ");
                        String filterDate = scan.next();
                        // This checks if the string provided is valid
                        if(filterDate.length() != 10 || filterDate.charAt(4) != '/' || filterDate.charAt(7) != '/'
                                || !Character.isDigit(filterDate.charAt(0)) || !Character.isDigit(filterDate.charAt(1))
                                || !Character.isDigit(filterDate.charAt(2)) || !Character.isDigit(filterDate.charAt(3))
                                || !Character.isDigit(filterDate.charAt(5)) || !Character.isDigit(filterDate.charAt(6))
                                || !Character.isDigit(filterDate.charAt(8)) || !Character.isDigit(filterDate.charAt(9)))
                            throw new IllegalArgumentException();
                        GeneralLedger.filter(jack, filterDate);
                        break;
                    case ("l"):
                    case ("L"):
                        System.out.println("Enter Date (YYYY/MM/DD): ");
                        String lookDate = scan.next();
                        // This checks if the string provided is valid
                        if(lookDate.length() != 10 || lookDate.charAt(4) != '/' || lookDate.charAt(7) != '/'
                                || !Character.isDigit(lookDate.charAt(0)) || !Character.isDigit(lookDate.charAt(1))
                                || !Character.isDigit(lookDate.charAt(2)) || !Character.isDigit(lookDate.charAt(3))
                                || !Character.isDigit(lookDate.charAt(5)) || !Character.isDigit(lookDate.charAt(6))
                                || !Character.isDigit(lookDate.charAt(8)) || !Character.isDigit(lookDate.charAt(9)))
                            throw new IllegalArgumentException();
                        System.out.println("Enter amount ($): ");
                        double lookAmount = scan.nextDouble();
                        scan.nextLine();
                        System.out.println("Enter description: ");
                        String lookDescribe = scan.nextLine();
                        Transaction lookTemp = new Transaction(lookDate, lookAmount, lookDescribe);
                        if (jack.exists(lookTemp)) {
                            for (int i = 0; i < jack.size(); i++)
                                if (lookTemp.equals(jack.getTransaction(i + 1))) {
                                    jack.printTransaction(i + 1);
                                    break;
                                }
                        } else
                            System.out.println("The transaction you are looking for does not exist in the ledger.");
                        break;
                    case ("s"):
                    case ("S"):
                        System.out.println("There are currently " + jack.size() + " transactions in the ledger.");
                        break;
                    case ("b"):
                    case ("B"):
                        backupJack = (GeneralLedger) jack.clone();
                        System.out.println("Created a backup of the current general ledger.");
                        break;
                    case ("cb"):
                    case ("cB"):
                    case ("Cb"):
                    case ("CB"):
                        String msg = (jack.equals(backupJack)) ? "The current general ledger is " +
                                "the same as the backup copy."
                                : "The current general ledger is NOT the same as the backup copy.";
                        System.out.println(msg);
                        break;
                    case ("pb"):
                    case ("pB"):
                    case ("Pb"):
                    case ("PB"):
                        backupJack.printAllTransactions();
                        break;
                    case ("rb"):
                    case ("rB"):
                    case ("Rb"):
                    case ("RB"):
                        jack = (GeneralLedger) backupJack.clone();
                        System.out.println("General ledger successfully reverted to the backup copy.");
                        break;
                    case ("pf"):
                    case ("pF"):
                    case ("Pf"):
                    case ("PF"):
                        System.out.printf("Financial Data for Jack's Account: " +
                                        "\nAssets: $%.2f\nLiabilities: $%.2f\nNet Worth: $%.2f\n\n"
                                , jack.getTotalDebitAmount(), jack.getTotalCreditAmount(),
                                (jack.getTotalDebitAmount() + jack.getTotalCreditAmount()));
                        break;
                    case ("q"):
                    case ("Q"):
                        System.out.println("Program terminating successfully...");
                        scan.close();
                        status = false;
                        System.exit(0);
                    default:
                        System.out.println("Invalid menu option. Please try again.");
                }
            } catch (IllegalArgumentException | InputMismatchException w) {
                System.out.println("Invalid input. Please try again.");
            } catch (FullGeneralLedgerException e) {
                System.out.println("The ledger you want to add a transaction to is full! " +
                        "Please try a different action.");
            } catch (InvalidTransactionException e) {
                System.out.println("Invalid transaction. Please try again.");
            } catch (TransactionAlreadyExistsException e) {
                System.out.println("The transaction you are trying to add already exists! Please try again.");
            } catch (InvalidLedgerPositionException e) {
                System.out.println("No transaction exists at that position. Please try again.");
            }
        }
    }
}