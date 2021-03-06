package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	private TwitterClient client;
	String screenName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// gets an instance of the authenticated activity
		client = TwitterApplication.getRestClient();
		
		screenName = getArguments().getString("screen_name", "");
		populateTimeline(25, -1, -1);		
	}
	
	public void populateTimeline(int count, long sinceId, long maxId) {

		client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray json) {
					//Log.d("debug", json.toString());
					addAll(Tweet.fromJSONArray(json));
				}
		
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			}
		);
	}
	
	
}