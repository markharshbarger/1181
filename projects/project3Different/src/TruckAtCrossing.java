/**
 * Represents a truck that has arrived at the road and railroad junction
 */
public class TruckAtCrossing extends Event {
    private int truckNum;

    /**
     * Constructor of TruckAtCrossing
     * 
     * @param truckNum int - the number of the truck that is at the current event 
     * @param time double - the time of the current event
     */
    public TruckAtCrossing(int truckNum, double time) {
        super(time);
        this.truckNum = truckNum;
    }

    /**
     * Gets the number of the truck associated with the event
     * 
     * @return int of the truck number
     */
    @Override
    public int getNum() {
        return truckNum;
    }

    /**
     * A String representation of the object
     * 
     * @return the current time with the truck and number and that the truck is waiting at the crossing
     */
    @Override
    public String toString() {
        return super.getTime() + ": TRUCK#" + truckNum + " waits at crossing";
    }
}