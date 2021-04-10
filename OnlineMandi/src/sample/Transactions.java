package sample;
//import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Transactions {
    public static File dbFile = new File("./src/sample/Resources");
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + dbFile.getAbsolutePath() + "\\" + DB_NAME;
    public static final String TRANSACTION_TABLE="Transactions";
    public static final String COLUMN_SELLER_PHONE="SellerPhone";
    public static final String COLUMN_BUYER_PHONE="BuyerPhone";
    public static final String COLUMN_TRANSACTION_ID="TransactionId";
    public static final String COLUMN_CROP_NAME="CropName";
    public static final String COLUMN_QUANTITY="Quantity";
    public static final String COLUMN_PRICE="Price";
    public static final String COLUMN_TRANSACTION_TIME="TransactionTime";
    public static final String ADD_TRANSACTION="INSERT INTO "+TRANSACTION_TABLE+" VALUES ( NULL, ? , ? , ? , ? , ? , ? ) ";
    public static final String QUERY_SELL_TRANSACTION="SELECT * FROM "+TRANSACTION_TABLE+" WHERE "+COLUMN_SELLER_PHONE+" = ? ";
    public static final String QUERY_BUY_TRANSACTION="SELECT * FROM "+TRANSACTION_TABLE+" WHERE "+COLUMN_BUYER_PHONE+" = ? ";

    public Connection conn;
    public PreparedStatement addTransaction;
    public PreparedStatement getSellTransactionWithPhoneNumber;
    public PreparedStatement getBuyTransactionWithPhoneNumber;

    private static Transactions transactions;

    public static Transactions getInstance() {
        if (transactions == null)
            transactions = new Transactions();
        return transactions;
    }

    private Transactions() {

    }
    public boolean processATransaction(String buyerPhone,int offerId,int quantity,Timestamp timestamp){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            Offer offer = SellerTable.getInstance().getOffer(offerId);
            addTransaction=conn.prepareStatement(ADD_TRANSACTION);
            addTransaction.setString(1,offer.getSellerPhone());
            addTransaction.setString(2,buyerPhone);
            addTransaction.setString(3,offer.getCropName());
            addTransaction.setInt(4,quantity);
            addTransaction.setInt(5,offer.getPrice());
            addTransaction.setTimestamp(6,timestamp);
            addTransaction.executeUpdate();
            int current_quantity = offer.getQuantity();
            if(current_quantity>quantity) {
                SellerTable.getInstance().updateOfferQuantity(offerId, current_quantity - quantity);
            }else {
                SellerTable.getInstance().deleteOffer(offerId);
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while buying an offer in the Transactions class "+e.getMessage());
            return false;
        }finally {
            try {
                addTransaction.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in the buy method of the Transactions class");
            }
        }
    }
    public ArrayList<Transaction> getSellTransaction(String sellerPhone){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            getSellTransactionWithPhoneNumber = conn.prepareStatement(QUERY_SELL_TRANSACTION);
            getSellTransactionWithPhoneNumber.setString(1,sellerPhone);
            ResultSet results=getSellTransactionWithPhoneNumber.executeQuery();
            ArrayList<Transaction>ans=new ArrayList<>();
            while(results.next()){
                Transaction transaction=new Transaction(results.getInt(1),results.getString(2),results.getString(3),
                        UserTable.getInstance().getFullName(results.getString(2)),
                        UserTable.getInstance().getFullName(results.getString(3)),
                        results.getString(4), results.getInt(5),results.getInt(6),results.getTimestamp(7));
                ans.add(transaction);
            }
            return ans;
        }catch (SQLException e){
            System.out.println("Error occured while fetching the sell Transactions of a user in the getSellTransaction method of Transactions class "+e.getMessage());
            return null;
        }finally {
            try{
                getSellTransactionWithPhoneNumber.close();
                conn.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in the getSellTransaction method of Transactions class "+e.getMessage());
            }
        }
    }
    public ArrayList<Transaction> getBuyTransaction(String buyerPhone){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            getBuyTransactionWithPhoneNumber = conn.prepareStatement(QUERY_BUY_TRANSACTION);
            getBuyTransactionWithPhoneNumber.setString(1,buyerPhone);
            ResultSet results=getBuyTransactionWithPhoneNumber.executeQuery();
            ArrayList<Transaction>ans=new ArrayList<>();
            while(results.next()){
                Transaction transaction=new Transaction(results.getInt(1),results.getString(2),results.getString(3),
                        UserTable.getInstance().getFullName(results.getString(2)),
                        UserTable.getInstance().getFullName(results.getString(3)),
                        results.getString(4), results.getInt(5),results.getInt(6),results.getTimestamp(7));
                ans.add(transaction);
            }
            return ans;
        }catch (SQLException e){
            System.out.println("Error occured while fetching the sell Transactions of a user in the getBuyTransaction method of Transactions class "+e.getMessage());
            return null;
        }finally {
            try{
                getBuyTransactionWithPhoneNumber.close();
                conn.close();
            }catch (SQLException e) {
                System.out.println("Error occured while closing the resources in the getBuyTransaction method of Transactions class " + e.getMessage());
            }
        }
    }

}
