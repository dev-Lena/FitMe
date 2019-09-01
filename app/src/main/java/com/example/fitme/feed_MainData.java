package com.example.fitme;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class feed_MainData{

//TextView review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url, textView_likes_number, textView_likes, textView_detailed_review_card, textView_more, textView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4;
//ImageView imageView_reviewcard_profile_image, imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5 ;
//ImageButton imageButton_like,imageButton_comment, imageButton_bookmark ;
//

String review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url, textView_likes_number, textView_likes, textView_detailed_review_card, textView_more,
        textView_hashtag,TextView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4, review_date;
int imageView_reviewcard_profile_image;
    int imageView_reviewcard_img1;
    int imageView_reviewcard_img2;
    int imageView_reviewcard_img3;
    int imageView_reviewcard_img4;
    int imageView_reviewcard_img5;
    int imageButton_review_edit_completed;
    float int_ratingBar;



//    public feed_MainData(TextView textView_shoppingmall_url, TextView textView_detailed_review_card, RatingBar int_ratingBar, TextView textView_hashtag) {
//
//    }

    public feed_MainData(String textView_shoppingmall_url,
                         String textView_detailed_review_card,
                         float int_ratingBar, String textView_hashtag) {

        this.textView_shoppingmall_url = textView_shoppingmall_url;
        this.textView_detailed_review_card = textView_detailed_review_card;
        this.textView_hashtag = textView_hashtag;
        this.imageButton_review_edit_completed = imageButton_review_edit_completed;
        this.int_ratingBar = int_ratingBar;
//        this.review_date = review_date;
    }
    public String getTextView_hashtag() {
        return textView_hashtag;
    }

    public void setTextView_hashtag(String textView_hashtag) {
        this.textView_hashtag = textView_hashtag;
    }

    public float getInt_ratingBar() {
        return int_ratingBar;
    }

    public void setInt_ratingBar(float int_ratingBar) {
        this.int_ratingBar = int_ratingBar;
    }

    // feed_MainData 생성자를 넣어주면 -> feed 값을 받아올 때 넣어줄 곳이 생겨 ^^*

    public String getReview_date() {

        return review_date;
    }

    public void setReview_date(String review_date) {

        Date currenttime = Calendar.getInstance().getTime();// Date타입 변수 currenttime에 시간을 받아옴
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.KOREA); // 심플 데이타 포맷으로 시간 입력
        review_date = simpleformat.format(currenttime);


        this.review_date = review_date;
    }

//

    public String getTextView_shoppingmall_url() {
        return textView_shoppingmall_url;
    }
//
    public void setTextView_shoppingmall_url(String textView_shoppingmall_url) {
        this.textView_shoppingmall_url = textView_shoppingmall_url;
    }


    public String getTextView_detailed_review_card() {
        return textView_detailed_review_card;
    }


    public int imageButton_review_edit_completed() {
        return imageButton_review_edit_completed;
    }

    public void setTextView_detailed_review_card(String textView_detailed_review_card) {
        this.textView_detailed_review_card = textView_detailed_review_card;
    }
//
//

}
