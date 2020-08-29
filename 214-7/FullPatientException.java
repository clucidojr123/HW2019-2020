/**
 * This class represents a FullPatientException, which is thrown when the database has reached it's maximum number
 * of recipients or donors (100 for each)
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class FullPatientException extends Exception{
    public FullPatientException(){

    }
}