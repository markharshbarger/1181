import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 0;
        int n = 0;
        if (args.length == 2) {
            numberOfThreads = Integer.valueOf(args[0]);
            n = Integer.valueOf(args[1]);
        } else { // if command line doesn't have two args then don;t return anything
            System.out.println("Please specify threadcount and value of n in command line");
            return;
        }

        long startTime = System.currentTimeMillis(); // start clock
        int dividedWork = n / numberOfThreads;
        List<PrimeThread> threadList = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; ++i) {
            int start = i * dividedWork;
            PrimeThread currentThread = new PrimeThread(start, (start + dividedWork));
            threadList.add(currentThread);
        }

        for (PrimeThread t : threadList) {
            t.start();
        }

        for (PrimeThread t : threadList) {
            t.join();
        }

        long endTime = System.currentTimeMillis(); // end clock
        System.out.println("System took " + (endTime - startTime) + " ms to complete program");
        int answer = 0;
        for (PrimeThread t : threadList) {
            answer += t.getResult();
        }
        System.out.println("Answer: " + answer);
    }

}
