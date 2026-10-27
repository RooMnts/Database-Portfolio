/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package car_renting;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author dante
 */
public class Car_Renting {

    
    public static void main(String[] args) {
        // TODO code application logic here        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver into memory so Java can communicate with MySQL
            Connection datta=DriverManager.getConnection // Create a connection to the Car_Renting database hosted on the local MySQL server
                ("jdbc:mysql://localhost:3306/Car_Renting","your_username","your_password");
            Scanner myKB = new Scanner(System.in); // Scanner to read user input
            
            System.out.println("Connection succesfull!"); // Confirm connection
           
            int option = -1; // Variable to store menu choice, star invalid
            do { // Menu loop, keeps running until user chooses 0 (Exit)
                System.out.println("\n*-*-*-MENU-*-*-*");
                System.out.println("1. Search cars rented by client");
                System.out.println("2. Show services hired by a client");
                System.out.println("3. List available cars");
                System.out.println("4. Search bookings by date");
                System.out.println("5. Show total cost spent by client");
                System.out.println("6. Show all clients with their addresses");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                
                try{
                option = myKB.nextInt(); // Try to read option as a number
                myKB.nextLine(); // Clear buffer after reading
                } catch (Exception e) {
                    System.out.println("Invalid option. Please enter a number."); // If user writes letters insted of numbers, catch the erros
                    myKB.nextLine(); // Clear wrong input
                    option = -1; //Keep invalid so menu repeat
                }
                
                switch (option){ //Swith decides wich metod to run based on user choice
                    case 1 -> car_Renting_Datta.ClientName(datta, myKB);
                    case 2 -> car_Renting_Datta.ClientServices(datta, myKB);
                    case 3 -> car_Renting_Datta.AvailableCars(datta);
                    case 4 -> car_Renting_Datta.BookingsByDate(datta, myKB);
                    case 5 -> car_Renting_Datta.TotalCost(datta, myKB);
                    case 6 -> car_Renting_Datta.ClientAddresses(datta, myKB);
                    case 0 -> System.out.println("Exit..."); // End program
                    default -> System.out.println("Invalid option. Try again.");
                }
            } while (option != 0); // While keeps showing menu until option is 0
            
        }catch(SQLException e){ // If connection fails or query has problems
                    System.out.println("Database error");
        }
        catch(ClassNotFoundException e){ // If driver is missing
        }
    }
}