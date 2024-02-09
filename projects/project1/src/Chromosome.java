import java.util.ArrayList;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {
    private static Random rng;
    
    /**
     * Empty constructor
     */
    public Chromosome() {
    }

    public Chromosome(ArrayList<Item> items) {
        super(items);

        rng = new Random();
        for (Item i : this) {
            Item newItem = new Item(i);
            
            int randNum = rng.nextInt(10); // 0 - 9
            if (randNum < 5) {
                newItem.setIncluded(true);
            } else {
                newItem.setIncluded(false);
            }
            this.set(this.indexOf(i), newItem);
        }
    }

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

    public int getFitness() {
        // Double totalWeight = 0.0;
        // int totalValue = 0;
        // for (Item i : this) {
        //     if (i.isIncluded() == true) {
        //         totalWeight += i.getWeight();
        //         if (totalWeight > 10.0)
        //             return 0;
        
        //         totalValue += i.getValue();
        //         }
        // }
        // return totalValue;

        Double totalWeight = 0.0;
        int totalValue = 0;
        boolean wentOver = false;
        for (Item i : this) {
            if (i.isIncluded() == true) {
                totalWeight += i.getWeight();
                if (totalWeight > 10.0) {
                    if (wentOver == false) {
                        wentOver = true;
                        totalValue = 0 - i.getValue();
                        continue;
                    }
                    totalValue -= i.getValue();
                }
                if (wentOver == false) {
                    totalValue += i.getValue();
                }
            }
        }
        return totalValue;
    }

    public int compareTo(Chromosome other) {
        return other.getFitness() - this.getFitness();
    }

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
