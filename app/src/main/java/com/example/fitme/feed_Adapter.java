package com.example.fitme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class feed_Adapter extends RecyclerView.Adapter<feed_Adapter.FeedViewHolder>{

    // 리사이클러뷰에 넣을 ArrayList

    private ArrayList<feed_MainData> arrayList;

    public feed_Adapter(ArrayList<feed_MainData>arrayList){   // 생성자
        this.arrayList = arrayList ;
    }

    @NonNull
    @Override
    public feed_Adapter.FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_review_card,parent,false);
        FeedViewHolder holder = new FeedViewHolder(view);
        return holder;
    }

    @Override
    // 생성된 뷰홀더에 아이템 내용 세팅하는 곳
    // 생성자로 현제 데이터가 들어오지 않기 때문에 position 값을 이용해서 데이터를 가지고 옴.

    //TextView review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url, textView_likes_number, textView_likes,
    // textView_detailed_review_card, textView_more, textView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4;
//ImageView imageView_reviewcard_profile_image, imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5 ;
//ImageButton imageButton_like,imageButton_comment, imageButton_bookmark ;
    public void onBindViewHolder(@NonNull final feed_Adapter.FeedViewHolder holder, int position) {
//        holder.imageView_reviewcard_profile_image.setImageResource(arrayList.get(position).getImageView_reviewcard_profile_image());
//        holder.imageView_reviewcard_img1.setImageResource(arrayList.get(position).getImageView_reviewcard_img1());
//        holder.imageView_reviewcard_img2.setImageResource(arrayList.get(position).getImageView_reviewcard_img2());
//        holder.imageView_reviewcard_img3.setImageResource(arrayList.get(position).getImageView_reviewcard_img3());
//        holder.imageView_reviewcard_img4.setImageResource(arrayList.get(position).getImageView_reviewcard_img4());
//        holder.imageView_reviewcard_img5.setImageResource(arrayList.get(position).getImageView_reviewcard_img5());
//        holder.imageButton_like.setImageResource(arrayList.get(position).getImageButton_like());
//        holder.imageButton_comment.setImageResource(arrayList.get(position).getImageButton_comment());
//        holder.imageButton_bookmark.setImageResource(arrayList.get(position).getImageButton_bookmark());
//        holder.review_card.setText(arrayList.get(position).getReview_card());
//        holder.textView19.setText(arrayList.get(position).getTextView19());
//        holder.textView_mysize.setText(arrayList.get(position).getTextView_mysize());
//        holder.textView_nickname.setText(arrayList.get(position).getTextView_nickname());
//        holder.textView20.setText(arrayList.get(position).getTextView20());
        holder.textView_shoppingmall_url.setText(arrayList.get(position).getTextView_shoppingmall_url());
//        holder.textView_likes_number.setText(arrayList.get(position).getTextView_likes_number());
//        holder.textView_likes.setText(arrayList.get(position).getTextView_likes());
        holder.textView_detailed_review_card.setText(arrayList.get(position).getTextView_detailed_review_card());
//        holder.textView_more.setText(arrayList.get(position).getTextView_more());
//        holder.textView_hashtag1.setText(arrayList.get(position).getTextView_hashtag1());
//        holder.textView_hashtag2.setText(arrayList.get(position).getTextView_hashtag2());
//        holder.textView_hashtag3.setText(arrayList.get(position).getTextView_hashtag3());
//        holder.textView_hashtag4.setText(arrayList.get(position).getTextView_hashtag4());

holder.itemView.findViewById(position);
// holder.itemView.setTag(position);  // -> findViewById와 같은 기능
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //클릭했을 때 어떤 걸 할건지
            }
        });

        // 길게 눌렀을 때 어떤 걸 할건지
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();   // ArrayList의 사이즈만큼
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url, textView_likes_number, textView_likes,
         textView_detailed_review_card, textView_more, textView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4;
ImageView imageView_reviewcard_profile_image, imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5 ;
ImageButton imageButton_like,imageButton_comment, imageButton_bookmark ;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.imageView_reviewcard_profile_image = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_profile_image);
//            this.imageView_reviewcard_img1 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img1);
//            this.imageView_reviewcard_img2 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img2);
//            this.imageView_reviewcard_img3 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img3);
//            this.imageView_reviewcard_img4 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img4);
//            this.imageView_reviewcard_img5 = (ImageView) itemView.findViewById(R.id.imageView_reviewcard_img5);
//            this.imageButton_like = (ImageButton) itemView.findViewById(R.id.imageButton_like);
//            this.imageButton_comment = (ImageButton) itemView.findViewById(R.id.imageButton_comment);
//            this.imageButton_bookmark = (ImageButton) itemView.findViewById(R.id.imageButton_bookmark);
//            this.review_card = (TextView) itemView.findViewById(R.id.review_card);
//            this.textView_mysize = (TextView) itemView.findViewById(R.id.textView_mysize);
//            this.textView_nickname = (TextView) itemView.findViewById(R.id.textView_nickname);
            this.textView_shoppingmall_url = (TextView) itemView.findViewById(R.id.textView_shoppingmall_url);
//            this.textView_likes_number = (TextView) itemView.findViewById(R.id.textView_likes_number);
//            this.textView_likes = (TextView) itemView.findViewById(R.id.textView_likes);
            this.textView_detailed_review_card = (TextView) itemView.findViewById(R.id.textView_detailed_review_card);
//            this.textView_more = (TextView) itemView.findViewById(R.id.textView_more);
//            this.textView_hashtag1 = (TextView) itemView.findViewById(R.id.textView_hashtag1);
//            this.textView_hashtag2 = (TextView) itemView.findViewById(R.id.textView_hashtag2);
//            this.textView19 = (TextView) itemView.findViewById(R.id.textView19);
//            this.textView20 = (TextView) itemView.findViewById(R.id.textView20);
//            this.textView_hashtag3 = (TextView) itemView.findViewById(R.id.textView_hashtag3);
//            this.textView_hashtag4 = (TextView) itemView.findViewById(R.id.textView_hashtag4);





        }

    }
}
