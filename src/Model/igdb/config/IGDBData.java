package Model.igdb.config;

public class IGDBData {

    private String apiKey;
    private String baseUrl;
    private IGDBGameField game;
    private IGDBField platform;
    private IGDBField genre;
    private IGDBField camera;
    private IGDBField gameMode;

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public IGDBGameField getGame() {
        return game;
    }

    public IGDBField getPlatform() {
        return platform;
    }

    public IGDBField getGenre() {
        return genre;
    }

    public IGDBField getCamera() {
        return camera;
    }

    public IGDBField getGameMode() {
        return gameMode;
    }

}
