import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Represents a simualtion for deliverying packages with trucks and drones. The trucks have a railroad inbetween their path that
 * occasionally is blocked by a train.
 */
public class PackageDeliverySim {
    private static final int DISTANCE_TO_CROSSING = 3000; // units
    private static final int DISTANCE_FROM_CROSSING_TO_FINISH = 27000; // units
    private static final int TOTAL_TRIP_DISTANCE = DISTANCE_TO_CROSSING + DISTANCE_FROM_CROSSING_TO_FINISH;
    private static double PERCENT_BY_DRONE;
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
    private static Map<Integer, Double> truckTime = new TreeMap<>();

    /**
     * Carry's out a package delivery simulation. In the simulation there are trucks and drones that can deliver packages to
     * their final destination. There is a railroad that has a train periodically blocking the path for trucks. The simulation
     * simulates the trip times for both the drones and trucks. The simulation will print out statistics to the console. There
     * are beginning statistics and ending statistics. It also prints out the train schedule and will print out the events as
     * those events are performed
     * 
     * @param percentDrone double - the percent of packages delivered by drones. Has to be between 1 and 0 (exclusive).
     * @param trainScheduleFileLocation String - the file location of the train schedule to be used for the simulation
     * @return double - how many minutes it will take to deliver all the packages. 
     */
    public static double conductSim(double percentDrone, String trainScheduleFileLocation) {
        if (percentDrone >= 1 || percentDrone <= 0) {
            System.out.println("Please enter a value between 1 and 0 (exclusive)");
            return -1;
        }
        
        PERCENT_BY_DRONE = percentDrone;
        calculateDrone();
        calculateTruck();

        printBeginningStats();
        addTrainCrossingToQueue(trainScheduleFileLocation); // also prints out train schedule

        droneCalculation();

        addTruckLaunchToQueue();

        while (!eventQueue.isEmpty()) {
            Event currEvent = eventQueue.poll();
            System.out.println(currEvent);

            if (currEvent instanceof TruckStart) {
                eventQueue.offer(new TruckAtCrossing(currEvent.getNum(), currEvent.getTime() + (DISTANCE_TO_CROSSING / TRUCK_SPEED)));
                continue;

            } else if (currEvent instanceof TruckAtCrossing) {
                if (isTrainCrossing || !truckWaiting.isEmpty()) {
                    truckWaiting.offer(currEvent);
                } else {
                    Event truck = new TruckCrossing(currEvent.getNum(), currEvent.getTime());
                    eventQueue.offer(truck);
                }
                continue;

            } else if (currEvent instanceof TruckCrossing) {
                Event truck = new TruckEnd(currEvent.getNum(), (currEvent.getTime() + (DISTANCE_FROM_CROSSING_TO_FINISH / TRUCK_SPEED)));
                eventQueue.offer(truck);
                if (!(eventQueue.peek() instanceof TrainCrossing) && !truckWaiting.isEmpty()) {
                    Event truck2 = truckWaiting.poll();
                    Event newTruck = new TruckCrossing(truck2.getNum(), currEvent.getTime() + 1);
                    eventQueue.offer(newTruck);
                }
                continue;

            } else if (currEvent instanceof TruckEnd) {
                truckTime.put(currEvent.getNum(), currEvent.getTime() - truckTime.get(currEvent.getNum()));
                if (eventQueue.isEmpty()) {
                    totalTruckTime = currEvent.getTime();
                }
                continue;

            } else if (currEvent instanceof TrainCrossing) {
                isTrainCrossing = true;
                continue;
                
            } else { // has to be a trainFinishCrossing
                isTrainCrossing = false;

                if (!truckWaiting.isEmpty()) {
                    Event truck = truckWaiting.poll();
                    Event newTruck = new TruckCrossing(truck.getNum(), currEvent.getTime() + 1);
                    eventQueue.offer(newTruck);
                }
                continue;
            }
        }

        System.out.print("\n\nSTATS\n-----\n");
        printStats();
        return Math.max(totalDroneTime, totalTruckTime);
    }

