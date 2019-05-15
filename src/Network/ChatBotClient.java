package Network;

import Model.chatbot.ChatBotData;
import Model.chatbot.Trigger;
import Util.UserException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatBotClient extends HttpClient {

    public final static int MAX_QUERY = 256;

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
        URL url = new URL(chatBotData.getMsgUrl());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set POST request
        con.setRequestMethod("POST");

        //Set headers
        setBasicHeaders(con);

        //Send data
        sendPostData(con, preparePostData(triggerSet(Trigger.WELCOME.getId(), "")));

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
        URL url = new URL(chatBotData.getMsgUrl());
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

    public JSONObject goToInteraction(String interaction, HashMap<String, String> parameters) throws Exception {

        //Check parameters
        if(parameters == null) {
            throw new UserException("Invalid Message");
        }

        //Create HTTP connection
        URL url = new URL(chatBotData.getMsgUrl());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set POST request
        con.setRequestMethod("POST");

        //Set headers
        setBasicHeaders(con);

        //Send data
        sendPostData(con, preparePostData(triggerSet(interaction, paramsToString(parameters))));

        //Get POST response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        return getJSONResponse(con);

    }

    private String paramsToString(HashMap<String, String> params) throws JSONException {

        JSONObject object = new JSONObject();

        //Navigate struct
        for(HashMap.Entry<String, String> entry : params.entrySet()) {
            object.put(entry.getKey(), entry.getValue());
        }

        return object.toString();

    }

    private void setBasicHeaders(HttpsURLConnection con) {
        con.setRequestProperty("authorization", "Bearer " + chatBotData.getApiKey() + "");
        con.setRequestProperty("content-type", "application/json");
    }

    private HashMap<String, String> triggerSet(String triggerId, String stringParams) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        hashMap.put("storyId", chatBotData.getStoryId());
        hashMap.put("trigger", triggerId);
        if(!stringParams.isEmpty()) {
            hashMap.put("parameters", stringParams);
        }
        return hashMap;
    }

    private HashMap<String, String> msgSet(String msg) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sessionId", sessionId);
        hashMap.put("storyId", chatBotData.getStoryId());
        hashMap.put("query", msg);
        return hashMap;
    }

}
