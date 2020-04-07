/**
 * This exception is thrown when a new train has the same number as a train already in a track.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class TrainAlreadyExistsException extends Exception{
    public TrainAlreadyExistsException(){
    }
    public TrainAlreadyExistsException(String s){
        super(s);
    }
}