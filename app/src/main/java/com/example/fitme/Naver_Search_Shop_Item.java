package com.example.fitme;

public class Naver_Search_Shop_Item {
    /**
     * 네이버 검색 - 쇼핑 API (리사이클러뷰) - 아이템
     **/

    /**ExampleAdapter와 Naver_Search_Shop_Item, Naver_Search_Shop_Main, Naver_Search_Shop_Detail 자바 클래스는 네이버 Shop API 한세트임**/


    // title , link , image , mallName , lprice , hprice
    private String title;
    private String link;
    private String image;
    private String mallName;
    private String lprice;
    private String hprice;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getLprice() {
        return lprice;
    }

    public void setLprice(String lprice) {
        this.lprice = lprice;
    }

    public String getHprice() {
        return hprice;
    }

    public void setHprice(String hprice) {
        this.hprice = hprice;
    }

    public Naver_Search_Shop_Item(String title, String link, String image, String mallName, String lprice, String hprice) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.mallName = mallName;
        this.lprice = lprice;
        this.hprice = hprice;
    }

}
