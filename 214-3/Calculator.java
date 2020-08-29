import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents a calculator that holds the interface for the user to interact with.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */

public class Calculator {
    /**
     * This is the main method for interacting with the user.
     * A menu is provided with multiple options for the user to select.
     * The menu will pop up after every menu interaction until the user selects q or Q,
     * which will terminate the program.
     * If the user enters invalid inputs, error messages will appear, bringing the user back to the menu.
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        HistoryStack calc = new HistoryStack();
        HistoryStack undoRedo = new HistoryStack();
        boolean status = true;
        String menu = "[A] Add new equation\n" +
                "[F] Change equation from history\n" +
                "[B] Print previous equation\n" +
                "[P] Print full history\n" +
                "[U] Undo\n" +
                "[R] Redo\n" +
                "[C] Clear history\n" +
                "[Q] Quit ";
        System.out.println("Welcome to the Calculator!\n");
        while(status) {
            System.out.println(menu + "\nEnter a selection: ");
            try {
                String pick = scan.nextLine().toUpperCase();
                switch (pick) {
                    case ("A"):
                        System.out.println("Please enter an equation (in-fix notation): ");
                        String newEquation = scan.nextLine();
                        for(int i = 0; i < newEquation.length(); i++)
                            // Check if equation given has all valid characters
                            if(!(newEquation.charAt(i) == ' ' || newEquation.charAt(i) == '('
                                    || newEquation.charAt(i) == ')' || newEquation.charAt(i) == '+'
                                    || newEquation.charAt(i) == '-' || newEquation.charAt(i) == '*' ||
                                    newEquation.charAt(i) == '/' || newEquation.charAt(i) == '%' ||
                                    newEquation.charAt(i) == '^' || Character.isDigit(newEquation.charAt(i))))
                                throw new IllegalArgumentException();
                        Equation add = new Equation(newEquation);
                        calc.push(add);
                        if(add.isBalanced())
                            System.out.printf("The equation is balanced and the answer is %.3f\n", add.getAnswer());
                        else
                            System.out.println("The equation is not balanced.");
                        break;
                    case ("F"):
                        //Check if calculator is empty
                        if(calc.empty())
                            throw new EmptyStackException();
                        System.out.println("Which equation would you like to change? ");
                        int change = scan.nextInt();
                        //Check if position input is valid
                        if(change < 0 || change > calc.size())
                            throw new IllegalArgumentException();
                        String tempEquation = calc.getEquation(change).getEquation();
                        boolean changeStatus = true;
                        boolean asking = false;
                        System.out.println("Equation at position " + change
                                + ": " + tempEquation);
                        Equation temp = new Equation(tempEquation);
                        while(changeStatus) {
                            System.out.println("What would you like to do to the equation (Replace / remove / add)? ");
                            String changePick = scan.next().toUpperCase();
                            switch (changePick) {
                                case "REPLACE":
                                    System.out.println("What position would you like to change? ");
                                    int changePosition = scan.nextInt();
                                    if(changePosition < 0)
                                        throw new IllegalArgumentException();
                                    System.out.println("What would you like to replace it with? ");
                                    char newChar = scan.next().charAt(0);
                                    if(!(newChar == ' ' || newChar == '('
                                            || newChar == ')' || newChar == '+' || newChar == '-'
                                            || newChar == '*' || newChar == '/' || newChar == '%'
                                            || newChar == '^' || Character.isDigit(newChar)))
                                        throw new IllegalArgumentException();
                                    temp.changeEquation(changePick,changePosition,newChar);
                                    asking = true;
                                    break;
                                case "REMOVE":
                                    System.out.println("What position would you like to remove? ");
                                    int removePosition = scan.nextInt();
                                    if(removePosition < 0)
                                        throw new IllegalArgumentException();
                                    temp.changeEquation(changePick,removePosition,' ');
                                    asking = true;
                                    break;
                                case "ADD":
                                    System.out.println("What position would you like to add? ");
                                    int addPosition = scan.nextInt();
                                    if(addPosition < 0  || addPosition > calc.size())
                                        throw new IllegalArgumentException();
                                    System.out.println("What would you like to add? ");
                                    char addChar = scan.next().charAt(0);
                                    if(!(addChar == ' ' || addChar == '('
                                            || addChar == ')' || addChar == '+' || addChar == '-'
                                            || addChar == '*' || addChar == '/' || addChar == '%'
                                            || addChar == '^' || Character.isDigit(addChar)))
                                        throw new IllegalArgumentException();
                                    temp.changeEquation(changePick,addPosition,addChar);
                                    asking = true;
                                    break;
                                default:
                                    System.out.println("Invalid Choice. Please Try again.");
                                    scan.nextLine();
                            }
                            while(asking) {
                                System.out.println("Equation: " + temp.getEquation());
                                System.out.println("Would you like to make any more changes? ");
                                String newChoice = scan.nextLine().toUpperCase();
                                if (newChoice.equals("N") || newChoice.equals("NO")) {
                                    calc.push(temp);
                                    if (temp.isBalanced())
                                        System.out.printf("The equation is balanced and the answer is %.3f\n", temp.getAnswer());
                                    else
                                        System.out.println("The equation " + temp.getEquation() + " is not balanced.");
                                    changeStatus = false;
                                    asking = false;
                                } else if (!(newChoice.equals("Y") || newChoice.equals("YES"))) {
                                    System.out.println("Invalid Option. Please try again.");
                                    scan.nextLine();
                                }
                            }
                        }
                        break;
                    case ("B"):
                        //Check if calculator is empty
                        if(calc.empty())
                            throw new EmptyStackException();
                        System.out.println(calc.peek().toString());
                        break;
                    case ("P"):
                        //Check if calculator is empty
                        if(calc.empty())
                            throw new EmptyStackException();
                        System.out.println(calc.toString());
                        break;
                    case ("U"):
                        //Check if calculator is empty
                        if(calc.empty())
                            System.out.println("There is nothing to undo.");
                        else {
                            Equation tempUndo = calc.peek();
                            undoRedo.push(calc.pop());
                            System.out.println("Equation '" + tempUndo.getEquation() + "' undone.");
                        }
                        break;
                    case ("R"):
                        //Check if undo stack is empty
                        if(undoRedo.empty())
                            System.out.println("There is nothing to redo.");
                        else {
                            calc.push(undoRedo.pop());
                            System.out.println("Redoing equation '" + calc.peek().getEquation() + "'.");
                        }
                        break;
                    case ("C"):
                        calc = new HistoryStack();
                        System.out.println("Successfully reset the calculator.");
                        break;
                    case ("Q"):
                        System.out.println("Program terminating normally...");
                        scan.close();
                        status = false;
                        break;
                    default:
                        System.out.println("Invalid menu option. Please try again.");
                        scan.nextLine();
                }
            } catch (IllegalArgumentException w) {
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException w) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            } catch (EmptyStackException w) {
                System.out.println("There are no equations in the history. Please try again.");
            }
        }
    }
}