import Controller.ChatController;
import View.ChatView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChatView chatView = new ChatView();
                ChatController chatController = new ChatController(chatView);
                chatView.registerListeners(chatController);
            }
        });
    }
}
