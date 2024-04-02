/**
 * Represents a train finishing crossing over the railroad and road junction
 */
public class TrainFinishedCrossing extends Event {
    /**
     * TrainFinishedCrossing constructor 
     * 
     * @param time double - represents the time of the event
     */
    public TrainFinishedCrossing(double time) {
        super(time);
    }

    /**
     * Returns a string representation of the object
     * 
     * @return String of the event time with that it is a train leaving the crossing
     */
    @Override
    public String toString() {
        return super.getTime() + ": TRAIN leaves crossing";
    }
}
