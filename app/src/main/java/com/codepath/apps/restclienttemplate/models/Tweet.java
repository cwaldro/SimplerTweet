package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;

    //create and return tweet from JSONObject data
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        //need User constructor to create user object from JSONobject (implemented in User.java)
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        return tweet;
    }
    //create and return list of tweet objects from whol jsonArray
    public static List<Tweet> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        //for all tweet obj in json array add tweet object to list
        for(int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
