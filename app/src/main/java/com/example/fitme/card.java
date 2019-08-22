package com.example.fitme;


public class card {
    private String imgURL;
    private String title;

    // 리뷰카드 리사이클러뷰 추가 제거 수정에 필요한 메소드 만드는 클래스

    public card(String imgURL, String title){
        this.imgURL = imgURL ;
        this.title = title;
    }
    public String getImgURL(){
        return imgURL;
    }
    public void setImgURL(String imgURL){
        this.imgURL = imgURL ;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(){
        this.title = title ;
    }

}
