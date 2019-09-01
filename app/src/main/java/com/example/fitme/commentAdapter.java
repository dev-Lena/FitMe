package com.example.fitme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class commentAdapter  extends RecyclerView.Adapter<commentAdapter.CommentViewHolder> {
    private ArrayList<comment_Data> commentArrayListList;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_comment_nickname;
        public TextView textView_comment_content;
        public ImageView imageView_comment_profile;

        public CommentViewHolder(View itemView) {
            super(itemView);
            textView_comment_nickname = itemView.findViewById(R.id.textView_comment_nickname);
            textView_comment_content = itemView.findViewById(R.id.textView_comment_content);
            imageView_comment_profile = itemView.findViewById(R.id.imageView_comment_profile);

        }
    }

    public commentAdapter(ArrayList<comment_Data> exampleList) {
        commentArrayListList = exampleList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment_card, parent, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(v);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        comment_Data currentItem = commentArrayListList.get(position);

        holder.textView_comment_nickname.setText(commentArrayListList.get(position).getTextView_comment_nickname());
        holder.textView_comment_content.setText(commentArrayListList.get(position).getTextView_comment_content());
        holder.imageView_comment_profile.setImageResource(commentArrayListList.get(position).getImageView_comment_profile());

    }

    @Override
    public int getItemCount() {
        return commentArrayListList.size();
    }
}
