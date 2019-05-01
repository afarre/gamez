package Network;

import Model.ChatBotData;
import Model.IGDBData;
import Util.UserException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class IGDBClient extends HttpClient {

    //BotEngine URLs
    private final static String GAMES_URL = "https://api-v3.igdb.com/games/";

    //Instance
    private static IGDBClient igdbClient;

    //Attributes
    private IGDBData igdbData;

    //Private constructor to make it Singleton
    private IGDBClient() {}

    private IGDBClient(IGDBData igdbData) {
        this.igdbData = igdbData;
    }

    //Instance getter
    public static IGDBClient getInstance(IGDBData igdbData) {
        if(igdbClient == null) {
            igdbClient = new IGDBClient(igdbData);
        }
        return igdbClient;
    }

    public JSONArray listGames() throws Exception {

        //Create HTTP connection
        URL url = new URL(GAMES_URL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set POST request
        con.setRequestMethod("GET");

        //Set headers
        setBasicHeaders(con);

        //Get GET response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        return getJSONArrayResponse(con);

    }

    private void setBasicHeaders(HttpsURLConnection con) {
        con.setRequestProperty("user-key", igdbData.getApiKey());
    }

}
