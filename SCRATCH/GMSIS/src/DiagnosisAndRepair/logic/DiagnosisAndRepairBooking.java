/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.logic;

import Authentication.sqlite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.beans.property.*;

/**
 *
 * @author Bayzid
 */
public class DiagnosisAndRepairBooking {

    private SimpleIntegerProperty bookingID;
    private SimpleStringProperty custName;
    private SimpleStringProperty vehicleReg;
    private SimpleStringProperty make;
    private SimpleStringProperty mechanicName;
    private SimpleIntegerProperty mileage;
    private SimpleStringProperty date;
    private SimpleIntegerProperty duration;
    private SimpleStringProperty startTime;
    private SimpleStringProperty endTime;
    private SimpleStringProperty partName;

    public DiagnosisAndRepairBooking(int id, String reg, String make, String cName, String mName, String date, int duration, int mileage, String startTime, String endTime, String nameOfPart) {
        this.bookingID = new SimpleIntegerProperty(id);
        this.vehicleReg = new SimpleStringProperty(reg);
        this.make = new SimpleStringProperty(make);
        this.custName = new SimpleStringProperty(cName);
        this.mechanicName = new SimpleStringProperty(mName);
        this.date = new SimpleStringProperty(date);
        this.duration = new SimpleIntegerProperty(duration);
        this.mileage = new SimpleIntegerProperty(mileage);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.partName = new SimpleStringProperty(nameOfPart);
    }

    /*public DiagnosisAndRepairBooking(int id, String reg, String nameOfPart, String mName, String date, int duration)
    {
        this.bookingID = new SimpleIntegerProperty(id);
        this.vehicleReg = new SimpleStringProperty(reg);
        this.partName = new SimpleStringProperty(nameOfPart);
        this.mechanicName = new SimpleStringProperty(mName);
        this.date = new SimpleStringProperty(date);
        this.duration = new SimpleIntegerProperty(duration);    
    }*/
    
    public int getBookingID() {
        return bookingID.get();
    }

    public String getCustName() {
        return custName.get();
    }

    public String getVehicleReg() {
        return vehicleReg.get();
    }

    public String getPartName() {
        return partName.get();
    }

    public String getMake() {
        return make.get();
    }

    public String getMechanicName() {
        return mechanicName.get();
    }

    public int getMileage() {
        return mileage.get();
    }

    public String getDate() {
        return date.get();
    }

    public int getDuration() {
        return duration.get();
    }

    public String getStartTime() {
        return startTime.get();
    }

    public String getEndTime() {
        return endTime.get();
    }

    public void setPartName(String name) {
        if (name.equals("")) {
            partName.set("");
            return;
        }
        if (partName.get().isEmpty()) {
            partName.set(name);
            return;
        }
        String updateString = partName.get() + ", " + name;
        partName.set(updateString);
    }
    

    public void setBookingID(int id) {
        bookingID.set(id);
    }

    public void setCustName(String cName) {
        custName.set(cName);
    }

    public void setVehicleReg(String reg) {
        vehicleReg.set(reg);
    }

    public void setMake(String m) {
        make.set(m);
    }

    public void setMechanicName(String mName) {
        mechanicName.set(mName);
    }

    public void setMileage(int mile) {
        mileage.set(mile);
    }

    public void setDate(String d) {
        date.set(d);
    }

    public void setDuration(int time) {
        duration.set(time);
    }

    public void setStartTime(String time) {
        startTime.set(time);
    }

    public void setEndTime(String time) {
        endTime.set(time);
    }
    
    //returns duration in minutes for 2 times
    public int calculateDuration(String sTime, String eTime)
     {
        
        String[] arr1 = new String[2];
        String[] arr2 = new String[2];
       
        arr1 = sTime.split(":"); 
        arr2 = eTime.split(":");
        
        int duration=0;
        
        int startHour = Integer.parseInt(arr1[0]);
        int endHour = Integer.parseInt(arr2[0]);
  
        int startMin = Integer.parseInt(arr1[1]);
        int endMin = Integer.parseInt(arr2[1]);
        
        if(startMin == endMin)
        {
            duration += (endHour-startHour) * 60;
        }
        else
        {   
            duration += ((endHour-startHour)-1) * 60;                     
            duration +=(60-startMin);        
            duration += endMin;  
        }
        return duration;
    }  
    
    public boolean checkSaturday(LocalDate item)
    {
            if(item.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                return true;
            }
            return false;
    }
    
    public String findCustName(int custID) throws ClassNotFoundException
    {
        String custName = "";
        Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select customer_fullname from customer where customer_id='"+ custID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                custName = rs.getString("customer_fullname");    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return custName;
    }
    
    public String findVehReg(int vehID) throws ClassNotFoundException
    {
        String vehReg = "";
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select RegNumber from vehicleList where vehicleID='"+ vehID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                vehReg = rs.getString("RegNumber");    
           
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehReg;
    }
    
    public String findMechName(int mechID) 
    {
        String mechName = "";
        try {

            Connection conn = (new sqlite().connect());
            
            String SQL = "Select fullname from mechanic where mechanic_id='"+ mechID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                 
                mechName = rs.getString("fullname");    
            
               rs.close();
               conn.close();
               
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mechName;
    }
    
    public String findCustID(String reg) throws ClassNotFoundException
    {
        String custID = "";
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select customerid from vehicleList where RegNumber='"+ reg +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
           
            custID = rs.getString("customerid");    
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return custID;
    }
    
    public String findMechID(int bookingID) throws ClassNotFoundException
    {
        String mechID = "";
        Connection conn = null;
        try {

           conn = (new sqlite().connect());

            String SQL = "Select mechanic_id from booking where booking_id='"+ bookingID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                mechID = rs.getString("mechanic_id");   
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mechID;
    }
    
     public int findVehID(String vehReg) throws ClassNotFoundException
    {
        int vehRegID = 0;
        Connection conn = null;
        try {

            conn = (new sqlite().connect());

            String SQL = "Select vehicleID from vehicleList where RegNumber='"+ vehReg +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
                  
                vehRegID = rs.getInt("vehicleID");   
            
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return vehRegID;
    }
     
     public String findMake(int vehID) throws ClassNotFoundException
    {
        String make = "";
  
        try {

            Connection conn = (new sqlite().connect());
            
            String SQL = "Select Make from vehicleList where vehicleID='"+ vehID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
               
            make = rs.getString("Make");    
       
           rs.close();
           conn.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return make;
    }
    
    public int findMileage(int vehID) throws ClassNotFoundException
    {
        int mileage = 0;
  
        try {

           Connection conn = (new sqlite().connect());
            
            String SQL = "Select Mileage from vehicleList where vehicleID='"+ vehID +"'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
               
            mileage = rs.getInt("Mileage");    
       
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return mileage;
    }
    
    //update mileage on vehicle table 
    public void updateMileage(int mileage, int id) throws ClassNotFoundException
    {   
        try
        {
            Connection conn = (new sqlite().connect());
            String sql = "UPDATE vehicleList SET Mileage=? WHERE vehicleID=?";
            PreparedStatement state = conn.prepareStatement(sql);
           
            state.setInt(1, mileage);
            state.setInt(2, id);
                
            state.execute();
            
            conn.close();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());  
        }
    }

}
