package Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonManager {

    public JsonManager(){

    }

    /**
     * Loads and reads a Json file
     * @return Json file in JsonArray format
     * @throws FileNotFoundException In case the file is not found
     */
    public JsonArray readJson(String jsonFile) throws FileNotFoundException{
        JsonArray jsonArray;
        BufferedReader br = null;
        try {
            Gson gson = new Gson();
            FileReader fr = new FileReader(jsonFile);
            br = new BufferedReader(fr);
            jsonArray = gson.fromJson(br, JsonArray.class).getAsJsonArray();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
