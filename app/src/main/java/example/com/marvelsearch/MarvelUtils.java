package example.com.marvelsearch;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MarvelUtils {
    public static final String EXTRA_SEARCH_RESULT = "MarvelUtils.SearchResult";

    final static String MARVEL_SEARCH_BASE_URL = "https://gateway.marvel.com:443/v1/public/characters";
    final static String MARVEL_SEARCH_CHAR_PARAM = "name";
    final static String MARVEL_SEARCH_API_PARAM = "apikey";
    final static String MARVEL_SEARCH_HASH_PARAM = "hash";
    final static String MARVEL_SEARCH_TS_PARAM = "ts";

    final static String PUBLIC_API_KEY = "4c5f3e062d637c9e37a168fb4dc5b5d3";
    final static String PRIVATE_API_KEY = "19623756cd9941dd74babe40e02d8d1f0a44a496";

    public static class SearchResult implements Serializable {
        public String name;
        public String description;
        public String imageURL;
    }

    public static String buildMarvelSearchURL(String character_name) {
        long ts = System.currentTimeMillis();
        String hashString = ts + PRIVATE_API_KEY + PUBLIC_API_KEY;
        String hash = new String(Hex.encodeHex(DigestUtils.md5(hashString)));

        Uri.Builder builder = Uri.parse(MARVEL_SEARCH_BASE_URL).buildUpon();

        builder.appendQueryParameter(MARVEL_SEARCH_CHAR_PARAM, character_name);
        builder.appendQueryParameter(MARVEL_SEARCH_TS_PARAM, String.valueOf(ts));
        builder.appendQueryParameter(MARVEL_SEARCH_API_PARAM, PUBLIC_API_KEY);
        builder.appendQueryParameter(MARVEL_SEARCH_HASH_PARAM, hash);

        Log.d("MU - buildMarvelSearchU", builder.build().toString());
        return builder.build().toString();
    }

    public static ArrayList<SearchResult> parseSearchResultsJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONObject("data").getJSONArray("results");

            ArrayList<SearchResult> searchResultsList = new ArrayList<SearchResult>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                SearchResult result = new SearchResult();
                JSONObject resultItem = searchResultsItems.getJSONObject(i);
                result.name = resultItem.getString("name");
                result.description = resultItem.getString("description");
                JSONObject thumbnail = resultItem.getJSONObject("thumbnail");
                result.imageURL = thumbnail.getString("path");
                result.imageURL = result.imageURL + "/standard_amazing." + thumbnail.getString("extension");
                searchResultsList.add(result);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }
}
