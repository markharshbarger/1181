import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private BlackJack game;
    private JPanel centerPanel;

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
        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");
        // hitButton.addActionListener(e -> hitButton());
        // standButton.addActionListener(e -> standButton());
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel();
        JLabel bankStringLabel = new JLabel("Bank:");
        infoPanel.add(bankStringLabel);
        infoPanel.add(bankLabel);
        this.add(infoPanel, BorderLayout.NORTH);

        centerPanel = new JPanel(new GridLayout(2,1));
        playerPanel = new JPanel();
        housePanel = new JPanel();
        playerPanel.setBackground(tableColor);
        housePanel.setBackground(tableColor);
        centerPanel.add(housePanel);
        centerPanel.add(playerPanel);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    // used 'https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel' to add pictures
    // used 'https://opengameart.org/content/playing-cards-1' for playing card images, but had to crop each card into its own file

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

    public void refreshHouseHand(ArrayList<Card> hand) {
        BufferedImage cardPicture;
        playerPanel.removeAll();
        for (Card i : hand) {
            JLabel picLabel = null;
            try {
                cardPicture = ImageIO.read(new File(i.getFileLocation()));
                picLabel = new JLabel(new ImageIcon(cardPicture));
            } catch (IOException e) {
                System.out.println("Error getting card image");
            }
            housePanel.add(picLabel);
        }
        updateGraphics();
        System.out.println("dealing to House");
    }

    public void refreshPlayerHand(ArrayList<Card> hand) {
        BufferedImage cardPicture;
        playerPanel.removeAll();
        for (Card i : hand) {
            JLabel picLabel = null;
            try {
                cardPicture = ImageIO.read(new File(i.getFileLocation()));
                picLabel = new JLabel(new ImageIcon(cardPicture));
            } catch (IOException e) {
                System.out.println("Error getting card image");
            }
            playerPanel.add(picLabel);
        }
        updateGraphics();
        System.out.println("dealing to Player");
    }

    public void updateGraphics() {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
