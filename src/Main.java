import Controller.Controller;
import Model.ChatBotData;
import Model.IGDBData;
import Model.JsonManager;
import Network.ChatBotClient;
import Network.IGDBClient;
import View.ChatView;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                JSONArray response = igdbClient.listGames();
                System.out.println(response.toString());
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
