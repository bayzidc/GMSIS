/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.logic;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fabiha
 */
public class VehicleCustomerInfo {
    
   public SimpleStringProperty customerName; 
   public SimpleStringProperty vehicleregNo;
   public SimpleStringProperty bookingDate;
    
   
   public VehicleCustomerInfo(String vehicleregNo, String customerName, String bDate)
   {
      this.customerName = new SimpleStringProperty(customerName);
      this.vehicleregNo = new SimpleStringProperty(vehicleregNo);
      this.bookingDate = new SimpleStringProperty(bDate);
     
   }
   public String getCustomerName(){
           return customerName.get();
}

   public String getBookingDate()
   {
       return bookingDate.get();
   }
           
   public String getRegNumber()
   {
       return vehicleregNo.get();
   }
   public void setCustomerName(String name)
   {
       customerName.set(name);
   }
   
    public void setRegNumber(String regNo)
   {
       vehicleregNo.set(regNo);
   }
   
   public void setBookingDate(String date)
   {
       bookingDate.set(date);
   }
   
  
   public SimpleStringProperty customerNameProperty()
   {
       return customerName;
   }
   
   public SimpleStringProperty vehicleregNoProperty()
   {
       return vehicleregNo;
   }
   
   public SimpleStringProperty bookingDateProperty()
   {
       return bookingDate;
   }
   
   
}
    

