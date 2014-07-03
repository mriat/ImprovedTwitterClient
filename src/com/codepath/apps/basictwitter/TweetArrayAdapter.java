package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// get the data item for position
		Tweet tweet = getItem(position);
		
		// find or inflate the template
		View v;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else { v = convertView; }
		
		// find the views within template
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvRelTime = (TextView) v.findViewById(R.id.tvRelTime);
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		// populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvScreenName.setText("@" + tweet.getUser().getScreenName());
		tvName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvRelTime.setText(" - " + getRelativeTimeAgo(tweet.getCreatedAt()));
		
		ivProfileImage.setTag(tweet.getUser().getScreenName());
		ivProfileImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String screenName = (String) v.getTag();
				Intent i = new Intent(v.getContext(), ProfileActivity.class);
				i.putExtra("screenName", screenName);
				v.getContext().startActivity(i);
			}
		});
		
		return v;
	}
	
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}

}
