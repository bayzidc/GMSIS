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
public class PartsItem {
    
    
    public StringProperty partName;
    public IntegerProperty quantity;
    public StringProperty arrivalDate;
    
    public PartsItem(String partName, int quantity, String arrivalDate) {
        //this.partID = new SimpleIntegerProperty(partID);
        this.partName = new SimpleStringProperty(partName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.arrivalDate = new SimpleStringProperty(arrivalDate);
    }
    
    

    public String getPartName() {
        return partName.get();
    }
    
    public int getQuantity() {
        return quantity.get();
    }

    public String getArrivalDate() {
        return arrivalDate.get();
    }
    
    
    
    public void setPartName(String name) {
        partName.set(name);
    }

    public void setQuantity(int number) {
        quantity.set(number);
    }
    
    public void setArrivalDate(String date) {
        arrivalDate.set(date);
    }
    
}
