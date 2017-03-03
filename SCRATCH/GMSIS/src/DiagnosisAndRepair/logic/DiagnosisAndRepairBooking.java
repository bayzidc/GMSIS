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
  
   private SimpleIntegerProperty bookingID;
   private SimpleStringProperty custName;
   private SimpleStringProperty vehicleReg;
   private SimpleStringProperty make;
   private SimpleStringProperty mechanicName;
   private SimpleDoubleProperty mileage;
   private SimpleStringProperty date;
   private SimpleIntegerProperty duration;
   private SimpleStringProperty startTime;
   private SimpleStringProperty endTime;
  
   public DiagnosisAndRepairBooking(int id, String reg, String make, String cName, String mName, String date, int duration, double mileage, String startTime, String endTime)
   {
       this.bookingID = new SimpleIntegerProperty(id);
       this.vehicleReg = new SimpleStringProperty(reg);
       this.make = new SimpleStringProperty(make);
       this.custName = new SimpleStringProperty(cName); 
       this.mechanicName = new SimpleStringProperty(mName);
       this.date = new SimpleStringProperty(date);
       this.duration = new SimpleIntegerProperty(duration);
       this.mileage = new SimpleDoubleProperty(mileage);
       this.startTime = new SimpleStringProperty(startTime);
       this.endTime = new SimpleStringProperty(endTime);
   }

   
   public int getBookingID()
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
   public String getMake()
   {
       return make.get();
   }
   public String getMechanicName()
   {
       return mechanicName.get();
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
   public String getStartTime()
   {
       return startTime.get();
   }
   public String getEndTime()
   {
       return endTime.get();
   }
   
   
   public void setBookingID(int id)
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
   public void setMake(String m)
   {
       make.set(m);
   }
   public void setMechanicName(String mName)
   {
       mechanicName.set(mName);
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
   public void setStartTime(String time)
   {
       startTime.set(time);
   }
   public void setEndTime(String time)
   {
       endTime.set(time);
   }
   
    public IntegerProperty getBookingIDProperty()
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
   public StringProperty getMakeProperty()
   {
       return make;
   }
   public StringProperty getMechanicNameProperty()
   {
       return mechanicName;
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
   public StringProperty getStartTimeProperty()
   {
       return startTime;
   }
   public StringProperty getEndTimeProperty()
   {
       return endTime;
   }
}