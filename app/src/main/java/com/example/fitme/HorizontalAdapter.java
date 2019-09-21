package com.example.fitme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Horizontal_ItemData> Image_dataList;
    private Context mContext;  // 이미지 Context 를 활용해서 넣기 위해 추가

    public HorizontalAdapter(ArrayList<Horizontal_ItemData> data,  Context mContext)
    {
        this.Image_dataList = data;
        this.mContext = mContext; // 이미지 Context 를 활용해서 넣기 위해 추가

    }
    protected ImageView imageView_feed_item_image;



    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_feed_item_image;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_feed_item_image = itemView.findViewById(R.id.imageView_feed_item_image);
        }
    }


    @NonNull
    @Override
    public HorizontalAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
//        return new HorizontalAdapter.HorizontalViewHolder(v);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_images, parent, false);

        HorizontalAdapter.HorizontalViewHolder holder = new HorizontalAdapter.HorizontalViewHolder(view);
        mContext = parent.getContext();// 이미지 picasso 라이브러리 사용시 필요


        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalAdapter.HorizontalViewHolder holder, int position) {
        Horizontal_ItemData horizontal_itemData = Image_dataList.get(position);

        String review_Image = horizontal_itemData.getImageView_reviewcard_img();

        Picasso.get()
                .load(review_Image)
                .fit()
                .centerInside()
                .placeholder(R.drawable.review_plz) // 이미지가 없을 때 기본
                .error(R.drawable.review_plz)// 에러가 났을 때
                .into(holder.imageView_feed_item_image);


    }

    @Override
    public int getItemCount() {
        return Image_dataList.size();
    }


}
