package com.shpun.zhihudaily;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Gson.StoryDetail;
import com.shpun.zhihudaily.Retrofit.ZhiHuDailyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoryActivity extends AppCompatActivity {

    private static final String TAG = "StoryActivity";

    private String storyId;
    private RelativeLayout storyTopLayout;
    private ImageView storyImage;
    private TextView storyTitle;
    private TextView storyImageSource;
    private WebView storyWebView;

    private String shareUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Toolbar storyToolbar = (Toolbar)findViewById(R.id.story_toolbar);
        setSupportActionBar(storyToolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        Intent intent = getIntent();
        storyId = intent.getStringExtra("story_id");

        storyImage = (ImageView)findViewById(R.id.story_detail_image);
        storyTitle = (TextView)findViewById(R.id.story_detail_title);
        storyTopLayout = (RelativeLayout)findViewById(R.id.story_detail_top);
        storyImageSource = (TextView)findViewById(R.id.story_detail_image_source);
        storyWebView = (WebView)findViewById(R.id.story_detail_webview);
        storyWebView.getSettings().setJavaScriptEnabled(true);


        initStory();


    }

    private void initStory(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZhiHuDailyService zhiHuDailyService = retrofit.create(ZhiHuDailyService.class);
        Call<StoryDetail> storyDetailCall = zhiHuDailyService.getStoryDetail(storyId);

        storyDetailCall.enqueue(new Callback<StoryDetail>() {
            @Override
            public void onResponse(Call<StoryDetail> call, Response<StoryDetail> response) {

                if(response.body().image == null || response.body().image.length() == 0){
                    storyTopLayout.setVisibility(View.GONE);

                }else{
                    storyTopLayout.setVisibility(View.VISIBLE);

                    Glide.with(StoryActivity.this).load(response.body().image).into(storyImage);
                    storyTitle.setText(response.body().title);
                    storyImageSource.setText(response.body().imageSource);
                }

                shareUrl = response.body().shareUrl;

                String html = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"ZhiHuCSS.css\"/></head><body>"+response.body().body+"</body></html>";
                storyWebView.loadDataWithBaseURL("file:///android_asset/", html , "text/html", "utf-8", null);
            }

            @Override
            public void onFailure(Call<StoryDetail> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"retrofit Call<StoryDetail> fail");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.story_detail_menu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.story_detail_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,shareUrl);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享给"));
                break;
            case R.id.story_detail_comment:
                Intent intentToComment = new Intent(StoryActivity.this,CommentActivity.class);
                intentToComment.putExtra("story_id",storyId);
                startActivity(intentToComment);
                break;

        }

        return true;
    }



}
