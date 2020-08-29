/**
 * This exception is thrown when the ledger for a generalLedger object is full
 * @author Cesare Lucido
 *      e-mail: cesare.lucido@stonybrook.edu
 *      R01
 */
public class FullGeneralLedgerException extends Exception{
    public FullGeneralLedgerException(){

    }
    public FullGeneralLedgerException(String s){
        super(s);
    }
}
