/**
 * This class represents an equation that holds an answer in decimal, binary, and hexadecimal if it is balanced.
 * It also holds the prefix and postfix representation of the given infix equation if it is balanced.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */

public class Equation {
    private String equation;
    private String prefix;
    private String postfix;
    private double answer;
    private String binary;
    private String hex;
    private int position;
    private boolean balanced;

    /**
     * Constructor for the equation class. Takes in a string that is validated to
     * contain only mathematical characters and if it is balanced, calculates all of the other
     * values/strings associated with the equation object.
     * @param e
     * The infix equation string
     */
    public Equation(String e){
        this.equation = e;
        this.balanced = this.isBalanced();
        if(balanced) {
            postfix = toPostfix(equation);
            this.toPrefix();
            this.solveEquation();
        } else
            this.setUnbalanced();
    }

    /**
     * Getter for the position of this equation within a HistoryStack
     * @return
     * returns the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Getter for the infix equation string
     * @return
     * returns the infix equation as a string
     */
    public String getEquation() {
        return equation;
    }
    /**
     * Getter for the prefix equation string
     * @return
     * returns the prefix equation as a string
     */
    public String getPrefix() {
        return prefix;
    }
    /**
     * Getter for the postfix equation string
     * @return
     * returns the postfix equation as a string
     */
    public String getPostfix() {
        return postfix;
    }
    /**
     * Getter for the answer of the equation
     * @return
     * returns the answer as a double
     */
    public double getAnswer() {
        return answer;
    }
    /**
     * Getter for the binary answer of this equation
     * @return
     * returns the binary answer of this equation as a string
     */
    public String getBinary() {
        return binary;
    }
    /**
     * Getter for the hexadecimal answer of this equation
     * @return
     * returns the binary answer of this equation as a string
     */
    public String getHex() {
        return hex;
    }

    /**
     * Sets the position of this equation within a HistoryStack
     * @param position
     * The new position of this equation
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets the equation of this object and re-evaluates the other values within the object
     * @param equation
     * The new infix equation string
     */
    public void setEquation(String equation) {
        this.equation = equation;
        this.balanced = this.isBalanced();
        if(balanced) {
            postfix = toPostfix(equation);
            this.toPrefix();
            this.solveEquation();
        } else
            this.setUnbalanced();
    }

    /**
     * Checks if the equation has balanced parenthesis
     * @return
     * Returns a boolean indicating if the equation has balanced parenthesis
     */
    public boolean isBalanced(){
        EquationStack temp = new EquationStack();
        for(int i = 0; i < equation.length(); i++){
            if(equation.charAt(i) == '(')
                temp.push('(');
            else if(equation.charAt(i) == ')') {
                if(temp.empty()) return false;
                temp.pop();
            }
        }
        this.balanced = temp.empty();
        return temp.empty();
    }

    /**
     * Changes a integer to binary
     * @param number
     * the integer to convert
     * @return
     * returns the integer as a binary number as a string
     */
    public String decToBin(int number){
        if(!this.balanced)
            return "N/A";
        int ogNum = number;
        number = Math.abs(number);
        String temp = "";
        if(number == 0)
            return "0";
        while(number > 0){
            int remain = number % 2;
            temp = remain + temp;
            number /= 2;
        }
        if(ogNum < 0)
            temp = "-" + temp;
        binary = temp;
        return temp;
    }

    /**
     * If the equation is unbalanced, this method sets the answer to 0
     * and changes the prefix and postfix string to "N/A".
     */
    public void setUnbalanced(){
        balanced = false;
        answer = 0;
        binary = "0";
        hex = "0";
        prefix = "N/A";
        postfix = "N/A";
    }
    /**
     * Changes a integer to hexadecimal
     * @param number
     * the integer to convert
     * @return
     * returns the integer as a hexadecimal number as a string
     */
    public String decToHex(int number){
        if(!this.balanced)
            return "N/A";
        int ogNum = number;
        number = Math.abs(number);
        String base = "0123456789ABCDEF";
        String temp = "";
        if(number == 0)
            return "0";
        while(number > 0){
            int remain = number % 16;
            temp = base.charAt(remain) + temp;
            number /= 16;
        }
        if(ogNum < 0)
            temp = "-" + temp;
        hex = temp;
        return temp;
    }

    /**
     * Creates a neatly formatted table of this equation
     * @return
     * returns a textual representation of this equation as a string
     */
    public String toString() {
        String first = String.format("%-5s%s%35s%35s%30s%22s%15s\n",
                "#", "Equation", "Prefix", "Postfix", "Answer","Binary","Hexadecimal");
        for(int i = 0; i < 150; i++)
            first = first.concat("-");
        first = first.concat("\n");
        first = first.concat(String.format("%-5s%s%35s%35s%19s%.3f%22s%15s\n",
                position, equation, prefix,
                postfix, "", answer,
                binary,hex));
        return first;
    }

