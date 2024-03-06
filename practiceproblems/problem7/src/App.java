import java.util.ArrayList;
import java.util.Collections;

// Name: Mark Harshbarger
// WSU email: harshbarger.26@wright.ed
public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<Athlete<Integer>> athleteList = new ArrayList<>();
        athleteList.add(new Athlete<Integer>("John Doe", "baseball", 3));
        athleteList.add(new Athlete<Integer>("Sam Johnson", "footbal", 2));
        athleteList.add(new Athlete<Integer>("Keven Smith", "baseball", 1));
        athleteList.add(new Athlete<Integer>("Sally Johnson", "swimming", 3));
        athleteList.add(new Athlete<Integer>("James Smith", "swimming", 4));


        System.out.println("Unsorted:");
        for (Athlete<Integer> athlete : athleteList) {
            System.out.println(athlete);
        }
        System.out.println();

        System.out.println("Sorted by sport and then name:");
        Collections.sort(athleteList, new ComparatorClass());
        for (Athlete<Integer> athlete : athleteList) {
            System.out.println(athlete);
        }
        System.out.println();

        System.out.println("Sorted by sport and then ranking:");
        Collections.sort(athleteList);
        Collections.sort(athleteList);
        for (Athlete<Integer> athlete : athleteList) {
            System.out.println(athlete);
        }
    }
}
