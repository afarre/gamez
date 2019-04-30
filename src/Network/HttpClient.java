package Network;

import Model.APIData;
import Model.Trigger;
import Util.UserException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class HttpClient {

    public final static int MAX_QUERY = 256;

    //BotEngine URLs
    private final static String MSG_URL = "https://api.chatbot.com/query";

    //Instance
    private static HttpClient httpClient;

    //Attributes
    private APIData apiData;
    private String sessionId;

    //Private constructor to make it Singleton
    private HttpClient() {}

    private HttpClient(APIData apiData) {
        this.apiData = apiData;
        sessionId = UUID.randomUUID().toString();
    }

    //Instance getter
    public static HttpClient getInstance(APIData apiData) {
        if(httpClient == null) {
            httpClient = new HttpClient(apiData);
        }
        return httpClient;
    }

    public JSONObject initConversation() throws Exception {

        //Create HTTP connection
        URL url = new URL(MSG_URL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set POST request
        con.setRequestMethod("POST");

        //Set headers
        setBasicHeaders(con);

        //Send data
        sendPostData(con, preparePostData(welcomeSet()));

        //Get POST response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        return getJSONResponse(con);

    }

    public JSONObject sendMsg(String msg) throws Exception {

        //Check msg length
        if(msg == null || msg.isEmpty() || msg.length() > MAX_QUERY) {
            throw new UserException("Invalid Message");
        }

        //Create HTTP connection
        URL url = new URL(MSG_URL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set POST request
        con.setRequestMethod("POST");

        //Set headers
        setBasicHeaders(con);

        //Send data
        sendPostData(con, preparePostData(msgSet(msg)));

        //Get POST response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        return getJSONResponse(con);

    }


    private void setBasicHeaders(HttpsURLConnection con) {
        con.setRequestProperty("authorization", "Bearer " + apiData.getApiKey() + "");
        con.setRequestProperty("content-type", "application/json");
    }

    private HashMap<String, Object> welcomeSet() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        hashMap.put("storyId", apiData.getStoryId());
        hashMap.put("trigger", Trigger.WELCOME.getId());
        return hashMap;
    }

    private HashMap<String, Object> msgSet(String msg) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        hashMap.put("storyId", apiData.getStoryId());
        hashMap.put("query", msg);
        return hashMap;
    }

    private JSONObject preparePostData(HashMap<String, Object> params) throws JSONException {

        //Set JSON data
        JSONObject data = new JSONObject();
        for(HashMap.Entry<String, Object> entry : params.entrySet()) {
            data.put(entry.getKey(), entry.getValue());
        }

        return data;

    }

    private boolean validateResponseCode(int code) {
        return code != 400 && code != 401 && code != 404 && code != 500;
    }

    private void sendPostData(HttpsURLConnection con, JSONObject data) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data.toString());
        wr.flush();
        wr.close();
    }

    private JSONObject getJSONResponse(HttpsURLConnection con) throws IOException, JSONException {

        //Get response as String
        BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while((inputLine = buff.readLine()) != null) {
            response.append(inputLine);
        }
        buff.close();

        //Convert to JSON
        return new JSONObject(response.toString());

    }

}
