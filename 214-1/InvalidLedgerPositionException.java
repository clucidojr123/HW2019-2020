/**
 * This exception is thrown when the ledger position of a transaction is invalid
 * @author Cesare Lucido
 *      e-mail: cesare.lucido@stonybrook.edu
 *      sbid: 112831455
 *      R01
 */
public class InvalidLedgerPositionException extends Exception{
    public InvalidLedgerPositionException() {

    }
    public InvalidLedgerPositionException(String s){
        super(s);
    }
}