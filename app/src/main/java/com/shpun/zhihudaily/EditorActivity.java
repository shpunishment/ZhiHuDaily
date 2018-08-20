package com.shpun.zhihudaily;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.shpun.zhihudaily.Adapter.EditorActivityAdapter;
import com.shpun.zhihudaily.Gson.Editor;

import java.util.ArrayList;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = "EditorActivity+editor";

    private EditorActivityAdapter editorActivityAdapter;

    private List<Editor> mEditorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar editorToolbar = (Toolbar)findViewById(R.id.editor_toolbar);
        setSupportActionBar(editorToolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("主编");
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        List<Editor> mEditorList = (ArrayList<Editor>) bundle.getSerializable("editor");

        RecyclerView editorRecyclerView = (RecyclerView)findViewById(R.id.editor_recyclerview);
        editorRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        editorRecyclerView.setLayoutManager(layoutManager);
        editorActivityAdapter = new EditorActivityAdapter(mEditorList);
        editorRecyclerView.setAdapter(editorActivityAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
