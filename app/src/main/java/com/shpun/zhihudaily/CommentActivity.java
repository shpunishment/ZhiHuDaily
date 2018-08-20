package com.shpun.zhihudaily;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.shpun.zhihudaily.Adapter.CommentAdapter;
import com.shpun.zhihudaily.Gson.CommentObject;
import com.shpun.zhihudaily.Retrofit.ZhiHuDailyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "CommentActivity";

    private ActionBar actionBar;
    private CommentAdapter mCommentAdapter;
    private String storyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar commentToolbar = (Toolbar)findViewById(R.id.comment_toolbar);
        setSupportActionBar(commentToolbar);

        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        Intent intent = getIntent();
        storyId = intent.getStringExtra("story_id");

        RecyclerView commentRecyclerView = (RecyclerView)findViewById(R.id.comment_recyclerview);
        commentRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(layoutManager);
        mCommentAdapter = new CommentAdapter();
        commentRecyclerView.setAdapter(mCommentAdapter);

        initComment();

    }

    private void initComment(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZhiHuDailyService service = retrofit.create(ZhiHuDailyService.class);

        Call<CommentObject> commentObjectCall = service.getComment(storyId);

        commentObjectCall.enqueue(new Callback<CommentObject>() {
            @Override
            public void onResponse(Call<CommentObject> call, Response<CommentObject> response) {
                actionBar.setTitle(response.body().count+"条评论");
                mCommentAdapter.setAdapter(response.body().comments);
                mCommentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<CommentObject> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"comment Call fail");
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
