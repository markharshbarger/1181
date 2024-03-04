public class App implements HandObserver {
    // Name: Mark Harshbarger
    // WSU email: harshbarger.26@wright.ed
    private final GameWindow gameGUI;
    private BlackJack gameLogic;
    public App() {
        gameLogic = new BlackJack(this);
        gameGUI = new GameWindow(gameLogic);
        gameLogic.play();
    }

    @Override
    public void handChange() {
        gameGUI.refreshPlayerHand(gameLogic.getPlayerHand());
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}