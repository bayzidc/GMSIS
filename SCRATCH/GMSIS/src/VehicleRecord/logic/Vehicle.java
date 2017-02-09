/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Userimport javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class Vehicle {
  


   public SimpleStringProperty regNumber = new SimpleStringProperty(); 
   public SimpleStringProperty make = new SimpleStringProperty();
   public SimpleStringProperty model = new SimpleStringProperty();
   public SimpleDoubleProperty engSize = new SimpleDoubleProperty();
   public SimpleStringProperty fuelType = new SimpleStringProperty();
   public SimpleStringProperty colour = new SimpleStringProperty();
   public SimpleStringProperty motRenewal = new SimpleStringProperty();
   public SimpleStringProperty lastService = new SimpleStringProperty();
   public SimpleIntegerProperty mileage = new SimpleIntegerProperty();
   public SimpleStringProperty vehicleType = new SimpleStringProperty();
   public SimpleStringProperty warranty = new SimpleStringProperty();
   public SimpleStringProperty warNameAndAdd = new SimpleStringProperty();
   public SimpleIntegerProperty vecID = new SimpleIntegerProperty();
   
   
   

   
   public String getRegNumber() {
      return regNumber.get();
   }

   public String getMake() {
      return make.get();
   }

   public String getModel() {
      return model.get();
   }

   public double getEngSize() {
      return engSize.get();
   }
   public String getFuelType() {
      return fuelType.get();
   }
   public String getColour() {
      return colour.get();
   }
   public String getMotRenewal() {
      return motRenewal.get();
   }
   public String getLastService() {
      return lastService.get();
   }
   public int getMileage() {
      return mileage.get();
   }
   
   public String getVehicleType()
   {
       return vehicleType.get();
   }
   
   public String getWarranty()
   {
       return warranty.get();
   }
   
   public String getWarNameAndAdd()
   {
      return warNameAndAdd.get();
   }
    
   public int getVecID()
   {
       return vecID.get();
   }
}
