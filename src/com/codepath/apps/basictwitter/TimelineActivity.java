package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;

public class TimelineActivity extends FragmentActivity {
	
	private static final int REQUEST_CODE = 37;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
	}
	

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "first",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "second",
			    		MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("screenName", "loggedInUser");
		startActivity(i);
	}
	
	public void onComposeAction(MenuItem mi) {		
		// first parameter is the context, second is the class of the activity to launch
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE); // brings up the second activity   
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweets, menu);
        return true;
    }
    
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			
			Tweet composeTweet = (Tweet) data.getSerializableExtra("tweet");
			
			Bundle args = new Bundle();
	        
	        args.putSerializable("new_tweet", composeTweet);
	        TweetsListFragment fragment = new TweetsListFragment();
	        fragment.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.commit();
			
			/*
			tweets.add(0, composeTweet);
			lvTweets.setSelection(0);
			aTweets.notifyDataSetChanged();
			*/
			
		}  	
	}
}