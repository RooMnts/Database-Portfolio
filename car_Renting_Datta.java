/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package car_renting;

/**
 *
 * @author dante
 */
import java.sql.*;
import java.util.Scanner;

public class car_Renting_Datta {
   
    public static void ClientName(Connection datta, Scanner myKB) { // Option 1, search cars rented by cllient
        System.out.println("Enter client name: "); // Ask user for client name
        String name = myKB.nextLine(); //Read and clean input
        
         if (name.isEmpty()) { // Validation, name cannot be empty or contain numbers
            System.out.println("Name cannot be empty. Please try again.");
            return;
        }
         for (int i = 0; i < name.length(); i++){
             if (Character.isDigit(name.charAt(i))){
                 System.out.println("Names cannot contain numbers.");
                 return;
             }
         }
        
        try { // SQL query joins Client and CarHire tables to find cars
            String sql = "SELECT c.Name, ch.Car_type " + "FROM Client c " + 
                    "JOIN CarHire ch ON c.Client_ID = ch.Client_ID " + "WHERE c.Name LIKE ?";
            PreparedStatement prep = datta.prepareStatement(sql); // Prepare query
            prep.setString(1, "%" + name + "%"); // Use LIKE to match partial names
            ResultSet result = prep.executeQuery(); // Run query
            
            boolean found = false;
            while (result.next()){ // Loop through results
                System.out.println("Client: " + result.getString("Name") + // Show each car rented by this client
                        " | Car: " + result.getString("Car_type"));
                found = true;
            }
            if (!found) {
                System.out.println("No cars found for that client.");
            }
        } catch (SQLException e) {
            System.out.println("Error running query: " + e.getMessage());
        }
    }  
        
