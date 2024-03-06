import java.util.Comparator;

/**
 * A custom comparator class to compare Athlete
 */
public class ComparatorClass implements Comparator<Athlete> {
    /**
     * Compares two Athlete, first compares by sport and then by name
     * 
     * @param a the first Athlete being compared
     * @param b the second Athlete being compared
     * @return an int based on the Athlete being compared
     */
    @Override
    public int compare(Athlete a, Athlete b) {
        int compareSport = a.getSport().compareTo(b.getSport());
        if (compareSport != 0) {
            return compareSport;
        } else {
            return a.getName().compareTo(b.getName());
        }
    }
}
