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
    public static final String UPDATE_OFFER_PRICE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_PRICE + " = ? WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_END_DATE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_END_DATE + " = ? " + " WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_QUANTITY = " UPDATE " + SELL_TABLE + " SET " + COLUMN_QUANTITY + " = ? " + " WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String DELETE_OFFER = " DELETE FROM " + SELL_TABLE + " WHERE " + COLUMN_OFFER_ID + " = ? ";

    public Connection conn;
    public PreparedStatement addOffer;
    public PreparedStatement updateOfferPrice;
    public PreparedStatement updateOfferEndDate;
    public PreparedStatement updateOfferQuantity;
    public PreparedStatement deleteOffer;

    private static SellerTable sellerTable;

    public static SellerTable getInstance() {
        if(sellerTable == null)
            sellerTable = new SellerTable();
        return sellerTable;
    }

    private SellerTable() {

    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            addOffer = conn.prepareStatement(ADD_OFFER);
            updateOfferPrice = conn.prepareStatement(UPDATE_OFFER_PRICE);
            updateOfferEndDate = conn.prepareStatement(UPDATE_OFFER_END_DATE);
            updateOfferQuantity = conn.prepareStatement(UPDATE_OFFER_QUANTITY);
            deleteOffer = conn.prepareStatement(DELETE_OFFER);
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while opening the seller database " + e.getMessage());
            return false;
        }
    }

    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (addOffer != null) {
                addOffer.close();
            }
            if (deleteOffer != null) {
                deleteOffer.close();
            }
            if (updateOfferQuantity != null) {
                updateOfferQuantity.close();
            }
            if (updateOfferPrice != null) {
                updateOfferPrice.close();
            }
            if (updateOfferEndDate != null) {
                updateOfferEndDate.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while closing the resources of the seller database " + e.getMessage());
            return false;
        }
    }

    public boolean deleteOffer(int offerId) {
        try {
            deleteOffer.setInt(1, offerId);
            deleteOffer.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while deleting the offer " + e.getMessage());
            return false;
        }
    }

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
            addOffer.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while adding an offer to the seller table " + e.getMessage());
            return false;
        }
    }
}
