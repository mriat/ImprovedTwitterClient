package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	private User user;
	
	private TextView tvName;
	private TextView tvTagline;
	private TextView tvFollowers;
	private TextView tvFollowing;
	private ImageView ivProfileImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		getViews();
		
		Intent intent = getIntent();
		String screenName = intent.getStringExtra("screenName");
		
		if (screenName.equals("loggedInUser")) { loadMyInfo(); }
		else { loadUserInfo(screenName); }
	}

	private void getViews() {
		
		tvName = (TextView) findViewById(R.id.tvName);
		tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
	}
		
	private void loadMyInfo() {
		TwitterApplication.getRestClient().getAccountVerify(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						user = User.fromJSON(json);
						setInfo();
					}

					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());

					}
				});
	}
	
	private void loadUserInfo(String name) {
		TwitterApplication.getRestClient().getUserShowInfo(name, 
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						user = User.fromJSON(json);
						setInfo();
					}

					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
				});
	}
	
	private void setInfo() {
			getActionBar().setTitle("@" + user.getScreenName());
			
			tvName.setText(user.getName());
			tvTagline.setText(user.getTagline());
			tvFollowers.setText(user.getFollowersCount() + " Followers");
			tvFollowing.setText(user.getFriendsCount() + " Following");
			
			ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);			

			Bundle args = new Bundle();
	        args.putString("screen_name", user.getScreenName());
	        UserTimelineFragment fragment = new UserTimelineFragment();
	        fragment.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentUserTimeline, fragment);
            ft.commit();
	}
}
