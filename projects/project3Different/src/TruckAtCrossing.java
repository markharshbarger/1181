public class TruckAtCrossing extends Event {
    private int truckNum;

    public TruckAtCrossing(int truckNum, double time) {
        super(time);
        this.truckNum = truckNum;
    }

    @Override
    public int getNum() {
        return truckNum;
    }

    @Override
    public String toString() {
        return super.getTime() + ": TRUCK#" + truckNum + " waits at crossing";
    }
}