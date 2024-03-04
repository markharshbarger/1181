import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// "https://stackoverflow.com/questions/60891289/update-gui-in-response-to-model-changes" used this to understand how to seperate game logic
// and GUI
public class GameWindow extends JFrame {
    // variables
    private final Color tableColor = new Color(50, 168, 82);
    private JLabel bankLabel;
    private JPanel housePanel;
    private JPanel playerPanel;

    public GameWindow() {
        bankLabel = new JLabel();
        this.setLayout(new BorderLayout());
        this.createComponents();
        this.setTitle("Black Jack");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setVisible(true);

        this.mainGameLoop();
    }

    private void createComponents() {
        JPanel buttonPanel = new JPanel();
        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");
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

        playerPanel = new JPanel();
        housePanel = new JPanel();
        playerPanel.setBackground(tableColor);
        housePanel.setBackground(tableColor);
        this.add(housePanel, BorderLayout.CENTER);
        this.add(playerPanel, BorderLayout.CENTER);
    }

    // used 'https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel' to add pictures
    // used 'https://opengameart.org/content/playing-cards-1' for playing card images, but had to crop each card into its own file
    private void mainGameLoop() {
        BlackJack game = new BlackJack();
        game.play();
    }

    private void addCardToPlayerPanel (ArrayList<Card> hand) {
        BufferedImage cardPicture;
        for (Card i : hand) {
            JLabel picLabel = null;
            try {
                cardPicture = ImageIO.read(new File(i.getFileLocation()));
                picLabel = new JLabel(new ImageIcon(cardPicture));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            playerPanel.add(picLabel);
        }
        updateGraphics();
    }

    public void dealToHouse(Card card) {

    }

    private void addCardToHousePanel(ArrayList<Card> hand) {
        BufferedImage cardPicture;
        for (Card i : hand) {
            JLabel picLabel = null;
            try {
                cardPicture = ImageIO.read(new File("src/CardImages/BACK_CARD.png"));
                picLabel = new JLabel(new ImageIcon(cardPicture));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            housePanel.add(picLabel);
        }
        updateGraphics();
    }

    public void dealToHouseFaceDown() {

    }

    private void addFaceDownCardToHouse() {

    }

    public void revealHouseCard(Card card) {
        
    }

    public boolean hitButton() {
        return true;
    }

    public boolean standButton() {
        return true;
    }

    public void updateGraphics() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
