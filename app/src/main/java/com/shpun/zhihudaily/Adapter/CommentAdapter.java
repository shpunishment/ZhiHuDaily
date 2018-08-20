package com.shpun.zhihudaily.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shpun.zhihudaily.Gson.Comment;
import com.shpun.zhihudaily.R;

import java.util.List;

/**
 *
 * 评论页面 CommentActivity 的 RecyclerView 适配器
 * 用于显示评论
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> mCommentList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView commentAvatar;
        TextView commentName;
        TextView commentContent;
        RelativeLayout replyLayout;
        TextView replyName;
        TextView replyContent;

        public ViewHolder(View view){
            super(view);
            commentAvatar = (ImageView)view.findViewById(R.id.comment_avatar);
            commentName = (TextView)view.findViewById(R.id.comment_name);
            commentContent = (TextView)view.findViewById(R.id.comment_content);

            replyLayout = (RelativeLayout)view.findViewById(R.id.comment_reply_layout);
            replyName = (TextView)view.findViewById(R.id.reply_name);
            replyContent = (TextView)view.findViewById(R.id.reply_content);
        }

    }

    public void setAdapter(List<Comment> commentList){
        mCommentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        View view  = LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        if(comment.replyTo != null){
            holder.replyLayout.setVisibility(View.VISIBLE);
            holder.replyName.setText("//" + comment.replyTo.author + " : ");
            holder.replyContent.setText(comment.replyTo.content);
        }else{
            holder.replyLayout.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(comment.avatar).into(holder.commentAvatar);
        holder.commentName.setText(comment.author);
        holder.commentContent.setText(comment.content);
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }
}
