import Controller.Controller;
import Model.UserInfo;
import Model.chatbot.ChatBotData;
import Model.igdb.config.IGDBData;
import Network.ChatBotClient;
import Network.IGDBClient;
import View.ChatView;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        try {

            //Prepare connection to ChatBot API
            File configFile = new File("data/chatbot.json");
            ChatBotData chatBotData = new Gson().fromJson(new FileReader(configFile), ChatBotData.class);
            ChatBotClient chatBotClient = ChatBotClient.getInstance(chatBotData);

            //Prepare connection to IGDB API
            configFile = new File("data/igdb.json");
            IGDBData igdbData = new Gson().fromJson(new FileReader(configFile), IGDBData.class);
            IGDBClient igdbClient = IGDBClient.getInstance(igdbData);

            //Get user data
            configFile = new File("data/user.json");
            UserInfo userInfo = new Gson().fromJson(new FileReader(configFile), UserInfo.class);

            SwingUtilities.invokeLater(() -> {
                ChatView chatView = new ChatView();
                Controller chatController = new Controller(chatView, chatBotClient, igdbClient, userInfo);
                chatView.registerListeners(chatController);
                try {
                    chatController.startChat();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });

        } catch(NullPointerException | FileNotFoundException e) {
            System.err.println("No existe algún fichero");
            e.printStackTrace();
        } catch(JsonIOException e) {
            System.err.println("El formato de algún fichero es incorrecto");
            e.printStackTrace();
        }

    }

}
