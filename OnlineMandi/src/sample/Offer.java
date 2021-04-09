package sample;

import java.util.Date;

public class Offer {
    private int offerId;
    private String cropName;
    private int quantity;
    private int price;
    private Date startDate;
    private Date endDate;
    private String sellerName;
    private String sellerPhone;
//    private String description;
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Offer(int offerId, String cropName, int quantity, int price, Date startDate, Date endDate, String sellerName, String sellerPhone) {
        this.offerId = offerId;
        this.cropName = cropName;
        this.quantity = quantity;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }
}
