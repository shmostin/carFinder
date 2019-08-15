package com.austin.carfinder.finalProject;

public class CarInfoDataModel {

    private String price;
    private String miles;
    private String trim;
    private String dealerWebsite;
    private String carImageUrl;
    private String dealerUrl;



    public CarInfoDataModel(String price, String miles, String trim, String dealerWebsite, String carImageUrl, String dealerUrl) {
        this.price = price;
        this.miles = miles;
        this.trim = trim;
        this.dealerWebsite = dealerWebsite;
        this.carImageUrl = carImageUrl;
        this.dealerUrl = dealerUrl;
    }

    public String getCarImageUrl() {
        return carImageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getMiles() {
        return miles;
    }

    public String getTrim() {
        return trim;
    }

    public String getDealerWebsite() {
        return dealerWebsite;
    }

    public String getDealerUrl() { return dealerUrl; }
}
