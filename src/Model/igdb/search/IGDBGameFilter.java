package Model.igdb.search;

import java.util.ArrayList;

public class IGDBGameFilter {

    private String name;
    private double rating;
    private long time;
    private ArrayList<String> cameras;
    private ArrayList<String> gameModes;
    private ArrayList<String> genres;
    private ArrayList<String> platforms;
    private int age;
    private int maxGames;

    public IGDBGameFilter(){
        rating = -1;
        time = -1;
        cameras = new ArrayList<>();
        gameModes = new ArrayList<>();
        genres = new ArrayList<>();
        platforms = new ArrayList<>();
        age = -1;
        maxGames = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ArrayList<String> getCameras() {
        return cameras;
    }

    public void addCamera(String camera) {
        if(!cameras.contains(camera)) {
            cameras.add(camera);
        }
    }

    public ArrayList<String> getGameModes() {
        return gameModes;
    }

    public void addGameMode(String gameMode) {
        if(!gameModes.contains(gameMode)) {
            gameModes.add(gameMode);
        }
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void addGenre(String genre) {
        if(!genres.contains(genre)) {
            genres.add(genre);
        }
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public void addPlatforms(String platform) {
        if(!platforms.contains(platform)) {
            platforms.add(platform);
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public void setMaxGames(int maxGames) {
        this.maxGames = maxGames;
    }

}
