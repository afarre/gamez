package Model.igdb.search;

import java.util.ArrayList;

public class IGDBGameSearch {

    private long id;
    private String name;
    private String description;
    private ArrayList<Long> relatedGames;

    public IGDBGameSearch() {
        relatedGames = new ArrayList<>();
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

    public ArrayList<Long> getRelatedGames() {
        return relatedGames;
    }

    public void addRelatedGame(long relatedGame) {
        if(!relatedGames.contains(relatedGame)) {
            relatedGames.add(relatedGame);
        }
    }

}
