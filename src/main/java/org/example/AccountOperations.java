package org.example;

import java.sql.*;
import java.util.Scanner;

public class AccountOperations {
    /**
     *
     */
    public static void createNewAccount(Connection connection) throws SQLException {
        try{
            String accNumber;
            String name;
            String type;
            long balance;
            Scanner scanner = new Scanner(System.in);
            System.out.println("ENTER NEW ACCOUNT NUMBER: ");
            accNumber = scanner.nextLine();
            System.out.println("ENTER YOUR NAME: ");
            name = scanner.nextLine();
            System.out.println("ENTER THE TYPE OF ACCOUNT SAVING / CURRENT: ");
            type = scanner.nextLine();
            balance = 0;

            String sqlQuery = "insert into accountTable1(number,name,type,balance) values(?,?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, accNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, type);
            preparedStatement.setLong(4, balance);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("ACCOUNT CREATED SUCCESSFULLY");
                showAccDetails(accNumber,connection);
            } else {
                System.out.println("ACCOUNT CREATION FAILED !");
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }





    }
    public static void showAccDetails(String accNumber , Connection connection) throws SQLException {
                System.out.println("\nYOUR ACCOUNT DETAILS");
                ResultSet resultSet = getResultSetBasedOnAccNumber(connection,accNumber);
                System.out.println("YOUR ACCOUNT NUMBER  :"+accNumber);
                System.out.println("YOUR NAME            :"+resultSet.getString("name"));
                System.out.println("YOUR ACCOUNT TYPE    :"+resultSet.getString("type"));
                System.out.println("YOUR ACCOUNT BALANCE :"+resultSet.getString("balance"));

    }
    public static void checkBalance(Connection connection) throws SQLException {
        String accNumber ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR ACCOUNT NUMBER :");
        accNumber = scanner.nextLine();
        ResultSet resultSet = getResultSetBasedOnAccNumber(connection,accNumber);
        System.out.println("YOUR CURRENT BALANCE IS "+resultSet.getString("balance"));

    }
    public static ResultSet getResultSetBasedOnAccNumber(Connection connection , String accNumber) throws SQLException {
        Statement statement = connection.createStatement();
        String sqlQuery = "select * from accountTable1";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while(resultSet.next()){
            if(resultSet.getString("number").equalsIgnoreCase(accNumber)){
                return resultSet;
            }
        }
        return null;
    }

    public static void withdrawBalance(Connection connection) throws SQLException {

    }

    public static void depositBalance(Connection connection) throws SQLException {
        String accNumber ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR ACCOUNT NUMBER :");
        accNumber = scanner.nextLine();
        ResultSet resultSet = getResultSetBasedOnAccNumber(connection,accNumber);
        long balance = resultSet.getLong("balance");

        System.out.println("HOW MUCH AMOUNT YOU WANT TO DEPOSIT");
        long depositAmount = scanner.nextLong();
        depositAmount += balance;
        // update the new balance in the database
        String sqlQuery = "update accountTable1 set balance = ? where number = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setLong(1, depositAmount);
        preparedStatement.setString(2,accNumber);
        int rows = preparedStatement.executeUpdate();
        if(rows > 0){
            System.out.println("BALANCE DEPOSITED SUCCESSFULLY");
            showAccDetails(accNumber,connection);
        } else {
            System.out.println("BALANCE DEPOSIT FAILED ");
        }

    }
    public static long getBalance(Connection connection ) throws SQLException {
        String accNumber ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER YOUR ACCOUNT NUMBER :");
        accNumber = scanner.nextLine();
        ResultSet resultSet = getResultSetBasedOnAccNumber(connection,accNumber);
        long balance = resultSet.getLong("balance");
        return balance;
    }
}