    /**
     * Solves the equation string of this object by using the postfix expression to evaluate.
     * After calculating the answer, it calls decToBin and decToHex to convert the answer.
     */
    public void solveEquation(){
        if(!balanced) {
            this.setUnbalanced();
            return;
        }
        NumStack temp = new NumStack();
        for(int i = 0; i < postfix.length(); i++) {
            if(Character.isDigit(postfix.charAt(i))) {
                int w = i;
                while(w != equation.length() && Character.isDigit(postfix.charAt(w)))
                    w++;
                double add = Double.parseDouble(postfix.substring(i, w));
                i = w;
                temp.push(add);
            } else if (postfix.charAt(i) != ' '){
                double two = temp.pop();
                double one = temp.pop();
                double result;
                char op = postfix.charAt(i);
                switch(op) {
                    case '+':
                        result = one + two;
                        temp.push(result);
                        break;
                    case '-':
                        result = one - two;
                        temp.push(result);
                        break;
                    case '*':
                        result = one * two;
                        temp.push(result);
                        break;
                    case '/':
                        if(two == 0) {
                            this.setUnbalanced();
                            return;
                        }
                        result = one / two;
                        temp.push(result);
                        break;
                    case '%':
                        if(two == 0) {
                            this.setUnbalanced();
                            return;
                        }
                        result = one % two;
                        temp.push(result);
                        break;
                    case '^':
                        result = Math.pow(one, two);
                        temp.push(result);
                        break;
                    default:
                        this.setUnbalanced();
                        return;
                }
            }
        }
        answer = temp.pop();
        binary = this.decToBin((int)answer);
        hex = this.decToHex((int)answer);
    }

    /**
     * Method to get the precedence of an operator.
     * @param c
     * the operator to calculate the precedence
     * @return
     * returns the precedence of an operator as an integer,
     * or -1 if the input is not an operator.
     */
    public int precedence(char c){
        switch(c){
            case '$':
                return 0;
            case '(':
                return 1;
            case '+':
            case '-':
                return 2;
            case '*':
            case '/':
            case '%':
                return 3;
            case '^':
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Returns the postfix equation for a given infix equation
     * @param express
     * the equation to convert
     * @return
     * returns the converted postfix equation as a string.
     */
    public String toPostfix(String express){
        String s = "";
        EquationStack temp = new EquationStack();
        temp.push('$');
        for(int i = 0; i < express.length(); i++) {
            //Gets the next number from the expression
            if(Character.isDigit(express.charAt(i))) {
                int w = i;
                while (w != express.length() && Character.isDigit(express.charAt(w)))
                    w++;
                String token = express.substring(i, w);
                i = w;
                s = s.concat(token + " ");
            } else if(express.charAt(i) == '(') //Pushes ( on the stack
                temp.push('(');
            // If the char is a ')', pops operator from stack and adds it to postfix until it reaches '('
            else if(express.charAt(i) == ')') {
                String top = temp.pop();
                while(!top.equals("( ")) {
                    s = s.concat(top);
                    top = temp.pop();
                }
            } // If the char is an operator
            else if(express.charAt(i) != ' ' && express.charAt(i) != '(') {
                String top = temp.peek();
                while(precedence(top.charAt(0)) >= precedence(express.charAt(i))) {
                    s = s + temp.pop();
                    top = temp.peek();
                }
                temp.push(express.charAt(i));
            }
        }
        // Add the rest of the operators
        String top = temp.pop();
        while(!top.equals("$ ")) {
            if(!top.equals("( "))
                s = s.concat(top);
            top = temp.pop();
        }
        return s;
    }

    /**
     * Recursive method to reverse the equation string to convert to prefix
     * @param s
     * String to reverse.
     * @return
     * Returns the reversed string
     */
    public String reverse(String s){
        if(s.length() <= 1)
            return s;
        else return(reverse(s.substring(1)) + s.charAt(0));
    }

    /**
     * Converts the infix equation of this object to prefix.
     * First, it reverses the string and changes the parenthesis after to make it balanced.
     * Then it calls the toPostfix method on the new string and after, reverses it again.
     */
    public void toPrefix() {
        char[] reverseArray = reverse(equation).toCharArray();
        String r = "";
        for(int i = 0; i < reverseArray.length; i++) {
            if (reverseArray[i] == '(')
                reverseArray[i] = ')';
            else if (reverseArray[i] == ')')
                reverseArray[i] = '(';
            r = r + reverseArray[i];
        }
        prefix = reverse(toPostfix(r)).substring(1);
    }

    /**
     * Method to change the equation by adding, replacing, or removing a character
     * @param s
     * the type of change
     * @param selection
     * the position which is selected to change
     * @param newChar
     * The character to add or replace
     */
    public void changeEquation(String s,int selection, char newChar){
        switch(s){
            case "REPLACE":
                char[] tempReplace = equation.toCharArray();
                String newReplace = "";
                for(int i = 0; i < tempReplace.length; i++) {
                    if(i == selection)
                        tempReplace[i] = newChar;
                    newReplace = newReplace + tempReplace[i];
                }
                this.setEquation(newReplace);
                break;
            case "REMOVE":
                char[] tempRemove = equation.toCharArray();
                String newRemove = "";
                for(int i = 0; i < tempRemove.length; i++) {
                    if(i == selection)
                        i++;
                    newRemove = newRemove + tempRemove[i];
                }
                this.setEquation(newRemove);
                break;
            case "ADD":
                char[] tempAdd = equation.toCharArray();
                String newAdd = "";
                for(int i = 0; i < tempAdd.length; i++) {
                    if(i == selection)
                        newAdd = newAdd + newChar;
                    newAdd = newAdd + tempAdd[i];
                }
                this.setEquation(newAdd);
                break;
        }
    }
}