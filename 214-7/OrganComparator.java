import java.util.Comparator;
/**
 * This class represents a comparator between two patients' organ of need or donation
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class OrganComparator implements Comparator<Patient> {
    public int compare(Patient o1, Patient o2) {
        return o1.getOrgan().compareTo(o2.getOrgan());
    }
}