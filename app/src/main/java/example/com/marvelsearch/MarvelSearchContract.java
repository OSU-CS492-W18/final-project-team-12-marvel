package example.com.marvelsearch;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 3/17/2018.
 */

public class MarvelSearchContract {

    private MarvelSearchContract() {}

    public static class SavedSearches implements BaseColumns {
        public static final String TABLE_NAME = "savedSearches";
        public static final String COLUMN_SEARCH_TERM = "searchTerm";
    }
}
