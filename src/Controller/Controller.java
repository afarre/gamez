package Controller;

import Model.APIData;
import Model.JsonManager;
import Model.UserInfo;
import View.ChatView;
import com.google.gson.Gson;
import sun.nio.cs.US_ASCII;

import javax.jws.soap.SOAPBinding;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Controller implements ActionListener {
    private ChatView chatView;
    private UserInfo userInfo;

    public Controller(ChatView chatView, JsonManager jsonManager) {
        this.chatView = chatView;
        try {
            File configFile = new File("data/userInfo.json");
            userInfo = new Gson().fromJson(new FileReader(configFile), UserInfo.class);
        } catch (FileNotFoundException e) {
            System.out.println("There was an error reading the user personal data.");
        }

        if (userInfo == null){
            chatView.updateCenter("<html>Hello and welcome to Gamez, your gaming chatbot!<br/>I see you are new here. Why don't you tell me a bit about yourself?<br/><html>", true);
            //chatView.updateCenter("Hello and welcome to Gamez, your gaming chatbot!\nI see you are new here. Why don't you tell me a bit about yourself?\n", true);
        }else {
            chatView.updateCenter("<html>Hello and welcome to Gamez, your gaming chatbot!<br/>It's nice to have you back " + userInfo.getName() + "! How have you been?<br/><html>", true);
            //chatView.updateCenter("Hello and welcome to Gamez, your gaming chatbot!\nIt's nice to have you back " + userInfo.getName() + "! How have you been?", true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chatView.updateCenter(chatView.getUserMessage(), false);
        botEngineAnswer();
    }

    private void botEngineAnswer() {


    }
}
