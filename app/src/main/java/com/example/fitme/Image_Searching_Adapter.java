package com.example.fitme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Image_Searching_Adapter extends RecyclerView.Adapter<Image_Searching_Adapter.ExampleViewHolder> {
private Context mContext;
private ArrayList<Image_Searching_ItemData> imageSearchItemList;
private OnItemClickListener mListener;

public interface OnItemClickListener {
    void onItemClick(int position);
}

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Image_Searching_Adapter(Context context, ArrayList<Image_Searching_ItemData> exampleList) {
        mContext = context;
        imageSearchItemList = exampleList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ExampleViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Image_Searching_ItemData imageSearchingItemData = imageSearchItemList.get(position);

        String imageUrl = imageSearchingItemData.getImageUrl();
        String creatorName = imageSearchingItemData.getCreator();
        int likeCount = imageSearchingItemData.getLikeCount();

        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText("Likes: " + likeCount);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return imageSearchItemList.size();
    }

public class ExampleViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageView;
    public TextView mTextViewCreator;
    public TextView mTextViewLikes;

    public ExampleViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.image_view);
        mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
        mTextViewLikes = itemView.findViewById(R.id.text_view_likes);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            }
        });
    }
}
}
