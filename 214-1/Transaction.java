/**
 * A transaction contains general information such as the date, amount
 * (or the change in cash as a result of the transaction), and description.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class Transaction implements Comparable<Transaction>{
    private String date;
    private double amount;
    private String description;
    /**
     * Constructor for Transaction class
     * @param date
     * The date the transaction occurred
     * @param a
     * The amount of debit or credit in the transaction
     * @param d
     * The description of the transaction
     */
    public Transaction(String date, double a, String d){
        this.date = date;
        this.amount = a;
        this.description = d;
    }

    /**
     * Getter for amount of debit or credit in transaction
     * @return
     * Returns the amount of debit or credit in transaction
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Getter for the date of the transaction
     * @return
     * Returns the date of the transaction
     */
    public String getDate(){
        return this.date;
    }

    /**
     * Getter for the description of the transaction
     * @return
     * Returns the description of the transaction
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Creates a deep copy of a transaction
     * @return
     * Returns a deep copy of a transaction
     */
    public Object clone() {
        return new Transaction(this.date, this.amount, this.description);
    }

    /**
     * Compares an object to this transaction to see if they are equal
     * @param o
     * The object to compare the transaction to
     * @return
     * Returns a boolean indicating if the two objects are equal
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return (this.amount == that.amount) &&
                (this.date.equals(that.date)) &&
                (this.description.equals(that.description));
    }

    /**
     * Comparing two transactions by their dates. This helps when inserting a new transaction into a ledger
     * @param o
     * The transaction to compare with
     * @return
     * Returns an integer depending on comparison of dates. 1 indicates a newer transaction, -1 indicates a lower one,
     * and 0 results in both having the same exact date.
     */
    public int compareTo(Transaction o) {
        String[] thisTemp = this.getDate().split("/");
        String[] thatTemp = o.getDate().split("/");
        if (Integer.parseInt(thisTemp[0]) > Integer.parseInt(thatTemp[0]))
            return 1;
        else if(Integer.parseInt(thisTemp[0]) < Integer.parseInt(thatTemp[0]))
            return -1;
        else if(Integer.parseInt(thisTemp[1]) > Integer.parseInt(thatTemp[1]))
            return 1;
        else if(Integer.parseInt(thisTemp[1]) < Integer.parseInt(thatTemp[1]))
            return -1;
        else if(Integer.parseInt(thisTemp[2]) > Integer.parseInt(thatTemp[2]))
            return 1;
        else if(Integer.parseInt(thisTemp[2]) < Integer.parseInt(thatTemp[2]))
            return -1;
        return 0;
    }

}
