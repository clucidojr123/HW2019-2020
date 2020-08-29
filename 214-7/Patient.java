import java.io.Serializable;
/**
 * This class represents an active organ donor or recipient within a database
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class Patient implements Comparable, Serializable {
    private String name;
    private String organ;
    private int age;
    private BloodType bloodType;
    private int ID;
    private boolean isDonor;
    private int connections;

    /**
     * Constructor for the Patient class
     */
    public Patient(String n, String o, int a, BloodType b, int ID, boolean i, int c){
        this.name = n;
        this.organ = o;
        this.age = a;
        this.bloodType = b;
        this.ID = ID;
        this.isDonor = i;
        this.connections = c;
    }

    /**
     * Comparator between this Patient and another Patient based on ID
     * @param o
     * The Patient to compare
     * @return
     * returns an integer indicating the result of comparison
     */
    public int compareTo(Object o){
        Patient that = (Patient) o;
        return Integer.compare(this.ID, that.ID);
    }

    /**
     * Getter for the Patient's organ
     * @return
     * Returns the Patient's organ as a String
     */
    public String getOrgan(){
        return organ;
    }
    /**
     * Getter for the Patient's number of connections
     * @return
     * Returns the Patient's number of connections
     */
    public int getConnections() {
        return connections;
    }
    /**
     * Setter for the Patient's number of connections
     * @param connections
     * The number to change connections by
     */
    public void incrementConnections(int connections) {
        this.connections += connections ;
    }

    /**
     * Getter for the patient's name
     * @return
     * returns the patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the patient's BloodType
     * @return
     * returns the patient's BloodType
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Getter for the patient's ID
     * @return
     * returns the patient's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Setter for the patient's ID
     * @param ID
     * the new ID of the patient
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Method to convert the Patient to a string representation
     * @return
     * returns a textual representation of the patient
     */
    public String toString(){
        return String.format("%-8s%-19s%-8s%-14s%-13s|",
                "   " + ID + "  |", name, "| " + age + "  |", organ, "| " + bloodType.getBloodType());
    }
}