package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//parameterize class with viewholder AFTER defining vh
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    //member vars to pass in context and list of tweets into
    Context context;
    List<Tweet> tweets;

    //custom constructor to initialize member vars

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row inflate layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //create a java view with inflater to wrap in VH
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //get data at pos
        Tweet tweet = tweets.get(position);
        //bind data with viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all tweets from the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of tweets
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    //define viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
        ImageView ivUrl;

        //constructing viewholder with initialization of member vars
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById((R.id.tvScreenName));
            tvTimestamp = itemView.findViewById((R.id.tvTimestamp));
            ivUrl = itemView.findViewById(R.id.ivUrl);
        }

        //helper function to fill in views on screen using tweet at position OnBindVH takes in
        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvTimestamp.setText(tweet.timestamp);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            if(!tweet.embedUrl.equals("")) {
                Glide.with(context).load(tweet.embedUrl).into(ivUrl);
                ivUrl.setVisibility(View.VISIBLE);
            }
            else {
                ivUrl.setVisibility(View.GONE);
            }
        }
    }
}
