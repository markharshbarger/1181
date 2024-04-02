public class App {
    // Name: Mark Harshbarger
    // WSU email: harshbarger.26@wright.ed
    public static void main(String[] args) {
        // 47% (.47) yeilds the best time, this includes only whole percentages and not decimals within the percentage
        PackageDeliverySim.conductSim(.47, "src/train_schedule.txt");
    }
}
