import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GeneticAlgorithm {
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException {
        ArrayList<Item> list = new ArrayList<>();
        
        try {
            FileInputStream fileData = new FileInputStream(filename);
            Scanner fileInput = new Scanner(fileData);

            while (fileInput.hasNextLine()) {
                String line = fileInput.nextLine();

                String name = line.substring(0, line.indexOf(","));
                line = line.substring(line.indexOf(",") + 1, line.length());

                Double weight = Double.parseDouble(line.substring(0, line.indexOf(",")).trim());
                line = line.substring(line.indexOf(",") + 1, line.length());

                int value = Integer.parseInt(line.trim());

                list.add(new Item(name, weight, value));
            }

            fileInput.close();
            fileData.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found; make sure " + filename + " is named correctly and in the correct directory!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        return list;
    }

    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {
        ArrayList<Chromosome> initialPop = new ArrayList<>();

        for (int i = 0; i < populationSize; ++i) {
            initialPop.add(new Chromosome(items));
        }

        return initialPop;
    }
}
