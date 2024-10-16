package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String URL = "jdbc:mysql://localhost:3306/account";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    public static void main( String[] args )
    {

        System.out.println("******WELCOME TO THE DHEERAJ'S BANK ***********");
        try{
            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement statement = connection.createStatement();

            MainLoop:
            while(true){

                Scanner scanner = new Scanner(System.in);

                System.out.println("\nSELECT YOUR OPTION \n");

                System.out.println("1. CREATE NEW ACCOUNT ");
                System.out.println("2. CHECK BALANCE ");
                System.out.println("3. WITHDRAW BALANCE ");
                System.out.println("4. DELETE YOUR ACCOUNT ");
                System.out.println("5. EXIT ");

                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        AccountOperations.createNewAccount(connection);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        System.out.println("exiting the application");
                        break MainLoop;
                    default:
                        System.out.println("enter a valid value between 1-5 inclusive");
                }


            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {

        }
    }
}
