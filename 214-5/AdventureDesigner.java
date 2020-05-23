import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents the game designer used to make and play a game with scenes
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class AdventureDesigner{
    private static SceneTree dab = new SceneTree();
    private static Scanner scan = new Scanner(System.in);
    /**
     * This is the main method for interacting with the user.
     * A menu is provided with multiple options for the user to select.
     * The menu will pop up after every menu interaction until the user selects q or Q,
     * which will terminate the program.
     * If the user enters invalid inputs, error messages will appear, bringing the user back to the menu.
     * If the user selects "Play Game" they will play through the game they have created,
     * and it will bring the user back to the menu when finished
     */
    public static void main(String[] args){
        boolean status = true;
        String menu =
                "A) Add Scene\n" +
                "R) Remove Scene\n" +
                "S) Show Current Scene\n" +
                "P) Print Adventure Tree\n" +
                "B) Go Back A Scene\n" +
                "F) Go Forward A Scene\n" +
                "G) Play Game\n" +
                "N) Print Path To Cursor\n" +
                "M) Move Scene\n" +
                "Q) Quit";
        while(status) {
            System.out.println(menu + "\nEnter a selection: ");
            try {
                String pick = scan.nextLine().toUpperCase();
                switch (pick) {
                    case ("A"):
                        System.out.println("Please enter a title: ");
                        String newTitle = scan.nextLine();
                        System.out.println("Please enter a scene: ");
                        String newScene = scan.nextLine();
                        dab.addNewNode(newTitle,newScene);
                        System.out.println("Scene #" + SceneNode.getNumScenes() + " added.");
                        break;
                    case ("R"):
                        if(dab.getCursor() == null)
                            throw new NoSuchNodeException();
                        System.out.println("Please enter an option: ");
                        String removeOption = scan.nextLine().toUpperCase();
                        dab.removeScene(removeOption,true);
                        break;
                    case ("S"):
                        if(dab.getCursor() == null)
                            throw new NoSuchNodeException();
                        dab.getCursor().displayFullScene();
                        break;
                    case ("P"):
                        if(dab.getCursor() == null)
                            throw new NoSuchNodeException();
                        System.out.println(dab.toString());
                        break;
                    case ("B"):
                        dab.moveCursorBackwards();
                        System.out.println("Successfully moved back to " + dab.getCursor().getTitle());
                        break;
                    case ("F"):
                        System.out.println("Please enter an option: ");
                        String forwardOption = scan.nextLine().toUpperCase();
                        dab.moveCursorForward(forwardOption);
                        System.out.println("Successfully moved to " + dab.getCursor().getTitle());
                        break;
                    case ("G"):
                        if(dab.getCursor() == null)
                            throw new NoSuchNodeException();
                        playGame();
                        break;
                    case ("N"):
                        if(dab.getCursor() == null)
                            throw new NoSuchNodeException();
                        System.out.println(dab.getPathFromRoot());
                        break;
                    case ("M"):
                        if(dab.getCursor() == null || SceneNode.getNumScenes() == 1)
                            throw new NoSuchNodeException();
                        System.out.println("Move current scene to: ");
                        int sceneSelect = scan.nextInt();
                        scan.nextLine();
                        if (sceneSelect <= 0 || sceneSelect > SceneNode.getNumScenes())
                            throw new IllegalArgumentException();
                        dab.moveScene(sceneSelect);
                        System.out.println("Successfully moved scene.");
                        break;
                    case ("Q"):
                        System.out.println("Program terminating normally...");
                        scan.close();
                        status = false;
                        break;
                    default:
                        System.out.println("Invalid menu option. Please try again.");
                }
            } catch (IllegalArgumentException w) {
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException w) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            } catch (FullSceneException e) {
                System.out.println("The scene you are trying to add to is full!");
            } catch (NoSuchNodeException e) {
                System.out.println("There are no scenes in the tree or " +
                        "there is no node to select with the given input. Please try again.");
            }
        }
    }

    /**
     * This method creates the interface for playing the user created game.
     */
    public static void playGame(){
        System.out.println("Now beginning game...\n");
        dab.setCursor(dab.getRoot());
        while(!dab.getCursor().isEnding()) {
            try {
                dab.getCursor().displayScene();
                System.out.println("Please enter an option: ");
                String option = scan.nextLine().toUpperCase();
                dab.moveCursorForward(option);
            } catch (Exception e) {
                System.out.println("Invalid Option. Please try again.");
            }
        }
        dab.getCursor().displayScene();
        System.out.println("The End.\n\nReturning back to creator mode...");
    }
}