/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.logic;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class CustBookingInfo {
   public SimpleStringProperty fullName = new SimpleStringProperty(); 
   public SimpleStringProperty bookingDate = new SimpleStringProperty();
   
   public CustBookingInfo(String fName, String bDate)
   {
      this.fullName = new SimpleStringProperty(fName);
      this.bookingDate = new SimpleStringProperty(bDate);
   }
   public String getFullName(){
           return fullName.get();
}

   public String getBookingDate()
   {
       return bookingDate.get();
   }
           
   public void setFullName(String fullN)
   {
       fullName.set(fullN);
   }
   
   public void setBookingDate(String bDate)
   {
       bookingDate.set(bDate);
   }
}


