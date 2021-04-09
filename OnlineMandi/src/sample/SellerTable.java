package sample;
import java.sql.*;
public class SellerTable {
    public static final String DB_NAME = "SELLER.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\databases\\" + DB_NAME;
    public static final String SELL_TABLE = "SellTable";
    public static final String COLUMN_SELLER_PHONE = "SellerPhone";
    public static final String COLUMN_SELLER_NAME = "SellerName";
    public static final String COLUMN_START_DATE = "StartDate";
    public static final String COLUMN_CROP_NAME = "CropName";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_END_DATE = "EndDate";
    public static final String COLUMN_OFFER_ID = "OfferId";
    public static final String COLUMN_PRICE = "Price";

    public static final String ADD_OFFER = " INSERT INTO " + SELL_TABLE + " VALUES ( ? , ? , ? , ? , ? , ? , ? , ? ) ";
    public static final String ADD_OFFER = " INSERT INTO " + SELL_TABLE + " VALUES ( ? , ? , ? , ? , ? , ? , ? ) ";
    public static final String UPDATE_OFFER_PRICE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_PRICE + " = ? WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_END_DATE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_END_DATE + " = ? " + " WHERE " +
@@ -96,14 +96,13 @@ public boolean deleteOffer(int offerId) {
    public boolean addOffer(int offerId, String cropName, int quantity, int price,
                             Date startDate, Date endDate, String sellerName, String sellerPhone) {
        try {
            addOffer.setInt(1, offerId);
            addOffer.setString(2, cropName);
            addOffer.setInt(3, quantity);
            addOffer.setInt(4, price);
            addOffer.setDate(5, startDate);
            addOffer.setDate(6, endDate);
            addOffer.setString(7, sellerName);
            addOffer.setString(8, sellerPhone);
            addOffer.setString(1, cropName);
            addOffer.setInt(2, quantity);
            addOffer.setInt(3, price);
            addOffer.setDate(4, startDate);
            addOffer.setDate(5, endDate);
            addOffer.setString(6, sellerName);
            addOffer.setString(7, sellerPhone);
            addOffer.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while adding an offer to the seller table " + e.getMessage());
            return false;
        }
    }
    public boolean updateOfferPrice(int offerId,int price){
        try {
            updateOfferPrice.setInt(1,price);
            updateOfferPrice.setInt(2,offerId);
            int affectedRows=updateOfferPrice.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the offer price");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the offer price "+e.getMessage());
            return false;
        }
    }
    public boolean updateOfferQuantity(int offerId,int quantity){
        try {
            updateOfferPrice.setInt(1,quantity);
            updateOfferPrice.setInt(2,offerId);
            int affectedRows=updateOfferPrice.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the offer quantity");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the offer quantity "+e.getMessage());
            return false;
        }
    }
    public boolean updateOfferEndDate(int offerId,Date endDate){
        try {
            updateOfferPrice.setDate(1,endDate);
            updateOfferPrice.setInt(2,offerId);
            int affectedRows=updateOfferEndDate.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the offer end date");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the end date "+e.getMessage());
            return false;
        }
    }
}
