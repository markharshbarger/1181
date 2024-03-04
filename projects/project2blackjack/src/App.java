public class App implements HandObserver, HouseHandObserver {
    // Name: Mark Harshbarger
    // WSU email: harshbarger.26@wright.ed
    private final GameWindow gameGUI;
    private BlackJack gameLogic;
    public App() {
        gameLogic = new BlackJack(this, this);
        gameGUI = new GameWindow(gameLogic);
        gameLogic.play();
    }

    @Override
    public void handChange() {
        gameGUI.refreshPlayerHand(gameLogic.getPlayerHand());
    }

    @Override
    public void houseHandChange() {
        gameGUI.refreshHouseHand(gameLogic.getHouseHand());
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}