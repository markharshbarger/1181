import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyFrame extends JFrame {
    private JTextField celsiusField;
    private JTextField fahrenheitField;
    private JTextField lastEdited;
    private int textFieldLength = 10;
    public MyFrame() {
        this.createComponents();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(500,200));
        this.setVisible(true);
    }

    public void createComponents() {
        JPanel fahrenheitPanel = new JPanel();
        this.add(fahrenheitPanel);

        JLabel fahrenheitLabel = new JLabel("Fahrenheit");
        fahrenheitPanel.add(fahrenheitLabel);

        fahrenheitField = new JTextField(textFieldLength);
        fahrenheitField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            lastEdited = (JTextField) e.getSource();
            }
        });
        fahrenheitPanel.add(fahrenheitField);


        JPanel celsiusPanel = new JPanel();
        this.add(celsiusPanel);

        JLabel celsius = new JLabel("Celsius");
        celsiusPanel.add(celsius);

        celsiusField = new JTextField(textFieldLength);
        celsiusField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            lastEdited = (JTextField) e.getSource();
            }
        });
        celsiusPanel.add(celsiusField);


        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> {
            if (lastEdited == celsiusField) {
                int celsiusInput;
                try {
                    celsiusInput = celsiusToFahrenheit(Integer.parseInt(celsiusField.getText()));
                } catch (NumberFormatException exception) {
                    celsiusField.setText("A whole number!");
                    return;
                }
                fahrenheitField.setText(String.valueOf(celsiusInput));
            } else if (lastEdited == fahrenheitField) {
                int fahrenheitInput;
                try {
                    fahrenheitInput = fahrenheitToCelsius(Integer.parseInt(fahrenheitField.getText()));
                } catch (NumberFormatException exception) {
                    fahrenheitField.setText("A whole number!");
                    return;
                }
                celsiusField.setText(String.valueOf(fahrenheitInput));
            }
        });
        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.add(convertButton);
    }

    private int celsiusToFahrenheit(int celsius) {
        return (celsius * 9 / 5) + 32;
    }

    private int fahrenheitToCelsius(int fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
}
