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

    public StringProperty partName;
    public DoubleProperty cost;
    public IntegerProperty quantity;
    public StringProperty installDate;
    public StringProperty warrantyExpireDate;
    public StringProperty vehicleRegNo;
    public StringProperty customerFullName;

    public partsUsed(String partName, double cost,int quantity, String installDate, String warrantyExpireDate, String vehicleRegNo, String customerFullName) {
        this.partName = new SimpleStringProperty(partName);
        this.cost = new SimpleDoubleProperty(cost);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.installDate = new SimpleStringProperty(installDate);
        this.warrantyExpireDate = new SimpleStringProperty(warrantyExpireDate);
        this.vehicleRegNo = new SimpleStringProperty(vehicleRegNo);
        this.customerFullName = new SimpleStringProperty(customerFullName);
    }

    public String getPartName() {
        return partName.get();
    }

    public double getCost() {
        return cost.get();
    }
    
    public int getQuantity() {
        return quantity.get();
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

    public void setPartName(String name) {
        partName.set(name);
    }

    public void setCost(Double number) {
        cost.set(number);
    }
    
    public void setQuantity(int number) {
        quantity.set(number);
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
    public StringProperty partNameProperty() {
        return partName;
    }

    public DoubleProperty costProperty() {
        return cost;
    }
    
    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public StringProperty installDateProperty() {
        return installDate;
    }

    public StringProperty expireDateProperty() {
        return warrantyExpireDate;
    }

    public StringProperty vehRegNoProperty() {
        return vehicleRegNo;
    }

    public StringProperty customerNameProperty() {
        return customerFullName;
    }

}
