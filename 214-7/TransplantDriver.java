import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents a transplant driver that hold the main interface between the user and
 * the database of recipients and donors
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class TransplantDriver {
    public static final String DONOR_FILE = "donors.txt";
    public static final String RECIPIENT_FILE = "recipients.txt";

    public TransplantDriver() {

    }
    /**
     * This is the main method for creating an interface between the user and the management system.
     * The menu will be shown after every selection unless the user selects "q" which terminates the program.
     */
    public static void main(String[] args) throws FileNotFoundException, FullPatientException {
        Scanner scan = new Scanner(System.in);
        boolean status = true;
        TransplantGraph transplant;
        String menu =
                "\n(LR) - List all recipients\n" +
                        "(LO) - List all donors\n" +
                        "(AO) - Add new donor\n" +
                        "(AR) - Add new recipient\n" +
                        "(RO) - Remove donor\n" +
                        "(RR) - Remove recipient\n" +
                        "(SR) - Sort recipients\n" +
                        "(SO) - Sort donors\n" +
                        "(Q) - Quit";
        try {
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            transplant = (TransplantGraph) fin.readObject();
            System.out.println("Loading data from transplant.obj...");
            fin.close();
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("transplant.obj not found. Creating new TransplantGraph object...");
            transplant = TransplantGraph.buildFromFiles("donors.txt","recipients.txt");
        }
        while (status) {
            System.out.println(menu + "\nEnter a selection: ");
            try {
                String pick = scan.nextLine().toUpperCase();
                switch (pick) {
                    case ("LR"):
                        transplant.printAllRecipients();
                        break;
                    case ("LO"):
                        transplant.printAllDonors();
                        break;
                    case ("AO"):
                        System.out.println("Please enter the organ donor name: ");
                        String donorName = scan.nextLine();
                        System.out.println("Please enter the organ they are donating: ");
                        String donorOrgan = scan.nextLine();
                        System.out.println("Please enter the blood type of this donor: ");
                        String donorBloodType = scan.nextLine();
                        System.out.println("Please enter the age of the donor: ");
                        int donorAge = scan.nextInt();
                        scan.nextLine();
                        Patient tempDonor = new Patient(donorName, donorOrgan, donorAge,
                                new BloodType(donorBloodType), -1, true, 0);
                        transplant.addDonor(tempDonor);
                        System.out.println("The organ donor with ID " + tempDonor.getID()
                                + " was successfully added to the donor list!");
                        break;
                    case ("AR"):
                        System.out.println("Please enter the new recipient's name: ");
                        String recName = scan.nextLine();
                        System.out.println("Please enter the blood type of the recipient: ");
                        String recBloodType = scan.nextLine();
                        System.out.println("Please enter the recipient's age: ");
                        int recAge = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Please enter the organ needed: ");
                        String recOrgan = scan.nextLine();
                        Patient tempRec = new Patient(recName, recOrgan, recAge,
                                new BloodType(recBloodType), -1, false, 0);
                        transplant.addDonor(tempRec);
                        System.out.println(tempRec.getName() + " is now on the organ transplant waitlist!");
                        break;
                    case ("RO"):
                        System.out.println("Please enter the name of the organ donor to remove: ");
                        String removeDonor = scan.nextLine();
                        transplant.removeDonor(removeDonor);
                        System.out.println(removeDonor + " was removed from the organ donor list.");
                        break;
                    case ("RR"):
                        System.out.println("Please enter the name of the recipient to remove: ");
                        String removeRec = scan.nextLine();
                        transplant.removeRecipient(removeRec);
                        System.out.println(removeRec + " was removed from the organ transplant waitlist.");
                        break;
                    case ("SR"):
                        String recMenu = "\n" +
                                "(I) Sort by ID\n" +
                                "(N) Sort by Number of Donors\n" +
                                "(B) Sort by Blood Type\n" +
                                "(O) Sort by Organ Donated\n" +
                                "(Q) Back to Main Menu";
                        String recChoice = "";
                        while(!recChoice.equals("Q")) {
                            try {
                                System.out.println("Please enter a selection: \n" + recMenu);
                                recChoice = scan.nextLine().toUpperCase();
                                transplant.sortList(false, recChoice);
                            } catch (IllegalArgumentException w){
                                System.out.println("Invalid submenu option. Please try again.");
                            }
                        }
                        System.out.println("Returning to main menu. ");
                        break;
                    case ("SO"):
                        String donorMenu = "\n" +
                                "(I) Sort by ID\n" +
                                "(N) Sort by Number of Recipients\n" +
                                "(B) Sort by Blood Type\n" +
                                "(O) Sort by Organ Needed\n" +
                                "(Q) Back to Main Menu";
                        String donorChoice = "";
                        while(!donorChoice.equals("Q")) {
                            try {
                                System.out.println("Please enter a selection: \n" + donorMenu);
                                donorChoice = scan.nextLine().toUpperCase();
                                transplant.sortList(true, donorChoice);
                            } catch (IllegalArgumentException w){
                                System.out.println("Invalid submenu option. Please try again.");
                            }
                        }
                        System.out.println("Returning to main menu. ");
                        break;
                    case ("Q"):
                        System.out.println("Writing data to transplant.obj...");
                        FileOutputStream file = new FileOutputStream("transplant.obj");
                        ObjectOutputStream fout = new ObjectOutputStream(file);
                        fout.writeObject(transplant); // Here "objToWrite" is the object to serialize
                        fout.close();
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
            } catch (FullPatientException e) {
                System.out.println("The number of patients has reached its limit. Please try again.");
            } catch (PatientNotFoundException e) {
                System.out.println("There is no patient with the given name. Please try again.");
            } catch (IOException e) {
                System.out.println("The filename you are providing does not exist. Please try again.");
           }

        }
    }
}