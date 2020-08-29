/**
 * This exception is thrown when the transaction is invalid
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class InvalidTransactionException extends Exception{
    public InvalidTransactionException(){

    }
    public InvalidTransactionException(String s){
        super(s);
    }
}