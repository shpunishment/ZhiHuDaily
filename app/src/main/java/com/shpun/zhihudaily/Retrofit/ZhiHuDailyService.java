package com.shpun.zhihudaily.Retrofit;


import com.shpun.zhihudaily.Gson.CommentObject;
import com.shpun.zhihudaily.Gson.Latest;
import com.shpun.zhihudaily.Gson.StoryDetail;
import com.shpun.zhihudaily.Gson.TypeStory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ZhiHuDailyService {

    @GET("news/latest")
    Call<Latest> getLatest();

    @GET("news/{id}")
    Call<StoryDetail> getStoryDetail(@Path("id")String id);

    @GET("theme/{type}")
    Call<TypeStory> getTypeStory(@Path("type")String type);

    @GET("news/{id}/comments")
    Call<CommentObject> getComment(@Path("id")String id);


}
