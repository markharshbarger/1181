public class TrainCrossing extends Event {
    public TrainCrossing(double time) {
        super(time);
    }

    @Override
    public String toString() {
        return super.getTime() + ": TRAIN arrives at crossing";
    }
}
