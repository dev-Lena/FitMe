package com.example.fitme;

public class feed_MainData{

//TextView review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url, textView_likes_number, textView_likes, textView_detailed_review_card, textView_more, textView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4;
//ImageView imageView_reviewcard_profile_image, imageView_reviewcard_img1, imageView_reviewcard_img2, imageView_reviewcard_img3, imageView_reviewcard_img4, imageView_reviewcard_img5 ;
//ImageButton imageButton_like,imageButton_comment, imageButton_bookmark ;
//

String review_card,textView19,textView_mysize,textView_nickname, textView20, textView_shoppingmall_url, textView_likes_number, textView_likes, textView_detailed_review_card, textView_more,
        textView_hashtag,TextView_hashtag1, textView_hashtag2, textView_hashtag3,textView_hashtag4;
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

    public feed_MainData( String textView_shoppingmall_url, String textView_detailed_review_card, float int_ratingBar, String textView_hashtag) {
        this.textView_shoppingmall_url = textView_shoppingmall_url;
        this.textView_detailed_review_card = textView_detailed_review_card;
        this.textView_hashtag = textView_hashtag;
        this.imageButton_review_edit_completed = imageButton_review_edit_completed;
        this.int_ratingBar = int_ratingBar;
    }
//    public String getReview_card() {
//        return review_card;
//    }
//
//    public void setReview_card(String review_card) {
//        this.review_card = review_card;
//    }
//
//    public String getTextView19() {
//        return textView19;
//    }
//
//    public void setTextView19(String textView19) {
//        this.textView19 = textView19;
//    }
//
//    public String getTextView_mysize() {
//        return textView_mysize;
//    }
//
//    public void setTextView_mysize(String textView_mysize) {
//        this.textView_mysize = textView_mysize;
//    }
//
//    public String getTextView_nickname() {
//        return textView_nickname;
//    }
//
//    public void setTextView_nickname(String textView_nickname) {
//        this.textView_nickname = textView_nickname;
//    }
//
//    public String getTextView20() {
//        return textView20;
//    }
//
//    public void setTextView20(String textView20) {
//        this.textView20 = textView20;
//    }

    public String getTextView_shoppingmall_url() {
        return textView_shoppingmall_url;
    }
//
    public void setTextView_shoppingmall_url(String textView_shoppingmall_url) {
        this.textView_shoppingmall_url = textView_shoppingmall_url;
    }
//
//    public String getTextView_likes_number() {
//        return textView_likes_number;
//    }
//
//    public void setTextView_likes_number(String textView_likes_number) {
//        this.textView_likes_number = textView_likes_number;
//    }
//
//    public String getTextView_likes() {
//        return textView_likes;
//    }
//
//    public void setTextView_likes(String textView_likes) {
//        this.textView_likes = textView_likes;
//    }

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
//    public String getTextView_more() {
//        return textView_more;
//    }
//
//    public void setTextView_more(String textView_more) {
//        this.textView_more = textView_more;
//    }
//
//    public String getTextView_hashtag1() {
//        return textView_hashtag1;
//    }
//
//    public void setTextView_hashtag1(String textView_hashtag1) {
//        this.textView_hashtag1 = textView_hashtag1;
//    }
//
//    public String getTextView_hashtag2() {
//        return textView_hashtag2;
//    }
//
//    public void setTextView_hashtag2(String textView_hashtag2) {
//        this.textView_hashtag2 = textView_hashtag2;
//    }
//
//    public String getTextView_hashtag3() {
//        return textView_hashtag3;
//    }
//
//    public void setTextView_hashtag3(String textView_hashtag3) {
//        this.textView_hashtag3 = textView_hashtag3;
//    }
//
//    public String getTextView_hashtag4() {
//        return textView_hashtag4;
//    }
//
//    public void setTextView_hashtag4(String textView_hashtag4) {
//        this.textView_hashtag4 = textView_hashtag4;
//    }
//
//    public int getImageView_reviewcard_profile_image() {
//        return imageView_reviewcard_profile_image;
//    }
//
//    public void setImageView_reviewcard_profile_image(int imageView_reviewcard_profile_image) {
//        this.imageView_reviewcard_profile_image = imageView_reviewcard_profile_image;
//    }
//
//    public int getImageView_reviewcard_img1() {
//        return imageView_reviewcard_img1;
//    }
//
//    public void setImageView_reviewcard_img1(int imageView_reviewcard_img1) {
//        this.imageView_reviewcard_img1 = imageView_reviewcard_img1;
//    }
//
//    public int getImageView_reviewcard_img2() {
//        return imageView_reviewcard_img2;
//    }
//
//    public void setImageView_reviewcard_img2(int imageView_reviewcard_img2) {
//        this.imageView_reviewcard_img2 = imageView_reviewcard_img2;
//    }
//
//    public int getImageView_reviewcard_img3() {
//        return imageView_reviewcard_img3;
//    }
//
//    public void setImageView_reviewcard_img3(int imageView_reviewcard_img3) {
//        this.imageView_reviewcard_img3 = imageView_reviewcard_img3;
//    }
//
//    public int getImageView_reviewcard_img4() {
//        return imageView_reviewcard_img4;
//    }
//
//    public void setImageView_reviewcard_img4(int imageView_reviewcard_img4) {
//        this.imageView_reviewcard_img4 = imageView_reviewcard_img4;
//    }
//
//    public int getImageView_reviewcard_img5() {
//        return imageView_reviewcard_img5;
//    }
//
//    public void setImageView_reviewcard_img5(int imageView_reviewcard_img5) {
//        this.imageView_reviewcard_img5 = imageView_reviewcard_img5;
//    }
//
//    public int getImageButton_like() {
//        return imageButton_like;
//    }
//
//    public void setImageButton_like(int imageButton_like) {
//        this.imageButton_like = imageButton_like;
//    }
//
//    public int getImageButton_comment() {
//        return imageButton_comment;
//    }
//
//    public void setImageButton_comment(int imageButton_comment) {
//        this.imageButton_comment = imageButton_comment;
//    }
//
//    public int getImageButton_bookmark() {
//        return imageButton_bookmark;
//    }
//
//    public void setImageButton_bookmark(int imageButton_bookmark) {
//        this.imageButton_bookmark = imageButton_bookmark;
//    }
//
//    int imageButton_like;
//    int imageButton_comment;
//    int imageButton_bookmark ;

}
