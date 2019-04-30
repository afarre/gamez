package Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BotResponse {

    private JSONObject msg;

    public BotResponse(JSONObject msg) {
        this.msg = msg;
    }

    public String getBotFulfilment() throws JSONException {

        //Get fulfilment
        JSONArray responses = (JSONArray) msg.getJSONObject("result").get("fulfillment");

        //Get text messages
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < responses.length(); i++) {
            JSONObject obj = responses.getJSONObject(i);
            sb.append(obj.getString("message"));
            sb.append(System.lineSeparator());
        }

        return sb.toString();

    }

}
