// used 'https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel' to add pictures
// used 'https://opengameart.org/content/playing-cards-1' for playing card images, but had to crop each card into its own file
// "https://stackoverflow.com/questions/60891289/update-gui-in-response-to-model-changes" used this to understand how to seperate game logic
// and GUI
import javax.swing.SwingUtilities;

/**
 * This class represents the main method in a game of Black Jack, it ties together the logic and GUI of the game.
 */
public class App implements HandObserver, HouseHandObserver, EndOfRoundObserver, BankObserver, HighScoreObserver {
    // Name: Mark Harshbarger
    // WSU email: harshbarger.26@wright.ed
    private final GameWindowGUI gameGUI;
    private BlackJack gameLogic;

    /**
     * Constructor that initilizes the gameGUI and gameLogic and ties them together. Also adds in instances of itself to use
     * for all the observers that this class implemented
     */
    public App() {
        gameLogic = new BlackJack(this, this, this, this, this);
        gameGUI = new GameWindowGUI(gameLogic);
        gameLogic.addGUI(gameGUI);
    }

    public static void main(String[] args) throws Exception {
        // when play button in rulewindow is clicked it launches the gamewindow using App() method
        SwingUtilities.invokeLater(() -> new RuleWindow());
    }

    /**
     * Changes the hand for player in the GUI, whenever the hand for player changes in game logic
     */
    @Override
    public void handChange() {
        SwingUtilities.invokeLater(() -> gameGUI.refreshPlayerHand(gameLogic.getPlayerHand()));
    }

    /**
     * Changes the hand for house in the GUI, whenever the hand for the house changes in game logic
     */
    @Override
    public void houseHandChange() {
        SwingUtilities.invokeLater(() ->  gameGUI.refreshHouseHand(gameLogic.getHouseHand()));
    }

    /**
     * Outputs the round result to the GUI whenever a round is finished
     */
    @Override
    public void endOfRound() {
        SwingUtilities.invokeLater(() -> gameGUI.refreshRoundStat(gameLogic.getRoudStat()));
    }

    /**
     * Synchronizes the bank from game logic to the GUI
     */
    @Override
    public void bankChange() {
        SwingUtilities.invokeLater(() -> gameGUI.setBank(gameLogic.getBank()));
    }

    /**
     * Synchronizes the high score from game logic to the GUI
     */
    @Override
    public void newHighScore() {
        SwingUtilities.invokeLater(() -> gameGUI.setHighScore(gameLogic.getHighScore()));
    }
}