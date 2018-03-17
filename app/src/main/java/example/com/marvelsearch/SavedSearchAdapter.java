package example.com.marvelsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/17/2018.
 */

public class SavedSearchAdapter extends RecyclerView.Adapter<SavedSearchAdapter.SearchItemViewHolder> {
    private ArrayList<String> mSearchItems;
    private OnSavedSearchItemClickListener mSearchItemClickListener;
    private Context mContext;

    public interface OnSavedSearchItemClickListener {
        void onSavedSearchItemClick(String location);
    }

    public SavedSearchAdapter(Context context, OnSavedSearchItemClickListener clickListener) {
        mContext = context;
        mSearchItemClickListener = clickListener;
    }

    public void updateSearchItems(ArrayList<String> searchItems) {
        mSearchItems = searchItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSearchItems != null) {
            return mSearchItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public SavedSearchAdapter.SearchItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.search_item, parent, false);
        return new SavedSearchAdapter.SearchItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SavedSearchAdapter.SearchItemViewHolder holder, int position) {
        holder.bind(mSearchItems.get(position));
    }

    class SearchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mSearchNameTV;

        public SearchItemViewHolder(View itemView) {
            super(itemView);
            mSearchNameTV = itemView.findViewById(R.id.tv_search_name);
            itemView.setOnClickListener(this);
        }

        public void bind(String query) {
            mSearchNameTV.setText(query);
        }

        @Override
        public void onClick(View v) {
            String query = mSearchItems.get(getAdapterPosition());
            mSearchItemClickListener.onSavedSearchItemClick(query);
        }
    }
}
