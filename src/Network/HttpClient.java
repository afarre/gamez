package Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public abstract class HttpClient {

    public boolean validateResponseCode(int code) {
        return code != 400 && code != 401 && code != 404 && code != 500;
    }

    public JSONObject preparePostData(HashMap<String, String> params) throws JSONException {

        //Set JSON data
        JSONObject data = new JSONObject();
        for(HashMap.Entry<String, String> entry : params.entrySet()) {
            data.put(entry.getKey(), entry.getValue());
        }

        return data;

    }

    public void sendPostData(HttpsURLConnection con, JSONObject data) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data.toString());
        wr.flush();
        wr.close();
    }

    public JSONObject getJSONResponse(HttpsURLConnection con) throws IOException, JSONException {

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

    public JSONArray getJSONArrayResponse(HttpsURLConnection con) throws IOException, JSONException {

        //Get response as String
        BufferedReader buff = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while((inputLine = buff.readLine()) != null) {
            response.append(inputLine);
        }
        buff.close();

        //Convert to JSON
        return new JSONArray(response.toString());

    }

}
