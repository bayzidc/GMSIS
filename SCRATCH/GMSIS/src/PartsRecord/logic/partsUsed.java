/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.logic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fabiha
 */
public class partsUsed {
    public IntegerProperty partId;
    public DoubleProperty cost;
    public StringProperty installDate;
    public StringProperty warrantyExpireDate;
    public StringProperty vehicleRegNo;
    public StringProperty customerFullName ;
   
    public partsUsed(int partId, double cost,String installDate, String warrantyExpireDate,String vehicleRegNo, String customerFullName) {
        this.partId = new SimpleIntegerProperty(partId);
        this.cost = new SimpleDoubleProperty(cost);
        this.installDate = new SimpleStringProperty(installDate);
        this.warrantyExpireDate = new SimpleStringProperty(warrantyExpireDate);
        this.vehicleRegNo= new SimpleStringProperty(vehicleRegNo);
        this.customerFullName = new SimpleStringProperty(customerFullName);
    }

    public int getPartID() {
        return partId.get();
    }
    public double getCost() {
        return cost.get();
    }
    public String getInstallDate() {
        return installDate.get();
    }

    public String getWarrantyExpireDate() {
        return warrantyExpireDate.get();
    }

    public String getVehicleRegNo() {
        return vehicleRegNo.get();
    }
    
    public String getCustomerFullName() {
        return customerFullName.get();
    }
    
    public void setPartId(int number) {
        partId.set(number);
    }
    
    public void setCost(Double number) {
        cost.set(number);
    }
    
    public void setInstallDate(String date) {
        installDate.set(date);
    }

    public void setWarrantyExpireDate(String date) {
        warrantyExpireDate.set(date);
    }
    
    public void setVehicleRegNo(String number) {
        vehicleRegNo.set(number);
    }
    
    public void setcustomerFullName(String name) {
        customerFullName.set(name);
    }
    
    //Property values
    public IntegerProperty partIDProperty() {
        return partId;
    }
    
    public DoubleProperty costProperty() {
        return cost;
    }
    
    public StringProperty installDateProperty() {
        return installDate;
    }

    public StringProperty expireDateProperty() {
        return warrantyExpireDate;
    }
    
    public StringProperty vehRegNoProperty() {
        return vehicleRegNo ;
    }
    
    public StringProperty customerNameProperty() {
        return customerFullName ;
    }    
    
}
    

