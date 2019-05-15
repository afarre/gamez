import Controller.Controller;
import Model.ChatBotData;
import Model.igdb.config.IGDBData;
import Model.JsonManager;
import Network.ChatBotClient;
import Network.IGDBClient;
import View.ChatView;
import com.google.gson.Gson;

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
            try {
                //JSONArray response = igdbClient.listGames();
                //long id = igdbClient.getGameModeId("multi");
                //System.out.println(id);
            } catch(Exception e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> {
                ChatView chatView = new ChatView();
                Controller chatController = new Controller(chatView, new JsonManager(), chatBotClient);
                chatView.registerListeners(chatController);
            });

        } catch(FileNotFoundException e) {
            System.err.println("El fichero \"config.json\" no ha sido encontrado.");
        }

    }

}
