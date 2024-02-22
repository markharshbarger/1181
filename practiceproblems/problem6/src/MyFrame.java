import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private JLabel currentPlayerTurn;
    private String playerOneTurn = "Player 1's turn";
    private String playerTwoTurn = "PLayer 2's turn";

    /**
     * Constructor of MyFrame, sets layout to border layout and creates the component. Also initializes the other values
     * needed for JFrame
     */
    public MyFrame() {
        this.setLayout(new BorderLayout());
        this.createComponents();
        
        this.setTitle("TicTacToe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setVisible(true);
    }

    /**
     * Creates the components of the game. It's a Tic tac toe game. Player one starts with"X" then player two with "O", switches
     * between the two. Player can not choose a tile that already as on "X" or "O". Game doesn't tell you who won.
     */
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