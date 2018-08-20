package com.shpun.zhihudaily.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Gson.Editor;
import com.shpun.zhihudaily.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  EditorActivity 中 RecyclerView 适配器
 *  用于显示主编信息
 */
public class EditorActivityAdapter extends RecyclerView.Adapter<EditorActivityAdapter.ViewHolder> {

    private List<Editor> mEditorList;
    private Context mContext;

    static class ViewHolder extends EditorAdapter.ViewHolder{

        ImageView editorAvatar;
        TextView editorName;
        TextView editorBio;

        public ViewHolder(View view){
            super(view);

            editorAvatar = (ImageView)view.findViewById(R.id.editor_activity_avatar);
            editorName = (TextView) view.findViewById(R.id.editor_activity_name);
            editorBio = (TextView)view.findViewById(R.id.editor_activity_bio);

        }

    }

    public EditorActivityAdapter(List<Editor> editorList){
        mEditorList = editorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.editor_activity_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Editor editor = mEditorList.get(position);
        Glide.with(mContext).load(editor.avatar).into(holder.editorAvatar);
        holder.editorName.setText(editor.name);
        holder.editorBio.setText(editor.bio);
    }

    @Override
    public int getItemCount() {
        return mEditorList.size();
    }
}
