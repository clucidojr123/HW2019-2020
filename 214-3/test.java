/**
 * This class represents a station that includes a the interface between the user and the
 * station to perform different tasks. The station also contains a linked list of tracks,
 * which themselves have a linked list of trains.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class test{
    public static void main(String[] args){
        HistoryStack dab = new HistoryStack();
        Equation one = new Equation("(5839/23 + ((68-7) % 8))");
        Equation two = new Equation("2 + 22");
        //System.out.println(one.reverse(one.getEquation()));
        //System.out.println(one.getPrefix());
        //System.out.println(one.getPostfix());
        //System.out.println(one.getAnswer());
        dab.push(one);
        dab.push(two);
        System.out.println(dab.toString());
        //System.out.println(one.toString());
    }
}