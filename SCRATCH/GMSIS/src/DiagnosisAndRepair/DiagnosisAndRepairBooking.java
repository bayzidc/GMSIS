/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author User
 */
public class DiagnosisAndRepairBooking {
  
   public SimpleStringProperty custID = new SimpleStringProperty(); 
   public SimpleStringProperty regNumber = new SimpleStringProperty();
   public SimpleStringProperty mechanicID = new SimpleStringProperty();
   public SimpleStringProperty partsRequired = new SimpleStringProperty();
   public SimpleDoubleProperty mileage = new SimpleDoubleProperty();
   public SimpleStringProperty date = new SimpleStringProperty();
   public SimpleIntegerProperty duration = new SimpleIntegerProperty();
  
   public String getCustID()
   {
       return custID.get();
   }
   public String getVehicleID()
   {
       return regNumber.get();
   }
   public String getMechanicID()
   {
       return mechanicID.get();
   }
   public String getParts()
   {
       return partsRequired.get();
   }
   public double getMileage()
   {
       return mileage.get();
   }
   public String getDate()
   {
       return date.get();
   }
   public int getDuration()
   {
       return duration.get();
   }
}