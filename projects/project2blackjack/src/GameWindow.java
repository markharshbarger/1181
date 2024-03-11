import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameWindow extends JFrame {
    // variables
    private BlackJack game;
    private final Color tableColor = new Color(50, 168, 82);
    private final Font scoreFont = new Font("Arial", Font.BOLD, 37);
    private final Font ButtonFont = new Font("Serif", Font.BOLD, 27);
    private final Font infoFont = new Font("Sherif", Font.BOLD, 18);
    private JLabel bankLabel;
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

    public GameWindow(BlackJack game) {
        this.game = game;
        this.setLayout(new BorderLayout());
        this.createComponents();
        this.setTitle("Black Jack");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createComponents() {
        JPanel buttonPanel = new JPanel();
        createBetComponent(buttonPanel);
        createHitAndStandComponent(buttonPanel);
        this.add(buttonPanel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel();
        JLabel bankStringLabel = new JLabel("Bank:");
        bankStringLabel.setFont(infoFont);
        bankLabel = new JLabel(String.valueOf(game.getBank()));
        bankLabel.setFont(infoFont);
        infoPanel.add(bankStringLabel);
        infoPanel.add(bankLabel);
        this.add(infoPanel, BorderLayout.NORTH);

        playerScoreLabel = new JLabel("0");
        playerScoreLabel.setFont(scoreFont);
        houseScoreLabel = new JLabel("0");
        houseScoreLabel.setFont(scoreFont);

        gameResultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gameResultLabel = new JLabel("");
        gameResultLabel.setFont(scoreFont);
        gameResultPanel.add(gameResultLabel);

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

    private void createBetComponent(JPanel buttonPanel) {
        spinner = new JSpinner(new SpinnerNumberModel(game.getBetAmount(), 1 , 999, 10));
        spinner.setFont(ButtonFont);
        betButton = new JButton("Bet");
        betButton.setFont(ButtonFont);
        betButton.addActionListener(e -> betButton());
        buttonPanel.add(spinner);
        buttonPanel.add(betButton);
    }

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

    private void hitButton() {
        System.out.println("Hitting");
        game.hit();
    }

    public void setHitAndStandButton(boolean value) {
        hitButton.setEnabled(value);
        standButton.setEnabled(value);
    }

    private void standButton() {
        System.out.println("Standing");
        game.stand();
    }

    private void betButton() {
        if (game.getBank() == 0) {
            gameResultLabel.setForeground(Color.RED);
            refreshRoundStat("Game Over, out of funds");
            return;
        }
        refreshRoundStat("");
        if ((int)spinner.getValue() > game.getBank()) {
            return;
        }
        setBetAndSpinner(false);
        game.setBetAmount((int)spinner.getValue());
        game.play();
    }

    public void setBank(int value) {
        bankLabel.setText(String.valueOf(value));
    }

    public void setBetAndSpinner(boolean value) {
        spinner.setEnabled(value);
        betButton.setEnabled(value);
    }

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

    public void refreshRoundStat(String roundStat) {
        gameResultLabel.setText(roundStat);
        update(getGraphics());
    }

    public void updateGraphics() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
