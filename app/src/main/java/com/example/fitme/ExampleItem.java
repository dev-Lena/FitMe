package com.example.fitme;

public class ExampleItem {

    // title , link , image , mallName , lprice , hprice
    private String title;
    private String link;
    private String image;
    private String mallName;
    private int lprice;
    private int hprice;

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

    public int getLprice() {
        return lprice;
    }

    public void setLprice(int lprice) {
        this.lprice = lprice;
    }

    public int getHprice() {
        return hprice;
    }

    public void setHprice(int hprice) {
        this.hprice = hprice;
    }

    public ExampleItem(String title, String link, String image, String mallName, int lprice, int hprice) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.mallName = mallName;
        this.lprice = lprice;
        this.hprice = hprice;
    }

}
