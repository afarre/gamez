package Controller;

import Model.chatbot.Trigger;
import Network.IGDBClient;
import View.ChatView;
import Model.UserInfo;
import Network.ChatBotClient;
import Util.BotResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements ActionListener {

    private ChatView chatView;
    private ChatBotClient chatBotClient;
    private IGDBClient igdbClient;
    private UserInfo userInfo;
    private BotResponse response;

    public Controller(ChatView chatView, ChatBotClient chatBotClient, IGDBClient igdbClient, UserInfo userInfo) {

        //Get data
        this.chatView = chatView;
        this.chatBotClient = chatBotClient;
        this.igdbClient = igdbClient;
        this.userInfo = userInfo;
        response = new BotResponse(userInfo, igdbClient);

        //Set window close event
        this.chatView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try(Writer writer = new FileWriter("data/user.json")) {
                    Gson gson = new GsonBuilder().create();
                    gson.toJson(userInfo, writer);
                } catch(Exception e) {}
                System.exit(0);
            }
        });

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
        getBotAnswer(msg);
    }

    public void startChat() throws Exception {

        //Get init message
        showMessages(response.getBotFulfilment(chatBotClient.initConversation()));

        //Send user status
        HashMap<String, String> hashMap = new HashMap<>();
        if(!userInfo.getName().isEmpty()) {
            hashMap.put("userName", userInfo.getName());
            showMessages(response.getBotFulfilment(chatBotClient.goToInteraction(Trigger.EXIST_USER.getId(), hashMap)));
        } else {
            showMessages(response.getBotFulfilment(chatBotClient.goToInteraction(Trigger.NOT_EXIST_USER.getId(), hashMap)));
        }

    }

    private void getBotAnswer(String msg) {
        try {
            showMessages(response.getBotFulfilment(chatBotClient.sendMsg(msg)));
            if(response.hasExit()) {
                try(Writer writer = new FileWriter("data/user.json")) {
                    Gson gson = new GsonBuilder().create();
                    gson.toJson(userInfo, writer);
                } catch(Exception e) {}
                System.exit(0);
            }
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }

    private void showMessages(ArrayList<String> msgs) {
        for(String msg : msgs) {
            chatView.updateCenter(msg, true);
        }
    }

}
