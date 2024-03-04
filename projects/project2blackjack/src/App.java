// used 'https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel' to add pictures
// used 'https://opengameart.org/content/playing-cards-1' for playing card images, but had to crop each card into its own file
// "https://stackoverflow.com/questions/60891289/update-gui-in-response-to-model-changes" used this to understand how to seperate game logic
// and GUI
public class App implements HandObserver, HouseHandObserver {
    // Name: Mark Harshbarger
    // WSU email: harshbarger.26@wright.ed
    private final GameWindow gameGUI;
    private BlackJack gameLogic;
    public App() {
        gameLogic = new BlackJack(this, this);
        gameGUI = new GameWindow(gameLogic);
        gameLogic.addGUI(gameGUI);
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