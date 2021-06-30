package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

//make parcelable because User class used in Tweet class which is parcelabe
@Parcel
public class User {

    //user member vars
    public String name;
    public String screenName;
    public String profileImageUrl;

    //default constructor for parceler
    public User(){}

    //creates and returns User object from JSONObject
    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        return user;
    }
}
