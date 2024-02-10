import java.util.ArrayList;
import java.util.Random;

/**
 * Chromosome class represents an ArrayList of Items that included methods to compare, crossover, and mutate it with other Chromosomes
 */
public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {
    private static Random rng;
    
    /**
     * Empty constructor sets no fields
     */
    public Chromosome() {
    }

    /**
     * Constructor for Chromosome
     * 
     * @param items - ArrayList of Item that sets the items to be in the Chromosome object
     */
    public Chromosome(ArrayList<Item> items) {
        super(items);

        // takes the size of the arraylist into account (bigger lists means less percent of list you will be able to carry)
        // based equation off of the smaller list (smaller list still has 50% probabiltiy)
        int upperLimit = (int)((3.5 / this.size()) * 100);

        rng = new Random();
        for (Item i : this) {
            Item newItem = new Item(i);
            
            int randNum = rng.nextInt(100); // 0 - 99
            if (randNum < upperLimit) {
                newItem.setIncluded(true);
            } else {
                newItem.setIncluded(false);
            }
            this.set(this.indexOf(i), newItem);
        }
    }

    /**
     * Performs a 'crossover' of two Chromosome objects, both Chromosomes should containe the same ordered ArrayList. What differs
     * is the if the item is included or not. It's a 50% to 50% probability of which 'parent' the 'child' will have its included value
     * based on, for each item in the ArrayList.
     * 
     * @param other - Chromosome object that 50% of its included value will be in the 'child' or resulting Chromosome object
     * @return Chromosome - 'child' of the two 'parents'. Is a deep copy that contains 50% of each parents values.
     */
    public Chromosome crossover(Chromosome other) {
        Chromosome child = new Chromosome();

        rng = new Random();
        for (int i = 0; i < this.size(); ++i) { // this and other should have the same size arraylist in same order
            Item newItem = new Item(this.get(i));

            int randNum = rng.nextInt(10); // 0 - 9
            if (randNum < 5) {
                newItem.setIncluded(this.get(i).isIncluded());
            } else {
                newItem.setIncluded(other.get(i).isIncluded());
            }
            child.add(newItem);
        }
        return child;
    }
    
    /**
     * Mutates the items in the object's ArrayList. Each item has a 10% chance that it will be
     * mutated (invert the 'included' boolean value)
     */
    public void mutate() {
        rng = new Random();

        for (Item i : this) {
            int randNum = rng.nextInt(10);

            if (randNum != 1) {
                continue;
            }
            
            if (i.isIncluded() == true) {
                i.setIncluded(false);
            } else {
                i.setIncluded(true);
            }
        }
    }

    /**
     * Calculates the fitness of the object by adding the sum of all the included items values, unless the sum of the items weights
     * is over 10.
     * 
     * @return int - sum of included items values, unless the sum of the items weights is over 10 which returns 0
     */
    public int getFitness() {
        Double totalWeight = 0.0;
        int totalValue = 0;
        for (Item i : this) {
            if (i.isIncluded() == true) {
                totalWeight += i.getWeight();
                if (totalWeight > 10.0)
                    return 0;
        
                totalValue += i.getValue();
                }
        }
        return totalValue;
        }

    /**
     * Compares two chromosome object based on the fitness of each Chromosome
     * @param other Chromosome - the other object being compared
     * @return int - negative int if this object is greater than other object, 0 if objects equal, positive if this is less than other
     */
    @Override
    public int compareTo(Chromosome other) {
        return other.getFitness() - this.getFitness();
    }

    /**
     * Returns the String represention of the object
     * @return String - outputs all the items included in the Chromosome along with the total fitness of the object at the end
     */
    @Override
    public String toString() {
        String finalString = "";
        for (Item i : this) {
            if (i.isIncluded() == true) {
                finalString += i.toString() + "\n";
            }
        }
        return finalString + "Fitness: " + getFitness() + "\n";
    }
}
