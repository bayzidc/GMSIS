/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PartsRecord.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

/**
 *
 * @author Fabiha
 */
public class PartsStock {
    
    public IntegerProperty partIDentify;
    public StringProperty partName;
    public StringProperty partDescription;
    public IntegerProperty partStockLevel;
    public DoubleProperty cost;
    
   
   
    public PartsStock(int partId,String partName, String partDescription,int partStockLevel, double cost) {
        
        this.partIDentify = new SimpleIntegerProperty(partId);
        this.partName = new SimpleStringProperty(partName);
        this.partDescription = new SimpleStringProperty(partDescription);
        this.partStockLevel= new SimpleIntegerProperty(partStockLevel);
        this.cost = new SimpleDoubleProperty(cost);
    }
    
    
    // Return the 'partsid' value.
    public int getPartIDentify() {
        return partIDentify.get();
    }
    public String getPartName() {
        return partName.get();
    }

    public String getPartDescription() {
        return partDescription.get();
    }

    public int getPartStockLevel() {
        return partStockLevel.get();
    }
    
    public double getCost() {
        return cost.get();
    }
    // Set the 'partsId' value
    public void setPartId(int number) {
        partIDentify.set(number);
    }
    
    public void setPartName(String name) {
        partName.set(name);
    }

    public void setPartDescription(String description) {
        partDescription.set(description);
    }
    
    public void setPartStockLevel(int number) {
        partStockLevel.set(number);
    }
    public void setCost(double number) {
        cost.set(number);
    }

    //Property values
    // Returns the StringProperty object
    public IntegerProperty partIDProperty() {
        return partIDentify;
    }
    
    public StringProperty partNameProperty() {
        return partName;
    }

    public StringProperty partDescriptionProperty() {
        return partDescription;
    }
    
    public IntegerProperty partStockProperty() {
        return partStockLevel ;
    }
    
    public DoubleProperty costProperty() {
        return cost;
    }
    
    
 }
