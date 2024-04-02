/**
 * Represents a truck that has completed it's journey
 */
public class TruckEnd extends Event {
    private int truckNum;

    /**
     * Constructor of TruckEnd
     * 
     * @param truckNum int - the number of the truck that is at the current event 
     * @param time double - the time of the current event
     */
    public TruckEnd(int truckNum, double time) {
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
     * @return the current time with the truck and number and that the truck has completed its journey
     */
    @Override
    public String toString() {
        return super.getTime() + ": TRUCK#" + truckNum + " completes journey";
    }
}
