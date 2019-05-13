package Model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class UserInfo {
    private String name;
    private ArrayList<JsonObject> consoles;
    private ArrayList<JsonObject> favGenres;
    private ArrayList<JsonObject> favGames;

    public UserInfo(String name, ArrayList<JsonObject> consoles, ArrayList<JsonObject> favGames, ArrayList<JsonObject> favGenres){
        this.name = name;
        this.consoles = consoles;
        this.favGames = favGames;
        this.favGenres = favGenres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<JsonObject> getConsoles() {
        return consoles;
    }

    public void setConsoles(ArrayList<JsonObject> consoles) {
        this.consoles = consoles;
    }

    public ArrayList<JsonObject> getFavGenres() {
        return favGenres;
    }

    public void setFavGenres(ArrayList<JsonObject> favGenres) {
        this.favGenres = favGenres;
    }

    public ArrayList<JsonObject> getFavGames() {
        return favGames;
    }

    public void setFavGames(ArrayList<JsonObject> favGames) {
        this.favGames = favGames;
    }
}
