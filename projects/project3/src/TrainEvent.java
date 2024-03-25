public enum TrainEvent {
    TRAIN_CROSSING ("TRAIN arrives at crossing"),
    TRAIN_FINISHED_CROSSING ("TRAIN leaves crossing");
    
    private String description;

    TrainEvent(String descprition) {
        this.description = descprition;
    }

    @Override
    public String toString() {
        return description;
    }
}
