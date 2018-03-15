package example.com.marvelsearch;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import example.com.marvelsearch.NetworkUtils;

import java.io.IOException;

public class MarvelSearchLoader extends AsyncTaskLoader<String> {
    private final static String TAG = MarvelSearchLoader.class.getSimpleName();

    private String mSearchResultsJSON;
    private String mMarvelSearchURL;

    MarvelSearchLoader(Context context, String url) {
        super(context);
        mMarvelSearchURL = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMarvelSearchURL != null) {
            if (mSearchResultsJSON != null) {
                Log.d(TAG, "loader returning cached results");
                deliverResult(mSearchResultsJSON);
            } else {
                forceLoad();
            }
        }
    }

    @Override
    public String loadInBackground() {
        if (mMarvelSearchURL != null) {
            Log.d(TAG, "loading results from Marvel with URL: " + mMarvelSearchURL);
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHTTPGet(mMarvelSearchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        } else {
            return null;
        }
    }

    @Override
    public void deliverResult(String data) {
        mSearchResultsJSON = data;
        super.deliverResult(data);
    }
}
