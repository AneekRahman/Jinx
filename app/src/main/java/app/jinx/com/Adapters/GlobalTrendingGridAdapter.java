package app.jinx.com.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import app.jinx.com.Classes.GlobalTrending;
import app.jinx.com.Classes.MyUtils;
import app.jinx.com.R;

public class GlobalTrendingGridAdapter extends BaseAdapter {

    // Given trending posts
    private Context mContext;
    private List<GlobalTrending> mTrendingHashes;

    // My Utils to format numbers
    private MyUtils mMyUtils = new MyUtils();

    // Class instance constructor
    public GlobalTrendingGridAdapter(Context c, List<GlobalTrending> trendings) {
        mContext = c;
        this.mTrendingHashes = trendings;
    }

    // Needed method
    @Override
    public int getCount() {
        return mTrendingHashes.size();
    }
    // Needed method
    @Override
    public Object getItem(int position) {
        return mTrendingHashes.get(position);
    }
    // Needed method
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflating the trending grid layout for each of the grids
        convertView = LayoutInflater.from(mContext).inflate(R.layout.global_trending_layout, null);
        GlobalTrending trending = mTrendingHashes.get(position);

        // Connecting the grid layouts view to code
        TextView trendingCount = (TextView) convertView.findViewById(R.id.global_trending_count);
        TextView trendingHash = (TextView) convertView.findViewById(R.id.global_trending_hash);
        TextView trendingHashCount = (TextView) convertView.findViewById(R.id.global_trending_hash_count);

        // Setting the trending hash and count
        trendingCount.setText(String.valueOf(position + 1) + ".");
        trendingHash.setText(trending.getHashTag());
        trendingHashCount.setText(String.valueOf(mMyUtils.formatNumbers(trending.getHashCount())));

        return convertView;

    }


}