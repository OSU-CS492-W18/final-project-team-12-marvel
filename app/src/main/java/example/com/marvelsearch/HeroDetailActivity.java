package example.com.marvelsearch;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class HeroDetailActivity extends AppCompatActivity {

    private TextView mTVSearchResultName;
    private TextView mTVSearchResultDescription;
    private ImageView mIVPicture;

    private SQLiteDatabase mDB;

    private MarvelUtils.SearchResult mSearchResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);

        mTVSearchResultName = findViewById(R.id.tv_name);
        mTVSearchResultDescription = findViewById(R.id.tv_description);
        mIVPicture = findViewById(R.id.iv_image);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MarvelUtils.EXTRA_SEARCH_RESULT)) {
            mSearchResult = (MarvelUtils.SearchResult) intent.getSerializableExtra(MarvelUtils.EXTRA_SEARCH_RESULT);
            mTVSearchResultName.setText(mSearchResult.name);
            mTVSearchResultDescription.setText(mSearchResult.description);
            Glide.with(mIVPicture.getContext())
                    .load(mSearchResult.imageURL)
                    .into(mIVPicture);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_result_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareRepo() {
        if (mSearchResult != null) {
            String shareText = getString(R.string.share_text_prefix) + ": " +
                    mSearchResult.name + ", " + mSearchResult.description;

            ShareCompat.IntentBuilder.from(this)
                    .setChooserTitle(R.string.share_chooser_title)
                    .setType("text/plain")
                    .setText(shareText)
                    .startChooser();
        }
    }

}
