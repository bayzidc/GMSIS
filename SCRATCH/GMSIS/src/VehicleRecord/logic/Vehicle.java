/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecord.logic;

import Authentication.sqlite;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

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
   public SimpleStringProperty warrantyExpDate = new SimpleStringProperty();
   public SimpleIntegerProperty vecID = new SimpleIntegerProperty();
   public SimpleIntegerProperty custID = new SimpleIntegerProperty();
   public SimpleStringProperty custName = new SimpleStringProperty();

    

   public Vehicle(String regNumber, String make, String model, double engSize, String fuelType, String colour, String motRenewal, String lastService, int mileage, String vehicleType, String warranty, String warNameAndAdd, String warrantyExpDate,int vecID,int custID, String cName)
   {
       this.regNumber = new SimpleStringProperty(regNumber);
       this.make = new SimpleStringProperty(make);
       this.model = new SimpleStringProperty(model);
       this.engSize = new SimpleDoubleProperty(engSize);
       this.fuelType = new SimpleStringProperty(fuelType);
       this.colour = new SimpleStringProperty(colour);
       this.motRenewal = new SimpleStringProperty(motRenewal);
       this.lastService = new SimpleStringProperty(lastService);
       this.mileage = new SimpleIntegerProperty(mileage);
       this.vehicleType = new SimpleStringProperty(vehicleType);
       this.warranty = new SimpleStringProperty(warranty);
       this.warNameAndAdd = new SimpleStringProperty(warNameAndAdd);
       this.warrantyExpDate = new SimpleStringProperty(warrantyExpDate);
       this.vecID = new SimpleIntegerProperty(vecID);
       this.custID = new SimpleIntegerProperty(custID);
       this.custName = new SimpleStringProperty(cName);
   }
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
   
   public String getWarrantyExpDate()
   {
       return warrantyExpDate.get();
   }
   public int getVecID()
   {
       return vecID.get();
   }
   
   public int getCustID()
   {
       return custID.get();
   }
   
   public String getCustName()
   {
       return custName.get();
   }
   public void setRegNumber(String regN) {
        regNumber.set(regN);
   }

   public void setMake(String carMake) {
        make.set(carMake);
   }

   public void setModel(String carModel) {
        model.set(carModel);
   }

   public void setEngSize(double engS) {
      engSize.set(engS);
   }
   public void setFuelType(String fuelT) {
      fuelType.set(fuelT);
   }
   public void setColour(String col) {
      colour.set(col);
   }
   public void setMotRenewal(String motRen) {
      motRenewal.set(motRen);
   }
   public void setLastService(String lastSer) {
      lastService.set(lastSer);
   }
   public void setMileage(int mil) {
      mileage.set(mil);
   }
   
   public void setVehicleType(String vehicleT)
   {
       vehicleType.set(vehicleT);
   }
   
   public void setWarranty(String war)
   {
       warranty.set(war);
   }
   
   public void setWarNameAndAdd(String nameAndAdd)
   {
       warNameAndAdd.set(nameAndAdd);
   }
   
   public void setWarrantyExpDate(String expDate)
   {
       warrantyExpDate.set(expDate);
   }
   public void setVecID(int vID)
   {
       vecID.set(vID);
   }
   
   public void setCustID(int cID)
   {
       custID.set(cID);
   }
   
   public void setCustName(String cN)
   {
       custName.set(cN);
   }
         
   public LocalDate convert(String string)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(string, formatter);
        return localDate;
    }
   
   public void restrictDecimal(TextField field)
    {
        DecimalFormat format = new DecimalFormat( "#.0" );
        field.setTextFormatter( new TextFormatter<>(input ->
        {
        if ( input.getControlNewText().isEmpty() )
        {
            return input;
        }
        ParsePosition parsePosition = new ParsePosition( 0 );
        Object object = format.parse( input.getControlNewText(), parsePosition );
        if ( object == null || parsePosition.getIndex() < input.getControlNewText().length() )
        {
            return null;
        }
        else
        {
            return input;
        }
        }));
    }
   
   public boolean decimalPlaces(double num) 
    {
        String numstr = Double.toString(num);
        String[] strarray = numstr.split("[.]");
        if (strarray.length == 2)
        {
            if (strarray[1].length() > 2)
            {
                alertInf("Only enter upto 2 decimal places.");
                return false;
            }
        }
        return true;
    }
  
   
    public void alertInf(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Pop up box
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
