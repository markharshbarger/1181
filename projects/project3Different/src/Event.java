/**
 * Event class represents an object that contains a time (double) that can be compared to other Events.
 * Also contains an event number (int eventNum) that is mainly used for classes extending this class
 */
public class Event implements Comparable<Event> {
    private double time;
    
    /**
     * Constructor for Event class
     * 
     * @param time double - sets the time of the event
     */
    public Event(double time) {
        this.time = time;
    }

    /**
     * Get Event time
     * 
     * @return double - represents the Event time
     */
    public double getTime() {
        return time;
    }

    /**
     * Set Event time
     * 
     * @param time double - represents the Event time
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Get's the Event Number for event, default value is -1. It is meant to be overriden for classes that extend Event class.
     * It's purpose is to help track the events.
     * 
     * @return int - Event Number for Event
     */
    public int getNum() {
        return -1;
    }

    /**
     * Compare to method that compares this Event to other Event's mainly based on their time values. But if time values are
     * equal it sorts based on the instanceof the object; train events are before truck events and TruckAtCrossing events are
     * before other truck events
     * 
     * @param other Event - represents the other Event being compared
     * @return -1 if this Event's time is earlier than other Event's time, 1 if other Event's time is less than
     * this Event's time. -1 if this instanceof a train event and 1 if other instanceof a train event. -1 if this instance of
     * TruckAtCrossing and 1 if other instanceof TruckAtCrossing
     * 
     * @throws NullPointerException - when trying to compare a null object with any other object
     * @throws ClassCastException - when trying to compare objects of two different data types
     */
    @Override
    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        } else if (this.time > other.time) {
            return 1;

        } else {  // if time is equal
            if ((this instanceof TrainCrossing) || (this instanceof TrainFinishedCrossing)) {
                return -1;
            } else if ((other instanceof TrainCrossing) || (other instanceof TrainFinishedCrossing)) {
                return 1;
            } else if (this instanceof TruckAtCrossing){
                return -1;
            } else if (other instanceof TruckAtCrossing) {
                return 1;
            }
        }
        return 0;
    }
}
