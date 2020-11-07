package app.jinx.com;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import app.jinx.com.Adapters.GlobalGridPostsAdapter;
import app.jinx.com.Classes.ExpandableHeightGridView;
import app.jinx.com.Classes.GlobalPost;

public class GlobalFragment extends Fragment {

    // View decalration
    private ExpandableHeightGridView mMainGridView;
    private SimpleExoPlayerView mExoPlayerView;

    // Main Grid posts and adapter
    private GlobalGridPostsAdapter mMainAdapter;
    private List<GlobalPost> mPosts = new ArrayList<>();

    // Exoplayer declarations
    private String vidAddress;
    private ExoPlayer mExoPlayer;
    private MediaSource mMediaSource;
    private int mExoPlayerWindowIndex = 0;
    private long mPlaybackPosition = 0;

    public GlobalFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_global, container, false);

        // Connecting view to code
        mMainGridView = (ExpandableHeightGridView) rootview.findViewById(R.id.global_main_gridview);
        mExoPlayerView = (SimpleExoPlayerView) rootview.findViewById(R.id.global_main_exo_player);

        // Setting up main Global ExoPlayer
        vidAddress = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

        // Setting up main Grid View and adapter
        setExoPlayerFixedHeight();
        mMainAdapter = new GlobalGridPostsAdapter(getContext(), mPosts);
        mMainGridView.setAdapter(mMainAdapter);
        mMainGridView.setExpanded(true);
        mMainGridView.setFocusable(false);

        // TODO remove test posts
        insertPostsToAdapter();

        return rootview;

    }

    private void insertPostsToAdapter(){
        for(int i = 0; i < 12; i++){
            GlobalPost post = new GlobalPost(0, 100, 3900);
            mPosts.add(post);
        }
        mMainAdapter.notifyDataSetChanged();

    }

    private void setExoPlayerFixedHeight(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;



        mExoPlayerView.getLayoutParams().height = (height / 7) * 5;
        mExoPlayerView.requestLayout();

    }

    // Initializing the posts exoplayer
    public void initializeExoPlayer(){

        if(mExoPlayer == null){

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mExoPlayerView.setControllerShowTimeoutMs(0);
            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mExoPlayerWindowIndex, mPlaybackPosition);
        }

        prepareExoPlayer();

    }

    // Preparing the exoplayer with the video address
    private void prepareExoPlayer(){

        Uri uri = Uri.parse(vidAddress);
        mMediaSource = buildMediaSource(uri);
        mExoPlayer.prepare(mMediaSource, false, false);

        mExoPlayer.addListener(mExoPlayerEventListener);

    }
    // Building mediasource for exoplayer
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("ripple-exo")).createMediaSource(uri);
    }

    // Exoplayer events listener
    Player.EventListener mExoPlayerEventListener = new Player.EventListener() {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            // Giving Player state response to user
            switch (playbackState){

                case ExoPlayer.STATE_READY: {
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                break;
                case ExoPlayer.STATE_BUFFERING: { }
                case ExoPlayer.STATE_ENDED: {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                case ExoPlayer.STATE_IDLE: {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                break;
            }

        }

        // On error keep on trying to ready the player on and on (This happens when internet connection unintentionally goes)
        @Override
        public void onPlayerError(ExoPlaybackException error) {

            mExoPlayer.prepare(mMediaSource, false, false);

        }
        // Not needed default methods
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {}
        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}
        @Override
        public void onLoadingChanged(boolean isLoading) {}
        @Override
        public void onPositionDiscontinuity(int reason) {}
        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
        @Override
        public void onSeekProcessed() {}
        @Override
        public void onRepeatModeChanged(int repeatMode) {}
        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}
    };

    // Releasing the exoplayer manually ( When home fragment gets paused or stopped )
    public void releaseExoPlayer() {

        if(mExoPlayer != null) {

            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mExoPlayerWindowIndex = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.removeListener(mExoPlayerEventListener);
            mExoPlayer.release();
            mExoPlayer = null;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
        }
    }

}
