/**
 * This exception is thrown when a transaction already exists within a ledger
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class TransactionAlreadyExistsException extends Exception{
    public TransactionAlreadyExistsException() {

    }
    public TransactionAlreadyExistsException(String s){
        super(s);
    }
}