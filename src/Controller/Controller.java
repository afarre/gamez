package Controller;

import Model.JsonManager;
import Model.UserInfo;
import Network.ChatBotClient;
import Util.BotResponse;
import View.ChatView;
import com.google.gson.Gson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private ChatView chatView;
    private UserInfo userInfo;
    private ChatBotClient chatBotClient;
    private BotResponse response;

    public Controller(ChatView chatView, JsonManager jsonManager, ChatBotClient chatBotClient) {
        this.chatView = chatView;
        this.chatBotClient = chatBotClient;
        response = new BotResponse();
        try {
            File configFile = new File("data/user.json");
            userInfo = new Gson().fromJson(new FileReader(configFile), UserInfo.class);
            System.out.println("read ok");
        } catch (FileNotFoundException ignored) { }

        if (userInfo == null){
            botEngineAnswer("Not exists");
            //chatView.updateCenter("<html>Hello and welcome to Gamez, your gaming chatbot!" +
              //      "<br/>I see you are new here. Why don't you tell me a bit about yourself?<br/><html>", true);
            //chatView.updateCenter("Hello and welcome to Gamez, your gaming chatbot!\nI see you are new here. Why don't you tell me a bit about yourself?\n", true);
        }else {
            /*
            chatView.updateCenter("<html>" +
                    "<div align=right>" +
                    " text a la dreta " +
                    "  </div>" +
                    "<div align=left>" +
                    " Some Random text to be right aligned " +
                    "  </div>" +
                    "</html>", false);*/
            botEngineAnswer("Exists");
            chatView.updateCenter("<html>Hello and welcome to Gamez, your gaming chatbot!<br/>It's nice to have you back " + userInfo.getName() + ".<br/>How have you been?<br/><html>", true);
            //chatView.updateCenter("Hello and welcome to Gamez, your gaming chatbot!\nIt's nice to have you back " + userInfo.getName() + "! How have you been?", true);
        }

        try {
            chatBotClient.initConversation();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = chatView.getUserMessage();
        chatView.updateCenter(msg, false);
        botEngineAnswer(msg);
    }

    private void botEngineAnswer(String msg) {
        try {
            ArrayList<String> messages = response.getBotFulfilment(chatBotClient.sendMsg(msg));
            for (String answer: messages){
                System.out.println("[DEBUG] " + answer);
                chatView.updateCenter(answer,true);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String beautify(String msg){
        String[] aux = msg.split("[\n]");
        return "<html><html>";
    }

}
