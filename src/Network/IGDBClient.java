package Network;

import Model.igdb.config.*;
import Model.igdb.search.IGDBFieldSearch;
import Model.igdb.search.IGDBGameFilter;
import Model.igdb.search.IGDBGame;
import Model.igdb.search.IGDBPegi;
import Util.UserException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IGDBClient extends HttpClient {

    private final static int MAX_GAMES = 5;

    private final static String FIELD_PARAM = "fields";
    private final static String FILTER_PARAM = "filter";
    private final static String ORDER_PARAM = "order";
    private final static String LIMIT_PARAM = "limit";
    private final static String SEARCH_PARAM = "search";

    private final static String BEGIN_OP = "prefix";
    private final static String IN_ARRAY_OR_OP = "any";
    private final static String GREATER_E_OP = "gte";

    private final static String POPULAR_DESC = "popularity:desc";

    private final static int CAMERA_OPTION = 0;
    private final static int GAME_MODE_OPTION = 1;
    private final static int GENRE_OPTION = 2;
    private final static int PLATFORM_OPTION = 3;
    private final static int PEGI_OPTION = 4;
    private final static int KEYWORD_OPTION = 5;

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

    public ArrayList<IGDBGame> getRelatedGames(long gameId, int limit) throws Exception {

        //Get game field
        IGDBGameField gameField = igdbData.getGame();

        //Get url
        StringBuilder sb = new StringBuilder(igdbData.getBaseUrl());
        sb.append(gameField.getUrl());

        //Get fields
        IGDBGameFieldData fieldsData = gameField.getFields();
        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldsData.getRelated());

        //Add GET params
        sb.append(gameId);
        sb.append("?").append(getFieldsParam(fields));
        sb.append("&").append(ORDER_PARAM).append("=").append(POPULAR_DESC);

        //Create HTTP connection
        URL url = new URL(sb.toString());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set GET request
        con.setRequestMethod("GET");

        //Set headers
        setBasicHeaders(con);

        //Get GET response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        JSONArray response = getJSONArrayResponse(con);
        ArrayList<IGDBGame> games = new ArrayList<>();
        if(response.length() > 0) {
            JSONArray relatedGameIds = response.getJSONObject(0).getJSONArray(fieldsData.getRelated());
            for(int i = 0; i < Math.min(relatedGameIds.length(), limit); i++) {
                try {
                    IGDBGame game = getGameFromId(relatedGameIds.getLong(i));
                    if (game != null) {
                        games.add(game);
                    }
                } catch(Exception e) {
                    //Error adding game
                }
            }
        }

        return games;

    }

    private IGDBGame getGameFromId(long gameId) throws Exception {

        //Get game field
        IGDBGameField gameField = igdbData.getGame();

        //Get url
        StringBuilder sb = new StringBuilder(igdbData.getBaseUrl());
        sb.append(gameField.getUrl());

        //Get fields
        IGDBGameFieldData fieldsData = gameField.getFields();
        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldsData.getId());
        fields.add(fieldsData.getName());
        fields.add(fieldsData.getDescription());
        //fields.add(fieldsData.getRating());
        fields.add(fieldsData.getGenres());
        fields.add(fieldsData.getPlatforms());

        //Add GET params
        sb.append(gameId);
        sb.append("?").append(getFieldsParam(fields));

        //Create HTTP connection
        URL url = new URL(sb.toString());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set GET request
        con.setRequestMethod("GET");

        //Set headers
        setBasicHeaders(con);

        //Get GET response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        JSONArray response = getJSONArrayResponse(con);
        if(response.length() > 0) {
            return getGamesFromResponse(response, fieldsData).get(0);
        }

        return null;

    }

    public ArrayList<IGDBGame> getGamesFromFilter(IGDBGameFilter filter) throws Exception {

        //Get game field
        IGDBGameField gameField = igdbData.getGame();

        //Get url
        StringBuilder sb = new StringBuilder(igdbData.getBaseUrl());
        sb.append(gameField.getUrl());

        //Get fields
        IGDBGameFieldData fieldsData = gameField.getFields();
        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldsData.getId());
        fields.add(fieldsData.getName());
        fields.add(fieldsData.getDescription());
        //fields.add(fieldsData.getPegi());
        //fields.add(fieldsData.getRating());
        fields.add(fieldsData.getGenres());
        fields.add(fieldsData.getPlatforms());

        //Add GET params
        sb.append("?").append(getFieldsParam(fields));
        String filterParams = getParamsFromFilter(filter, fieldsData);
        if(filterParams.length() > 0) {
            sb.append("&").append(filterParams);
        }

        //Create HTTP connection
        URL url = new URL(sb.toString());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set GET request
        con.setRequestMethod("GET");

        //Set headers
        setBasicHeaders(con);

        //Get GET response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        JSONArray response = getJSONArrayResponse(con);
        return getGamesFromResponse(response, fieldsData);

    }

    private ArrayList<IGDBGame> getGamesFromResponse(JSONArray response, IGDBGameFieldData fieldsData) {

        ArrayList<IGDBGame> games = new ArrayList<>();
        for(int i = 0; i < response.length(); i++) {

            try {

                //Prepare struct
                JSONObject responseFrag = response.getJSONObject(i);
                IGDBGame search = new IGDBGame();

                //Add params
                search.setId(responseFrag.getLong(fieldsData.getId()));
                search.setName(responseFrag.getString(fieldsData.getName()));
                search.setDescription(responseFrag.getString(fieldsData.getDescription()));
                //search.setScore(responseFrag.getDouble(fieldsData.getRating()));

                JSONArray genres = responseFrag.getJSONArray(fieldsData.getGenres());
                for (int j = 0; j < genres.length(); j++) {
                    search.addGenre(getGenreName(genres.getLong(j)));
                }

                JSONArray platforms = responseFrag.getJSONArray(fieldsData.getPlatforms());
                for (int j = 0; j < platforms.length(); j++) {
                    search.addPlatform(getPlatformName(platforms.getLong(j)));
                }

                //Add search result
                games.add(search);

            } catch(Exception e) {}

        }

        return games;

    }

    private String getParamsFromFilter(IGDBGameFilter filter, IGDBGameFieldData fields) throws Exception {

        StringBuilder sb = new StringBuilder();

        //Check name
        if(filter.getName() != null) {
            sb.append(SEARCH_PARAM).append("=").append(filter.getName());
        }

        //Check rating
        /*if(filter.getRating() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getRating(), GREATER_E_OP, String.valueOf(filter.getRating())));
        }*/

        //Check age
        /*String pegi = getArrayFilter(IGDBPegi.getPegiFromAge(filter.getAge()), PEGI_OPTION);
        if(pegi.length() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getPegi(), IN_ARRAY_OR_OP, pegi));
        }*/

        //Check cameras
        String cameras = getArrayFilter(filter.getCameras(), CAMERA_OPTION);
        if(cameras.length() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getCameras(), IN_ARRAY_OR_OP, cameras));
        }

        //Check game modes
        String gameModes = getArrayFilter(filter.getGameModes(), GAME_MODE_OPTION);
        if(gameModes.length() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getGameModes(), IN_ARRAY_OR_OP, gameModes));
        }

        //Check genres
        String genres = getArrayFilter(filter.getGenres(), GENRE_OPTION);
        if(genres.length() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getGenres(), IN_ARRAY_OR_OP, genres));
        }

        //Check platforms
        String platforms = getArrayFilter(filter.getPlatforms(), PLATFORM_OPTION);
        if(platforms.length() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getPlatforms(), IN_ARRAY_OR_OP, platforms));
        }

        //Check keywords
        String keywords = getArrayFilter(filter.getKeywords(), KEYWORD_OPTION);
        if(keywords.length() > 0) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(getFilterParam(fields.getKeywords(), IN_ARRAY_OR_OP, keywords));
        }

        //Check max games
        if(sb.length() > 0) {
            sb.append("&");
        }
        sb.append(LIMIT_PARAM).append("=");
        if(filter.getMaxGames() > 0) {
            sb.append(filter.getMaxGames());
        } else {
            sb.append(MAX_GAMES);
        }

        return sb.toString();

    }

    private String getArrayFilter(ArrayList<String> elements, int option) throws Exception {

        //Check elements
        StringBuilder params = new StringBuilder();
        for(String element : elements) {

            //Get id
            long id = -1;
            switch (option) {
                case CAMERA_OPTION:
                    id = getCameraId(element);
                    break;
                case GAME_MODE_OPTION:
                    id = getGameModeId(element);
                    break;
                case GENRE_OPTION:
                    id = getGenreId(element);
                    break;
                case PLATFORM_OPTION:
                    id = getPlatformId(element);
                    break;
                case PEGI_OPTION:
                    id = Long.valueOf(element);
                    break;
                case KEYWORD_OPTION:
                    id = getKeywordId(element);
                    break;
            }

            //Check if is a valid id
            if (id != -1) {
                if (params.length() > 0) {
                    params.append(",");
                }
                params.append(id);
            }

        }

        return params.toString();

    }

    private long getKeywordId(String keyword) throws Exception {
        return getFieldId(keyword, igdbData.getKeyword());
    }

    private long getPlatformId(String platformName) throws Exception {
        return getFieldId(platformName, igdbData.getPlatform());
    }

    private long getGenreId(String genreName) throws Exception {
        return getFieldId(genreName, igdbData.getGenre());
    }

    private long getCameraId(String cameraName) throws Exception {
        return getFieldId(cameraName, igdbData.getCamera());
    }

    private long getGameModeId(String gameModeName) throws Exception {
        return getFieldId(gameModeName, igdbData.getGameMode());
    }

    private long getFieldId(String fieldName, IGDBField field) throws Exception {

        //Get url
        StringBuilder sb = new StringBuilder(igdbData.getBaseUrl());
        sb.append(field.getUrl());

        //Get fields
        IGDBFieldData fieldsData = field.getFields();
        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldsData.getId());
        fields.add(fieldsData.getName());

        //Add GET params
        sb.append("?").append(getFieldsParam(fields));
        sb.append("&").append(getFilterParam(fieldsData.getName(), BEGIN_OP, fieldName));

        //Create HTTP connection
        URL url = new URL(sb.toString());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set GET request
        con.setRequestMethod("GET");

        //Set headers
        setBasicHeaders(con);

        //Get GET response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        ArrayList<IGDBFieldSearch> searches = new ArrayList<>();
        JSONArray response = getJSONArrayResponse(con);
        for(int i = 0; i < response.length(); i++) {
            JSONObject responseFrag = response.getJSONObject(i);
            searches.add(new IGDBFieldSearch(responseFrag.getLong(fieldsData.getId()),
                    responseFrag.getString(fieldsData.getName())));
        }

        //Get most similar platform
        int diff = -1;
        long id = -1;
        for(IGDBFieldSearch search : searches) {
            int newDiff = search.getName().compareToIgnoreCase(fieldName);
            if (newDiff == 0) {
                return search.getId();
            } else if (id < 0 || Math.abs(newDiff) < Math.abs(diff)) {
                diff = newDiff;
                id = search.getId();
            }
        }

        return id;

    }

    private String getGenreName(long id) throws Exception {
        return getFieldName(id, igdbData.getGenre());
    }

    private String getPlatformName(long id) throws Exception {
        return getFieldName(id, igdbData.getPlatform());
    }

    private String getFieldName(long id, IGDBField field) throws Exception {

        //Get url
        StringBuilder sb = new StringBuilder(igdbData.getBaseUrl());
        sb.append(field.getUrl());

        //Get fields
        IGDBFieldData fieldsData = field.getFields();
        ArrayList<String> fields = new ArrayList<>();
        fields.add(fieldsData.getId());
        fields.add(fieldsData.getName());

        //Add GET params
        sb.append(id);
        sb.append("?").append(getFieldsParam(fields));

        //Create HTTP connection
        URL url = new URL(sb.toString());
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //Set GET request
        con.setRequestMethod("GET");

        //Set headers
        setBasicHeaders(con);

        //Get GET response
        if(!validateResponseCode(con.getResponseCode())) {
            throw new UserException("Invalid Response");
        }

        //Get JSON response
        JSONArray response = getJSONArrayResponse(con);
        if(response.length() > 0) {
            JSONObject responseFrag = response.getJSONObject(0);
            return responseFrag.getString(fieldsData.getName());
        }

        return "";

    }

    private String getFilterParam(String field, String operation, String value) {
        return String.format("%s[%s][%s]=%s", FILTER_PARAM, field, operation, value);
    }

    private String getFieldsParam(ArrayList<String> fields) {

        StringBuilder sb = new StringBuilder(FIELD_PARAM);
        sb.append("=");

        for(int i = 0; i < fields.size(); i++) {
            if(i != 0) {
                sb.append(",");
            }
            sb.append(fields.get(i));
        }

        return sb.toString();

    }

    private void setBasicHeaders(HttpsURLConnection con) {
        con.setRequestProperty("user-key", igdbData.getApiKey());
        con.setRequestProperty("accept", "application/json");
    }

}
