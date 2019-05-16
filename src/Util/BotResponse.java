package Util;

import Model.chatbot.Form;
import Model.UserInfo;
import Model.igdb.config.IGDBField;
import Model.igdb.search.IGDBGame;
import Model.igdb.search.IGDBGameFilter;
import Network.IGDBClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class BotResponse {

    private Form form;
    private UserInfo userInfo;
    private IGDBClient igdbClient;

    public BotResponse(UserInfo userInfo, IGDBClient igdbClient) {
        this.userInfo = userInfo;
        this.igdbClient = igdbClient;
        form = new Form();
    }

    public ArrayList<String> getBotFulfilment(JSONObject msg) throws Exception {

        ArrayList<String> messages = new ArrayList<>();

        //Get fulfilment
        JSONArray responses = (JSONArray) msg.getJSONObject("result").get("fulfillment");
        JSONObject interaction = msg.getJSONObject("result").getJSONObject("interaction");
        JSONObject params = msg.getJSONObject("result").getJSONObject("parameters");

        //Get text messages
        for(int i = 0; i < responses.length(); i++) {

            JSONObject obj = responses.getJSONObject(i);

            //Check action
            String action = interaction.getString("action");
            if(action.equals("quit_action")) {
                //TODO: Quit
            } else if(action.equals("confirm_form")) {
                messages.addAll(makeGamesMsg(igdbClient.getGamesFromFilter(makeFilter())));
            }

            //Get bot message
            if(obj.has("message")) {
                String botMsg = obj.getString("message");
                messages.add(botMsg);
            }

            //Check if exist args
            if(params.length() == 0) {
                continue;
            }

            //Check args
            JSONArray argKeys = params.names();
            for(int j = 0; j < argKeys.length(); j++) {

                String key = argKeys.getString(j);
                if(key.equals("userName")) {
                    userInfo.setName(params.getString(key));
                } else if(key.equals("userAge")) {
                    userInfo.setAge(params.getInt(key));
                } else if(key.equals("platform")) {
                    form.setConsole(params.getString(key));
                } else if(key.equals("genre")) {
                    form.setGenre(params.getString(key));
                } else if(key.equals("camera")) {
                    form.setCamera(params.getString(key));
                } else if(key.equals("numPlayers")) {
                    if(!params.isNull(key)) {
                        try {
                            form.setNumPlayers(Integer.valueOf(params.getString(key)) == 1 ? "singleplayer" : "multiplayer");
                        } catch(NumberFormatException e) {
                            form.setNumPlayers("multiplayer");
                        }
                    } else {
                        form.setNumPlayers("multiplayer");
                    }
                } else if(key.equals("rating")) {
                    if(!params.isNull(key)) {
                        try {
                            form.setRating(Double.valueOf(params.getString(key)));
                        } catch(NumberFormatException e) {
                            form.setRating(0);
                        }
                    } else {
                        form.setRating(0);
                    }
                } else if(key.equals("numRecommendations")) {
                    if(!params.isNull(key)) {
                        try {
                            form.setNumRecommendations(Integer.valueOf(params.getString(key)));
                        } catch(NumberFormatException e) {
                            form.setNumRecommendations(5);
                        }
                    } else {
                        form.setNumRecommendations(-1);
                    }
                }

            }

        }

        return messages;

    }

    private ArrayList<String> makeGamesMsg(ArrayList<IGDBGame> games) {

        ArrayList<String> msgs = new ArrayList<>();
        for(IGDBGame game : games) {
            StringBuilder sb = new StringBuilder();
            sb.append(game.getName());
            msgs.add(sb.toString());
        }

        if(msgs.size() == 0) {
            msgs.add("No games where found");
        }

        return msgs;

    }

    private IGDBGameFilter makeFilter() {

        IGDBGameFilter filter = new IGDBGameFilter();
        filter.addPlatform(form.getConsole());
        filter.addGenre(form.getGenre());
        filter.addCamera(form.getCamera());
        filter.addGameMode(form.getNumPlayers());
        filter.setRating(form.getRating());
        filter.setMaxGames(form.getNumRecommendations());

        return filter;

    }

}
