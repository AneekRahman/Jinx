package app.jinx.com;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // View Declaration
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LinearLayout mMainHolder, mCommentHolder;
    private TextView mCommentCount;
    private ImageView mCommentPostUserDp, mCommentBackIcon;

    private boolean isCommentLayoutOn = false;

    // Fragment instances to initiate the exoplayers' start stop method
    private HomeFragment mHomeFragment;
    private GlobalFragment mGlobalFragment;

    // Nav bar icons
    private int[] imageResId = {
            R.drawable.nav_home_icon,
            R.drawable.nav_earth_icon,
            R.drawable.nav_message_icon,
            R.drawable.nav_profile_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dark status bar text
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // TODO remove this
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Connecting view with code
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        mMainHolder = (LinearLayout) findViewById(R.id.main_activity_main_holder);
        mCommentHolder = (LinearLayout) findViewById(R.id.home_post_comment_layout);
        mCommentCount = (TextView) findViewById(R.id.home_post_comment_comment_count);
        mCommentPostUserDp = (ImageView)findViewById(R.id.home_post_comment_user_dp);
        mCommentBackIcon = (ImageView) findViewById(R.id.home_post_comment_back_icon);

        // Space on top to avoid the glitch for having a transparent navbar
        mMainHolder.setPadding(0, getStatusBarHeight(), 0, 0);
        mCommentHolder.setPadding(0, getStatusBarHeight(), 0, 0);

        // Setting up the view pager
        mViewPager.setOffscreenPageLimit(4);
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){

                    case 0:
                        break;
                    case 1:
                        if(mGlobalFragment != null)
                            mGlobalFragment.initializeExoPlayer();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                // Stopping the home exoplayers when switched to another tab besides home
                if(tab.getPosition() == 0){
                    // Called from main activity to home fragment
                    mHomeFragment.stopExoPlayers();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                }else if(tab.getPosition() == 1) {
                    // Called from main activity to global fragment
                    mGlobalFragment.releaseExoPlayer();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                // Scrolling home recycler to top when tapped on home nav icon while in home
                if(tab.getPosition() == 0){
                    // Called from main activity to home
                    mHomeFragment.scrollToTop();

                }

            }
        });

    }

    // The viewpager class constructor
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }

    // Viewpager setup method
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mHomeFragment = new HomeFragment();
        mGlobalFragment = new GlobalFragment();

        viewPagerAdapter.addFrag(mHomeFragment);
        viewPagerAdapter.addFrag(mGlobalFragment);
        viewPagerAdapter.addFrag(new MessageFragment());
        viewPagerAdapter.addFrag(new ProfileFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }

    // Getting the height of the status bar to give space between the root-layout and the window top
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    // Shows the comment layout when a posts comment icon is triggered (Accessed from fragment)
    public void showComments(String commentCount, String postUserDpUrl){

        isCommentLayoutOn = true;
        mCommentHolder.setVisibility(View.VISIBLE);
        mCommentCount.setText(commentCount);
        Glide.with(this)
                .load(postUserDpUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(mCommentPostUserDp);
        mHomeFragment.stopExoPlayers();

        mCommentBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCommentCount.setText("0");
                mCommentHolder.setVisibility(View.GONE);
                isCommentLayoutOn = false;

            }
        });

    }

    @Override
    public void onBackPressed() {
        if(isCommentLayoutOn) {
            mCommentHolder.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
