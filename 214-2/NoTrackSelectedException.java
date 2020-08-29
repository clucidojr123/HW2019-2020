/**
 * This exception is thrown when the user tries to call a selected track but the station has no track selected;
 * the station list is empty.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class NoTrackSelectedException extends Exception{
    public NoTrackSelectedException(){
    }
    public NoTrackSelectedException(String s){
        super(s);
    }
}