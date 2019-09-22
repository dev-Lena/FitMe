package com.example.fitme;

public class InsightItemData {


//text_view_fitname,image_view, textView_waist,textView_sero,textView_bugGi,textView_long,textView_ancle


    // title , link , image , mallName , lprice , hprice
    private String text_view_fitname;
    private String image_view;
    private String textView_waist;
    private String textView_sero;
    private String textView_bugGi;
    private String textView_long;
    private String textView_ancle;


    public String getText_view_fitname() {
        return text_view_fitname;
    }

    public void setText_view_fitname(String text_view_fitname) {
        this.text_view_fitname = text_view_fitname;
    }

    public String getImage_view() {
        return image_view;
    }

    public void setImage_view(String image_view) {
        this.image_view = image_view;
    }

    public String getTextView_waist() {
        return textView_waist;
    }

    public void setTextView_waist(String textView_waist) {
        this.textView_waist = textView_waist;
    }

    public String getTextView_sero() {
        return textView_sero;
    }

    public void setTextView_sero(String textView_sero) {
        this.textView_sero = textView_sero;
    }

    public String getTextView_bugGi() {
        return textView_bugGi;
    }

    public void setTextView_bugGi(String textView_bugGi) {
        this.textView_bugGi = textView_bugGi;
    }

    public String getTextView_long() {
        return textView_long;
    }

    public void setTextView_long(String textView_long) {
        this.textView_long = textView_long;
    }

    public String getTextView_ancle() {
        return textView_ancle;
    }

    public void setTextView_ancle(String textView_ancle) {
        this.textView_ancle = textView_ancle;
    }

    public InsightItemData(String text_view_fitname, String textView_waist, String textView_sero, String textView_bugGi, String textView_long, String image_view, String textView_ancle) {
        this.text_view_fitname = text_view_fitname;
        this.image_view = image_view;
        this.textView_waist = textView_waist;
        this.textView_sero = textView_sero;
        this.textView_long = textView_long;
        this.textView_bugGi = textView_bugGi;
        this.textView_ancle = textView_ancle;
    }

}
