public class Truck extends Event{
    private int number;
    private final int DISTANCE_TO_CROSSING;
    private final int DISTANCE_FROM_CROSSING_TO_FINISH;
    private final int truckSpeed = 30; // units per minute
    private final double startTime; // use to find the time it took for truck to finish
    private TruckEvent truckEvent;


    public Truck(int number, double time, int DISTANCE_TO_CROSSING, int DISTANCE_FROM_CROSSING_TO_FINISH) {
        super(time);
        this.number = number;
        startTime = time;
        truckEvent = TruckEvent.TRUCK_START;
        this.DISTANCE_TO_CROSSING = DISTANCE_TO_CROSSING;
        this.DISTANCE_FROM_CROSSING_TO_FINISH = DISTANCE_FROM_CROSSING_TO_FINISH;
    }

    public double getStartTime() {
        return startTime;
    }

    public TruckEvent getEvent() {
        return truckEvent;
    }

    public void setTime(double time) {
        super.setTime(time);
    }

    public double getTime() {
        return super.getTime();
    }

    @Override
    public String toString() {
        return this.getTime() + ": TRUCK#" + number + " " + truckEvent;
    }

    @Override
    public boolean processEvent(boolean trainCrossing, boolean truckInQueue) {
        switch (truckEvent) {
            case TRUCK_START:
                super.setTime((DISTANCE_TO_CROSSING / truckSpeed) + super.getTime());
                if (trainCrossing || truckInQueue) {
                    truckEvent = TruckEvent.TRUCK_WAITS_AT_CROSSING;
                } else {
                    truckEvent = TruckEvent.TRUCK_CROSS;
                }
                break;

            case TRUCK_WAITS_AT_CROSSING:
                if (!(trainCrossing && truckInQueue)) {
                    super.setTime((DISTANCE_FROM_CROSSING_TO_FINISH / truckSpeed) + super.getTime());
                    truckEvent = TruckEvent.TRUCK_CROSS;
                } else if ()
            case TRUCK_END
            default:
                break;
        }
        return true;
    }
}
