package sample;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private String sellerPhone;
    private String buyerPhone;
    private String sellerName;
    private String buyerName;
    private String cropName;
    private int quantity;
    private int price;
    private Timestamp timestamp;

    public Transaction(int transactionId, String sellerPhone, String buyerPhone, String sellerName, String buyerName, String cropName, int quantity, int price, Timestamp timestamp) {
        this.transactionId = transactionId;
        this.sellerPhone = sellerPhone;
        this.buyerPhone = buyerPhone;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.cropName = cropName;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getCropName() {
        return cropName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
