package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.ComposeActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    //create tag for logging success vs failure
    public static final String TAG = "TimelineActivity";

    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);
        //find RV
        rvTweets = findViewById(R.id.rvTweets);
        //initialize list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
        //configure RV w layout manager and adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);
        //add logout button to timeline activity
        btnLogout = findViewById(R.id.btnLogout);
        populateHomeTimeline();
    }
    @Override
    //override function for new menu
    public boolean onCreateOptionsMenu(Menu menu) {
        //sets timeline using API call
        //inflate menu with menu passed in; inflates items to action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //must return true for menu to be displayed
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.compose) {
            //Compose Item was selected
          Toast.makeText(this, "Compose!", Toast.LENGTH_SHORT).show();
          //return true navigates to compose activity with intent
            Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
            startActivity(intent);
          return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    private void populateHomeTimeline() {
    client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess" + json.toString());
                //create list of tweets from parsing jsonarray
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJSONArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    //log error
                    Log.e(TAG, "Json exception");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure" + response, throwable);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               client.clearAccessToken(); // forget who's logged in
               Intent i = new Intent(TimelineActivity.this, LoginActivity.class);
               startActivity(i);
               finish(); // navigate backwards to Login screen
           }
        });
    }
}