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

    public int getNum() {
        return 0;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        } else if (this.time > other.time) {
            return 1;

            // if time is equal
        } else {
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
