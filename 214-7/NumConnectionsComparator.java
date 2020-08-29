import java.util.Comparator;
/**
 * This class represents a comparator between two patients' number of connections
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class NumConnectionsComparator implements Comparator<Patient> {
    public int compare(Patient o1, Patient o2) {
        return Integer.compare(o1.getConnections(), o2.getConnections());
    }
}