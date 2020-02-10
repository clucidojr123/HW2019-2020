/**
 * This exception is thrown when the transaction is invalid
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class InvalidTransactionException extends Exception{
    public InvalidTransactionException(){

    }
    public InvalidTransactionException(String s){
        super(s);
    }
}