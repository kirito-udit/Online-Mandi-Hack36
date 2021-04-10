package sample;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.scene.image.Image;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class UserTable {
    public static final String UTABLE = "USERTABLE";

    public static final String COLUMN_PHONE = "PhoneNumber";
    public static final String COLUMN_ID = "AadharNumber";
    public static final String COLUMN_NAME = "FullName";
    public static final String COLUMN_POBOX = "PoBox";
    public static final String COLUMN_DOB = "DOB";
    public static final String COLUMN_CITY = "City";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_PROFILE_PIC = "ProfilePicture";
    public static final String COLUMN_LATITUDE="Latitude";
    public static final String COLUMN_LONGITUDE="Longitude";

    public static final String INSERT_USER = "INSERT INTO " + UTABLE + " VALUES (?,?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_PHONE = "UPDATE " + UTABLE + " SET " + COLUMN_PHONE + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_NAME = "UPDATE " + UTABLE + " SET " + COLUMN_NAME + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_POBOX = "UPDATE " + UTABLE + " SET " + COLUMN_POBOX + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_CITY = "UPDATE " + UTABLE + " SET " + COLUMN_CITY + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_PASSWORD = "UPDATE " + UTABLE + " SET " + COLUMN_PASSWORD + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_PROFILE_PIC = "UPDATE " + UTABLE + " SET " + COLUMN_PROFILE_PIC + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_LONGITUDE="UPDATE "+UTABLE+" SET "+COLUMN_LATITUDE+" = ? WHERE "+COLUMN_ID+" = ? ";
    public static final String UPDATE_LATITUDE="UPDATE "+UTABLE+" SET "+COLUMN_LONGITUDE+" = ? WHERE "+COLUMN_ID+" = ? ";

    public static final String QUERY_PASSWORD_VERIFICATION=" SELECT "+COLUMN_PASSWORD+" FROM "+UTABLE+" WHERE "+COLUMN_PHONE+" = ? ";
    public static final String SELECT_FULL_NAME_WITH_THIS_PHONE_NUMBER="SELECT "+COLUMN_NAME+" FROM "+UTABLE+" WHERE "+COLUMN_PHONE+" = ? ";
    public static final String SELECT_PROFILE_PIC_WITH_THIS_PHONE_NUMBER="SELECT "+COLUMN_PROFILE_PIC+" FROM "+UTABLE+" WHERE "+COLUMN_PHONE+" = ? ";
    public static final String QUERY_ADDRESS="SELECT ( "+COLUMN_CITY+" , "+COLUMN_POBOX+" , "+COLUMN_LATITUDE+" , "+COLUMN_LONGITUDE+" ) WHERE "+
            COLUMN_ID+" = ? ";

    public Connection conn;
    public PreparedStatement insertUser;
    public PreparedStatement updatePhone;
    public PreparedStatement updateName;
    public PreparedStatement updatePoBox;
    public PreparedStatement updateCity;
    public PreparedStatement updatePassword;
    public PreparedStatement updateProfilePic;
    public PreparedStatement updateLatitde;
    public PreparedStatement updateLongitude;
    public PreparedStatement queryAddress;
    public PreparedStatement queryPasswordVerfication;
    public PreparedStatement selectFullNameWithThisPhoneNumber;
    public PreparedStatement selectProfilePicWithThisPhoneNumber;

    private static UserTable userTable;

    public static UserTable getInstance() {
        if(userTable == null)
            userTable = new UserTable();
        return userTable;
    }

    private UserTable() {

    }



    public boolean insertUser(String fullName, String phoneNumber, String password, String city, FileInputStream fis, String aadharNumber, Date dob, String poBox,
                              double latitude,double longitude) {
        try {
            conn=Server.getConnection();
            insertUser=conn.prepareStatement(INSERT_USER);
            insertUser.setString(1, fullName);
            insertUser.setString(2, phoneNumber);
            insertUser.setString(3, password);
            insertUser.setString(4, city);
            insertUser.setBinaryStream(5, fis, fis.available());
            insertUser.setString(6, aadharNumber);
            insertUser.setDate(7, dob);
            insertUser.setString(8, poBox);
            insertUser.setDouble(9,latitude);
            insertUser.setDouble(10,longitude);
            insertUser.executeUpdate();
            System.out.println("Insert user successful");
            return true;
        } catch (Exception e) {
            System.out.println("Insert user failed: " + e.getMessage());
            return false;
        }finally {
            try{
                insertUser.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in insertUser in UserTable");
                return false;
            }
        }
    }

    private boolean updateName(String aadharNumber, String newName) {
        try {
            conn=Server.getConnection();
            updateName=conn.prepareStatement(UPDATE_NAME);
            updateName.setString(1, newName);
            updateName.setString(2, aadharNumber);
            int affectedRows=updateName.executeUpdate();
            if(affectedRows==1) {
                System.out.println("Update Name Successful");
                return true;
            }
            else {
                System.out.println("Update Name failed: ");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Update Name failed: " + e.getMessage());
            return false;
        }finally{
            try{
                updateName.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in insertUser in UserTable");
            }
        }
    }

    private boolean updateCity(String aadharNumber, String newCity) {
        try {
            conn=Server.getConnection();
            updateCity=conn.prepareStatement(UPDATE_CITY);
            updateCity.setString(1, newCity);
            updateCity.setString(2, aadharNumber);
            int affectedRows=updateCity.executeUpdate();
            if(affectedRows==1) {
                System.out.println("Update city Successful");
                return true;
            }
            else {
                System.out.println("Update City failed: ");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Update City failed: " + e.getMessage());
            return false;
        }finally {
            try{
                updateCity.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in updateCity in UserTable");
            }
        }
    }

    private boolean updatePoBox(String aadharNumber, String newPoBox) {
        try {
            conn=Server.getConnection();
            updatePoBox=conn.prepareStatement(UPDATE_POBOX);
            updatePoBox.setString(1, newPoBox);
            updatePoBox.setString(2, aadharNumber);
            int affectedRows=updatePoBox.executeUpdate();
            if(affectedRows==1) {
                System.out.println("Update PoBox Successful");
                return true;
            }
            else {
                System.out.println("Update PoBox failed: ");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Update PoBox failed: " + e.getMessage());
            return false;
        }finally {
            try{
                updatePoBox.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in updatePoBox in UserTable");
            }
        }
    }

    private boolean updatePhone(String aadharNumber, String newPhoneNumber) {
        try {
            conn=Server.getConnection();
            updatePhone=conn.prepareStatement(UPDATE_PHONE);
            updatePhone.setString(1, newPhoneNumber);
            updatePhone.setString(2, aadharNumber);
            int affectedRows=updatePhone.executeUpdate();
            if(affectedRows==1){
                System.out.println("Update Phone Successful");
                return true;
            }else {
                System.out.println("Update Phone failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Update Phone failed: " + e.getMessage());
            return false;
        }finally {
            try{
                updatePhone.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in updatePhone in UserTable");
            }
        }
    }

    private boolean updateProfilePic(String aadharNumber, String local) {
        try {
            conn=Server.getConnection();
            updateProfilePic=conn.prepareStatement(UPDATE_PROFILE_PIC);
            FileInputStream fis = new FileInputStream(local);
            updateProfilePic.setBinaryStream(1, fis, fis.available());
            updateProfilePic.setString(2, aadharNumber);
            int affectedRows=updateProfilePic.executeUpdate();
            if(affectedRows==1){
                System.out.println("Update ProfilePic Successful");
                return true;
            }else {
                System.out.println("Update ProfilePic failed");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Update ProfilePic failed: " + e.getMessage());
            return false;
        }finally {
            try{
                updateProfilePic.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in updateProfilePic in UserTable");
            }
        }
    }

    private boolean updateLatitude(String aadharNumber, double newLatitude) {
        try {
            conn=Server.getConnection();
            updateLatitde=conn.prepareStatement(UPDATE_LATITUDE);
            updateLatitde.setDouble(1, newLatitude);
            updateLatitde.setString(2, aadharNumber);
            int affectedRows=updateLatitde.executeUpdate();
            if(affectedRows==1){
                System.out.println("Update Latitude Successful");
                return true;
            }else {
                System.out.println("Update Latitude failed");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Update latitude failed: " + e.getMessage());
            return false;
        }finally {
            try{
                updateLatitde.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in updateLatitue in UserTable");
            }
        }
    }
    private boolean updateLongitude(String aadharNumber, double newLongitude) {
        try {
            conn=Server.getConnection();
            updateLongitude=conn.prepareStatement(UPDATE_LONGITUDE);
            updateLongitude.setDouble(1, newLongitude);
            updateLongitude.setString(2, aadharNumber);
            int affectedRows=updateLongitude.executeUpdate();
            if(affectedRows==1){
                System.out.println("Update Longitude Successful");
                return true;
            }else {
                System.out.println("Update Longitude failed");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Update longitude failed: " + e.getMessage());
            return false;
        }finally {
            try{
                updateLongitude.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in updateLongitude in UserTable");
            }
        }
    }

    public FullNameProfilePic authentication(String password,String phoneNumber){
        try {
            conn=Server.getConnection();
            queryPasswordVerfication=conn.prepareStatement(QUERY_PASSWORD_VERIFICATION);
            queryPasswordVerfication.setString(1,phoneNumber);
            ResultSet results=queryPasswordVerfication.executeQuery();
            while(results.next()){
                if(results.getString(1).equals(password)) {
                    FullNameProfilePic fullNameProfilePic=new FullNameProfilePic(getFullName(phoneNumber), getProfilePic(phoneNumber));
                    return fullNameProfilePic;
                }
            }
            return null;
        }catch(Exception e){
            System.out.println("Exception occured while password verifcation during user login "+e.getMessage());
            return null;
        }finally {
            try{
                queryPasswordVerfication.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in authentication in UserTable");
            }
        }
    }

    public String getFullName(String phoneNumber){
        try{
            conn=Server.getConnection();
            selectFullNameWithThisPhoneNumber=conn.prepareStatement(SELECT_FULL_NAME_WITH_THIS_PHONE_NUMBER);
            selectFullNameWithThisPhoneNumber.setString(1,phoneNumber);
            ResultSet results=selectFullNameWithThisPhoneNumber.executeQuery();
            while(results.next()){
                return results.getString(1);
            }
            return null;
        }catch (Exception e){
            System.out.println("Exception occured while checking the full name of the user during user login "+e.getMessage());
            return null;
        }finally {
            try{
                selectFullNameWithThisPhoneNumber.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in selectFullNameWithThisPhoneNumber in UserTable");
            }
        }
    }
    public Image getProfilePic(String phoneNumber){
        try {
            conn=Server.getConnection();
            selectProfilePicWithThisPhoneNumber=conn.prepareStatement(SELECT_PROFILE_PIC_WITH_THIS_PHONE_NUMBER);
            selectProfilePicWithThisPhoneNumber.setString(1, phoneNumber);
            ResultSet results = selectProfilePicWithThisPhoneNumber.executeQuery();
            while (results.next()) {
                InputStream input = results.getBinaryStream(1);
                Image image = null;
                if (input != null && input.available() > 1) {
                    System.out.println("image available");
                    image = new Image(input);
                    System.out.println("image h:"+image.getHeight());
                    System.out.println("image w:"+image.getWidth());
                }
                return image;
            }
        }catch (Exception e){
            System.out.println("Exception occured while checking the profile pic of the user during user login ");
            e.printStackTrace();
        }finally {
            try{
                selectProfilePicWithThisPhoneNumber.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in selectProfilePicWithThisPhoneNumber in UserTable");
            }
        }
        return null;
    }
    public Address queryAddress(String aadharNumber){
        try {
            conn = Server.getConnection();
            queryAddress=conn.prepareStatement(QUERY_ADDRESS);
            queryAddress.setString(1,aadharNumber);
            ResultSet results=queryAddress.executeQuery();
            if(results.next()) {
                Address address=new Address(results.getString(1),results.getString(2),results.getDouble(3),results.getDouble(4));
                return address;
            }
        }catch (SQLException e){
            System.out.println("Error occured while fetching the user Address from user Table "+e.getMessage());
            return null;
        } finally {
            try{
                queryAddress.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources in selectProfilePicWithThisPhoneNumber in UserTable");
            }
        }
        return null;
    }
}
