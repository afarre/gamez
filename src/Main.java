import Controller.Controller;
import Model.UserInfo;
import Model.chatbot.ChatBotData;
import Model.igdb.config.IGDBData;
import Model.JsonManager;
import Model.igdb.search.IGDBGame;
import Network.ChatBotClient;
import Network.IGDBClient;
import View.ChatView;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

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

            try {
                /*IGDBGameFilter filter = new IGDBGameFilter();
                filter.setName("sekiro");
                filter.setAge(21);
                filter.setMaxGames(20);
                filter.setRating(50);
                filter.addCamera("third");
                filter.addGameMode("single");
                filter.addGenre("adventure");
                filter.addKeyword("japan");
                filter.addPlatform("ps4");
                ArrayList<IGDBGame> games = igdbClient.getGamesFromFilter(filter);*/
                ArrayList<IGDBGame> games = igdbClient.getRelatedGames(userInfo.getFavGames().get(0).getId(), 5);
                System.out.println(games);
            } catch(Exception e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> {
                ChatView chatView = new ChatView();
                Controller chatController = new Controller(chatView, new JsonManager(), chatBotClient, userInfo);
                chatView.registerListeners(chatController);
            });

        } catch(FileNotFoundException e) {
            System.err.println("El fichero \"config.json\" no ha sido encontrado.");
        }

    }

}
