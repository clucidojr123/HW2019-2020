/**
 * This exception is thrown when a new track has the same number as a track already in a station.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class TrackAlreadyExistsException extends Exception{
    public TrackAlreadyExistsException(){
    }
    public TrackAlreadyExistsException(String s){
        super(s);
    }
}