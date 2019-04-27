package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ChatView extends JFrame {
    private JButton sendButton;
    private JTextField textField;

    private static final String SEND = "Send";

    public ChatView(){
        setTitle("Gamez");
        setSize(600, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        drawWindow();
        setVisible(true);
    }

    private void drawWindow() {
        JPanel principal = new JPanel(new BorderLayout());
        generateNorth(principal);
        generateCenter(principal);
        generateSouth(principal);
        setContentPane(principal);
    }

    private void generateSouth(JPanel principal) {
        JPanel south = new JPanel(new BorderLayout());
        textField = new JTextField();
        sendButton = new JButton(SEND);
        south.add(textField, BorderLayout.CENTER);
        south.add(sendButton, BorderLayout.EAST);
        principal.add(south, BorderLayout.SOUTH);

        sendButton.registerKeyboardAction(sendButton.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

        sendButton.registerKeyboardAction(sendButton.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);

    }

    private void generateCenter(JPanel principal) {

        int width = this.getWidth();
        int height = this.getHeight();

        JLabel center = new JLabel();
        ImageIcon img = new ImageIcon("data/Greeting bot 2.png");
        Image linesImg = img.getImage();
        Image auxImg = linesImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        center.setIcon(new ImageIcon(auxImg));
        principal.add(center, BorderLayout.CENTER);
    }

    private void generateNorth(JPanel principal) {
        JLabel topLabel = new JLabel("Gamez");
        topLabel.setFont(new Font("Slab Serif", Font.PLAIN, 50));
        JPanel north = new JPanel();
        north.setLayout(new FlowLayout(FlowLayout.CENTER));
        north.add(topLabel);
        principal.add(north, BorderLayout.NORTH);
    }


    public String getUserMessage(){
        String msg = textField.getText();
        textField.setText("");
        return msg;
    }

    public void registerListeners(ActionListener chatController) {
        sendButton.addActionListener(chatController);
    }

    public void showMessage(String userMessage) {

    }
}
