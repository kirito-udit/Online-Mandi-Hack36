package sample;

//import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SellerTable {
    public static File dbFile = new File("./src/sample/Resources");
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+dbFile.getAbsolutePath()+"\\"+DB_NAME;
    public static final String SELL_TABLE = "SellTable";
    public static final String COLUMN_SELLER_PHONE = "SellerPhone";
    public static final String COLUMN_START_DATE = "StartDate";
    public static final String COLUMN_CROP_NAME = "CropName";
    public static final String COLUMN_QUANTITY = "Quantity";
    public static final String COLUMN_END_DATE = "EndDate";
    public static final String COLUMN_OFFER_ID = "OfferId";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_DESCRIPTION="Description";
    public static final String ADD_OFFER = " INSERT INTO " + SELL_TABLE + " ( "+COLUMN_CROP_NAME+" , "+COLUMN_QUANTITY+" , "+
            COLUMN_PRICE+" , "+COLUMN_START_DATE+" , "+COLUMN_END_DATE+" , "+COLUMN_SELLER_PHONE+" , "+COLUMN_DESCRIPTION+" ) VALUES ( ? , ? , ? , ? , ? , ? , ? ) ";
    public static final String UPDATE_OFFER_PRICE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_PRICE + " = ? WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_END_DATE = " UPDATE " + SELL_TABLE + " SET " + COLUMN_END_DATE + " = ? " + " WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_OFFER_QUANTITY = " UPDATE " + SELL_TABLE + " SET " + COLUMN_QUANTITY + " = ? " + " WHERE " +
            COLUMN_OFFER_ID + " = ? ";
    public static final String UPDATE_DESCRIPTION=" UPDATE "+SELL_TABLE+" SET "+COLUMN_DESCRIPTION+" = ? "+" WHERE "+
            COLUMN_OFFER_ID+" = ? ";
    public static final String GET_OFFER="SELECT * FROM "+SELL_TABLE+" WHERE "+COLUMN_OFFER_ID+" = ? ";
    public static final String DELETE_OFFER = " DELETE FROM " + SELL_TABLE + " WHERE " + COLUMN_OFFER_ID + " = ? ";
    public static final String GET_ALL_OFFERS = " SELECT * FROM " + SELL_TABLE;

    public Connection conn;
    public PreparedStatement addOffer;
    public PreparedStatement updateOfferPrice;
    public PreparedStatement updateOfferEndDate;
    public PreparedStatement updateOfferQuantity;
    public PreparedStatement updateDescription;
    public PreparedStatement deleteOffer;
    public PreparedStatement getAllOffers;
    public PreparedStatement getQuantity;

    private static SellerTable sellerTable;

    public static SellerTable getInstance() {
        if(sellerTable == null)
            sellerTable = new SellerTable();
        return sellerTable;
    }

    private SellerTable() {

    }


    public boolean deleteOffer(int offerId) {
        try {
            conn.close();
            conn = DriverManager.getConnection(CONNECTION_STRING);
            deleteOffer=conn.prepareStatement(DELETE_OFFER);
            deleteOffer.setInt(1, offerId);
            deleteOffer.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while deleting the offer " + e.getMessage());
            return false;
        }finally {
            try{
                deleteOffer.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources of deleteOffer "+e.getMessage());
            }
        }
    }

    public boolean addOffer(String cropName, int quantity, int price,
                            Date startDate, Date endDate,
                            String sellerPhone,String description) {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            addOffer=conn.prepareStatement(ADD_OFFER);
            addOffer.setString(1, cropName);
            addOffer.setInt(2, quantity);
            addOffer.setInt(3, price);
            addOffer.setDate(4, startDate);
            addOffer.setDate(5, endDate);
            addOffer.setString(6, sellerPhone);
            addOffer.setString(7,description);
            addOffer.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while adding an offer to the seller table ");
            e.printStackTrace();
            return false;
        }finally {
            try{
                addOffer.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while addOffer "+e.getMessage());
            }
        }
    }

    public boolean updateOfferPrice(int offerId,int price){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            updateOfferPrice=conn.prepareStatement(UPDATE_OFFER_PRICE);
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
        }finally {
            try{
                updateOfferPrice.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while updateOfferPrice "+e.getMessage());
            }
        }
    }
    public Offer getOffer(int offerId){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            getQuantity = conn.prepareStatement(GET_OFFER);
            getQuantity.setInt(1,offerId);
            ResultSet results = getQuantity.executeQuery();
            while(results.next()){
                Offer offer=new Offer(results.getInt(1),results.getString(2),results.getInt(3),results.getInt(4),
                        results.getDate(5),results.getDate(6),results.getString(7),results.getString(8));
                return offer;
            }
        }catch (SQLException e){
            System.out.println("Exception occured while fetching the answer in the SellTable class "+e.getMessage());
        }finally {
            try {
                getQuantity.close();
                conn.close();
            }catch(SQLException e){
                System.out.println("Exception occured while closing the resources in getQuantity method of the SellTable class "+e.getMessage());
                return null;
            }
        }
        return null;
    }

    public boolean updateOfferQuantity(int offerId,int quantity){
        try {
            conn.close();
            conn = DriverManager.getConnection(CONNECTION_STRING);
            updateOfferQuantity=conn.prepareStatement(UPDATE_OFFER_QUANTITY);
            updateOfferQuantity.setInt(1,quantity);
            updateOfferQuantity.setInt(2,offerId);
            int affectedRows=updateOfferQuantity.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the offer quantity");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the offer quantity "+e.getMessage());
            return false;
        }finally {
            try{
                updateOfferQuantity.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while updateOfferQuantity "+e.getMessage());
            }
        }
    }
    public boolean updateOfferEndDate(int offerId,Date endDate){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            updateOfferEndDate=conn.prepareStatement(UPDATE_OFFER_END_DATE);
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
        }finally {
            try{
                updateOfferEndDate.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while updateOfferEndDate "+e.getMessage());
            }
        }
    }

    public boolean updateDescription(int offerId,String description){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            updateDescription=conn.prepareStatement(UPDATE_DESCRIPTION);
            updateOfferPrice.setString(1,description);
            updateOfferPrice.setInt(2,offerId);
            int affectedRows=updateDescription.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the offer description");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the offer description "+e.getMessage());
            return false;
        }finally {
            try{
                updateDescription.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing updateDescription "+e.getMessage());
            }
        }
    }
    //this function returns the list of all the offers of a particular seller
    public ArrayList<Offer> getAllOffers() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            getAllOffers=conn.prepareStatement(GET_ALL_OFFERS);
            ArrayList <Offer> offers = new ArrayList<Offer>();
            ResultSet resultSet = getAllOffers.executeQuery();
            while (resultSet.next()) {
                Offer offer = new Offer(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
                        resultSet.getInt(4), resultSet.getDate(5), resultSet.getDate(6),
                        resultSet.getString(7),resultSet.getString(8));
                System.out.println(offer.getOfferId());
                System.out.println(offer.getStartDate());
                System.out.println(offer.getEndDate());
                offers.add(offer);
            }
            return offers;
        }
        catch (SQLException e) {
            System.out.println("Couldn't fetch all offers: "+e.getMessage());
        }finally {
            try{
                getAllOffers.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while getAllOffers "+e.getMessage());
            }
        }
        return null;
    }
}
