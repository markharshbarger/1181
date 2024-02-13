import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class represents a program that allows the user to convert Celsius to Fahrenheit or vice versa. 
 */
public class MyFrame extends JFrame {
    private JTextField celsiusField;
    private JTextField fahrenheitField;
    private JTextField lastEdited;
    private int textFieldLength = 10;

    /**
     * Constructor of MyFrame. Creates all the components and sets the default parameters for a JFrame
     */
    public MyFrame() {
        this.createComponents();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(500,200));
        this.setVisible(true);
    }

    /**
     * Creates the components for the JFrame. Has 3 JPanel's; 2 JPanel's have a JLabel with a JTextField for user input. Other JPanel
     * has a JButton. The 2 JPanels with a JTextField include a KeyListerner that allows the program to keep track the last field edited
     * by user. JButton perfoms a calculation between one field to another field when clicked.
     */
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
        convertButton.addActionListener(e -> convertInput());
        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.add(convertButton);
    }

    /**
     * Converts Celsius to Fahrenheit
     * 
     * @param celsius int - represents the temperature in Celsuis
     * @return int - of the converted temperature in Fahrenheit
     */
    private int celsiusToFahrenheit(int celsius) {
        return (celsius * 9 / 5) + 32;
    }

    /**
     * Converts Fahrenheit to Celsius
     * 
     * @param fahrenheit int - represents the temperature in Fahrenheit
     * @return int - of the converted temperature in Celsuis
     */
    private int fahrenheitToCelsius(int fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    /**
     * Convert's the input from one JTextField and outputs the converted temperature to the other JTextField. Includes exception
     * handling if the input isn't an int.
     */
    private void convertInput() {
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
    }
}
