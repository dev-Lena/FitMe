package com.example.fitme;

public class Comment_ItemData {
    /**
     * 각 리뷰의 댓글 (리사이클러뷰) - 아이템
     **/
    // Comment_Activity, Comment_ItemData, Comment_Adapter 가 세트입니다.


    // 댓글 리사이클러뷰에 들어가는 아이템 xml에 있는 뷰 객체들
    private String  textView_comment_nickname;
    private String textView_comment_content;
    private String reviewUniqueCode ;
    private String imageView_comment_profile ;

    public String getReviewUniqueCode() {
        return reviewUniqueCode;
    }

    public void setReviewUniqueCode(String reviewUniqueCode) {
        this.reviewUniqueCode = reviewUniqueCode;
    }


    public String getTextView_comment_nickname() {
        return textView_comment_nickname;
    }

    public void setTextView_comment_nickname(String textView_comment_nickname) {
        this.textView_comment_nickname = textView_comment_nickname;
    }

    public String getTextView_comment_content() {
        return textView_comment_content;
    }

    public void setTextView_comment_content(String textView_comment_content) {
        this.textView_comment_content = textView_comment_content;
    }

    public String getImageView_comment_profile() {
        return imageView_comment_profile;
    }

    public void setImageView_comment_profile(String imageView_comment_profile) {
        this.imageView_comment_profile = imageView_comment_profile;
    }

    // 생성자
    public Comment_ItemData(String textView_comment_nickname, String textView_comment_content, String imageView_comment_profile, String reviewUniqueCode) {
           this.textView_comment_nickname = textView_comment_nickname ;
           this.textView_comment_content = textView_comment_content ;
           this.imageView_comment_profile = imageView_comment_profile ;
           this.reviewUniqueCode =reviewUniqueCode;
        }


}
