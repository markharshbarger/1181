/**
 * Class represents an Athlete that has a name, sport, and a ranking, includes a toString and a compareTo method
 */
public class Athlete<E extends Comparable<E>> implements Comparable<Athlete<E>> {
    private String name;
    private String sport;
    private E ranking;

    /**
     * Constructor of Athlete class, sets all variables
     * 
     * @param name String - represents the name of the Athlete
     * @param sport String - represents the sport name of the Athlete
     * @param ranking E - represents the ranking of Athlete, can be any data type that is Comparable
     */
    public Athlete(String name, String sport, E ranking) {
        this.name = name;
        this.sport = sport;
        this.ranking = ranking;
    }

    /**
     * Compares this Athlete to an other Athlete, first based on sport and then by rank
     * @param other - other Athlete being compared
     * @return int - based on the comparasion
     */
    @Override
    public int compareTo(Athlete<E> other) {
        int compareSport = this.sport.compareTo(other.sport);
        if (compareSport != 0) {
            return compareSport;
        } else {
            return this.ranking.compareTo(other.ranking);
        }
    }

    /**
     * Makes a String of Athlete's data: name, sport, ranking.
     * 
     * @return name, sport, and ranking in a nice format (String)
     */
    @Override
    public String toString() {
        return name + " (" + sport + " - " + ranking + ")";
    }

    /**
     * Gets Athlete's name
     * 
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * Gets Athlete's sport
     * 
     * @return sport (String)
     */
    public String getSport() {
        return sport;
    }

    /**
     * Gets Athlete's ranking
     * 
     * @return ranking (E)
     */
    public E getRanking() {
        return ranking;
    }
}
