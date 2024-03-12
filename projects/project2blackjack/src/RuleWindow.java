import javax.swing.*;
import java.awt.*;

public class RuleWindow {
    JFrame frame = new JFrame();
    JButton playButton = new JButton("Play");
    JButton quitButton = new JButton("Quit");
    JPanel rulePanel;
    JPanel buttonPanel;

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
        frame.setSize(1000, 350);
        frame.setTitle("Black Jack Rules");
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private void playGame() {
        SwingUtilities.invokeLater(() -> new App());
        frame.dispose();
    }

    private void quitGame() {
        System.exit(0);
    }

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
                        "6. Select amount to bet and hit the Bet Button to begin a round\n" +
                        "7. Use 'hit' button to draw a card, 'stand' button to have the house reveal and draw cards\n" +
                        "8. See how much money you can accumulate.";
        ruleTextArea.setText(rules);

        Font ruleFont = new Font("Rule Font", Font.ROMAN_BASELINE, 20);
        ruleTextArea.setFont(ruleFont);

        rulePanel.add(ruleTextArea);
        frame.add(rulePanel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(rulePanel);
    }

}
