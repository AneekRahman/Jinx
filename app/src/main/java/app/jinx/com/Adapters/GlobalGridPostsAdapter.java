package app.jinx.com.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

import app.jinx.com.Classes.GlobalPost;
import app.jinx.com.R;

public class GlobalGridPostsAdapter extends BaseAdapter {

    // Given global posts
    private Context mContext;
    private List<GlobalPost> mPosts;

    // Class instance constructor
    public GlobalGridPostsAdapter(Context c, List<GlobalPost> posts) {
        mContext = c;
        this.mPosts = posts;
    }

    // Needed method
    @Override
    public int getCount() {
        return mPosts.size();
    }
    // Needed method
    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }
    // Needed method
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflating the global grid layout for each of the girds
        convertView = LayoutInflater.from(mContext).inflate(R.layout.global_grid_post_layout, null);

        // Connecting the grid layouts view to code
        ImageView imageView = (ImageView) convertView.findViewById(R.id.global_post_preview_imageview);
        RelativeLayout mainHolder = (RelativeLayout) convertView.findViewById(R.id.global_grid_main_holder);

        // Giving each grid a fixed size to fit 3 in each row
        setPostMainHolderSize(mainHolder);

        // Setting the global post gif
        Glide.with(mContext)
                .load("https://media.giphy.com/media/5UCieYmJFW2rzcHkab/giphy.gif")
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);

        return convertView;

    }

    // Grid size setter method
    private void setPostMainHolderSize(RelativeLayout v){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixelsFrom24Dp = (int) (24 * scale + 0.5f);
        int pixelsFrom40Dp = (int) (40 * scale + 0.5f);

        int adjustedWidthPerIV = ( displayWidth - pixelsFrom24Dp ) / 3;
        int adjustedHeightPerIV = adjustedWidthPerIV + pixelsFrom40Dp;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(adjustedWidthPerIV, adjustedHeightPerIV);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        v.setLayoutParams(params);

    }


}