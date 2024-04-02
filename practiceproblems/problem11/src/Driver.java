import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 0;
        int n = 0;
        
        // get the command line parameters
        Scanner userInput = new Scanner(System.in);
        System.out.print("Number of threads to use: ");
        numberOfThreads = userInput.nextInt();
        System.out.print("\nValue of n: ");
        userInput.nextLine();
        n = userInput.nextInt();
        userInput.close();

        long startTime = System.currentTimeMillis();
        int dividedWork = n / numberOfThreads;
        List<PrimeThread> threadList = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; ++i) {
            PrimeThread currentThread = new PrimeThread(i * dividedWork, (i * dividedWork) + dividedWork);
            threadList.add(currentThread);
        }

        for (PrimeThread t : threadList) {
            t.start();
        }

        for (PrimeThread t : threadList) {
            t.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("System took " + (endTime - startTime) + " ms to complete program");
        int answer = 0;
        for (PrimeThread t : threadList) {
            answer += t.getResult();
        }
        System.out.println("Answer: " + answer);
    }

}
