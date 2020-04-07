import java.util.Stack;
/**
 * This class represents a stack that holds the history of equations provided by the user.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class HistoryStack extends Stack<Equation>{

    public HistoryStack(){
    }

    /**
     * Pushes a new equation onto the stack and sets the position of the equation
     * @param newEquation
     * The equation to push onto the stack
     */
    public Equation push(Equation newEquation){
        newEquation.setPosition(this.size() + 1);
        super.push(newEquation);
        return newEquation;
    }

    /**
     * Returns an equation at the given location
     * @param position
     * The position of the equation to return
     * @return
     * returns the equation at the position
     */
    public Equation getEquation(int position){
        HistoryStack temp = new HistoryStack();
        while(!this.empty()){
            if(position == this.peek().getPosition()) {
                Equation getter = this.peek();
                while (!temp.empty()) {
                    this.push(temp.pop());
                }
                return getter;
            } else
                temp.push(this.pop());
        }
        while(!temp.empty()) {
            this.push(temp.pop());
        }
        return null;
    }

    /**
     * Makes a neatly formatted table of each equation within the stack
     * @return
     * Returns a string representation of this HistoryStack
     */
    public String toString(){
        String first = String.format("%-5s%s%35s%35s%30s%22s%15s\n",
                "#", "Equation", "Prefix", "Postfix", "Answer","Binary","Hexadecimal");
        for(int i = 0; i < 150; i++)
            first = first.concat("-");
        first = first.concat("\n");
        HistoryStack temp = new HistoryStack();
        int tempTop = this.size();
        while(!this.empty()) {
            if(this.peek().getPosition() == tempTop)
                first = first.concat(String.format("%-5s%s%37s%35s%24s%.3f%21s%16s\n",
                        this.peek().getPosition(), this.peek().getEquation(), this.peek().getPrefix(),
                        this.peek().getPostfix(), "", this.peek().getAnswer(),
                        this.peek().getBinary(),this.peek().getHex()));
            else
                first = first.concat(String.format("%-5s%s%35s%35s%19s%.3f%21s%16s\n",
                    this.peek().getPosition(), this.peek().getEquation(), this.peek().getPrefix(),
                    this.peek().getPostfix(), "", this.peek().getAnswer(),
                    this.peek().getBinary(),this.peek().getHex()));
            temp.push(this.pop());
        }
        while(!temp.empty()) {
            this.push(temp.pop());
        }
        return first;
    }
}
