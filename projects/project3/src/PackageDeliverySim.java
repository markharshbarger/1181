import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class PackageDeliverySim {
    private static double PERCENT_BY_DRONE;
    private static final int DISTANCE_TO_CROSSING = 3000;
    private static final int DISTANCE_FROM_CROSSING_TO_FINISH = 27000;
    private static final int TOTAL_TRIP_DISTANCE = DISTANCE_TO_CROSSING + DISTANCE_FROM_CROSSING_TO_FINISH;
    private static double singleDroneTime;
    private static double totalDroneTime;
    private static int numOfDrones;
    private static int numOfTrucks;
    private static final int totalPackages = 1500;
    private static boolean trainAtCrossing = false;
    private static PriorityQueue<Event> eventQueue = new PriorityQueue<>();
    private static Queue<Truck> truckWaiting = new ArrayDeque<>();

    public static void conductSim(double percentDrone, String trainScheduleFileLocation) {
        if (percentDrone > 1 || percentDrone < 0) {
            System.out.println("Please enter a value under 1");
            return;
        }
        PERCENT_BY_DRONE = percentDrone;
        numOfDrones = calculateDrone();
        numOfTrucks = calculateTruck();

        // remove
        System.out.println(numOfTrucks);
        System.out.println(numOfDrones);

        droneCalculation();

        addTruckLaunchToQueue();
        addTrainCrossingToQueue(trainScheduleFileLocation);

        while (!eventQueue.isEmpty()) {
            if (!truckWaiting.isEmpty()) {

            }
            Event currentEvent = eventQueue.poll();
            System.out.println(currentEvent);
            if (currentEvent instanceof Truck) {

            } else if (currentEvent instanceof Train) {
                trainAtCrossing = currentEvent.processEvent(trainAtCrossing, truckWaiting.isEmpty()); // parameters don't matter
                // if a train isn't at crossing then the train has crossed and doesn't need to be put into the eventQueue to processed
                if (trainAtCrossing) {
                    eventQueue.offer(currentEvent);
                }
            }
        }
    }

    private static void addTruckLaunchToQueue() {
        double currentTime = 0.0;
        int truckNum = 0;
        for (int i = 0; i < numOfTrucks; ++i) {
            eventQueue.offer(new Truck(truckNum++, currentTime, DISTANCE_TO_CROSSING, DISTANCE_FROM_CROSSING_TO_FINISH));
            currentTime += 15.0; // truck launch interval
        }
    }

    private static int calculateTruck() {
        return (int)(Math.ceil((totalPackages - numOfDrones) / 10.0));
    } 

    private static int calculateDrone() {
        return (int)(totalPackages * PERCENT_BY_DRONE);
    }

    private static void droneCalculation() {
        // in minutes for a single drone
        singleDroneTime = TOTAL_TRIP_DISTANCE / 500;

        // drone can be launched every 3 min
        totalDroneTime = ((numOfDrones - 1) * 3) + 60;
        System.out.println(totalDroneTime);
    }

    private static void addTrainCrossingToQueue(String filename) {
        try {
            FileInputStream fileData = new FileInputStream(filename);
            Scanner fileInput = new Scanner(fileData);

            while (fileInput.hasNextLine()) {

                int departureTime = fileInput.nextInt();
                int tripTime = fileInput.nextInt();

                eventQueue.offer(new Train(departureTime, tripTime));
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
    }
}
