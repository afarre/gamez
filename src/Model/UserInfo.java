package Model;

import Model.igdb.search.IGDBGame;

import java.util.ArrayList;

public class UserInfo {

    private String name;
    private int age;
    private ArrayList<String> consoles;
    private ArrayList<String> favGenres;
    private ArrayList<IGDBGame> favGames;

    public UserInfo(){
        name = "";
        age = -1;
        consoles = new ArrayList<>();
        favGenres = new ArrayList<>();
        favGames = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<String> getConsoles() {
        return consoles;
    }

    public void addConsole(String console) {
        if(!consoles.contains(console)) {
            consoles.add(console);
        }
    }

    public ArrayList<String> getFavGenres() {
        return favGenres;
    }

    public void addFavGenre(String favGenre) {
        if(!favGenres.contains(favGenre)) {
            favGenres.add(favGenre);
        }
    }

    public ArrayList<IGDBGame> getFavGames() {
        return favGames;
    }

    public void addFavGames(IGDBGame favGame) {
        if(!favGames.contains(favGame)) {
            favGames.add(favGame);
        }
    }

}
