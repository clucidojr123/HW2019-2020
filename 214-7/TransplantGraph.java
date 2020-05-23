import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * This class represents an Transplant Graph that contains an adjacency matrix for the organ donors and recipients.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class TransplantGraph implements Serializable {
    private ArrayList<Patient> donors = new ArrayList<>();
    private ArrayList<Patient> recipients = new ArrayList<>();
    public static final int MAX_PATIENTS = 100;
    private boolean[][] connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];

    /**
     * Constructor for TransplantGraph Class
     */
    public TransplantGraph(){

    }

    /**
     * Method to build the database from a donor and recipient text file
     * @param donorFile
     * the name of the donor text file
     * @param recipientFile
     * the name of the recipient text file
     * @return
     * returns a TransplantGraph with the patient information of both files
     * @throws FileNotFoundException
     * Thrown when the file name is invalid
     * @throws FullPatientException
     * Thrown if the max amount of recipients of donors is reached
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile)
            throws FileNotFoundException, FullPatientException {
        TransplantGraph temp = new TransplantGraph();
        Scanner scan = new Scanner (new File(donorFile));
        System.out.println("Loading data from '" + donorFile + "'...");
        String[] tempInfo;
        Patient tempPatient;
        while(scan.hasNextLine()){
            tempInfo = scan.nextLine().split(", ");
            tempInfo[3] = tempInfo[3].substring(0, 1).toUpperCase() + tempInfo[3].substring(1);
            tempPatient = new Patient(tempInfo[1],tempInfo[3], Integer.parseInt(tempInfo[2]),
                    new BloodType(tempInfo[4]), Integer.parseInt(tempInfo[0]), true, 0);
            temp.addDonor(tempPatient);
        }
        scan = new Scanner(new File(recipientFile));
        System.out.println("Loading data from '" + recipientFile + "'...");
        while(scan.hasNextLine()){
            tempInfo = scan.nextLine().split(", ");
            tempInfo[3] = tempInfo[3].substring(0, 1).toUpperCase() + tempInfo[3].substring(1);
            tempPatient = new Patient(tempInfo[1],tempInfo[3], Integer.parseInt(tempInfo[2]),
                    new BloodType(tempInfo[4]), Integer.parseInt(tempInfo[0]), false, 0);
            temp.addRecipient(tempPatient);
        }
        scan.close();
        return temp;
    }

    /**
     * Method to add a recipient patient into the database
     * @param patient
     * The new patient to add
     * @throws FullPatientException
     * Thrown if the max amount of recipients is reached
     */
    public void addRecipient(Patient patient) throws FullPatientException {
        if(recipients.size() == MAX_PATIENTS)
            throw new FullPatientException();
        recipients.add(patient);
        for(int i = 0; i < donors.size(); i++) {
            connections[recipients.size() - 1][i] =
                    BloodType.isCompatible(patient.getBloodType(), donors.get(i).getBloodType())
                            && donors.get(i).getOrgan().equals(patient.getOrgan());
            if(connections[recipients.size() - 1][i]) {
                patient.incrementConnections(1);
                donors.get(i).incrementConnections(1);
            }
        }
        if(patient.getID() == -1)
            patient.setID(recipients.size() - 1);
    }
    /**
     * Method to add a donor patient into the database
     * @param patient
     * The new patient to add
     * @throws FullPatientException
     * Thrown if the max amount of donors is reached
     */
    public void addDonor(Patient patient) throws FullPatientException {
        if(donors.size() == MAX_PATIENTS)
            throw new FullPatientException();
        donors.add(patient);
        for(int i = 0; i < recipients.size(); i++) {
            connections[i][donors.size() - 1] =
                    BloodType.isCompatible(recipients.get(i).getBloodType(), patient.getBloodType())
                            && patient.getOrgan().equals(recipients.get(i).getOrgan());
            if(connections[i][donors.size() - 1]) {
                recipients.get(i).incrementConnections(1);
                patient.incrementConnections(1);
            }
        }
        if(patient.getID() == -1)
            patient.setID(donors.size() - 1);
    }

    /**
     * Method to search the position of the patient with the given name
     * @param name
     * the name to search for
     * @return
     * returns the index of the patient within the list, returns -1 if it cannot find a patient
     */
    public int searchPosition(String name){
        int position = -1;
        int w = 0;
        for (Patient p : recipients) {
            if(p.getName().equals(name))  {
                position = w;
                break;
            }
            w++;
        }
        return position;
    }

    /**
     * Method to remove a recipient from the database
     * @param name
     * The name of the patient to remove
     * @throws PatientNotFoundException
     * Thrown if the patient name given does not exist within the database
     */
    public void removeRecipient(String name) throws PatientNotFoundException {
        int position = searchPosition(name);
        if(position == -1)
            throw new PatientNotFoundException();
        for(int i = 0; i < connections[position].length; i++)
            if(connections[position][i] && recipients.get(position).getOrgan().equals(donors.get(i).getOrgan()))
                donors.get(i).incrementConnections(-1);
        recipients.remove(position);
        for(int i = position; i < recipients.size() - 1; i++){
            connections[i] = connections[i + 1];
        }
        for(int i = 0; i < recipients.size(); i++)
            recipients.get(i).setID(i);
    }
    /**
     * Method to remove a donor from the database
     * @param name
     * The name of the patient to remove
     * @throws PatientNotFoundException
     * Thrown if the patient name given does not exist within the database
     */
    public void removeDonor(String name) throws PatientNotFoundException {
        int position = searchPosition(name);
        if(position == -1)
            throw new PatientNotFoundException();
        for(int i = 0; i < recipients.size(); i++) {
            if(connections[i][position] && recipients.get(i).getOrgan().equals(donors.get(position).getOrgan()))
                recipients.get(i).incrementConnections(-1);
            for(int j = position; j < donors.size() - 1; j++) {
                connections[i][j] = connections[i][j + 1];
            }
        }
        for(int i = 0; i < donors.size(); i++)
            donors.get(i).setID(i);
    }

    /**
     * Method to sort the donor or recipient list
     * @param isDonor
     * Indicates whether to sort the donor or recipient list
     * @param option
     * Indicates which comparator to use when sorting
     */
    public void sortList(boolean isDonor, String option){
        switch (option){
            case ("I"):
                if(isDonor) {
                    Collections.sort(donors);
                    printAllDonors();
                }
                else {
                    Collections.sort(recipients);
                    printAllRecipients();
                }
                break;
            case ("N"):
                if(isDonor) {
                    donors.sort(new NumConnectionsComparator());
                    printAllDonors();
                }
                else {
                    recipients.sort(new NumConnectionsComparator());
                    printAllRecipients();
                }
                break;
            case ("B"):
                if(isDonor) {
                    donors.sort(new BloodTypeComparator());
                    printAllDonors();
                } else {
                    recipients.sort(new BloodTypeComparator());
                    printAllRecipients();
                }
                break;
            case ("O"):
                if(isDonor) {
                    donors.sort(new OrganComparator());
                    printAllDonors();
                }
                else {
                    recipients.sort(new OrganComparator());
                    printAllRecipients();
                }
                break;
            case ("Q"):
                if(isDonor)
                    Collections.sort(donors);
                else
                    Collections.sort(recipients);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Method to print all recipients in the database
     */
    public void printAllRecipients(){
        String first = "Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================\n";
        for(Patient p : recipients){
            first += p.toString() + " ";
            for(int j = 0; j < donors.size(); j++) {
                if(connections[p.getID()][j])
                    first += j + ", ";
            }
            first += "\n";
        }
        System.out.println(first);
    }

    /**
     * Method to print all donors in the database
     */
    public void printAllDonors(){
        String first = "Index | Donor Name         | Age | Organ Donated | Blood Type | Recipient IDs\n" +
                "=============================================================================\n";
        for(Patient p : donors){
            first += p.toString() + " ";
            for(int j = 0; j < recipients.size(); j++) {
                if(connections[j][p.getID()])
                    first += j + ", ";
            }
            first += "\n";
        }
        System.out.println(first);
    }
}