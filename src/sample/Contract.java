package sample;

//import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Contract {
    public static final String CONTRACT_TABLE = "ContractTable";
    public static final String COLUMN_CONTRACT_ID="ContractId";
    public static final String COLUMN_CONTRACTOR_PHONE = "ContractorPhone";
    public static final String COLUMN_CROP_NAME= "CropName";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_DESCRIPTION="Description";
    public static final String ADD_CONTRACT = " INSERT INTO " + CONTRACT_TABLE + " ( "+COLUMN_CONTRACTOR_PHONE+
            " , "+COLUMN_CROP_NAME+" , "+COLUMN_PRICE+" , "+COLUMN_DESCRIPTION+" ) VALUES ( ? , ? , ? , ? ) ";
    public static final String UPDATE_CONTRACT_PRICE = " UPDATE " + CONTRACT_TABLE + " SET " + COLUMN_PRICE + " = ? WHERE " +
            COLUMN_CONTRACT_ID + " = ? ";
    public static final String UPDATE_DESCRIPTION=" UPDATE "+CONTRACT_TABLE+" SET "+COLUMN_DESCRIPTION+" = ? "+" WHERE "+
            COLUMN_CONTRACT_ID+" = ? ";
    public static final String GET_CONTRACT="SELECT * FROM "+CONTRACT_TABLE+" WHERE "+COLUMN_CONTRACT_ID+" = ? ";
    public static final String DELETE_CONTRACT = " DELETE FROM " + CONTRACT_TABLE + " WHERE " + COLUMN_CONTRACT_ID + " = ? ";
    public static final String GET_ALL_CONTRACTS = " SELECT * FROM " + CONTRACT_TABLE+" WHERE "+COLUMN_CONTRACTOR_PHONE +" <> ?";

    public Connection conn;
    public PreparedStatement addContract;
    public PreparedStatement updateContractPrice;
    public PreparedStatement updateDescription;
    public PreparedStatement deleteContract;
    public PreparedStatement getContract;
    public PreparedStatement getAllContracts;

    private static Contract contract;

    public static Contract getInstance() {
        if(contract == null)
            contract = new Contract();
        return contract;
    }

    private Contract() {

    }


    public boolean deleteContract(int contractId) {
        try {
            conn=Server.getConnection();
            deleteContract=conn.prepareStatement(DELETE_CONTRACT);
            deleteContract.setInt(1, contractId);
            deleteContract.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while deleting the contract " + e.getMessage());
            return false;
        }finally {
            try{
                deleteContract.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing the resources of deleteContract "+e.getMessage());
            }
        }
    }

    public boolean addContract(String cropName,int price,
                            String sellerPhone,String description) {
        try {
            conn=Server.getConnection();
            addContract=conn.prepareStatement(ADD_CONTRACT);
            addContract.setString(1, sellerPhone);
            addContract.setString(2, cropName);
            addContract.setInt(3, price);
            addContract.setString(4,description);
            addContract.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error occured while adding a contract to the Contract table ");
            e.printStackTrace();
            return false;
        }finally {
            try{
                addContract.close();
            }catch (SQLException e){
                System.out.println("Error occured while addContract "+e.getMessage());
            }
        }
    }

    public boolean updateContractPrice(int contractId,int price){
        try {
            conn=Server.getConnection();
            updateContractPrice=conn.prepareStatement(UPDATE_CONTRACT_PRICE);
            updateContractPrice.setInt(1,price);
            updateContractPrice.setInt(2,contractId);
            int affectedRows=updateContractPrice.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the contract price");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the contract price "+e.getMessage());
            return false;
        }finally {
            try{
                updateContractPrice.close();
            }catch (SQLException e){
                System.out.println("Error occured while updateContractPrice "+e.getMessage());
            }
        }
    }

    public boolean updateDescription(int contractId,String description){
        try {
            conn=Server.getConnection();
            updateDescription=conn.prepareStatement(UPDATE_DESCRIPTION);
            updateDescription.setString(1,description);
            updateDescription.setInt(2,contractId);
            int affectedRows=updateDescription.executeUpdate();
            if(affectedRows!=1){
                throw new SQLException("Couldn't update the contract description");
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error occured while updating the contract description "+e.getMessage());
            return false;
        }finally {
            try{
                updateDescription.close();
            }catch (SQLException e){
                System.out.println("Error occured while closing updateDescription "+e.getMessage());
            }
        }
    }

    public ContractDummy getContract(int contractId){
        try {
            conn=Server.getConnection();
            getContract= conn.prepareStatement(GET_CONTRACT);
            getContract.setInt(1,contractId);
            ResultSet results = getContract.executeQuery();
            while(results.next()){
                ContractDummy contractDummy=new sample.ContractDummy(results.getInt(1),results.getString(2),results.getString(3),results.getInt(4),
                        results.getString(5));
                return contractDummy;
            }
        }catch (SQLException e){
            System.out.println("Exception occured while fetching the contract in the ContractTable class "+e.getMessage());
        }finally {
            try {
                getContract.close();
            }catch(SQLException e){
                System.out.println("Exception occured while closing the resources in getContract method of the ContractTable class "+e.getMessage());
                return null;
            }
        }
        return null;
    }




    //this function returns the list of all the offers of a particular seller
    public ArrayList<ContractDummy> getAllContracts(String phoneNumber) {
        try {
            conn=Server.getConnection();
            getAllContracts=conn.prepareStatement(GET_ALL_CONTRACTS);
            getAllContracts.setString(1,phoneNumber);
            ArrayList<ContractDummy> contract_list = new ArrayList<>();
            ResultSet resultSet = getAllContracts.executeQuery();
            while (resultSet.next()) {
                ContractDummy contractDummy= new ContractDummy(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5));
                System.out.println(contractDummy.getPrice());
                contract_list.add(contractDummy);
            }
            return contract_list;
        }
        catch (SQLException e) {
            System.out.println("Couldn't fetch all contracts: "+e.getMessage());
        }finally {
            try{
                getAllContracts.close();
            }catch (SQLException e){
                System.out.println("Error occured while getAllContracts "+e.getMessage());
            }
        }
        return null;
    }
}