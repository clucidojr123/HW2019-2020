/**
 * This exception is thrown when the train inputted by the user is invalid.
 * A train is invalid when the time inputted is invalid or there is a scheduling conflict when attempting to insert
 * a train into a track.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class InvalidTrainException extends Exception{
    public InvalidTrainException(){
    }
    public InvalidTrainException(String s){
        super(s);
    }
}