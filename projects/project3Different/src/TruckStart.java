/**
 * Represents a truck that has started it's journey
 */
public class TruckStart extends Event {
    private int truckNum;

    /**
     * Constructor of TruckStart
     * 
     * @param truckNum int - the number of the truck that is at the current event 
     * @param time double - the time of the current event
     */
    public TruckStart(int truckNum, double time) {
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
     * @return the current time with the truck and number and that the truck is beginning it's journey
     */
    @Override
    public String toString() {
        return super.getTime() + ": TRUCK#" + truckNum + " begins journey";
    }
}
