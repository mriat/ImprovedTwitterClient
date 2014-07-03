package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	private int followersCount;
	private int friendsCount;
	private String tagline;
	
	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public int getFollowersCount() {
		return followersCount;
	}
	
	public int getFriendsCount() {
		return friendsCount;
	}

	public String getTagline() {
		return tagline;
	}
	
	// User.fromJSON(...)
	public static User fromJSON(JSONObject json) {
		User u = new User();
		try {
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json.getString("profile_image_url");		
			
			u.followersCount = json.getInt("followers_count");
			u.friendsCount = json.getInt("friends_count");
			
			u.tagline = json.getString("description");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;		
	}
}