import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        // enter the file location of the .txt file
        String fileLocation = "/home/goldeye/1181/projects/project1/textfiles/more_Items.txt"; // answer is ~7600

        ArrayList<Chromosome> currentGen = GeneticAlgorithm.initializePopulation(GeneticAlgorithm.readData(fileLocation), 10);
        ArrayList<Chromosome> nextGen = new ArrayList<>();

        // set cycles ran. 20 works for smaller arraylist, but the bigger arraylist takes at least ~2000 for a better reading, I use 10000
        // and it has given me the correct answer every time I've ran it and doesn't take long to run
        int amountOfGen = 10000;

        for (int i = 0; i < amountOfGen; ++i) {
            for (Chromosome current : currentGen) {
                nextGen.add(current);
            }

            // shuffle for random pairing of parents
            Collections.shuffle(nextGen);
            int currentGenSize = nextGen.size();
            for (int j = 0; j < currentGenSize - 1; j += 2) {
                Chromosome child = nextGen.get(j).crossover(nextGen.get(j + 1));
                nextGen.add(child);
            }

            // Random 10% get mutated
            int numOfMut = (int)(currentGen.size() * 0.1);
            Collections.shuffle(nextGen);
            for (int b = 0; b < numOfMut; ++b) {
                nextGen.get(b).mutate();
            }

            Collections.sort(nextGen);
            currentGen.clear();
            
            // transfer next gen to current gen
            for (int l = 0; l < 10; ++l) {
                currentGen.add(nextGen.get(l));
            }
            nextGen.clear();
        }

        Collections.sort(currentGen);
        System.out.println(currentGen.get(0));
    }
}