    public static void ClientServices(Connection datta, Scanner myKB){ // Option 2, Shows services hired by a client
        System.out.println("Enter client name to see their services: "); // Ask for name
        String name = myKB.nextLine(); // Read and clean input
        
        if (name.isEmpty()) { // Validation, name cannot be empty or contain numbers
            System.out.println("Name cannot be empty.");
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                System.out.println("Names cannot contain numbers.");
                return;
            }
        }

        
    try{ // SQL query joins Client, CarHire, Reservation and ServiceInformation to list services
        String sql = "SELECT r.Service_name, si.Service_charge " +
              "FROM Client c " +
              "JOIN CarHire ch ON c.Client_ID = ch.Client_ID " +
              "JOIN Reservation r ON ch.Reservation_ID = r.Reservation_ID " +
              "JOIN ServiceInformation si ON r.Service_name = si.Service_name " +
              "WHERE c.Name LIKE ?";
        PreparedStatement prep = datta.prepareStatement(sql); // Prepare query
        prep.setString(1, "%" + name + "%"); // Partial name search
        ResultSet result = prep.executeQuery(); // Run query
        
        boolean found = false;
        while (result.next()){ // loop through results
            System.out.println("Service: " + result.getString("Service_name") + // Show each service and its charge
                    " | Charge: $" + result.getInt("Service_charge"));            
            found = true;          
        }
        if(!found){
            System.out.println("No services found for " + name);
        }
    }catch (SQLException e){
               System.out.println("Error running query: " + e.getMessage());
               }
    }
    
    public static void AvailableCars(Connection datta) { // Option 3, list available cars
        try{
            String sql = "SELECT Car_type, Base_rent FROM CarCare"; // SQL query selects all cars and their base rent
            Statement prep = datta.createStatement(); // Simple statement
            ResultSet result = prep.executeQuery(sql); // Run query
            
            boolean found = false;
            while (result.next()) { // Loop through results
                System.out.println("Car Type: " + result.getString("Car_type") + 
                        " | Base Rent: $" + result.getDouble("Base_rent"));
                found = true;
            }
            if (!found){
                System.out.println("No cars available.");
            }
        } catch (SQLException e) {
            System.out.println("Error running query: " + e.getMessage());
        }
    }
    
    public static void BookingsByDate(Connection datta, Scanner myKB){ // Option 4, search bookings by date
        System.out.println("Enter a booking date (YYYY-MM-DD): "); // Ask for date
        String date = myKB.nextLine(); // Read and clean input
        
       if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) { // Validation, check if the date has the correct format YYYY-MM-DD
           System.out.println("Invalid date format. Please use YYYY-MM-DD.");
           return; // Stop method if format is wrong
        }

        try{
            String sql = "SELECT c.Name, ch.Car_type, ch.Date_of_Reservation " + // SQL query joins Client and CarHire, filters by reservation date
                     "FROM Client c " +
                     "JOIN CarHire ch ON c.Client_ID = ch.Client_ID " +
                     "WHERE DATE(ch.Date_of_Reservation) = ?";
            PreparedStatement prep = datta.prepareStatement(sql); // Prepare query
            prep.setString(1, date); // Add date as parameter
            ResultSet result = prep.executeQuery(); // Run query
            
            boolean found = false;
            while (result.next()){ // Loop through results
                System.out.println("Client: " + result.getString("Name") + 
                        " | Car: " + result.getString("Car_type") 
                + " | Date: " + result.getString("Date_of_Reservation"));
                found = true;
            }
            if(!found){
                System.out.println("No bookings found on " + date);
            }
        }catch (SQLException e) {
            System.out.println("Error running query: " + e.getMessage());
        }
    }
       
    public static void TotalCost(Connection datta, Scanner myKB) { //Option 5,  show total cost spent by client
        System.out.println("Enter client name: "); // Ask user for client name
        String name = myKB.nextLine(); // Read and clean input
        
        if (name.isEmpty()) { // Validation, name cannot be empty or contain numbers
            System.out.println("Name cannot be empty.");
            return;
        }
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                System.out.println("Names cannot contain numbers.");
                return;
            }
        }

        try { /** SQL joins several tables to calculated the total spent by the client
         * It adds base rent of the car plus the service charge for each reservation
         */           
            String sql = "SELECT c.Name, SUM(cc.Base_rent + si.Service_charge) AS total_cost " + 
                    "FROM Client c " + "JOIN CarHire ch ON c.Client_ID = ch.Client_ID " + 
                    "JOIN Reservation r ON ch.Reservation_ID = r.Reservation_ID " + "JOIN ServiceInformation si ON r.Service_name = si.Service_name " + 
                    "JOIN CarCare cc ON ch.Car_type = cc.Car_type " + "WHERE c.Name LIKE ? " + "GROUP BY c.Name";
            PreparedStatement prep = datta.prepareStatement(sql); // Prepare query
            prep.setString(1, "%" + name + "%"); // Use partial name
            ResultSet result = prep.executeQuery(); // Run query
            
            if (result.next() && result.getDouble("total_cost") > 0) { // Show total cost if there is a result
                System.out.println("Client: " + result.getString("Name") + 
                        " | Total Spent: $" + result.getDouble("total_cost"));
            } else {
                System.out.println("No spending records found for that client.");
            }
        }catch (SQLException e) {
            System.out.println("Error running query: " + e.getMessage());
        }
    }
    
    public static void ClientAddresses(Connection datta, Scanner myKB){ // Option 6, show all clients with their addresses
        try{
            String sql = "SELECT Name, Client_address FROM Client"; // SQL selects client names and their address from the Client table
            Statement stmt = datta.createStatement(); // Simple statement is enough
            ResultSet result = stmt.executeQuery(sql); // Run query
            
            boolean found = false; // Track if we printed any row
            while (result.next()){ // Loop through results
                System.out.println("Client: " + result.getString("Name") + " | Address: " + result.getString("Client_address"));
                found = true;
            }
            if (!found) { // If there are no rows, inform the user
                System.out.println("No clients found.");
            }
        }catch (SQLException e){
            System.out.println("Error running query: " + e.getMessage());
        }
    }        
    }

