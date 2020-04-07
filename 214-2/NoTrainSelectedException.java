/**
 * This exception is thrown when the user tries to call a selected train but the track has no train selected;
 * the track list is empty.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class NoTrainSelectedException extends Exception{
    public NoTrainSelectedException(){
    }
    public NoTrainSelectedException(String s){
        super(s);
    }
}