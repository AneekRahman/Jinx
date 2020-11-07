package app.jinx.com;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import app.jinx.com.Adapters.ProfileUserPostsAdapter;
import app.jinx.com.Classes.ExpandableHeightGridView;
import app.jinx.com.Classes.ProfileUserGridPost;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    // View declaration
    private ImageView mUserDpImageView;
    private ExpandableHeightGridView mGridView;

    // Profile grid posts list and adapter
    private ProfileUserPostsAdapter mAdapter;
    private List<ProfileUserGridPost> mPosts = new ArrayList<>();

    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        // Connecting view to code
        mUserDpImageView = (ImageView) rootview.findViewById(R.id.profile_user_dp_imageview);
        mGridView = (ExpandableHeightGridView) rootview.findViewById(R.id.profile_posts_gridview);

        // Load the users profile
        Glide.with(this)
                .load(getString(R.string.aneek_dp_url))
                .apply(RequestOptions.centerCropTransform())
                .into(mUserDpImageView);

        // Setting up the Gridview and its adapter
        mAdapter = new ProfileUserPostsAdapter(getContext(), mPosts);
        mGridView.setAdapter(mAdapter);
        mGridView.setExpanded(true);
        mGridView.setFocusable(false);

        // TODO remove test posts
        insertPostsToAdapter();

        return rootview;

    }

    private void insertPostsToAdapter(){

        ProfileUserGridPost post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        post = new ProfileUserGridPost(0, "");
        mPosts.add(post);

        mAdapter.notifyDataSetChanged();

    }

}
