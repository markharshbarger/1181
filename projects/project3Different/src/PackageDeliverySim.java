import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

public class PackageDeliverySim {
    private static double PERCENT_BY_DRONE;
    private static final int DISTANCE_TO_CROSSING = 3000;
    private static final int DISTANCE_FROM_CROSSING_TO_FINISH = 27000;
    private static final int TOTAL_TRIP_DISTANCE = DISTANCE_TO_CROSSING + DISTANCE_FROM_CROSSING_TO_FINISH;
    private static double singleDroneTime;
    private static double totalDroneTime;
    private static int numOfDrones;
    private static int numOfTrucks;
    private static double totalTruckTime;
    private static final int TOTAL_PACKAGES = 1500;
    private static final double DRONE_CAPACITY = 1;
    private static final int DRONE_SPEED = 500; // units per minute
    private static final int DRONE_TAKE_OFF_INTERVAL = 3; // minutes
    private static final double TRUCK_CAPACITY = 10;
    private static final int TRUCK_SPEED = 30; // units per minute
    private static final int TRUCK_DEPERATURE_INTERVAL = 15; // minutes

    private static boolean isTrainCrossing = false;
    private static PriorityQueue<Event> eventQueue = new PriorityQueue<>();
    private static Queue<Event> truckWaiting = new ArrayDeque<>();
    private static Map<Integer, Double> truckStartingTime = new TreeMap<>();
    private static Map<Integer, Double> truckTotalTripTime = new TreeMap<>();

    public static double conductSim(int percentDrone, String trainScheduleFileLocation) {
        if (percentDrone > 100 || percentDrone < 0) {
            System.out.println("Please enter a value under between (including) 100 and 0");
            return 0.0;
        }
        PERCENT_BY_DRONE = percentDrone / 100.0;
        numOfDrones = calculateDrone();
        numOfTrucks = calculateTruck();

        // remove
        System.out.println(numOfTrucks);
        System.out.println(numOfDrones);

        droneCalculation();

        addTruckLaunchToQueue();
        addTrainCrossingToQueue(trainScheduleFileLocation);

        while (!eventQueue.isEmpty()) {
            Event currEvent = eventQueue.poll();
            if (currEvent instanceof TruckStart) {
                System.out.println(currEvent);
                eventQueue.offer(new TruckAtCrossing(currEvent.getNum(), currEvent.getTime() + (DISTANCE_TO_CROSSING / TRUCK_SPEED)));
                continue;
            } else if (currEvent instanceof TruckAtCrossing) {
                if (isTrainCrossing || !truckWaiting.isEmpty()) {
                    System.out.println(currEvent);
                    truckWaiting.offer(currEvent);
                } else {
                    Event truck = new TruckCrossing(currEvent.getNum(), currEvent.getTime());
                    System.out.println(truck);
                    eventQueue.offer(truck);
                }
                continue;
            } else if (currEvent instanceof TruckCrossing) {
                System.out.println(currEvent);
                Event truck = new TruckEnd(currEvent.getNum(), (currEvent.getTime() + (DISTANCE_FROM_CROSSING_TO_FINISH / TRUCK_SPEED)));
                eventQueue.offer(truck);
                if (!(eventQueue.peek() instanceof TrainCrossing) && !truckWaiting.isEmpty()) {
                    Event truck2 = truckWaiting.poll();
                    Event newTruck = new TruckCrossing(truck2.getNum(), currEvent.getTime() + 1);
                    eventQueue.offer(newTruck);
                }
                continue;
            } else if (currEvent instanceof TruckEnd) {
                System.out.println(currEvent);
                truckTotalTripTime.put(currEvent.getNum(), currEvent.getTime() - truckStartingTime.get(currEvent.getNum()));
                if (eventQueue.isEmpty()) {
                    totalTruckTime = currEvent.getTime();
                }
                continue;
            } else if (currEvent instanceof TrainCrossing) {
                System.out.println(currEvent);
                isTrainCrossing = true;
                // has to be a trainFinishCrossing
            } else {
                System.out.println(currEvent);
                isTrainCrossing = false;

                if (!truckWaiting.isEmpty()) {
                    Event truck = truckWaiting.poll();
                    Event newTruck = new TruckCrossing(truck.getNum(), currEvent.getTime() + 1);
                    eventQueue.offer(newTruck);
                }
            }
        }

        System.out.print("\n\nSTATS\n-----\n");
        printStats();
        return Math.max(totalDroneTime, totalTruckTime);
    }

    private static void addTruckLaunchToQueue() {
        double currentTime = 0.0; 
        int truckNum = 0;
        for (int i = 0; i < numOfTrucks; ++i) {
            eventQueue.offer(new TruckStart(truckNum, currentTime));
            truckStartingTime.put(truckNum, currentTime);
            currentTime += TRUCK_DEPERATURE_INTERVAL;
            ++truckNum;
        }
    }

    private static int calculateTruck() {
        return (int)(Math.ceil((TOTAL_PACKAGES - numOfDrones) / TRUCK_CAPACITY));
    } 

    private static int calculateDrone() {
        return (int)(Math.ceil((TOTAL_PACKAGES * PERCENT_BY_DRONE)) / DRONE_CAPACITY);
    }

    private static void droneCalculation() {
        singleDroneTime = TOTAL_TRIP_DISTANCE / DRONE_SPEED;

        // subtract one becuase last drone doesn't need to wait for other drones
        totalDroneTime = ((numOfDrones - 1) * DRONE_TAKE_OFF_INTERVAL) + singleDroneTime;
        System.out.println(totalDroneTime);
    }

    private static void printStats() {
        double totalTimePerTruck = 0;
        for (Integer i : truckTotalTripTime.keySet()) {
            double truckTime = truckTotalTripTime.get(i);
            totalTimePerTruck += truckTime;
            System.out.println("TRUCK#" + i + " total trip time: " +  truckTime + " minutes");
        }
        double averageTruckTime = totalTimePerTruck / truckTotalTripTime.size();
        System.out.printf("\nTRUCK AVG TRIP TIME: %.1f minutes\n", averageTruckTime);
        System.out.printf("TRUCK TOTAL TIME: %.1f minutes\n", totalTruckTime);

        System.out.printf("\nDRONE TRIP TIME: %.1f minutes\n", singleDroneTime);
        System.out.printf("DRONE TOTAL TIME: %.1f minutes\n", totalDroneTime);

        System.out.printf("\nTOTAL TIME: %.1f minutes\n", Math.max(totalTruckTime, totalDroneTime));
    }

    private static void addTrainCrossingToQueue(String filename) {
        try {
            FileInputStream fileData = new FileInputStream(filename);
            Scanner fileInput = new Scanner(fileData);

            while (fileInput.hasNextLine()) {

                int departureTime = fileInput.nextInt();
                int tripTime = fileInput.nextInt();

                eventQueue.offer(new TrainCrossing(departureTime));
                eventQueue.offer(new TrainFinishedCrossing(departureTime + tripTime));
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
