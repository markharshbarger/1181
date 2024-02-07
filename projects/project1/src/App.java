import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        // String fileLocation = "/home/goldeye/1181/projects/project1/textfiles/Items.txt";
        String fileLocation = "/home/goldeye/1181/projects/project1/textfiles/more_Items.txt"; // answer is ~7600
        ArrayList<Chromosome> currentGen = GeneticAlgorithm.initializePopulation(GeneticAlgorithm.readData(fileLocation), 10);

        // for (Chromosome item : currentGen) {
        //     System.out.println(item);
        // }
        // System.out.println("\n\n\n\n\n");
        // Collections.sort(currentGen);
        // for (Chromosome item : currentGen) {
        //     System.out.println(item);
        // }

        int amountOfGen = 3000;
        for (int i = 0; i < amountOfGen; ++i) {
            ArrayList<Chromosome> nextGen = new ArrayList<>();
            for (Chromosome current : currentGen) {
                nextGen.add(new Chromosome(current));
            }

            // shuffle for random pairing of parents
            Collections.shuffle(nextGen);
            for (int j = 0; j < nextGen.size() - 1; j += 2) {
                Chromosome child = nextGen.get(j).crossover(nextGen.get(j + 1));
                nextGen.add(child);
            }

            // 10% get mutated
            int numOfMut = (int)(currentGen.get(0).size() * 0.1);
            Collections.shuffle(nextGen);

            for (int b = 0; b < numOfMut; ++b) {
                nextGen.get(b).mutate();
            }

            Collections.sort(nextGen);
            currentGen.clear();
            
            for (int l = 0; l < 10; ++l) {
                currentGen.add(nextGen.get(l));
            }
            nextGen.clear();
        }

        Collections.sort(currentGen);
        
        System.out.println(currentGen.get(0));
    }
}
