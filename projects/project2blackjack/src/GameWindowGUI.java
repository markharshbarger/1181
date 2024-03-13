import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class represents the GUI of a game of Black Jack, uses BlackJack.java for the logic part. Window is set to open
 * in the middle of screen. Game was built for a 1080p monitor, should still work for higher resolution but some elements might
 * be smaller
 */
public class GameWindowGUI extends JFrame {
    // variables
    private BlackJack game;
    private final Color tableColor = new Color(50, 168, 82);
    private final Font scoreFont = new Font("Arial", Font.BOLD, 37);
    private final Font ButtonFont = new Font("Serif", Font.BOLD, 27);
    private final Font infoFont = new Font("Sherif", Font.BOLD, 18);
    private JLabel bankLabel;
    private JLabel highScorLabel;
    private JLabel playerScoreLabel;
    private JLabel houseScoreLabel;
    private JLabel gameResultLabel;
    private JPanel housePanel;
    private JPanel playerPanel;
    private JPanel centerPanel;
    private JPanel gameResultPanel;
    private JSpinner spinner;
    private JButton betButton;
    private JButton hitButton;
    private JButton standButton;

    /**
     * Constructor for GUI that initializes the defualt setttings to have a window appear. Has window appear in the middle of
     * the screen
     * 
     * @param game - the logic for Black Jack game
     */
    public GameWindowGUI(BlackJack game) {
        this.game = game;
        this.setLayout(new BorderLayout());
        this.createComponents();
        this.setTitle("Black Jack");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Creates the components for the window, includes all the buttons and info that is going to be displayed
     */
    private void createComponents() {
        // create buttons for hit and stand and bet
        JPanel buttonPanel = new JPanel();
        createBetComponent(buttonPanel);
        createHitAndStandComponent(buttonPanel);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // create the labels for displaying bank and high score info
        JPanel infoPanel = new JPanel();
        JLabel bankStringLabel = new JLabel("Bank:");
        JLabel highScoreStringLabel = new JLabel("High Score:");
        highScoreStringLabel.setFont(infoFont);
        bankStringLabel.setFont(infoFont);
        bankLabel = new JLabel(String.valueOf(game.getBank()));
        highScorLabel = new JLabel(String.valueOf(game.getHighScore()));
        highScorLabel.setFont(infoFont);
        bankLabel.setFont(infoFont);
        infoPanel.add(highScoreStringLabel);
        infoPanel.add(highScorLabel);
        infoPanel.add(new JLabel("   "));
        infoPanel.add(bankStringLabel);
        infoPanel.add(bankLabel);
        this.add(infoPanel, BorderLayout.NORTH);

        // create label for displaying player and house's hand values
        playerScoreLabel = new JLabel("0");
        playerScoreLabel.setFont(scoreFont);
        houseScoreLabel = new JLabel("0");
        houseScoreLabel.setFont(scoreFont);

        // create a label for displaying the results of the round
        gameResultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gameResultLabel = new JLabel("");
        gameResultLabel.setFont(scoreFont);
        gameResultPanel.add(gameResultLabel);

        // creates the panels for the player and house's hand to displayed, along with their current hand score
        centerPanel = new JPanel(new GridLayout(3,1));
        playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        housePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerPanel.setBackground(tableColor);
        housePanel.setBackground(tableColor);
        gameResultPanel.setBackground(tableColor);
        centerPanel.add(housePanel);
        centerPanel.add(gameResultPanel);
        centerPanel.add(playerPanel);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a JSpinner and JButton for the bet component of the game. Adds an action listener to JButton
     * 
     * @param buttonPanel - the panel which JSpinner and JButton will be added to
     */
    private void createBetComponent(JPanel buttonPanel) {
        spinner = new JSpinner(new SpinnerNumberModel(game.getBetAmount(), 1 , 999, 10));
        spinner.setFont(ButtonFont);
        betButton = new JButton("Bet");
        betButton.setFont(ButtonFont);
        betButton.addActionListener(e -> betButton());
        buttonPanel.add(spinner);
        buttonPanel.add(betButton);
    }

    /**
     * Creates two JButton's for 'hit' and 'stand'. Adds action listener's to both
     * 
     * @param buttonPanel - the panel which both JButton's will be added to
     */
    private void createHitAndStandComponent(JPanel buttonPanel) {
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        hitButton.setFont(ButtonFont);
        standButton.setFont(ButtonFont);
        hitButton.addActionListener(e -> hitButton());
        standButton.addActionListener(e -> standButton());
        setHitAndStandButton(false);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
    }

    /**
     * The action method for the 'hit' button. Calls the game's logic to perform hit action
     */
    private void hitButton() {
        System.out.println("Hitting");
        game.hit();
    }

    /**
     * Sets the 'hit' and 'stand' buttons to be shown or not
     * 
     * @param value - true to show the buttons and allow input, false otherwise
     */
    public void setHitAndStandButton(boolean value) {
        hitButton.setEnabled(value);
        standButton.setEnabled(value);
    }

    /**
     * The action method for the 'stand' button. Uses the game logic to perform a stand action
     */
    private void standButton() {
        System.out.println("Standing");
        game.stand();
    }

    /**
     * Sets bet amount in game logic to the value specified by user, if user has 0 funds, will display that the game is over.
     * Allows user to play again. If the user bets an amount over what is in bank, game uses the max amount in bank for bet.
     * Disables bet button and plays a round of Black Jack
     */
    private void betButton() {
        if (game.getBank() == 0) {
            gameResultLabel.setForeground(Color.RED);
            refreshRoundStat("Game Over, out of funds");
            newGame();
            return;
        }
        refreshRoundStat("");
        setBetAndSpinner(false);

        // if user bets over the amount in bank, have them bet the amount in the bank
        if ((int)spinner.getValue() > game.getBank()) {
            spinner.setValue(game.getBank());
            game.setBetAmount((int)game.getBank());
            game.play();
            return;
        }
        game.setBetAmount((int)spinner.getValue());
        game.play();
    }

    /**
     * Sets the bank amount displayed in GUI
     * 
     * @param value int - the amount to set bank to display
     */
    public void setBank(int value) {
        bankLabel.setText(String.valueOf(value));
    }

    /**
     * Sets the bet button and spinner to value specifiec
     * 
     * @param value true to display button and spinner and allow user input, false otherwise
     */
    public void setBetAndSpinner(boolean value) {
        SwingUtilities.invokeLater(() -> {
            spinner.setEnabled(value);
            betButton.setEnabled(value);
        });

    }

    /**
     * Refreshes the hand of the house on the GUI and displays the hand's current value along with the cards
     * 
     * @param hand - the house's hand
     */
    public void refreshHouseHand(Hand hand) {
        BufferedImage cardPicture;
        housePanel.removeAll();
        ArrayList<Card> cardList = hand.getHand();
        for (Card card : cardList) {
            JLabel picLabel = null;
            try {
                cardPicture = ImageIO.read(new File(card.getFileLocation()));
                picLabel = new JLabel(new ImageIcon(cardPicture));
            } catch (IOException e) {
                System.out.println("Error getting card image");
            }
            housePanel.add(picLabel);
        }
        houseScoreLabel.setText(String.valueOf(hand.value()));
        housePanel.add(houseScoreLabel);
        updateGraphics();
        System.out.println("refreshing house");
    }

    /**
     * Refreshes the hand of the player on the GUI and displays the hand's current value along with the cards
     * 
     * @param hand - the player's hand
     */
    public void refreshPlayerHand(Hand hand) {
        BufferedImage cardPicture;
        playerPanel.removeAll();
        ArrayList<Card> cardList = hand.getHand();
        for (Card card : cardList) {
            JLabel picLabel = null;
            try {
                cardPicture = ImageIO.read(new File(card.getFileLocation()));
                picLabel = new JLabel(new ImageIcon(cardPicture));
            } catch (IOException e) {
                System.out.println("Error getting card image");
            }
            playerPanel.add(picLabel);
        }
        playerScoreLabel.setText(String.valueOf(hand.value()));
        playerPanel.add(playerScoreLabel);
        updateGraphics();
        System.out.println("refreshing Player");
    }

    /**
     * Displays the winner/loser of last round to the GUI
     * 
     * @param roundStat String - that contains the winner/loser
     */
    public void refreshRoundStat(String roundStat) {
        gameResultLabel.setText(roundStat);
        updateGraphics();
    }

    /**
     * Updates the graphics of the GUI
     */
    private void updateGraphics() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Creates a JOptionPane for the user to have the option to play again, yes for a new game, no for the application to exit
     */
    private void newGame() {
        int userInput = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (userInput == JOptionPane.YES_OPTION) {
            game.newGame();
        } else if (userInput == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Sets the high score on the GUI
     * 
     * @param value int - the high score
     */
    public void setHighScore(int value) {
        highScorLabel.setText(Integer.toString(value));
    }
}
