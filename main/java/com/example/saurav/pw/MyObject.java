package com.example.saurav.pw;

public class MyObject {

    private String imgUrl;
    private String imgTitle;
    private String imgDest;
    private String mainUrl;
    private String data_Type;

    MyObject(String imgUrl, String imgTitle, String imgDest, String mainUrl, String data_Type) {
        this.imgUrl = imgUrl;
        this.imgTitle = imgTitle;
        this.imgDest = imgDest;
        this.mainUrl = mainUrl;
        this.data_Type = data_Type;
    }

    String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    String getImgDest() {
        return imgDest;
    }

    public void setImgDest(String imgDest) {
        this.imgDest = imgDest;
    }

    String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    String getData_Type() {
        return data_Type;
    }

    public void setData_Type(String data_Type) {
        this.data_Type = data_Type;
    }
}
