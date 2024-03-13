import javax.swing.*;
import java.awt.*;

/**
 * JPanel that displays the rules for the game of Black Jack includes an option to start the game or an option to close
 * the application
 */
public class RuleWindow {
    JFrame frame = new JFrame();
    JButton playButton = new JButton("Play");
    JButton quitButton = new JButton("Quit");
    JPanel rulePanel;
    JPanel buttonPanel;

    /**
     * Constructor for rule window that initilizes all the values needed to display the GUI to the user. Includes
     * two buttons to play the game or close the application
     */
    public RuleWindow() {
        frame.setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        playButton.addActionListener(e -> playGame());
        quitButton.addActionListener(e -> quitGame());
        buttonPanel.add(quitButton);
        buttonPanel.add(playButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        setRules();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050, 450);
        frame.setTitle("Black Jack Rules");
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    /**
     * Invokes the App method which initilizes the Black Jack game with GUI, disposes of Rule Window frame (current frame)
     */
    private void playGame() {
        SwingUtilities.invokeLater(() -> new App());
        frame.dispose();
    }

    /**
     * Closes this application
     */
    private void quitGame() {
        System.exit(0);
    }

    /**
     * Sets the JTextArea to display the game rules to user
     */
    private void setRules() {
        rulePanel = new JPanel();
        JTextArea ruleTextArea = new JTextArea();
        ruleTextArea.setEditable(false);

        String rules =  "Rules:\n" +
                        "1. Card suit does not matter\n" +
                        "2. Numbered cards values follow their number.\n" +
                        "3. Face cards are 10, and Ace is either 11 or 1\n" +
                        "4. The program automatically chooses Ace value based on which one helps you the best.\n" + 
                        "5. The person with the highest score, without going over 21 wins.\n" +
                        "6. Select amount to bet and hit the 'Bet' Button to begin a round\n" +
                        "7. Use 'hit' button to draw a card, 'stand' button to have the house reveal and draw cards\n" +
                        "8. See how much money you can accumulate.\n" +
                        "9. When you lose all your money, hit 'Bet' button to have an option to play again";
        ruleTextArea.setText(rules);

        Font ruleFont = new Font("Rule Font", Font.ROMAN_BASELINE, 20);
        ruleTextArea.setFont(ruleFont);

        rulePanel.add(ruleTextArea);
        frame.add(rulePanel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(rulePanel);
    }

}
