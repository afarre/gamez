package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ChatView extends JFrame {

    private JButton sendButton;
    private JTextField textField;
    private JScrollPane scrollPane;
    private Box center;
    private JPanel principal;

    private static final String SEND = "Send";

    public ChatView(){
        setTitle("Gamez");
        setSize(400, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        drawWindow();
        setVisible(true);
    }

    private void drawWindow() {
        principal = new JPanel(new BorderLayout());
        generateNorth();
        generateCenter();
        generateSouth();
        setContentPane(principal);
    }

    private void generateSouth() {
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


    private void generateCenter(){
        center = Box.createVerticalBox();
        scrollPane = new JScrollPane(center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        principal.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateCenter(String msg, boolean botMsg){
        //JPanel borderedPanel = new JPanel();
        JLabel msgLabel = new JLabel("<html><body style='width: %1spx'>" + msg + "</hmtl>\n");
        //Dimension d = new Dimension();
        //d.setSize(350, 20);
        //msgLabel.setMaximumSize(d);
        //msgLabel.setPreferredSize(new Dimension(379,10));
        //msgLabel.setOpaque(true);

        if (botMsg){
            //borderedPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            msgLabel.setHorizontalAlignment(JLabel.LEFT);
            msgLabel.setVerticalAlignment(JLabel.TOP);
        }else {
            //borderedPanel.setBorder(BorderFactory.createLoweredBevelBorder());
            msgLabel.setHorizontalAlignment(JLabel.RIGHT);
            msgLabel.setVerticalAlignment(JLabel.TOP);
            msgLabel.setForeground(Color.BLUE);
        }

        //borderedPanel.add(msgLabel);

        textField.requestFocus();
        center.add(msgLabel);
        principal.add(scrollPane, BorderLayout.CENTER);
        setContentPane(principal);

        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum() );

    }

    private void generateCenter2() {

        int width = this.getWidth();
        int height = this.getHeight();

        JLabel test = new JLabel("angel");
/*
        try {
            PaintPane pane = new PaintPane(ImageIO.read(new File("data/Greeting bot 2.png")));
            pane.setLayout(new BorderLayout());
            add(pane);

            JLabel label = new JLabel("I'm on fire");
            label.setFont(label.getFont().deriveFont(Font.BOLD, 48));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            pane.add(label);
            principal.add(pane, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        JLabel center = new JLabel();
        ImageIcon img = new ImageIcon("data/Greeting bot 2.png");
        Image linesImg = img.getImage();
        Image auxImg = linesImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        center.setIcon(new ImageIcon(auxImg));

        JLabel background = new JLabel(new ImageIcon("data/Greeting bot 2.png"));
        background.setLayout(new BorderLayout());
        test.setFont(test.getFont().deriveFont(Font.BOLD, 20));
        test.setForeground(Color.BLACK);
        test.setHorizontalAlignment(JLabel.CENTER);
        background.add(test);

        principal.add(background, BorderLayout.CENTER);

    }

    private void generateNorth() {
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
        textField.addActionListener(chatController);
    }

}
