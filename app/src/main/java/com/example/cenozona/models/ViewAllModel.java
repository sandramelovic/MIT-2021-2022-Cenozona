package com.example.cenozona.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {

    String name,minImg;
    String description;
    String rating;
    String img_url;
    String type;
    int price, rodaPrice, maxiPrice, disPrice, lidlPrice, tempoPrice;
    String documentId;

    public ViewAllModel(String name, String description, String rating, String img_url, String type, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
    }

    public ViewAllModel(String name, String description, String type, int rodaPrice, int maxiPrice, int disPrice, int lidlPrice, int tempoPrice) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.rodaPrice = rodaPrice;
        this.maxiPrice = maxiPrice;
        this.disPrice = disPrice;
        this.lidlPrice = lidlPrice;
        this.tempoPrice = tempoPrice;
    }

    public ViewAllModel(String name, String description, String rating, String img_url, String type, int price, int rodaPrice, int maxiPrice, int disPrice, int lidlPrice, int tempoPrice) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
        this.rodaPrice = rodaPrice;
        this.maxiPrice = maxiPrice;
        this.disPrice = disPrice;
        this.lidlPrice = lidlPrice;
        this.tempoPrice = tempoPrice;
    }

    public ViewAllModel(String name, String minImg, String description, String rating, String img_url, String type, int price, int rodaPrice, int maxiPrice, int disPrice, int lidlPrice, int tempoPrice, String documentId) {
        this.name = name;
        this.minImg = minImg;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
        this.rodaPrice = rodaPrice;
        this.maxiPrice = maxiPrice;
        this.disPrice = disPrice;
        this.lidlPrice = lidlPrice;
        this.tempoPrice = tempoPrice;
        this.documentId = documentId;
    }

    public ViewAllModel() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getMinImg() {
        return minImg;
    }

    public void setMinImg(String minImg) {
        this.minImg = minImg;
    }

    public int getDisPrice() {
        return disPrice;
    }

    public void setDisPrice(int disPrice) {
        this.disPrice = disPrice;
    }

    public int getLidlPrice() {
        return lidlPrice;
    }

    public void setLidlPrice(int lidlPrice) {
        this.lidlPrice = lidlPrice;
    }

    public int getTempoPrice() {
        return tempoPrice;
    }

    public void setTempoPrice(int tempoPrice) {
        this.tempoPrice = tempoPrice;
    }

    public int getRodaPrice() {
        return rodaPrice;
    }

    public void setRodaPrice(int rodaPrice) {
        this.rodaPrice = rodaPrice;
    }

    public int getMaxiPrice() {
        return maxiPrice;
    }

    public void setMaxiPrice(int maxiPrice) {
        this.maxiPrice = maxiPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
