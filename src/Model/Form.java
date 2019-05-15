package Model;

public class Form {
    private String console;
    private String genre;
    private String camera;
    private int numPlayers;
    private double rating;
    private int numRecommendations;

    public Form(){ }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumRecommendations() {
        return numRecommendations;
    }

    public void setNumRecommendations(int numRecommendations) {
        this.numRecommendations = numRecommendations;
    }
}
