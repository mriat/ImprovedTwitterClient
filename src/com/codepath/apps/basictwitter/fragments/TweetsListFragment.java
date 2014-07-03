package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	
	private ArrayList<Tweet> tweets;
	protected TweetArrayAdapter aTweets;
	private ListView lvTweets;
	
	private static final int RECORD_COUNT = 25;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		
		/*
		Tweet newTweet = (Tweet) getArguments().getSerializable("new_tweet");
		tweets.add(0, newTweet);
		lvTweets.setSelection(0);
		aTweets.notifyDataSetChanged();*/
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

		// assign our view references
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		

		
		// return the layout view
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		@Override
		    public void onLoadMore(int page, int totalItemsCount) {
	            
				// Triggered only when new data needs to be appended to the list
	            // Add whatever code is needed to append new items to your AdapterView
	    	
		    	if (totalItemsCount >= tweets.size()) {
		    		long maxId = aTweets.getItem(totalItemsCount - 1).getUid() - 1;
	                populateTimeline(RECORD_COUNT, -1, maxId);
	            }
		    }
		});
	
	}

	// delegate the adding to the internal adapter
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	
	public void populateTimeline(int count, long sinceId, long maxId) {
		
	}
		
	
	/*
	public Tweet getItem(int pos) {
		return aTweets.getItem(pos);
	}*/
	//
	
	/*
	// return the adapter to the activity
	public TweetArrayAdapter getAdapter() {
		return aTweets;
	}*/
	
}
