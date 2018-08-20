package com.shpun.zhihudaily.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Gson.Editor;
import com.shpun.zhihudaily.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * TypeFragment 中 主编的 RecyclerView 适配器
 * 用于显示主编头像
 */
public class EditorAdapter extends RecyclerView.Adapter<EditorAdapter.ViewHolder> {

    private List<Editor> mEditorList;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView editorAvatar;

        public ViewHolder(View view){
            super(view);
            editorAvatar = (ImageView)view.findViewById(R.id.editor_avatar);
        }

    }

    public void setAdapter(List<Editor> editorList){
        mEditorList = editorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.editor_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditorList != null){
                    Intent intent = new Intent("com.shpun.zhihudaily.EDITOR_DETAIL");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("editor", (Serializable) mEditorList);
                    intent.putExtra("bundle",bundle);
                    mContext.startActivity(intent);
                }

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Editor editor = mEditorList.get(position);
        Glide.with(mContext).load(editor.avatar).into(holder.editorAvatar);
    }

    @Override
    public int getItemCount() {
        return mEditorList == null?0:mEditorList.size();
    }

}
