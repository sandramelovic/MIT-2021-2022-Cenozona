package com.example.cenozona.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {

    String currentDate;
    String currentTime;
    String productName;
    String productPrice,productPriceRoda,productPriceDis,productPriceTempo,productPriceMaxi,productPriceLidl;
    String totalQuantity;
    int totalPrice,totalPriceRoda,totalPriceDis,totalPriceTempo,totalPriceLidl,totalPriceMaxi;
    String documentId;

    public MyCartModel() {
    }

    public MyCartModel(String currentDate, String currentTime, String productName, String productPrice, String totalQuantity, int totalPrice) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public MyCartModel(String currentDate, String currentTime, String productName, String productPrice, String productPriceRoda, String productPriceDis, String productPriceTempo, String productPriceMaxi, String productPriceLidl, String totalQuantity, int totalPrice, int totalPriceRoda, int totalPriceDis, int totalPriceTempo, int totalPriceLidl, int totalPriceMaxi, String documentId) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPriceRoda = productPriceRoda;
        this.productPriceDis = productPriceDis;
        this.productPriceTempo = productPriceTempo;
        this.productPriceMaxi = productPriceMaxi;
        this.productPriceLidl = productPriceLidl;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.totalPriceRoda = totalPriceRoda;
        this.totalPriceDis = totalPriceDis;
        this.totalPriceTempo = totalPriceTempo;
        this.totalPriceLidl = totalPriceLidl;
        this.totalPriceMaxi = totalPriceMaxi;
        this.documentId = documentId;
    }



    public int getTotalPriceRoda() {
        return totalPriceRoda;
    }

    public void setTotalPriceRoda(int totalPriceRoda) {
        this.totalPriceRoda = totalPriceRoda;
    }

    public int getTotalPriceDis() {
        return totalPriceDis;
    }

    public void setTotalPriceDis(int totalPriceDis) {
        this.totalPriceDis = totalPriceDis;
    }

    public int getTotalPriceTempo() {
        return totalPriceTempo;
    }

    public void setTotalPriceTempo(int totalPriceTempo) {
        this.totalPriceTempo = totalPriceTempo;
    }

    public int getTotalPriceLidl() {
        return totalPriceLidl;
    }

    public void setTotalPriceLidl(int totalPriceLidl) {
        this.totalPriceLidl = totalPriceLidl;
    }

    public int getTotalPriceMaxi() {
        return totalPriceMaxi;
    }

    public void setTotalPriceMaxi(int totalPriceMaxi) {
        this.totalPriceMaxi = totalPriceMaxi;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
