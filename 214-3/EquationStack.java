import java.util.Stack;
/**
 * This class represents a station that includes a the interface between the user and the
 * station to perform different tasks. The station also contains a linked list of tracks,
 * which themselves have a linked list of trains.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class EquationStack extends Stack<String> {
    public EquationStack() {

    }

    /**
     * Pushes a String onto the stack and adds a space after it
     * @param s
     * The string to push
     */
    public String push(String s){
        s = s + " ";
        super.push(s);
        return "You should never see this in console.";
    }

    /**
     * Pushes a character onto the stack by adding space (char becomes a string)
     * @param c
     * The character to push onto the stack
     */
    public void push(char c){
        String s = c + " ";
        super.push(s);
    }
}