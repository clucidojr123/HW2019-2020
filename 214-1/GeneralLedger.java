/**
 * This class represents a General Ledger that can hold transaction objects and the total debit and credit amounts.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class GeneralLedger{
    public static final int MAX_TRANSACTIONS = 50;
    private Transaction[] ledger = new Transaction[MAX_TRANSACTIONS];
    private double totalDebitAmount;
    private double totalCreditAmount;
    private int items;

    /**
     * Constructor for GeneralLedger class
     */
    public GeneralLedger(){
    }

    /**
     * @param list
     * Takes an array of transactions and creates a deep clone for the new GeneralLedger
     * @param debit
     * The total debit amount
     * @param credit
     * The total credit amount
     * @param items
     * The number of transactions in the list array
     */
    public GeneralLedger(Transaction[] list, double debit, double credit, int items) {
        this.totalDebitAmount = debit;
        this.totalCreditAmount = credit;
        this.items = items;
        for(int i = 0; i < this.items; i++)
            this.ledger[i] = (Transaction)list[i].clone();
    }

    /**
     * Getter for number of transactions in the ledger
     * @return
     * Returns the number of transactions in the ledger
     */
    public int size(){
        return this.items;
    }

    /**
     * Getter for  the total debit amount in the GeneralLedger
     * @return
     * Returns the new amount for the total debit
     */
    public double getTotalDebitAmount(){
        return this.totalDebitAmount;
    }

    /**
     * Getter for  the total credit amount in the GeneralLedger
     * @return
     * Returns the new amount for the total credit
     */
    public double getTotalCreditAmount(){
        return this.totalCreditAmount;
    }

    /**
     * Sets the number of items in a ledger. Used in the static filter() method
     * @param s
     * The number of items in the ledger
     */
    public void setItems(int s){
        this.items = s;
    }

    /**
     * Sets the total debit amount in the GeneralLedger
     * @param d
     * The new amount for the total debit
     */
    public void setTotalDebitAmount(double d){
        this.totalDebitAmount += d;
    }

    /**
     * Sets the total credit amount in the GeneralLedger
     * @param d
     * The new amount for the total credit
     */
    public void setTotalCreditAmount(double d){
        this.totalCreditAmount += d;
    }

    /**
     * Adds newTransaction into this GeneralLedger if it does not already exist
     * The new transaction is inserted in date order
     * If there are multiple entries with the same date,
     * the new entry will be placed directly after that date's current entries
     * @param newTransaction
     * The new transaction to add to the ledger
     * @throws FullGeneralLedgerException
     * Thrown if the ledger is already at it's max capacity (50 transactions).
     * @throws TransactionAlreadyExistsException
     * Thrown if the transaction to be added is already within the ledger.
     * @throws InvalidTransactionException
     * Thrown if the date provided is outside of the bounds for the ledger
     * (Between years 1900-2050, months 1-12, days 1-30).
     */
    public void addTransaction(Transaction newTransaction) throws FullGeneralLedgerException, TransactionAlreadyExistsException,
            InvalidTransactionException {
        if (items == 49)
            throw new FullGeneralLedgerException();
        if (this.exists(newTransaction))
            throw new TransactionAlreadyExistsException();
        String[] temp = newTransaction.getDate().split("/");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = Integer.parseInt(temp[2]);
        if ((year < 1900) || (year > 2050) || (month < 1) || (month > 12) || (day < 1) || (day > 30)
                || newTransaction.getAmount() == 0)
            throw new InvalidTransactionException();
        else {
            this.items++;
            if(newTransaction.getAmount() > 0)
                this.setTotalDebitAmount(newTransaction.getAmount());
            else
                this.setTotalCreditAmount(newTransaction.getAmount());
            ledger[this.size() - 1] = (Transaction)newTransaction.clone();
            for(int i = this.size() - 1; i > 0; i--) {
                if(newTransaction.compareTo(ledger[i - 1]) < 0) {
                    Transaction tempo = (Transaction)ledger[i].clone();
                    ledger[i] = (Transaction)ledger[i - 1].clone();
                    ledger[i - 1] = (Transaction)tempo.clone();
                }

            }
        }
    }

    /**
     * Removes the transaction at the position provided.
     * The ledger shifts some entries to remove gaps in the array.
     * @param position
     * The position of the transaction to remove
     * @throws InvalidLedgerPositionException
     * Thrown if the position provided is invalid
     */
    public void removeTransaction(int position) throws InvalidLedgerPositionException{
        if((position <= 0) || (position > this.items))
            throw new InvalidLedgerPositionException();
        for(int i = position - 1; i < this.items - 1; i++)
            ledger[i] = (Transaction) ledger[i + 1].clone();
        this.items--;
    }

    /**
     * Returns the transaction from the position provided
     * @param position
     * The position of the transaction to return
     * @return
     * returns the transaction at the specified position
     * @throws InvalidLedgerPositionException
     * Thrown if the position provided is invalid
     */
    public Transaction getTransaction(int position) throws InvalidLedgerPositionException{
        if((position <= 0) || (position > this.items))
            throw new InvalidLedgerPositionException();
        return (Transaction) ledger[position - 1].clone();
    }

    /**
     * Checks if the transaction provided is within the ledger
     * @param trans
     * The transaction object to search for
     * @return
     * returns true if the transaction exists in the ledger and false otherwise
     */
    public boolean exists(Transaction trans){
        for(Transaction dab : this.ledger){
            if(trans.equals(dab)) return true;
        }
        return false;
    }

    /**
     * Calls the toString method and prints it
     */
    public void printAllTransactions(){
        System.out.println(this.toString());
        System.out.println();
    }

    /**
     * Prints a single transaction rather then all transactions
     * @param t
     * The position of the transaction to be printed
     * @throws InvalidLedgerPositionException
     * Thrown if the position is invalid
     */
    public void printTransaction(int t) throws InvalidLedgerPositionException {
        if((t <= 0) || (t > this.items))
            throw new InvalidLedgerPositionException();
        System.out.print(String.format("%-10s%-15s%-10s%10s%20s\n", "No.", "Date", "Debit", "Credit", "Description"));
        for(int i = 0; i < 100; i++)
            System.out.print("-");
        if (ledger[t - 1].getAmount() > 0) {
            System.out.println(String.format("\n%-10s%-15s%-29.2f%s",
                    (t), ledger[t - 1].getDate(), ledger[t - 1].getAmount(), ledger[t - 1].getDescription()));
        } else {
            System.out.println(String.format("\n%-10s%-29s%-15.2f%s",
                    (t), ledger[t - 1].getDate(), -ledger[t - 1].getAmount(), ledger[t - 1].getDescription()));
        }
        System.out.println();
    }

    /**
     * Returns a String representation of this GeneralLedger object as a formatted table of all transactions
     * @return
     * Returns a String representation of this GeneralLedger
     */
    public String toString(){
        String first = String.format("%-10s%-15s%-10s%10s%20s\n", "No.", "Date", "Debit", "Credit", "Description");
        for(int i = 0; i < 100; i++)
            first = first.concat("-");
        System.out.println();
        for(int i = 0; i < this.items; i++) {
            if (ledger[i].getAmount() > 0) {
                first = first.concat(String.format("\n%-10s%-15s%-29.2f%s",
                        (i + 1), ledger[i].getDate(), ledger[i].getAmount(), ledger[i].getDescription()));
            } else {
                first = first.concat(String.format("\n%-10s%-29s%-15.2f%s",
                        (i + 1), ledger[i].getDate(), -ledger[i].getAmount(), ledger[i].getDescription()));
            }
        }
        return first;
    }

    /**
     * Creates a deep copy of the GeneralLedger. The ledger is deep copied in the GeneralLedger's constructor
     * @return
     * Returns a deep copy of the GeneralLedger
     */
    public Object clone() {
        return new GeneralLedger(this.ledger, this.totalDebitAmount, this.totalCreditAmount, this.items);
    }

    /**
     * Compares an object to this GeneralLedger to see if they are equal
     * @param o
     * The object to compare the GeneralLeger to
     * @return
     * Returns a boolean indicating if the two objects are equal
     */
    public boolean equals(Object o){
        boolean status = true;
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        GeneralLedger that = (GeneralLedger) o;
        for(int i = 0; i < this.items; i++)
            if(!this.ledger[i].equals(that.ledger[i])) {
                status = false;
                break;
            }
        return status;
    }

    /**
     * Filters a GeneralLedger to only print transactions from a given date
     * @param gl
     * The GeneralLedger to filter
     * @param date
     * The date to filter by
     */
    public static void filter(GeneralLedger gl, String date){
        GeneralLedger temp = new GeneralLedger();
        for(int i = 0; i < gl.size(); i++)
            if(gl.ledger[i].getDate().equals(date)) {
                temp.ledger[temp.items] = (Transaction) gl.ledger[i].clone();
                temp.setItems(temp.size() + 1);
            }
        temp.printAllTransactions();
    }

}





