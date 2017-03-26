/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.logic;

import Authentication.sqlite;
import CustomerAccount.gui.GuiController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.*;

/**
 *
 * @author Bayzid
 */
public class Mechanic{
    
    private SimpleIntegerProperty mechanicID;
    private SimpleDoubleProperty hourlyRate;
    private SimpleDoubleProperty hoursWorked;
    
    public Mechanic(int id, double rate, double hour)
    {
        this.mechanicID = new SimpleIntegerProperty(id);
        this.hourlyRate = new SimpleDoubleProperty(rate);
        this.hoursWorked = new SimpleDoubleProperty(hour);
    }

    public int getMechanicID() {
        return mechanicID.get();
    }

    public double getHourlyRate() {
        return hourlyRate.get();
    }

    public double getHoursWorked() {
        return hoursWorked.get();
    }
    
    public void setmechanicID(int id) {
        mechanicID.set(id);
    }

    public void setHourlyRate(double rate) {
        hourlyRate.set(rate);
    }

    public void setHoursWorked(double hour) {
        hoursWorked.set(hour);
    }
    
    public void addMechanicBill(int bID, int vID, int cID, int mID) throws ClassNotFoundException {
        
        int bookingID = bID; 
        int vehicleID = vID;
        int customerID = cID;
        int mechanicID = mID;
        
        try {
            
              Mechanic mech = new Mechanic(mechanicID,findMechanicHourlyRate(bookingID),findMechanicHoursWorked(bookingID));

              GuiController.showBill.addCostToBillMechanic(GuiController.showBill, mech); //create a addMechanicCostToBill method in bill class
              Double mechanicCost = GuiController.showBill.getMechanicCost(); //create total mech cost method
              
                Connection conn = new sqlite().connect();

                String sql = "insert into bill(customerID, bookingID, vehicleID, mechanicCost, totalCost, settled) values(?,?,?,?,?,?)";
                PreparedStatement state = conn.prepareStatement(sql);
                state.setInt(1, customerID);
                state.setInt(2, bookingID);
                state.setInt(3, vehicleID);

                state.setDouble(4, mechanicCost);
 
                state.setDouble(5, mechanicCost);
 
                String exists = IfWarrantyAndNotExpired(vehicleID, conn);
                if(exists.equalsIgnoreCase("Yes"))
                {
                    state.setBoolean(6, true);
                }
                else if(exists.equalsIgnoreCase("No"))
                {
                    state.setBoolean(6, false);
                }
            
               GuiController.showBill.setMechanicCost(0);
               GuiController.showBill.setPartsCost(0);
               GuiController.showBill.calculateTotalCost();
                state.execute();

                state.close();
                conn.close();
           
        } 
        catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
     public void updateMechanicBill(int bID, int mID) throws ClassNotFoundException {
        
        int bookingID = bID; 
        int mechanicID = mID;
        
        try {
            
              Mechanic mech = new Mechanic(mechanicID,findMechanicHourlyRate(bookingID),findMechanicHoursWorked(bookingID));
              CustomerAccount.gui.GuiController.showBill.addCostToBillMechanic(CustomerAccount.gui.GuiController.showBill, mech);
             //GuiController.showBill.addMechanicCostToBill(GuiController.showBill, mech); //create a addMechanicCostToBill method in bill class
             
              Double mechanicCost = GuiController.showBill.getMechanicCost(); //create total mech cost method
              
              
                Connection conn = new sqlite().connect();

                String sql = "UPDATE bill SET mechanicCost=?, totalCost=? WHERE bookingID=?";
                
                PreparedStatement state = conn.prepareStatement(sql);
     
                state.setDouble(1, mechanicCost);
 
                double partsCost = findPartsCost(bookingID,conn);
                
                
                CustomerAccount.gui.GuiController.showBill.setPartsCost(partsCost);
                CustomerAccount.gui.GuiController.showBill.calculateTotalCost();
                double totalCost = CustomerAccount.gui.GuiController.showBill.getTotalCost();
                
                state.setDouble(2, totalCost);
                
                state.setDouble(3, bookingID);
                
               GuiController.showBill.setMechanicCost(0);
               GuiController.showBill.setPartsCost(0);
               GuiController.showBill.calculateTotalCost();
                state.execute();

                state.close();
                conn.close();
           
        } 
        catch (SQLException e) {
             e.printStackTrace();
        }
    }
    
      public double findMechanicHoursWorked(int bookingID) throws ClassNotFoundException
    {
        double hour = 0.0;
        
         Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select duration from booking where booking_id='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
  
                hour = (double)rs.getInt("duration") / 60;    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        
        return hour;
    }
    
    public double findMechanicHourlyRate(int bookingID) throws ClassNotFoundException
    {
        double rate = 0;
        
         Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select hourlyRate from mechanic,booking where booking.mechanic_id=mechanic.mechanic_id and booking.booking_id='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                rate = rs.getDouble("hourlyRate");    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        
        return rate;
    }
    
     
    public String IfWarrantyAndNotExpired(int vehicleID, Connection conn) throws ClassNotFoundException{
        
        String check= "";
        
        try {

            String SQL = "Select Warranty,WarrantyExpDate from vehicleList where vehicleID ='" + vehicleID + "'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
               
            check = rs.getString("Warranty");   

            if(check.equalsIgnoreCase("Yes"))
            {
                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate expDate = LocalDate.parse(rs.getString("WarrantyExpDate"), formatter);
                if(now.isAfter(expDate))
                {
                    check = "No";  //past the exp date
                }
            }
            
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }
    
    public double findPartsCost(int bookingID, Connection conn) throws ClassNotFoundException
    {
        double pCost = 0.0;
        
        try {

            String SQL = "Select partsCost from bill where bookingID='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                pCost = rs.getDouble("partsCost");   
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        
        return pCost;
    }
    
    
}
