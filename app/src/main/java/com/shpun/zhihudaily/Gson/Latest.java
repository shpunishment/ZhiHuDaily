package com.shpun.zhihudaily.Gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Latest {

    public String date;
    @SerializedName("stories")
    public List<Story> stories;
    @SerializedName("top_stories")
    public List<TopStory> topStories;

}
