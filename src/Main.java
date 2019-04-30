import Controller.Controller;
import Model.APIData;
import Model.JsonManager;
import Network.HttpClient;
import View.ChatView;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        try {

            File configFile = new File("data/API.json");
            APIData apiData = new Gson().fromJson(new FileReader(configFile), APIData.class);
            HttpClient httpClient = HttpClient.getInstance(apiData);

            SwingUtilities.invokeLater(() -> {
                ChatView chatView = new ChatView();
                Controller chatController = new Controller(chatView, new JsonManager(), httpClient);
                chatView.registerListeners(chatController);
            });

        } catch(FileNotFoundException e) {
            System.err.println("El fichero \"config.json\" no ha sido encontrado.");
        }

    }

}
