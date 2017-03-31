/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fabiha
 */
public class PartsUsedPerVehicle {
   public SimpleStringProperty customerName; 
   public SimpleStringProperty vehicleRegNo;
   public SimpleStringProperty partUsedName;
   public IntegerProperty quantity;
    
   
   public PartsUsedPerVehicle(String vehicleRegNo, String customerName, String partUsedName, int quantity)
   {
      this.customerName = new SimpleStringProperty(customerName);
      this.vehicleRegNo = new SimpleStringProperty(vehicleRegNo);
      this.partUsedName = new SimpleStringProperty(partUsedName);
      this.quantity = new SimpleIntegerProperty(quantity);
     
   }
   public String getCustomerName(){
        return customerName.get();
    }
   public int getQuantity() {
        return quantity.get();
    }

  public String getRegNumber(){
       return vehicleRegNo.get();
   }
  
  public String getPartUsedName(){
      return partUsedName.get();
  }
  
  public void setPartUsedName(String name)
   {
       partUsedName.set(name);
   }
  
  
   public void setCustomerName(String name)
   {
       customerName.set(name);
   }
   
    public void setRegNumber(String regNo)
   {
       vehicleRegNo.set(regNo);
   }
   
   public void setQuantity(int number) {
        quantity.set(number);
    }
   
  
   public SimpleStringProperty customerNameProperty()
   {
       return customerName;
   }
   
   public SimpleStringProperty vehicleRegNoProperty()
   {
       return vehicleRegNo;
   }
   public SimpleStringProperty partNameProperty()
   {
       return partUsedName;
   }
   
   public IntegerProperty quantityProperty() {
        return quantity;
    }
   
   
    
}
