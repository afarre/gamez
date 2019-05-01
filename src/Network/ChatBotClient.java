package Network;

import Model.ChatBotData;
import Model.Trigger;
import Util.UserException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class ChatBotClient extends HttpClient {

    public final static int MAX_QUERY = 256;

    //BotEngine URLs
    private final static String MSG_URL = "https://api.chatbot.com/query";

    //Instance
    private static ChatBotClient chatBotClient;

    //Attributes
    private ChatBotData chatBotData;
    private String sessionId;

    //Private constructor to make it Singleton
    private ChatBotClient() {}

    private ChatBotClient(ChatBotData chatBotData) {
        this.chatBotData = chatBotData;
        sessionId = UUID.randomUUID().toString();
    }

    //Instance getter
    public static ChatBotClient getInstance(ChatBotData chatBotData) {
        if(chatBotClient == null) {
            chatBotClient = new ChatBotClient(chatBotData);
        }
        return chatBotClient;
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
        con.setRequestProperty("authorization", "Bearer " + chatBotData.getApiKey() + "");
        con.setRequestProperty("content-type", "application/json");
    }

    private HashMap<String, Object> welcomeSet() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        hashMap.put("storyId", chatBotData.getStoryId());
        hashMap.put("trigger", Trigger.WELCOME.getId());
        return hashMap;
    }

    private HashMap<String, Object> msgSet(String msg) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        hashMap.put("storyId", chatBotData.getStoryId());
        hashMap.put("query", msg);
        return hashMap;
    }

}
