package Model.igdb.search;

import java.util.ArrayList;

public class IGDBGame {

    private long id;
    private String name;
    private String description;
    private double score;
    private ArrayList<String> genres;
    private ArrayList<String> platforms;

    public IGDBGame() {
        genres = new ArrayList<>();
        platforms = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public void addPlatform(String platform) {
        if(!platforms.contains(platform)) {
            platforms.add(platform);
        }
    }

}
