import java.util.Comparator;
/**
 * This class represents comparator for two patients' BloodTypes
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class BloodTypeComparator implements Comparator<Patient> {
    public int compare(Patient o1, Patient o2) {
        if(o1.getBloodType().getBloodType().length() > o2.getBloodType().getBloodType().length())
            return -2;
        else
            return o1.getBloodType().getBloodType().compareTo(o2.getBloodType().getBloodType());
    }
}