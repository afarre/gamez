import Controller.Controller;
import Model.APIData;
import Model.JsonManager;
import View.ChatView;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                APIData apiData = null;

                try {
                    File configFile = new File("data/API.json");
                    apiData = new Gson().fromJson(new FileReader(configFile), APIData.class);

                    if (apiData != null){
                        System.out.println(apiData.getApiKey());
                        System.out.println(apiData.getParam1());
                        System.out.println(apiData.getParam2());

                        ChatView chatView = new ChatView();
                        Controller chatController = new Controller(chatView, new JsonManager());
                        chatView.registerListeners(chatController);
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("El fichero \"config.json\" no ha sido encontrado.");
                }

            }
        });
    }
}
