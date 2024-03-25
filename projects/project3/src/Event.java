public class Event implements Comparable<Event> {
    private double time;

    public Event(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Process an event and returns true if the event is still occuring, false if event is complelty done. Since Event is just
     * a single action it always returns true
     * 
     * @param trainAtCrossing boolean - true if there is a Train crossing false otherwise
     * @param truckInQueue boolean - true if there are trucks in waiting queue, false otherwise
     * @return true if there is another event to be processed
     */
    public boolean processEvent(boolean trainAtCrossing, boolean truckInQueue) {
        return true;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        } else if (this.time > other.time) {
            return 1;
        } else {
            // if both have same time, have train come first
            if ((this instanceof Train) && (other instanceof Truck)) {
                return -1;
            } else if ((other instanceof Train) && (this instanceof Truck)) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
