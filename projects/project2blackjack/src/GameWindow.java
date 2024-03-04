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
    private JLabel bankLabel;
    private JLabel playerScoreLabel;
    private JLabel houseScoreLabel;
    private JPanel housePanel;
    private JPanel playerPanel;
    private JPanel centerPanel;
    JButton hitButton;
    JButton standButton;

    public GameWindow(BlackJack game) {
        bankLabel = new JLabel();
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
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        hitButton.addActionListener(e -> hitButton());
        standButton.addActionListener(e -> standButton());
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel();
        JLabel bankStringLabel = new JLabel("Bank:");
        infoPanel.add(bankStringLabel);
        infoPanel.add(bankLabel);
        this.add(infoPanel, BorderLayout.NORTH);

        playerScoreLabel = new JLabel("0");
        playerScoreLabel.setFont(scoreFont);
        houseScoreLabel = new JLabel("0");
        houseScoreLabel.setFont(scoreFont);

        centerPanel = new JPanel(new GridLayout(2,1));
        playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        housePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerPanel.setBackground(tableColor);
        housePanel.setBackground(tableColor);
        centerPanel.add(housePanel);
        centerPanel.add(playerPanel);
        this.add(centerPanel, BorderLayout.CENTER);
    }



    // private void addCardToPlayerPanel (ArrayList<Card> hand) {
    //     BufferedImage cardPicture;
    //     for (Card i : hand) {
    //         JLabel picLabel = null;
    //         try {
    //             cardPicture = ImageIO.read(new File(i.getFileLocation()));
    //             picLabel = new JLabel(new ImageIcon(cardPicture));
    //         } catch (IOException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //         playerPanel.add(picLabel);
    //     }
    //     updateGraphics();
    // }

    private void hitButton() {
        game.bet();
        System.out.println("Betting");
    }

    public void setHitAndStandButton(boolean value) {
        hitButton.setEnabled(value);
        standButton.setEnabled(value);
    }

    public boolean standButton() {
        System.out.println("Standing");
        return true;
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
        System.out.println("dealing to House");
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
        System.out.println("dealing to Player");
    }

    public void updateGraphics() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
