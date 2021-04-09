package sample;

import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SellerTable {
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\databases\\" + DB_NAME;
    public static File dbFile = new File("./src/sample/Resources");
    public static final String DB_NAME = "REGISTER.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+dbFile.getAbsolutePath()+"\\"+DB_NAME;
    public static final String SELL_TABLE = "SellTable";
    public static final String COLUMN_SELLER_PHONE = "SellerPhone";
    public static final String COLUMN_SELLER_NAME = "SellerName";
    public static final String COLUMN_START_DATE = "StartDate";
    public static final String COLUMN_CROP_NAME = "CropName";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_END_DATE = "EndDate";
    public static final String COLUMN_OFFER_ID = "OfferId";
    public static final String COLUMN_PRICE = "Price";
    public static final String ADD_OFFER = " INSERT INTO " + SELL_TABLE + " VALUES ( NULL, ? , ? , ? , ? , ? , ? , ? ) ";
    public static final String UPDATE_OFFER_PRICE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_PRICE + " = ? WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_END_DATE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_END_DATE + " = ? " + " WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_QUANTITY = " UPDATE " + SELL_TABLE + " SET " + COLUMN_QUANTITY + " = ? " + " WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String DELETE_OFFER = " DELETE FROM " + SELL_TABLE + " WHERE " + COLUMN_OFFER_ID + " = ? ";
    public static final String GET_ALL_OFFERS = " SELECT * FROM " + SELL_TABLE;

    public Connection conn;
    public PreparedStatement addOffer;
    public PreparedStatement updateOfferPrice;
    public PreparedStatement updateOfferEndDate;
    public PreparedStatement updateOfferQuantity;
    public PreparedStatement deleteOffer;
    public PreparedStatement getAllOffers;

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
            this.conn = Server.conn;
            System.out.println(dbFile.getAbsolutePath());
            System.out.println(CONNECTION_STRING);
            conn = Server.conn;
            addOffer = conn.prepareStatement(ADD_OFFER);
            updateOfferPrice = conn.prepareStatement(UPDATE_OFFER_PRICE);
            updateOfferEndDate = conn.prepareStatement(UPDATE_OFFER_END_DATE);
            updateOfferQuantity = conn.prepareStatement(UPDATE_OFFER_QUANTITY);
            deleteOffer = conn.prepareStatement(DELETE_OFFER);
            getAllOffers = conn.prepareStatement(GET_ALL_OFFERS);
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while opening the seller database " + e.getMessage());
            return false;
        }
    }
    public boolean close() {
        try {
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
            if (getAllOffers != null) {
                getAllOffers.close();
            }
            if (conn != null) {
                conn.close();
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
    public boolean addOffer(String cropName, int quantity, int price,
                             Date startDate, Date endDate, String sellerName, String sellerPhone) {
        try {
            addOffer.setString(1, cropName);
            addOffer.setInt(2, quantity);
            addOffer.setInt(3, price);
            addOffer.setDate(4, startDate);
            addOffer.setDate(5, endDate);
            addOffer.setString(6, sellerName);
            addOffer.setString(7, sellerPhone);
            addOffer.executeUpdate();
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

    public ArrayList<Offer> getAllOffers() {
        try {
            ArrayList <Offer> offers = new ArrayList<Offer>();
            ResultSet resultSet = getAllOffers.executeQuery();
            while (resultSet.next()) {
//                System.out.println("1234567");
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getInt(3));
//                System.out.println(resultSet.getInt(4));
//                System.out.println(resultSet.getDate(5).toString());
//                System.out.println(resultSet.getDate(6).toString());
//                System.out.println(resultSet.getString(7));
//                System.out.println(resultSet.getString(8));

                Offer offer = new Offer(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4),resultSet.getDate(5),resultSet.getDate(6),resultSet.getString(7),resultSet.getString(8));
                System.out.println(offer.getOfferId());
                System.out.println(offer.getStartDate());
                System.out.println(offer.getEndDate());
                offers.add(offer);
            }
            return offers;
        }
        catch (SQLException e) {
            System.out.println("Couldn't fetch all offers: "+e.getMessage());
        }
    //this function returns the list of all the offers of a particular seller
    ObservableList<Offer> getAllOffers(){

        return null;
    }
}
