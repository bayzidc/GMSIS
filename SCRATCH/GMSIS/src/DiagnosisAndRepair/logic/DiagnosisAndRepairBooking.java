/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiagnosisAndRepair.logic;

import javafx.beans.property.*;
/**
 *
 * @author Bayzid
 */
public class DiagnosisAndRepairBooking {
  
   private SimpleStringProperty bookingID;
   private SimpleStringProperty custName;
   private SimpleStringProperty vehicleReg;
   private SimpleStringProperty mechanicName;
   private SimpleStringProperty partsRequired;
   private SimpleDoubleProperty mileage;
   private SimpleStringProperty date;
   private SimpleIntegerProperty duration;
  
   public DiagnosisAndRepairBooking(String id, String reg, String cName, String mName, String date, int duration, String parts, double mileage)
   {
       this.bookingID = new SimpleStringProperty(id);
       this.vehicleReg = new SimpleStringProperty(reg);
       this.custName = new SimpleStringProperty(cName); 
       this.mechanicName = new SimpleStringProperty(mName);
       this.date = new SimpleStringProperty(date);
       this.duration = new SimpleIntegerProperty(duration);
       this.partsRequired = new SimpleStringProperty(parts);  
       this.mileage = new SimpleDoubleProperty(mileage);
   }
   
   public String getBookingID()
   {
       return bookingID.get();
   }
   public String getCustName()
   {
       return custName.get();
   }
   public String getVehicleReg()
   {
       return vehicleReg.get();
   }
   public String getMechanicName()
   {
       return mechanicName.get();
   }
   public String getPartsRequired()
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
   
   public void setBookingID(String id)
   {
       bookingID.set(id);
   }
   public void setCustName(String cName)
   {
       custName.set(cName);
   }
   public void setVehicleReg(String reg)
   {
       vehicleReg.set(reg);
   }
   public void setMechanicName(String mName)
   {
       mechanicName.set(mName);
   }
   public void setPartsRequired(String parts)
   {
       partsRequired.set(parts);
   }
   public void setMileage(double mile)
   {
       mileage.set(mile);
   }
   public void setDate(String d)
   {
       date.set(d);
   }
   public void setDuration(int time)
   {
       duration.set(time);
   }
   
    public StringProperty getBookingIDProperty()
   {
       return bookingID;
   }
   public StringProperty getCustNameProperty()
   {
       return custName;
   }
   public StringProperty getVehicleRegProperty()
   {
       return vehicleReg;
   }
   public StringProperty getMechanicNameProperty()
   {
       return mechanicName;
   }
   public StringProperty getPartsRequiredProperty()
   {
       return partsRequired;
   }
   public DoubleProperty getMileageProperty()
   {
       return mileage;
   }
   public StringProperty getDateProperty()
   {
       return date;
   }
   public IntegerProperty getDurationProperty()
   {
       return duration;
   }
}