import java.io.Serializable;
/**
 * This class represents a blood type of a patient.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class BloodType implements Serializable {
    private String bloodType;
    private int bloodNum;
    public static final boolean[][] BLOOD_MATRIX = {{true, false, false, false,}, {true, true, false, false},
            {true, false, true, false}, {true, true, true, true}};

    /**
     * Constructor for BloodType class
     */
    public BloodType(String b){
        bloodType = b;
        bloodNum = b.equals("O") ? 0 : b.equals("A") ? 1 : b.equals("B") ? 2 : 3;
    }

    /**
     * Compares two BloodTypes to see if they are compatible
     * @param recipient
     * BloodType of the recipient
     * @param donor
     * BloodType of the donor
     * @return
     * returns whether or not the BloodTypes are compatible
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor){
        return BLOOD_MATRIX[recipient.bloodNum][donor.bloodNum];
    }

    /**
     * Getter for the BloodType
     * @return
     * Return the BloodType as a String
     */
    public String getBloodType() {
        return bloodType;
    }
}