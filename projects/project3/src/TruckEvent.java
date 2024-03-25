public enum TruckEvent {
    TRUCK_START ("begins journey"),
    TRUCK_WAITS_AT_CROSSING ("waits at crossing"),
    TRUCK_CROSS ("crosses crossing"),
    TRUCK_END ("completes journey");

    private String description;

    TruckEvent(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}