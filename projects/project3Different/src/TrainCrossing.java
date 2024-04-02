/**
 * Represents the start of a train crossing over the railroad and road junction
 */
public class TrainCrossing extends Event {
    /**
     * TrainCrossing constructor 
     * 
     * @param time double - represents the time of the event
     */
    public TrainCrossing(double time) {
        super(time);
    }

    /**
     * Returns a string representation of the object
     * 
     * @return String of the event time with that it is a train arriving at the crossing
     */
    @Override
    public String toString() {
        return super.getTime() + ": TRAIN arrives at crossing";
    }
}
