package sample;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class UserTable {
    public static final String DB_NAME = "Register.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\databases\\" + DB_NAME;
    public static final String UTABLE = "USERTABLE";

    public static final String COLUMN_PHONE = "PhoneNumber";
    public static final String COLUMN_ID = "AadharNumber";
    public static final String COLUMN_NAME = "Name";
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

    public Connection conn;
    public PreparedStatement insertUser;
    public PreparedStatement updatePhone;
    public PreparedStatement updateName;
    public PreparedStatement updatePoBox;
    public PreparedStatement updateCity;
    public PreparedStatement updatePassword;
    public PreparedStatement updateProfilePic;

    private static UserTable userTable;
    private UserTable(){

    }
    public static UserTable getInstance() {
        if(userTable==null) userTable = new UserTable();
        return userTable;
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
        } catch (SQLException e) {
            System.out.println("Couldn't close connections: " + e.getMessage());
        }
    }

    public void insertUser(String fullName, String phoneNumber, String password, String city, String local, String aadharNumber, Date dob, String poBox) {
        try {
            insertUser.setString(1, fullName);
            insertUser.setString(2, phoneNumber);
            insertUser.setString(3, password);
            insertUser.setString(4, city);
            FileInputStream fis = new FileInputStream(local);
            insertUser.setBinaryStream(5, fis, fis.available());
            insertUser.setString(6, aadharNumber);
            insertUser.setDate(7, dob);
            insertUser.setString(8, poBox);
            insertUser.executeQuery();
        } catch (Exception e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    public void updateName(String aadharNumber, String newName) {
        try {
            updateName.setString(1, newName);
            updateName.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update Name failed: " + e.getMessage());
        }
    }

    public void updateCity(String aadharNumber, String newCity) {
        try {
            updateCity.setString(1, newCity);
            updateCity.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update City failed: " + e.getMessage());
        }
    }

    public void updatePoBox(String aadharNumber, String newPoBox) {
        try {
            updatePoBox.setString(1, newPoBox);
            updatePoBox.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update PoBox failed: " + e.getMessage());
        }
    }

    public void updatePhone(String aadharNumber, String newPhoneNumber) {
        try {
            updatePhone.setString(1, newPhoneNumber);
            updatePhone.setString(2, aadharNumber);
        } catch (SQLException e) {
            System.out.println("Update Phone failed: " + e.getMessage());
        }
    }

    public void updateProfilePic(String aadharNumber, String local) {
        try {
            FileInputStream fis = new FileInputStream(local);
            updateProfilePic.setBinaryStream(1, fis, fis.available());
            updateProfilePic.setString(2, aadharNumber);
            updateProfilePic.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update ProfilePic failed: " + e.getMessage());
        }
    }
}
