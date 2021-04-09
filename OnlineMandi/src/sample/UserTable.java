package sample;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class UserTable {
    public static final String DB_NAME = "register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\databases\\" + DB_NAME;
    public static final String UTABLE = "USERTABLE";

    public static final String COLUMN_PHONE = "PhoneNumber";
    public static final String COLUMN_ID = "AadharNumber";
    public static final String COLUMN_NAME = "FullName";
    public static final String COLUMN_POBOX = "PoBox";
    public static final String COLUMN_DOB = "DOB";
    public static final String COLUMN_CITY = "City";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_PROFILE_PIC = "Password";

    public static final String INSERT_USER = "INSERT INTO " + UTABLE + " VALUES (?,?,?,?,?,?,?,?)";
    public static final String UPDATE_PHONE = "UPDATE " + UTABLE + " SET " + COLUMN_PHONE + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_NAME = "UPDATE " + UTABLE + " SET " + COLUMN_NAME + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_POBOX = "UPDATE " + UTABLE + " SET " + COLUMN_POBOX + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_CITY = "UPDATE " + UTABLE + " SET " + COLUMN_CITY + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_DOB = "UPDATE " + UTABLE + " SET " + COLUMN_DOB + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_PASSWORD = "UPDATE " + UTABLE + " SET " + COLUMN_PASSWORD + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String UPDATE_PROFILE_PIC = "UPDATE " + UTABLE + " SET " + COLUMN_PROFILE_PIC + " = ? WHERE " + COLUMN_ID + " = ?";
    public static final String QUERY_PASSWORD_VERIFICATION=" SELECT "+COLUMN_PASSWORD+" FROM "+UTABLE+" WHERE "+COLUMN_PHONE+" = ? ";
    public static final String SELECT_FULL_NAME_WITH_THIS_PHONE_NUMBER="SELECT "+COLUMN_NAME+" FROM "+UTABLE+" WHERE "+COLUMN_PHONE+" = ? ";
    public static final String SELECT_PROFILE_PIC_WITH_THIS_PHONE_NUMBER="SELECT "+COLUMN_PROFILE_PIC+" FROM "+UTABLE+" WHERE "+COLUMN_PHONE+" ? ";

    public Connection conn;
    public PreparedStatement insertUser;
    public PreparedStatement updatePhone;
    public PreparedStatement updateName;
    public PreparedStatement updatePoBox;
    public PreparedStatement updateCity;
    public PreparedStatement updatePassword;
    public PreparedStatement updateProfilePic;
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

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertUser = conn.prepareStatement(INSERT_USER);
            updatePhone = conn.prepareStatement(UPDATE_PHONE);
            updateName = conn.prepareStatement(UPDATE_NAME);
            updatePoBox = conn.prepareStatement(UPDATE_POBOX);
            updateCity = conn.prepareStatement(UPDATE_CITY);
            updatePassword = conn.prepareStatement(UPDATE_PASSWORD);
            updateProfilePic = conn.prepareStatement(UPDATE_PROFILE_PIC);
            queryPasswordVerfication=conn.prepareStatement(QUERY_PASSWORD_VERIFICATION);
            selectFullNameWithThisPhoneNumber=conn.prepareStatement(SELECT_FULL_NAME_WITH_THIS_PHONE_NUMBER);
            selectProfilePicWithThisPhoneNumber=conn.prepareStatement(SELECT_PROFILE_PIC_WITH_THIS_PHONE_NUMBER);
            return true;
        } catch (SQLException e) {
            System.out.println("Open failed!" + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (updatePoBox != null) {
                updatePoBox.close();
            }
            if (updateCity != null) {
                updateCity.close();
            }
            if (updatePassword != null) {
                updatePassword.close();
            }
            if (updateProfilePic != null) {
                updateProfilePic.close();
            }
            if (updateName != null) {
                updateName.close();
            }
            if (updatePhone != null) {
                updatePhone.close();
            }
            if (insertUser != null) {
                insertUser.close();
            }
            if (conn != null) {
                conn.close();
            }
            if(queryPasswordVerfication!=null){
                queryPasswordVerfication.close();
            }
            if(selectProfilePicWithThisPhoneNumber!=null){
                selectProfilePicWithThisPhoneNumber.close();
            }
            if(selectFullNameWithThisPhoneNumber!=null){
                selectFullNameWithThisPhoneNumber.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connections: " + e.getMessage());
        }
    }

    public void insertUser(String fullName, String phoneNumber, String password, String city, FileInputStream fis, String aadharNumber, Date dob, String poBox) {
        try {
            insertUser.setString(1, fullName);
            insertUser.setString(2, phoneNumber);
            insertUser.setString(3, password);
            insertUser.setString(4, city);
            insertUser.setBinaryStream(5, fis, fis.available());
            insertUser.setString(6, aadharNumber);
            insertUser.setDate(7, dob);
            insertUser.setString(8, poBox);
            insertUser.executeUpdate();
        } catch (Exception e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    private void updateName(String aadharNumber, String newName) {
        try {
            updateName.setString(1, newName);
            updateName.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update Name failed: " + e.getMessage());
        }
    }

    private void updateCity(String aadharNumber, String newCity) {
        try {
            updateCity.setString(1, newCity);
            updateCity.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update City failed: " + e.getMessage());
        }
    }

    private void updatePoBox(String aadharNumber, String newPoBox) {
        try {
            updatePoBox.setString(1, newPoBox);
            updatePoBox.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update PoBox failed: " + e.getMessage());
        }
    }

    private void updatePhone(String aadharNumber, String newPhoneNumber) {
        try {
            updatePhone.setString(1, newPhoneNumber);
            updatePhone.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update Phone failed: " + e.getMessage());
        }
    }

    private void updateProfilePic(String aadharNumber, String local) {
        try {
            FileInputStream fis = new FileInputStream(local);
            updateProfilePic.setBinaryStream(1, fis, fis.available());
            updateProfilePic.setString(2, aadharNumber);
            updateProfilePic.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update ProfilePic failed: " + e.getMessage());
        }
    }

    public FullNameProfilePic authentication(String password,String phoneNumber){
        try {
            queryPasswordVerfication.setString(1,phoneNumber);
            ResultSet results=queryPasswordVerfication.executeQuery();
            while(results.next()){
                if(results.getString(1)==password) {
                    FullNameProfilePic fullNameProfilePic=new FullNameProfilePic(getFullName(phoneNumber), getProfilePic(phoneNumber));
                    return fullNameProfilePic;
                }
            }
            return null;
        }catch(Exception e){
            System.out.println("Exception occured while password verifcation during user login "+e.getMessage());
            return null;
        }
    }

    private String getFullName(String phoneNumber){
        try{
            selectFullNameWithThisPhoneNumber.setString(1,phoneNumber);
            ResultSet results=selectFullNameWithThisPhoneNumber.executeQuery();
            while(results.next()){
                return results.getString(1);
            }
            return null;
        }catch (Exception e){
            System.out.println("Exception occured while checking the full name of the user during user login "+e.getMessage());
            return null;
        }
    }
    private Image getProfilePic(String phoneNumber){
        try{
            selectProfilePicWithThisPhoneNumber.setString(1,phoneNumber);
            ResultSet results=selectProfilePicWithThisPhoneNumber.executeQuery();
            while(results.next()){
                FileInputStream fis= (FileInputStream) results.getBinaryStream(1);
                Image image=new Image(fis);
                return image;
            }
            return null;
        }catch (Exception e){
            System.out.println("Exception occured while checking the profile pic of the user during user login "+e.getMessage());
            return null;
        }
    }
}
