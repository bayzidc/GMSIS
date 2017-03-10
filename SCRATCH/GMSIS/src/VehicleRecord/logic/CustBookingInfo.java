/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class CustBookingInfo {
   public SimpleStringProperty fullName = new SimpleStringProperty(); 
   public SimpleStringProperty bookingDate = new SimpleStringProperty();
   public SimpleStringProperty regNumber = new SimpleStringProperty();
   public SimpleDoubleProperty totalCost = new SimpleDoubleProperty();
  
   
   public CustBookingInfo(String fName, String bDate, String reg, double tCost)
   {
      this.fullName = new SimpleStringProperty(fName);
      this.bookingDate = new SimpleStringProperty(bDate);
      this.regNumber = new SimpleStringProperty(reg);
      this.totalCost = new SimpleDoubleProperty(tCost);
   }
   public String getFullName(){
           return fullName.get();
}

   public String getBookingDate()
   {
       return bookingDate.get();
   }
           
   public String getRegNumber()
   {
       return regNumber.get();
   }
   
   public double getTotalCost()
   {
       return totalCost.get();
   }
   public void setFullName(String fullN)
   {
       fullName.set(fullN);
   }
   
   public void setBookingDate(String bDate)
   {
       bookingDate.set(bDate);
   }
   
   public void setRegNumber(String reg)
   {
       regNumber.set(reg);
   }
   public void setTotalCost(double tC)
   {
       totalCost.set(tC);
   }
   public SimpleStringProperty fullNameProperty()
   {
       return fullName;
   }
   
   public SimpleStringProperty bookingDateProperty()
   {
       return bookingDate;
   }
   
   public SimpleStringProperty regNumberProperty()
   {
       return regNumber;
   }
   
   public SimpleDoubleProperty totalCostProperty()
   {
       return totalCost;
   }
}


