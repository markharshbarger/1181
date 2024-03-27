public class TruckEnd extends Event {
    private int truckNum;

    public TruckEnd(int truckNum, double time) {
        super(time);
        this.truckNum = truckNum;
    }

    @Override
    public int getNum() {
        return truckNum;
    }

    @Override
    public String toString() {
        return super.getTime() + ": TRUCK#" + truckNum + " completes journey";
    }
}
