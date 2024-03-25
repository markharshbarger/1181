public class Train extends Event {
    private TrainEvent trainEvent;
    private int duration;

    /**
     * Constructor for Train, sets the time and includes the duration the train will be crossing
     * Sets the TrainEvent to crossing
     * 
     * @param time - the time that the train begins to cross
     */
    public Train(int time, int duration) {
        super(time);
        this.duration = duration;
        trainEvent = TrainEvent.TRAIN_CROSSING;
    }

    public TrainEvent getTrainEvent() {
        return trainEvent;
    }

    @Override
    public String toString() {
        return super.getTime() + ": " + trainEvent;
    }

    /**
     * Process a train event, if the train event has more events to process it returns true, false otherwise. Changes the
     * trainEvent to match what it will be doing next
     * 
     * @param trainAtCrossing boolean - doesn't matter
     * @param truckInQueue boolean - doesn't matter
     * @return true if train has another event to be processed, false otherwise
     */
    @Override
    public boolean processEvent(boolean trainAtCrossing, boolean truckInQueue) {
        if (trainEvent.equals(TrainEvent.TRAIN_CROSSING)) {
            trainEvent = TrainEvent.TRAIN_FINISHED_CROSSING;
            super.setTime(super.getTime() + duration);
            return true;
        } else {
            return false;
        }
    }
}
