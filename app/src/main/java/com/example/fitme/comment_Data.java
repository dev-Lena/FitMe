package com.example.fitme;

public class comment_Data {


    // 댓글 리사이클러뷰에 들어가는 아이템 xml에 있는 뷰 객체들
        private String  textView_comment_nickname, textView_comment_content ;
        private int imageView_comment_profile ;


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

    public int getImageView_comment_profile() {
        return imageView_comment_profile;
    }

    public void setImageView_comment_profile(int imageView_comment_profile) {
        this.imageView_comment_profile = imageView_comment_profile;
    }

    // 생성자
    public comment_Data( String textView_comment_nickname, String textView_comment_content) {
           this.textView_comment_nickname = textView_comment_nickname ;
           this.textView_comment_content = textView_comment_content ;
//           this.imageView_comment_profile = imageView_comment_profile ;
        }


}
