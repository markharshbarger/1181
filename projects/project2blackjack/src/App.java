// used 'https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel' to add pictures
// used 'https://opengameart.org/content/playing-cards-1' for playing card images, but had to crop each card into its own file
// "https://stackoverflow.com/questions/60891289/update-gui-in-response-to-model-changes" used this to understand how to seperate game logic
// and GUI

import javax.swing.SwingUtilities;

public class App implements HandObserver, HouseHandObserver, EndOfRoundObserver, BankObserver, HighScoreObserver {
    // Name: Mark Harshbarger
    // WSU email: harshbarger.26@wright.ed
    private final GameWindow gameGUI;
    private BlackJack gameLogic;
    public App() {
        gameLogic = new BlackJack(this, this, this, this, this);
        gameGUI = new GameWindow(gameLogic);
        gameLogic.addGUI(gameGUI);
        // gameLogic.play();
    }

    @Override
    public void handChange() {
        SwingUtilities.invokeLater(() -> gameGUI.refreshPlayerHand(gameLogic.getPlayerHand()));
    }

    @Override
    public void houseHandChange() {
        SwingUtilities.invokeLater(() ->  gameGUI.refreshHouseHand(gameLogic.getHouseHand()));
    }

    @Override
    public void endOfRound() {
        SwingUtilities.invokeLater(() -> gameGUI.refreshRoundStat(gameLogic.getRoudStat()));
    }

    @Override
    public void bankChange() {
        SwingUtilities.invokeLater(() -> gameGUI.setBank(gameLogic.getBank()));
    }

    @Override
    public void newHighScore() {
        SwingUtilities.invokeLater(() -> gameGUI.setHighScore(gameLogic.getHighScore()));
    }

    public static void main(String[] args) throws Exception {
        // when play button in rulewindow is clicked it launches the gamewindow
        SwingUtilities.invokeLater(() -> new RuleWindow());
    }

    public static void playGame() {
        new App();
    }
}