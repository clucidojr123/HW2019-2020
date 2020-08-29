/**
 * This exception is thrown when the ledger position of a transaction is invalid
 * @author Cesare Lucido
 *      e-mail: cesare.lucido@stonybrook.edu
 *      R01
 */
public class InvalidLedgerPositionException extends Exception{
    public InvalidLedgerPositionException() {

    }
    public InvalidLedgerPositionException(String s){
        super(s);
    }
}