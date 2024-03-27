public class TruckStart extends Event {
    private int truckNum;

    public TruckStart(int truckNum, double time) {
        super(time);
        this.truckNum = truckNum;
    }

    @Override
    public int getNum() {
        return truckNum;
    }

    @Override
    public String toString() {
        return super.getTime() + ": TRUCK#" + truckNum + " begins journey";
    }
}
