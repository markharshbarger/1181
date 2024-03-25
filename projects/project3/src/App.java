public class App {
    // 1,500 total packages a day
    // 30,000 units to railroad crossing; 30,000 units until finish in total
    // drone carry one package, truck carry ten package
    public static void main(String[] args) throws Exception {
        // 17%?
        PackageDeliverySim.conductSim(.25, "src/train_schedule.txt");
    }
}
