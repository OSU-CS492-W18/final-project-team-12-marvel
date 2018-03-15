package example.com.marvelsearch;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MarvelUtils {
    public static final String EXTRA_SEARCH_RESULT = "MarvelUtils.SearchResult";

    final static String MARVEL_SEARCH_BASE_URL = "https://api.github.com/search/repositories";
    final static String MARVEL_SEARCH_QUERY_PARAM = "q";
    final static String MARVEL_SEARCH_SORT_PARAM = "sort";

    public static class SearchResult implements Serializable {
        public String fullName;
        public String description;
        public String htmlURL;
        public int stars;
    }

    public static String buildMarvelSearchURL(String searchQuery, String sort, String language,
                                              String user, boolean searchInName, boolean searchInDescription,
                                              boolean searchInReadme) {

        Uri.Builder builder = Uri.parse(MARVEL_SEARCH_BASE_URL).buildUpon();

        if (!TextUtils.isEmpty(sort)) {
            builder.appendQueryParameter(MARVEL_SEARCH_SORT_PARAM, sort);
        }

        String queryValue = new String(searchQuery);
        if (!TextUtils.isEmpty(language)) {
            queryValue += " language:" + language;
        }
        if (!TextUtils.isEmpty(user)) {
            queryValue += " user:" + user;
        }

        ArrayList<String> searchInList = new ArrayList<>();
        if (searchInName) {
            searchInList.add("name");
        }
        if (searchInDescription) {
            searchInList.add("description");
        }
        if (searchInReadme) {
            searchInList.add("readme");
        }
        if (!searchInList.isEmpty()) {
            queryValue += " in:" + TextUtils.join(",", searchInList);
        }

        builder.appendQueryParameter(MARVEL_SEARCH_QUERY_PARAM, queryValue);

        return builder.build().toString();
    }

    public static ArrayList<SearchResult> parseSearchResultsJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("items");

            ArrayList<SearchResult> searchResultsList = new ArrayList<SearchResult>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                SearchResult result = new SearchResult();
                JSONObject resultItem = searchResultsItems.getJSONObject(i);
                result.fullName = resultItem.getString("full_name");
                result.description = resultItem.getString("description");
                result.htmlURL = resultItem.getString("html_url");
                result.stars = resultItem.getInt("stargazers_count");
                searchResultsList.add(result);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }
}
