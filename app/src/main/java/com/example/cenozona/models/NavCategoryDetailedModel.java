package com.example.cenozona.models;

public class NavCategoryDetailedModel {

    String name;
    String img_url;
    String type;
    String price;
    String maxiPrice;
    String disPrice;
    String lidlPrice;
    String tempoPrice;
    String rodaPrice;


    public NavCategoryDetailedModel() {
    }


    public NavCategoryDetailedModel(String name, String img_url, String type, String price, String maxiPrice, /*String disPrice, String lidlPrice, String tempoPrice,*/ String rodaPrice) {
        this.name = name;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
        this.maxiPrice = maxiPrice;
        this.rodaPrice = rodaPrice;
       /* this.disPrice = disPrice;
        this.lidlPrice = lidlPrice;
        this.tempoPrice = tempoPrice;*/
    }

    public String getName() {
        return name;
    }

    public String getMaxiPrice() {
        return maxiPrice;
    }

    public void setMaxiPrice(String maxiPrice) {
        this.maxiPrice = maxiPrice;
    }

    public String getDisPrice() {
        return disPrice;
    }

    public void setDisPrice(String disPrice) {
        this.disPrice = disPrice;
    }

    public String getLidlPrice() {
        return lidlPrice;
    }

    public void setLidlPrice(String lidlPrice) {
        this.lidlPrice = lidlPrice;
    }

    public String getTempoPrice() {
        return tempoPrice;
    }

    public void setTempoPrice(String tempoPrice) {
        this.tempoPrice = tempoPrice;
    }

    public String getRodaPrice() {
        return rodaPrice;
    }

    public void setRodaPrice(String rodaPrice) {
        this.rodaPrice = rodaPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