    /**
     * Add's all the TruckStart events to the event queue. Intervals their deperature by TRUCK_DEPERATURE_INTERVAL
     */
    private static void addTruckLaunchToQueue() {
        double currentTime = 0.0; 
        int truckNum = 0;
        for (int i = 0; i < numOfTrucks; ++i) {
            eventQueue.offer(new TruckStart(truckNum, currentTime));
            truckTime.put(truckNum, currentTime);
            currentTime += TRUCK_DEPERATURE_INTERVAL;
            ++truckNum;
        }
    }

    /**
     * Calculate the amount of trucks need to deliver the packages. Number of trucks is effected the PERCENT_BY_DRONE
     */
    private static void calculateTruck() {
        numOfTrucks = (int)(Math.ceil((TOTAL_PACKAGES - numOfDrones) / TRUCK_CAPACITY));
    } 

    /**
     * Calculate the amount of drone's needed to deliver the packages. Number of drones is based on the PERCENT_BY_DRONE
     */
    private static void calculateDrone() {
        numOfDrones = (int)(Math.ceil((TOTAL_PACKAGES * PERCENT_BY_DRONE)) / DRONE_CAPACITY);
    }

    /**
     * Calculate the single and total Drone times
     */
    private static void droneCalculation() {
        singleDroneTime = TOTAL_TRIP_DISTANCE / DRONE_SPEED;

        // subtract one becuase last drone doesn't need to wait for other drones
        totalDroneTime = ((numOfDrones - 1) * DRONE_TAKE_OFF_INTERVAL) + singleDroneTime;
    }

    /**
     * Prints to console the beginning statistics of the simulation. These include the percent drone and number of packages,
     * and the number of drones and trucks.
     */
    private static void printBeginningStats() {
        System.out.printf("With %.1f%% drones and %d packages,\n", PERCENT_BY_DRONE * 100, TOTAL_PACKAGES);
        System.out.println("There will be:");
        System.out.println("-" + numOfDrones + " drones");
        System.out.println("-" + numOfTrucks + " trucks\n");
    }

    /**
     * Prints to console the ending statistics of the simulation. These include the individual truck times,
     * individual drone time, total truck time, total drone time, truck average trip time, and the total time.
     */
    private static void printStats() {
        double totalTimePerTruck = 0;
        for (Integer i : truckTime.keySet()) { // TreeMap has order
            double individualTruckTime = truckTime.get(i);
            totalTimePerTruck += individualTruckTime;
            System.out.println("TRUCK#" + i + " total trip time: " +  individualTruckTime + " minutes");
        }
        double averageTruckTime = totalTimePerTruck / truckTime.size();
        System.out.printf("\nTRUCK AVG TRIP TIME: %.1f minutes\n", averageTruckTime);
        System.out.printf("TRUCK TOTAL TIME: %.1f minutes\n", totalTruckTime);

        System.out.printf("\nDRONE TRIP TIME: %.1f minutes\n", singleDroneTime);
        System.out.printf("DRONE TOTAL TIME: %.1f minutes\n", totalDroneTime);

        System.out.printf("\nTOTAL TIME: %.1f minutes\n", Math.max(totalTruckTime, totalDroneTime));
    }

    /**
     * Adds train events from a .txt file to the eventQueue. File should contains deperature time with a space and trip time/
     * trip length and a newline for each one. Also prints to console the train schedule
     * Example of .txt format (\n represents a new line after):
     * 91 35\n
     * 
     * @param filename - the file location of the .txt file that contains the train's schedule
     */
    private static void addTrainCrossingToQueue(String filename) {
        System.out.println("TRAIN SCHEDULE");
        System.out.println("---------------");
        try {
            FileInputStream fileData = new FileInputStream(filename);
            Scanner fileInput = new Scanner(fileData);

            while (fileInput.hasNextLine()) {

                int departureTime = fileInput.nextInt();
                int tripTime = fileInput.nextInt();

                System.out.println(departureTime + "-" + (departureTime + tripTime));

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
        System.out.print("\n");
    }
}
