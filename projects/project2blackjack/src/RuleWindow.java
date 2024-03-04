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
        frame.setSize(800, 350);
        frame.setTitle("Black Jack Rules");
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private void playGame() {
        frame.dispose();
        new GameWindow();
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
                        "2. The goal of the game is to beat the House.\n" +
                        "3. The person with the highest score, without going over 21 wins.\n" +
                        "4. Numbered cards values follow their number.\n" + 
                        "5. Jack is 11, Queen is 12, King is 13, Ace is 14 or 1.\n" +
                        "6. The program automatically chooses Ace value based on which one helps you the best.";
        ruleTextArea.setText(rules);

        Font ruleFont = new Font("Rule Font", Font.ROMAN_BASELINE, 16);
        ruleTextArea.setFont(ruleFont);

        rulePanel.add(ruleTextArea);
        frame.add(rulePanel, BorderLayout.CENTER);
    }

}
