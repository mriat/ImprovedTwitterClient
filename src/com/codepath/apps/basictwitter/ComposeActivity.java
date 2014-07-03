package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	
    private EditText etTweet;
    private Button btnTweet;
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		client = TwitterApplication.getRestClient();
		
		etTweet = (EditText) findViewById(R.id.etTweet);
		btnTweet = (Button) findViewById(R.id.btnTweet);
	}
	
	
	public void onTweet(View v) {
		client.postTweet(etTweet.getText().toString(), new JsonHttpResponseHandler() {
			
			@Override
            public void onSuccess(JSONObject jsonObject) {
				
				Tweet tweet = Tweet.fromJSON(jsonObject);
                Intent data = new Intent();
                data.putExtra("tweet", tweet);
                setResult(RESULT_OK, data);
                finish(); 
            }
			
			@Override
            public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
            }
		});
	}
}

