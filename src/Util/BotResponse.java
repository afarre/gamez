package Util;

import Model.Form;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BotResponse {

    private Form form;

    public BotResponse() {}

    public ArrayList<String> getBotFulfilment(JSONObject msg) throws JSONException {

        ArrayList<String> messages = new ArrayList<>();

        //Get fulfilment
        JSONArray responses = (JSONArray) msg.getJSONObject("result").get("fulfillment");

        //Get text messages
        for(int i = 0; i < responses.length(); i++) {

            JSONObject obj = responses.getJSONObject(i);
            messages.add(obj.getString("message"));

            if(obj.getString("message").equals("Alright, let's go for it!") || obj.getString("message").equals("Very well, let's get the questions started!")){
                System.out.println("new form");
                form = new Form();
            } else if (obj.getString("message").startsWith("#")){
                parseResponse(obj.getString("message"));
                int aux = messages.size() - 1;
                StringBuilder strb = new StringBuilder(messages.get(aux));
                strb.delete(0,2);
                messages.remove(aux);
                messages.add(aux, strb.toString());
            }
        }
        return messages;
    }

    private void parseResponse(String msg) {
        String[] fields = msg.split(": ");

        switch (Integer.parseInt(String.valueOf(msg.charAt(1))) - 1){
            case 0:
                System.out.println(form);
                System.out.println(fields[1]);
                form.setConsole(fields[1]);
                break;
            case 1:
                form.setGenre(fields[1]);
                break;
            case 2:
                form.setCamera(fields[1]);
                break;
            case 3:
                form.setNumPlayers(Integer.parseInt(fields[1]));
                break;
            case 4:
                form.setRating(Double.parseDouble(fields[1]));
                break;
            case 5:
                form.setNumRecommendations(Integer.parseInt(fields[1]));
                System.out.println("Form:");
                System.out.println(form.getConsole() + "\n" + form.getGenre() + "\n" + form.getCamera() + "\n" + form.getNumPlayers() + "\n" + form.getRating() + "\n" + form.getNumRecommendations());
                break;
        }

        JsonElement element = new JsonParser().parse(new Gson().toJson(form));
        System.out.println("element: " + element);
    }
}
