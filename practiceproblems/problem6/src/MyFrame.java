import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private JLabel currentPlayerTurn;
    private String playerOneTurn = "Player 1's turn";
    private String playerTwoTurn = "PLayer 2's turn";

    public MyFrame() {
        this.setLayout(new BorderLayout());
        this.createComponents();
        
        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setVisible(true);
    }

    private void createComponents() {
        JPanel buttonPanel = new JPanel(new GridLayout(3,3));

        for (int i = 1; i <= 9; ++i) {
            JButton tmpButton = new JButton(String.valueOf(i));

            tmpButton.addActionListener(e -> {
                String buttonText = tmpButton.getText();
                if (buttonText.equals("X") || buttonText.equals("O")) {
                    return;
                } else if (currentPlayerTurn.getText().equals(playerOneTurn)) {
                    tmpButton.setText("X");
                    currentPlayerTurn.setText(playerTwoTurn);
                } else {
                    tmpButton.setText("O");
                    currentPlayerTurn.setText(playerOneTurn);
                }
            });
            buttonPanel.add(tmpButton);
        }
        this.add(buttonPanel, BorderLayout.CENTER);

        JPanel playerTurnPanel = new JPanel();
        currentPlayerTurn = new JLabel(playerOneTurn);
        playerTurnPanel.add(currentPlayerTurn);
        this.add(playerTurnPanel, BorderLayout.SOUTH);
    }
}