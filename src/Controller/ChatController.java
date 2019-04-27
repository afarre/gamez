package Controller;

import View.ChatView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatController implements ActionListener {
    private ChatView chatView;

    public ChatController(ChatView chatView) {
        this.chatView = chatView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.print("Enter pressed. User msg: ");
        System.out.println(chatView.getUserMessage());
        chatView.showMessage(chatView.getUserMessage());
    }
}
