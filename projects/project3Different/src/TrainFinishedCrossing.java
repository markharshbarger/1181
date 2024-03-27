public class TrainFinishedCrossing extends Event {
    public TrainFinishedCrossing(double time) {
        super(time);
    }

    @Override
    public String toString() {
        return super.getTime() + ": TRAIN leaves crossing";
    }
}
