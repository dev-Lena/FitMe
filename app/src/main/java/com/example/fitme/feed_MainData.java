package com.example.fitme;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class feed_MainData{

String review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url,
         textView_likes, textView_detailed_review_card, textView_more,
        textView_hashtag,TextView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4, review_date,
    // 이미지
        textView_review_writer, textView_reviewcard_number;
    String imageView_reviewcard_profile_image;
    String imageView_reviewcard_img1;
    int imageView_reviewcard_img2;
    int imageView_reviewcard_img3;
    int imageView_reviewcard_img4;
    int imageView_reviewcard_img5;
    int imageButton_review_edit_completed;
    float float_ratingBar;
//    Boolean Is_liked;
//    ArrayList comment ;


    public feed_MainData(String textView_shoppingmall_url,
                         String textView_detailed_review_card,
                         float float_ratingBar, String textView_hashtag, String review_date,
                         String textView_review_writer, String textView_reviewcard_number,
                         String textView_nickname, String textView_mysize , String imageView_reviewcard_img1,
                         String imageView_reviewcard_profile_image) {

        this.textView_shoppingmall_url = textView_shoppingmall_url;
        this.textView_detailed_review_card = textView_detailed_review_card;
        this.float_ratingBar = float_ratingBar;
        this.textView_hashtag = textView_hashtag;
        this.imageButton_review_edit_completed = imageButton_review_edit_completed;
        this.review_date = review_date;
        this.textView_review_writer = textView_review_writer;
        this.textView_reviewcard_number = textView_reviewcard_number;
        this.textView_nickname =textView_nickname;
        this.textView_mysize =textView_mysize;
        // 이미지
        this.imageView_reviewcard_img1 = imageView_reviewcard_img1;
        this.imageView_reviewcard_profile_image =imageView_reviewcard_profile_image;
        //좋아요
//        this.Is_liked = Is_liked;
//        this.textView_likes_number = textView_likes_number;
        // 댓글 arrayList
//        this.comment = comment; // 각 아이템의 댓글을 담고있는 arrayList

    }

    public String getImageView_reviewcard_profile_image() {
        return imageView_reviewcard_profile_image;
    }

    public void setImageView_reviewcard_profile_image(String imageView_reviewcard_profile_image) {
        this.imageView_reviewcard_profile_image = imageView_reviewcard_profile_image;
    }
//
    public String getImageView_reviewcard_img1() {

            return imageView_reviewcard_img1;

    }

    public void setImageView_reviewcard_img1(String imageView_reviewcard_img1) {
        this.imageView_reviewcard_img1 = imageView_reviewcard_img1;
    }


    public String getTextView_mysize() {
        return textView_mysize;
    }

    public void setTextView_mysize(String textView_mysize) {
        this.textView_mysize = textView_mysize;
    }

    public String getTextView_nickname() {
        return textView_nickname;
    }

    public void setTextView_nickname(String textView_nickname) {
        this.textView_nickname = textView_nickname;
    }


    public String getTextView_review_writer() {

        return textView_review_writer;
    }

    public void setTextView_review_writer(String textView_review_writer) {

        this.textView_review_writer = textView_review_writer;
    }

    public String getTextView_reviewcard_number() {
        return textView_reviewcard_number;
    }

    public void setTextView_reviewcard_number(String textView_reviewcard_number) {
        this.textView_reviewcard_number = textView_reviewcard_number;
    }

    public String getTextView_hashtag() {
        return textView_hashtag;
    }

    public void setTextView_hashtag(String textView_hashtag) {
        this.textView_hashtag = textView_hashtag;
    }

    public float getfloat_ratingBar() {
        return float_ratingBar;
    }

    public void setfloat_ratingBar(float float_ratingBar) {
        this.float_ratingBar = float_ratingBar;
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




}
